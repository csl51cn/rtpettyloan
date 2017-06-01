package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实时网签小额贷款合同管理Service接口实现类
 */
@Service
public class PettyLoanContractServiceImpl implements PettyLoanContractService {
    private static final Logger logger = LoggerFactory.getLogger(PettyLoanContractServiceImpl.class);
    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    /**
     * 保存或者更新小额贷款合同记录
     *
     * @param contract 包含小额贷款合同的实体类
     * @throws BaseException
     */
    public void saveOrUpdatePettyLoanContract(PettyLoanContract contract) throws BaseException {
        if (StringUtil.isNullOrEmpty(contract.getSendStatus())) {
            contract.setSendStatus(0);//设置发送状态,0表示未发送，1表示已发送
        }
        if (StringUtil.isNullOrEmpty(contract.getInsertDate())) {//设置保存时间
            contract.setInsertDate(new Date());
        }
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

        return pettyLoanContractDao.findPettyLoanContractByDate(startDate, endDate, pageBean);

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
     *
     * @param sendStatus      申报状态0:未申报，1：已申报
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String startDate, String endDate, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractBySendStatus(sendStatus, startDate, endDate, pageBean);
    }

    /**
     * 根据业务数据date_id查询小额贷款合同
     *
     * @param dateId 业务数据date_id
     * @return
     */
    public PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException {
        PettyLoanContract pettyLoanContract = pettyLoanContractDao.findPettyLoanContractByWorkInfoId(dateId);
        //将以百分之一为单位的月利率转换为以千分之一为单位的月利率，接口文档要求千分之一为单位的利率
        pettyLoanContract.setIntRate(pettyLoanContract.getIntRate() * 10);
        //当前查询的业务数据贷款类型都是自营贷款，委托贷款未走业务系统，设置贷款类型为自营贷款530001
        if (StringUtils.isEmpty(pettyLoanContract.getConCustomerName())) {
            pettyLoanContract.setLoanCate("530001");
        } else {
            pettyLoanContract.setLoanCate("530002");
        }
        return pettyLoanContract;
    }

    /**
     * 从Data_WorkInfo表中查询合同数据，批量保存到小额贷款合同表中
     *
     * @param ids Data_WorkInfo表中的Date_Id
     * @throws BaseException
     */
    public void batchSavePettyLoanContract(String ids) throws BaseException {
        String[] idsArr = ids.split(",");
        List<PettyLoanContract> list = new ArrayList<PettyLoanContract>();
        for (String id : idsArr) {
            Integer dateId = new Integer(id);
            //根据dateId查询小额贷款合同表，避免重复插入数据
            PettyLoanContract existPettyLoanContract = pettyLoanContractDao.findContractByDateId(dateId);
            if (existPettyLoanContract != null) {//如果存在，跳过
                continue;
            }
            PettyLoanContract pettyLoanContract = pettyLoanContractDao.findPettyLoanContractByWorkInfoId(dateId);
            //将以百分之一为单位的月利率转换为以千分之一为单位的月利率，接口文档要求千分之一为单位的利率
            pettyLoanContract.setIntRate(pettyLoanContract.getIntRate() * 10);
            //当前查询的业务数据贷款类型都是自营贷款，委托贷款未走业务系统，设置贷款类型为自营贷款530001
            if (StringUtils.isEmpty(pettyLoanContract.getConCustomerName())) {
                pettyLoanContract.setLoanCate("530001");
            } else {
                pettyLoanContract.setLoanCate("530002");
            }
            if (StringUtil.isNullOrEmpty(pettyLoanContract.getSendStatus())) {
                pettyLoanContract.setSendStatus(0);//设置发送状态,0表示未发送，1表示已发送
            }
            if (StringUtil.isNullOrEmpty(pettyLoanContract.getInsertDate())) {//设置保存时间
                pettyLoanContract.setInsertDate(new Date());
            }
            if (StringUtils.isBlank(pettyLoanContract.getContractNo())) {
                logger.error("批量插入合同数据：Date_Id：" + id + "的合同编号为空");
            }
            if (StringUtils.isBlank(pettyLoanContract.getCertificateType())) {
                logger.error("批量插入合同数据：Date_Id：" + id + "证件类型为空");
            }
            if (StringUtils.isBlank(pettyLoanContract.getCertificateNo())) {
                logger.error("批量插入合同数据：Date_Id：" + id + "证件号码为空");
            }
            if (StringUtils.isBlank(pettyLoanContract.getCustomerName())) {
                logger.error("批量插入合同数据：Date_Id：" + id + "借款人名称为空");
            }
            if (null == pettyLoanContract.getContractSignDate()) {
                logger.error("批量插入合同数据：Date_Id：" + id + "合同签订日期为空");
            }
            if (null == pettyLoanContract.getContractAmount()) {
                logger.error("批量插入合同数据：Date_Id：" + id + "合同金额为空");
            }
            list.add(pettyLoanContract);
        }
        if (list.size() > 0) { //插入0个记录，会抛异常
            pettyLoanContractDao.batchSavePettyLoanContract(list);
        }

    }

    public PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException {

        return pettyLoanContractDao.findPettyLoanContractByContractNo(contractNo,pageBean);
    }

    public PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractByContractNoFromBizSys(contractNo,pageBean);
    }
}
