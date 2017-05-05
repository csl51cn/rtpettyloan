package com.global.fems.business.controller;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.util.StringUtil;
import com.global.param.domain.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
     * @return showPettyLoanContract 展示合同信息空页面
     */
    @RequestMapping(params = "method=showPettyLoanContract")
    public String showPettyLoanContract() {

        return "business/pettyLoanContract/fillPettyLoanContract";

    }

    /**
     *保存或更新小额贷款合同
     * @param contract 从页面接收参数，填充实体类
     * @param result 数据验证的错误返回
     * @param model
     * @return
     */
    @RequestMapping(params = "method=savePettyLoanContract")
    public String savePettyLoanContract(@Valid PettyLoanContract contract, BindingResult result, Model model) {
        try {
            //校验PettyLoanContract对象的数据是否完整
            if (result.hasErrors() && result.getFieldErrorCount() > 0){
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error: list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新合同记录时，数据校验未通过");
            }
            if (StringUtil.isNullOrEmpty(contract.getSendStatus())) {
                contract.setSendStatus(0);//设置发送状态,0表示未发送，1表示已发送
                contract.setInsertDate(new Date());
            }
            contractService.saveOrUpdatePettyLoanContract(contract);
            model.addAttribute("msg","1");//返回操作成功标志

        } catch (BaseException e) {
            e.printStackTrace();
            model.addAttribute("msg","0");//返回操作失败标志
        }
        model.addAttribute("model", contract);
        return "business/pettyLoanContract/fillPettyLoanContract";

    }

    /**
     * 根据合同签订的时间段查询小额贷款合同记录
     *
     * @param startDate 合同签订时间的起始时间
     * @param endDate   合同签订时间的终止时间
     * @param pageBean  接收分页参数
     * @return 返回分页小额贷款合同记录
     */
    @RequestMapping(params = "method=findPettyLoanContractByDate")
    @ResponseBody
    public Map<String, Object> findPettyLoanContractByDate(String startDate, String endDate, PageBean pageBean) throws BaseException {


        pageBean = contractService.findPettyLoanContractByDate(startDate, endDate, pageBean);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }

    /**
     * 根据id查询小额贷款合同记录
     *
     * @param id 小额贷款合同主键
     * @return 返回小额贷款合同记录
     */
    @RequestMapping(params = "method=findpettyLoanContractById")
    public String findpettyLoanContractById(String id,Model model) throws BaseException {
        PettyLoanContract pettyLoanContract = contractService.findpettyLoanContractById(id);
        model.addAttribute("model",pettyLoanContract);
        model.addAttribute("disabled",true);
        return "business/pettyLoanContract/fillPettyLoanContract";
    }


    /**
     * 根据申报状态查询小额贷款合同记录
     *
     * @param sendStatusCode  申报状态，0未申报，1已申报
     * @param insertStartDate 录入时间起始时间
     * @param insertEndDate   录入时间终止时间
     * @param pageBean        接收分页参数
     * @return 返回根据申报状态查询的分页后小额贷款合同记录
     */
    @RequestMapping(params = "method=findPettyLoanContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findPettyLoanContractBySendStatus(Integer sendStatusCode, String insertStartDate, String insertEndDate, PageBean pageBean) throws BaseException {
        pageBean = contractService.findPettyLoanContractBySendStatus(sendStatusCode, insertStartDate, insertEndDate, pageBean);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }

}
