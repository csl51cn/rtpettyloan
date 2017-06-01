package com.global.fems.business.controller;

import java.util.HashMap;
import java.util.List;
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

import com.alibaba.fastjson.JSON;
import com.global.fems.business.domain.JSHMsg;
import com.global.fems.business.domain.SpfeAmtQuery;
import com.global.fems.business.domain.UsdCvsRate;
import com.global.fems.business.service.SafeExRateService;
import com.global.fems.business.service.SpfeQueryService;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.util.SysUtils;
import com.global.web.BaseController;

/**
 * 个人结售汇查询
 * 
 * @author Sili Jiang
 * @version 2015-07-06
 * 
 */
@Controller
@RequestMapping("/spfeQuery.do")
public class SpfeQueryController extends BaseController {

	private static final Logger log = Logger
			.getLogger(SpfeQueryController.class);
	
	@Autowired
	private SpfeQueryService spfeQueryService;
	@Autowired
	private SafeExRateService safeExRateService;

	/**
	 * 个人结售汇额度查询
	 * 
	 * @param model ModelMap
	 * @param spfeAmtQuery SpfeAmtQuery
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=spfeAmtQuery")
	public String spfeAmtQuery(ModelMap model, HttpServletRequest request) throws Exception {
		return "business/query/spfeAmtQuery";
	}

	/**
	 * 个人结售汇额度查询
	 * 
	 * @param model ModelMap
	 * @param spfeAmtQuery SpfeAmtQuery
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeAmtDataQuery", produces = "text/plain;charset=UTF-8")
	public String spfeAmtDataQuery(ModelMap model, SpfeAmtQuery spfeAmtQuery,HttpServletRequest request) throws BaseException, DataCheckException  {
		User user = super.getSessionUser(request);
		return JSON.toJSONString(spfeQueryService.spfeAmtQuery(spfeAmtQuery, user));
	}

	/**
	 * 个人结售汇额度登记指令查询列表
	 * 
	 * @param model ModelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=spfeRegisterList")
	public String spfeRegisterList(ModelMap model) throws Exception {
		return "business/query/spfeRegisterList";
	}

	/**
	 * 个人结售汇额度登记指令查询
	 * 
	 * @param model ModelMap
	 * @param registerQuery SpfeRegisterQuery
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeRegisterQuery")
	public Map<String, Object> spfeRegisterQuery(ModelMap model, PageBean page, HttpServletRequest request)
			throws Exception {
		String refNo = request.getParameter("refNo");
		String seqNo = request.getParameter("seqNo");
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put(LIST_ROWS, spfeQueryService.spfeRegisterQuery(refNo, seqNo));
		return map;
	}

	/**
	 * 美元折算率查询列表
	 * 
	 * @param model Model
	 * @return
	 */
	@RequestMapping(params = "method=usdCvsRateList")
	public String usdCvsRateList(Model model) throws BaseException {
		model.addAttribute("yearMonth", SysUtils.getNowDateTime("yyyyMM"));
		return "business/query/usdCvsRateList";
	}

	/**
	 * 美元折算率查询
	 * 
	 * @param model ModelMap
	 * @param usdCvsRate UsdCvsRate
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=usdCvsRateQuery")
	public Map<String, Object> usdCvsRateQuery(ModelMap model, PageBean page, UsdCvsRate usdCvsRate,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(2);
//		User user = SessionManager.getSession(request);
		if (StringUtils.isBlank(usdCvsRate.getCurrencyCode()) && StringUtils.isBlank(usdCvsRate.getYearMonth())) {
			return null;
		}
//		PageBean pageBean = spfeQueryService.usdCvsRateQuery(usdCvsRate.getCurrencyCode(), usdCvsRate.getYearMonth(),
//				page, user);
		PageBean pageBean = this.safeExRateService.queryForPage(usdCvsRate.getCurrencyCode(), usdCvsRate.getYearMonth(), page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}

	/**
	 * 结售汇信息查询列表
	 * 
	 * @param model Model
	 * @return
	 */
	@RequestMapping(params = "method=jshMsgList")
	public String jshMsgList(Model model) throws BaseException {
		return "business/query/jshMsgList";
	}

	/**
	 * 结售汇信息查询
	 * 
	 * @param model ModelMap
	 * @param jshMsg JSHMsg
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=jshMsgQuery")
	public Map<String, Object> jshMsgQuery(ModelMap model, PageBean page, JSHMsg jshMsg, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(2);
		User user = super.getSessionUser(request);
		PageBean pageBean = spfeQueryService.jshMsgQuery(jshMsg, page, user);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}

	/**
	 * 结售汇信息详细信息查询
	 * 
	 * @param model ModelMap
	 * @param refNo 业务参号
	 * @param tardeType 业务类型
	 * @param request HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=jshMsgDetailQuery")
	public String jshMsgDetailQuery(ModelMap model, String refNo, String tardeType, String bizType,
			HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		JSHMsg mode = spfeQueryService.jshMsgDetailQuery(refNo, tardeType, user);
		model.addAttribute("mode", mode);
		model.addAttribute("tardeType", tardeType);
		model.addAttribute("bizType", bizType);
		return "business/query/spfeLmtDetail";
	}
	
	/**
	 * 导入外管美元折算率
	 * @throws BaseException
	 * @throws DataCheckException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=importSafeExRate")
	public String importSafeExRate(String yearMonth, HttpServletRequest request, Model model) throws BaseException, DataCheckException {
		User user = super.getSessionUser(request);
		PageBean page = new PageBean();
		page.setPage(1);
		page.setRows(100);
		if (StringUtils.isBlank(yearMonth)) {
			yearMonth = SysUtils.getNowDateTime("yyyyMM");
		}
		PageBean pageBean = spfeQueryService.usdCvsRateQuery(null, yearMonth, page, user);
		List<UsdCvsRate> ret = (List<UsdCvsRate>) pageBean.getDataList();
		safeExRateService.insertSafeExRate(yearMonth, ret, user);
		return this.usdCvsRateList(model);
	}
	
	/**
	 * 沿用上一个月的美元折算率
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=copyPreMonthExRate")
	public String copyPreMonthExRate(HttpServletRequest request, Model model) throws BaseException {
		User user = super.getSessionUser(request);
		safeExRateService.copyPreMonthExRate(user);
		return this.usdCvsRateList(model);
	}
}
