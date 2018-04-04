package com.global.fems.business.service;

import com.global.fems.business.domain.ContractIssueInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;

/**
 * 贷款放款信息管理Service接口
 */
public interface ContractIssueInfoService {
    PageBean findContractByContractNoFromContractInfo(String contractNo, String sendStatus, PageBean pageBean) throws DAOException;

    ContractIssueInfo findContractByDateId(String dateId) throws DAOException;

    void batchSaveContract(String ids) throws DAOException;

    void declaredUpdate(ContractIssueInfo contractIssueInfo) throws DAOException;

    void saveOrUpdate(ContractIssueInfo contractIssueInfo) throws DAOException;

    PageBean findContractIssueInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ContractIssueInfo findContractIssueInfoById(String id) throws DAOException;

    ResultModel deleteRecord(String ids) throws DAOException;

    PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ResultModel setNotSend(String ids) throws DAOException;
}
