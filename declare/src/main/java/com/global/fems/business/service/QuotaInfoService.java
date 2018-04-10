package com.global.fems.business.service;

import com.global.fems.business.domain.QuotaInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;

/**
 * @author senlin.deng
 * @date 2018/4/3 16:32
 * 额度信息管理service接口
 */
public interface QuotaInfoService {
    /**
     * 根据合同编号从业务系统查询授信额度信息
     *
     * @param contractNo 合同编号
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException;

    /**
     * 根据dateId查询授信额度详细信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    QuotaInfo findQuotaInfoByDateId(String dateId) throws DAOException;

    /**
     * 已申报更新
     *
     * @param quotaInfo
     * @throws DAOException
     */
    void declaredUpdate(QuotaInfo quotaInfo) throws DAOException;

    /**
     * 单条保存或更新
     *
     * @param quotaInfo
     * @throws DAOException
     */
    void saveOrUpdate(QuotaInfo quotaInfo) throws DAOException;

    /**
     * 批量保存
     *
     * @param ids date_id
     * @throws DAOException
     */
    void batchSaveContract(String ids) throws DAOException;


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
     * 通过JK开头合同号从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param contractNoQuery
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findQuotaBriefInfoByContractNo(String contractNoQuery, PageBean pageBean) throws DAOException;

    /**
     * 通过签约日期从DC_QUOTA_INFO中查询授信额度信息
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
     * 根据id从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    QuotaInfo findQuotaInfoById(String id) throws DAOException;

    /**
     * 根据date_id设置已申报记录删除
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    ResultModel deleteRecord(String ids) throws DAOException;


    /**
     * 设置指定id的记录的申报状态
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    ResultModel setNotSend(String ids) throws DAOException;

    /**
     * 根据申报状态查询和签约日期查询最新的授信额度信息
     *
     * @param sendStatusCode
     * @param contractNo
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    PageBean findLastQuotaInfoBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException;
}
