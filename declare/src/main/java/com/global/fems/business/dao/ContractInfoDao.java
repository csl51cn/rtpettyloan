package com.global.fems.business.dao;

import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.DAOException;

import java.util.List;

/**
 * 贷款合同信息管理Dao接口实现类
 */
public interface ContractInfoDao {
    //void save(ContractInfoCycleNode contractInfoCycleNode) throws DAOException;

    void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException;

    List<ContractInfoCycleNode> findContractListByContractNo(String contractNo) throws DAOException;

    ContractInfoCycleNode findContractByContractNoFromPettyLoanContract(String contractNo) throws DAOException;

    CoCustomerCycleNode findCoCustomerInfoFromDataMemberInfo(String code)  throws DAOException;

    CoCustomerCycleNode findCoCustomerInfoFromDataCompanyInfo(String code) throws DAOException;

    void batchSavePettyLoanContract(List<ContractInfoCycleNode> list) throws DAOException;

    ContractInfoCycleNode findContractByContractNoFromDCContractInfo(String contractNo)throws DAOException;
}
