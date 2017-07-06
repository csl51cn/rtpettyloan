package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * Created by senlin.deng on 2017-05-26.
 */
public class JRBRespHeaderMsg extends SerialBean {

    private JRBRET ret;
    private String tranTimestamp;
    private String messageType;
    private String sourceBranchNo;
    private String branchId;
    private String messageCode;
    private String retStatus;
    private String serviceCode;
    private String destBranchNo;
    private String tranDate;
    private String seqNo;
    private String  filePath;

    public JRBRET getRet() {
        return ret;
    }

    public void setRet(JRBRET ret) {
        this.ret = ret;
    }

    public String getTranTimestamp() {
        return tranTimestamp;
    }

    public void setTranTimestamp(String tranTimestamp) {
        this.tranTimestamp = tranTimestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public String getSourceBranchNo() {
        return sourceBranchNo;
    }

    public void setSourceBranchNo(String sourceBranchNo) {
        this.sourceBranchNo = sourceBranchNo;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDestBranchNo() {
        return destBranchNo;
    }

    public void setDestBranchNo(String destBranchNo) {
        this.destBranchNo = destBranchNo;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
