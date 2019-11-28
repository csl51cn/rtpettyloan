package com.global.fems.business.controller;

import com.global.fems.business.service.BatchDeclareService;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 批量申报
     *
     * @param ids             记录的id,可以为多个
     * @param transactionType 交易类型
     * @return
     */
    @RequestMapping(params = "method=sendBatchFile", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel sendBatchFile(String ids, String transactionType, HttpServletRequest request) {
        try {
            return batchDeclareService.sendBatchFile(ids, transactionType, SessionManager.getSession(request).getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail();
        }

    }

    /**
     * 批量申报重新发送通知报文
     *
     * @param id      上报结果记录id
     * @param request
     * @return
     */
    @RequestMapping(params = "method=reReportFilePath", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel reReportFilePath(String id, HttpServletRequest request) {
        try {
            return batchDeclareService.reReportFilePath(id, SessionManager.getSession(request).getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail();
        }

    }


}
