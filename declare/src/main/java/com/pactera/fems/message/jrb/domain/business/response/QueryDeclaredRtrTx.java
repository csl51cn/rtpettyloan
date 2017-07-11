package com.pactera.fems.message.jrb.domain.business.response;

import com.pactera.fems.message.jrb.domain.JRBRtrTx;

public class QueryDeclaredRtrTx extends JRBRtrTx {

    private String  msgCode;//上报结果代码
    private String  msgInfo;//上报结果信息

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
}
