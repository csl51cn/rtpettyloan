package com.pactera.fems.message.jrb.domain.business.request;

import com.pactera.fems.message.jrb.domain.JRBGetTx;

/**
 * 查询申报结果
 */
public class QueryDeclared extends JRBGetTx {

    private String batchNo;
    private String dataType;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
