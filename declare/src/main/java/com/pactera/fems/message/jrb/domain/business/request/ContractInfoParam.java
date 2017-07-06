package com.pactera.fems.message.jrb.domain.business.request;

import com.global.framework.util.SysUtils;
import org.global.framework.xmlbeans.bean.SerialBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款合同信息循环节点实体类--自营贷款
 */
public class ContractInfoParam extends SerialBean {
    private String reportType;        //上报类型:增 删 改
    private String orgCode;          //组织机构代码
    private String contractNo;       //合同编号
    private String loanCate;         //贷款类别
    private String contractName;     //合同名称
    private String customerType;      //借款人类别
    private String customerName;      //借款人名称
    private String certificateType;   //借款人证件类型
    private String certificateNo;     //借款人证件号码
    //    private String linkman = "无";           //联系人 选填
    //    private String telephone = "无";       //联系电话 选填
    private String loanObject;        //贷款对象
    private String loanObjectSize;    //贷款对象规模
    private String contractSignDate;  //合同签订日期
    private String contractBeginDate; //合同有效起始日期
    private String contractEndDate;   //合同有效结束日期
    private String contractAmount;    //合同金额
    private String outstanding;       //贷款余额
    private String guarType;          //担保方式
    private String ccy;               //币种
    private String isRealQuotaLoan;   //是否额度项下贷款
    private String realQuotaNo;      //授信额度协议编号
    private String intRate;           //月利率(‰)
    private String priPltyRate;       //逾期月利率(‰)
    private String contractStatus;    //合同状态
    private String relationManager;   //客户经理
    private String disputeScheme;     //争议解决方式
    //    private String remark = "无";            //备注
    private List<CoCustomerInfoParam> coCustomerInfo = new ArrayList<CoCustomerInfoParam>();//共同借款人数组

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

    public String getLoanCate() {
        return loanCate;
    }

    public void setLoanCate(String loanCate) {
        this.loanCate = loanCate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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
//
//    public String getLinkman() {
//        return linkman;
//    }
//
//    public void setLinkman(String linkman) {
//        this.linkman = linkman;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }

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

    public String getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(String contractSignDate) {
        String date = SysUtils.formatDateStrToString(contractSignDate, "yyyyMMdd");
        this.contractSignDate = date;
    }

    public String getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(String contractBeginDate) {
        String date = SysUtils.formatDateStrToString(contractBeginDate, "yyyyMMdd");
        this.contractBeginDate = date;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        String date = SysUtils.formatDateStrToString(contractEndDate, "yyyyMMdd");
        this.contractEndDate = date;

    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getGuarType() {
        return guarType;
    }

    public void setGuarType(String guarType) {
        this.guarType = guarType;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
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

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getRelationManager() {
        return relationManager;
    }

    public void setRelationManager(String relationManager) {
        this.relationManager = relationManager;
    }

    public String getDisputeScheme() {
        return disputeScheme;
    }

    public void setDisputeScheme(String disputeScheme) {
        this.disputeScheme = disputeScheme;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }

    public List<CoCustomerInfoParam> getCoCustomerInfo() {
        return coCustomerInfo;
    }

    public void setCoCustomerInfo(List<CoCustomerInfoParam> coCustomerInfo) {
        this.coCustomerInfo = coCustomerInfo;
    }
}