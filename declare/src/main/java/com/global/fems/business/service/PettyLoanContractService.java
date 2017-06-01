package com.global.fems.business.service;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

/**
 * 实时网签小额贷款合同管理Service接口
 */
public interface PettyLoanContractService {
    void saveOrUpdatePettyLoanContract(PettyLoanContract contract) throws BaseException;

    PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractById(String id) throws BaseException;

    PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException;

    void batchSavePettyLoanContract(String ids) throws BaseException;

    PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException;

    PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean)throws BaseException;

}
