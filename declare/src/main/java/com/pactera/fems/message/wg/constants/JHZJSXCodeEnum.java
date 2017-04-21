package com.pactera.fems.message.wg.constants;


public enum JHZJSXCodeEnum {
    CODE_110("110", "货物贸易"),

    CODE_121("121", "运输"),

    CODE_122("122", "旅游"),

    CODE_123("123", "金融和保险服务"),

    CODE_124("124", "专有权利使用费和特许费"),

    CODE_125("125", "咨询服务"),

    CODE_126("126", "其他服务"),

    CODE_131("131", "职工报酬和赡家款"),

    CODE_132("132", "投资收益"),

    CODE_133("133", "其他经常转移"),

    CODE_210("210", "资本账户"),

    CODE_221("221", "投资资本金"),

    CODE_222("222", "直接投资撤资"),

    CODE_223("223", "房地产"),

    CODE_22A("22A", "其他直接投资"),

    CODE_231("231", "对境外证券投资撤回"),

    CODE_232("232", "证券筹资"),

    CODE_24A("24A", "其他投资"),

    CODE_250("250", "国内外汇贷款"),

    CODE_270("270", "经批准的资本其他");

    private String code;
    private String value;

    private JHZJSXCodeEnum(String code, String value) {
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
        for (JHZJSXCodeEnum _enum : JHZJSXCodeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}


