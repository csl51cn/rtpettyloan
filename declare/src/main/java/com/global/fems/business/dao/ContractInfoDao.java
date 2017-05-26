package com.global.fems.business.dao;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.DAOException;

/**
 * 贷款合同信息管理Dao接口实现类
 */
public interface ContractInfoDao {
    void save(ContractInfoCycleNode contractInfoCycleNode) throws DAOException;
}
