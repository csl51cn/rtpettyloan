package com.global.fems.business.controller;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.service.ContractInfoService;
import com.global.fems.interfaces.validator.First;
import com.global.fems.interfaces.validator.Second;
import com.global.framework.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款合同信息管理Controller
 */
@Controller
@RequestMapping("/contractInfo.do")
public class ContractInfoController {

    @Autowired
    private ContractInfoService contractInfoService;


    /**
     * 跳转到合同信息页面
     *
     * @return
     */
    @RequestMapping(params = "method=showContractInfo")
    public String showContractInfo() {

        return "business/pettyLoanContract/fillContractInfo";

    }

    /**
     * 保存自营贷款
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveContract")
    public String saveLoanContract(@Validated({First.class}) ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        return saveOrUpdate(contractInfoCycleNode, result, model);
    }

    /**
     * 保存委托贷款
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveEntrustedLoanContract")
    public String saveEntrustedLoanContract(@Validated({First.class, Second.class}) ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        return saveOrUpdate(contractInfoCycleNode, result, model);
    }

    /**
     * 保存或更新贷款合同的通用方法
     *
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    private String saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        try {
            //校验ContractInfoCycleNode对象的数据是否完整
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新合同记录时，数据校验未通过");
            }

            contractInfoService.saveOrUpdate(contractInfoCycleNode);
            model.addAttribute("msg", "1");//返回操作成功标志

        } catch (BaseException e) {
            e.printStackTrace();
            model.addAttribute("msg", "0");//返回操作失败标志
        }
        model.addAttribute("model", contractInfoCycleNode);
        return "business/pettyLoanContract/fillContractInfo";
    }


    /**
     * 批量保存合同信息
     * @param contractNos
     * @return
     */
    @RequestMapping(params = "method=batchSaveContract")
    @ResponseBody
    public String batchSaveContract(String contractNos) {
        try {
            contractInfoService.batchSaveContract(contractNos);
            return "1";
        } catch (Exception e) {

            return "0";
        }
    }

    @RequestMapping(params = "method=findContractBycontractNo")
    public  String findContractBycontractNo(String  contractNo,Model model){
        ContractInfoCycleNode contractInfoCycleNode = contractInfoService.findContractBycontractNo(contractNo);
        model.addAttribute("model",contractInfoCycleNode);
        return "business/pettyLoanContract/fillContractInfo";

    }


}
