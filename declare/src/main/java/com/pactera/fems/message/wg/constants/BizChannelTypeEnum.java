package com.pactera.fems.message.wg.constants;


public enum BizChannelTypeEnum {
    TYPE_11("11", "柜台渠道（页面模式）"),

    TYPE_12("12", "柜台渠道（接口模式）"),

    TYPE_21("21", "网上银行"),

    TYPE_22("22", "手机银行"),

    TYPE_23("23", "自助终端"),

    TYPE_24("24", "电话银行"),

    TYPE_31("31", "支付机构（页面模式）"),

    TYPE_32("32", "支付机构（接口模式）"),

    TYPE_41("41", "特许兑换机构（页面模式）"),

    TYPE_42("42", "特许兑换机构（接口模式）");

    private String code;
    private String value;

    private BizChannelTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(String code) {
        for (BizChannelTypeEnum _enum : BizChannelTypeEnum.values())
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        return null;
    }
}


