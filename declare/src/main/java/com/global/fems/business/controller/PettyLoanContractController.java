package com.global.fems.business.controller;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 小额贷款合同管理Controller
 * Created by senlin.deng on 2017/4/28.
 */
@Controller
@RequestMapping("/pettyLoanContract.do")
public class PettyLoanContractController {

    @Autowired
    private PettyLoanContractService contractService;

    /**
     * 跳转到小额贷款合同页面
     *
     * @return showPettyLoanContract展示合同信息空页面
     */
    @RequestMapping(params = "method=showPettyLoanContract")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/fillPettyLoanContract";

    }

    /**
     * 保存小额贷款合同
     * @param contract
     * @return
     */
    @RequestMapping(params = "method=savePettyLoanContract")
    @ResponseBody
    public ResultModel savePettyLoanContract(PettyLoanContract contract) {
        try {
            contract.setSendStatus(0);//发送状态,0表示未发送，1表示已发送
            contractService.savePettyLoanContract(contract);
            return ResultModel.ok();
        } catch (BaseException e) {
            e.printStackTrace();
            return ResultModel.fail();//保存失败

        }

    }

    /**
     * 根据起止日期查询小额贷款合同
     * @return
     */
    @RequestMapping(params="method=findPettyLoanContractByDate")
    @ResponseBody
    public Map<String, Object> findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean){


        pageBean = contractService.findPettyLoanContractByDate(startDate, endDate, pageBean);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total",pageBean.getTotalRows());
        map.put("rows",pageBean.getDataList());
        return map;
    }

    /**
     * 根据id查询合同记录
     * @param id
     * @return
     */
    @RequestMapping(params = "method=findpettyLoanContractById")
    public String  findpettyLoanContractById(String id,Model model){
        PettyLoanContract pettyLoanContract = contractService.findpettyLoanContractById(id);
        model.addAttribute("model",pettyLoanContract);
        return  "business/pettyLoanContract/pettyLoanContractDetail";
    }

}
