package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

public class JRBRET extends SerialBean {
    private String retMsg;
    private String retCode;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
