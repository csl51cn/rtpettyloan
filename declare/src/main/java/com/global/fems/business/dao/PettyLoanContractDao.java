package com.global.fems.business.dao;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

import java.util.List;

/**
 * 实时网签小额贷款合同信息管理Dao接口
 */
public interface PettyLoanContractDao {


    PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractById(String id) throws BaseException;

    void saveOrUpdate(PettyLoanContract contract) throws BaseException;

    PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException;

    void batchSavePettyLoanContract(List<PettyLoanContract> list);

    PettyLoanContract findContractByDateId(Integer dateId);
}
