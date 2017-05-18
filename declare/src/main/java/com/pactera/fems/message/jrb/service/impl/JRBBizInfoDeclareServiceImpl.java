package com.pactera.fems.message.jrb.service.impl;

import com.global.fems.business.domain.PettyLoanContract;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.RealTimeOnlineContract;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import com.pactera.fems.message.jrb.support.JRBGetTxValidator;
import com.pactera.fems.message.jrb.support.JRBMsgHandler;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
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

    public Map doRealTimeDeclare(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws DataCheckException {
        RealTimeOnlineContract realTimeOnlineContract = new RealTimeOnlineContract();

        Map map = new HashMap();
        try {
            PropertyUtils.copyBean2Map(contract, map);
            removeNullField(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //校验数据
        Set<ConstraintViolation<PettyLoanContract>> constraintViolations = validator.validate(contract);
        System.out.println(constraintViolations.size());
        JRBGetTxValidator.validateLoanContract(map);
        //校验通过,
        JRBGetTxValidator.setFeild(realTimeOnlineContract, map);
        //设置签约时间格式
        realTimeOnlineContract.setContractSignDate(realTimeOnlineContract.getContractSignDate());

        JRBMsgHandler.sendMessage(realTimeOnlineContract, headerMsg, "/xmlcfg/request/JRBReqRealTimeContract.xml");

        return null;
    }

    //删除值为空的属性
    private void removeNullField(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            Object value = next.getValue();
            if (value == null || value == "") {
                iterator.remove();
            }
        }
    }

    public Map dorealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws DataCheckException {
        return null;
    }


}
