package com.global.fems.business.controller;

import com.global.fems.business.domain.ContractInfoCycleNode;
import com.global.fems.business.interfaces.First;
import com.global.fems.business.interfaces.Second;
import com.global.fems.business.service.ContractInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
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
 * 贷款合同信息管理Controller
 */
@Controller
@RequestMapping("/contractInfo.do")
public class ContractInfoController extends BaseController {

    @Autowired
    private ContractInfoService contractInfoService;


    /**
     * 跳转到合同信息页面
     *
     * @return
     */
    @RequestMapping(params = "method=showContractInfo")
    public String showContractInfo() {

        return "business/pettyLoanContract/fillContractInfo";

    }

    /**
     * 保存自营贷款
     *
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveContract")
    public String saveLoanContract(@Validated({First.class}) ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        return saveOrUpdate(contractInfoCycleNode, result, model);
    }

    /**
     * 保存委托贷款
     *
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveEntrustedLoanContract")
    public String saveEntrustedLoanContract(@Validated({First.class, Second.class}) ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        return saveOrUpdate(contractInfoCycleNode, result, model);
    }

    /**
     * 保存或更新贷款合同的通用方法
     *
     * @param contractInfoCycleNode
     * @param result
     * @param model
     * @return
     */
    private String saveOrUpdate(ContractInfoCycleNode contractInfoCycleNode, BindingResult result, Model model) {
        try {
            //校验ContractInfoCycleNode对象的数据是否完整,如果有错误将错误信息取出,返回页面
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新合同记录时，数据校验未通过");
            }
            if (contractInfoCycleNode.getIsSend() != null && contractInfoCycleNode.getIsSend() == 1) {
                contractInfoService.declaredUpdate(contractInfoCycleNode);
            } else {
                contractInfoService.saveOrUpdate(contractInfoCycleNode);
            }
            model.addAttribute("msg", "1");//返回操作成功标志

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getLocalizedMessage());//返回操作失败标志
        }
        model.addAttribute("model", contractInfoCycleNode);
        return "business/pettyLoanContract/fillContractInfo";
    }


    /**
     * 批量保存合同信息
     *
     * @param ids
     * @return 是否操作成功：1保存成功，0保存失败
     */
    @RequestMapping(params = "method=batchSaveContract")
    @ResponseBody
    public String batchSaveContract(String ids) {
        try {
            contractInfoService.batchSaveContract(ids);
            return "1";
        } catch (Exception e) {

            return "0";
        }
    }

    /**
     * 根据dateId联合查询合同详细信息
     *
     * @param dateId
     * @param model
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findContractByDateId")
    public String findContractByDateId(String dateId, Model model) throws DAOException {
        ContractInfoCycleNode contractInfoCycleNode = contractInfoService.findContractByDateId(dateId);
        model.addAttribute("model", contractInfoCycleNode);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillContractInfo";
    }

    /**
     * 根据申报状态和合同号查询合同部分信息
     *
     * @param contractNo
     * @param sendStatus
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findContractByContractNoFromRealTimeContract")
    @ResponseBody
    public Map<String, Object> findContractByContractNoFromRealTimeContract(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {
        pageBean = contractInfoService.findContractByContractNoFromRealTimeContract(contractNo.trim(), sendStatus, pageBean);
        return pageBean2Map(pageBean);

    }

    /**
     * 根据合同号查询合同简略信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     */
    @RequestMapping(params = "method=findContractBriefInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = contractInfoService.findContractBriefInfoByContractNo(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 根据申报状态和签约时间段查询合同简略信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findContractBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        pageBean = contractInfoService.findContractBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据申报状态和签约时间段查询最新版本合同简略信息
     *
     * @param sendStatusCode
     * @param contractNo
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findLastContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findLastContractBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = contractInfoService.findLastContractBySendStatus(sendStatusCode, contractNo, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 根据id查询记录
     *
     * @param id
     * @param model
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findContractById")
    public String findContractById(String id, Model model) throws BaseException {
        ContractInfoCycleNode contractInfoCycleNode = contractInfoService.findContractById(id);
        model.addAttribute("model", contractInfoCycleNode);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillContractInfo";
    }


    /**
     * 已申报删除合同信息---单条记录
     *
     * @param contractInfoCycleNode
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=deleteRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecord(ContractInfoCycleNode contractInfoCycleNode) throws BaseException {
        ResultModel resultModel = contractInfoService.deleteRecord(contractInfoCycleNode.getDateId() + "");
        return resultModel;
    }

    /**
     * 已申报删除合同信息---多条记录
     *
     * @param ids
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=deleteRecordBatch", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecordBatch(String ids) throws BaseException {
        ResultModel resultModel = contractInfoService.deleteRecord(ids);
        return resultModel;
    }

    /**
     * 设置为未申报
     *
     * @param ids
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=setNotSend", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel setNotSend(String ids) throws BaseException {
        ResultModel resultModel = contractInfoService.setNotSend(ids);
        return resultModel;
    }




}
