package com.global.fems.business.controller;

import com.global.fems.business.service.RealTimeDeclareService;
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
    @RequestMapping(params = "method=sendRealTimeDeclare")
    @ResponseBody
    public String sendPettyLoanContract(String ids) throws Exception {
        try {
            realTimeDeclareService.sendPettyLoanContract(ids);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

    }



}
