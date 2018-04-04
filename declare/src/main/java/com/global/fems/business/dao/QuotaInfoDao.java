package com.global.fems.business.dao;

import com.global.framework.dbutils.support.PageBean;
/**
 *
 * 授信额度信息管理Dao接口
 * @author senlin.deng
 * @date 2018/4/3 16:34
 *
 */
public interface QuotaInfoDao {
    /**
     * 根据合同编号查询授信额度信息
     * @param contractNo
     * @param pageBean
     * @return
     */
    PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean);
}
