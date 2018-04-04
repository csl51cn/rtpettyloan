package com.pactera.fems.message.jrb.service.impl;

import com.global.fems.business.domain.*;
import com.global.fems.business.interfaces.First;
import com.global.fems.business.interfaces.Second;
import com.global.framework.util.StringUtil;
import com.pactera.fems.message.jrb.domain.JRBReqBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.JRBRespBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBRespMsg;
import com.pactera.fems.message.jrb.domain.business.request.*;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import com.pactera.fems.message.jrb.support.JRBGetTxValidator;
import com.pactera.fems.message.jrb.support.JRBMsgHandler;
import org.apache.commons.lang.StringUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * 报文申报服务接口实现类,校验数据,调用socket客户端和SFTP客户端发送报文
 */
@Service
public class JRBBizInfoDeclareServiceImpl implements JRBBizInfoDeclareService {


    @Autowired
    private Validator validator;

    /**
     * 实时网签---自营贷款
     * 校验合同数数据,合格则申报
     *
     * @param contract
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    @Override
    public Map doRealTimeDeclare(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception {
        RealTimeOnlineContract realTimeOnlineContract = new RealTimeOnlineContract();
        Map result = new HashMap();

        //校验数据
        if (validateContract(contract, result, new Class[]{First.class})) {
            return result;
        }

        //校验通过,将数据复制到JRBReqRealTimeContract.xml模板对应的实体类中
        Map map = new HashMap();
        PropertyUtils.copyBean2Map(contract, map);
        JRBGetTxValidator.setFeild(realTimeOnlineContract, map);
        //判断是否是循环授信,如果是,将循环授信合同编号赋给实时网签的合同号
        if(StringUtils.equals("740001",contract.getIsRealQuotaLoan())){
            realTimeOnlineContract.setContractNo(contract.getRealQuotaNo());
        }
        //设置签约时间格式
        realTimeOnlineContract.setContractSignDate(realTimeOnlineContract.getContractSignDate());
        //发送数据
        JRBRespMsg respMsg = (JRBRespMsg) JRBMsgHandler.sendMessage(realTimeOnlineContract, headerMsg);
        result.put("respMsg", respMsg);
        return result;
    }

    /**
     * 实时网签--委托贷款
     * 校验合同数数据,合格则申报
     *
     * @param contract
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    @Override
    public Map doRealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception {
        RealTimeOnlineEntrustedContract realTimeOnlineEntrustedContract = new RealTimeOnlineEntrustedContract();
        Map result = new HashMap();
        if (validateContract(contract, result, new Class[]{First.class, Second.class})) {
            return result;
        }

        //校验通过,将数据复制到JRBReqRealTimeContractEntrustedLoan.xml模板对应的实体类中
        Map map = new HashMap();
        PropertyUtils.copyBean2Map(contract, map);
        JRBGetTxValidator.setFeild(realTimeOnlineEntrustedContract, map);
        //设置签约时间格式
        realTimeOnlineEntrustedContract.setContractSignDate(realTimeOnlineEntrustedContract.getContractSignDate());
        //发送数据
        JRBRespMsg respMsg = (JRBRespMsg) JRBMsgHandler.sendMessage(realTimeOnlineEntrustedContract, headerMsg);
        result.put("respMsg", respMsg);
        return result;
    }

    /**
     * 查询上报结果
     *
     * @param queryDeclared
     * @param headerMsg
     * @return
     * @throws Exception
     */
    @Override
    public Map doQueryDeclared(QueryDeclared queryDeclared, JRBReqHeaderMsg headerMsg) throws Exception {
        Map result = new HashMap();
        //发送数据
        JRBRespMsg respMsg = (JRBRespMsg) JRBMsgHandler.sendMessage(queryDeclared, headerMsg);
        result.put("respMsg", respMsg);
        return result;
    }

    /**
     * 贷款合同信息文件批量上传到SFTP服务器---自营贷款
     *
     * @param ContractInfoCycleNodeList
     * @return
     * @throws Exception
     */
    @Override
    public Map doSendContractInfoBatchFile(List<ContractInfoCycleNode> ContractInfoCycleNodeList, ContractInfo contractInfo) throws Exception {

        Map result = new HashMap();
        List contractInfoParamList = new ArrayList();
        for (ContractInfoCycleNode node : ContractInfoCycleNodeList) {
            //校验数据,如果校验失败,将不符合要求的数据信息返回到页面
            if (validateContract(node, result, new Class[]{First.class})) {
                StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
                validateError.append(node.getContractNo());
                validateError.append(",借款人:");
                validateError.append(node.getCustomerName());
                validateError.append(" ");
                validateError.append(result.get("validateError"));
                result.put("validateError", validateError.toString());
                return result;
            }
            ContractInfoParam contractInfoParam = new ContractInfoParam();
            Map fieldAndValue = new HashMap();
            PropertyUtils.copyBean2Map(node, fieldAndValue);
            //将合同信息循环节点转换为与xml对应的实体类
            JRBGetTxValidator.setFeild(contractInfoParam, fieldAndValue, "coCustomerInfo");
            setCoCustomer(fieldAndValue, contractInfoParam);
            //判断是否是循环授信,如果是,将循环授信合同编号赋给实时网签的合同号
            if(StringUtils.equals("740001",node.getIsRealQuotaLoan())){
                contractInfoParam.setContractNo(node.getRealQuotaNo());
            }
            contractInfoParam.setContractSignDate(contractInfoParam.getContractSignDate());
            contractInfoParam.setContractBeginDate(contractInfoParam.getContractBeginDate());
            contractInfoParam.setContractEndDate(contractInfoParam.getContractEndDate());
            contractInfoParamList.add(contractInfoParam);
            fieldAndValue.clear();
        }
        contractInfo.setRecordCount(contractInfoParamList.size() + "");
        contractInfo.setContractInfo(contractInfoParamList);
        Map map = JRBMsgHandler.sendBatchFile(contractInfo, contractInfo.getDataType(), contractInfo.getBatchNo());
        if (map.get("error") != null) {
            result.put("error", map.get("error"));
        } else {
            result.put("fileName", map.get("fileName"));
        }

        return result;
    }

    /**
     * 贷款合同信息文件批量上传到SFTP服务器---委托贷款
     *
     * @param list
     * @param contractInfo
     * @return
     * @throws Exception
     */
    @Override
    public Map doSendEntrustedContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception {
        return null;
    }

    /**
     * 批量报文信息实时报文上报
     *
     * @param headerMsg
     * @return
     * @throws Exception
     */
    @Override
    public Map doContractInfoDeclare(JRBReqBatchFileMsg headerMsg) throws Exception {
        JRBRespBatchFileMsg respMsg = (JRBRespBatchFileMsg) JRBMsgHandler.sendMessage(headerMsg);
        HashMap result = new HashMap();
        result.put("respMsg", respMsg);
        return result;
    }

    /**
     * 贷款放款信息文件批量上传到SFTP服务器
     *
     * @param contractIssueInfoList
     * @param contractIssueInfoUpload
     * @return
     * @throws Exception
     */
    @Override
    public Map doSendContractInfoIssueBatchFile(ArrayList<ContractIssueInfo> contractIssueInfoList, ContractIssueInfoUpload contractIssueInfoUpload) throws Exception {
        Map result = new HashMap();
        ArrayList<ContractIssueInfoUploadParam> contractIssueInfoUploadParamList = new ArrayList<>();
        for (ContractIssueInfo node : contractIssueInfoList) {
            //校验数据,如果校验失败,将不符合要求的数据信息返回到页面
            if (validateContract(node, result, new Class[]{})) {
                StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
                validateError.append(node.getContractNo());
                validateError.append(",借款人:");
                validateError.append(node.getCustomerName());
                validateError.append(" ");
                validateError.append(result.get("validateError"));
                result.put("validateError", validateError.toString());
                return result;
            }
            ContractIssueInfoUploadParam contractIssueInfoUploadParam = new ContractIssueInfoUploadParam();
            Map fieldAndValue = new HashMap();
            PropertyUtils.copyBean2Map(node, fieldAndValue);
            //将合同信息循环节点转换为与xml对应的实体类
            JRBGetTxValidator.setFeild(contractIssueInfoUploadParam, fieldAndValue);
            //判断是否是循环授信,如果是,将循环授信合同编号赋给实时网签的合同号
            if(StringUtils.equals("740001",node.getIsRealQuotaLoan())){
                contractIssueInfoUploadParam.setContractNo(node.getRealQuotaNo());
            }
            //设置时间格式
            contractIssueInfoUploadParam.setSignDate(contractIssueInfoUploadParam.getSignDate());
            contractIssueInfoUploadParam.setDdDate(contractIssueInfoUploadParam.getDdDate());
            contractIssueInfoUploadParam.setMatureDate(contractIssueInfoUploadParam.getMatureDate());
            //如果开始使用展期,需要取消注释
//            if(!StringUtil.isNullOrEmpty(contractIssueInfoUploadParam.getExtStartDate())){
//                contractIssueInfoUploadParam.setExtStartDate(contractIssueInfoUploadParam.getExtStartDate());
//            }
//            if(!StringUtil.isNullOrEmpty(contractIssueInfoUploadParam.getExtEndDate())){
//                contractIssueInfoUploadParam.setExtEndDate(contractIssueInfoUploadParam.getExtEndDate());
//            }
            contractIssueInfoUploadParamList.add(contractIssueInfoUploadParam);
            fieldAndValue.clear();
        }
        contractIssueInfoUpload.setRecordCount(contractIssueInfoUploadParamList.size() + "");
        contractIssueInfoUpload.setIssueInfo(contractIssueInfoUploadParamList);
        Map map = JRBMsgHandler.sendBatchFile(contractIssueInfoUpload, contractIssueInfoUpload.getDataType(), contractIssueInfoUpload.getBatchNo());
        if (map.get("error") != null) {
            result.put("error", map.get("error"));
        } else {
            result.put("fileName", map.get("fileName"));
        }

        return result;
    }

    /**
     * 还款计划信息文件批量上传到SFTP服务器
     *
     * @param payPlanInfolist
     * @param payPlanInfoUpload
     * @return
     * @throws Exception
     */
    @Override
    public Map doSendPayPlanInfoBatchFile(ArrayList<PayPlanInfo> payPlanInfolist, PayPlanInfoUpload payPlanInfoUpload) throws Exception {
        Map result = new HashMap();
        ArrayList<PayPlanInfoUploadParam> payPlanInfoUploadParamList = new ArrayList<>();
        for (PayPlanInfo node : payPlanInfolist) {
            //校验数据,如果校验失败,将不符合要求的数据信息返回到页面
            if (validateContract(node, result, new Class[]{})) {
                StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
                validateError.append(node.getContractNo());
                validateError.append(" ");
                validateError.append(result.get("validateError"));
                result.put("validateError", validateError.toString());
                return result;
            }
            PayPlanInfoUploadParam payPlanInfoUploadParam = new PayPlanInfoUploadParam();
            Map fieldAndValue = new HashMap();
            PropertyUtils.copyBean2Map(node, fieldAndValue);
            //将还款计划信息循环节点转换为与xml对应的实体类
            JRBGetTxValidator.setFeild(payPlanInfoUploadParam, fieldAndValue);
            //判断是否是循环授信,如果是,将循环授信合同编号赋给实时网签的合同号
            if(StringUtils.equals("740001",node.getIsRealQuotaLoan())){
                payPlanInfoUploadParam.setContractNo(node.getRealQuotaNo());
            }
            //设置时间格式
            payPlanInfoUploadParam.setRepayDate(payPlanInfoUploadParam.getRepayDate());
            payPlanInfoUploadParam.setStartDate(payPlanInfoUploadParam.getStartDate());
            payPlanInfoUploadParam.setEndDate(payPlanInfoUploadParam.getEndDate());
            payPlanInfoUploadParamList.add(payPlanInfoUploadParam);
            fieldAndValue.clear();
        }
        payPlanInfoUpload.setRecordCount(payPlanInfoUploadParamList.size() + "");
        payPlanInfoUpload.setPayPlanInfo(payPlanInfoUploadParamList);
        Map map = JRBMsgHandler.sendBatchFile(payPlanInfoUpload, payPlanInfoUpload.getDataType(), payPlanInfoUpload.getBatchNo());
        if (map.get("error") != null) {
            result.put("error", map.get("error"));
        } else {
            result.put("fileName", map.get("fileName"));
        }

        return result;
    }

    /**
     * 贷款回收计划信息文件批量上传到SFTP服务器
     *
     * @param repayInfolist
     * @param repayInfoUpload
     * @return
     * @throws Exception
     */
    @Override
    public Map doSendRepayInfoBatchFile(ArrayList<RepayInfo> repayInfolist, RepayInfoUpload repayInfoUpload) throws Exception {
        Map result = new HashMap();
        ArrayList<RepayInfoUploadParam> repayInfoUploadParamList = new ArrayList<>();
        for (RepayInfo node : repayInfolist) {
            //校验数据,如果校验失败,将不符合要求的数据信息返回到页面
            if (validateContract(node, result, new Class[]{})) {
                StringBuilder validateError = new StringBuilder("当前记录申报失败,此条记录之前的记录已申报(如果有).合同信息--合同编号:");
                validateError.append(node.getContractNo());
                validateError.append(" ");
                validateError.append(result.get("validateError"));
                result.put("validateError", validateError.toString());
                return result;
            }
            RepayInfoUploadParam repayInfoUploadParam = new RepayInfoUploadParam();
            Map fieldAndValue = new HashMap();
            PropertyUtils.copyBean2Map(node, fieldAndValue);
            //将还款计划信息循环节点转换为与xml对应的实体类
            JRBGetTxValidator.setFeild(repayInfoUploadParam, fieldAndValue);
            //判断是否是循环授信,如果是,将循环授信合同编号赋给实时网签的合同号
            if(StringUtils.equals("740001",node.getIsRealQuotaLoan())){
                repayInfoUploadParam.setContractNo(node.getRealQuotaNo());
            }

            //设置时间格式
            repayInfoUploadParam.setRepayDate(repayInfoUploadParam.getRepayDate());
            repayInfoUploadParam.setStartDate(repayInfoUploadParam.getStartDate());
            repayInfoUploadParam.setEndDate(repayInfoUploadParam.getEndDate());
            repayInfoUploadParamList.add(repayInfoUploadParam);
            fieldAndValue.clear();
        }
        repayInfoUpload.setRecordCount(repayInfoUploadParamList.size() + "");
        repayInfoUpload.setRepayInfo(repayInfoUploadParamList);
        Map map = JRBMsgHandler.sendBatchFile(repayInfoUpload, repayInfoUpload.getDataType(), repayInfoUpload.getBatchNo());
        if (map.get("error") != null) {
            result.put("error", map.get("error"));
        } else {
            result.put("fileName", map.get("fileName"));
        }

        return result;
    }


    /**
     * 设置共借人信息
     *
     * @param fieldAndValue
     * @param contractInfoParam
     */
    private void setCoCustomer(Map fieldAndValue, ContractInfoParam contractInfoParam) {
        //共借人人数上限为4,根据业务实际碰到过的最多人数来确定的
        for (int i = 1; i < 5; i++) {
            String coCustomerType = (String) fieldAndValue.get("coCustomerType" + i);
            if (!StringUtil.isNullOrEmpty(coCustomerType)) {
                String coCustomerName = (String) fieldAndValue.get("coCustomerName" + i);
                String coCertificateType = (String) fieldAndValue.get("coCertificateType" + i);
                String coCertificateNo = (String) fieldAndValue.get("coCertificateNo" + i);
                String coTelephone = (String) fieldAndValue.get("coTelephone" + i);
                CoCustomerInfoParam coCustomerInfoParam = new CoCustomerInfoParam();
                coCustomerInfoParam.setCustomerType(coCustomerType);
                coCustomerInfoParam.setCustomerName(coCustomerName);
                coCustomerInfoParam.setCertificateType(coCertificateType);
                coCustomerInfoParam.setCertificateNo(coCertificateNo);
                coCustomerInfoParam.setTelephone(coTelephone);
                contractInfoParam.getCoCustomerInfo().add(coCustomerInfoParam);
            }
        }
    }

    /**
     * 校验合同数据具体方法
     *
     * @param t
     * @param result
     * @param args
     * @param <T>
     * @return 如果有错误, 返回true, 没有错误返回false
     */
    private <T> boolean validateContract(T t, Map result, Class... args) {

        //使用validator校验数据
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, args);
        //如果存在错误信息,取出,放到要返回的map中k
        if (constraintViolations.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> next = iterator.next();
                String message = next.getMessage();
                sb.append(message + "  ");
            }
            result.put("validateError", sb.toString());
            return true;
        }
        return false;
    }


}
