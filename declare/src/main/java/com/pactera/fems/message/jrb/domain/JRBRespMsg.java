package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * Created by senlin.deng on 2017-05-26.
 */
public class JRBRespMsg extends SerialBean {
    private JRBRespHeader header;
    private JRBRespBody  body;

    public JRBRespHeader getHeader() {
        return header;
    }

    public void setHeader(JRBRespHeader header) {
        this.header = header;
    }

    public JRBRespBody getBody() {
        return body;
    }

    public void setBody(JRBRespBody body) {
        this.body = body;
    }
}
