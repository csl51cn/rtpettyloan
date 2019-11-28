package com.global.fems.business.strategy.imp;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.dao.DeclareResultDao;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.domain.DeclareResult;
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
 * 贷款合同信息上传策略
 */
@Component
public class SendContractInoBatchFileStrategy implements SendBatchFileStrategy {

    @Autowired
    private ContractInfoDao contractInfoDao;
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
        ArrayList<ContractInfoCycleNode> list = new ArrayList<ContractInfoCycleNode>();
        for (String id : idsArr) {
            ContractInfoCycleNode contractById = contractInfoDao.findContractById(id);
            list.add(contractById);
        }
        //上传批量文件
        Map<String, String> sendContractInfoBatchFileResult = jrbBizInfoDeclareManager.sendContractInfoBatchFile(list);
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
                for (ContractInfoCycleNode node : list) {
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
                    contractInfoDao.saveOrUpdate(node);
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
     * @param isSend  上报状态
     */
    @Override
    public void updateStatus(String batchNo, int isSend) {
        List<ContractInfoCycleNode> contractInfoCycleNodes = contractInfoDao.findByBatchNo(batchNo);
        contractInfoCycleNodes.forEach(contractInfoCycleNode -> contractInfoCycleNode.setIsSend(isSend));
        contractInfoDao.batchUpdateContract(contractInfoCycleNodes,true);
    }

    @Override
    public void update(Entity entity) {
        DeclareResult declareResult = (DeclareResult) entity;
        if (StringUtils.equals(declareResult.getDataType(), MessageTypeEnum.CONTRACT_INFO.getDesc())) {
            List<ContractInfoCycleNode> list = contractInfoDao.findByBatchNo(declareResult.getBatchNo());
            if (list.size() > 0) {
                Integer isSend;
                if ("000000".equals(declareResult.getDeclareResultCode())) {
                    isSend = 1;
                } else {
                    isSend = 0;
                }
                list.forEach(contractInfoCycleNode -> {
                    contractInfoCycleNode.setIsSend(isSend);
                    contractInfoDao.saveOrUpdate(contractInfoCycleNode);
                });
            }
        }
    }
}
