package com.global.fems.business.enums;

/**
 * 交易名称与交易码和报文类型对应关系
 */
public enum DataTypeEnum {
    /**
     * 交易码枚举
     */
    QUOTA_INFO("QUOTA_INFO", "PTLN101-0101", "0101"),
    CONTRACT_INFO("CONTRACT_INFO", "PTLN102-0102", "0102"),
    ISSUE_INFO("ISSUE_INFO", "PTLN103-0103", "0103"),
    REPAY_INFO("REPAY_INFO", "PTLN104-0104", "0104"),
    PAYPLAN_INFO("PAYPLAN_INFO", "PTLN105-0105", "0105"),
    ABNORMAL_INFO("ABNORMAL_INFO", "PTLN106-0106", "0106"),
    NETBOOK_INFO("NETBOOK_INFO", "PTLN107-0107", "0107");

    private String type;
    private String value;
    private String simpleValue;

    private DataTypeEnum(String type, String value, String simpleValue) {
        this.type = type;
        this.value = value;
        this.simpleValue = simpleValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSimpleValue() {
        return simpleValue;
    }

    public void setSimpleValue(String simpleValue) {
        this.simpleValue = simpleValue;
    }

    public static String getValueByType(String type) {
        DataTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            DataTypeEnum _enum = arr$[i$];
            if (type.equals(_enum.getType())) {
                return _enum.getValue();
            }
        }
        return null;
    }

    public static String getTypeByValue(String value) {
        DataTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            DataTypeEnum _enum = arr$[i$];
            if (value.equals(_enum.getValue())) {
                return _enum.getType();
            }
        }
        return null;
    }

    public static String getSimpleValueByType(String type) {
        for (DataTypeEnum dataTypeEnum : DataTypeEnum.values()) {
            if (dataTypeEnum.getType().equals(type)) {
                return dataTypeEnum.getSimpleValue();
            }
        }
        return null;
    }
}
