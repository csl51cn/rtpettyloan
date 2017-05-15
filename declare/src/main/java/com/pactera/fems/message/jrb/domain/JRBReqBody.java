package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

public class JRBReqBody extends SerialBean {


    private JRBGetTx getTx;

    public JRBReqBody() {

    }

    public JRBReqBody(JRBGetTx getTx) {
        this.getTx = getTx;
    }

    public JRBGetTx getGetTx() {
        return getTx;
    }

    public void setGetTx(JRBGetTx getTx) {
        this.getTx = getTx;
    }
}
