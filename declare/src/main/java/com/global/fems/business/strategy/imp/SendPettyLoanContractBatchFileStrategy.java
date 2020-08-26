package com.global.fems.business.strategy.imp;

import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.domain.PettyLoanContract;
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
 * @author: Senlin.Deng
 * @Description: 网签文件批量报送策略
 * @date: Created in 2020/8/20 11:51
 * @Modified By:
 */
@Component
public class SendPettyLoanContractBatchFileStrategy implements SendBatchFileStrategy {

    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    @Autowired
    private JRBBizInfoDeclareManager jrbBizInfoDeclareManager;
    @Autowired
    private DeclareResultDao declareResultDao;

    /**
     * 批量报文文件发送
     *
     * @param ids    记录id
     * @param userId 用户id
     * @return 返回发送结果
     * @throws Exception 异常
     */
    @Override
    public ResultModel sendBatchFile(String ids, String userId) throws Exception {

        String[] idsArr = ids.split(",");
        List<PettyLoanContract> list = new ArrayList<>();
        for (String id : idsArr) {
            PettyLoanContract contractById = pettyLoanContractDao.findPettyLoanContractById(id);
            list.add(contractById);
        }

        //上传批量文件
        Map<String, String> sendContractInfoBatchFileResult = jrbBizInfoDeclareManager.sendPettyLoanContractBatchFile(list);
        DeclareResult declareResult = createDeclareResult(userId, sendContractInfoBatchFileResult);
        declareResultDao.saveOrUpdate(declareResult);

        //是否有错误信息
        if (sendContractInfoBatchFileResult.get("validateError") != null) {
            //数据校验失败
            //给出合同信息
            String validateError = sendContractInfoBatchFileResult.get("validateError");
            return ResultModel.fail(validateError);

        } else if (sendContractInfoBatchFileResult.get("error") != null) {
            //批量文件发送失败
            String error = sendContractInfoBatchFileResult.get("error");
            return ResultModel.fail(error);
        }

        String fileName = sendContractInfoBatchFileResult.get("fileName");
        //发送实时报文,指明文件位置
        Map contractInfoDeclareResult = jrbBizInfoDeclareManager.packageMsgHeader(fileName);
        JRBRespBatchFileMsg respMsg = (JRBRespBatchFileMsg) contractInfoDeclareResult.get("respMsg");

        if (respMsg != null) {
            JRBRespHeaderMsg msg = respMsg.getHeader().getMsg();
            JRBRET ret = msg.getRet();
            String retCode = ret.getRetCode();
            if ("000000".equals(retCode)) {
                //申报成功,更新数据库
                for (PettyLoanContract node : list) {
                    //设置为已发送
                    node.setSendStatus(1);
                    //设置批次号
                    node.setBatchNo(msg.getSeqNo());
                    //设置记录数
                    node.setRecordCount(list.size() + "");
                    //设置申报时间
                    String tranDate = msg.getTranDate();
                    String tranTimestamp = msg.getTranTimestamp();
                    Timestamp strToTimestamp = SysUtils.getStrToTimestamp(tranDate + " " + tranTimestamp, "yyyyMMdd HHmmssSSS");
                    node.setSendDate(strToTimestamp);
                    pettyLoanContractDao.saveOrUpdate(node);
                }
                return ResultModel.ok();
            } else {
                return ResultModel.fail("申报失败:" + ReturnMsgCodeEnum.getValueByCode(retCode));
            }
        } else {
            return ResultModel.fail("申报失败:批量报文文件已发送,实时报文未获得响应");
        }

    }

    /**
     * 更新上报状态
     *
     * @param batchNo 批次号
     * @param isSend  是否上报
     */
    @Override
    public void updateStatus(String batchNo, int isSend) {
        List<PettyLoanContract> byBatchNo = pettyLoanContractDao.findByBatchNo(batchNo);
        byBatchNo.forEach(quotaInfo -> quotaInfo.setSendStatus(isSend));
        pettyLoanContractDao.batchUpdateInfo(byBatchNo, true);
    }

    @Override
    public void update(Entity entity) {
        DeclareResult declareResult = (DeclareResult) entity;
        if (StringUtils.equals(declareResult.getDataType(), MessageTypeEnum.NETBOOK_INFO.getDesc())) {
            List<PettyLoanContract> list = pettyLoanContractDao.findByBatchNo(declareResult.getBatchNo());
            if (list.size() > 0) {
                Integer isSend;
                if ("000000".equals(declareResult.getDeclareResultCode())) {
                    isSend = 1;
                } else {
                    isSend = 0;
                }
                list.forEach(node -> {
                    node.setSendStatus(isSend);
                    pettyLoanContractDao.saveOrUpdate(node);
                });
            }
        }
    }
}
