package com.pactera.fems.message.jrb.domain.business.request;

import org.global.framework.xmlbeans.bean.SerialBean;

import java.util.List;

/**
 * 贷款合同信息循环节点实体类--委托贷款
 */
public class EntrustedContractInfoParam extends SerialBean {
    private String reportType;        //上报类型:增 删 改
    private String orgCode;          //组织机构代码
    private String contractNo;       //合同编号
    private String loanCate;         //合同类别
    private String contractName;     //合同名称
    private String customerType;      //借款人类别
    private String customerName;      //借款人名称
    private String certificateType;   //借款人证件类型
    private String certificateNo;     //借款人证件号码
    private String linkman;           //联系人
    private String telephone;         //联系电话
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
    private String realQuotaNo;       //授信额度协议编号
    private String intRate;           //月利率(‰)
    private String priPltyRate;       //逾期月利率(‰)
    private String contractStatus;    //合同状态
    private String relationManager;   //客户经理
    private String disputeScheme;     //争议解决方式
    private String remark;            //备注
    private String conCustomerType;   //委托人类别
    private String conCustomerName;   //委托人
    private String conCertificateType;//委托人证件类型
    private String conCertificateNo;  //委托人证件号码
    private String conJurisitc;       //委托方法定代表人/负责人
    private String conTelephone;      //委托人联系电话
    private String conLocus;          //委托人地址
    private String conPostalCode;     //委托人邮编
    private String conFincalOrg;      //委托人开户金融机构
    private String conAccountNo;      //委托人账户
    private String assCustomerName;   //受托人
    private String assJuristic;       //受托人法定代表人/负责人
    private String assTelephone;      //受托人联系电话
    private String assLocus;          //受托人地址
    private String assPostalCode;     //受托人邮编
    private String conFee;            //代理费
    private List<CoCustomerInfoParam> coCustomerInfo;//共同借款人数组

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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(String contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public String getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(String contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getConJurisitc() {
        return conJurisitc;
    }

    public void setConJurisitc(String conJurisitc) {
        this.conJurisitc = conJurisitc;
    }

    public String getConTelephone() {
        return conTelephone;
    }

    public void setConTelephone(String conTelephone) {
        this.conTelephone = conTelephone;
    }

    public String getConLocus() {
        return conLocus;
    }

    public void setConLocus(String conLocus) {
        this.conLocus = conLocus;
    }

    public String getConPostalCode() {
        return conPostalCode;
    }

    public void setConPostalCode(String conPostalCode) {
        this.conPostalCode = conPostalCode;
    }

    public String getConFincalOrg() {
        return conFincalOrg;
    }

    public void setConFincalOrg(String conFincalOrg) {
        this.conFincalOrg = conFincalOrg;
    }

    public String getConAccountNo() {
        return conAccountNo;
    }

    public void setConAccountNo(String conAccountNo) {
        this.conAccountNo = conAccountNo;
    }

    public String getAssCustomerName() {
        return assCustomerName;
    }

    public void setAssCustomerName(String assCustomerName) {
        this.assCustomerName = assCustomerName;
    }

    public String getAssJuristic() {
        return assJuristic;
    }

    public void setAssJuristic(String assJuristic) {
        this.assJuristic = assJuristic;
    }

    public String getAssTelephone() {
        return assTelephone;
    }

    public void setAssTelephone(String assTelephone) {
        this.assTelephone = assTelephone;
    }

    public String getAssLocus() {
        return assLocus;
    }

    public void setAssLocus(String assLocus) {
        this.assLocus = assLocus;
    }

    public String getAssPostalCode() {
        return assPostalCode;
    }

    public void setAssPostalCode(String assPostalCode) {
        this.assPostalCode = assPostalCode;
    }

    public String getConFee() {
        return conFee;
    }

    public void setConFee(String conFee) {
        this.conFee = conFee;
    }

    public List<CoCustomerInfoParam> getCoCustomerInfo() {
        return coCustomerInfo;
    }

    public void setCoCustomerInfo(List<CoCustomerInfoParam> coCustomerInfo) {
        this.coCustomerInfo = coCustomerInfo;
    }
}