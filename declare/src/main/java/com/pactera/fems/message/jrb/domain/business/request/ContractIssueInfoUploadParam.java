package com.pactera.fems.message.jrb.domain.business.request;

import com.global.framework.util.SysUtils;
import org.global.framework.xmlbeans.bean.SerialBean;

/**
 * 贷款放款信息循环节点实体类
 */
public class ContractIssueInfoUploadParam extends SerialBean {
    private String reportType;        //上报类型:增 删 改
    private String orgCode;          //组织机构代码
    private String contractNo;       //合同编号
    private String dueBillNo;       //发放编号
    private String customerType;      //借款人类别
    private String customerName;      //借款人名称
    private String certificateType;   //借款人证件类型
    private String certificateNo;     //借款人证件号码
    private String ddAmt;             //发放金额
    private String loanCate;         //贷款类别
    private String intRate;           //月利率(‰)
    private String priPltyRate;       //逾期月利率(‰)
    private String rateType;          //利率性质
    private String signDate;          //合同签订日期
    private String ddDate;          //发放日期
    private String matureDate;      //到期日期
//    private String extStartDate;    //展期开始日期
//    private String extEndDate;       //展期到期日期
//    private String extOutstanding;  //展期余额
    private String zone;            //投放区域
    private String guarType;          //担保方式
    private String term;             //贷款期限
    private String purpose;         //贷款用途
    private String loanObject;        //贷款对象
    private String loanObjectSize;    //贷款对象规模
    private String rateCalcMode;   //计息方式
    private String repayMode;     //还款方式
    private String industry;      //投放行业
    private String riskLevel;          //五级分类
    private String issueStatus;   //发放状态
//    private String remark;      //备注
//    private String fairAmt;       //公允价值

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

    public String getDdAmt() {
        return ddAmt;
    }

    public void setDdAmt(String ddAmt) {
        this.ddAmt = ddAmt;
    }

    public String getLoanCate() {
        return loanCate;
    }

    public void setLoanCate(String loanCate) {
        this.loanCate = loanCate;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getPriPltyRate() {
        return priPltyRate;
    }

    public void setPriPltyRate(String priPltyRate) {
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
        String date = SysUtils.formatDateStrToString(signDate, "yyyyMMdd");
        this.signDate = date;
    }

    public String getDdDate() {
        return ddDate;
    }

    public void setDdDate(String ddDate) {
        String date = SysUtils.formatDateStrToString(ddDate, "yyyyMMdd");
        this.ddDate = date;
    }

    public String getMatureDate() {
        return matureDate;
    }

    public void setMatureDate(String matureDate) {
        String date = SysUtils.formatDateStrToString(matureDate, "yyyyMMdd");
        this.matureDate = date;
    }
//
//    public String getExtStartDate() {
//        return extStartDate;
//    }
//
//    public void setExtStartDate(String extStartDate) {
//        String date = SysUtils.formatDateStrToString(extStartDate, "yyyyMMdd");
//        this.extStartDate = date;
//
//    }
//
//    public String getExtEndDate() {
//        return extEndDate;
//    }
//
//    public void setExtEndDate(String extEndDate) {
//        String date = SysUtils.formatDateStrToString(extEndDate, "yyyyMMdd");
//        this.extEndDate = date;
//    }
//
//    public String getExtOutstanding() {
//        return extOutstanding;
//    }
//
//    public void setExtOutstanding(String extOutstanding) {
//        this.extOutstanding = extOutstanding;
//    }

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

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public String getFairAmt() {
//        return fairAmt;
//    }
//
//    public void setFairAmt(String fairAmt) {
//        this.fairAmt = fairAmt;
//    }
}