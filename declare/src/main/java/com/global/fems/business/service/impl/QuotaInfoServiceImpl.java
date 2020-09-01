package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.dao.QuotaInfoDao;
import com.global.fems.business.domain.QuotaInfo;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.fems.business.service.QuotaInfoService;
import com.global.fems.message.util.OrgCode;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
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
 * @author senlin.deng
 * @date 2018/4/3 16:34
 * 额度信息管理Service接口实现类
 */
@Service
public class QuotaInfoServiceImpl implements QuotaInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotaInfoServiceImpl.class);
    @Autowired
    private QuotaInfoDao quotaInfoDao;
    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;


    @Autowired
    private PettyLoanContractService PettyLoanContractService;

    @Override
    public PageBean findQuotaInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException {
        return quotaInfoDao.findQuotaInfoByContractNoFromBizSys(contractNo, pageBean);
    }

    @Override
    public QuotaInfo findQuotaInfoByDateId(String dateId) throws DAOException {
        QuotaInfo quotaInfo = quotaInfoDao.findQuotaInfoByDateId(dateId);
        return quotaInfo;
    }


    /**
     * 已申报修改
     *
     * @param quotaInfo 额度信息
     * @throws DAOException
     */
    @Override
    public void declaredUpdate(QuotaInfo quotaInfo) throws DAOException {
        //查询已有记录,将是否为最新版本设置为N
        List<QuotaInfo> list = quotaInfoDao.findQuotaInfoListByDateId(String.valueOf(quotaInfo.getDateId()));
        for (QuotaInfo existedQuotaInfo : list) {
            if ("100003".equals(existedQuotaInfo.getReportType()) && existedQuotaInfo.getIsSend() == 0) {
                throw new DAOException("当前记录存在申报删除记录未申报,不能进行已申报合同信息修改");
            }
            if (!"N".equals(existedQuotaInfo.getIsLast())) {
                existedQuotaInfo.setIsLast("N");
            }
        }
        quotaInfoDao.batchUpdateQuotaoInfo(list, true);
        quotaInfo.setId(null);
        //设置上报类型为修改记录
        quotaInfo.setReportType("100002");
        //设置组织机构代码
        quotaInfo.setOrgCode((OrgCode.getOrgCode()));
        //设置是否发送,0:否,1:是
        quotaInfo.setIsSend(0);
        //设置数据类型
        quotaInfo.setDataType("QUOTA_INFO");
        quotaInfo.setBatchNo(null);
        if (StringUtil.isNullOrEmpty(quotaInfo.getInsertDate())) {
            //设置记录保存时间
            quotaInfo.setInsertDate(new Date());
        }
        //设置是否是最新记录
        quotaInfo.setIsLast("Y");
        quotaInfoDao.saveOrUpdate(quotaInfo);

    }

    /**
     * 保存或更新未申报额度信息
     *
     * @param quotaInfo 额度信息
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(QuotaInfo quotaInfo) throws DAOException {
        try {
            if (StringUtil.isNullOrEmpty(quotaInfo.getDateId())) {
                //设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(quotaInfo.getContractNoQuery());
                quotaInfo.setDateId(dateId);
            }
            if (quotaInfo.getIsSend() != null && quotaInfo.getIsSend() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            //判断是否已经录入
            List<QuotaInfo> existQuotaInfoList = quotaInfoDao.findQuotaoInfoListByDateId(quotaInfo.getDateId() + "");
            if (existQuotaInfoList != null && existQuotaInfoList.size() > 0) {
                for (QuotaInfo node : existQuotaInfoList) {
                    if ("Y".equals(node.getIsLast()) && node.getIsSend() == 0) {
                        if (StringUtil.isNullOrEmpty(quotaInfo.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                    node.setIsLast("N");
                }
                quotaInfoDao.batchUpdateQuotaoInfo(existQuotaInfoList, true);
            }

            //设置上报类型,初始保存时,默认为新增:100001
            quotaInfo.setReportType("100001");
            //设置组织机构代码
            quotaInfo.setOrgCode((OrgCode.getOrgCode()));
            //设置是否发送,0:否,1:是
            quotaInfo.setIsSend(0);
            //设置数据类型
            quotaInfo.setDataType("QUOTA_INFO");

            if (StringUtil.isNullOrEmpty(quotaInfo.getInsertDate())) {
                //设置记录保存时间
                quotaInfo.setInsertDate(new Date());
            }
            //设置是否是最新记录
            quotaInfo.setIsLast("Y");
            quotaInfoDao.saveOrUpdate(quotaInfo);
        } catch (Exception e) {
            throw new DAOException(e.getLocalizedMessage());
        }
    }


    /**
     * 根据dateId从DC_PETTY_LOAN_CONTRACT,已放款客户表,Data_WorkInfo查询贷款合同信息,批量插入到DC_CONTRACT_INFO中
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(String ids) throws DAOException {
        String[] idsArr = ids.split(",");

        List<QuotaInfo> list = new ArrayList<>();
        for (String dateId : idsArr) {
            List<QuotaInfo> existQuotaInfoList = quotaInfoDao.findQuotaoInfoListByDateId(dateId);
            //如果存在且上报类型不是删除，跳过,避免重复插入
            if (existQuotaInfoList != null && existQuotaInfoList.size() > 0) {
                //如果成功删除的记录数=成功增加的记录数量,并且成功增加的记录数>0,允许新增,其余情况不允许再向数据库插入新数据
                //deletedSuccessCount:删除成功的数量  addedSuccessCount:添加成功的数量
                Long deletedSuccessCount = quotaInfoDao.findCountByDateIdAndReportTypeAndResult(dateId, "100003", "上报成功");
                Long addedSuccessCount = quotaInfoDao.findCountByDateIdAndReportTypeAndResult(dateId, "100001", "上报成功");
                boolean canInsertFlag = deletedSuccessCount >= addedSuccessCount && addedSuccessCount > 0;
                if (!canInsertFlag) {
                    continue;
                }
            }
            //根据dateId查询合同信息
            QuotaInfo quotaInfo = quotaInfoDao.findQuotaInfoByDateId(dateId);
            //设置数据类型
            quotaInfo.setDataType("QUOTA_INFO");

            //设置币种,人民币:730001
            quotaInfo.setCcy("730001");
            //设置合同状态,有效 :490001
            quotaInfo.setContractStatus("490001");
            //设置客户经理
            quotaInfo.setRelationManager("渠道来源");


            //需要判断同一个循环授信合同号是否是第一次放款,如果是第二次及其以后的放款,需要修改为更新还款记录
            boolean isFirstRevolvingCredit = PettyLoanContractService.findIsFirstIssue(Integer.valueOf(dateId), quotaInfo.getContractNo());
            if (isFirstRevolvingCredit) {
                //设置上报类型,初始保存时,默认为新增:100001
                quotaInfo.setReportType("100001");
            } else {
                //如果不是第一次发放,需要设置为更新:100002
                quotaInfo.setReportType("100002");
            }
            //设置组织机构代码
            quotaInfo.setOrgCode(OrgCode.getOrgCode());
            //设置是否申报,0:否,1:是
            quotaInfo.setIsSend(0);
            //设置记录保存时间
            quotaInfo.setInsertDate(new Date());
            //是否最新版本
            quotaInfo.setIsLast("Y");


            //校验属性值是否为空
            validate(quotaInfo.getContractNoQuery(), quotaInfo);

            list.add(quotaInfo);
        }
        //插入0个记录，会抛异常
        if (list.size() > 0) {
            quotaInfoDao.batchSaveQuotaInfo(list);
        }

    }

    @Override
    public PageBean findQuotaInfoByDateFromBizSys(String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return quotaInfoDao.findQuotaInfoByDateFromBizSys(signStartDate, signEndDate, pageBean);
    }

    @Override
    public PageBean findQuotaBriefInfoByContractNo(String contractNoQuery, PageBean pageBean) throws DAOException {

        return quotaInfoDao.findQuotaBriefInfoByContractNo(contractNoQuery, pageBean);
    }

    @Override
    public PageBean findQuotaInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return quotaInfoDao.findQuotaInfoBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
    }

    @Override
    public QuotaInfo findQuotaInfoById(String id) throws DAOException {
        return quotaInfoDao.findQuotaInfoById(id);
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
        for (String dateId : idsArr) {
            try {
                QuotaInfo quotaInfo = null;
                //查询已有记录,将是否为最新版本设置为N
                List<QuotaInfo> list = quotaInfoDao.findQuotaInfoListByDateId(dateId);
                for (QuotaInfo quotaInfoNode : list) {
                    if ("100003".equals(quotaInfoNode.getReportType()) && quotaInfoNode.getIsSend() == 0) {

                        throw new BaseException("合同编号为:" + quotaInfoNode.getContractNoQuery() + ",此记录已设置申报删除,无需再次设置");
                    }
                    if (!"N".equals(quotaInfoNode.getIsLast())) {
                        quotaInfo = quotaInfoNode;
                        quotaInfoNode.setIsLast("N");
                    }
                }
                quotaInfoDao.batchUpdateQuotaoInfo(list, true);
                quotaInfo.setId(null);
                //设置组织机构代码
                quotaInfo.setOrgCode((OrgCode.getOrgCode()));
                //设置申报状态为未申报,0:否,1:是
                quotaInfo.setIsSend(0);
                //设置记录保存时间
                quotaInfo.setInsertDate(new Date());
                quotaInfo.setIsLast("Y");
                //batchNo设置为空
                quotaInfo.setBatchNo(null);

                //校验属性值是否为空
                validate(quotaInfo.getContractNoQuery(), quotaInfo);
                //设置上报类型为修改记录
                quotaInfo.setReportType("100003");
                quotaInfoDao.saveOrUpdate(quotaInfo);

            } catch (BaseException e) {
                LOGGER.debug("QuotaInfoServiceImpl:deleteRecord() " + e.getLocalizedMessage());
                return ResultModel.fail(e.getLocalizedMessage());
            }
        }
        return ResultModel.ok();
    }

    @Override
    public ResultModel setNotSend(String ids) throws DAOException {
        try {
            String[] idsArr = ids.split(",");
            ArrayList<QuotaInfo> quotaInfoArrayList = new ArrayList<>();
            for (String id : idsArr) {
                QuotaInfo quotaInfoById = quotaInfoDao.findQuotaInfoById(id);
                quotaInfoById.setIsSend(0);
                quotaInfoById.setBatchNo(null);
                quotaInfoArrayList.add(quotaInfoById);
            }
            quotaInfoDao.batchUpdateQuotaoInfo(quotaInfoArrayList, true);
        } catch (DAOException e) {
            LOGGER.debug("QuotaInfoServiceImpl:setNotSend() " + e.getLocalizedMessage());
            return ResultModel.fail();
        }
        return ResultModel.ok();
    }

    /**
     * @param sendStatusCode 申报状态
     * @param contractNo     jk合同号
     * @param startDate      签署日期
     * @param endDate        签署日期
     * @param pageBean       分页信息
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastQuotaInfoBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException {
        return quotaInfoDao.findLastQuotaInfoBySendStatus(sendStatusCode, contractNo, startDate, endDate, pageBean);
    }

    private void validate(String contractNo, QuotaInfo quotaInfo) {
        if (StringUtils.isBlank(quotaInfo.getContractNo())) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的额度协议编号为空");
        }
        if (StringUtils.isBlank(quotaInfo.getCustomerType())) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人类别为空");
        }
        if (StringUtils.isBlank(quotaInfo.getCustomerName())) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人名称为空");
        }
        if (StringUtils.isBlank(quotaInfo.getCertificateType())) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的证件类型为空");
        }
        if (StringUtils.isBlank(quotaInfo.getCertificateNo())) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的证件号码为空");
        }

        if (null == quotaInfo.getContractSignDate()) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的合同签订日期为空");
        }
        if (null == quotaInfo.getContractBeginDate()) {
            LOGGER.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的合同有效起始日期为空");
        }
        if (null == quotaInfo.getContractEndDate()) {
            LOGGER.error("批量插入贷款合同信息：合同编号：" + contractNo + "的合同有效结束日期为空");
        }
        if (null == quotaInfo.getContractAmount()) {
            LOGGER.error("批量插入贷款合同信息：合同编号：" + contractNo + "的放款金额为空");
        }
    }

}
