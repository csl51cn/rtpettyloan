package com.global.reports.charts.dao;

import java.util.List;
import java.util.Map;

import com.global.framework.exception.BaseException;
import com.global.reports.charts.vo.ChartPointVo;

public interface ChartQueryDao {


	/**
	 * 查询业务量按日统计
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 * @throws BaseException
	 */
	public List<ChartPointVo>  queryBusinessDayStatistics(String startDate, String endDate, String type) throws BaseException;

	public List<Map<String, Object>> queryBizVolumeList(String startDate, String endDate) throws BaseException;
}
