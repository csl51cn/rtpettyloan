package com.global.fems.business.dao;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

import java.util.List;

/**
 * 实时网签小额贷款合同信息管理Dao接口
 */
public interface PettyLoanContractDao {


    PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractById(String id) throws BaseException;

    void saveOrUpdate(PettyLoanContract contract) throws DAOException;

    PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String contractNo, String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException;

    void batchSavePettyLoanContract(List<PettyLoanContract> list) throws BaseException;

    PettyLoanContract findContractByDateId(Integer dateId) throws BaseException;

    PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException;

    PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean) throws BaseException;

    int findPettyLoanContractDateIdByContractNo(String contractNo) throws BaseException;

    PageBean findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws DAOException;

    List<PettyLoanContract> findContractListByDateId(String dateId) throws DAOException;

    PageBean findLastPettyLoanContractBySendStatus(Integer sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException;

    String findTermUnit(Integer dateId) throws DAOException;

    String findProductType(Integer dateId) throws DAOException;

    int findBusinessCount(Integer dateId,String revolvingCreditContractNo);



    List<PettyLoanContract> findByBatchNo(String batchNo);


    void batchUpdateInfo(List<PettyLoanContract> byBatchNo,  boolean isUpdateValueNullField);

    List<PettyLoanContract> findRealQuotaContractListByWorkInfoId(List<String> dateIds);

    /**
     * 将记录设置为已上报
     *
     * @param dateIds dateIds ,格式dateId1,dateId2,dateId3
     */
    void updateSent(List<String> dateIds);
}
