package com.global.fems.business.controller;

import com.global.fems.business.service.RealTimeDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 指定时间范围内的小额贷款合同实时申报
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(params = "method=sendRealTimeDeclare")
    public String sendPettyLoanContract(String  startDate, String  endDate) throws Exception {
        realTimeDeclareService.sendPettyLoanContract(startDate, endDate);
        return "business/pettyLoanContract/realTimeDeclare";

    }



}
