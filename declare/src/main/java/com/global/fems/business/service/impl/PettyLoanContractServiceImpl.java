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
    public PettyLoanContract findPettyLoanContractById(String id) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractById(id);
    }

    /**
     * 根据申报状态查询小额贷款合同记录
     * @param sendStatus 申报状态0:未申报，1：已申报
     * @param insertStartDate
     * @param insertEndDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String insertStartDate, String insertEndDate, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractBySendStatus(sendStatus, insertStartDate, insertEndDate, pageBean);
    }

    /**
     * 根据业务数据id查询小额贷款合同
     * @param id
     * @return
     */
    public PettyLoanContract findPettyLoanContractByWorkInfoId(String id) throws BaseException {

        return pettyLoanContractDao.findPettyLoanContractByWorkInfoId(id);
    }
}
