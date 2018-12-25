package com.global.fems.business.dao;

import com.global.fems.business.domain.RepayInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 贷款回收信息管理dao
 */
public interface RepayInfoDao {


    /**
     * 根据还款日期/合同号从业务系统查询还款信息
     *
     * @param repayStartDate
     * @param repayEndDate
     * @param contractNo
     * @param pageBean
     * @return
     */
    PageBean findRepayInfoByRepayDateAndContractNoFromBizSys(String repayStartDate, String repayEndDate, String contractNo, PageBean pageBean) throws DAOException;

    RepayInfo findRepayInfoByIdFromBizSys(String id) throws DAOException;

    List<RepayInfo> findRepayInfoListByDateIdAndCounter(Integer dateId, String counter, String repayDate) throws DAOException;

    void batchSaveContract(ArrayList<RepayInfo> list) throws DAOException;

    void saveOrUpdate(RepayInfo repayInfo) throws DAOException;

    void batchUpdateRepayInfo(List<RepayInfo> list, boolean isUpdateValueNullField) throws DAOException;

    PageBean findBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    RepayInfo findRepayInfoById(String id) throws DAOException;

    PageBean findRepayInfoBySendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException;

    int findTotalCounter(Integer dateId) throws DAOException;

    PageBean findLastRepayInfoSendStatus(String sendStatusCode, String contractNo, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException;

    Float findPenaltyPrincipalInterest(Integer dateId, String counter, String repayDate) throws DAOException;

    Float findPenaltyServiceFee(Integer dateId, String counter, String repayDate) throws DAOException;

    Map findRepayPrincipalInterest(Integer dateId, String counter, String repayDate) throws DAOException;

    String findRepayModeByDateId(Integer dateId) throws DAOException;


    /**
     * 根据batchNo查询记录
     *
     * @param batchNo 批次号
     * @return
     */
    List<RepayInfo> findByBatchNo(String batchNo);


    /**
     * 根据date_id,期数,还款日期,交易类型和上报结果查询记录数
     *
     * @param dateId     dateId
     * @param counter    期数
     * @param repayDate  还款日期
     * @param reportType 交易类型
     * @param result     上报结果
     * @return 满足条件的记录数
     */
    Long findCountByDateIdAndCounterAndReportTypeAndResult(Integer dateId, String counter, String repayDate, String reportType, String result);
}
