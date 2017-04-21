package com.pactera.fems.message.wg.constants;


public enum JHDataModifyResonEnum {
    CODE_01("01", "金额录入错误"),

    CODE_02("02", "币种录入错误"),

    CODE_03("03", "姓名录入错误"),

    CODE_04("04", "结汇资金属性录入错误"),

    CODE_05("05", "结汇资金形态录入错误"),

    CODE_06("06", "其它");

    private String code;
    private String value;

    private JHDataModifyResonEnum(String code, String value) {
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
        for (JHDataModifyResonEnum _enum : JHDataModifyResonEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}


