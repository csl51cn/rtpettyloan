package com.global.fems.business.dao;

import com.global.fems.business.domain.PayPlanInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

import java.util.List;

/**
 * 还款计划信息管理Dao接口
 */

public interface PayPlanInfoDao {
    List<PayPlanInfo> findPayPlanInfoListByDateId(String dateId) throws DAOException;

    List<PayPlanInfo> findPayPlanInfoListByDateIdFromBizSys(String dateId) throws DAOException;

    void batchSaveContract(List<PayPlanInfo> list) throws DAOException;

    PageBean findContractInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findPayPlanInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    void batchUpdateContract(List<PayPlanInfo> list, boolean isUpdateValueNullField) throws DAOException;

    void saveOrUpdate(PayPlanInfo payPlanInfo) throws DAOException;

    PayPlanInfo findPayPlanInfoById(String id) throws DAOException;

    List<PayPlanInfo> findPayPlanInfoListByDateIdAndCounter(Integer dateId, String counter) throws DAOException;

    PageBean findLastPayPlanInfoBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    PageBean findPayPlanBriefInfoByContractNo(String contractNo, PageBean pageBean);
}
