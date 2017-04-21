package com.pactera.fems.message.wg.constants;


public enum BizTypeEnum {
    TYPE_01("01", "占用额度的结汇/购汇 "),

    TYPE_02("02", "个人贸易结汇/购汇"),

    TYPE_03("03", "提供凭证的经常项目其他结汇/购汇 "),

    TYPE_04("04", "资本项目结汇/购汇"),

    TYPE_05("05", "通过支付机构的结汇/购汇 ");

    private String code;
    private String value;

    private BizTypeEnum(String code, String value) {
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
        for (BizTypeEnum _enum : BizTypeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
