package com.global.fems.business.strategy.imp;

import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.dao.QuotaInfoDao;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.domain.QuotaInfo;
import com.global.fems.business.enums.MessageTypeEnum;
import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.business.strategy.SendBatchFileStrategy;
import com.global.fems.message.service.impl.JRBBizInfoDeclareManager;
import com.global.framework.dbutils.support.Entity;
import com.global.framework.util.SysUtils;
import com.global.param.domain.ResultModel;
import com.pactera.fems.message.jrb.domain.JRBRET;
import com.pactera.fems.message.jrb.domain.JRBRespBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBRespHeaderMsg;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 授信额度信息上传策略
 */
@Component
public class SendQuotatInoBatchFileStrategy implements SendBatchFileStrategy {

    @Autowired
    private QuotaInfoDao quotaInfoDao;
    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;
    @Autowired
    private DeclareResultDao declareResultDao;

    /**
     * 根据传入的id,查询合同信息,调用报文申报管理类,先上传批量报文文件到SFTP,然后发送实时报文
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel sendBatchFile(String ids, String userId) throws Exception {
        String[] idsArr = ids.split(",");
        ArrayList<QuotaInfo> list = new ArrayList<>();
        for (String id : idsArr) {
            QuotaInfo quotaInfoById = quotaInfoDao.findQuotaInfoById(id);
            list.add(quotaInfoById);
        }
        //上传批量文件
        Map<String, String> sendQuotaInfoBatchFileResult = jrbBizInfoDeclareManager.sendQuotaInfoBatchFile(list);


        //是否有错误信息
        if (sendQuotaInfoBatchFileResult.get("validateError") != null) {
            //数据校验失败
            //给出合同信息
            String validateError = sendQuotaInfoBatchFileResult.get("validateError");
            return ResultModel.fail(validateError);

        } else if (sendQuotaInfoBatchFileResult.get("error") != null) {
            //批量文件发送失败
            String error = sendQuotaInfoBatchFileResult.get("error");
            return ResultModel.fail(error);
        }

        DeclareResult declareResult = createDeclareResult(userId, sendQuotaInfoBatchFileResult);
        declareResultDao.saveOrUpdate(declareResult);

        String fileName = sendQuotaInfoBatchFileResult.get("fileName");
        //发送实时报文,指明文件位置
        Map contractInfoDeclareResult = jrbBizInfoDeclareManager.packageMsgHeader(fileName);
        JRBRespBatchFileMsg respMsg = (JRBRespBatchFileMsg) contractInfoDeclareResult.get("respMsg");

        if (respMsg != null) {
            JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
            JRBRET ret = msg.getRet();
            String retCode = ret.getRetCode();
            if ("000000".equals(retCode)) {
                //申报成功,更新数据库
                for (QuotaInfo node : list) {
                    //设置为已发送
                    node.setIsSend(1);
                    //设置批次号
                    node.setBatchNo(msg.getSeqNo());
                    //设置记录数
                    node.setRecordCount(list.size() + "");
                    //设置申报时间
                    String tranDate = msg.getTranDate();
                    String tranTimestamp = msg.getTranTimestamp();
                    Timestamp strToTimestamp = SysUtils.getStrToTimestamp(tranDate + " " + tranTimestamp, "yyyyMMdd HHmmssSSS");
                    node.setSendDate(strToTimestamp);
                    quotaInfoDao.saveOrUpdate(node);
                }

                return ResultModel.ok();
            } else {
                return ResultModel.fail("申报失败:" + ReturnMsgCodeEnum.getValueByCode(retCode));
            }
        } else {
            return ResultModel.fail("申报失败:批量报文文件已发送,实时报文未获得响应");
        }

    }

    @Override
    public void update(Entity entity) {
        DeclareResult declareResult = (DeclareResult) entity;
        if (StringUtils.equals(declareResult.getDataType(), MessageTypeEnum.QUOTA_INFO.getDesc())) {
            List<QuotaInfo> list = quotaInfoDao.findByBatchNo(declareResult.getBatchNo());
            if (list.size() > 0) {
                Integer isSend;
                if ("000000".equals(declareResult.getDeclareResultCode())) {
                    isSend = 1;
                } else {
                    isSend = 0;
                }
                list.forEach(quotaInfo -> {
                    quotaInfo.setIsSend(isSend);
                    quotaInfoDao.saveOrUpdate(quotaInfo);
                });
            }
        }
    }
}
