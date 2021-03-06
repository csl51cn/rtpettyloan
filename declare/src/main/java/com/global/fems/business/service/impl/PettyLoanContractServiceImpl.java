package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.DAOException;
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
    @Override
    public void saveOrUpdatePettyLoanContract(PettyLoanContract contract) throws BaseException {
        try {

            if (StringUtil.isNullOrEmpty(contract.getDateId())) {//设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(contract.getContractNo());
                contract.setDateId(dateId);
            }
            List<PettyLoanContract> existContractList = pettyLoanContractDao.findContractListByDateId(contract.getDateId() + "");
            if (existContractList != null && existContractList.size() > 0) {
                for (PettyLoanContract pettyLoanContract : existContractList) {
                    if ("Y".equals(pettyLoanContract.getIsLast()) && pettyLoanContract.getSendStatus() == 0) {
                        if (StringUtil.isNullOrEmpty(contract.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                }
            }
            //身份证或者组织机构码把字母转为大写
            contract.setCertificateNo(contract.getCertificateNo().toUpperCase());
            if (contract.getSendStatus() != null && contract.getSendStatus() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            if (StringUtil.isNullOrEmpty(contract.getSendStatus())) {
                contract.setSendStatus(0);//设置发送状态,0表示未发送，1表示已发送
            }
            if (StringUtil.isNullOrEmpty(contract.getInsertDate())) {//设置保存时间
                contract.setInsertDate(new Date());
            }

            if (StringUtil.isNullOrEmpty(contract.getIsLast())) {
                //设置为最新
                contract.setIsLast("Y");
            }
            pettyLoanContractDao.saveOrUpdate(contract);

        } catch (Exception e) {
            throw new BaseException(e.getLocalizedMessage());
        }


    }

    /**
     * 分页查询合同记录
     *
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {

        return pettyLoanContractDao.findPettyLoanContractByDate(startDate, endDate, pageBean);

    }

    /**
     * 根据id查询合同记录
     *
     * @param id
     * @return
     */
    @Override
    public PettyLoanContract findPettyLoanContractById(String id) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractById(id);
    }

    /**
     * 根据申报状态查询所有小额贷款合同记录
     *
     * @param sendStatus 申报状态0:未申报，1：已申报
     * @param contractNo 合同编号
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String contractNo, String startDate, String endDate, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractBySendStatus(sendStatus, contractNo, startDate, endDate, pageBean);
    }

    /**
     * 根据业务数据date_id查询小额贷款合同
     *
     * @param dateId 业务数据date_id
     * @return
     */
    @Override
    public PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException {
        PettyLoanContract pettyLoanContract = pettyLoanContractDao.findPettyLoanContractByWorkInfoId(dateId);
        setIntRate(pettyLoanContract);
        //当前查询的业务数据贷款类型都是自营贷款，委托贷款未走业务系统，设置贷款类型为自营贷款530001
        if (StringUtils.isEmpty(pettyLoanContract.getConCustomerName())) {
            pettyLoanContract.setLoanCate("530001");
        } else {
            pettyLoanContract.setLoanCate("530002");
        }
        //身份证或组织机构码把字母转为大写
        pettyLoanContract.setCertificateNo(pettyLoanContract.getCertificateNo().toUpperCase());
        return pettyLoanContract;
    }

    //设置利率
    private void setIntRate(PettyLoanContract pettyLoanContract) {
        Integer dateId = pettyLoanContract.getDateId();
        String productType = pettyLoanContractDao.findProductType(dateId);
        //将以百分之一为单位的利率转换为以千分之一为单位的利率，接口文档要求千分之一为单位的利率
        Double intRate = pettyLoanContract.getIntRate();
        intRate *= 10;
        String termUnit = pettyLoanContractDao.findTermUnit(dateId);
        if ("付易贷".equals(productType) && "周".equals(termUnit)) {
            intRate /= 7;
        } else if ("付易贷".equals(productType) && "月".equals(termUnit)) {
            intRate /= 30;
        }
        pettyLoanContract.setIntRate(intRate);
    }

    /**
     * 从Data_WorkInfo表中查询合同数据，批量保存到小额贷款合同表中
     *
     * @param ids Data_WorkInfo表中的Date_Id
     * @throws BaseException
     */
    @Override
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
            setIntRate(pettyLoanContract);
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
            //设置为最新
            pettyLoanContract.setIsLast("Y");
            //身份证或组织机构码把字母转为大写
            pettyLoanContract.setCertificateNo(pettyLoanContract.getCertificateNo().toUpperCase());
            String conCertificateNo = pettyLoanContract.getConCertificateNo();
            if (!StringUtil.isNullOrEmpty(conCertificateNo)) {
                pettyLoanContract.setConCertificateNo(conCertificateNo.toUpperCase());
            }
            validate(id, pettyLoanContract);
            list.add(pettyLoanContract);
        }
        if (list.size() > 0) { //插入0个记录，会抛异常
            pettyLoanContractDao.batchSavePettyLoanContract(list);
        }

    }


    /**
     * 根据合同编号从DC_PETTY_LOAN_CONTRACT中查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @Override
    public PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException {

        return pettyLoanContractDao.findPettyLoanContractByContractNo(contractNo, pageBean);
    }

    /**
     * 根据合同编号从业务系统查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @Override
    public PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findPettyLoanContractByContractNoFromBizSys(contractNo, pageBean);
    }

    /**
     * 更新已申报合同
     *
     * @param contract
     */
    @Override
    public void declaredUpdate(PettyLoanContract contract) throws BaseException {
        //查询已有记录,将是否为最新版本设置为N
        List<PettyLoanContract> list = pettyLoanContractDao.findContractListByDateId(contract.getDateId() + "");

        for (PettyLoanContract pettyLoanContract : list) {

            if ("N".equals(pettyLoanContract.getIsLast())) {
                continue;
            } else {
                pettyLoanContract.setIsLast("N");
                pettyLoanContractDao.saveOrUpdate(pettyLoanContract);
            }

        }
        //设置id为空
        contract.setId(null);
        //设置网签合同号为空
        contract.setNetSignNo(null);
        //设置申报状态为未申报,0:否,1:是
        contract.setSendStatus(0);
        //设置申报时间为空
        contract.setSendDate(null);
        //设置记录保存时间
        contract.setInsertDate(new Date());
        contract.setIsLast("Y");
        //校验属性值是否为空
        validate(contract.getDateId() + "", contract);
        pettyLoanContractDao.saveOrUpdate(contract);
    }

    /**
     * 根据申报状态查询最新的小额贷款合同记录
     *
     * @param sendStatusCode 申报状态0:未申报，1：已申报
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findLastPettyLoanContractBySendStatus(Integer sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws BaseException {
        return pettyLoanContractDao.findLastPettyLoanContractBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
    }

    /**
     * 校验数据是否为空
     *
     * @param id
     * @param pettyLoanContract
     */
    private void validate(String id, PettyLoanContract pettyLoanContract) {
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
    }

}
