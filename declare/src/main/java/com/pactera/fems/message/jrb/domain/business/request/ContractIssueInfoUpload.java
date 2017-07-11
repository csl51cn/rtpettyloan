package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 贷款放款信息批量文件上传实体类
 */
public class ContractIssueInfoUpload extends BatchFileInfo {
    private String dataType = "ISSUE_INFO";//数据类型
    private List<ContractIssueInfoUploadParam> issueInfo;//发放信息集合
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<ContractIssueInfoUploadParam> getIssueInfo() {
        return issueInfo;
    }

    public void setIssueInfo(List<ContractIssueInfoUploadParam> issueInfo) {
        this.issueInfo = issueInfo;
    }
}
