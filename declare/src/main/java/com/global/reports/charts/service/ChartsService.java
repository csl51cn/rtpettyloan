package com.global.reports.charts.service;

import java.util.List;
import java.util.Map;

import com.global.framework.exception.BaseException;

public interface ChartsService {

	
	/**
	 * 时间段业务统计查询
	 * @param startDate开始日期
	 * @param endDate结束日期
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBusinessLineData(String startDate, String endDate) throws Exception ;

	public List<Map<String, Object>> queryBizVolumeList(String startDate, String endDate) throws BaseException;
}
