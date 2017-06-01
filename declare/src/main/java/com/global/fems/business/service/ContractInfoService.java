package com.global.fems.business.service;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ResultModel;

/**
 * 贷款合同信息管理Service接口
 */
public interface ContractInfoService {
    ResultModel saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws BaseException;

    void batchSaveContract(String contractNos) throws BaseException;

    ContractInfoCycleNode findContractBycontractNo(String contractNo)  ;
}
