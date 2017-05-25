package com.global.fems.message.service.impl;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.util.DateTimeUtil;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class JRBBizInfoDeclareManager {
    private static final Logger log = Logger.getLogger(JRBBizInfoDeclareManager.class);

    @Autowired
    private SysCommonService sysCommonService;

    @Autowired
    private JRBBizInfoDeclareService jrbBizInfoDeclareService;


    /**
     * 实时网签
     * 组装请求报文头
     *
     * @param contract 从表DC_PETTY_LOAN_CONTRACT中查询的合同信息
     * @return
     * @throws Exception
     */
    public Map realTimeDeclare(PettyLoanContract contract) throws Exception {

        //组装请求报文头
        JRBReqHeaderMsg headerMsg = new JRBReqHeaderMsg();
        //设置服务代码
        headerMsg.setSERVICE_CODE("SVR_PTLN");
        //设置交易码
        headerMsg.setTRAN_CODE("PTLN001");
        //设置交易模式
        headerMsg.setTRAN_MODE("ONLINE");
        //设置组织机构代码
        headerMsg.setBRANCH_ID("91500000584252884K");
        //设置交易日期
        headerMsg.setTRAN_DATE(DateTimeUtil.getNowDateTime("yyyyMMdd"));
        //设置交易时间
        headerMsg.setTRAN_TIMESTAMP(DateTimeUtil.getNowDateTime("HHmmssSSS"));
        //设置用户语言
        headerMsg.setUSER_LANG("CHINESE");
        //设置渠道流水号
        headerMsg.setSEQ_NO(sysCommonService.getSeqNo("wfl_taskinfo"));
       // headerMsg.setSEQ_NO("2017051900000001");
        //设置模块标识
        headerMsg.setMODULE_ID("CL");
        //设置报文类型
        headerMsg.setMESSAGE_TYPE("1200");
        //设置报文代码
        headerMsg.setMESSAGE_CODE("0001");

        Map map = null;
        if ("530002".equals(contract.getLoanCate())) {
            //委托贷款
            map = jrbBizInfoDeclareService.dorealTimeDeclareEntrustedLoan(contract, headerMsg);
        } else {
            //自营贷款
            map = jrbBizInfoDeclareService.doRealTimeDeclare(contract, headerMsg);
        }

        return map;
    }

    /**
     * 额度信息申报
     * @param list
     * @return
     */

    public  Map  quotaInfoDeclare(List list){

        return null;
    }

    /**
     * 合同信息申报
     * @param list
     * @return
     */

    public  Map  contractInfoDeclare(List list){

        return null;
    }

}
