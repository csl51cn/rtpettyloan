package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PayPlanInfoDao;
import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.PayPlanInfo;
import com.global.fems.business.service.PayPlanInfoService;
import com.global.fems.message.util.OrgCode;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.util.StringUtil;
import com.global.param.domain.ResultModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 还款计划信息管理Service接口实现类
 */
@Service
public class PayPlanInfoServiceImpl implements PayPlanInfoService {
    private Logger logger = LoggerFactory.getLogger(PayPlanInfoServiceImpl.class);

    @Autowired
    private PayPlanInfoDao payPlanInfoDao;
    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;


    /**
     * 批量保存
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(String ids) throws DAOException {
        String[] idsArr = ids.split(",");
        List<PayPlanInfo> list = new ArrayList<PayPlanInfo>();
        a:
        for (String dateId : idsArr) {
            List<PayPlanInfo> existPayPlanInfoList = payPlanInfoDao.findPayPlanInfoListByDateId(dateId);
            if (existPayPlanInfoList != null && existPayPlanInfoList.size() > 0) {//如果存在且上报类型不是删除，跳过,避免重复插入
                Boolean isDelete = false; //是否删除的标记
                for (PayPlanInfo payPlanInfo : existPayPlanInfoList) {
                    if (!"100003".equals(payPlanInfo.getReportType())) {
                        isDelete = true;
                        break;
                    } else if (payPlanInfo.getIsSend() == 0 && "Y".equals(payPlanInfo.getIsLast())) {//上报类型不为删除,跳过
                        continue a;
                    }
                }
                if (!isDelete) { //已经被删除时,允许保存
                    continue;
                }
            }
            //根据dateId从还款计划表中查询还款计划信息
            List<PayPlanInfo> payPlanInfoList = payPlanInfoDao.findPayPlanInfoListByDateIdFromBizSys(dateId);
            for (PayPlanInfo payPlanInfo : payPlanInfoList) {
                //设置上报类型,初始保存时,默认为新增:100001
                payPlanInfo.setReportType("100001");
                //设置组织机构代码
                payPlanInfo.setOrgCode(OrgCode.getOrgCode());
                //设置是否申报,0:否,1:是
                payPlanInfo.setIsSend(0);
                //设置记录保存时间
                payPlanInfo.setInsertDate(new Date());
                //是否最新版本
                payPlanInfo.setIsLast("Y");
                //设置总期数
                payPlanInfo.setTotalCounter(payPlanInfoList.size() + "");
                //设置数据类型
                payPlanInfo.setDataType("PAYPLAN_INFO");
                // 校验值是否为空
                validate(payPlanInfo.getContractNo(), payPlanInfo);
                list.add(payPlanInfo);
            }
        }
        if (list.size() > 0) {
            payPlanInfoDao.batchSaveContract(list);
        }
    }

    /**
     * 从DC_CONTRACT_ISSUE_INFO表中根据合同编号查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        return payPlanInfoDao.findContractInfoByContractNo(contractNo, pageBean);

    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询还款计划信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findPayPlanInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return payPlanInfoDao.findPayPlanInfoBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
    }

    /**
     * 已申报删除
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @Override
    public ResultModel deleteRecord(String ids) throws DAOException {
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            try {
                PayPlanInfo payPlanInfo = payPlanInfoDao.findPayPlanInfoById(id);
                payPlanInfo.setId(null);
                //设置申报状态为未申报,0:否,1:是
                payPlanInfo.setIsSend(0);
                //设置记录保存时间
                payPlanInfo.setInsertDate(new Date());
                payPlanInfo.setIsLast("Y");
                //校验属性值是否为空
                validate(payPlanInfo.getContractNo(), payPlanInfo);
                //设置上报类型为修改记录
                payPlanInfo.setReportType("100003");

                //查询已有记录,将是否为最新版本设置为N
                List<PayPlanInfo> list = payPlanInfoDao.findPayPlanInfoListByDateIdAndCounter(payPlanInfo.getDateId(), payPlanInfo.getCounter());
                if (list != null && list.size() > 0) {
                    for (PayPlanInfo payPlanInfoNode : list) {
                        if ("100003".equals(payPlanInfoNode.getReportType())) {
                            throw new DAOException("合同编号为:" + payPlanInfoNode.getContractNo() + ",此记录第" + payPlanInfoNode.getCounter() + "期已设置申报删除,无需再次设置");
                        }
                        if ("N".equals(payPlanInfoNode.getIsLast())) {
                            continue;
                        } else {
                            payPlanInfoNode.setIsLast("N");
                        }
                    }
                    payPlanInfoDao.batchUpdateContract(list, true);
                }
                payPlanInfoDao.saveOrUpdate(payPlanInfo);
            } catch (DAOException e) {
                logger.debug("PayPlanInfoServiceImpl:deleteRecord()", e.getLocalizedMessage());
                return ResultModel.fail(e.getLocalizedMessage());
            }
        }
        return ResultModel.ok();
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public PayPlanInfo findPayPlanInfoById(String id) throws DAOException {
        PayPlanInfo payPlanInfo = payPlanInfoDao.findPayPlanInfoById(id);
        return payPlanInfo;
    }

    /**
     * 已申报修改
     *
     * @param payPlanInfo
     * @throws DAOException
     */
    @Override
    public void declaredUpdate(PayPlanInfo payPlanInfo) throws DAOException {
        //查询已有记录,将是否为最新版本设置为N
        List<PayPlanInfo> list = payPlanInfoDao.findPayPlanInfoListByDateIdAndCounter(payPlanInfo.getDateId(), payPlanInfo.getCounter());
        for (PayPlanInfo node : list) {
            if ("100003".equals(node.getReportType()) && node.getIsSend() == 0) {
                throw new DAOException("当前记录已申报删除,不能进行已申报合同信息修改");
            }
            if ("N".equals(node.getIsLast())) {
                continue;
            } else {
                node.setIsLast("N");
            }
        }
        payPlanInfoDao.batchUpdateContract(list, true);
        payPlanInfo.setId(null);
        //设置申报状态为未申报,0:否,1:是
        payPlanInfo.setIsSend(0);
        //设置组织机构代码
        payPlanInfo.setOrgCode(OrgCode.getOrgCode());
        //设置记录保存时间
        payPlanInfo.setInsertDate(new Date());
        payPlanInfo.setIsLast("Y");
        //校验属性值是否为空
        validate(payPlanInfo.getContractNo(), payPlanInfo);
        //设置上报类型为修改记录
        payPlanInfo.setReportType("100002");
        //设置数据类型
        payPlanInfo.setDataType("PAYPLAN_INFO");

        payPlanInfoDao.saveOrUpdate(payPlanInfo);

    }

    /**
     * 保存或更新
     *
     * @param payPlanInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(PayPlanInfo payPlanInfo) throws DAOException {
        try {
            if (StringUtil.isNullOrEmpty(payPlanInfo.getDateId())) {
                //如果没有dateId,设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(payPlanInfo.getContractNo());
                payPlanInfo.setDateId(dateId);
            }

            if (payPlanInfo.getIsSend() != null && payPlanInfo.getIsSend() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            //判断是否已经录入
            List<PayPlanInfo> existPayPlanInfoList = payPlanInfoDao.findPayPlanInfoListByDateIdAndCounter(payPlanInfo.getDateId(), payPlanInfo.getCounter());
            if (existPayPlanInfoList != null && existPayPlanInfoList.size() > 0) {
                for (PayPlanInfo node : existPayPlanInfoList) {
                    if ("Y".equals(node.getIsLast()) && node.getIsSend() == 0) {
                        if (StringUtil.isNullOrEmpty(payPlanInfo.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                    node.setIsLast("N");
                }
                payPlanInfoDao.batchUpdateContract(existPayPlanInfoList, true);
            }

            //设置上报类型,初始保存时,默认为新增:100001
            payPlanInfo.setReportType("100001");
            //设置组织机构代码
            payPlanInfo.setOrgCode((OrgCode.getOrgCode()));
            //设置是否发送,0:否,1:是
            payPlanInfo.setIsSend(0);
            //设置数据类型
            payPlanInfo.setDataType("PAYPLAN_INFO");

            if (StringUtil.isNullOrEmpty(payPlanInfo.getInsertDate())) {
                //设置记录保存时间
                payPlanInfo.setInsertDate(new Date());
            }
            //设置是否是最新记录
            payPlanInfo.setIsLast("Y");
            payPlanInfoDao.saveOrUpdate(payPlanInfo);
        } catch (Exception e) {
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询最新还款计划信息,申报时使用
     *
     * @param sendStatusCode
     * @param contactNo
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastPayPlanInfoBySendStatus(String sendStatusCode, String contactNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return payPlanInfoDao.findLastPayPlanInfoBySendStatus(sendStatusCode, contactNo, signStartDate, signEndDate, pageBean);
    }

    /**
     * 设置为未申报
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @Override
    public ResultModel setNotSend(String ids) throws DAOException {
        try {
            String[] idArr = ids.split(",");
            ArrayList<PayPlanInfo> payPlanInfoArrayList = new ArrayList<>();
            for (String id : idArr) {
                PayPlanInfo payPlanInfoById = payPlanInfoDao.findPayPlanInfoById(id);
                payPlanInfoById.setIsSend(0);
                payPlanInfoArrayList.add(payPlanInfoById);
            }
            payPlanInfoDao.batchUpdateContract(payPlanInfoArrayList, true);
        } catch (DAOException e) {
            logger.debug("PayPlanInfoServiceImpl:setNotSend()", e.getLocalizedMessage());
            return ResultModel.fail();
        }
        return ResultModel.ok();
    }

    /**
     * 根据合同编号查询还款计划信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findPayPlanBriefInfoByContractNo(String contractNo, PageBean pageBean) {
        return payPlanInfoDao.findPayPlanBriefInfoByContractNo(contractNo, pageBean);
    }


    private void validate(String contractNo, PayPlanInfo payPlanInfo) {
        if (StringUtils.isBlank(payPlanInfo.getCounter())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的还款期数为空");
        }
        if (StringUtils.isBlank(payPlanInfo.getRepayDate())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的应还日期为空");
        }

        if (StringUtils.isBlank(payPlanInfo.getRepayPriAmt())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的应还本金为空");
        }
        if (StringUtils.isBlank(payPlanInfo.getRepayIntAmt())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的应还利息为空");
        }
    }
}
