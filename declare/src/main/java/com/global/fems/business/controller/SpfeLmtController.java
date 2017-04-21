package com.global.fems.business.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.enums.SpfeLmtTradType;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.business.service.SpfeLmtService;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.CommonOrgUser;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.User;
import com.global.framework.system.service.SysCommonService;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.CacheService;
import com.global.framework.util.DateTimeUtil;
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
@RequestMapping("/spfeLmt.do")
public  class SpfeLmtController extends BaseController {

	private static final Logger log = Logger
			.getLogger(SpfeLmtController.class);

	@Autowired
	private SpfeLmtService spfeLmtService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private TasklistService tasklistService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysCommonService sysCommonService;
	@Autowired
	private SafeExRateService safeExRateService;
	
	
	/**
	 * 获取折美元金额
	 * @return
	 * @throws DataCheckException 
	 */
	@ResponseBody
	@RequestMapping(params = "method=querySafeExRate")
	public String querySafeExRate(String txCur, String txAmt, HttpServletRequest request) throws Exception{
		return safeExRateService.transTxAmtToUsdAmt(txAmt, txCur);
	}
	
	/**
	 * 预关注风险提示页面
	 * 
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "method=agentCord")
	public String agentCord(String personName,String idType, String idCode, String dueTime, String reason, ModelMap model) throws UnsupportedEncodingException{
		print(model, personName, idType, idCode, dueTime, reason);
		return "business/spfeLmt/agentCord";
	}
	
	
	/**
	 * 关注名单告知页面
	 * 
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params = "method=riskCue")
	public String riskCue(String personName,String idType, String idCode, String dueTime, String reason, ModelMap model) throws UnsupportedEncodingException{
		print(model, personName, idType, idCode, dueTime, reason);
		return "business/spfeLmt/riskCue";
	}
	
	
	/**
	 * 占用额度的结汇信息录入
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=occupyJHLmtHandle")
	public String occupyJHLmtHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeLmt mode = spfeLmtService.findById(txnSerialNo);
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "JH");
		model.put("tradeNo", SpfeLmtTradType.TRADTYPE_OCCUPYJH.getCode());
		model.put("OCCUPY_LMT_STATUS", "Y");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeLmt/spfeLmtHandle";
	}
	/**
	 * 占用额度的购汇信息录入
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=occupyGHLmtHandle")
	public String occupyGHLmtHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeLmt mode = spfeLmtService.findById(txnSerialNo);
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "GH");
		model.put("tradeNo", SpfeLmtTradType.TRADTYPE_OCCUPYGH.getCode());
		model.put("OCCUPY_LMT_STATUS", "Y");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeLmt/spfeLmtHandle";
	}
	/**
	 * 不占用额度的结汇信息录入
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=notOccupyJHLmtHandle")
	public String notOccupyJHLmtHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeLmt mode = spfeLmtService.findById(txnSerialNo);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=mode.getBIZ_TX_TIME() && !"".equals(mode.getBIZ_TX_TIME())){
				mode.setBIZ_TX_TIME(sdf.format(sdf.parse(mode.getBIZ_TX_TIME())));
			}
			model.put("mode", mode);
		}else{
			SpfeLmt mode = new SpfeLmt();
			mode.setBIZ_TX_TIME(DateTimeUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "JH");
		model.put("tradeNo", SpfeLmtTradType.TRADTYPE_UNOCCUPYJH.getCode());
		model.put("OCCUPY_LMT_STATUS", "N");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeLmt/spfeLmtHandle";
	}
	/**
	 * 不占用额度的购汇信息录入
	 * 
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(params = "method=notOccupyGHLmtHandle")
	public String notOccupyGHLmtHandle(ModelMap model,String txnSerialNo,String transState) throws Exception {
		if(txnSerialNo!=null){
			SpfeLmt mode = spfeLmtService.findById(txnSerialNo);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=mode.getBIZ_TX_TIME() && !"".equals(mode.getBIZ_TX_TIME())){
				mode.setBIZ_TX_TIME(sdf.format(sdf.parse(mode.getBIZ_TX_TIME())));
			}
			model.put("mode", mode);
		}else{
			SpfeLmt mode = new SpfeLmt();
			mode.setBIZ_TX_TIME(DateTimeUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
			model.put("mode", mode);
		}
		model.put("transState", transState);
		model.put("TRADE_TYPE", "GH");
		model.put("tradeNo", SpfeLmtTradType.TRADTYPE_UNOCCUPYGH.getCode());
		model.put("OCCUPY_LMT_STATUS", "N");//是否占用额度
		model.put("chinnels", channelService.getChannelList());
		model.put("countrys", CacheService.getCountryCacheList());
		model.put("currencys", CacheService.getCurrencyCacheList());
		return "business/spfeLmt/spfeLmtHandle";
	}

	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=doSave")
	@ResponseBody
	public SpfeLmt doSave(ModelMap model, SpfeLmt mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeLmtService.doHandle(mode, user,false, null);
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
	public SpfeLmt doHandle(ModelMap model, SpfeLmt mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		return spfeLmtService.doHandle(mode, user,true, null);
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
			String isAgree = this.spfeLmtService.doCheck(checkMsg, user);
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (Exception  e) {
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
			HttpServletRequest request) throws Exception,DataCheckException {
		if (StringUtils.isBlank(checkMsg)) {
			throw new BaseException("授权信息不能为空");
		}
		User user = super.getSessionUser(request);
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String isAgree = this.spfeLmtService.doAuth(checkMsg, user);
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
	public void doAgain(ModelMap model, SpfeLmt mode, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		spfeLmtService.doAgain(mode, user);
	}
	
	
	/**
	 * 预关注风险提示打印
	 * 
	 * @param model ModelMap
	 * @param personName 姓名
	 * @param idCode 证件号码
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "method=riskCuePrint")
	public String riskCuePrint(ModelMap model, String personName, String idType, String idCode, String dueTime, String reason, HttpServletRequest request) throws Exception {
		print(model, personName, idType, idCode, dueTime, reason);
		return "/business/spfeLmt/riskCuePrint";
	}
	
	/**
	 * 关注名单告知打印
	 * 
	 * @param model ModelMap
	 * @param personName 姓名
	 * @param idCode 证件号码
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "method=agentCordPrint")
	public String agentCordPrint(ModelMap model, String personName, String idType, String idCode, String dueTime, String reason, HttpServletRequest request) throws Exception {
		print(model, personName, idType, idCode, dueTime, reason);
		return "/business/spfeLmt/agentCordPrint";
	}

	private void print(ModelMap model, String personName, String idType, String idCode, String dueTime, String reason)
			throws UnsupportedEncodingException {
		model.addAttribute("idCode", idCode);
		model.addAttribute("dueTime", dueTime);
		model.addAttribute("personName", URLDecoder.decode(personName,"UTF-8"));
		model.addAttribute("reason", URLDecoder.decode(reason,"UTF-8"));
		model.addAttribute("idType", URLDecoder.decode(idType,"UTF-8"));
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
		SpfeLmt mode = spfeLmtService.findById(seqNo);				
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
		model.addAttribute("sfbl", "N");
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
		SpfeLmt mode = spfeLmtService.findById(seqNo);				
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
		mode.setTX_CODE(JHZJSXCodeEnum.getValueByCode(mode.getTX_CODE()));
		mode.setSALEFX_SETTLE_CODE(JHZJXTCodeEnum.getValueByCode(mode.getSALEFX_SETTLE_CODE()));
		model.addAttribute("mode", mode);
		model.addAttribute("sfbl", "N");
		model.addAttribute("sfzy", sfzy);
		model.addAttribute("commonOrgUser", commonOrgUser);		
		return "/business/spfeLmt/occupyJHPrint";
	}
}
