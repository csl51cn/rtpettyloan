package com.global.fems.business.enums;

/**
 * socket返回消息状态码
 */
public enum ReturnMsgCodeEnum {
    RETURNCODE_1907("1907", "报文类型和报文代码没有匹配上"),
    RETURNCODE_1708("1708", "远程连接失败"),
    RETURNCODE_1709("1709", "EOD处理中，不允许进行数据上报操作"),
    RETURNCODE_991201("991201", "原文件不存在"),
    RETURNCODE_991205("991205", "报文中有字段不满足报文标准的的要求，校验出错"),
    RETURNCODE_991207("991207", "报文文件中的批次号与数据库有重复"),
    RETURNCODE_000000("000000", "Success/文件传输成功/上报成功"),
    RETURNCODE_000001("1709", "数据处理中/上报失败"),
    RETURNCODE_100000("100000", "查找不到组织机构信息"),
    RETURNCODE_100002("100002", "新增时，数据已存在"),
    RETURNCODE_010101("010101", "报修改时，该额度数据对应的客户数据不存在"),
    RETURNCODE_010102("010102", "报修改时，对应的额度是数据不存在"),
    RETURNCODE_010201("100002", "报修改时，该合同数据数据不存在"),
    RETURNCODE_010203("010203", "授信额度下贷款，对应的剩余额度不足"),
    RETURNCODE_010301("010301", "报修改时，该发放数据数据不存在"),
    RETURNCODE_010302("010302", "报新增时，该数据对应的合同信息未上报"),
    RETURNCODE_010401("010401", "报修改时，该回收数据数据不存在"),
    RETURNCODE_010402("010402", "报新增时，该数据对应的发放信息未上报"),
    RETURNCODE_010501("010501", "报新增时，报修改时，该回收数据数据不存在"),
    RETURNCODE_010502("010502", "报新增时，该数据对应的发放信息未上报"),
    RETURNCODE_010601("010601", "报修改时，该回收数据数据不存在");



    private String code;
    private String value;



    private ReturnMsgCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(String code) {
        ReturnMsgCodeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            ReturnMsgCodeEnum _enum = arr$[i$];
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }

        return null;
    }
}
