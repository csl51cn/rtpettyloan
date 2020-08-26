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

    Map<String, String> doSendContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception;

    Map doSendEntrustedContractInfoBatchFile(List<ContractInfoCycleNode> list, ContractInfo contractInfo) throws Exception;

    Map doContractInfoDeclare(JRBReqBatchFileMsg headerMsg) throws Exception;

    Map<String, String> doSendContractInfoIssueBatchFile(ArrayList<ContractIssueInfo> list, ContractIssueInfoUpload contractIssueInfoUpload) throws Exception;

    Map<String, String> doSendPayPlanInfoBatchFile(ArrayList<PayPlanInfo> list, PayPlanInfoUpload payPlanInfoUpload) throws Exception;

    Map<String, String> doSendRepayInfoBatchFile(ArrayList<RepayInfo> list, RepayInfoUpload repayInfoUpload) throws Exception;

    Map<String, String> doSendQuotaInfoBatchFile(ArrayList<QuotaInfo> list, QuotaInfoUpload quotaInfoUpload) throws Exception;

    Map doQueryDeclared(QueryDeclared queryDeclared, JRBReqHeaderMsg headerMsg) throws Exception;

    Map<String, String> doSendPettyLoanContractBatchFile(List<PettyLoanContract> list, PettyLoanContractInfoUpload pettyLoanContractInfo) throws Exception;
}

