/**
 * 
 */
package com.global.reports.charts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.global.framework.exception.BaseException;
import com.global.reports.charts.service.ChartsService;

/**
 * @author longjun
 *
 */
@Controller
@RequestMapping("/charts.do")
public class ChartsController {
	
	@Autowired
	private ChartsService chartsService;
	/**
	 * 显示图形报表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "method=businessLine")
	public String statisticsChart(Model model){
		return "reports/charts/businessLine";
	}
	
	@RequestMapping(params = "method=bizVolumeReport")
	public String bizVolumeReport() {
		return "reports/charts/bizVolumeReport";
	}
	
	
	/**
	 * 获取图形报表数据
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryBusinessLineData",produces="text/plain;charset=UTF-8")
	public String queryBusinessLineData(String  startDate,String endDate) throws Exception{
		return JSON.toJSONString(chartsService.queryBusinessLineData(startDate, endDate));
	}
	
	/**
	 * 查询业务量-列表
	 * @throws BaseException 
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryBizVolumeList")
	public List<Map<String, Object>> queryBizVolumeList(String  startDate,String endDate) throws BaseException {
		return this.chartsService.queryBizVolumeList(startDate, endDate);
	}
}
