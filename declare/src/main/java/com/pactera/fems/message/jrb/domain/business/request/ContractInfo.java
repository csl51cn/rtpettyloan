package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * 贷款合同信息批量文件上传实体类
 */
public class ContractInfo  extends BatchFileInfo {
    private String dataType = "QUOTA_INFO";//数据类型

    private List<ContractInfoParam> contractInfo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public List<ContractInfoParam> getContractInfo() {
        return contractInfo;
    }


    public void setContractInfo(List<ContractInfoParam> contractInfo) {
        this.contractInfo = contractInfo;
    }
}
