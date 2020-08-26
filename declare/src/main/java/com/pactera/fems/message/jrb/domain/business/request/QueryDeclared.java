package com.pactera.fems.message.jrb.domain.business.request;

import com.pactera.fems.message.jrb.domain.JRBGetTx;

/**
 * 查询申报结果
 */
public class QueryDeclared extends JRBGetTx {

    private String fileName;
    private String dataType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
