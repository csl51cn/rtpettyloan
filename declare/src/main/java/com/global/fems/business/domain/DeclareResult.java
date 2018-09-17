package com.global.fems.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.global.framework.dbutils.annotation.ColumnMapping;
import com.global.framework.dbutils.annotation.TableMapping;
import com.global.framework.dbutils.support.Entity;

import java.util.Date;

/**
 * @author: Senlin.Deng
 * @Description: 批量文件申报结果
 * @date: Created in 2018/9/3 16:02
 * @Modified By:
 */
@TableMapping(primaryKey = "id", primaryKeyType = "Single", tableName = "DC_DECLARE_RESULT")
public class DeclareResult extends Entity {
    /**
     * 主键
     */
    @ColumnMapping(columnName = "id", columnType = "String")
    private String id;

    /**
     * 批次号
     */
    @ColumnMapping(columnName = "batch_no", columnType = "String")
    private String batchNo;

    /**
     * 记录数
     */
    @ColumnMapping(columnName = "record_count", columnType = "Integer")
    private String recordCount;
    /**
     * 数据类型
     */
    @ColumnMapping(columnName = "data_type", columnType = "String")
    private String dataType;


    /**
     * 上报结果码
     */
    @ColumnMapping(columnName = "declare_result_code", columnType = "String")
    private String declareResultCode;

    /**
     * 上报结果
     */
    @ColumnMapping(columnName = "declare_result", columnType = "String")
    private String declareResult;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ColumnMapping(columnName = "gmt_create",columnType = "Date")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ColumnMapping(columnName = "gmt_modified",columnType = "Date")
    private Date gmtModified;


    /**
     * 创建人id
     */
    @ColumnMapping(columnName = "create_id",columnType = "String")
    private String createId;

    /**
     * 最后一个修改人id
     */
    @ColumnMapping(columnName = "modified_id",columnType = "String")
    private String modifiedId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDeclareResultCode() {
        return declareResultCode;
    }

    public void setDeclareResultCode(String declareResultCode) {
        this.declareResultCode = declareResultCode;
    }

    public String getDeclareResult() {
        return declareResult;
    }

    public void setDeclareResult(String declareResult) {
        this.declareResult = declareResult;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(String modifiedId) {
        this.modifiedId = modifiedId;
    }
}