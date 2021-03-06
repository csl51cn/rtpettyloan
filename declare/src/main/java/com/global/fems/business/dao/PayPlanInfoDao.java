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

    /**
     * 根据batchNo查询记录
     *
     * @param batchNo 批次号
     * @return
     */
    List<PayPlanInfo> findByBatchNo(String batchNo);

    /**
     * 根据date_id,期数,交易类型和上报结果查询记录数
     *
     * @param dateId     dateId
     * @param counter    期数
     * @param reportType 交易类型
     * @param result     上报结果
     * @return 满足条件的记录数
     */
    Long findCountByDateIdAndCounterAndReportTypeAndResult(String dateId, String counter, String reportType, String result);
}
