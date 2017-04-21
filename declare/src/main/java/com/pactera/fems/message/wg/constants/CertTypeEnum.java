package com.pactera.fems.message.wg.constants;


public enum CertTypeEnum {
    TYPE_01("01", "居民身份证"),

    TYPE_04("04", "外国护照"),

    TYPE_05("05", "外国人持中国永久居留证"),

    TYPE_06("06", "港澳居民来往内地通行证"),

    TYPE_07("07", "台湾居民来往大陆通行证"),

    TYPE_09("09", "中国护照"),

    TYPE_10("10", "外交官证");

    private String code;
    private String value;

    private CertTypeEnum(String code, String value) {
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
        for (CertTypeEnum _enum : CertTypeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}
