package com.pactera.fems.message.jrb.domain.business.request;

import com.global.framework.util.SysUtils;
import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 还款计划信息批量文件上传实体类
 */
public class RepayInfoUploadParam extends SerialBean {
    private String reportType; //上报类型
    private String orgCode;//组织机构代码
    private String contractNo;//合同编号
    private String dueBillNo;//发放编号
    private String repayDate; //回收日期
    private String counter;//还款期数
    private String customerType; //借款人类别
    private String customerName; //借款人姓名
    private String certificateType; //借款人证件类型
    private String certificateNo; //借款人证件号码
    private String gatherMode; //扣款方式
    private String repayPriAmt ; //收回本金
    private String repayIntAmt ; //收回利息
    private String startDate; //起息日期
    private String endDate; //止息日期
    private String receiptType;//回收类型
    private String delayDays ;//逾期天数
    private String delayAmt ;//逾期本金
    private String delayInterest ;//逾期利息
    private String delayFee ;//逾期滞纳金
    private String priPltyRate ;//逾期月利率
//    private String remark;//备注

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

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {

        String date = SysUtils.formatDateStrToString(repayDate, "yyyyMMdd");
        this.repayDate = date;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
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

    public String getGatherMode() {
        return gatherMode;
    }

    public void setGatherMode(String gatherMode) {
        this.gatherMode = gatherMode;
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
        String date = SysUtils.formatDateStrToString(startDate, "yyyyMMdd");
        this.startDate = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        String date = SysUtils.formatDateStrToString(endDate, "yyyyMMdd");
        this.endDate = date;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(String delayDays) {
        this.delayDays = delayDays;
    }

    public String getDelayAmt() {
        return delayAmt;
    }

    public void setDelayAmt(String delayAmt) {
        this.delayAmt = delayAmt;
    }

    public String getDelayInterest() {
        return delayInterest;
    }

    public void setDelayInterest(String delayInterest) {
        this.delayInterest = delayInterest;
    }

    public String getDelayFee() {
        return delayFee;
    }

    public void setDelayFee(String delayFee) {
        this.delayFee = delayFee;
    }

    public String getPriPltyRate() {
        return priPltyRate;
    }

    public void setPriPltyRate(String priPltyRate) {
        this.priPltyRate = priPltyRate;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}
