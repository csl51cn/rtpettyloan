package com.global.fems.business.dao;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

import java.util.Date;

public interface PettyLoanContractDao {
        void  insert(PettyLoanContract contract) throws BaseException;

    PageBean findForPage(String startDate, String endDate,PageBean pageBean);

    PettyLoanContract findpettyLoanContractById(String id);
}
