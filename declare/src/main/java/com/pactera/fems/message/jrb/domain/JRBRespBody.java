package com.pactera.fems.message.jrb.domain;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * Created by senlin.deng on 2017-05-26.
 */
public class JRBRespBody  extends SerialBean {
    private JRBRtrTx rtrtx;

    public JRBRtrTx getRtrtx() {
        return rtrtx;
    }

    public void setRtrtx(JRBRtrTx rtrtx) {
        this.rtrtx = rtrtx;
    }
}
