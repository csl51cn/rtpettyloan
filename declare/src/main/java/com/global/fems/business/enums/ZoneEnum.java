package com.global.fems.business.enums;

/**
 * 投放区域枚举,元素名为各区域名拼音全拼,zone为业务系统中的代码,code为申报系统中的代码
 */
public enum ZoneEnum {
    YUZHONGQU("1783", "500103"), //渝中区
    DADUKOUQU("1802", "500104"),//大渡口区
    JIANGBEIQU("1782", "500105"),//江北区
    SHAPINGBAQU("1784", "500106"),//沙坪坝区
    JIULONGPOQU("1793", "500107"),//九龙坡区
    NANANQU("1786", "500108"),//南岸区
    BEIBEIQU("1785", "500109"),//北碚区
    WANSHENGQU("1799", "500110"),//万盛区
    SHUANGQIAOQU("1820", "500111"),//双桥区
    YUBEIQU("1787", "500112"),//渝北区
    BANANQU("1794", "500113"),//巴南区
    WANZHOUQU("1800", "500101"),//万州区
    FULINGQU("1788", "500102"),//涪陵区
    QIANJIANGQU("1810", "500114"),//黔江区
    CHANGSHOUQU("1789", "500115"),//长寿区
    HECHUANQU("1801", "500382"),//合川区
    YONGCHUANQU("1790", "500383"),//永川区
    JIANGJINQU("1792", "500381"),//江津区
    NANCHUANQU("1795", "500384"),//南川区
    QIJIANGQU("1796", "500222"),//綦江区
    TONGNANXIAN("1797", "500223"),//潼南县
    TONGLIANGXIAN("1791", "500224"),//铜梁县
    DAZUXIAN("1803", "500225"),//大足县
    RONGCHANGXIAN("1798", "500226"),//荣昌县
    BISHANXIAN("1804", "500227"),//璧山县
    DIANJIANGXIAN("1806", "500231"),//垫江县
    WULONGXIAN("1811", "500232"),//武隆县
    FENGDUXIAN("1805", "500230"),//丰都县
    CHENGKOUXIAN("1821", "500229"),//城口县
    LIANGPINGXIAN("1816", "500228"),//梁平县
    KAIXIAN("1808", "500234"),//开县
    WUXIXIAN("1817", "500238"),//巫溪县
    WUSHANXIAN("1809", "500237"),//巫山县
    FENGJIEXIAN("1819", "500236"),//奉节县
    YUNYANGXIAN("1818", "500235"),//云阳县
    ZHONGXIAN("1807", "500233"),//忠县
    SHIZHUXIAN("1812", "500240"),//石柱县
    PENGSHUIXIAN("1815", "500243"),//彭水县
    YOUYANGXIAN("1814", "500242"),//酉阳县
    XIUSHANXIAN("1813", "500241"),//秀山县
    QITA("1822", "999999");//其他


    private String zone;
    private String code;

    private ZoneEnum(String zone, String code) {
        this.zone = zone;
        this.code = code;
    }


    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getCodeByZone(String zone) {
        ZoneEnum[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            ZoneEnum _enum = arr$[i$];
            if (zone.equals(_enum.getZone())) {
                return _enum.getCode();
            }
        }

        return null;
    }
}
