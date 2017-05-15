package com.global.fems.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 批量申报管理Controller
 */
@Controller
@RequestMapping("/batchDeclare.do")
public class BatchDeclareController {


    @RequestMapping(params = "method=showBatchDeclarePage")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/batchDeclare";

    }


}
