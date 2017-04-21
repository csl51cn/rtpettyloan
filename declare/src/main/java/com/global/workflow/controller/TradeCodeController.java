package com.global.workflow.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.framework.system.web.common.CacheService;
import com.global.web.BaseController;
import com.global.workflow.domain.TradeCode;
import com.global.workflow.service.TradeCodeService;

/**
 * 
 * @author chen.feng
 * @date 2014-7-6 下午12:15:40
 */
@Controller
@RequestMapping("/flow/tradeCodeController.do")
public class TradeCodeController extends BaseController {
	
	@Autowired
	private TradeCodeService tradeCodeService;

	@ResponseBody
	@RequestMapping(params = "method=getTradeCode")
	public List<TradeCode> getTradeCode() {
		return CacheService.getTradeCodeCacheList();
	}
	
	/**
	 * 进入交易编号列表页面
	 * @return
	 */
	@RequestMapping(params = "method=listTradeCode")
	public String listTradeCode() throws BaseException {
		return "flow/tradecode";
	}
	
	/**
	 * 查询交易编号列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "method=getTradeCodeList")
	public List<TradeCode> getTradeCodeList() throws BaseException {
		return this.tradeCodeService.getTradeCodeList();
	}
	
	/**
	 * 保存交易编号信息
	 * @param tradeCodes
	 */
	@ResponseBody
	@RequestMapping(params = "method=saveTradeCode")
	public void saveTradeCode(String tradeCodes) throws BaseException {
		if (StringUtils.isBlank(tradeCodes)) {
			throw new BaseException("交易编号信息为空");
		}
		this.tradeCodeService.saveTradeCode(tradeCodes);
	}
}
