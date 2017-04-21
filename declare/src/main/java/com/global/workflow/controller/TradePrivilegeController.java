package com.global.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.web.BaseController;
import com.global.workflow.domain.TradePrivilege;
import com.global.workflow.service.TradePrivilegeService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Controller
@RequestMapping("/flow/tradePrivilegeController.do")
public class TradePrivilegeController extends BaseController {

	@Autowired
	private TradePrivilegeService tradePrivilegeService;
	
	@RequestMapping(params = "method=listTradePrivilege")
	public String listTradePrivilege(String tradeNo, Model model) throws BaseException {
		model.addAttribute("tradeNo", tradeNo);
		return "flow/listTradePrivilege";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=queryTradePrivilege")
	public List<TradePrivilege> queryTradePrivilege(String tradeNo) throws BaseException {
		return this.tradePrivilegeService.queryTradePrivilege(tradeNo);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=update")
	public Map<String,String> update(String privilegeForms) throws BaseException {
		Map<String,String> map = new HashMap<String,String>();
		try {
			this.tradePrivilegeService.update(privilegeForms);
			map.put("code", "0");
			map.put("message", "success");
		} catch (Exception e) {
			map.put("code", "01");
			map.put("message", "fail");
		}
		return map;
	}
	
}
