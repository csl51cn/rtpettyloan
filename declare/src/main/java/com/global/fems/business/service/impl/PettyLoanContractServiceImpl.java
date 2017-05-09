package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.apache.commons.lang.StringUtils;
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
     * 根据业务数据date_id查询小额贷款合同
     * @param id 业务数据date_id
     * @return
     */
    public PettyLoanContract findPettyLoanContractByWorkInfoId(String id) throws BaseException {
        PettyLoanContract pettyLoanContract = pettyLoanContractDao.findPettyLoanContractByWorkInfoId(id);
        //将以百分之一为单位的月利率转换为以千分之一为单位的月利率，接口文档要求千分之一为单位的利率
        pettyLoanContract.setIntRate(pettyLoanContract.getIntRate() * 10 );
        //当前查询的业务数据贷款类型都是自营贷款，委托贷款未走业务系统，设置贷款类型为自营贷款530001
        if (StringUtils.isEmpty(pettyLoanContract.getConCustomerName())) {
            pettyLoanContract.setLoanCate("530001");
        }else{
            pettyLoanContract.setLoanCate("530002");
        }
        return pettyLoanContract;
    }
}
