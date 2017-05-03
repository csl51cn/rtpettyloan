package com.global.fems.business.service;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

import java.util.Date;

/**
 * Created by senlin.deng on 2017/5/2.
 */
public interface PettyLoanContractService {
      void  savePettyLoanContract(PettyLoanContract contract) throws BaseException;

     PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean);

    PettyLoanContract findpettyLoanContractById(String id);
}
