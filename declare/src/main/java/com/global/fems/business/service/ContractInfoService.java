package com.global.fems.business.service;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ResultModel;

/**
 * 贷款合同信息管理Service接口
 */
public interface ContractInfoService {
    void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws Exception;

    void batchSaveContract(String ids) throws DAOException;

    ContractInfoCycleNode findContractByDateId(String dateId) throws DAOException;

    PageBean findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws DAOException;

    PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    PageBean findContractBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ContractInfoCycleNode findContractById(String id) throws DAOException;

    void declaredUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException;

    ResultModel deleteRecord(String ids) throws DAOException;

    PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    ResultModel setNotSend(String ids) throws DAOException;
    /**
     *  将记录设置为已上报
     * @param dateIds dateIds ,格式dateId1,dateId2,dateId3
     * @return
     * @throws BaseException
     */
    void updateSent(String dateIds);
}
