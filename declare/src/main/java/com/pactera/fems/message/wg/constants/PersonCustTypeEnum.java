package com.pactera.fems.message.wg.constants;


public enum PersonCustTypeEnum {
    TYPE_01("01", "正常"),

    TYPE_02("02", "预分类"),

    TYPE_03("03", "关注名单");

    private String code;
    private String value;

    private PersonCustTypeEnum(String code, String value) {
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
        for (PersonCustTypeEnum _enum : PersonCustTypeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
