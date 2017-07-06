package com.global.fems.business.dao;

import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;

import java.util.List;

/**
 * 贷款合同信息管理Dao接口实现类
 */
public interface ContractInfoDao {

    void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException;

    List<ContractInfoCycleNode> findContractListByDateId(String dateId) throws DAOException;

    ContractInfoCycleNode findContractByDateIdFromBizSys(String dateId) throws DAOException;

    CoCustomerCycleNode findCoCustomerInfoFromDataMemberInfo(String code) throws DAOException;

    CoCustomerCycleNode findCoCustomerInfoFromDataCompanyInfo(String code) throws DAOException;

    void batchSaveContract(List<ContractInfoCycleNode> list) throws DAOException;

    PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findContractBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ContractInfoCycleNode findContractById(String id) throws DAOException;

    void batchUpdateContract(List<ContractInfoCycleNode> list, boolean isUpdateValueNullField) throws DAOException;

    PageBean findLastContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;
}
