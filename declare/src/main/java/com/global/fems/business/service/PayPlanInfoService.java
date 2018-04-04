package com.global.fems.business.service;

import com.global.fems.business.domain.PayPlanInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;

/**
 * 还款计划信息管理Service接口
 */
public interface PayPlanInfoService {
    void batchSaveContract(String ids) throws DAOException;

    PageBean findContractInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findPayPlanInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ResultModel deleteRecord(String ids) throws DAOException;

    PayPlanInfo findPayPlanInfoById(String id) throws DAOException;

    void declaredUpdate(PayPlanInfo payPlanInfo) throws DAOException;

    void saveOrUpdate(PayPlanInfo payPlanInfo) throws DAOException;

    PageBean findLastPayPlanInfoBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ResultModel setNotSend(String ids) throws DAOException;

    PageBean findPayPlanBriefInfoByContractNo(String contractNo, PageBean pageBean);
}
