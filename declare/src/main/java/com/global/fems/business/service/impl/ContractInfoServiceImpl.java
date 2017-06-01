package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.exception.BaseException;
import com.global.framework.util.StringUtil;
import com.global.param.domain.ResultModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷款合同信息管理Service接口实现类
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ContractInfoServiceImpl.class);
    @Autowired
    private ContractInfoDao contractInfoDao;

    /**
     * 保存或更新贷款合同信息,这些合同是未申报的
     *
     * @param contractInfoCycleNode
     * @return
     */
    public ResultModel saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws BaseException {

        try {
            if (contractInfoCycleNode.getIsSend() != null && contractInfoCycleNode.getIsSend() != 0) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            //设置上报类型,初始保存时,默认为新增:100001
            contractInfoCycleNode.setReportType("100001");
            //设置组织机构代码
            contractInfoCycleNode.setOrgCode("91500000584252884K");
            //设置是否发送,0:否,1:是
            contractInfoCycleNode.setIsSend(0);
            if (StringUtil.isNullOrEmpty(contractInfoCycleNode.getInsertDate())) {
                //设置记录保存时间
                contractInfoCycleNode.setInsertDate(new Date());
            }
            //设置是否是最新记录
            contractInfoCycleNode.setIsLast("Y");
            contractInfoDao.saveOrUpdate(contractInfoCycleNode);
            return ResultModel.ok();
        } catch (DAOException e) {
            return ResultModel.fail();
        }
    }


    /**
     * 根据合同号从DC_PETTY_LOAN_CONTRACT,已放款客户表,Data_WorkInfo查询贷款合同信息,批量插入到DC_CONTRACT_INFO中
     *
     * @param contractNos
     * @throws BaseException
     */
    public void batchSaveContract(String contractNos) throws DAOException {
        String[] contractNoArr = contractNos.split(",");

        List<ContractInfoCycleNode> list = new ArrayList<ContractInfoCycleNode>();
        for (String contractNo : contractNoArr) {

            List<ContractInfoCycleNode> existContractList = contractInfoDao.findContractListByContractNo(contractNo);
            if (existContractList != null && existContractList.size() > 0) {//如果存在，跳过,避免重复插入
                continue;
            }
            //根据合同号查询合同信息
            ContractInfoCycleNode contractInfoCycleNode = contractInfoDao.findContractByContractNoFromPettyLoanContract(contractNo);

            //设置共同借款人相关信息
            String coCustomerName1 = contractInfoCycleNode.getCoCustomerName1();
            if (StringUtils.isNotBlank(coCustomerName1)) {
                String[] coCustomerArr = coCustomerName1.split(",");
                for (int i = 1; i <= coCustomerArr.length; i++) {
                    String code = coCustomerArr[i - 1].substring(coCustomerArr[i - 1].indexOf("|") + 1);
                    CoCustomerCycleNode coCustomerCycleNo = null;
                    if (code.length() == 18) {//共借人为个人
                        coCustomerCycleNo = contractInfoDao.findCoCustomerInfoFromDataMemberInfo(code);
                        coCustomerCycleNo.setCoCertificateNo(code);
                        coCustomerCycleNo.setCoCustomerType("480001");
                        coCustomerCycleNo.setCoCertificateType("150001");
                    } else {//共借人为企业
                        coCustomerCycleNo = contractInfoDao.findCoCustomerInfoFromDataCompanyInfo(code);
                        coCustomerCycleNo.setCoCertificateNo(code);
                        coCustomerCycleNo.setCoCustomerType("480002");
                        coCustomerCycleNo.setCoCertificateType("150002");
                    }
                    setCoCustomerInfo(contractInfoCycleNode, i, coCustomerCycleNo);

                }
            }

            //设置担保方式,需要手动填写,默认设置为信用 240001
            contractInfoCycleNode.setGuarType("240001");
            //设置币种,人民币:730001
            contractInfoCycleNode.setCcy("730001");
            //是否是额度项下贷款,目前在业务系统内的都不是额度项下贷款,2017年5月31日
            contractInfoCycleNode.setIsRealQuotaLoan("740002");
            //设置合同状态,有效 :490001
            contractInfoCycleNode.setContractStatus("490001");
            //设置客户经理
            contractInfoCycleNode.setRelationManager("渠道来源");
            //设置争议解决方式,默认是法院:400001
            contractInfoCycleNode.setDisputeScheme("400001");

            //目前系统都是自营贷款,没有委托贷款,所以没有委托人相关信息 2017年5月31日

            //设置上报类型,初始保存时,默认为新增:100001
            contractInfoCycleNode.setReportType("100001");
            //设置组织机构代码
            contractInfoCycleNode.setOrgCode("91500000584252884K");
            //设置是否发送,0:否,1:是
            contractInfoCycleNode.setIsSend(0);
            //设置记录保存时间
            contractInfoCycleNode.setInsertDate(new Date());

            contractInfoCycleNode.setIsLast("Y");


            if (StringUtils.isBlank(contractInfoCycleNode.getLoanObject())) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的贷款对象为空");
            }
            if (StringUtils.isBlank(contractInfoCycleNode.getLoanObjectSize())) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的贷款对象规模为空");
            }
            if (StringUtils.isBlank(contractInfoCycleNode.getCertificateType())) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的证件类型为空");
            }
            if (StringUtils.isBlank(contractInfoCycleNode.getCertificateNo())) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的证件号码为空");
            }
            if (StringUtils.isBlank(contractInfoCycleNode.getCustomerName())) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的借款人名称为空");
            }
            if (null == contractInfoCycleNode.getContractSignDate()) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的合同签订日期为空");
            }
            if (null == contractInfoCycleNode.getContractBeginDate()) {
                logger.error(" 批量插入贷款合同信息：合同编号：" + contractNo + "的合同有效起始日期为空");
            }
            if (null == contractInfoCycleNode.getContractEndDate()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的合同有效结束日期为空");
            }
            if (null == contractInfoCycleNode.getContractAmount()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的放款金额为空");
            }
            if (null == contractInfoCycleNode.getLoanCate()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的贷款类型为空");
            }
            if (null == contractInfoCycleNode.getOutstanding()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的余额为空");
            }
            if (null == contractInfoCycleNode.getIntRate()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的月利率为空");
            }
            if (null == contractInfoCycleNode.getPriPltyRate()) {
                logger.error("批量插入贷款合同信息：合同编号：" + contractNo + "的逾期月利率为空");
            }
            
            list.add(contractInfoCycleNode);
        }
        if (list.size() > 0) { //插入0个记录，会抛异常
            contractInfoDao.batchSavePettyLoanContract(list);
        }

    }

    public ContractInfoCycleNode findContractBycontractNo(String contractNo) {
        return  contractInfoDao.findContractByContractNoFromDCContractInfo(contractNo);
    }


    /**
     * 设置共借人信息
     * @param contractInfoCycleNode
     * @param i
     * @param coCustomerCycleNode
     */
    private void setCoCustomerInfo(ContractInfoCycleNode contractInfoCycleNode, int i, CoCustomerCycleNode coCustomerCycleNode) {
        try {
            Class clazz = contractInfoCycleNode.getClass();

            //设置共借人类型
            Field coCustomerType = clazz.getDeclaredField("coCustomerType" + i);
            coCustomerType.setAccessible(true);
            coCustomerType.set(contractInfoCycleNode, coCustomerCycleNode.getCoCustomerType());

            //设置共借人名称
            Field coCustomerName = clazz.getDeclaredField("coCustomerName" + i);
            coCustomerName.setAccessible(true);
            coCustomerName.set(contractInfoCycleNode, coCustomerCycleNode.getCoCustomerName());

            //设置共借人证件类型
            Field coCertificateType = clazz.getDeclaredField("coCertificateType" + i);
            coCertificateType.setAccessible(true);
            coCertificateType.set(contractInfoCycleNode, coCustomerCycleNode.getCoCertificateType());

            //设置共借人证件号码coCertificateNo4
            Field coCertificateNo = clazz.getDeclaredField("coCertificateNo" + i);
            coCertificateNo.setAccessible(true);
            coCertificateNo.set(contractInfoCycleNode, coCustomerCycleNode.getCoCertificateNo());
        } catch (Exception e) {
            logger.error("ContractInfoServiceImpl:setCoCustomerInfo()");
            e.printStackTrace();
        }
    }
}
