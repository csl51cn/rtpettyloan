package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 授信额度信息批量文件上传实体类
 */
public class QuotaInfo  extends BatchFileInfo {
    private String recordCount;
    private String batchNo;
    private String dataType = "QUOTA_INFO";//数据类型
    private List<QuotaInfoParam> quotaInfo;//额度信息数组

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<QuotaInfoParam> getQuotaInfo() {
        return quotaInfo;
    }

    @Override
    public String getRecordCount() {
        return recordCount;
    }

    @Override
    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public void setQuotaInfo(List<QuotaInfoParam> quotaInfo) {
        this.quotaInfo = quotaInfo;

    }

    @Override
    public String getBatchNo() {
        return batchNo;
    }

    @Override
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
