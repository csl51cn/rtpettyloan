package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

public class JRBReqHeader extends SerialBean {
    public JRBReqHeaderMsg getHeaderMsg() {
        return headerMsg;
    }
    public JRBReqHeader(){}
    public JRBReqHeader(JRBReqHeaderMsg headerMsg) {
        this.headerMsg = headerMsg;
    }

    public void setHeaderMsg(JRBReqHeaderMsg headerMsg) {
        this.headerMsg = headerMsg;
    }

    private JRBReqHeaderMsg headerMsg;


}
