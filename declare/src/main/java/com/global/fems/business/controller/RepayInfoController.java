package com.global.fems.business.controller;

import com.global.fems.business.domain.RepayInfo;
import com.global.fems.business.service.RepayInfoService;
import com.global.framework.dbutils.support.DAOException;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.ResultModel;
import org.apache.commons.lang.StringUtils;
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
 * 贷款回收信息管理Controller
 */
@Controller
@RequestMapping("/repayInfo.do")
public class RepayInfoController {
    @Autowired
    private RepayInfoService repayInfoService;

    /**
     * 跳转到编辑页面
     *
     * @return
     */
    @RequestMapping(params = "method=showRepayInfo")
    public String showPayPlanInfo() {
        return "business/pettyLoanContract/fillRepayInfo";
    }

    /**
     * 根据合同号查询从业务系统还款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findRepayInfoByContractNoFromBizSys")
    @ResponseBody
    public Map<String, Object> findRepayInfoByContractNoFromBizSys(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = repayInfoService.findRepayInfoByContractNoFromBizSys(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据还款日期查询还款信息
     *
     * @param repayStartDate
     * @param repayStartDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findRepayInfoByRepayDateFromBizSys")
    @ResponseBody
    public Map<String, Object> findRepayInfoByRepayDateFromBizSys(String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        pageBean = repayInfoService.findRepayInfoByRepayDateFromBizSys(repayStartDate, repayEndDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 批量保存记录
     *
     * @param ids
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=batchSaveRepayInfo")
    @ResponseBody
    public String batchSaveRepayInfo(String ids) throws DAOException {
        try {
            repayInfoService.batchSaveRepayInfo(ids);
            return "1";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    /**
     * 根据Date_还款登记表id查询还款信息
     *
     * @param id    Date_还款登记表id
     * @param model
     * @return
     */
    @RequestMapping(params = "method=findRepayInfoByIdFromBizSys")
    public String findRepayInfoByIdFromBizSys(String id, Model model) throws DAOException {
        RepayInfo repayInfo = repayInfoService.findRepayInfoByIdFromBizSys(id);
        model.addAttribute("model", repayInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillRepayInfo";
    }

    /**
     * 保存或更新还款信息
     *
     * @param repayInfo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(params = "method=saveRepayInfo")
    public String saveRepayInfo(@Validated() RepayInfo repayInfo, BindingResult result, Model model) {
        try {
            //校验,如果有错误将错误信息取出,返回页面
            if (result.hasErrors() && result.getFieldErrorCount() > 0) {
                Map<String, String> erorrMap = new HashMap<String, String>();
                List<FieldError> list = result.getFieldErrors();
                for (FieldError error : list) {
                    erorrMap.put(error.getField(), error.getDefaultMessage());
                }
                model.addAttribute("errors", erorrMap);
                throw new DAOException("保存或更新记录时，数据校验未通过");
            }
            if(StringUtils.isEmpty(repayInfo.getPriPltyRate())){
                repayInfo.setPriPltyRate("0");
            }
            if(StringUtils.isEmpty(repayInfo.getDelayInterest())){
                repayInfo.setDelayInterest("0");
            }
            if(StringUtils.isEmpty(repayInfo.getDelayFee())){
                repayInfo.setDelayFee("0");
            }
            if (StringUtils.isEmpty(repayInfo.getDelayAmt())) {
                repayInfo.setDelayAmt("0");
            }
            if (repayInfo.getIsSend() != null && repayInfo.getIsSend() == 1) {
                repayInfoService.declaredUpdate(repayInfo);
            } else {
                repayInfoService.saveOrUpdate(repayInfo);
            }
            model.addAttribute("msg", "1");//返回操作成功标志
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getLocalizedMessage());//返回操作失败标志
        }
        model.addAttribute("model", repayInfo);
        return "business/pettyLoanContract/fillRepayInfo";
    }

    /**
     * 根据合同号从DC_REPAY_INFO查询还款信息
     *
     * @param contractNo
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findBriefInfoByContractNo")
    @ResponseBody
    public Map<String, Object> findBriefInfoByContractNo(String contractNo, PageBean pageBean) throws DAOException {
        pageBean = repayInfoService.findBriefInfoByContractNo(contractNo.trim(), pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据DC_REPAY_INFO的id查询还款信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(params = "method=findRepayInfoById")
    public String findRepayInfoById(String id, Model model) throws DAOException {
        RepayInfo repayInfo = repayInfoService.findRepayInfoById(id);
        model.addAttribute("model", repayInfo);
        model.addAttribute("disabled", true);
        return "business/pettyLoanContract/fillRepayInfo";
    }


    /**
     * 根据还款日期和发送状态查询还款信息
     *
     * @param sendStatusCode
     * @param repayStartDate
     * @param repayEndDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findRepayInfoBySendStatus")
    @ResponseBody
    public Map<String, Object> findRepayInfoBySendStatus(String sendStatusCode, String repayStartDate, String repayEndDate, PageBean pageBean) throws DAOException {
        pageBean = repayInfoService.findRepayInfoBySendStatus(sendStatusCode, repayStartDate, repayEndDate, pageBean);
        return pageBean2Map(pageBean);
    }

    /**
     * 根据还款日期和发送状态查询最新版本的还款信息
     *
     * @param sendStatusCode
     * @param startDate
     * @param endDate
     * @param pageBean
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=findLastRepayInfoSendStatus")
    @ResponseBody
    public Map<String, Object> findLastRepayInfoSendStatus(String sendStatusCode, String startDate, String endDate, PageBean pageBean) throws DAOException {
        pageBean = repayInfoService.findLastRepayInfoSendStatus(sendStatusCode, startDate, endDate, pageBean);
        return pageBean2Map(pageBean);
    }


    /**
     * 已申报删除合同信息---单条记录
     *
     * @param RepayInfo
     * @return
     * @throws DAOException
     */
    @RequestMapping(params = "method=deleteRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResultModel deleteRecord(RepayInfo RepayInfo) throws DAOException {
        ResultModel resultModel = repayInfoService.deleteRecord(RepayInfo.getId() + "");
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
    public ResultModel deleteRecordBatch(String ids) throws DAOException {
        ResultModel resultModel = repayInfoService.deleteRecord(ids);
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
    public ResultModel setNotSend(String ids) throws DAOException {
        ResultModel resultModel = repayInfoService.setNotSend(ids);
        return resultModel;
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

