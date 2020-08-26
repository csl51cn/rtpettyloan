package com.pactera.fems.message.jrb.domain.business.request;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * @author: Senlin.Deng
 * @Description: 网签节点
 * @date: Created in 2020/8/19 16:33
 * @Modified By:
 */
public class PettyLoanContractInfoUploadParam extends SerialBean {


    /**
     * 组织机构代码
     */
    private String orgCode;


    /**
     * 合同号
     */
    private String contractNo;


    /**
     * 贷款类型
     */
    private String loanCate;


    /**
     * 借款人类型
     */
    private String customerType;

    /**
     * 借款人名称
     */

    private String customerName;
    /**
     * 借款人证件类型
     */
    private String certificateType;
    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 合同签署日期
     */
    private String contractSignDate;


    /**
     * 月利率‰
     */
    private String intRate;


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getLoanCate() {
        return loanCate;
    }

    public void setLoanCate(String loanCate) {
        this.loanCate = loanCate;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(String contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }
}
