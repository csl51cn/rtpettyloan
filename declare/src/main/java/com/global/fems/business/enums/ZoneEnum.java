package com.global.fems.business.enums;

/**
 * 投放区域枚举,元素名为各区域名拼音全拼,zone为业务系统中的代码,code为申报系统中的代码
 */
public enum ZoneEnum {
    YUZHONGQU("1783", "230001"), //渝中区
    DADUKOUQU("1802", "230002"),//大渡口区
    JIANGBEIQU("1782", "230003"),//江北区
    SHAPINGBAQU("1784", "230004"),//沙坪坝区
    JIULONGPOQU("1793", "230005"),//九龙坡区
    NANANQU("1786", "230006"),//南岸区
    BEIBEIQU("1785", "230007"),//北碚区
    WANSHENGQU("1799", "230008"),//万盛区
    SHUANGQIAOQU("1820", "230009"),//双桥区
    YUBEIQU("1787", "230010"),//渝北区
    BANANQU("1794", "230011"),//巴南区
    WANZHOUQU("1800", "230012"),//万州区
    FULINGQU("1788", "230013"),//涪陵区
    QIANJIANGQU("1810", "230014"),//黔江区
    CHANGSHOUQU("1789", "230015"),//长寿区
    HECHUANQU("1801", "230016"),//合川区
    YONGCHUANQU("1790", "230017"),//永川区
    JIANGJINQU("1792", "230018"),//江津区
    NANCHUANQU("1795", "230019"),//南川区
    QIJIANGQU("1796", "230020"),//綦江区
    TONGNANXIAN("1797", "230021"),//潼南县
    TONGLIANGXIAN("1791", "230022"),//铜梁县
    DAZUXIAN("1803", "230023"),//大足县
    RONGCHANGXIAN("1798", "230024"),//荣昌县
    BISHANXIAN("1804", "230025"),//璧山县
    DIANJIANGXIAN("1806", "230026"),//垫江县
    WULONGXIAN("1811", "230027"),//武隆县
    FENGDUXIAN("1805", "230028"),//丰都县
    CHENGKOUXIAN("1821", "230029"),//城口县
    LIANGPINGXIAN("1816", "230030"),//梁平县
    KAIXIAN("1808", "230031"),//开县
    WUXIXIAN("1817", "230032"),//巫溪县
    WUSHANXIAN("1809", "230033"),//巫山县
    FENGJIEXIAN("1819", "230034"),//奉节县
    YUNYANGXIAN("1818", "230035"),//云阳县
    ZHONGXIAN("1807", "230036"),//忠县
    SHIZHUXIAN("1812", "230037"),//石柱县
    PENGSHUIXIAN("1815", "230038"),//彭水县
    YOUYANGXIAN("1814", "230039"),//酉阳县
    XIUSHANXIAN("1813", "230040"),//秀山县
    QITA("1822", "230044");//其他


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
