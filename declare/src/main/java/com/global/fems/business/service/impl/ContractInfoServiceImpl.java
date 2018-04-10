package com.global.fems.business.service.impl;

import com.global.fems.business.dao.ContractInfoDao;
import com.global.fems.business.dao.PettyLoanContractDao;
import com.global.fems.business.domain.CoCustomerCycleNode;
import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
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

    @Autowired
    private PettyLoanContractDao pettyLoanContractDao;

    /**
     * 保存或更新贷款合同信息,这些合同是未申报的
     *
     * @param contractInfoCycleNode
     * @return
     */
    @Override
    public void saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException {


        try {
            if (StringUtil.isNullOrEmpty(contractInfoCycleNode.getDateId())) {
                //设置dateId
                int dateId = pettyLoanContractDao.findPettyLoanContractDateIdByContractNo(contractInfoCycleNode.getContractNo());
                contractInfoCycleNode.setDateId(dateId);
            }

            if (contractInfoCycleNode.getIsSend() != null && contractInfoCycleNode.getIsSend() == 1) {
                throw new DAOException("当前记录已申报,不能使用保存按钮,请使用已申报修改");
            }
            List<ContractInfoCycleNode> existContractList = contractInfoDao.findContractListByDateId(contractInfoCycleNode.getDateId() + "");
            if (existContractList != null && existContractList.size() > 0) {
                for (ContractInfoCycleNode node : existContractList) {
                    if ("Y".equals(node.getIsLast()) && node.getIsSend() == 0) {
                        if (StringUtil.isNullOrEmpty(contractInfoCycleNode.getIsLast())) {
                            throw new DAOException("当前记录已录入,请通过申报查询后修改");
                        }
                    }
                    node.setIsLast("N");
                }
                contractInfoDao.batchUpdateContract(existContractList, true);
            }

            //设置上报类型,初始保存时,默认为新增:100001
            contractInfoCycleNode.setReportType("100001");
            //设置组织机构代码
            contractInfoCycleNode.setOrgCode((OrgCode.getOrgCode()));
            //设置是否发送,0:否,1:是
            contractInfoCycleNode.setIsSend(0);
            //设置数据类型
            contractInfoCycleNode.setDataType("CONTRACT_INFO");
            if (StringUtil.isNullOrEmpty(contractInfoCycleNode.getInsertDate())) {
                //设置记录保存时间
                contractInfoCycleNode.setInsertDate(new Date());
            }

            //设置是否是最新记录
            contractInfoCycleNode.setIsLast("Y");
            contractInfoDao.saveOrUpdate(contractInfoCycleNode);
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

        List<ContractInfoCycleNode> list = new ArrayList<ContractInfoCycleNode>();
        a:
        for (String dateId : idsArr) {

            List<ContractInfoCycleNode> existContractList = contractInfoDao.findContractListByDateId(dateId);
            if (existContractList != null && existContractList.size() > 0) {//如果存在且上报类型不是删除，跳过,避免重复插入
                Boolean isDelete = false; //是否删除的标记
                for (ContractInfoCycleNode contractInfoCycleNode : existContractList) {
                    if ("100003".equals(contractInfoCycleNode.getReportType())) {
                        isDelete = true;
                        break;
                    } else if (contractInfoCycleNode.getIsSend() == 0 && "Y".equals(contractInfoCycleNode.getIsLast())) {//上报类型不为删除,跳过
                        continue a;
                    }
                }

                if (!isDelete) { //已经被删除时,允许保存
                    continue;
                }
            }
            //根据dateId查询合同信息
            ContractInfoCycleNode contractInfoCycleNode = contractInfoDao.findContractByDateIdFromBizSys(dateId);
            //设置数据类型
            contractInfoCycleNode.setDataType("CONTRACT_INFO");
            //设置共同借款人相关信息
            setCoCustomer(contractInfoCycleNode);

            //设置币种,人民币:730001
            contractInfoCycleNode.setCcy("730001");
            //设置合同状态,有效 :490001
            contractInfoCycleNode.setContractStatus("490001");
            //设置客户经理
            contractInfoCycleNode.setRelationManager("渠道来源");
            //设置争议解决方式,默认是法院:400001
            contractInfoCycleNode.setDisputeScheme("400001");

            //委托贷款中的受托人只有通过手动填写,系统中没有

            //设置上报类型,初始保存时,默认为新增:100001
            contractInfoCycleNode.setReportType("100001");
            //设置组织机构代码
            contractInfoCycleNode.setOrgCode(OrgCode.getOrgCode());
            //设置是否申报,0:否,1:是
            contractInfoCycleNode.setIsSend(0);
            //设置记录保存时间
            contractInfoCycleNode.setInsertDate(new Date());
            //是否最新版本
            contractInfoCycleNode.setIsLast("Y");


            //校验属性值是否为空
            validate(contractInfoCycleNode.getContractNo(), contractInfoCycleNode);

            list.add(contractInfoCycleNode);
        }
        if (list.size() > 0) { //插入0个记录，会抛异常
            contractInfoDao.batchSaveContract(list);
        }

    }


    /**
     * 根据dateId联合查询合同详细信息
     *
     * @param dateId
     * @return
     */
    @Override
    public ContractInfoCycleNode findContractByDateId(String dateId) {
        ContractInfoCycleNode contractInfoCycleNode = contractInfoDao.findContractByDateIdFromBizSys(dateId);
        if (!StringUtil.isNullOrEmpty(contractInfoCycleNode)) {
            setCoCustomer(contractInfoCycleNode);
        }
        return contractInfoCycleNode;
    }

    /**
     * 根据申报状态和合同号查询合同部分信息
     *
     * @param contractNo
     * @param sendStatus
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {

        return pettyLoanContractDao.findContractByContractNoFromRealTimeContract(contractNo, sendStatus, pageBean);
    }

    /**
     * 根据合同号查询合同简略信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        return contractInfoDao.findContractBriefInfoByContractNo(contractNo, pageBean);
    }

    /**
     * 根据申报状态和签约时间段查询合同信息
     *
     * @param sendStatus
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findContractBySendStatus(String sendStatus, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {

        return contractInfoDao.findContractBySendStatus(sendStatus, signStartDate, signEndDate, pageBean);
    }


    /**
     * 根据记录id从DC_CONTRACT_INFO查询合同详细信息
     *
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public ContractInfoCycleNode findContractById(String id) throws DAOException {

        return contractInfoDao.findContractById(id);
    }

    /**
     * 已申报合同信息更新
     *
     * @param contractInfoCycleNode
     * @throws DAOException
     */
    @Override
    public void declaredUpdate(ContractInfoCycleNode contractInfoCycleNode) throws DAOException {

        //查询已有记录,将是否为最新版本设置为N
        List<ContractInfoCycleNode> list = contractInfoDao.findContractListByDateId(contractInfoCycleNode.getDateId() + "");
        if (list != null && list.size() > 0) {
            for (ContractInfoCycleNode contractInfoNode : list) {
                if ("100003".equals(contractInfoNode.getReportType())) {
                    throw new DAOException("当前记录已申报删除,不能进行已申报合同信息修改");
                }
                if ("N".equals(contractInfoNode.getIsLast())) {
                    continue;
                } else {
                    contractInfoNode.setIsLast("N");
                }
            }
            contractInfoDao.batchUpdateContract(list, true);
        }

        contractInfoCycleNode.setId(null);
        //设置组织机构代码
        contractInfoCycleNode.setOrgCode((OrgCode.getOrgCode()));
        //设置申报状态为未申报,0:否,1:是
        contractInfoCycleNode.setIsSend(0);
        //设置记录保存时间
        contractInfoCycleNode.setInsertDate(new Date());
        contractInfoCycleNode.setIsLast("Y");
        //校验属性值是否为空
        validate(contractInfoCycleNode.getContractNo(), contractInfoCycleNode);
        //设置上报类型为修改记录
        contractInfoCycleNode.setReportType("100002");
        //设置数据类型
        contractInfoCycleNode.setDataType("CONTRACT_INFO");
        contractInfoDao.saveOrUpdate(contractInfoCycleNode);

    }


    /**
     * 已申报记录删除
     *
     * @param ids
     * @return
     * @throws BaseException
     */
    @Override
    public ResultModel deleteRecord(String ids) throws DAOException {
        String[] idsArr = ids.split(",");
        for (String dateId : idsArr) {
            try {
                ContractInfoCycleNode contractInfoCycleNode = null;
                //查询已有记录,将是否为最新版本设置为N
                List<ContractInfoCycleNode> list = contractInfoDao.findContractListByDateId(dateId);
                for (ContractInfoCycleNode contractInfoNode : list) {
                    if ("100003".equals(contractInfoNode.getReportType()) && contractInfoNode.getIsSend() == 0) {

                        throw new BaseException("合同编号为:" + contractInfoNode.getContractNo() + ",此记录已设置申报删除,无需再次设置");
                    }
                    if ("N".equals(contractInfoNode.getIsLast())) {
                        continue;
                    } else {
                        contractInfoCycleNode = contractInfoNode;
                        contractInfoNode.setIsLast("N");
                    }
                }
                contractInfoDao.batchUpdateContract(list, true);
                contractInfoCycleNode.setId(null);
                //设置组织机构代码
                contractInfoCycleNode.setOrgCode((OrgCode.getOrgCode()));
                //设置申报状态为未申报,0:否,1:是
                contractInfoCycleNode.setIsSend(0);
                //设置记录保存时间
                contractInfoCycleNode.setInsertDate(new Date());
                contractInfoCycleNode.setIsLast("Y");

                //校验属性值是否为空
                validate(contractInfoCycleNode.getContractNo(), contractInfoCycleNode);
                //设置上报类型为修改记录
                contractInfoCycleNode.setReportType("100003");
                contractInfoDao.saveOrUpdate(contractInfoCycleNode);

            } catch (BaseException e) {
                logger.debug("ContractInfoServiceImpl:deleteRecord()", e.getLocalizedMessage());
                return ResultModel.fail(e.getLocalizedMessage());
            }
        }
        return ResultModel.ok();
    }

    /**
     * 根据申报状态和签约时间段查询最新的合同信息
     *
     * @param sendStatusCode 0:未发送,1:已发送
     * @param contractNo
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @Override
    public PageBean findLastContractBySendStatus(String sendStatusCode, String contractNo, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {

        return contractInfoDao.findLastContractBySendStatus(sendStatusCode, contractNo, signStartDate, signEndDate, pageBean);
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
            ArrayList<ContractInfoCycleNode> contractInfoCycleNodeList = new ArrayList<>();
            for (String id : idsArr) {
                ContractInfoCycleNode contractById = contractInfoDao.findContractById(id);
                contractById.setIsSend(0);
                contractInfoCycleNodeList.add(contractById);
            }
            contractInfoDao.batchUpdateContract(contractInfoCycleNodeList, true);
        } catch (DAOException e) {
            logger.debug("ContractInfoServiceImpl:setNotSend()", e.getLocalizedMessage());
            return ResultModel.fail();
        }
        return ResultModel.ok();
    }


    /**
     * 设置共借人信息,根据查询出来的共借人的身份证号查询共借人信息
     *
     * @param contractInfoCycleNode
     */
    private void setCoCustomer(ContractInfoCycleNode contractInfoCycleNode) {
        String coCustomerName1 = contractInfoCycleNode.getCoCustomerName1();
        if (StringUtils.isNotBlank(coCustomerName1)) {
            String[] coCustomerArr = coCustomerName1.split(",");
            for (int i = 1; i <= coCustomerArr.length; i++) {
                String code = coCustomerArr[i - 1].substring(coCustomerArr[i - 1].indexOf("|") + 1);
                if (code != null) {
                    CoCustomerCycleNode coCustomerCycleNo = contractInfoDao.findCoCustomerInfoFromDataMemberInfo(code);
                    if (coCustomerCycleNo != null) {//共借人为个人
                        coCustomerCycleNo.setCoCertificateNo(code.toUpperCase());
                        coCustomerCycleNo.setCoCustomerType("480001");
                        coCustomerCycleNo.setCoCertificateType("150001");
                    } else {//共借人为企业
                        coCustomerCycleNo = contractInfoDao.findCoCustomerInfoFromDataCompanyInfo(code);
                        coCustomerCycleNo.setCoCertificateNo(code.toUpperCase());
                        coCustomerCycleNo.setCoCustomerType("480002");
                        coCustomerCycleNo.setCoCertificateType("150002");
                    }
                    setCoCustomerInfo(contractInfoCycleNode, i, coCustomerCycleNo);
                }

            }
        }
    }

    /**
     * 设置共借人信息
     *
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

            //设置联系电话
            Field coTelephone = clazz.getDeclaredField("coTelephone" + i);
            coTelephone.setAccessible(true);
            coTelephone.set(contractInfoCycleNode, coCustomerCycleNode.getCoTelephone());

        } catch (Exception e) {
            logger.error("ContractInfoServiceImpl:setCoCustomerInfo()");
            e.printStackTrace();
        }
    }


    private void validate(String contractNo, ContractInfoCycleNode contractInfoCycleNode) {
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
    }

}
