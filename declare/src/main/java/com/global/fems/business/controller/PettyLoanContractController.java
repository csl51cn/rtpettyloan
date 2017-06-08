package com.global.fems.business.controller;

import com.global.fems.business.domain.PettyLoanContract;
import com.global.fems.business.service.PettyLoanContractService;
import com.global.fems.interfaces.validator.First;
import com.global.fems.interfaces.validator.Second;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实时网签合同信息管理Controller
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
     * 从Data_WorkInfo表中查询合同数据，批量保存到小额贷款合同表中
     *
     * @param ids Data_WorkInfo表中的Date_Id
     * @return 是否操作成功：1保存成功，0保存失败
     */
    @RequestMapping(params = "method=batchSaveContract")
    @ResponseBody
    public String batchSavePettyLoanContract(String ids) {
        try {
            contractService.batchSavePettyLoanContract(ids);
            return "1";
        } catch (BaseException e) {
            e.printStackTrace();
            return "0";
        }


    }

    /**
     * 保存或更新小额贷款合同---委托贷款
     *
     * @param contract 从页面接收参数，填充实体类
     * @param result   数据验证的错误返回
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveEntrustPettyLoanContract")
    public String saveEntrustPettyLoanContract(@Validated({First.class, Second.class}) PettyLoanContract contract, BindingResult result, Model model) {

        return saveOrUpdate(contract, result, model);

    }

    /**
     * 保存或更新小额贷款合同---自营贷款
     *
     * @param contract 从页面接收参数，填充实体类
     * @param result   数据验证的错误返回
     * @param model
     * @return
     */
    @RequestMapping(params = "method=savePettyLoanContract")
    public String savePettyLoanContract(@Validated({First.class}) PettyLoanContract contract, BindingResult result, Model model) {
        return saveOrUpdate(contract, result, model);

    }

    /**
     * 保存或更新小额贷款合同的通用方法
     *
     * @param contract
     * @param result
     * @param model
     * @return
     */
    private String saveOrUpdate(PettyLoanContract contract, BindingResult result, Model model) {
        try {
            //校验PettyLoanContract对象的数据是否完整
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新合同记录时，数据校验未通过");
            }

            contractService.saveOrUpdatePettyLoanContract(contract);
            model.addAttribute("msg", "1");//返回操作成功标志

        } catch (BaseException e) {
            e.printStackTrace();
            model.addAttribute("msg", "0");//返回操作失败标志
        }
        model.addAttribute("model", contract);
        return "business/pettyLoanContract/fillPettyLoanContract";
    }

    /**
     * 根据签约日期的时间段从业务系统的表中查询小额贷款合同记录
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
     * 根据小额贷款合同记录id记录
     *
     * @param id 小额贷款合同记录主键
     * @return 返回小额贷款合同记录
     */
    @RequestMapping(params = "method=findPettyLoanContractById")
    public String findPettyLoanContractById(String id, Model model) throws BaseException {
        PettyLoanContract pettyLoanContract = contractService.findPettyLoanContractById(id);
        model.addAttribute("model", pettyLoanContract);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillPettyLoanContract";
    }

    /**
     * 根据业务数据的date_id从表Data_WorkInfo查询合同记录
     *
     * @param dateId 业务数据的date_id
     * @return 返回小额贷款合同记录
     */
    @RequestMapping(params = "method=findPettyLoanContractByWorkInfoId")
    public String findPettyLoanContractByWorkInfoId(Integer dateId, Model model) throws BaseException {
        PettyLoanContract pettyLoanContract = contractService.findPettyLoanContractByWorkInfoId(dateId);
        model.addAttribute("model", pettyLoanContract);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillPettyLoanContract";
    }


    /**
     * 根据申报状态查询小额贷款合同记录
     *
     * @param sendStatusCode 申报状态，0未申报，1已申报
     * @param signStartDate  签约时间起始时间
     * @param signEndDate    签约时间终止时间
     * @param pageBean       接收分页参数
     * @return 返回根据申报状态查询的分页后小额贷款合同记录
     */
    @RequestMapping(params = "method=findPettyLoanContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findPettyLoanContractBySendStatus(Integer sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws BaseException {
        pageBean = contractService.findPettyLoanContractBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }

    /**
     * 根据合同编号从DC_PETTY_LOAN_CONTRACT查询合同信息
     * @param contractNo
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findPettyLoanContractByContractNo")
    @ResponseBody
    public  Map<String, Object> findPettyLoanContractByContractNo (String contractNo,PageBean pageBean) throws BaseException {
        pageBean = contractService.findPettyLoanContractByContractNo(contractNo,pageBean);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;
    }


    /**
     * 根据合同编号从业务系统查询合同信息
     * @param contractNo 合同编号
     * @param pageBean  查询结果
     * @return 返回easyui需要的total 和 rows格式的json数据
     * @throws BaseException
     */
    @RequestMapping(params = "method=findPettyLoanContractByContractNoFromBizSys")
    @ResponseBody
    public  Map<String, Object> findPettyLoanContractByContractNoFromBizSys(String contractNo,PageBean pageBean) throws BaseException {
        pageBean = contractService.findPettyLoanContractByContractNoFromBizSys(contractNo, pageBean);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", pageBean.getTotalRows());
        map.put("rows", pageBean.getDataList());
        return map;

    }



}
