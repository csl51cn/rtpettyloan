package com.global.reports.charts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.reports.charts.dao.ChartQueryDao;
import com.global.reports.charts.service.ChartsService;
import com.global.reports.charts.vo.ChartPointVo;

@Service("chartsService")
public class ChartsServiceImpl implements ChartsService {

	@Autowired
	private ChartQueryDao chartQueryDao;
	
	public List<Map<String,Object>> queryBusinessLineData(String startDate,String endDate) throws BaseException{
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<ChartPointVo>  rows1  = new ArrayList();
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			 rows1 = chartQueryDao.queryBusinessDayStatistics(startDate, endDate, "1");
		}
		
		List<ChartPointVo>  rows2  = new ArrayList();
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			 rows2 = chartQueryDao.queryBusinessDayStatistics(startDate, endDate, "2");
		}
		
		List<ChartPointVo>  rows3  = new ArrayList();
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			 rows3 = chartQueryDao.queryBusinessDayStatistics(startDate, endDate, "3");
		}
		
		List<ChartPointVo>  rows4  = new ArrayList();
		if(StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)){
			 rows4 = chartQueryDao.queryBusinessDayStatistics(startDate, endDate, "4");
		}
		
		Map<String,Object> line = new HashMap<String,Object>();
		line.put("name", "占用额度的结汇");
		line.put("data", rows1);
		data.add(line);
		line = new HashMap<String,Object>();
		line.put("name", "不占用额度的结汇");
		line.put("data", rows2);
		data.add(line);
		line = new HashMap<String,Object>();
		line.put("name", "占用额度的购汇");
		line.put("data", rows3);
		data.add(line);
		line = new HashMap<String,Object>();
		line.put("name", "不占用额度的购汇");
		line.put("data", rows4);
		data.add(line);
		return data;
	}
	
	public List<Map<String, Object>> queryBizVolumeList(String startDate, String endDate)
			throws BaseException {
		return this.chartQueryDao.queryBizVolumeList(startDate, endDate);
	}
}
