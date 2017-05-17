package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

public class JRBReqBody extends SerialBean {


    private JRBGetTx gettx;

    public JRBReqBody() {

    }

    public JRBReqBody(JRBGetTx gettx) {
        this.gettx = gettx;
    }

    public JRBGetTx getGettx() {
        return gettx;
    }

    public void setGettx(JRBGetTx gettx) {
        this.gettx = gettx;
    }
}
