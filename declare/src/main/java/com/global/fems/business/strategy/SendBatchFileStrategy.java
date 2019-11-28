package com.global.fems.business.strategy;

import com.global.fems.business.domain.DeclareResult;
import com.global.fems.business.service.Observer;
import com.global.param.domain.ResultModel;

import java.util.Date;
import java.util.Map;

/**
 * 批量发送报文文件策略接口
 *
 * @author senlin.deng
 */
public interface SendBatchFileStrategy extends Observer {

    /**
     * 批量报文文件发送
     *
     * @param ids    记录id
     * @param userId 用户id
     * @return 返回发送结果
     * @throws Exception 异常
     */
    ResultModel sendBatchFile(String ids, String userId) throws Exception;


    /**
     * 创建上报结果
     *
     * @param userId              用户id
     * @param sendBatchFileResult batchNo,dataType,recordCount相关信息的map
     * @return 返回上报结果对象
     */
    default DeclareResult createDeclareResult(String userId, Map<String, String> sendBatchFileResult) {
        DeclareResult declareResult = new DeclareResult();
        declareResult.setBatchNo(sendBatchFileResult.get("batchNo"));
        declareResult.setDataType(sendBatchFileResult.get("dataType"));
        declareResult.setRecordCount(sendBatchFileResult.get("recordCount"));
        declareResult.setRemoteFilePath(sendBatchFileResult.get("fileName"));
        declareResult.setGmtCreate(new Date());
        declareResult.setCreateId(userId);
        declareResult.setGmtModified(declareResult.getGmtCreate());
        declareResult.setModifiedId(declareResult.getCreateId());
        return declareResult;
    }

    /**
     * 更新上报状态
     *
     * @param batchNo 批次号
     * @param isSend 是否上报
     */
    void updateStatus(String batchNo, int isSend);
}
