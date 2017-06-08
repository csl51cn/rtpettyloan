package com.global.fems.business.service;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ResultModel;

/**
 * 贷款合同信息管理Service接口
 */
public interface ContractInfoService {
    ResultModel saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws BaseException;

    void batchSaveContract(String ids) throws BaseException;

    ContractInfoCycleNode findContractBycontractNo(String contractNo) throws BaseException;

    PageBean findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws BaseException;

    PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws BaseException;

    PageBean findContractBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws BaseException;

    ContractInfoCycleNode findContractById(String id) throws BaseException;

    void declaredUpdate(ContractInfoCycleNode contractInfoCycleNode) throws BaseException;

    ResultModel deleteRecord(ContractInfoCycleNode contractInfoCycleNode) ;
}
