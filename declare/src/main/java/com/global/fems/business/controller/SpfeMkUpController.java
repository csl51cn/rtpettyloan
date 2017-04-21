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

import com.global.fems.business.domain.SpfeMkUp;
import com.global.fems.business.enums.SpfeMkUpTradType;
import com.global.fems.business.service.SpfeMkUpService;
import com.global.fems.business.service.SpfeQueryService;
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
 * 个人结售汇信息补录
 * @author longjun
 *
 */
@Controller("/spfeMkUp.do")
public class SpfeMkUpController extends BaseController {

	private static final Logger log = Logger.getLogger(SpfeMkUpController.class);
	@Autowired
	private SpfeMkUpService spfeMkUpService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private SpfeQueryService spfeQueryService;
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
	@RequestMapping(params = "method=view")
	public String view(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMkUp mode = spfeMkUpService.findById(txnSerialNo);
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeMkUp/spfeMkUpHandle";
	}
	
	/**
	 * 占用额度的结汇信息补录
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=occupyJHMkUpHandle")
	public String occupyJHMkUpHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMkUp mode = spfeMkUpService.findById(txnSerialNo);
			mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "JH");
		model.put("tradeNo", SpfeMkUpTradType.TRADTYPE_OCCUPYJH_BL.getCode());
		model.put("OCCUPY_LMT_STATUS", "Y");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeMkUp/spfeMkUpHandle";
	}
	/**
	 * 占用额度的购汇信息补录
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=occupyGHMkUpHandle")
	public String occupyGHMkUpHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMkUp mode = spfeMkUpService.findById(txnSerialNo);
			mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "GH");
		model.put("tradeNo", SpfeMkUpTradType.TRADTYPE_OCCUPYGH_BL.getCode());
		model.put("OCCUPY_LMT_STATUS", "Y");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeMkUp/spfeMkUpHandle";
	}
	/**
	 * 不占用额度的结汇信息补录
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=notOccupyJHMkUpHandle")
	public String notOccupyJHMkUpHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMkUp mode = spfeMkUpService.findById(txnSerialNo);
			mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "JH");
		model.put("tradeNo", SpfeMkUpTradType.TRADTYPE_UNOCCUPYJH_BL.getCode());
		model.put("OCCUPY_LMT_STATUS", "N");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeMkUp/spfeMkUpHandle";
	}
	/**
	 * 不占用额度的购汇信息补录
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=notOccupyGHMkUpHandle")
	public String notOccupyGHMkUpHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeMkUp mode = spfeMkUpService.findById(txnSerialNo);
			mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "GH");
		model.put("tradeNo", SpfeMkUpTradType.TRADTYPE_UNOCCUPYGH_BL.getCode());
		model.put("OCCUPY_LMT_STATUS", "N");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeMkUp/spfeMkUpHandle";
	}

	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=doSave")
	@ResponseBody
	public SpfeMkUp doSave(ModelMap model, SpfeMkUp mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeMkUpService.doHandle(mode, user,false, null);
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
	public SpfeMkUp doHandle(ModelMap model, SpfeMkUp mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeMkUpService.doHandle(mode, user,true, null);
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
			String isAgree = this.spfeMkUpService.doCheck(checkMsg, user);
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (DataCheckException  e) {
			ret.put("status", false);
			log.error("复核操作失败:", e);
			throw new BaseException(e);
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
			String isAgree = this.spfeMkUpService.doAuth(checkMsg, user);
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (Exception e) {
			ret.put("status", false);
			log.error("授权操作失败:", e);
			ret.put("errorMsg", e.getMessage());
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
	public void doAgain(ModelMap model, SpfeMkUp mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		spfeMkUpService.doAgain(mode, user);
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
	public String occupyGHPrint(ModelMap model, String seqNo, String sfzy, HttpServletRequest request) throws Exception {
		SpfeMkUp mode = spfeMkUpService.findById(seqNo);				
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
		mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
		model.addAttribute("mode", mode);
		model.addAttribute("sfbl", "Y");
		model.addAttribute("sfzy", sfzy);
		model.addAttribute("commonOrgUser", commonOrgUser);		
		return "/business/spfeLmt/occupyGHPrint";
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
	public String occupyJHPrint(ModelMap model, String seqNo, String sfzy, HttpServletRequest request) throws Exception {
		SpfeMkUp mode = spfeMkUpService.findById(seqNo);				
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
		mode.setTX_CODE(JHZJSXCodeEnum.getValueByCode(mode.getTX_CODE()));
		mode.setSALEFX_SETTLE_CODE(JHZJXTCodeEnum.getValueByCode(mode.getSALEFX_SETTLE_CODE()));
		mode.setBIZ_TX_TIME(mode.getBIZ_TX_TIME().substring(0,10));
		model.addAttribute("mode", mode);
		model.addAttribute("sfbl", "Y");
		model.addAttribute("sfzy", sfzy);
		model.addAttribute("commonOrgUser", commonOrgUser);		
		return "/business/spfeLmt/occupyJHPrint";
	}
	
	
}
