package com.pactera.fems.message.jrb.domain.business.request;

import org.global.framework.xmlbeans.bean.SerialBean;

/**
 *共同借款人实体类
 */
public class CoCustomerInfoParam extends SerialBean {

    private String customerType;//共同借款人类别
    private String customerName;//共同借款人名称
    private String certificateType;//证件类型
    private String certificateNo;//证件号码
    private String linkman;//联系人
    private String telephone;//联系电话

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
}
