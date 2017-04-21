package com.pactera.fems.message.wg.constants;


public enum PubResonEnum {
    CODE_01("01", "分拆收结汇境外汇款人"),

    CODE_02("02", "分拆收结汇参与者"),

    CODE_03("03", "分拆收结汇资金归集者"),

    CODE_04("04", "分拆购付汇资金提供者"),

    CODE_05("05", "分拆购付汇参与者"),

    CODE_06("06", "分拆购付汇境外收款人"),

    CODE_07("07", "多次协助他人规避额度及真实性管理"),

    CODE_99("99", "其他");

    private String code;
    private String value;

    private PubResonEnum(String code, String value) {
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
        for (PubResonEnum _enum : PubResonEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
