package com.pactera.fems.message.wg.constants;


public enum JHZJXTCodeEnum {
    CODE_01("01", "外币现钞"),

    CODE_02("02", "汇入资金（包括外汇票据）"),

    CODE_03("03", "账户资金"),

    CODE_04("04", "旅行支票");

    private String code;
    private String value;

    private JHZJXTCodeEnum(String code, String value) {
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
        for (JHZJXTCodeEnum _enum : JHZJXTCodeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
