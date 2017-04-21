package com.pactera.fems.message.wg.constants;


public enum GHZJSXCodeEnum {
    CODE_310("310", "货物贸易"),

    CODE_321("321", "运输"),

    CODE_3221("3221", "自费出境学习"),

    CODE_3222("3222", "因私旅游"),

    CODE_3223("3223", "公务及商务出国"),

    CODE_3224("3224", "境外个人原币兑回"),

    CODE_3225("3225", "旅游项下其他"),

    CODE_323("323", "金融和保险服务"),

    CODE_324("324", "专有权利使用费和特许费"),

    CODE_325("325", "咨询服务"),

    CODE_326("326", "其他服务"),

    CODE_331("331", "职工报酬和赡家款"),

    CODE_332("332", "投资收益"),

    CODE_333("333", "其他经常转移"),

    CODE_410("410", "资本账户"),

    CODE_421("421", "投资资本金"),

    CODE_422("422", "直接投资撤资"),

    CODE_423("423", "房地产"),

    CODE_42A("42A", "其他直接投资"),

    CODE_431("431", "对境外证券投资"),

    CODE_432("432", "证券投资撤出"),

    CODE_44A("44A", "其他投资"),

    CODE_450("450", "国内外汇贷款"),

    CODE_470("470", "经批准的资本其他");

    private String code;
    private String value;

    private GHZJSXCodeEnum(String code, String value) {
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
        for (GHZJSXCodeEnum _enum : GHZJSXCodeEnum.values()) {
            if (code.equals(_enum.getCode())) {
                return _enum.getValue();
            }
        }
        return null;
    }
}


