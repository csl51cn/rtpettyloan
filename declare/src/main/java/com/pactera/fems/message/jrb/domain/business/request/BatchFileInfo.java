package com.pactera.fems.message.jrb.domain.business.request;

import com.pactera.fems.message.jrb.domain.JRBGetTx;

/**
 * Created by senlin.deng on 2017-05-23.
 */
public class BatchFileInfo extends JRBGetTx {
    protected String batchNo;//批次号,小贷公司保证批次号唯一

    protected String recordCount; //总记录数,正整数

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }


}
