package com.global.fems.business.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author: Senlin.Deng
 * @Description: 报文类型枚举
 * @date: Created in 2018/9/4 15:13
 * @Modified By:
 */
public enum MessageTypeEnum {
    /**
     * 授信额度信息
     */
    QUOTA_INFO("0101", "QUOTA_INFO"),

    /**
     * 贷款合同信息
     */
    CONTRACT_INFO("0102", "CONTRACT_INFO"),

    /**
     * 贷款发放信息
     */
    ISSUE_INFO("0103", "ISSUE_INFO"),


    /**
     * 贷款回收信息
     */
    REPAY_INFO("0104", "REPAY_INFO"),


    /**
     * 还款计划信息
     */
    PAY_PLAN_INFO("0104", "PAYPLAN_INFO");


    private String code;
    private String desc;


    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String findDescByCode(String code) {
        Optional<MessageTypeEnum> first = Arrays.stream(MessageTypeEnum.values()).filter(messageTypeEnum -> StringUtils.equals(messageTypeEnum.getCode(), code)).findFirst();
        return first.map(MessageTypeEnum::getDesc).orElse(null);
    }

    public static String findCodeByDesc(String desc) {
        Optional<MessageTypeEnum> first = Arrays.stream(MessageTypeEnum.values()).filter(messageTypeEnum -> StringUtils.equals(messageTypeEnum.getDesc(), desc)).findFirst();
        return first.map(MessageTypeEnum::getCode).orElse(null);
    }
}
