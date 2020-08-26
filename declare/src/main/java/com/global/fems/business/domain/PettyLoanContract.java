package com.global.fems.business.domain;


import com.global.fems.business.interfaces.First;
import com.global.fems.business.interfaces.Second;
import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 小额贷款合同实体类
 *
 * @author senlin.deng
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_PETTY_LOAN_CONTRACT")
public class PettyLoanContract extends Entity {

    /**
     * 主键
     */
    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;
    /**
     * Data_WorkInfo中的Date_Id
     */
    @ColumnMapping(columnName = "dateid", columnType = "Integer")
    private Integer dateId;

    /**
     * 合同编号 JK开头
     */
    @NotBlank(message = "{PettyLoanContract.contractNo.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contractNo", columnType = "String")
    private String contractNo;

    /**
     * 贷款类型
     */
    @NotBlank(message = "{PettyLoanContract.loanCate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "loanCate", columnType = "String")
    private String loanCate;

    /**
     * 借款人类型
     */
    @NotBlank(message = "{PettyLoanContract.customerType.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "customerType", columnType = "String")
    private String customerType;

    /**
     * 借款人名称
     */
    @NotBlank(message = "{PettyLoanContract.customerName.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "customerName", columnType = "String")
    private String customerName;
    /**
     * 借款人证件类型
     */
    @NotBlank(message = "{PettyLoanContract.certificateType.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "certificateType", columnType = "String")
    private String certificateType;
    /**
     * 证件号码
     */
    @NotBlank(message = "{PettyLoanContract.certificateNo.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "certificateNo", columnType = "String")
    private String certificateNo;
    /**
     * 委托人类别
     */
    @NotBlank(message = "{PettyLoanContract.conCustomerType.null}", groups = {Second.class})
    @ColumnMapping(columnName = "conCustomerType", columnType = "String")
    private String conCustomerType;

    /**
     * 委托人姓名
     */
    @NotBlank(message = "{PettyLoanContract.conCustomerName.null}", groups = {Second.class})
    @ColumnMapping(columnName = "conCustomerName", columnType = "String")
    private String conCustomerName;
    /**
     * 委托人证件类型
     */
    @NotBlank(message = "{PettyLoanContract.conCertificateType.null}", groups = {Second.class})
    @ColumnMapping(columnName = "conCertificateType", columnType = "String")
    private String conCertificateType;
    /**
     * 委托人证件号码
     */
    @NotBlank(message = "{PettyLoanContract.conCertificateNo.null}", groups = {Second.class})
    @ColumnMapping(columnName = "conCertificateNo", columnType = "String")
    private String conCertificateNo;
    /**
     * 委托代理费
     */
    @NotNull(message = "{PettyLoanContract.conFee.null}", groups = {Second.class})
    @ColumnMapping(columnName = "conFee", columnType = "BigDecimal")
    private Double conFee;

    /**
     * 合同金额
     */
    @NotNull(message = "{PettyLoanContract.contractAmount.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contractAmount", columnType = "BigDecimal")
    private Double contractAmount;
    /**
     * 月利率
     */
    @NotNull(message = "{PettyLoanContract.intRate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "intRate", columnType = "BigDecimal")
    private Double intRate;
    /**
     * 合同签订日期
     */
    @NotNull(message = "{PettyLoanContract.contractSignDate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contractSignDate", columnType = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractSignDate;
    /**
     * 发送状态,0表示未发送，1表示已发送
     */
    @ColumnMapping(columnName = "sendStatus", columnType = "Integer")
    private Integer sendStatus;

    /**
     * 记录保存日期
     */
    @ColumnMapping(columnName = "insertDate", columnType = "Date")
    private Date insertDate;
    /**
     * 记录申报日期
     */
    @ColumnMapping(columnName = "sendDate", columnType = "Date")
    private Date sendDate;
    /**
     * 渠道流水号
     */
    @ColumnMapping(columnName = "seqno", columnType = "String")
    private String seqNo;
    @ColumnMapping(columnName = "netsignno", columnType = "String")
    /**
     * 网签编号
     */
    private String netSignNo;
    /**
     * 是否是最新
     */
    @ColumnMapping(columnName = "islast", columnType = "String")
    private String isLast;
    /**
     * 业务编号，查询时会使用，DC_PETTY_LOAN_CONTRACT表中没有
     */
    private String businessNum;
    /**
     * 是否循环授信
     */
    @ColumnMapping(columnName = "is_real_quota_loan", columnType = "String")
    private String isRealQuotaLoan;
    /**
     * 循环授信合同编号
     */
    @ColumnMapping(columnName = "real_quota_no", columnType = "String")
    private String realQuotaNo;

    /**
     * 批次号
     */
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;

    /**
     * 同批次总记录数
     */
    @ColumnMapping(columnName = "record_count", columnType = "String")
    private String recordCount;

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

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getNetSignNo() {
        return netSignNo;
    }

    public void setNetSignNo(String netSignNo) {
        this.netSignNo = netSignNo;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    public String getIsRealQuotaLoan() {
        return isRealQuotaLoan;
    }

    public void setIsRealQuotaLoan(String isRealQuotaLoan) {
        this.isRealQuotaLoan = isRealQuotaLoan;
    }

    public String getRealQuotaNo() {
        return realQuotaNo;
    }

    public void setRealQuotaNo(String realQuotaNo) {
        this.realQuotaNo = realQuotaNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }
}
