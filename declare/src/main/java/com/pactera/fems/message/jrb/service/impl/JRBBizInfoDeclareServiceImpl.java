package com.pactera.fems.message.jrb.service.impl;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.interfaces.validator.First;
import com.global.fems.interfaces.validator.Second;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.RealTimeOnlineContract;
import com.pactera.fems.message.jrb.domain.business.request.RealTimeOnlineEntrustedContract;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import com.pactera.fems.message.jrb.support.JRBGetTxValidator;
import com.pactera.fems.message.jrb.support.JRBMsgHandler;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        Map result  = new HashMap();

        //校验数据
        if (validateContract(contract, result,new Class[]{First.class})) {
            return result;
        }

        //校验通过,将数据复制到JRBReqRealTimeContract.xml模板对应的实体类中
        Map map = new HashMap();
        PropertyUtils.copyBean2Map(contract, map);
        JRBGetTxValidator.setFeild(realTimeOnlineContract, map);
        //设置签约时间格式
        realTimeOnlineContract.setContractSignDate(realTimeOnlineContract.getContractSignDate());
        //发送数据
        JRBMsgHandler.sendMessage(realTimeOnlineContract, headerMsg);

        return null;
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
        Map result  = new HashMap();
        if (validateContract(contract, result,new Class[]{First.class, Second.class})) {
            return result;
        }


        //校验通过,将数据复制到JRBReqRealTimeContractEntrustedLoan.xml模板对应的实体类中
        Map map = new HashMap();
        PropertyUtils.copyBean2Map(contract, map);
        JRBGetTxValidator.setFeild(realTimeOnlineEntrustedContract, map);
        //设置签约时间格式
        realTimeOnlineEntrustedContract.setContractSignDate(realTimeOnlineEntrustedContract.getContractSignDate());
        //发送数据
        JRBMsgHandler.sendMessage(realTimeOnlineEntrustedContract, headerMsg);
        return null;
    }

    /**
     * 校验合同数据具体方法
     * @param contract
     * @param result
     * @param args
     * @return
     */
    private boolean validateContract(PettyLoanContract contract, Map result, Class...args) {
        //使用validator校验数据
        Set<ConstraintViolation<PettyLoanContract>> constraintViolations = validator.validate(contract,args);
        //如果存在错误信息,取出,放到要返回的map中
        if(constraintViolations.size() > 0){
            StringBuilder sb = new StringBuilder();
            Iterator<ConstraintViolation<PettyLoanContract>> iterator = constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<PettyLoanContract> next = iterator.next();
                String message = next.getMessage();
                sb.append(message +"  ");
            }
            result.put("validateError",sb.toString());
            return true;
        }
        return false;
    }


}
