package com.pactera.fems.message.wg.constants;


public enum JSHCancelResonEnum {
    CODE_01("01", "证件类型录入错误"),

    CODE_02("02", "证件号码录入错误"),

    CODE_03("03", "国别录入错误"),

    CODE_04("04", "业务类型录入错误"),

    CODE_05("05", "结售汇业务未实际发生"),

    CODE_06("06", "其它");

    private String code;
    private String value;

    private JSHCancelResonEnum(String code, String value) {
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
        for (JSHCancelResonEnum _enum : JSHCancelResonEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}