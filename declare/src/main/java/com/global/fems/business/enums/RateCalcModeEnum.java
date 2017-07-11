package com.global.fems.business.enums;

/**
 * 计息方式枚举,rateCalMode是业务系统中的代码,code是申报系统中的代码
 */
public enum RateCalcModeEnum {
    RateCalcMode_818("818", "270003"),//按月预付息，到期还本---按期还息，到期还本
    RateCalcMode_820("820", "270006"),//等额本息---等额本息
    RateCalcMode_1096("1096", "270005"),//首次付息，到期还本---预收利息，到期还本
    RateCalcMode_1215("1215", "270002"),//等本等息---分期还本付息
    RateCalcMode_1231("1231", "270002"),//按月付息，季度还本息随本减---分期还本付息
    RateCalcMode_1713("1713", "270002"),//按月付息,季度还本---分期还本付息
    RateCalcMode_1835("1835", "270002"),//按月预付息，季度还本---分期还本付息
    RateCalcMode_2059("2059", "270002");//一次性付息，按月还本---分期还本付息

    private String rateCalMode;
    private String code;

    private RateCalcModeEnum(String rateCalMode, String code) {
        this.rateCalMode = rateCalMode;
        this.code = code;
    }

    public String getRateCalMode() {
        return rateCalMode;
    }

    public void setRateCalMode(String rateCalMode) {
        this.rateCalMode = rateCalMode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getCodeByRateCalMode(String rateCalMode) {
        RateCalcModeEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            RateCalcModeEnum _enum = arr$[i$];
            if (rateCalMode.equals(_enum.getRateCalMode())) {
                return _enum.getCode();
            }
        }

        return null;
    }
}
