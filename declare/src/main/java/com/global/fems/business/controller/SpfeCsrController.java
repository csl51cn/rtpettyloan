package com.global.fems.business.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.service.SpfeCsrService;
import com.global.fems.business.service.SpfeMdfService;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.CacheService;
import com.global.param.service.ChannelService;
import com.global.web.BaseController;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;
import com.global.workflow.service.TasklistService;
import com.pactera.fems.message.wg.constants.CertTypeEnum;
import com.pactera.fems.message.wg.constants.GHZJSXCodeEnum;
import com.pactera.fems.message.wg.constants.JHZJSXCodeEnum;
import com.pactera.fems.message.wg.constants.JHZJXTCodeEnum;

/**
 * @author longjun
 */
@Controller
@RequestMapping("/spfeCsr.do")
public class SpfeCsrController extends BaseController {
    private static final Logger log = Logger
            .getLogger(SpfeCsrController.class);
    @Autowired
    private SpfeCsrService spfeCsrService;
    @Autowired
    private SpfeMdfService spfeMdfService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private TasklistService tasklistService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysCommonService sysCommonService;

    /**
     * 主体信息页面
     *
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=hanlde")
    public String hanlde(ModelMap model, String txnSerialNo, String transState) throws Exception {
        if (txnSerialNo != null) {
            SpfeCsr mode = spfeCsrService.findById(txnSerialNo);
            model.put("mode", mode);
        }
        model.put("txnSerialNo", txnSerialNo);
        model.put("transState", transState);
        return "business/spfeCsr/spfeCsrHanlde";
    }

    @RequestMapping(params = "method=findSpfeInfo")
    public String findSpfeInfo(String txnSerialNo, String refNo, String tradeNo, String transState, ModelMap model) throws BaseException {
        SpfeMdf mode = new SpfeMdf();
        if (StringUtils.isBlank(txnSerialNo) && StringUtils.isBlank(refNo)) {
            throw new BaseException("查询条件不能全部为空");
        }
        if (StringUtils.isNotBlank(transState)) {
            //查看修改详细信息
            mode = spfeCsrService.findSpfeInfoByCsrId(txnSerialNo);
            mode.setREFNO(refNo);
        } else {
            if (StringUtils.isBlank(tradeNo)) {
                //先查出交易编号
                TaskInfoHis task = this.tasklistService.getTaskInfoHis(txnSerialNo, refNo);
                if (task == null) {
                    log.error("未查询到相关业务数据或该笔业务已做了修改或撤消[查询条件:前次业务流水号=" + txnSerialNo + ", 前次业务参号=" + refNo + "]");
                    model.addAttribute("error", "未查询到相关业务数据或该笔业务已做了修改或撤消");
                    return "business/spfeMdf/error";
                }
                tradeNo = task.getTradeNo();
            }
            if (tradeNo.equals("000040") || tradeNo.equals("000041") || tradeNo.equals("000042") || tradeNo.equals("000043")) {
                //查询bu_spfe_lmt
                mode = spfeMdfService.findSpfeMdfByLmtSeqNo(txnSerialNo, refNo, "Y");
            } else if (tradeNo.equals("000060")) {
                //查询bu_spfe_mdf
                mode = spfeMdfService.findSpfeMdfByMdfSeqNo(txnSerialNo, refNo, "Y");
            } else if (tradeNo.equals("000080") || tradeNo.equals("000081") || tradeNo.equals("000082") || tradeNo.equals("000083")) {
                //查询bu_spfe_mkup
                mode = spfeMdfService.findSpfeMkupBySeqNo(txnSerialNo, refNo, "Y");
            }
        }
        if (mode != null && StringUtils.isNotBlank(mode.getSEQNO())) {
            //需要重新生成修改交易的业务流水号，此处重置为空
            if (!"4".equals(transState)) {
                mode.setSEQNO("");
                mode.setBIZNO("");
            }
            model.put("TRADE_TYPE", mode.getTRADE_TYPE());
            model.put("tradeNo", "000060");
            model.put("OCCUPY_LMT_STATUS", mode.getOCCUPY_LMT_STATUS());//是否占用额度
        } else {
            log.error("未查询到相关业务数据或该笔业务已做了修改或撤消[查询条件:前次业务流水号=" + txnSerialNo + ", 前次业务参号=" + refNo + "]");
            model.addAttribute("error", "未查询到相关业务数据或该笔业务已做了修改或撤消");
            return "business/spfeMdf/error";
        }
        mode.setTradeNo(tradeNo);

        model.put("transState", transState);
        model.put("currencys", CacheService.getCurrencyCacheList());
        model.put("countrys", CacheService.getCountryCacheList());
        model.put("chinnels", channelService.getChannelList());
        model.put("transState", transState);
        model.put("txnSerialNo", txnSerialNo);
        model.put("mode", mode);
        return "business/spfeMdf/spfeMdfEdit";
    }

    @RequestMapping(params = "method=showPageDetail")
    public String showPageDetail(ModelMap model, String primariyBizNo, String refNo, String transState) throws Exception {

        String tradType = null;
        String OCCUPY_LMT_STATUS = null;
        String dispatcher = "";
        if (StringUtils.isNotBlank(refNo)) {
            primariyBizNo = spfeMdfService.getBizNoByRefNo(refNo);
        }
        boolean isDetail = StringUtils.isNotBlank(transState) && Arrays.asList(new String[]{"6", "7"}).contains(transState) ? true : false;
        if (primariyBizNo == null || primariyBizNo.contains("MF")) {//如果是修改数据撤销
            SpfeMdf mode = this.spfeMdfService.findSpfeMdfByMdfSeqNo(null, primariyBizNo, isDetail ? "N" : "Y");
            if (mode != null) {
                tradType = mode.getTRADE_TYPE();
                primariyBizNo = mode.getPrimaryBizNo();
                OCCUPY_LMT_STATUS = mode.getOCCUPY_LMT_STATUS();//是否占用额度
                mode.setSEQNO(null);
                mode.setBIZNO(null);
            }
            model.put("mode", mode);

            dispatcher = "business/spfeMdf/spfeMdfHanlde";
        } else {//录入数据撤销
            SpfeLmt mode = this.spfeMdfService.findNotCancelByBizNo(primariyBizNo, isDetail);
            if (mode != null) {
                primariyBizNo = mode.getBIZNO();
                tradType = mode.getTRADE_TYPE();
                OCCUPY_LMT_STATUS = mode.getOCCUPY_LMT_STATUS();//是否占用额度
                mode.setSEQNO(null);
                mode.setBIZNO(null);
            }
            model.put("mode", mode);
            dispatcher = "business/spfeLmt/spfeLmtHandle";
        }
        if (StringUtils.isBlank(tradType)) {//没有查询到业务
            primariyBizNo = null;//用于前台判断是否查询到业务，没有编号则为没查询到
        }
        model.put("primariyBizNo", primariyBizNo);
        model.put("tradeNo", "000050");//业务编号
        model.put("OCCUPY_LMT_STATUS", OCCUPY_LMT_STATUS);//是否站额
        model.put("transState", transState);//状态都以详细信息展示
        model.put("TRADE_TYPE", tradType);
        model.put("currencys", CacheService.getCurrencyCacheList());//币种
        model.put("countrys", CacheService.getCountryCacheList());//国家
        model.put("chinnels", channelService.getChannelList());//渠道
        return dispatcher;
    }

    /**
     * 保存
     *
     * @return
     * @throws BaseException
     */
    @RequestMapping(params = "method=doSave")
    @ResponseBody
    public SpfeCsr doSave(ModelMap model, SpfeCsr mode, HttpServletRequest request) throws Exception {
        User user = super.getSessionUser(request);
        return spfeCsrService.doHandle(mode, user, false, null);
    }

    /**
     * 提交
     *
     * @param model
     * @param mode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "method=doHandle")
    @ResponseBody
    public SpfeCsr doHandle(ModelMap model, SpfeCsr mode, HttpServletRequest request) throws Exception {
        User user = super.getSessionUser(request);
        return spfeCsrService.doHandle(mode, user, true, null);
    }


    /**
     * 复核操作
     *
     * @param txnSerialNo 业务流水号
     * @param taskId      任务ID
     * @param isAgree     是否同意
     * @param opinion     复核意见
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(params = "method=doCheckTask")
    public Map<String, Object> doCheckTask(String checkMsg,
                                           HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(checkMsg)) {
            throw new BaseException("复核信息不能为空");
        }
        User user = super.getSessionUser(request);
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            String isAgree = this.spfeCsrService.doCheck(checkMsg, user);
            ret.put("isAgree", isAgree);
            ret.put("status", true);
        } catch (DataCheckException e) {
            ret.put("status", false);
            if (e.getCause() instanceof DataCheckException) {
                DataCheckException dce = (DataCheckException) e;
                ret.put("errorMsg", dce.getCode() + ":" + dce.getReason());
            } else {
                ret.put("errorMsg", e.getMessage());
            }
            log.error("复核操作失败:", e);
        }
        return ret;
    }

    /**
     * 授权操作
     *
     * @param txnSerialNo 业务流水号
     * @param taskId      任务ID
     * @param isAgree     是否同意
     * @param opinion     复核意见
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(params = "method=doAuthTask")
    public Map<String, Object> doAuthTask(String checkMsg,
                                          HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(checkMsg)) {
            throw new BaseException("授权信息不能为空");
        }
        User user = super.getSessionUser(request);
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            String isAgree = this.spfeCsrService.doAuth(checkMsg, user);
            ret.put("isAgree", isAgree);
            ret.put("status", true);
        } catch (Exception e) {
            ret.put("status", false);
            if (e.getCause() instanceof DataCheckException) {
                DataCheckException dce = (DataCheckException) e;
                ret.put("errorMsg", dce.getCode() + ":" + dce.getReason());
            } else {
                ret.put("errorMsg", e.getMessage());
            }
            log.error("授权操作失败:", e);
        }
        return ret;
    }


    /**
     * 再次经办
     *
     * @param model
     * @param mode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "method=doAgain")
    @ResponseBody
    public void doAgain(ModelMap model, SpfeCsr mode, HttpServletRequest request) throws Exception {
        // mode = send(xmlUtil.toXml(mode));
        User user = super.getSessionUser(request);
        spfeCsrService.doAgain(mode, user);
    }

    /**
     * 购汇通知书打印
     *
     * @param model        ModelMap
     * @param seqNo        撤消业务流水号
     * @param spfeAmtQuery SpfeAmtQuery
     * @param request
     * @throws Exception
     */
    @RequestMapping(params = "method=occupyGHPrint")
    public String occupyGHPrint(ModelMap model, String seqNo, HttpServletRequest request) throws Exception {
        SpfeMdf mode = spfeCsrService.findSpfeInfoByCsrId(seqNo);
        SpfeCsr mode1 = spfeCsrService.findById(seqNo);
        mode.setREFNO(mode1.getRefNo());
        TaskInfo taskInfo = tasklistService.getTaskInfo(seqNo);
        User user = new User();
        if (taskInfo == null) {
            TaskInfoHis taskInfoHis = tasklistService.getTaskInfoHis(seqNo);
            Org org = CacheService.getOrgById(taskInfoHis.getTransOrgNo());
            user.setUserId(taskInfoHis.getCreateUser());
            model.addAttribute("orgName", org.getOrgName());
            model.addAttribute("bizTxTime", taskInfoHis.getCreateDate());
        } else {
            Org org = CacheService.getOrgById(taskInfo.getTransOrgNo());
            user.setUserId(taskInfo.getCreateUser());
            model.addAttribute("orgName", org.getOrgName());
            model.addAttribute("bizTxTime", taskInfo.getCreateDate());
        }
        User _user = userService.getUserInfo(user);
        CommonOrgUser commonOrgUser = sysCommonService.getCommonOrgUser(_user.getUserCode());
        mode.setIDTYPE_CODE(CertTypeEnum.getValueByCode(mode.getIDTYPE_CODE()));
        mode.setTXCCY(CacheService.getCurrencyCacheById(mode.getTXCCY()).getCnName());
        mode.setCTYCODE(CacheService.getCountryCacheById(mode.getCTYCODE()).getCnName());
        mode.setTX_CODE(GHZJSXCodeEnum.getValueByCode(mode.getTX_CODE()));
        model.addAttribute("mode", mode);
        model.addAttribute("mode1", mode1);
        model.addAttribute("sfcx", "Y");
        model.addAttribute("commonOrgUser", commonOrgUser);
        return "/business/spfeMdf/occupyGHPrint";
    }

    /**
     * 结汇通知书打印
     *
     * @param model        ModelMap
     * @param seqNo        撤消业务流水号
     * @param spfeAmtQuery SpfeAmtQuery
     * @param request
     * @throws Exception
     */
    @RequestMapping(params = "method=occupyJHPrint")
    public String occupyJHPrint(ModelMap model, String seqNo, HttpServletRequest request) throws Exception {


        SpfeMdf mode = spfeCsrService.findSpfeInfoByCsrId(seqNo);
        SpfeCsr mode1 = spfeCsrService.findById(seqNo);
        mode.setREFNO(mode1.getRefNo());
        TaskInfo taskInfo = tasklistService.getTaskInfo(seqNo);
        User user = new User();
        if (taskInfo == null) {
            TaskInfoHis taskInfoHis = tasklistService.getTaskInfoHis(seqNo);
            Org org = CacheService.getOrgById(taskInfoHis.getTransOrgNo());
            user.setUserId(taskInfoHis.getCreateUser());
            model.addAttribute("orgName", org.getOrgName());
            model.addAttribute("bizTxTime", taskInfoHis.getCreateDate());
        } else {
            Org org = CacheService.getOrgById(taskInfo.getTransOrgNo());
            user.setUserId(taskInfo.getCreateUser());
            model.addAttribute("orgName", org.getOrgName());
            model.addAttribute("bizTxTime", taskInfo.getCreateDate());
        }
        User _user = userService.getUserInfo(user);
        CommonOrgUser commonOrgUser = sysCommonService.getCommonOrgUser(_user.getUserCode());

        //结汇资金形态SALEFX_SETTLE_CODE结汇资金属性TX_CODE


        mode.setIDTYPE_CODE(CertTypeEnum.getValueByCode(mode.getIDTYPE_CODE()));
        mode.setTXCCY(CacheService.getCurrencyCacheById(mode.getTXCCY()).getCnName());
        mode.setCTYCODE(CacheService.getCountryCacheById(mode.getCTYCODE()).getCnName());
        log.debug("SALEFX_SETTLE_CODE=" + mode.getSALEFX_SETTLE_CODE());
        mode.setTX_CODE(JHZJSXCodeEnum.getValueByCode(mode.getTX_CODE()));
        mode.setSALEFX_SETTLE_CODE(JHZJXTCodeEnum.getValueByCode(mode.getSALEFX_SETTLE_CODE()));
        model.addAttribute("mode", mode);
        model.addAttribute("mode1", mode1);
        model.addAttribute("sfcx", "Y");
        model.addAttribute("commonOrgUser", commonOrgUser);
        return "/business/spfeMdf/occupyJHPrint";
    }
}
