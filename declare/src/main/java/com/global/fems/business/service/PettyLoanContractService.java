package com.global.fems.business.service;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;

import java.util.List;

/**
 * 实时网签小额贷款合同管理Service接口
 */
public interface PettyLoanContractService {
    void saveOrUpdatePettyLoanContract(PettyLoanContract contract) throws BaseException;

    PageBean findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractById(String id) throws BaseException;

    PageBean findPettyLoanContractBySendStatus(Integer sendStatus, String contractNo, String startDate, String endDate, PageBean pageBean) throws BaseException;

    PettyLoanContract findPettyLoanContractByWorkInfoId(Integer dateId) throws BaseException;

    void batchSavePettyLoanContract(String ids) throws BaseException;

    PageBean findPettyLoanContractByContractNo(String contractNo, PageBean pageBean) throws BaseException;

    PageBean findPettyLoanContractByContractNoFromBizSys(String contractNo, PageBean pageBean) throws BaseException;

    void declaredUpdate(PettyLoanContract contract) throws BaseException;

    PageBean findLastPettyLoanContractBySendStatus(Integer sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws BaseException;

    /**
     * 指定循环授信合同编号,dateId查询是否是第一次放款的业务
     *
     * @param dateId                    流水号
     * @param revolvingCreditContractNo 循环授信合同编号
     * @return
     */
    boolean findIsFirstIssue(Integer dateId, String revolvingCreditContractNo);

    /**
     * 批量保存网签,合同信息,合同发放,还款计划,如果是循环授信贷款,会额外保存授信额度
     *
     * @param ids Data_WorkInfo表中的Date_Id
     */
    void batchSaveAllInfo(String ids) throws BaseException;


    /**
     * 通过dateId查找循环授信贷款
     *
     * @param dateIds dateIds ,格式dateId1,dateId2,dateId3
     * @return
     * @throws BaseException
     */
    List<PettyLoanContract> findRealQuotaContractListByWorkInfoId(String dateIds) throws BaseException;


    /**
     * 将记录设置为已上报
     * @param dateIds dateIds ,格式dateId1,dateId2,dateId3
     */
    void updateSent(String dateIds);
}
