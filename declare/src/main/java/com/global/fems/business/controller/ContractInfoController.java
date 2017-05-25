package com.global.fems.business.controller;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *贷款合同信息管理Controller
 */
@Controller
@RequestMapping("/contractInfo.do")
public class ContractInfoController {

    @Autowired
    private ContractInfoService contractInfoService;


    /**
     * 跳转到合同信息页面
     * @return
     */
    @RequestMapping(params = "method=showContractInfo")
    public String showContractInfo() {

        return "business/pettyLoanContract/fillContractInfo";

    }

    @RequestMapping(params = "method=saveContract")
    @ResponseBody
    public ResultModel saveLoanContract(ContractInfoCycleNode contractInfoCycleNode){
        ResultModel result =  contractInfoService.save(contractInfoCycleNode);
        return result;
    }



}
