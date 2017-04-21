package com.pactera.fems.message.wg.constants;


public enum OperateTypeEnum {
    TYPE_0("0", "正常"),

    TYPE_1("1", "补录"),

    TYPE_2("2", "录入修改"),

    TYPE_3("3", "补录修改"),

    TYPE_4("4", "录入删除"),

    TYPE_5("5", "补录删除");

    private String code;
    private String value;

    private OperateTypeEnum(String code, String value) {
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
        for (OperateTypeEnum _enum : OperateTypeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
