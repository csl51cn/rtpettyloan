package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 授信额度信息批量文件上传实体类
 */
public class QuotaInfo  extends BatchFileInfo {
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

    public void setQuotaInfo(List<QuotaInfoParam> quotaInfo) {
        this.quotaInfo = quotaInfo;
    }
}
