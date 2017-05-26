package com.global.fems.business.domain;

import com.global.framework.dbutils.support.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 贷款合同信息循环节点
 */
public class ContractInfoCycleNode extends Entity {
    private String id;              //主键
    private Integer dateId;           // Data_WorkInfo中的Date_Id
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractSignDate;  //合同签订日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractBeginDate; //合同有效起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEndDate;   //合同有效结束日期
    private Double contractAmount;    //合同金额
    private Double outstanding;       //贷款余额
    private String guarType;          //担保方式
    private String ccy;               //币种
    private String isRealQuotaLoan;   //是否额度项下贷款
    private String realQuotaNo;       //授信额度协议编号
    private Double intRate;           //月利率(‰)
    private Double priPltyRate;       //逾期月利率(‰)
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
    private Double conFee;            //代理费
    private List<CoCustomerCycleNode> coCustomerInfo;//共同借款人数组
    private String isSend; //发送状态,0表示未发送，1表示已发送
    private Date sendDate;//记录申报日期
    private Date insertDate;//记录保存日期
    private String isLast;//是否是最新,Y表示是,N表示否

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

    public Date getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(Date contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public Date getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(Date contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
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

    public Double getConFee() {
        return conFee;
    }

    public void setConFee(Double conFee) {
        this.conFee = conFee;
    }

    public List<CoCustomerCycleNode> getCoCustomerInfo() {
        return coCustomerInfo;
    }

    public void setCoCustomerInfo(List<CoCustomerCycleNode> coCustomerInfo) {
        this.coCustomerInfo = coCustomerInfo;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
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
}
