package com.pactera.fems.message.wg.constants;


public enum JSHDataImportResonEnum {
    CODE_01("01", "应急预案启动后补录"),

    CODE_02("02", "脱机操作"),

    CODE_03("03", "差错业务撤销后补录"),

    CODE_04("04", "其它");

    private String code;
    private String value;

    private JSHDataImportResonEnum(String code, String value) {
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
        for (JSHDataImportResonEnum _enum : JSHDataImportResonEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
