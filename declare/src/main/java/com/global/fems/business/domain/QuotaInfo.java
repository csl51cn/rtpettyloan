package com.global.fems.business.domain;


import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author senlin.deng
 * @date 2018/4/3 13:50
 * 授信额度信息实体类
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_QUOTA_INFO")
public class QuotaInfo extends Entity {

    /**
     * 主键
     */
    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;
    /**
     * Data_WorkInfo中的Date_Id
     */
    @ColumnMapping(columnName = "date_id", columnType = "Integer")
    private Integer dateId;
    /**
     * 批次号
     */
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;
    /**
     * 数据类型
     */
    @ColumnMapping(columnName = "data_type", columnType = "String")
    private String dataType;
    /**
     * 记录数
     */
    @ColumnMapping(columnName = "record_count", columnType = "Integer")
    private String recordCount;
    /**
     * 上报类型:增 删 改
     */
    @ColumnMapping(columnName = "report_type", columnType = "String")
    private String reportType;
    /**
     * 组织机构代码
     */
    @ColumnMapping(columnName = "org_code", columnType = "String")
    private String orgCode;
    /**
     * 额度协议编号
     */
    @NotBlank(message = "{QuotaInfo.contractNo.null}")
    @ColumnMapping(columnName = "contract_no", columnType = "String")
    private String contractNo;

    /**
     * 供查询的合同编号
     */
    @NotBlank(message = "{QuotaInfo.contractNoQuery.null}")
    @ColumnMapping(columnName = "contract_no_query", columnType = "String")
    private String contractNoQuery;

    /**
     * 额度协议名称
     */
    @ColumnMapping(columnName = "contract_name", columnType = "String")
    private String contractName;

    /**
     * 借款人类别
     */
    @NotBlank(message = "{QuotaInfo.customerType.null}")
    @ColumnMapping(columnName = "customer_type", columnType = "String")
    private String customerType;

    /**
     * 借款人姓名
     */
    @NotBlank(message = "{QuotaInfo.customerName.null}")
    @ColumnMapping(columnName = "customer_name", columnType = "String")
    private String customerName;

    /**
     * 借款人证件类型
     */
    @NotBlank(message = "{QuotaInfo.certificateType.null}")
    @ColumnMapping(columnName = "certificate_type", columnType = "String")
    private String certificateType;
    /**
     * 借款人证件号码
     */
    @NotBlank(message = "{QuotaInfo.certificateNo.null}")
    @ColumnMapping(columnName = "certificate_no", columnType = "String")
    private String certificateNo;
    /**
     * 额度协议签订日期
     */
    @NotBlank(message = "{QuotaInfo.contractSignDate.null}")
    @ColumnMapping(columnName = "contract_sign_date", columnType = "Date")
    private String contractSignDate;

    /**
     * 额度协议起期
     */
    @NotBlank(message = "{QuotaInfo.contractBeginDate.null}")
    @ColumnMapping(columnName = "contract_begin_date", columnType = "Date")
    private String contractBeginDate;
    /**
     * 额度协议止期
     */
    @NotBlank(message = "{QuotaInfo.contractEndDate.null}")
    @ColumnMapping(columnName = "contract_end_date", columnType = "Date")
    private String contractEndDate;

    /**
     * 合同金额
     */
    @NotNull(message = "{QuotaInfo.contractAmount.null}")
    @ColumnMapping(columnName = "contract_amount", columnType = "BigDecimal")
    private Double contractAmount;
    /**
     * 币种
     */
    @NotBlank(message = "{QuotaInfo.ccy.null}")
    @ColumnMapping(columnName = "ccy", columnType = "String")
    private String ccy;

    /**
     * 已用额度
     */
    @NotNull(message = "{QuotaInfo.usedAmount.null}")
    @ColumnMapping(columnName = "used_amount", columnType = "BigDecimal")
    private Double usedAmount;
    /**
     * 剩余额度
     */
    @NotNull(message = "{QuotaInfo.remainAmount.null}")
    @ColumnMapping(columnName = "remain_amount", columnType = "BigDecimal")
    private Double remainAmount;
    /**
     * 担保方式
     */
    @NotBlank(message = "{QuotaInfo.guarType.null}")
    @ColumnMapping(columnName = "guar_type", columnType = "String")
    private String guarType;
    /**
     * 是否循环额度
     */
    @NotBlank(message = "{QuotaInfo.isCircle.null}")
    @ColumnMapping(columnName = "is_circle", columnType = "String")
    private String isCircle;

    /**
     * 合同状态
     */
    @NotBlank(message = "{QuotaInfo.contractStatus.null}")
    @ColumnMapping(columnName = "contract_status", columnType = "String")
    private String contractStatus;

    /**
     * 客户经理
     */
    @NotBlank(message = "{QuotaInfo.relationManager.null}")
    @ColumnMapping(columnName = "relation_manager", columnType = "String")
    private String relationManager;
    /**
     * 备注
     */
    @ColumnMapping(columnName = "remark", columnType = "String")
    private String remark;

    /**
     * 发送状态,0表示未发送，1表示已发送
     */
    @ColumnMapping(columnName = "is_send", columnType = "Integer")
    private Integer isSend;
    /**
     * 记录申报日期
     */
    @ColumnMapping(columnName = "send_date", columnType = "Date")
    private Date sendDate;

    /**
     * 记录保存日期
     */
    @ColumnMapping(columnName = "insert_date", columnType = "Date")
    private Date insertDate;
    /**
     * 是否是最新,Y表示是,N表示否
     */
    @ColumnMapping(columnName = "is_last", columnType = "String")
    private String isLast;


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

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public Double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getGuarType() {
        return guarType;
    }

    public void setGuarType(String guarType) {
        this.guarType = guarType;
    }

    public String getIsCircle() {
        return isCircle;
    }

    public void setIsCircle(String isCircle) {
        this.isCircle = isCircle;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getContractNoQuery() {
        return contractNoQuery;
    }

    public void setContractNoQuery(String contractNoQuery) {
        this.contractNoQuery = contractNoQuery;
    }
}
