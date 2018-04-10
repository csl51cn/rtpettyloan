package com.global.fems.business.controller;

import com.global.fems.business.domain.QuotaInfo;
import com.global.fems.business.service.QuotaInfoService;
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
 * @author senlin.deng
 * @date 2018/4/3 14:57
 */
@Controller
@RequestMapping("/quotaInfo.do")
public class QuotaInfoController extends BaseController {

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


    /**
     * 根据JK开头合同编号从业务系统查询简略的授信额度信息
     *
     * @param contractNoQuery
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaInfoByContractNoFromBizSys")
    @ResponseBody
    public Map<String, Object> findQuotaInfoByContractNoFromBizSys(String contractNoQuery, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findQuotaInfoByContractNoFromBizSys(contractNoQuery.trim(), pageBean);
        return pageBean2Map(pageBean);

    }

    /**
     * 根据dateId联合查询授信额度详细信息
     *
     * @param dateId
     * @param model
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaInfoByDateId")
    public String findQuotaInfoByDateId(String dateId, Model model) throws DAOException {
        QuotaInfo quotaInfo = quotaInfoService.findQuotaInfoByDateId(dateId);
        model.addAttribute("model", quotaInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillQuotaInfo";
    }


    /**
     * 保存或更新授信额度信息,未申报的
     *
     * @param quotaInfo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveQuotaInfo")
    public String saveQuotaInfo(@Validated() QuotaInfo quotaInfo, BindingResult result, Model model) throws DAOException {
        try {
            //quotaInfo,如果有错误将错误信息取出,返回页面
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> err = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    err.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", err);
                throw new BaseException("保存或更新记录时，数据校验未通过");
            }
            if (quotaInfo.getIsSend() != null && quotaInfo.getIsSend() == 1) {
                quotaInfoService.declaredUpdate(quotaInfo);
            } else {
                quotaInfoService.saveOrUpdate(quotaInfo);
            }
            //返回操作成功标志
            model.addAttribute("msg", "1");

        } catch (Exception e) {
            e.printStackTrace();
            //返回操作失败标志
            model.addAttribute("msg", e.getLocalizedMessage());
        }
        model.addAttribute("model", quotaInfo);
        return "business/pettyLoanContract/fillQuotaInfo";
    }


    /**
     * 批量保存合同信息
     *
     * @param ids
     * @return 是否操作成功：1保存成功，0保存失败
     */
    @RequestMapping(params = "method=batchSaveQuotaInfo")
    @ResponseBody
    public String batchSaveQuotaInfo(String ids) throws DAOException {
        try {
            quotaInfoService.batchSaveContract(ids);
            return "1";
        } catch (Exception e) {

            return "0";
        }
    }

    /**
     * 通过合同签订时间段从业务系统查询授信额度信息
     *
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaInfoByDateFromBizSys")
    @ResponseBody
    public Map<String, Object> findQuotaInfoByDateFromBizSys(String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findQuotaInfoByDateFromBizSys(signStartDate, signEndDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 根据jk开头合同编号从DC_QUOTA_INFO查询授信额度信息
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaBriefInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findQuotaBriefInfoByContractNo(String contractNoQuery, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findQuotaBriefInfoByContractNo(contractNoQuery, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 通过合同签订时间段从业务系统查询授信额度信息
     *
     * @param sendStatusCode
     * @param signStartDate
     * @param signEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findQuotaInfoBySendStatus(String sendStatusCode, String signStartDate, String signEndDate, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findQuotaInfoBySendStatus(sendStatusCode,signStartDate, signEndDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 根据id查询记录从DC_QUOTA_INFO中查询授信额度信息
     *
     * @param id
     * @param model
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findQuotaInfoById")
    public String findQuotaInfoById(String id, Model model) throws DAOException {
        QuotaInfo quotaInfo = quotaInfoService.findQuotaInfoById(id);
        model.addAttribute("model", quotaInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillQuotaInfo";
    }


    /**
     * 授信额度信息已申报删除---多条记录
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=deleteRecordBatch", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecordBatch(String ids) throws DAOException {
        ResultModel resultModel = quotaInfoService.deleteRecord(ids);
        return resultModel;
    }


    /**
     * 授信额度信息已申报删除---单条记录
     *
     * @param quotaInfo
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=deleteRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecord(QuotaInfo quotaInfo) throws DAOException {
        ResultModel resultModel = quotaInfoService.deleteRecord(quotaInfo.getDateId() + "");
        return resultModel;
    }


    /**
     * 根据id设置为未申报
     *
     * @param ids
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=setNotSend", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel setNotSend(String ids)throws DAOException{
        ResultModel resultModel = quotaInfoService.setNotSend(ids);
        return resultModel;
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
    @RequestMapping(params = "method=findLastQuotaInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findLastQuotaInfoBySendStatus(String sendStatusCode, String contractNo, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = quotaInfoService.findLastQuotaInfoBySendStatus(sendStatusCode, contractNo, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }



}
