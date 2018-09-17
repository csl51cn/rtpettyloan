package com.global.fems.business.controller;

import com.global.fems.business.service.QueryDeclareService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.framework.util.DateTimeUtil;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 上报结果查询管理Controller
 */
@Controller
@RequestMapping("/queryDeclare.do")
public class QueryDeclareController extends BaseController {

    @Autowired
    private QueryDeclareService queryDeclareService;

    @RequestMapping(params = "method=showQueyDeclare")
    public String showQueyDeclare() {
        return "business/pettyLoanContract/queryDeclare";
    }

    @RequestMapping(params = "method=queryDeclare")
    @ResponseBody
    public ResultModel queryDeclare(String id, HttpServletRequest request) {
        try {
            ResultModel resultModel = queryDeclareService.queryDeclared(id, SessionManager.getSession(request).getUserId());
            return resultModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(params = "method=queryRawDeclareData")
    @ResponseBody
    public Map queryRawDeclareData(String batchNo, String transactionType, String startDate, String endDate, PageBean pageBean) {
        pageBean = queryDeclareService.queryRawDeclareData(batchNo, transactionType, startDate, DateTimeUtil.toMidNight(endDate) , pageBean);
        return pageBean2Map(pageBean);
    }


}
