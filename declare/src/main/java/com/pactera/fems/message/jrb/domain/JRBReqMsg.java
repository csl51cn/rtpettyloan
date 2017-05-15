package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;


public class JRBReqMsg extends SerialBean {
    private JRBReqHeader header;
    private JRBReqBody  body;

    public JRBReqHeader getHeader() {
        return header;
    }

    public void setHeader(JRBReqHeader header) {
        this.header = header;
    }

    public JRBReqBody getBody() {
        return body;
    }

    public void setBody(JRBReqBody body) {
        this.body = body;
    }
}
