package com.global.fems.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实时申报管理Controller
 */
@Controller
@RequestMapping("/realTimeDeclare.do")
public class RealTimeDeclareController {


    @RequestMapping(params = "method=showRealTimeDeclare")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/realTimeDeclare";

    }


}
