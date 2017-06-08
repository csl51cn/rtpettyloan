package com.global.fems.business.controller;

import com.global.fems.business.service.BatchDeclareService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 批量申报管理Controller
 */
@Controller
@RequestMapping("/batchDeclare.do")
public class BatchDeclareController {

    @Autowired
    private BatchDeclareService batchDeclareService;

    @RequestMapping(params = "method=showBatchDeclarePage")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/batchDeclare";

    }
    @RequestMapping(params = "method=sendBatchFile",produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel sendBatchFile(String ids){
        try {
          return   batchDeclareService.sendBatchFile(ids);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


}
