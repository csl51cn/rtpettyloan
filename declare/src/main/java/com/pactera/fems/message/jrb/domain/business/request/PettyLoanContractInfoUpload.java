package com.pactera.fems.message.jrb.domain.business.request;

import java.util.List;

/**
 * @author: Senlin.Deng
 * @Description: 网签批量文件
 * @date: Created in 2020/8/19 16:28
 * @Modified By:
 */
public class PettyLoanContractInfoUpload extends BatchFileInfo {
    /**
     * 数据类型
     */
    private String dataType = "NETBOOK_INFO";

    private List<PettyLoanContractInfoUploadParam> pettyLoanContractInfo;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<PettyLoanContractInfoUploadParam> getPettyLoanContractInfo() {
        return pettyLoanContractInfo;
    }

    public void setPettyLoanContractInfo(List<PettyLoanContractInfoUploadParam> pettyLoanContractInfo) {
        this.pettyLoanContractInfo = pettyLoanContractInfo;
    }
}
