package com.global.fems.business.common;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: Senlin.Deng
 * @Description: sft批量文件解析异常结果
 * @date: Created in 2020/8/25 13:34
 * @Modified By:
 */
public class SftpErrorResult {

    /**
     * 大数据处理批次号YYYYMMDD.批次号
     */
    @JSONField(name = "proc_batch_no")
    private String processBatchNo;

    /**
     * 发生时间 yyyyMMdd HH:mm:ss
     */
    @JSONField(name = "occuretime")
    private String occurTime;

    /**
     * 发生错误步骤标识
     */
    private String phase;

    /**
     * 组织机构号
     */
    @JSONField(name = "branch_id")
    private String branchId;


    /**
     * 合同编号
     */
    @JSONField(name = "contract_no")
    private String contractNo;

    /**
     * 错误类型
     */
    @JSONField(name = "errortype")
    private String errorType;

    /**
     * 错误信息
     */
    @JSONField(name = "errormsg")
    private String errorMsg;

    /**
     * 错误源数据,同一条合同号如果有多条记录有错误会合并成一条提示,但是oringaldata会表明有多少条,
     * 例如还款计划信息上报,合同号A上报10条,每条都有问题,只会有一个合同号,oringaldata是10
     */
    @JSONField(name = "oringaldata")
    private String oringalData;

    /**
     * 批量文件xml文件名
     */
    @JSONField(name = "batch_file")
    private String batchFile;

    public String getProcessBatchNo() {
        return processBatchNo;
    }

    public void setProcessBatchNo(String processBatchNo) {
        this.processBatchNo = processBatchNo;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOringalData() {
        return oringalData;
    }

    public void setOringalData(String oringalData) {
        this.oringalData = oringalData;
    }

    public String getBatchFile() {
        return batchFile;
    }

    public void setBatchFile(String batchFile) {
        this.batchFile = batchFile;
    }

    @Override
    public String toString() {
        return "SftpResultError{" +
                "processBatchNo='" + processBatchNo + '\'' +
                ", occurTime='" + occurTime + '\'' +
                ", phase='" + phase + '\'' +
                ", branchId='" + branchId + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", oringalData='" + oringalData + '\'' +
                ", batchFile='" + batchFile + '\'' +
                '}';
    }
}
