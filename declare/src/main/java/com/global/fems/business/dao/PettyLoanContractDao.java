package com.global.fems.business.dao;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

/**
 * 小额贷款合同管理Dao接口
 */
public interface PettyLoanContractDao {


    PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractById(String id) throws BaseException;

    void saveOrUpdate(PettyLoanContract contract) throws BaseException;

    PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String insertStartDate, String insertEndDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractByWorkInfoId(String id) throws BaseException;
}