package com.pactera.fems.message.jrb.service.impl;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.interfaces.validator.First;
import com.global.fems.interfaces.validator.Second;
import com.global.framework.util.StringUtil;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.JRBRespMsg;
import com.pactera.fems.message.jrb.domain.business.request.*;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import com.pactera.fems.message.jrb.support.JRBGetTxValidator;
import com.pactera.fems.message.jrb.support.JRBMsgHandler;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

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
    public Map dorealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception {
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
     * 贷款合同信息文件批量上传---自营贷款
     *
     * @param ContractInfoCycleNodeList
     * @return
     * @throws Exception
     */
    public Map doSendContractInfoBatchFile(List<ContractInfoCycleNode> ContractInfoCycleNodeList, ContractInfo contractInfo) throws Exception {

        Map result = new HashMap();
        List contractInfoParamList = new ArrayList();
        for (ContractInfoCycleNode node : ContractInfoCycleNodeList) {
            //校验数据
            if (validateContract(node, result, new Class[]{First.class})) {
                return result;
            }
            ContractInfoParam contractInfoParam = new ContractInfoParam();
            Map hashMap = new HashMap();
            PropertyUtils.copyBean2Map(node, hashMap);
            //将合同信息循环节点转换为与xml对应的实体类
            JRBGetTxValidator.setFeild(contractInfoParam, hashMap, "coCustomerInfo");
            setCoCustomer(hashMap, contractInfoParam);
            contractInfoParam.setContractSignDate(contractInfoParam.getContractSignDate());
            contractInfoParam.setContractBeginDate(contractInfoParam.getContractBeginDate());
            contractInfoParam.setContractEndDate(contractInfoParam.getContractEndDate());
            contractInfoParamList.add(contractInfoParam);
            hashMap.clear();
        }
        JRBMsgHandler.sendBatchFile(contractInfoParamList, contractInfo);
        return null;
    }


    public Map doSendEntrustedContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception {
        return null;
    }


    /**
     * 设置共借人信息
     *
     * @param hashMap
     * @param contractInfoParam
     */
    private void setCoCustomer(Map hashMap, ContractInfoParam contractInfoParam) {
        for (int i = 1; i < 5; i++) {
            String coCustomerType = (String) hashMap.get("coCustomerType" + i);
            if (!StringUtil.isNullOrEmpty(coCustomerType)) {
                String coCustomerName = (String) hashMap.get("coCustomerName" + i);
                String coCertificateType = (String) hashMap.get("coCertificateType" + i);
                String coCertificateNo = (String) hashMap.get("coCertificateNo" + i);
                CoCustomerInfoParam coCustomerInfoParam = new CoCustomerInfoParam();
                coCustomerInfoParam.setCustomerType(coCustomerType);
                coCustomerInfoParam.setCustomerName(coCustomerName);
                coCustomerInfoParam.setCertificateType(coCertificateType);
                coCustomerInfoParam.setCertificateNo(coCertificateNo);
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
