package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.dao.impl.PettyLoanContractDaoImpl;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 小额贷款合同管理Service
 */
@Service
public class PettyLoanContractServiceImpl implements PettyLoanContractService {

    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    /**
     * 插入合同记录
     * @param contract
     * @throws BaseException
     */
    public void savePettyLoanContract(PettyLoanContract contract) throws BaseException {
        pettyLoanContractDao.insert(contract);

    }

    /**
     * 分页查询合同记录
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) {

       return pageBean =  pettyLoanContractDao.findForPage( startDate, endDate, pageBean );

    }

    /**
     * 根据id查询合同记录
     * @param id
     * @return
     */
    public PettyLoanContract findpettyLoanContractById(String id) {
        return pettyLoanContractDao.findpettyLoanContractById(id);
    }
}
