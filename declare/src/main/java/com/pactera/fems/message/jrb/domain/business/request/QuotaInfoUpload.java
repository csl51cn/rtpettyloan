package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 授信额度信息批量文件上传实体类
 *
 * @author senlin.deng
 * @date 2018/4/8 14:37
 */
public class QuotaInfoUpload extends BatchFileInfo {

    /**
     * 数据类型
     */
    private String dataType = "QUOTA_INFO";
    /**
     * 额度信息数组
     */
    private List<QuotaInfoUploadParam> quotaInfo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<QuotaInfoUploadParam> getQuotaInfo() {
        return quotaInfo;
    }

    public void setQuotaInfo(List<QuotaInfoUploadParam> quotaInfo) {
        this.quotaInfo = quotaInfo;
    }
}
