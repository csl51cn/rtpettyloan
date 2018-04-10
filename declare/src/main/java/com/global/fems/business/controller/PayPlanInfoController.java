package com.global.fems.business.controller;

import com.global.fems.business.domain.PayPlanInfo;
import com.global.fems.business.service.PayPlanInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.param.domain.ResultModel;
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
 * 还款计划信息管理Controller
 */
@Controller
@RequestMapping("/payPlanInfo.do")
public class PayPlanInfoController extends BaseController{
    @Autowired
    private PayPlanInfoService payPlanInfoService;


    /**
     * 跳转到编辑页面
     *
     * @return
     */
    @RequestMapping(params = "method=showPayPlanInfo")
    public String showPayPlanInfo() {
        return "business/pettyLoanContract/fillPayPlanInfo";
    }

    /**
     * 批量保存合同信息
     *
     * @param ids
     * @return 是否操作成功：1保存成功
     */
    @RequestMapping(params = "method=batchSaveInfo")
    @ResponseBody
    public String batchSaveContract(String ids) throws DAOException {
        try {
            payPlanInfoService.batchSaveContract(ids);
            return "1";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    /**
     * 从DC_CONTRACT_ISSUE_INFO表中根据合同编号查询合同信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     */

    @RequestMapping(params = "method=findContractInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findContractInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = payPlanInfoService.findContractInfoByContractNo(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询还款计划信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findPayPlanInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findPayPlanInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        pageBean = payPlanInfoService.findPayPlanInfoBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询最新还款计划信息,申报时使用
     *
     * @param sendStatusCode
     * @param contractNo
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findLastPayPlanInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findLastPayPlanInfoBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = payPlanInfoService.findLastPayPlanInfoBySendStatus(sendStatusCode, contractNo, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 从DC_PAYPLAN_INFO表中根据签约时间和发送状态查询最新还款计划信息,申报时使用
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findPayPlanBriefInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findPayPlanBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = payPlanInfoService.findPayPlanBriefInfoByContractNo(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 已申报批量删除记录
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=deleteRecordBatch")
    @ResponseBody
    public ResultModel deleteRecordBatch(String ids) throws DAOException {
        ResultModel resultModel = payPlanInfoService.deleteRecord(ids);
        return resultModel;
    }

    /**
     * 已申报删除单条记录
     *
     * @param payPlanInfo
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=deleteRecord")
    @ResponseBody
    public ResultModel deleteRecord(PayPlanInfo payPlanInfo) throws DAOException {
        ResultModel resultModel = payPlanInfoService.deleteRecord(payPlanInfo.getId());
        return resultModel;
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @param model
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findPayPlanInfoById")
    public String findPayPlanInfoById(String id, Model model) throws DAOException {
        PayPlanInfo payPlanInfo = payPlanInfoService.findPayPlanInfoById(id);
        model.addAttribute("model", payPlanInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillPayPlanInfo";
    }

    /**
     * 保存或更新带款放款信息
     *
     * @param payPlanInfo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=savePayPlanInfo")
    public String saveLoanContract(@Validated() PayPlanInfo payPlanInfo, BindingResult result, Model model) {
        try {
            //校验,如果有错误将错误信息取出,返回页面
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new DAOException("保存或更新记录时，数据校验未通过");
            }
            if (payPlanInfo.getIsSend() != null && payPlanInfo.getIsSend() == 1) {
                payPlanInfoService.declaredUpdate(payPlanInfo);
            } else {
                payPlanInfoService.saveOrUpdate(payPlanInfo);
            }
            model.addAttribute("msg", "1");//返回操作成功标志

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getLocalizedMessage());//返回操作失败标志
        }
        model.addAttribute("model", payPlanInfo);
        return "business/pettyLoanContract/fillPayPlanInfo";
    }

    /**
     * 设置为未申报
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=setNotSend")
    @ResponseBody
    public ResultModel setNotSend(String ids) throws DAOException {
        ResultModel resultModel = payPlanInfoService.setNotSend(ids);
        return resultModel;
    }


}
