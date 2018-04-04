package com.global.fems.business.domain;

import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 还款计划信息实体类
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_PAYPLAN_INFO")
public class PayPlanInfo extends Entity {

    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;              //主键
    @ColumnMapping(columnName = "date_id", columnType = "Integer")
    private Integer dateId;        // Data_WorkInfo中的Date_Id
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;        //批次号
    @ColumnMapping(columnName = "data_type", columnType = "String")
    private String dataType;      //数据类型
    @ColumnMapping(columnName = "record_count", columnType = "Integer")
    private String recordCount; //记录数
    @ColumnMapping(columnName = "report_type", columnType = "String")
    private String reportType;   //上报类型:增 删 改
    @ColumnMapping(columnName = "org_code", columnType = "String")
    private String orgCode;      //组织机构代码
    @NotBlank(message = "{PayPlanInfo.contractNo.null}")
    @ColumnMapping(columnName = "contract_no", columnType = "String")
    private String contractNo;    //合同编号
    @NotBlank(message = "{PayPlanInfo.dueBillNo.null}")
    @ColumnMapping(columnName = "due_bill_no", columnType = "String")
    private String dueBillNo; //发放编号
    @NotBlank(message = "{PayPlanInfo.counter.null}")
    @ColumnMapping(columnName = "counter", columnType = "Integer")
    private String counter; //还款期数
    @NotBlank(message = "{PayPlanInfo.repayDate.null}")
    @ColumnMapping(columnName = "repay_date", columnType = "Date")
    private String repayDate; //应还日期
    @NotBlank(message = "{PayPlanInfo.repayPriAmt.null}")
    @ColumnMapping(columnName = "repay_pri_amt", columnType = "BigDecimal")
    private String repayPriAmt; //应还本金
    @NotBlank(message = "{PayPlanInfo.repayIntAmt.null}")
    @ColumnMapping(columnName = "repay_int_amt", columnType = "BigDecimal")
    private String repayIntAmt; //应还利息
    @NotBlank(message = "{PayPlanInfo.startDate.null}")
    @ColumnMapping(columnName = "start_date", columnType = "Date")
    private String startDate; //起息日期
    @NotBlank(message = "{PayPlanInfo.endDate.null}")
    @ColumnMapping(columnName = "end_date", columnType = "Date")
    private String endDate; //止息日期
    @ColumnMapping(columnName = "sign_date", columnType = "Date")
    private String signDate; //签约日期
    @ColumnMapping(columnName = "total_counter", columnType = "Integer")
    private String totalCounter; //总期数
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

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayPriAmt() {
        return repayPriAmt;
    }

    public void setRepayPriAmt(String repayPriAmt) {
        this.repayPriAmt = repayPriAmt;
    }

    public String getRepayIntAmt() {
        return repayIntAmt;
    }

    public void setRepayIntAmt(String repayIntAmt) {
        this.repayIntAmt = repayIntAmt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalCounter() {
        return totalCounter;
    }

    public void setTotalCounter(String totalCounter) {
        this.totalCounter = totalCounter;
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

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
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
