package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 还款计划信息批量文件上传实体类
 */
public class PayPlanInfoUpload extends BatchFileInfo {
    private String dataType = "PAYPLAN_INFO";//数据类型
    private List<PayPlanInfoUploadParam> payPlanInfo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<PayPlanInfoUploadParam> getPayPlanInfo() {
        return payPlanInfo;
    }

    public void setPayPlanInfo(List<PayPlanInfoUploadParam> payPlanInfo) {
        this.payPlanInfo = payPlanInfo;
    }
}
