package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

public class JRBReqHeader extends SerialBean {
    private JRBReqHeaderMsg msg;

    public JRBReqHeader(){}
    public JRBReqHeader(JRBReqHeaderMsg headerMsg) {
        this.msg = headerMsg;
    }

    public void setMsg(JRBReqHeaderMsg msg) {
        this.msg = msg;
    }
    public JRBReqHeaderMsg getMsg() {
        return msg;
    }




}
