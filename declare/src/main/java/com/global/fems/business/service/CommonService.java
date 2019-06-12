package com.global.fems.business.service;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.framework.util.SpringContextUtil;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Senlin.Deng
 * @Description: 提供一些通用方法
 * @date: Created in 2019/6/11 16:47
 * @Modified By:
 */
public class CommonService {




    private static FIFOCache<String, String> cache = new FIFOCache<>(30);

    /**
     * First in first out  缓存
     *
     * @param <K>
     * @param <V>
     */
    private static class FIFOCache<K, V> extends LinkedHashMap<K, V> {
        private final int SIZE;

        FIFOCache(int size) {
            super();
            SIZE = size;
        }

        /**
         * 重写淘汰机制
         *
         * @param eldest
         * @return
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            //如果缓存存储达到最大值删除最先进入的元素
            return size() > SIZE;
        }

    }

    /**
     * 返回循环授信合同编号正确的格式.其中首次提款时,不需要添加,从第二次提款起,每次添加的n为相同循环授信合同编号提款次数-1.
     * 例如,循环授信合同编号A,第一次提款时,上报合同编号为"A",第3次提款,此时上报的合同编号为"A-2"
     *
     * @param dateId                    dateId
     * @param revolvingCreditContractNo 循环授信合同编号
     * @return 正确格式的合同编号
     */
    public static String getRealQuotaNo(Integer dateId, String revolvingCreditContractNo) {
        //增加缓存是因为还款计划记录数较多,减少查询次数.缓存中的key:为当前时间 + dateId + 循环授信合同编号.
        String key = LocalDate.now().toString() + dateId + revolvingCreditContractNo;
        if (cache.containsKey(key)) {
            revolvingCreditContractNo = cache.get(key);
        } else {
            PettyLoanContractDao pettyLoanContractDao = SpringContextUtil.getBean(PettyLoanContractDao.class);
            int businessCount = pettyLoanContractDao.findBusinessCount(dateId, revolvingCreditContractNo);
            if (businessCount > 1) {
                //如果不是第一次提款,需要特殊处理
                revolvingCreditContractNo = revolvingCreditContractNo.trim() + "-" + (businessCount - 1);
                cache.put(key, revolvingCreditContractNo);
            }
        }
        return revolvingCreditContractNo;

    }




}
