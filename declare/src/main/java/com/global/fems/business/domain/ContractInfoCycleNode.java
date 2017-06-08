package com.global.fems.business.domain;

import com.global.fems.interfaces.validator.First;
import com.global.fems.interfaces.validator.Second;
import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 贷款合同信息循环节点
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_CONTRACT_INFO")
public class ContractInfoCycleNode extends Entity {

    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;              //主键
    @ColumnMapping(columnName = "date_id", columnType = "Integer")
    private Integer dateId;           // Data_WorkInfo中的Date_Id
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;
    @ColumnMapping(columnName = "data_type", columnType = "String")
    private String dataType;
    @ColumnMapping(columnName = "record_count", columnType = "String")
    private String recordCount ;
    @ColumnMapping(columnName = "report_type", columnType = "String")
    private String reportType;        //上报类型:增 删 改
    @ColumnMapping(columnName = "org_code", columnType = "String")
    private String orgCode;          //组织机构代码
    @NotBlank(message = "{ContractInfoCycleNode.contractNo.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_no", columnType = "String")
    private String contractNo;       //合同编号
    @NotBlank(message = "{ContractInfoCycleNode.loanCate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "loan_cate", columnType = "String")
    private String loanCate;         //合同类别
    @ColumnMapping(columnName = "contract_name", columnType = "String")
    private String contractName;     //合同名称
    @NotBlank(message = "{ContractInfoCycleNode.customerType.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "customer_type", columnType = "String")
    private String customerType;      //借款人类别
    @NotBlank(message = "{ContractInfoCycleNode.customerName.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "customer_name", columnType = "String")
    private String customerName;      //借款人名称
    @NotBlank(message = "{ContractInfoCycleNode.certificateType.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "certificate_type", columnType = "String")
    private String certificateType;   //借款人证件类型
    @NotBlank(message = "{ContractInfoCycleNode.certificateNo.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "certificate_no", columnType = "String")
    private String certificateNo;     //借款人证件号码
    @ColumnMapping(columnName = "linkman", columnType = "String")
    private String linkman;           //联系人
    @ColumnMapping(columnName = "telephone", columnType = "String")
    private String telephone;         //联系电话
    @NotBlank(message = "{ContractInfoCycleNode.loanObject.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "loan_object", columnType = "String")
    private String loanObject;        //贷款对象
    @NotBlank(message = "{ContractInfoCycleNode.loanObjectSize.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "loan_object_size", columnType = "String")
    private String loanObjectSize;    //贷款对象规模
    @NotBlank(message = "{ContractInfoCycleNode.contractSignDate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_sign_date", columnType = "Timestamp")
    private String contractSignDate;  //合同签订日期
    @NotBlank(message = "{ContractInfoCycleNode.contractBeginDate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_begin_date", columnType = "Timestamp")
    private String contractBeginDate; //合同有效起始日期
    @NotBlank(message = "{ContractInfoCycleNode.contractEndDate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_end_date", columnType = "Timestamp")
    private String contractEndDate;   //合同有效结束日期
    @NotNull(message = "{ContractInfoCycleNode.contractAmount.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_amount", columnType = "BigDecimal")
    private Double contractAmount;    //合同金额
    @NotNull(message = "{ContractInfoCycleNode.outstanding.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "outstanding", columnType = "BigDecimal")
    private Double outstanding;       //贷款余额
    @NotBlank(message = "{ContractInfoCycleNode.guarType.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "guar_type", columnType = "String")
    private String guarType;          //担保方式
    @NotBlank(message = "{ContractInfoCycleNode.ccy.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "ccy", columnType = "String")
    private String ccy;               //币种
    @NotBlank(message = "{ContractInfoCycleNode.isRealQuotaLoan.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "is_real_quota_loan", columnType = "String")
    private String isRealQuotaLoan;   //是否额度项下贷款
    @ColumnMapping(columnName = "real_quota_no", columnType = "String")
    private String realQuotaNo;       //授信额度协议编号
    @NotNull(message = "{ContractInfoCycleNode.intRate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "int_rate", columnType = "BigDecimal")
    private Double intRate;           //月利率(‰)
    @NotNull(message = "{ContractInfoCycleNode.priPltyRate.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "pri_plty_rate", columnType = "BigDecimal")
    private Double priPltyRate;       //逾期月利率(‰)
    @NotBlank(message = "{ContractInfoCycleNode.contractStatus.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "contract_status", columnType = "String")
    private String contractStatus;    //合同状态
    @NotBlank(message = "{ContractInfoCycleNode.relationManager.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "relation_manager", columnType = "String")
    private String relationManager;   //客户经理
    @NotBlank(message = "{ContractInfoCycleNode.disputeScheme.null}", groups = {First.class, Second.class})
    @ColumnMapping(columnName = "dispute_scheme", columnType = "String")
    private String disputeScheme;     //争议解决方式
    @ColumnMapping(columnName = "remark", columnType = "String")
    private String remark;            //备注
    @NotBlank(message = "{ContractInfoCycleNode.conCustomerType.null}", groups = {Second.class})
    @ColumnMapping(columnName = "con_customer_type", columnType = "String")
    private String conCustomerType;   //委托人类别
    @NotBlank(message = "{ContractInfoCycleNode.conCustomerName.null}", groups = {Second.class})
    @ColumnMapping(columnName = "con_customer_name", columnType = "String")
    private String conCustomerName;   //委托人
    @NotBlank(message = "{ContractInfoCycleNode.conCertificateType.null}", groups = {Second.class})
    @ColumnMapping(columnName = "con_certificate_type", columnType = "String")
    private String conCertificateType;//委托人证件类型
    @NotBlank(message = "{ContractInfoCycleNode.conCertificateNo.null}", groups = {Second.class})
    @ColumnMapping(columnName = "con_certificate_no", columnType = "String")
    private String conCertificateNo;  //委托人证件号码
    @ColumnMapping(columnName = "con_jurisitc", columnType = "String")
    private String conJurisitc;       //委托方法定代表人/负责人
    @ColumnMapping(columnName = "con_telephone", columnType = "String")
    private String conTelephone;      //委托人联系电话
    @ColumnMapping(columnName = "con_locus", columnType = "String")
    private String conLocus;          //委托人地址
    @ColumnMapping(columnName = "con_postalcode", columnType = "String")
    private String conPostalCode;     //委托人邮编
    @ColumnMapping(columnName = "con_fincal_org", columnType = "String")
    private String conFincalOrg;      //委托人开户金融机构
    @ColumnMapping(columnName = "con_account_no", columnType = "String")
    private String conAccountNo;      //委托人账户
    @NotBlank(message = "{ContractInfoCycleNode.assCustomerName.null}", groups = {Second.class})
    @ColumnMapping(columnName = "ass_customer_name", columnType = "String")
    private String assCustomerName;   //受托人
    @ColumnMapping(columnName = "ass_juristic", columnType = "String")
    private String assJuristic;       //受托人法定代表人/负责人
    @ColumnMapping(columnName = "ass_telephone", columnType = "String")
    private String assTelephone;      //受托人联系电话
    @ColumnMapping(columnName = "ass_locus", columnType = "String")
    private String assLocus;          //受托人地址
    @ColumnMapping(columnName = "ass_postalcode", columnType = "String")
    private String assPostalCode;     //受托人邮编
    @ColumnMapping(columnName = "con_fee", columnType = "BigDecimal")
    private Double conFee;            //代理费
    @ColumnMapping(columnName = "co_customer_type1", columnType = "String")
    private String coCustomerType1;//共同借款人1类别
    @ColumnMapping(columnName = "co_customer_name1", columnType = "String")
    private String coCustomerName1;//共同借款人1名称
    @ColumnMapping(columnName = "co_certificate_type1", columnType = "String")
    private String coCertificateType1;//证件类型
    @ColumnMapping(columnName = "co_certificate_no1", columnType = "String")
    private String coCertificateNo1;//证件号码
    @ColumnMapping(columnName = "co_linkman1", columnType = "String")
    private String coLinkman1;//联系人
    @ColumnMapping(columnName = "co_telephone1", columnType = "String")
    private String coTelephone1;//联系电话
    @ColumnMapping(columnName = "co_customer_type2", columnType = "String")
    private String coCustomerType2;//共同借款人2类别
    @ColumnMapping(columnName = "co_customer_name2", columnType = "String")
    private String coCustomerName2;//共同借款人2名称
    @ColumnMapping(columnName = "co_certificate_type2", columnType = "String")
    private String coCertificateType2;//证件类型
    @ColumnMapping(columnName = "co_certificate_no2", columnType = "String")
    private String coCertificateNo2;//证件号码
    @ColumnMapping(columnName = "co_linkman2", columnType = "String")
    private String coLinkman2;//联系人
    @ColumnMapping(columnName = "co_telephone2", columnType = "String")
    private String coTelephone2;//联系电话

    @ColumnMapping(columnName = "co_customer_type3", columnType = "String")
    private String coCustomerType3;//共同借款人3类别
    @ColumnMapping(columnName = "co_customer_name3", columnType = "String")
    private String coCustomerName3;//共同借款人3名称
    @ColumnMapping(columnName = "co_certificate_type3", columnType = "String")
    private String coCertificateType3;//证件类型
    @ColumnMapping(columnName = "co_certificate_no3", columnType = "String")
    private String coCertificateNo3;//证件号码
    @ColumnMapping(columnName = "co_linkman3", columnType = "String")
    private String coLinkman3;//联系人
    @ColumnMapping(columnName = "co_telephone3", columnType = "String")
    private String coTelephone3;//联系电话

    @ColumnMapping(columnName = "co_customer_type4", columnType = "String")
    private String coCustomerType4;//共同借款人4类别
    @ColumnMapping(columnName = "co_customer_name4", columnType = "String")
    private String coCustomerName4;//共同借款人4名称
    @ColumnMapping(columnName = "co_certificate_type4", columnType = "String")
    private String coCertificateType4;//证件类型
    @ColumnMapping(columnName = "co_certificate_no4", columnType = "String")
    private String coCertificateNo4;//证件号码
    @ColumnMapping(columnName = "co_linkman4", columnType = "String")
    private String coLinkman4;//联系人
    @ColumnMapping(columnName = "co_telephone4", columnType = "String")
    private String coTelephone4;//联系电话


    @ColumnMapping(columnName = "is_send", columnType = "String")
    private Integer isSend; //发送状态,0表示未发送，1表示已发送
    @ColumnMapping(columnName = "send_date", columnType = "String")
    private Date sendDate;//记录申报日期
    @ColumnMapping(columnName = "insert_date", columnType = "String")
    private Date insertDate;//记录保存日期
    @ColumnMapping(columnName = "is_last", columnType = "String")
    private String isLast;//是否是最新,Y表示是,N表示否

    @ColumnMapping(columnName = "net_sign_no", columnType = "String")
    private String netSignNo;

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

    public String getCoCustomerType1() {
        return coCustomerType1;
    }

    public void setCoCustomerType1(String coCustomerType1) {
        this.coCustomerType1 = coCustomerType1;
    }

    public String getCoCustomerName1() {
        return coCustomerName1;
    }

    public void setCoCustomerName1(String coCustomerName1) {
        this.coCustomerName1 = coCustomerName1;
    }

    public String getCoCertificateType1() {
        return coCertificateType1;
    }

    public void setCoCertificateType1(String coCertificateType1) {
        this.coCertificateType1 = coCertificateType1;
    }

    public String getCoCertificateNo1() {
        return coCertificateNo1;
    }

    public void setCoCertificateNo1(String coCertificateNo1) {
        this.coCertificateNo1 = coCertificateNo1;
    }

    public String getCoLinkman1() {
        return coLinkman1;
    }

    public void setCoLinkman1(String coLinkman1) {
        this.coLinkman1 = coLinkman1;
    }

    public String getCoTelephone1() {
        return coTelephone1;
    }

    public void setCoTelephone1(String coTelephone1) {
        this.coTelephone1 = coTelephone1;
    }

    public String getCoCustomerType2() {
        return coCustomerType2;
    }

    public void setCoCustomerType2(String coCustomerType2) {
        this.coCustomerType2 = coCustomerType2;
    }

    public String getCoCustomerName2() {
        return coCustomerName2;
    }

    public void setCoCustomerName2(String coCustomerName2) {
        this.coCustomerName2 = coCustomerName2;
    }

    public String getCoCertificateType2() {
        return coCertificateType2;
    }

    public void setCoCertificateType2(String coCertificateType2) {
        this.coCertificateType2 = coCertificateType2;
    }

    public String getCoCertificateNo2() {
        return coCertificateNo2;
    }

    public void setCoCertificateNo2(String coCertificateNo2) {
        this.coCertificateNo2 = coCertificateNo2;
    }

    public String getCoLinkman2() {
        return coLinkman2;
    }

    public void setCoLinkman2(String coLinkman2) {
        this.coLinkman2 = coLinkman2;
    }

    public String getCoTelephone2() {
        return coTelephone2;
    }

    public void setCoTelephone2(String coTelephone2) {
        this.coTelephone2 = coTelephone2;
    }

    public String getCoCustomerType3() {
        return coCustomerType3;
    }

    public void setCoCustomerType3(String coCustomerType3) {
        this.coCustomerType3 = coCustomerType3;
    }

    public String getCoCustomerName3() {
        return coCustomerName3;
    }

    public void setCoCustomerName3(String coCustomerName3) {
        this.coCustomerName3 = coCustomerName3;
    }

    public String getCoCertificateType3() {
        return coCertificateType3;
    }

    public void setCoCertificateType3(String coCertificateType3) {
        this.coCertificateType3 = coCertificateType3;
    }

    public String getCoCertificateNo3() {
        return coCertificateNo3;
    }

    public void setCoCertificateNo3(String coCertificateNo3) {
        this.coCertificateNo3 = coCertificateNo3;
    }

    public String getCoLinkman3() {
        return coLinkman3;
    }

    public void setCoLinkman3(String coLinkman3) {
        this.coLinkman3 = coLinkman3;
    }

    public String getCoTelephone3() {
        return coTelephone3;
    }

    public void setCoTelephone3(String coTelephone3) {
        this.coTelephone3 = coTelephone3;
    }

    public String getCoCustomerType4() {
        return coCustomerType4;
    }

    public void setCoCustomerType4(String coCustomerType4) {
        this.coCustomerType4 = coCustomerType4;
    }

    public String getCoCustomerName4() {
        return coCustomerName4;
    }

    public void setCoCustomerName4(String coCustomerName4) {
        this.coCustomerName4 = coCustomerName4;
    }

    public String getCoCertificateType4() {
        return coCertificateType4;
    }

    public void setCoCertificateType4(String coCertificateType4) {
        this.coCertificateType4 = coCertificateType4;
    }

    public String getCoCertificateNo4() {
        return coCertificateNo4;
    }

    public void setCoCertificateNo4(String coCertificateNo4) {
        this.coCertificateNo4 = coCertificateNo4;
    }

    public String getCoLinkman4() {
        return coLinkman4;
    }

    public void setCoLinkman4(String coLinkman4) {
        this.coLinkman4 = coLinkman4;
    }

    public String getCoTelephone4() {
        return coTelephone4;
    }

    public void setCoTelephone4(String coTelephone4) {
        this.coTelephone4 = coTelephone4;
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
}
