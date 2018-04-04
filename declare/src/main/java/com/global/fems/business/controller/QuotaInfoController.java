package com.global.fems.business.controller;

import com.global.fems.business.service.QuotaInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author senlin.deng
 * @date 2018/4/3 14:57
 */
@Controller
@RequestMapping("/quotaInfo.do")
public class QuotaInfoController {

    @Autowired
    private QuotaInfoService quotaInfoService;


    /**
     * 跳转到合同信息页面
     *
     * @return
     */
    @RequestMapping(params = "method=showQuotaInfo")
    public String showQuotaInfo() {

        return "business/pettyLoanContract/fillQuotaInfo";

    }


    @RequestMapping(params = "method=findQuotaInfoByContractNoFromBizSys")
    @ResponseBody
    public Map<String, Object> findQuotaInfoByContractNoFromBizSys(String  contractNo, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findQuotaInfoByContractNoFromBizSys(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);

    }

    /**
     * 将PageBean中的总记录数和数据放到map中
     *
     * @param pageBean
     * @return
     */
    private Map<String, Object> pageBean2Map(PageBean pageBean) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }


}
