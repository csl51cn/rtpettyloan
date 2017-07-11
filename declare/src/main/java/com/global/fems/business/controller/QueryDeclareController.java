package com.global.fems.business.controller;

import com.global.fems.business.enums.DataTypeEnum;
import com.global.fems.business.service.QueryDeclareService;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 上报结果查询管理Controller
 */
@Controller
public class QueryDeclareController {

    @Autowired
    private QueryDeclareService queryDeclareService;

    @RequestMapping(params = "method=showQueyDeclare")
    public String showQueyDeclare() {
        return "business/pettyLoanContract/queryDeclare";
    }

    @RequestMapping(params = "method=queryDeclare")
    @ResponseBody
    public ResultModel queryDeclare(String batchNo, String transactionType) {
        try {
            //获得上报的数据类型
            StringBuilder transType = new StringBuilder("PTLN");
            transType.append(transactionType.substring(1));
            transType.append("-" + transactionType);
            String dataType = DataTypeEnum.getTypeByValue(transType.toString());
            ResultModel resultModel = queryDeclareService.queryDeclared(batchNo, dataType);
            return resultModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
