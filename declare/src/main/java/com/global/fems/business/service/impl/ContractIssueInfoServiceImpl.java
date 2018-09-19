package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractIssueInfoDao;
import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.ContractIssueInfo;
import com.global.fems.business.enums.IndustryEnum;
import com.global.fems.business.enums.RateCalcModeEnum;
import com.global.fems.business.enums.ZoneEnum;
import com.global.fems.business.service.ContractIssueInfoService;
import com.global.fems.message.util.OrgCode;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.util.StringUtil;
import com.global.framework.util.SysUtils;
import com.global.param.domain.ResultModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷款放款信息管理Service接口实现类
 */
@Service
public class ContractIssueInfoServiceImpl implements ContractIssueInfoService {
    private Logger logger = LoggerFactory.getLogger(ContractIssueInfoServiceImpl.class);


    @Autowired
    private ContractIssueInfoDao contractIssueInfoDao;
    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    /**
     * 根据合同编号和申报状态从DC_CONTRACT_INFO中查询合同信息
     *
     * @param contractNo
     * @param sendStatus
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractByContractNoFromContractInfo(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {

        return contractIssueInfoDao.findContractByContractNoFromContractInfo(contractNo, sendStatus, pageBean);
    }

    /**
     * 根据dateId从DC_CONTRACT_INFO,已放款客户表,Data_WorkInfo查询贷款合同信息,批量插入到DC_CONTRACT_ISSUE_INFO中
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void batchSaveContract(String ids) throws DAOException {
        String[] idsArr = ids.split(",");
        List<ContractIssueInfo> list = new ArrayList<ContractIssueInfo>();
        a:
        for (String dateId : idsArr) {
            List<ContractIssueInfo> existContractIssueInfoList = contractIssueInfoDao.findContractListByDateId(dateId);
            if (existContractIssueInfoList != null && existContractIssueInfoList.size() > 0) {//如果存在且上报类型不是删除，跳过,避免重复插入
                Boolean isDelete = false; //是否删除的标记
                for (ContractIssueInfo contractIssueInfo : existContractIssueInfoList) {
                    if (!"100003".equals(contractIssueInfo.getReportType())) {
                        isDelete = true;
                        break;
                    } else if (contractIssueInfo.getIsSend() == 0 && "Y".equals(contractIssueInfo.getIsLast())) {
                        continue a;
                    }

                }
                if (!isDelete) { //已经被删除时,允许保存
                    continue;
                }
            }

            //根据dateId查询贷款发放信息
            ContractIssueInfo contractIssueInfo = contractIssueInfoDao.findContractByDateId(dateId);
            //设置投放区域,贷款期限,投放行业,计息方式,利率性质,扣款方式,五级分类,发放状态
            setFieldValue(contractIssueInfo);
            //设置上报类型,初始保存时,默认为新增:100001
            contractIssueInfo.setReportType("100001");
            //设置组织机构代码
            contractIssueInfo.setOrgCode(OrgCode.getOrgCode());
            //设置是否申报,0:否,1:是
            contractIssueInfo.setIsSend(0);
            //设置记录保存时间
            contractIssueInfo.setInsertDate(new Date());
            //是否最新版本
            contractIssueInfo.setIsLast("Y");
            //设置数据类型
            contractIssueInfo.setDataType("ISSUE_INFO");
            // 校验值是否为空
            validate(contractIssueInfo.getContractNo(), contractIssueInfo);

            list.add(contractIssueInfo);
        }
        if (list.size() > 0) {
            contractIssueInfoDao.batchSaveContract(list);
        }
    }

    /**
     * 已申报修改贷款放款信息
     *
     * @param contractIssueInfo
     * @throws DAOException
     */
    @Override
    public void declaredUpdate(ContractIssueInfo contractIssueInfo) throws DAOException {
        //查询已有记录,将是否为最新版本设置为N
        List<ContractIssueInfo> list = contractIssueInfoDao.findContractIssueInfoListByDateId(contractIssueInfo.getDateId() + "");
        for (ContractIssueInfo contractInfoNode : list) {
            if ("100003".equals(contractInfoNode.getReportType()) && contractInfoNode.getIsSend() == 0) {
                throw new DAOException("当前记录已申报删除,不能进行已申报合同信息修改");
            }
            if ("N".equals(contractInfoNode.getIsLast())) {
                continue;
            } else {
                contractInfoNode.setIsLast("N");
            }
        }
        contractIssueInfoDao.batchUpdateContract(list, true);
        contractIssueInfo.setId(null);
        //设置组织机构代码
        contractIssueInfo.setOrgCode((OrgCode.getOrgCode()));
        //设置申报状态为未申报,0:否,1:是
        contractIssueInfo.setIsSend(0);
        //设置记录保存时间
        contractIssueInfo.setInsertDate(new Date());
        contractIssueInfo.setIsLast("Y");
        //校验属性值是否为空
        validate(contractIssueInfo.getContractNo(), contractIssueInfo);
        //设置上报类型为修改记录
        contractIssueInfo.setReportType("100002");
        //设置数据类型
        contractIssueInfo.setDataType("ISSUE_INFO");
        //设置batch_no为空
        contractIssueInfo.setBatchNo(null);
        if (StringUtils.isBlank(contractIssueInfo.getExtStartDate())) {
            //空串保存时,数据库中的日期为:1900-01-01 00:00:00:000
            contractIssueInfo.setExtStartDate(null);
        }
        if (StringUtils.isBlank(contractIssueInfo.getExtEndDate())) {
            //空串保存时,数据库中的日期为:1900-01-01 00:00:00:000
            contractIssueInfo.setExtEndDate(null);
        }

        contractIssueInfoDao.saveOrUpdate(contractIssueInfo);


    }

    /**
     * 保存或更新贷款放款信息
     *
     * @param contractIssueInfo
     * @throws DAOException
     */
    @Override
    public void saveOrUpdate(ContractIssueInfo contractIssueInfo) throws DAOException {
        try {
            if (StringUtil.isNullOrEmpty(contractIssueInfo.getDateId())) {
                //设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(contractIssueInfo.getContractNo());
                contractIssueInfo.setDateId(dateId);
            }
            if (contractIssueInfo.getIsSend() != null && contractIssueInfo.getIsSend() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            //判断是否已经录入
            List<ContractIssueInfo> existContractList = contractIssueInfoDao.findContractListByDateId(contractIssueInfo.getDateId() + "");
            if (existContractList != null && existContractList.size() > 0) {
                for (ContractIssueInfo node : existContractList) {
                    if ("Y".equals(node.getIsLast()) && node.getIsSend() == 0) {
                        if (StringUtil.isNullOrEmpty(contractIssueInfo.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                    node.setIsLast("N");
                }
                contractIssueInfoDao.batchUpdateContract(existContractList, true);
            }
            if (StringUtils.isBlank(contractIssueInfo.getExtStartDate())) {
                //空串保存时,数据库中的日期为:1900-01-01 00:00:00:000
                contractIssueInfo.setExtStartDate(null);
            }
            if (StringUtils.isBlank(contractIssueInfo.getExtEndDate())) {
                //空串保存时,数据库中的日期为:1900-01-01 00:00:00:000
                contractIssueInfo.setExtEndDate(null);
            }
            //设置上报类型,初始保存时,默认为新增:100001
            contractIssueInfo.setReportType("100001");
            //设置组织机构代码
            contractIssueInfo.setOrgCode((OrgCode.getOrgCode()));
            //设置是否发送,0:否,1:是
            contractIssueInfo.setIsSend(0);
            //设置数据类型
            contractIssueInfo.setDataType("ISSUE_INFO");

            if (StringUtil.isNullOrEmpty(contractIssueInfo.getInsertDate())) {
                //设置记录保存时间
                contractIssueInfo.setInsertDate(new Date());
            }
            //设置是否是最新记录
            contractIssueInfo.setIsLast("Y");
            contractIssueInfoDao.saveOrUpdate(contractIssueInfo);
        } catch (Exception e) {
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    /**
     * 根据申报状态查询和签约日期查询贷款放款信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractIssueInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return contractIssueInfoDao.findContractIssueInfoBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
    }


    /**
     * 根据申报状态查询和签约日期查询贷款放款信息
     *
     * @param sendStatusCode
     * @param contractNo
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return contractIssueInfoDao.findLastContractBySendStatus(sendStatusCode, contractNo, signStartDate, signEndDate, pageBean);
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
            ArrayList<ContractIssueInfo> contractIssueInfoArrayList = new ArrayList<>();
            for (String id : idsArr) {
                ContractIssueInfo contractIssueInfo = contractIssueInfoDao.findContractIssueInfoById(id);
                contractIssueInfo.setIsSend(0);
                contractIssueInfo.setBatchNo(null);
                contractIssueInfoArrayList.add(contractIssueInfo);
            }
            contractIssueInfoDao.batchUpdateContract(contractIssueInfoArrayList, true);
        } catch (DAOException e) {
            logger.debug("ContractIssueInfoServiceImpl:setNotSend()", e.getLocalizedMessage());
            return ResultModel.fail();
        }
        return ResultModel.ok();
    }


    /**
     * 根据id查询贷款放款信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public ContractIssueInfo findContractIssueInfoById(String id) throws DAOException {
        return contractIssueInfoDao.findContractIssueInfoById(id);
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
                ContractIssueInfo contractIssueInfo = null;
                //查询已有记录,将是否为最新版本设置为N
                List<ContractIssueInfo> list = contractIssueInfoDao.findContractListByDateId(dateId);
                if (list != null && list.size() > 0) {
                    for (ContractIssueInfo contractInfoNode : list) {
                        if ("100003".equals(contractInfoNode.getReportType())) {
                            throw new DAOException("合同编号为:" + contractInfoNode.getContractNo() + ",此记录已设置申报删除,无需再次设置");
                        }
                        if ("N".equals(contractInfoNode.getIsLast())) {
                            continue;
                        } else {
                            contractIssueInfo = contractInfoNode;
                            contractInfoNode.setIsLast("N");
                        }
                    }
                    contractIssueInfoDao.batchUpdateContract(list, true);
                }
                contractIssueInfo.setId(null);
                //设置组织机构代码
                contractIssueInfo.setOrgCode((OrgCode.getOrgCode()));
                //设置申报状态为未申报,0:否,1:是
                contractIssueInfo.setIsSend(0);
                //设置记录保存时间
                contractIssueInfo.setInsertDate(new Date());
                contractIssueInfo.setIsLast("Y");
                //校验属性值是否为空
                validate(contractIssueInfo.getContractNo(), contractIssueInfo);
                //设置上报类型为删除记录
                contractIssueInfo.setReportType("100003");
                contractIssueInfoDao.saveOrUpdate(contractIssueInfo);


            } catch (DAOException e) {
                logger.debug("ContractIssueInfoServiceImpl:deleteRecord()", e.getLocalizedMessage());
                return ResultModel.fail(e.getLocalizedMessage());
            }
        }
        return ResultModel.ok();
    }

    /**
     * 根据合同号从DC_CONTRACT_ISSUE_INFO查询贷款放款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        return contractIssueInfoDao.findContractBriefInfoByContractNo(contractNo, pageBean);
    }

    /**
     * 根据申报状态查询和签约日期查询贷款放款信息,申报时使用.
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        return contractIssueInfoDao.findContractBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
    }

    /**
     * 设置投放区域,贷款期限,投放行业,计息方式,利率性质,扣款方式,五级分类,发放状态
     *
     * @param contractIssueInfo
     */

    private void setFieldValue(ContractIssueInfo contractIssueInfo) {
        //设置数据类型
        contractIssueInfo.setDataType("ISSUE_INFO");
        //投放区域
        String zone = ZoneEnum.getCodeByZone(contractIssueInfo.getZone());
        if (StringUtil.isNullOrEmpty(zone)) {
            //如果没有匹配的区域信息,设置为其它
            zone = "230044";
        }
        contractIssueInfo.setZone(zone);
        //贷款期限
        Long term = calTerm(contractIssueInfo);
        if (term >= 0) {
            if (term <= 1) {
                //(0,1]个月内
                contractIssueInfo.setTerm("250001");
            } else if (term <= 3) {
                //(1,3]个月内
                contractIssueInfo.setTerm("250002");
            } else if (term <= 6) {
                //(3,6]个月内
                contractIssueInfo.setTerm("250003");
            } else if (term <= 12) {
                //(6,12]个月内
                contractIssueInfo.setTerm("250004");
            } else {
                //(6,+∞]个月内
                contractIssueInfo.setTerm("250005");
            }
        } else {
            throw new DAOException("合同编号:" + contractIssueInfo.getContractNo() + "的贷款期限存在问题");
        }
        // 投放行业
        String industry = IndustryEnum.getCodeByIndustry(contractIssueInfo.getIndustry());
        if (StringUtil.isNullOrEmpty(industry)) {
            //如果没有匹配的行业,设置为其他
            industry = "290009";
        }
        contractIssueInfo.setIndustry(industry);
        // 计息方式
        String rateCalMode = RateCalcModeEnum.getCodeByRateCalMode(contractIssueInfo.getRateCalcMode());
        if (StringUtil.isNullOrEmpty(rateCalMode)) {
            //如果没有匹配的计息方式,设置为等额本息
            rateCalMode = "270006";
        }
        contractIssueInfo.setRateCalcMode(rateCalMode);
        //设置利率性质:固定利率520001
        contractIssueInfo.setRateType("520001");
        //设置扣款方式:银联代扣430002
        contractIssueInfo.setRepayMode("430002");
        //设置五级分类:正常510001
        contractIssueInfo.setRiskLevel("510001");
        //设置发放状态:已放款540002
        contractIssueInfo.setIssueStatus("540002");

    }


    /**
     * 根据dateId查询贷款放款信息
     *
     * @param dateId
     * @return
     * @throws DAOException
     */
    @Override
    public ContractIssueInfo findContractByDateId(String dateId) throws DAOException {
        ContractIssueInfo contractIssueInfo = contractIssueInfoDao.findContractByDateId(dateId);
        setFieldValue(contractIssueInfo);
        return contractIssueInfo;
    }

    /**
     * 计算贷款期限,并设置
     *
     * @param contractIssueInfo
     */
    private Long calTerm(ContractIssueInfo contractIssueInfo) {
        String ddDateStr = getDateStr(contractIssueInfo.getDdDate());
        String matureDateStr = getDateStr(contractIssueInfo.getMatureDate());
        LocalDate ddDate = LocalDate.parse(ddDateStr);
        LocalDate matureDate = LocalDate.parse(matureDateStr);
        long term = ddDate.until(matureDate, ChronoUnit.MONTHS);
        return term;
    }


    private String getDateStr(String dateStr) {

        Date strToDate = SysUtils.getStrToDate(dateStr, "yyyy-MM-dd HH:mm:ss.S");
        String dateToStr = SysUtils.getDateToStr(strToDate, "yyyy-MM-dd");
        return dateToStr;
    }

    private void validate(String contractNo, ContractIssueInfo contractIssueInfo) {
        if (StringUtils.isBlank(contractIssueInfo.getZone())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的投放区域为空");
        }
        if (StringUtils.isBlank(contractIssueInfo.getTerm())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的贷款期限为空");
        }

        if (StringUtils.isBlank(contractIssueInfo.getPurpose())) {
            logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的贷款用途为空");
        }
    }

}
