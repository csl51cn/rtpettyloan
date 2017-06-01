package com.pactera.fems.message.jrb.domain.business.response;

import com.pactera.fems.message.jrb.domain.JRBRtrTx;

public class RealTimeContractRtrTx extends JRBRtrTx {

    private String  netSignId;//网签编号

    public String getNetSignId() {
        return netSignId;
    }

    public void setNetSignId(String netSignId) {
        this.netSignId = netSignId;
    }
}
