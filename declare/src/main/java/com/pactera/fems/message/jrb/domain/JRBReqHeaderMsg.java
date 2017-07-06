package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;


public class JRBReqHeaderMsg extends SerialBean {
    private String serviceCode; //服务代码:实时：SVR_PTLN ,文件：SVR_FILE
    private String tranCode;//交易码
    private String tranType;//交易类型 可选 保留域,默认为空
    private String tranMode;//交易模式,实时:ONLINE ,文件:ASYNC
    private String branchId;//组织机构代码
    private String tranDate;//交易日期:YYYYMMDD
    private String tranTimestamp;//交易时间:HHMMSSNNN
    private String serverId;//服务器标识 可选 保留域，默认为空
    private String wsId;//终端标识 可选  小贷公司上报系统服务器地址
    private String userLang;//用户语言,中文：CHINESE,英文：AMERICAN/ENGLISH
    private String seqNo;//渠道流水号 小贷公司保证自身流水号不重复
    private String sourceBranchNo;//	源节点编号 可选 保留域，默认为空
    private String destBranchNo;//目标节点编号 可选 保留域，默认为空
    private String moduleId;//模块标识	 CL
    private String messageType;//报文类型
    private String messageCode;//报文代码
    private String filePath;//	文件路径	文件报文使用 文件名规则：组织机构代码-YYYYMMDD-数据类型-XX.xml

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranMode() {
        return tranMode;
    }

    public void setTranMode(String tranMode) {
        this.tranMode = tranMode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTimestamp() {
        return tranTimestamp;
    }

    public void setTranTimestamp(String tranTimestamp) {
        this.tranTimestamp = tranTimestamp;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getWsId() {
        return wsId;
    }

    public void setWsId(String wsId) {
        this.wsId = wsId;
    }

    public String getUserLang() {
        return userLang;
    }

    public void setUserLang(String userLang) {
        this.userLang = userLang;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSourceBranchNo() {
        return sourceBranchNo;
    }

    public void setSourceBranchNo(String sourceBranchNo) {
        this.sourceBranchNo = sourceBranchNo;
    }

    public String getDestBranchNo() {
        return destBranchNo;
    }

    public void setDestBranchNo(String destBranchNo) {
        this.destBranchNo = destBranchNo;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
