package com.pactera.fems.message.jrb.domain.business.response;

import com.pactera.fems.message.jrb.domain.JRBRtrTx;

public class QueryDeclaredRtrTx extends JRBRtrTx {

    /**
     * 上报结果代码
     */
    private String  msgCode ;

    /**
     * 上报结果信息
     */
    private String  msgInfo;

    /**
     * 大数据跑批解析此文件的批次号
     */
    private  String batchNo;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
