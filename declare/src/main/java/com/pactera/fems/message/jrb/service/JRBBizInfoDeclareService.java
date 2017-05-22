package com.pactera.fems.message.jrb.service;

import com.global.fems.business.domain.PettyLoanContract;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;

import java.util.Map;

/**
 * Created by senlin.deng on 2017/5/12.
 */
public interface JRBBizInfoDeclareService {
    Map doRealTimeDeclare( PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception;
    Map  dorealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception;
}

