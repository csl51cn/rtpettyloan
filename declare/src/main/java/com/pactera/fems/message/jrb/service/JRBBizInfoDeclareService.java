package com.pactera.fems.message.jrb.service;

import com.global.fems.business.domain.PettyLoanContract;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import org.global.framework.xmlbeans.bean.DataCheckException;

import java.util.Map;

/**
 * Created by senlin.deng on 2017/5/12.
 */
public interface JRBBizInfoDeclareService {
    Map doRealTimeDeclare( PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws DataCheckException;
    Map  dorealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws DataCheckException;
}

