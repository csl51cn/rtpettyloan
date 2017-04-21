package com.global.fems.business.controller;

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

import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.service.SpfeMdfService;
import com.global.framework.dbutils.support.PageBean;
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
 * 
 * @author longjun
 */
@Controller
@RequestMapping("/spfeMdf.do")
public  class SpfeMdfController extends BaseController {
	private static final Logger log = Logger
			.getLogger(SpfeMdfController.class);
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
	 * 主页面
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=hanlde")
	public String hanlde(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMdf mode = spfeMdfService.findById(txnSerialNo);
			model.put("mode", mode);
		}
		model.put("txnSerialNo", txnSerialNo);
		model.put("transState", transState);
		return "business/spfeMdf/spfeMdfHanlde";
	}
	
	/**
	 * 结汇 购汇 修改  
	 * @param model
	 * @param primariyBizNo
	 * @param transState
	 * @param tradType
	 * @param mdfSeqNo  spfeMdf 对象  流水号   
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=spfeLmtEdit")
	public String spfeLmtEdit(String txnSerialNo,String refNo, String tradeNo, String transState, ModelMap model) throws BaseException{
		SpfeMdf mode = new SpfeMdf();
		if (StringUtils.isBlank(txnSerialNo) && StringUtils.isBlank(refNo)) {
			throw new BaseException("查询条件不能全部为空");
		}
		if (StringUtils.isNotBlank(transState)) {
			//查看修改详细信息
			mode = spfeMdfService.findById(txnSerialNo);
		} else {
			if(StringUtils.isBlank(tradeNo)){
				//先查出交易编号
				TaskInfoHis task = this.tasklistService.getTaskInfoHis(txnSerialNo, refNo);
				if (task == null) {
					log.error("未查询到相关业务数据或该笔业务已做了修改或撤消[查询条件:前次业务流水号="+txnSerialNo+", 前次业务参号="+refNo+"]");
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
				mode = spfeMdfService.findSpfeMdfByMdfSeqNo(txnSerialNo, refNo , "Y");
			} else if (tradeNo.equals("000080") || tradeNo.equals("000081") || tradeNo.equals("000082") || tradeNo.equals("000083")) {
				//查询bu_spfe_mkup
				mode = spfeMdfService.findSpfeMkupBySeqNo(txnSerialNo, refNo , "Y");
			}
		}
		if(mode != null && StringUtils.isNotBlank(mode.getSEQNO())){
			//需要重新生成修改交易的业务流水号，此处重置为空
			if (!"4".equals(transState)){
				mode.setSEQNO("");
				mode.setBIZNO("");
			}
				
			model.put("TRADE_TYPE", mode.getTRADE_TYPE());
			model.put("tradeNo", "000060");
			model.put("OCCUPY_LMT_STATUS", mode.getOCCUPY_LMT_STATUS());//是否占用额度
		} else {
			log.error("未查询到相关业务数据或该笔业务已做了修改或撤消[查询条件:前次业务流水号="+txnSerialNo+", 前次业务参号="+refNo+"]");
			model.addAttribute("error", "未查询到相关业务数据或该笔业务已做了修改或撤消");
			return "business/spfeMdf/error";
		}
		mode.setTradeNo(tradeNo);
		model.put("transState", transState);
		model.put("currencys", CacheService.getCurrencyCacheList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("chinnels", channelService.getChannelList());
		model.put("transState", transState);
		model.put("mode", mode);
		return "business/spfeMdf/spfeMdfEdit";
	}
	
	/**
	 * 历史任务
	 * @return
	 */
	@RequestMapping(params = "method=finishTaskDialog")
	public String finishTaskDialog(){
		return "business/spfeMdf/finishTaskDialog";
	}
	
	
	/**
	 * 查询历史任务列表
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryFinishTaskData")
	public Map<String, Object> queryFinishTaskData(TaskInfo task,
			PageBean page, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(task.getStartFinishTime()) && StringUtils.isNotBlank(task.getEndFinishTime())) {
			PageBean pageBean = spfeMdfService.queryFinishTaskListByUserID(task, page);
			
			result.put(LIST_TOTAL, pageBean.getTotalRows());
			result.put(LIST_ROWS, pageBean.getDataList());
		}
		return result;
	}

	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=doSave")
	@ResponseBody
	public SpfeMdf doSave(ModelMap model, SpfeMdf mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeMdfService.doHandle(mode, user, false, null);
	}

	/**
	 * 提交
	 * @param model
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=doHandle")
	@ResponseBody
	public SpfeMdf doHandle(ModelMap model, SpfeMdf mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeMdfService.doHandle(mode, user, true, null);
	}
	
	
	/**
	 * 复核操作
	 * 
	 * @param txnSerialNo
	 *            业务流水号
	 * @param taskId
	 *            任务ID
	 * @param isAgree
	 *            是否同意
	 * @param opinion
	 *            复核意见
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
			String isAgree = this.spfeMdfService.doCheck(checkMsg, user);
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (DataCheckException  e) {
			ret.put("status", false);
			if (e.getCause() instanceof DataCheckException) {
				DataCheckException dce = (DataCheckException) e;
				ret.put("errorMsg",  dce.getCode() + ":" + dce.getReason());
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
	 * @param txnSerialNo
	 *            业务流水号
	 * @param taskId
	 *            任务ID
	 * @param isAgree
	 *            是否同意
	 * @param opinion
	 *            复核意见
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
			String isAgree = this.spfeMdfService.doAuth(checkMsg, user);
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
	 * @param model
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=doAgain")
	@ResponseBody
	public void doAgain(ModelMap model, SpfeMdf mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		spfeMdfService.doAgain(mode, user);
	}

	/**
	 * 购汇通知书打印
	 * 
	 * @param model ModelMap
	 * @param seqNo 业务流水号
	 * @param spfeAmtQuery SpfeAmtQuery
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "method=occupyGHPrint")
	public String occupyGHPrint(ModelMap model, String seqNo, HttpServletRequest request) throws Exception {
		SpfeMdf mode = spfeMdfService.findById(seqNo);				
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
		model.addAttribute("sfcx", "N");
		model.addAttribute("commonOrgUser", commonOrgUser);		
		return "/business/spfeMdf/occupyGHPrint";
	}
	/**
	 * 结汇通知书打印
	 * 
	 * @param model ModelMap
	 * @param seqNo 业务流水号
	 * @param spfeAmtQuery SpfeAmtQuery
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "method=occupyJHPrint")
	public String occupyJHPrint(ModelMap model, String seqNo, HttpServletRequest request) throws Exception {
		SpfeMdf mode = spfeMdfService.findById(seqNo);				
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
		//结汇资金形态SALEFX_SETTLE_CODE
		mode.setIDTYPE_CODE(CertTypeEnum.getValueByCode(mode.getIDTYPE_CODE()));
		mode.setTXCCY(CacheService.getCurrencyCacheById(mode.getTXCCY()).getCnName());
		mode.setCTYCODE(CacheService.getCountryCacheById(mode.getCTYCODE()).getCnName());
		mode.setTX_CODE(JHZJSXCodeEnum.getValueByCode(mode.getTX_CODE()));
		mode.setSALEFX_SETTLE_CODE(JHZJXTCodeEnum.getValueByCode(mode.getSALEFX_SETTLE_CODE()));
		
		model.addAttribute("mode", mode);
		model.addAttribute("sfcx", "N");
		model.addAttribute("commonOrgUser", commonOrgUser);		
		return "/business/spfeMdf/occupyJHPrint";
	}
	
}
