package com.pactera.fems.message.wg.constants;


public enum CustTypeEnum {
    TYPE_01("01", "境内个人"),

    TYPE_02("02", "境外个人");

    private String code;
    private String value;

    private CustTypeEnum(String code, String value) {
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
        for (CustTypeEnum _enum : CustTypeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}

