package com.global.fems.business.controller;

import com.global.fems.business.domain.ContractIssueInfo;
import com.global.fems.business.service.ContractIssueInfoService;
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
 * 贷款发放信息管理Controller
 */
@Controller
@RequestMapping("/contractIssueInfo.do")
public class ContractIssueInfoController extends BaseController {

    @Autowired
    private ContractIssueInfoService contractIssueInfoService;

    /**
     * 跳转到合同信息页面
     *
     * @return
     */
    @RequestMapping(params = "method=showContractIssueInfo")
    public String showContractInfo() {
        return "business/pettyLoanContract/fillContractIssueInfo";
    }

    /**
     * 批量保存合同信息
     *
     * @param ids
     * @return 是否操作成功：1保存成功，0保存失败
     */
    @RequestMapping(params = "method=batchSaveContract", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String batchSaveContract(String ids) throws DAOException {
        try {
            contractIssueInfoService.batchSaveContract(ids);
            return "1";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }


    /**
     * 根据dateId联合查询合同详细信息
     *
     * @param dateId
     * @param model
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=findContractByDateId")
    public String findContractByDateId(String dateId, Model model) throws DAOException {
        ContractIssueInfo contractIssueInfo = contractIssueInfoService.findContractByDateId(dateId);
        model.addAttribute("model", contractIssueInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillContractIssueInfo";
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
    @RequestMapping(params = "method=findContractInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findContractInfoByContractNo(String contractNo, String sendStatus, PageBean pageBean) throws DAOException {
        pageBean = contractIssueInfoService.findContractByContractNoFromContractInfo(contractNo.trim(), sendStatus, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 保存或更新带款放款信息
     *
     * @param contractIssueInfo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveContractIssueInfo")
    public String saveLoanContract(@Validated() ContractIssueInfo contractIssueInfo, BindingResult result, Model model) {
        return saveOrUpdate(contractIssueInfo, result, model);
    }


    /**
     * 保存或更新贷款合同的具体方法
     *
     * @param contractIssueInfo
     * @param result
     * @param model
     * @return
     */
    private String saveOrUpdate(ContractIssueInfo contractIssueInfo, BindingResult result, Model model) {
        try {
            //ContractIssueInfo,如果有错误将错误信息取出,返回页面
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新记录时，数据校验未通过");
            }
            if (contractIssueInfo.getIsSend() != null && contractIssueInfo.getIsSend() == 1) {
                contractIssueInfoService.declaredUpdate(contractIssueInfo);
            } else {
                contractIssueInfoService.saveOrUpdate(contractIssueInfo);
            }
            //返回操作成功标志
            model.addAttribute("msg", "1");

        } catch (Exception e) {
            e.printStackTrace();
            //返回操作失败标志
            model.addAttribute("msg", e.getLocalizedMessage());
        }
        model.addAttribute("model", contractIssueInfo);
        return "business/pettyLoanContract/fillContractIssueInfo";
    }

    /**
     * 根据申报状态查询和签约日期查询贷款放款信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findContractIssueInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findContractIssueInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        pageBean = contractIssueInfoService.findContractIssueInfoBySendStatus(sendStatusCode, signStartDate, signEndDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据申报状态查询和签约日期查询最新的贷款放款信息
     *
     * @param sendStatusCode
     * @param contractNo
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findLastContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findLastContractBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = contractIssueInfoService.findLastContractBySendStatus(sendStatusCode, contractNo, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 根据申报状态查询和签约日期查询贷款放款信息,申报时使用.由于前端页面上只有一个datagrid,它的field已经固定,
     * 没办法适应每个交易类型的字段名,后台将值的名称转换一下,才可以正确显示
     *
     * @param sendStatusCode
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findContractBySendStatus")
    @ResponseBody
    public Map<String, Object> findContractBySendStatus(String sendStatusCode, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = contractIssueInfoService.findContractBySendStatus(sendStatusCode, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 通过合同编号查询简略信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findContractBriefInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findContractBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = contractIssueInfoService.findContractBriefInfoByContractNo(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据id查询贷款放款信息
     *
     * @param id
     * @param model
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findContractIssueInfoById")
    public String findContractIssueInfoById(String id, Model model) throws DAOException {
        ContractIssueInfo contractIssueInfo = contractIssueInfoService.findContractIssueInfoById(id);
        model.addAttribute("model", contractIssueInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillContractIssueInfo";
    }

    /**
     * 已申报删除合同信息---单条记录
     *
     * @param contractIssueInfo
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=deleteRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecord(ContractIssueInfo contractIssueInfo) throws BaseException {
        ResultModel resultModel = contractIssueInfoService.deleteRecord(contractIssueInfo.getDateId() + "");
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
        ResultModel resultModel = contractIssueInfoService.deleteRecord(ids);
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
        ResultModel resultModel = contractIssueInfoService.setNotSend(ids);
        return resultModel;
    }




}
