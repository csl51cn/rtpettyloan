package com.pactera.fems.message.jrb.service;

import com.global.fems.business.domain.*;
import com.pactera.fems.message.jrb.domain.JRBReqBatchFileMsg;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface JRBBizInfoDeclareService {
    Map doRealTimeDeclare(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception;

    Map doRealTimeDeclareEntrustedLoan(PettyLoanContract contract, JRBReqHeaderMsg headerMsg) throws Exception;

    Map doSendContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception;

    Map doSendEntrustedContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception;

    Map doContractInfoDeclare(JRBReqBatchFileMsg headerMsg) throws Exception;

    Map doSendContractInfoIssueBatchFile(ArrayList<ContractIssueInfo> list, ContractIssueInfoUpload contractIssueInfoUpload) throws Exception;

    Map doSendPayPlanInfoBatchFile(ArrayList<PayPlanInfo> list, PayPlanInfoUpload payPlanInfoUpload) throws Exception;

    Map doSendRepayInfoBatchFile(ArrayList<RepayInfo> list, RepayInfoUpload repayInfoUpload) throws Exception;

    Map doQueryDeclared(QueryDeclared queryDeclared, JRBReqHeaderMsg headerMsg) throws Exception;

}

