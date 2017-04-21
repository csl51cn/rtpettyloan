package com.global.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.web.BaseController;
import com.global.workflow.domain.TradeTemplate;
import com.global.workflow.service.TradeTemplateService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Controller
@RequestMapping("/flow/tradeTemplateController.do")
public class TradeTemplateController extends BaseController {
	
	@Autowired
	private TradeTemplateService tradeTemplateService;
	
	@RequestMapping(params = "method=listTradeTemplate")
	public String listTradeTemplate() throws BaseException {
		return "flow/listTradeTemplate";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=queryTradeTemplate")
	public List<TradeTemplate> queryTradeTemplate() throws BaseException {
		return this.tradeTemplateService.queryTradeTemplate();
	}
	
	@ResponseBody
	@RequestMapping(params = "method=update")
	public Map<String, String> update(String tradeTempForms) throws BaseException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			tradeTemplateService.update(tradeTempForms);
			map.put("code", "0");
			map.put("messsage", "success");
		} catch (Exception e) {			
			map.put("code", "1");
			map.put("message", "fail");
		}
		return map;
	}
}
