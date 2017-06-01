package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * Created by senlin.deng on 2017-05-26.
 */
public class JRBRespHeader  extends SerialBean {
    private JRBRespHeaderMsg msg;

    public JRBRespHeaderMsg getMsg() {
        return msg;
    }

    public void setMsg(JRBRespHeaderMsg msg) {
        this.msg = msg;
    }
}
