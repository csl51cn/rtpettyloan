package com.global.fems.business.controller;

import com.global.fems.business.service.RealTimeDeclareService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实时申报管理Controller
 */
@Controller
@RequestMapping("/realTimeDeclare.do")
public class RealTimeDeclareController {

    @Autowired
    private RealTimeDeclareService realTimeDeclareService;

    @RequestMapping(params = "method=showRealTimeDeclarePage")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/realTimeDeclare";

    }

    /**
     * 指定id的小额贷款合同实时申报
     * @return
     */
    @RequestMapping(params = "method=sendRealTimeDeclare",produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel sendPettyLoanContract(String ids) throws Exception {

        ResultModel resultModel = realTimeDeclareService.sendPettyLoanContract(ids);
        return resultModel;

    }



}
