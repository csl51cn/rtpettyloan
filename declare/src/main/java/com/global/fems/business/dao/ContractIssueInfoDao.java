package com.global.fems.business.dao;

import com.global.fems.business.domain.ContractIssueInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

import java.util.List;

/**
 * 贷款放款信息管理Dao接口
 */
public interface ContractIssueInfoDao {
    PageBean findContractByContractNoFromContractInfo(String contractNo, String sendStatus, PageBean pageBean) throws DAOException;

    ContractIssueInfo findContractByDateId(String dateId) throws DAOException;

    void batchSaveContract(List<ContractIssueInfo> list) throws DAOException;

    List<ContractIssueInfo> findContractListByDateId(String dateId) throws DAOException;

    void saveOrUpdate(ContractIssueInfo contractIssueInfo) throws DAOException;

    PageBean findContractIssueInfoBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ContractIssueInfo findContractIssueInfoById(String id) throws DAOException;

    List<ContractIssueInfo> findContractIssueInfoListByDateId(String dateId) throws DAOException;

    void batchUpdateContract(List<ContractIssueInfo> list, boolean isUpdateValueNullField) throws DAOException;

    PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    /**
     * 根据batchNo查询记录
     * @param batchNo 批次号
     * @return
     */
    List<ContractIssueInfo> findByBatchNo(String batchNo);
}
