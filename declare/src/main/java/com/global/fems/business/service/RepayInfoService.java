package com.global.fems.business.service;

import com.global.fems.business.domain.RepayInfo;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;

/**
 * 贷款回收信息管理Service
 */
public interface RepayInfoService {

    /**
     * 根据还款日期/合同号从业务系统查询还款信息
     *
     * @param repayStartDate
     * @param repayEndDate
     * @param contractNo
     * @param pageBean
     * @return
     */
    PageBean findRepayInfoByRepayDateAndContractNoFromBizSys(String repayStartDate, String repayEndDate, String contractNo, PageBean pageBean) throws DAOException;

    void batchSaveRepayInfo(String ids) throws DAOException;

    RepayInfo findRepayInfoByIdFromBizSys(String id) throws DAOException;

    void saveOrUpdate(RepayInfo repayInfo) throws DAOException;

    void declaredUpdate(RepayInfo repayInfo) throws DAOException;

    PageBean findBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException;

    RepayInfo findRepayInfoById(String id) throws DAOException;

    PageBean findRepayInfoBySendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException;

    ResultModel deleteRecord(String ids) throws DAOException;

    PageBean findLastRepayInfoSendStatus(String sendStatusCode, String contractNo, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException;

    ResultModel setNotSend(String ids) throws DAOException;

    /**
     * 设置当期还款次数,使用RepayInfo的remark字段
     *
     * @param repayInfo 还款信息
     */
    void setRemark(RepayInfo repayInfo);

}
