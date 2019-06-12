package com.global.fems.business.service.impl;

import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.dao.RepayInfoDao;
import com.global.fems.business.domain.RepayInfo;
import com.global.fems.business.service.CommonService;
import com.global.fems.business.service.RepayInfoService;
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

import java.util.*;

/**
 * 贷款回收信息管理Service
 */
@Service
public class RepayInfoServiceImpl implements RepayInfoService {
    private Logger logger = LoggerFactory.getLogger(ContractIssueInfoServiceImpl.class);
    @Autowired
    private RepayInfoDao repayInfoDao;
    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;


    /**
     * 根据还款日期/合同号从业务系统查询还款信息
     *
     * @param repayStartDate
     * @param repayEndDate
     * @param contractNo
     * @param pageBean
     * @return
     */
    @Override
    public PageBean findRepayInfoByRepayDateAndContractNoFromBizSys(String repayStartDate, String repayEndDate, String contractNo, PageBean pageBean) throws DAOException {
        PageBean result = repayInfoDao.findRepayInfoByRepayDateAndContractNoFromBizSys(repayStartDate, repayEndDate, contractNo, pageBean);
        for (RepayInfo repayInfo : (List<RepayInfo>) result.getDataList()) {
            setRealQuotaNo(repayInfo);
        }
        return result;
    }


    /**
     * 批量保存还款信息
     *
     * @param ids Date_还款登记表的id
     * @throws DAOException
     */
    @Override
    public void batchSaveRepayInfo(String ids) throws DAOException {
        String[] idsArr = ids.split(",");
        TreeSet<RepayInfo> treeSet = new TreeSet<>((oldRepayInfo, newRepayInfo) -> {
            if (oldRepayInfo.getDateId().equals(newRepayInfo.getDateId()) && oldRepayInfo.getCounter().equals(newRepayInfo.getCounter())
                    && oldRepayInfo.getRepayDate().equals(newRepayInfo.getRepayDate())) {
                return 0;
            } else if (oldRepayInfo.getDateId() > newRepayInfo.getDateId()) {
                return 1;
            } else {
                return -1;
            }

        });
        for (String id : idsArr) {
            RepayInfo repayInfo = repayInfoDao.findRepayInfoByIdFromBizSys(id);
            boolean isAdd = treeSet.add(repayInfo);
            if (isAdd) {
                List<RepayInfo> existRepayInfoList = repayInfoDao.findRepayInfoListByDateIdAndCounter(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate());
                if (existRepayInfoList != null && existRepayInfoList.size() > 0) {
                    //如果成功删除的记录数=成功增加的记录数量,并且成功增加的记录数>0,允许新增,其余情况不允许再向数据库插入新数据
                    //deletedSuccessCount:删除成功的数量  addedSuccessCount:添加成功的数量
                    Long deletedSuccessCount = repayInfoDao.findCountByDateIdAndCounterAndReportTypeAndResult(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate(), "100003", "上报成功");
                    Long addedSuccessCount = repayInfoDao.findCountByDateIdAndCounterAndReportTypeAndResult(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate(), "100001", "上报成功");
                    boolean canInsertFlag = deletedSuccessCount >= addedSuccessCount && addedSuccessCount > 0;
                    if (!canInsertFlag) {
                        treeSet.remove(repayInfo);
                        continue;
                    }
                }
                //设置实还本金,实还利息.findRepayInfoByIdFromBizSys()查询的结果不能合并同期同日多条记录;设置回收利息:提前结清时有违约金,违约金加到回收利息中
                setRepayPrincipalInterest(repayInfo);
                setRealQuotaNo(repayInfo);
                int totalCounter = repayInfoDao.findTotalCounter(repayInfo.getDateId());
                //设置总期数
                repayInfo.setTotalCounter(Integer.toString(totalCounter));
                //将证件号字母转换为大写
                repayInfo.setCertificateNo(repayInfo.getCertificateNo().toUpperCase());
                //设置上报类型,初始保存时,默认为新增:100001
                repayInfo.setReportType("100001");
                //设置扣款方式:银联代扣430002
                repayInfo.setGatherMode("430002");
                //设置组织机构代码
                repayInfo.setOrgCode(OrgCode.getOrgCode());
                //设置是否申报,0:否,1:是
                repayInfo.setIsSend(0);
                //设置记录保存时间
                repayInfo.setInsertDate(new Date());
                //是否最新版本
                repayInfo.setIsLast("Y");
                //设置数据类型
                repayInfo.setDataType("REPAY_INFO");
                // 校验值是否为空
                validate(repayInfo.getContractNo(), repayInfo);
            }
        }
        if (treeSet.size() > 0) {
            repayInfoDao.batchSaveContract(new ArrayList(treeSet));

        }

    }

    private void setRealQuotaNo(RepayInfo repayInfo) {
        if (StringUtils.equals(repayInfo.getIsRealQuotaLoan(), "740001")) {
            /*
             * 740001为循环授信,需要判断合同编号是否为"循环授信合同编号-n"格式,其中首次提款时,不需要添加,从第二次提款起,每次添加的
             * n为相同循环授信合同编号提款次数-1.例如,循环授信合同编号A,第一次提款时,上报合同编号为"A",第3次提款,此时上报的合同编号为"A-2"
             */
            repayInfo.setRealQuotaNo(CommonService.getRealQuotaNo(repayInfo.getDateId(), repayInfo.getRealQuotaNo()));
        }
    }

    private void setRepayPrincipalInterest(RepayInfo repayInfo) {
        String counter = repayInfo.getCounter();
        String repayMode = repayInfoDao.findRepayModeByDateId(repayInfo.getDateId());
        //查询时,这两种还款方式数据库中还款期数是从0开始计算,根据Date_Id查询时加了1,此时需要减1才能与数据库数据匹配
        if ("1835".equals(repayMode) || "818".equals(repayMode)) {
            counter = (Integer.parseInt(counter) - 1) + "";
        }
        Map map = repayInfoDao.findRepayPrincipalInterest(repayInfo.getDateId(), counter, repayInfo.getRepayDate());
        String repay_pri_amt = map.get("repay_pri_amt") == null ? "0" : map.get("repay_pri_amt") + "";
        String repay_int_amt = map.get("repay_int_amt") == null ? "0" : map.get("repay_int_amt") + "";
        if (Integer.parseInt(repayInfo.getDelayDays()) > 0) {
            //如果逾期将实还本金和实还利息赋值给逾期本金,逾期利息
            repayInfo.setDelayAmt(repay_pri_amt);
            repayInfo.setDelayInterest(repay_int_amt);
            repayInfo.setRepayPriAmt("0");
            repayInfo.setRepayIntAmt("0");
        } else {
            //如果没有逾期将实还本金和实还利息赋值给收回本金,收回利息
            repayInfo.setDelayAmt("0");
            repayInfo.setDelayInterest("0");
            repayInfo.setRepayPriAmt(repay_pri_amt);
            repayInfo.setRepayIntAmt(repay_int_amt);
        }

        //设置回收利息:提前结清时有违约金,违约金加到回收利息中
        Float penalty1 = repayInfoDao.findPenaltyPrincipalInterest(repayInfo.getDateId(), counter, repayInfo.getRepayDate());
        Float penalty2 = repayInfoDao.findPenaltyServiceFee(repayInfo.getDateId(), counter, repayInfo.getRepayDate());
        Double repayIntAmt = Double.parseDouble(repayInfo.getRepayIntAmt()) + Double.parseDouble(String.valueOf(penalty1)) + Double.parseDouble(String.valueOf(penalty2));
        repayInfo.setRepayIntAmt(String.valueOf(repayIntAmt));
    }

    /**
     * 根据Date_还款登记表的id查询还款信息
     *
     * @param id Date_还款登记表的id
     * @return
     * @throws DAOException
     */
    @Override
    public RepayInfo findRepayInfoByIdFromBizSys(String id) throws DAOException {
        RepayInfo repayInfo = repayInfoDao.findRepayInfoByIdFromBizSys(id);
        setRealQuotaNo(repayInfo);
        setRepayPrincipalInterest(repayInfo);
        //设置扣款方式:银联代扣430002
        repayInfo.setGatherMode("430002");
        int totalCounter = repayInfoDao.findTotalCounter(repayInfo.getDateId());
        //设置总期数
        repayInfo.setTotalCounter(Integer.toString(totalCounter));
        //将证件号字母转换为大写
        repayInfo.setCertificateNo(repayInfo.getCertificateNo().toUpperCase());
        return repayInfo;
    }

    /**
     * 保存或更新
     *
     * @param repayInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(RepayInfo repayInfo) throws DAOException {
        try {
            if (StringUtil.isNullOrEmpty(repayInfo.getDateId())) {
                //如果没有dateId,设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(repayInfo.getContractNo());
                repayInfo.setDateId(dateId);
            }

            if (repayInfo.getIsSend() != null && repayInfo.getIsSend() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            //判断是否已经录入
            List<RepayInfo> existRepayInfoInfoList = repayInfoDao.findRepayInfoListByDateIdAndCounter(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate());
            if (existRepayInfoInfoList != null && existRepayInfoInfoList.size() > 0) {
                for (RepayInfo node : existRepayInfoInfoList) {
                    if ("Y".equals(node.getIsLast()) && node.getIsSend() == 0) {
                        if (StringUtil.isNullOrEmpty(repayInfo.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                    node.setIsLast("N");
                }
                repayInfoDao.batchUpdateRepayInfo(existRepayInfoInfoList, true);
            }

            //设置上报类型,初始保存时,默认为新增:100001
            repayInfo.setReportType("100001");
            //设置组织机构代码
            repayInfo.setOrgCode((OrgCode.getOrgCode()));
            //设置是否发送,0:否,1:是
            repayInfo.setIsSend(0);
            //设置数据类型
            repayInfo.setDataType("REPAY_INFO");

            if (StringUtil.isNullOrEmpty(repayInfo.getInsertDate())) {
                //设置记录保存时间
                repayInfo.setInsertDate(new Date());
            }
            //设置是否是最新记录
            repayInfo.setIsLast("Y");

            repayInfoDao.saveOrUpdate(repayInfo);
        } catch (Exception e) {
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    /**
     * 已申报记录更新
     *
     * @param repayInfo
     * @throws DAOException
     */
    @Override
    public void declaredUpdate(RepayInfo repayInfo) throws DAOException {
        //查询已有记录,将是否为最新版本设置为N
        List<RepayInfo> list = repayInfoDao.findRepayInfoListByDateIdAndCounter(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate());
        for (RepayInfo repayInfoNode : list) {
            if ("100003".equals(repayInfoNode.getReportType()) && repayInfoNode.getIsSend() == 0) {
                throw new DAOException("当前记录已存在申报删除未上报,不能进行已申报合同信息修改");
            }
            if (!"N".equals(repayInfoNode.getIsLast())) {
                repayInfoNode.setIsLast("N");
            }
        }
        repayInfoDao.batchUpdateRepayInfo(list, true);
        repayInfo.setId(null);
        //设置组织机构代码
        repayInfo.setOrgCode((OrgCode.getOrgCode()));
        //设置申报状态为未申报,0:否,1:是
        repayInfo.setIsSend(0);
        //设置记录保存时间
        repayInfo.setInsertDate(new Date());
        repayInfo.setIsLast("Y");
        //校验属性值是否为空
        validate(repayInfo.getContractNo(), repayInfo);
        //设置上报类型为修改记录
        repayInfo.setReportType("100002");
        //设置数据类型
        repayInfo.setDataType("REPAY_INFO");
        repayInfoDao.saveOrUpdate(repayInfo);
    }


    /**
     * 根据合同编号从DC_REPAY_INFO查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        return repayInfoDao.findBriefInfoByContractNo(contractNo.trim(), pageBean);
    }

    /**
     * 根据DC_REPAY_INFO主键从DC_REPAY_INFO查询合同信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public RepayInfo findRepayInfoById(String id) throws DAOException {

        return repayInfoDao.findRepayInfoById(id);
    }

    /**
     * 根据还款日期和发送状态查询还款信息
     *
     * @param sendStatusCode
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findRepayInfoBySendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {

        return repayInfoDao.findRepayInfoBySendStatus(sendStatusCode, repayStartDate, repayEndDate, pageBean);
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
                RepayInfo repayInfo = repayInfoDao.findRepayInfoById(id);
                repayInfo.setId(null);
                //设置申报状态为未申报,0:否,1:是
                repayInfo.setIsSend(0);
                //设置记录保存时间
                repayInfo.setInsertDate(new Date());
                repayInfo.setIsLast("Y");
                //校验属性值是否为空
                validate(repayInfo.getContractNo(), repayInfo);
                //设置上报类型为修改记录
                repayInfo.setReportType("100003");
                //设置batch_no为空
                repayInfo.setBatchNo(null);

                //查询已有记录,将是否为最新版本设置为N
                List<RepayInfo> list = repayInfoDao.findRepayInfoListByDateIdAndCounter(repayInfo.getDateId(), repayInfo.getCounter(), repayInfo.getRepayDate());
                if (list != null && list.size() > 0) {
                    for (RepayInfo repayInfoNode : list) {
                        if ("100003".equals(repayInfoNode.getReportType())) {
                            throw new DAOException("合同编号为:" + repayInfoNode.getContractNo() + ",此记录第" + repayInfoNode.getCounter() + "期已设置申报删除,无需再次设置");
                        }
                        if ("N".equals(repayInfoNode.getIsLast())) {
                            continue;
                        } else {
                            repayInfoNode.setIsLast("N");
                        }
                    }
                    repayInfoDao.batchUpdateRepayInfo(list, true);
                }
                repayInfoDao.saveOrUpdate(repayInfo);
            } catch (DAOException e) {
                logger.debug("RepayInfoServiceImpl:deleteRecord()", e.getLocalizedMessage());
                return ResultModel.fail(e.getLocalizedMessage());
            }
        }
        return ResultModel.ok();
    }

    /**
     * 根据还款日期和发送状态查询最新版本的还款信息
     *
     * @param sendStatusCode
     * @param contractNo
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastRepayInfoSendStatus(String sendStatusCode, String contractNo, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        return repayInfoDao.findLastRepayInfoSendStatus(sendStatusCode, contractNo, repayStartDate, repayEndDate, pageBean);
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
            String[] idsArr = ids.split(",");
            ArrayList<RepayInfo> repayInfoArrayList = new ArrayList<>();
            for (String id : idsArr) {
                RepayInfo repayInfoById = repayInfoDao.findRepayInfoById(id);
                repayInfoById.setIsSend(0);
                repayInfoById.setBatchNo(null);
                repayInfoArrayList.add(repayInfoById);
            }
            repayInfoDao.batchUpdateRepayInfo(repayInfoArrayList, true);
        } catch (DAOException e) {
            logger.debug("RepayInfoServiceImpl:setNotSend()", e.getLocalizedMessage());
            return ResultModel.fail();
        }
        return ResultModel.ok();
    }


    private void validate(String contractNo, RepayInfo repayInfo) {
        if (StringUtils.isBlank(repayInfo.getRepayDate())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的还款日期为空");
        }
        if (StringUtils.isBlank(repayInfo.getCounter())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的还款期数为空");
        }

        if (StringUtils.isBlank(repayInfo.getCustomerType())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人类别为空");
        }
        if (StringUtils.isBlank(repayInfo.getCustomerName())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人姓名为空");
        }
        if (StringUtils.isBlank(repayInfo.getCertificateType())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人证件类别为空");
        }
        if (StringUtils.isBlank(repayInfo.getCertificateNo())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人证件号码为空");
        }
        if (StringUtils.isBlank(repayInfo.getRepayPriAmt())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的收回本金为空");
        }
        if (StringUtils.isBlank(repayInfo.getStartDate())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的起息日期为空");
        }
        if (StringUtils.isBlank(repayInfo.getEndDate())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的止息日期为空");
        }
        if (StringUtils.isBlank(repayInfo.getReceiptType())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的回收类型为空");
        }
    }
}
