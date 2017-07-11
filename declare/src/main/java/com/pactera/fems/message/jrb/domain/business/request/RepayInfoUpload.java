package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 贷款回收信息批量文件上传实体类
 */
public class RepayInfoUpload extends BatchFileInfo {
    private String dataType = "REPAY_INFO";//数据类型
    private List<RepayInfoUploadParam> repayInfo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<RepayInfoUploadParam> getRepayInfo() {
        return repayInfo;
    }

    public void setRepayInfo(List<RepayInfoUploadParam> repayInfo) {
        this.repayInfo = repayInfo;
    }
}
