package com.global.fems.business.enums;

/**
 * 投放行业枚举,industry是业务系统中的代码,code是申报系统中的代码
 */
public enum IndustryEnum {
    /**
     * 农林牧渔---农业
     */
    INDUSTRY_437("437", "290001"),
    /**
     * 采矿业---工业
     */
    INDUSTRY_438("438", "290002"),

    /**
     * 制造业---工业
     */
    INDUSTRY_439("439", "290002"),

    /**
     * 电力\燃气\水的生产供应---工业
     */
    INDUSTRY_440("440", "290002"),

    /**
     * 建筑---工业
     */
    INDUSTRY_441("441", "290003"),

    /**
     * 交通运输\仓储\邮政---仓储业
     */
    INDUSTRY_443("443", "290007"),

    /**
     * 信息传输\软件\信息技术服务---其他
     */
    INDUSTRY_444("444", "290009"),

    /**
     * 批发业---商贸流通业
     */
    INDUSTRY_445("445", "290005"),

    /**
     * 住宿---住宿和餐饮业
     */
    INDUSTRY_446("446", "290008"),

    /**
     * 金融---其他
     */
    INDUSTRY_447("447", "290009"),

    /**
     * 房地产---房地产业
     */
    INDUSTRY_448("448", "290004"),

    /**
     * 租赁\商务服务---其他
     */
    INDUSTRY_449("449", "290009"),

    /**
     * 科学研究和技术服务---其他
     */
    INDUSTRY_450("450", "290009"),

    /**
     * 水利\环境\公共设施---其他
     */
    INDUSTRY_451("451", "290009"),

    /**
     * 居民\修理\其他服务---其他
     */
    INDUSTRY_452("452", "290009"),

    /**
     * 教育---其他
     */
    INDUSTRY_453("453", "290009"),

    /**
     * 卫生\社会保障\社会福利---其他
     */
    INDUSTRY_454("454", "290009"),

    /**
     * 文化\体育\娱乐---其他
     */
    INDUSTRY_455("455", "290009"),

    /**
     * 公共管理\社会组织---其他
     */
    INDUSTRY_456("456", "290009"),

    /**
     * 国际组织---其他
     */
    INDUSTRY_457("457", "290009"),

    /**
     * 未知---其他
     */
    INDUSTRY_458("458", "290009"),

    /**
     * 建筑安装---工业
     */
    INDUSTRY_459("2122", "290003"),

    /**
     * 建筑装饰装修---工业
     */
    INDUSTRY_460("2123", "290003"),

    /**
     * 建筑劳务---工业
     */
    INDUSTRY_461("2124", "290003"),

    /**
     * 零售---工业
     */
    INDUSTRY_462("2125", "290005"),

    /**
     * 零售---住宿和餐饮业
     */
    INDUSTRY_463("2126", "290008");
    private String industry;
    private String code;

    private IndustryEnum(String industry, String code) {
        this.industry = industry;
        this.code = code;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getCodeByIndustry(String industry) {
        IndustryEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            IndustryEnum _enum = arr$[i$];
            if (industry.equals(_enum.getIndustry())) {
                return _enum.getCode();
            }
        }

        return null;
    }
}
