package com.global.fems.business.domain;

import com.global.framework.dbutils.support.Entity;

/**
 * 共借人循环节点
 */
public class CoCustomerCycleNode  extends Entity {

    private String coCustomerType;//共同借款人类别
    private String coCustomerName;//共同借款人名称
    private String coCertificateType;//证件类型
    private String coCertificateNo;//证件号码
    private String coLinkman;//联系人
    private String coTelephone;//联系电话

    public String getCoCustomerType() {
        return coCustomerType;
    }

    public void setCoCustomerType(String coCustomerType) {
        this.coCustomerType = coCustomerType;
    }

    public String getCoCustomerName() {
        return coCustomerName;
    }

    public void setCoCustomerName(String coCustomerName) {
        this.coCustomerName = coCustomerName;
    }

    public String getCoCertificateType() {
        return coCertificateType;
    }

    public void setCoCertificateType(String coCertificateType) {
        this.coCertificateType = coCertificateType;
    }

    public String getCoCertificateNo() {
        return coCertificateNo;
    }

    public void setCoCertificateNo(String coCertificateNo) {
        this.coCertificateNo = coCertificateNo;
    }

    public String getCoLinkman() {
        return coLinkman;
    }

    public void setCoLinkman(String coLinkman) {
        this.coLinkman = coLinkman;
    }

    public String getCoTelephone() {
        return coTelephone;
    }

    public void setCoTelephone(String coTelephone) {
        this.coTelephone = coTelephone;
    }
}
