package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 小额贷款合同管理Service接口实现类
 */
@Service
public class PettyLoanContractServiceImpl implements PettyLoanContractService {

    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    /**
     * 保存或者更新小额贷款合同记录
     *
     * @param contract 包含小额贷款合同的实体类
     * @throws BaseException
     */
    public void saveOrUpdatePettyLoanContract(PettyLoanContract contract) throws BaseException {

        pettyLoanContractDao.saveOrUpdate(contract);

    }

    /**
     * 分页查询合同记录
     *
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {

        return pageBean = pettyLoanContractDao.findPettyLoanContractByDate(startDate, endDate, pageBean);

    }

    /**
     * 根据id查询合同记录
     *
     * @param id
     * @return
     */
    public PettyLoanContract findpettyLoanContractById(String id) throws BaseException {
        return pettyLoanContractDao.findpettyLoanContractById(id);
    }

    /**
     * @param sendStatus
     * @param insertStartDate
     * @param insertEndDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String insertStartDate, String insertEndDate, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractBySendStatus(sendStatus, insertStartDate, insertEndDate, pageBean);
    }
}
