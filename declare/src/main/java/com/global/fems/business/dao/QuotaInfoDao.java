package com.global.fems.business.dao;

import com.global.fems.business.domain.QuotaInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

import java.util.List;

/**
 * 授信额度信息管理Dao接口
 *
 * @author senlin.deng
 * @date 2018/4/3 16:34
 */
public interface QuotaInfoDao {

    /**
     * 根据合同编号从业务系统查询授信额度信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException;

    /**
     * 根据dateId从业务系统查询额度信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    QuotaInfo findQuotaInfoByDateId(String dateId) throws DAOException;

    /**
     * 根据dateId查询已有记录
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    List<QuotaInfo> findQuotaoInfoListByDateId(String dateId) throws DAOException;


    /**
     * 批量更新
     *
     * @param list
     * @param isUpdateValueNullField
     * @throws DAOException
     */
    void batchUpdateQuotaoInfo(List<QuotaInfo> list, boolean isUpdateValueNullField) throws DAOException;

    /**
     * 保存或更新
     *
     * @param quotaInfo
     */
    void saveOrUpdate(QuotaInfo quotaInfo) throws DAOException;


    /**
     * 批量保存授信额度信息
     *
     * @param list
     * @throws DAOException
     */
    void batchSaveQuotaInfo(List<QuotaInfo> list) throws DAOException;

    /**
     * 通过签约日期从业务系统查询授信额度信息
     *
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaInfoByDateFromBizSys(String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    /**
     * 通过jk合同编号从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param contractNoQuery
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaBriefInfoByContractNo(String contractNoQuery, PageBean pageBean) throws DAOException;

    /**
     * 通过协议签约日期从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    /**
     * 通过id 从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    QuotaInfo findQuotaInfoById(String id) throws DAOException;

    /**
     * 根据date_id查询授信额度信息记录
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    List<QuotaInfo> findQuotaInfoListByDateId(String dateId) throws DAOException;

    /**
     * 根据申报状态查询和签约日期查询最新的授信额度信息
     *
     * @param sendStatusCode
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findLastQuotaInfoBySendStatus(String sendStatusCode, String startDate, String endDate, PageBean pageBean) throws DAOException;

    /**
     * 根据batchNo查询记录
     * @param batchNo 批次号
     * @return
     */
    List<QuotaInfo> findByBatchNo(String batchNo);
}
