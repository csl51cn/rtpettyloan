package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 小额贷款合同实体类
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_PETTY_LOAN_CONTRACT")
public class PettyLoanContract extends Entity  {


    @ColumnMapping(columnName = "id", columnType = "String")
    private String id; // 主键
    @NotEmpty(message = "PettyLoanContract.contractNo.null")
    @ColumnMapping(columnName = "contractNo", columnType = "String")
    private String contractNo; //合同编号
    @NotEmpty(message = "PettyLoanContract.loanCate.null")
    @ColumnMapping(columnName = "loanCate", columnType = "String")
    private String loanCate;//贷款类型
    @NotEmpty(message = "PettyLoanContract.customerType.null")
    @ColumnMapping(columnName = "customerType", columnType = "String")
    private String customerType;//借款人类别
    @NotEmpty(message = "PettyLoanContract.customerName.null")
    @ColumnMapping(columnName = "customerName", columnType = "String")
    private String customerName;//借款人名称
    @NotEmpty(message="PettyLoanContract.certificateType.null")
    @ColumnMapping(columnName = "certificateType", columnType = "String")
    private String certificateType;//借款人证件类型
    @NotEmpty(message="PettyLoanContract.certificateNo.null")
    @ColumnMapping(columnName = "certificateNo", columnType = "String")
    private String certificateNo;//证件号码
    @ColumnMapping(columnName = "conCustomerType", columnType = "String")
    private String conCustomerType;//委托人类别
    @ColumnMapping(columnName = "conCustomerName", columnType = "String")
    private String conCustomerName;//委托人姓名
    @ColumnMapping(columnName = "conCertificateType", columnType = "String")
    private String conCertificateType;//委托人证件类型
    @ColumnMapping(columnName = "conCertificateNo", columnType = "String")
    private String conCertificateNo;//委托人证件号码
    @ColumnMapping(columnName = "conFee", columnType = "BigDecimal")
    private Double conFee;//委托代理费
    @NotEmpty(message = "PettyLoanContract.contractAmount.null")
    @ColumnMapping(columnName = "contractAmount", columnType = "BigDecimal")
    private Double contractAmount;//合同金额
    @NotEmpty(message = "PettyLoanContract.intRate.null")
    @ColumnMapping(columnName = "intRate", columnType = "BigDecimal")
    private Double intRate;//月利率
    @NotEmpty(message = "PettyLoanContract.contractSignDate.null")
    @ColumnMapping(columnName = "contractSignDate", columnType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractSignDate;//合同签订日期
    @ColumnMapping(columnName = "sendStatus", columnType = "Integer")
    private Integer sendStatus ;//发送状态,0表示未发送，1表示已发送
    @ColumnMapping(columnName = "insertDate", columnType = "Date")
    private Date insertDate;//记录保存日期
    @ColumnMapping(columnName = "sendDate", columnType = "Date")
    private Date sendDate;//记录保存日期
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getConFee() {
        return conFee;
    }

    public void setConFee(Double conFee) {
        this.conFee = conFee;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getIntRate() {
        return intRate;
    }

    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Date getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(Date contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }


}
