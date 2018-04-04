package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 贷款发放信息实体类
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_CONTRACT_ISSUE_INFO")
public class ContractIssueInfo  extends Entity{

    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;              //主键
    @ColumnMapping(columnName = "date_id", columnType = "Integer")
    private Integer dateId;        // Data_WorkInfo中的Date_Id
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;        //批次号
    @ColumnMapping(columnName = "data_type", columnType = "String")
    private String dataType;      //数据类型
    @ColumnMapping(columnName = "record_count", columnType = "String")
    private String recordCount; //记录数
    @ColumnMapping(columnName = "report_type", columnType = "String")
    private String reportType;   //上报类型:增 删 改
    @ColumnMapping(columnName = "org_code", columnType = "String")
    private String orgCode;      //组织机构代码
    @NotBlank(message = "{ContractIssueInfo.contractNo.null}")
    @ColumnMapping(columnName = "contract_no", columnType = "String")
    private String contractNo;    //合同编号
    @NotBlank(message = "{ContractIssueInfo.dueBillNo.null}")
    @ColumnMapping(columnName = "due_bill_no", columnType = "String")
    private String dueBillNo; //发放编号
    @NotBlank(message = "{ContractIssueInfo.customerType.null}")
    @ColumnMapping(columnName = "customer_type", columnType = "String")
    private String customerType; //借款人类别
    @NotBlank(message = "{ContractIssueInfo.customerName.null}")
    @ColumnMapping(columnName = "customer_name", columnType = "String")
    private String customerName; //借款人姓名
    @NotBlank(message = "{ContractIssueInfo.certificateType.null}")
    @ColumnMapping(columnName = "certificate_type", columnType = "String")
    private String certificateType; //借款人证件类型
    @NotBlank(message = "{ContractIssueInfo.certificateNo.null}")
    @ColumnMapping(columnName = "certificate_no", columnType = "String")
    private String certificateNo; //借款人证件号码
    @NotNull(message = "{ContractIssueInfo.ddAmt.null}")
    @ColumnMapping(columnName = "dd_amt", columnType = "BigDecimal")
    private Double ddAmt; //发放金额
    @NotBlank(message = "{ContractIssueInfo.loanCate.null}")
    @ColumnMapping(columnName = "loan_cate", columnType = "String")
    private String loanCate;//贷款类别
    @NotNull(message = "{ContractIssueInfo.intRate.null}")
    @ColumnMapping(columnName = "int_rate", columnType = "BigDecimal")
    private Double intRate; //月利率
    @NotNull(message = "{ContractIssueInfo.priPltyRate.null}")
    @ColumnMapping(columnName = "pri_plty_rate", columnType = "BigDecimal")
    private Double priPltyRate;//逾期月利率
    @NotBlank(message = "{ContractIssueInfo.rateType.null}")
    @ColumnMapping(columnName = "rate_type", columnType = "String")
    private String rateType; //利率性质
    @NotBlank(message = "{ContractIssueInfo.signDate.null}")
    @ColumnMapping(columnName = "sign_date", columnType = "Date")
    private String signDate;//签约日期
    @NotBlank(message = "{ContractIssueInfo.ddDate.null}")
    @ColumnMapping(columnName = "dd_date", columnType = "Date")
    private String ddDate;//放款日期
    @NotBlank(message = "{ContractIssueInfo.matureDate.null}")
    @ColumnMapping(columnName = "mature_date", columnType = "Date")
    private String matureDate;//到期日期
    @ColumnMapping(columnName = "ext_start_date", columnType = "Date")
    private String extStartDate ;//展期开始日期
    @ColumnMapping(columnName = "ext_end_date", columnType = "Date")
    private String extEndDate;//展期结束日期
    @ColumnMapping(columnName = "ext_outstanding", columnType = "String")
    private Double extOutstanding;//展期余额
    @NotBlank(message = "{ContractIssueInfo.zone.null}")
    @ColumnMapping(columnName = "zone", columnType = "String")
    private String zone;//投放区域
    @NotBlank(message = "{ContractIssueInfo.guarType.null}")
    @ColumnMapping(columnName = "guar_type", columnType = "String")
    private String guarType;//担保方式
    @NotBlank(message = "{ContractIssueInfo.term.null}")
    @ColumnMapping(columnName = "term", columnType = "String")
    private String term;//贷款期限
    @NotBlank(message = "{ContractIssueInfo.purpose.null}")
    @ColumnMapping(columnName = "purpose", columnType = "String")
    private String purpose;//贷款用途
    @NotBlank(message = "{ContractIssueInfo.loanObject.null}")
    @ColumnMapping(columnName = "loan_object", columnType = "String")
    private String loanObject;//贷款对象
    @NotBlank(message = "{ContractIssueInfo.loanObjectSize.null}")
    @ColumnMapping(columnName = "loan_object_size", columnType = "String")
    private String loanObjectSize;//贷款对象规模
    @NotBlank(message = "{ContractIssueInfo.rateCalcMode.null}")
    @ColumnMapping(columnName = "rate_calc_mode", columnType = "String")
    private String rateCalcMode;//计息方式
    @NotBlank(message = "{ContractIssueInfo.repayMode.null}")
    @ColumnMapping(columnName = "repay_mode", columnType = "String")
    private String repayMode;//还款方式
    @NotBlank(message = "{ContractIssueInfo.industry.null}")
    @ColumnMapping(columnName = "industry", columnType = "String")
    private String industry;//投放行业
    @NotBlank(message = "{ContractIssueInfo.riskLevel.null}")
    @ColumnMapping(columnName = "risk_level", columnType = "String")
    private String riskLevel;//五级分类
    @NotBlank(message = "{ContractIssueInfo.issueStatus.null}")
    @ColumnMapping(columnName = "issue_status", columnType = "String")
    private String issueStatus;//发放状态
    @ColumnMapping(columnName = "remark", columnType = "String")
    private String remark; //备注
    @ColumnMapping(columnName = "fair_amt", columnType = "BigDecimal")
    private Double fairAmt;//公允价值
    @ColumnMapping(columnName = "is_send", columnType = "Integer")
    private Integer isSend; //发送状态,0表示未发送，1表示已发送
    @ColumnMapping(columnName = "send_date", columnType = "Date")
    private Date sendDate;//记录申报日期
    @ColumnMapping(columnName = "insert_date", columnType = "Date")
    private Date insertDate;//记录保存日期
    @ColumnMapping(columnName = "is_last", columnType = "String")
    private String isLast;//是否是最新,Y表示是,N表示否
    @ColumnMapping(columnName = "net_sign_no", columnType = "String")
    private String netSignNo; //网签编号

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

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

    public String getDueBillNo() {
        return dueBillNo;
    }

    public void setDueBillNo(String dueBillNo) {
        this.dueBillNo = dueBillNo;
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

    public Double getDdAmt() {
        return ddAmt;
    }

    public void setDdAmt(Double ddAmt) {
        this.ddAmt = ddAmt;
    }

    public String getLoanCate() {
        return loanCate;
    }

    public void setLoanCate(String loanCate) {
        this.loanCate = loanCate;
    }

    public Double getIntRate() {
        return intRate;
    }

    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Double getPriPltyRate() {
        return priPltyRate;
    }

    public void setPriPltyRate(Double priPltyRate) {
        this.priPltyRate = priPltyRate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getDdDate() {
        return ddDate;
    }

    public void setDdDate(String ddDate) {
        this.ddDate = ddDate;
    }

    public String getMatureDate() {
        return matureDate;
    }

    public void setMatureDate(String matureDate) {
        this.matureDate = matureDate;
    }

    public String getExtStartDate() {
        return extStartDate;
    }

    public void setExtStartDate(String extStartDate) {
        this.extStartDate = extStartDate;
    }

    public String getExtEndDate() {
        return extEndDate;
    }

    public void setExtEndDate(String extEndDate) {
        this.extEndDate = extEndDate;
    }

    public Double getExtOutstanding() {
        return extOutstanding;
    }

    public void setExtOutstanding(Double extOutstanding) {
        this.extOutstanding = extOutstanding;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getGuarType() {
        return guarType;
    }

    public void setGuarType(String guarType) {
        this.guarType = guarType;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLoanObject() {
        return loanObject;
    }

    public void setLoanObject(String loanObject) {
        this.loanObject = loanObject;
    }

    public String getLoanObjectSize() {
        return loanObjectSize;
    }

    public void setLoanObjectSize(String loanObjectSize) {
        this.loanObjectSize = loanObjectSize;
    }

    public String getRateCalcMode() {
        return rateCalcMode;
    }

    public void setRateCalcMode(String rateCalcMode) {
        this.rateCalcMode = rateCalcMode;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    public String getNetSignNo() {
        return netSignNo;
    }

    public void setNetSignNo(String netSignNo) {
        this.netSignNo = netSignNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getFairAmt() {
        return fairAmt;
    }

    public void setFairAmt(Double fairAmt) {
        this.fairAmt = fairAmt;
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
}
