package com.global.fems.business.service;

import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

/**
 * @author senlin.deng
 * @date 2018/4/3 16:32
 * 额度信息管理service接口
 */
public interface QuotaInfoService {
    /**
     * 根据循环授信合同编号从业务系统查询授信额度信息
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException;
}
