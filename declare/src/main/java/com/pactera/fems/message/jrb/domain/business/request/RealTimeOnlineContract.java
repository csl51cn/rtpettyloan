package com.pactera.fems.message.jrb.domain.business.request;

import com.global.framework.util.SysUtils;
import com.pactera.fems.message.jrb.domain.JRBGetTx;

import java.util.Date;

/**
 * 实时网签
 */
public class RealTimeOnlineContract extends JRBGetTx {


    private String contractNo; //合同编号

    private String loanCate;//贷款类型

    private String customerType;//借款人类别

    private String customerName;//借款人名称

    private String certificateType;//借款人证件类型

    private String certificateNo;//证件号码

    private String conCustomerType;//委托人类别

    private String conCustomerName;//委托人

    private String conCertificateType;//委托人证件类型

    private String conCertificateNo;//委托人证件号码

    private String  conFee;//委托代理费

    private String contractAmount;//合同金额

    private String intRate;//月利率

    private String contractSignDate;//合同签订日期

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

    public String getConCustomerType() {
        return conCustomerType;
    }

    public void setConCustomerType(String conCustomerType) {
        this.conCustomerType = conCustomerType;
    }

    public String getConCustomerName() {
        return conCustomerName;
    }

    public void setConCustomerName(String conCustomerName) {
        this.conCustomerName = conCustomerName;
    }

    public String getConCertificateType() {
        return conCertificateType;
    }

    public void setConCertificateType(String conCertificateType) {
        this.conCertificateType = conCertificateType;
    }

    public String getConCertificateNo() {
        return conCertificateNo;
    }

    public void setConCertificateNo(String conCertificateNo) {
        this.conCertificateNo = conCertificateNo;
    }

    public String getConFee() {
        return conFee;
    }

    public void setConFee(String conFee) {
        this.conFee = conFee;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(String contractSignDate) {
        java.sql.Date date = SysUtils.getStrToDate(contractSignDate, "yyyyMMdd");
        this.contractSignDate = date.toString();
    }
}
