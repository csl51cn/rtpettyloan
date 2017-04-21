package com.global.reports.charts.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.reports.charts.dao.ChartQueryDao;
import com.global.reports.charts.vo.ChartPointVo;

/**
 * 图形报表dao'
 * @author longjun
 *
 */
@Repository("chartsQueryDao")
public class ChartsQueryDaoImpl  extends BaseDaoSupport  implements ChartQueryDao{


	public List<ChartPointVo> queryBusinessDayStatistics(String startDate, String endDate, String type) throws BaseException {
		String sql = "";
		if ("1".equals(type)) {
			//占用额度结汇
			sql = "select a.x xs, nvl(b.y, 0) y                                                                   "
					+"	  from (select to_char(to_date(?, 'yyyy-MM-dd') + (level - 1), 'yyyy-MM-dd') x     "
					+"	          from dual                                                                           "
					+"	        connect by trunc(to_date(?, 'yyyy-MM-dd') + level - 1) <=                  "
					+"	                   trunc(to_date(?, 'yyyy-MM-dd'))) a,                             "
					+"	       (select t.x, count(1) y                                                                "
					+"	          from (select to_char(a.x, 'yyyy-MM-dd') x                                           "
					+"	                  from (select h.createdate x                                                 "
					+"	                          from wfl_taskinfo_his h                                              "
					+"	                         where h.tradeno in ('000040','000080') and to_char(h.createdate, 'yyyy-MM-dd') >= ?            "
					+"	                           and to_char(h.createdate, 'yyyy-MM-dd') <= ?) a) t      "
					+"	         group by t.x) b                                                                      "
					+"	 where b.x(+) = a.x                                                                           "
					+"	 order by a.x asc";                                                                        
		} else if ("2".equals(type)) {
			//不占用额度结汇
			sql = "select a.x xs, nvl(b.y, 0) y                                                                   "
					+"	  from (select to_char(to_date(?, 'yyyy-MM-dd') + (level - 1), 'yyyy-MM-dd') x     "
					+"	          from dual                                                                           "
					+"	        connect by trunc(to_date(?, 'yyyy-MM-dd') + level - 1) <=                  "
					+"	                   trunc(to_date(?, 'yyyy-MM-dd'))) a,                             "
					+"	       (select t.x, count(1) y                                                                "
					+"	          from (select to_char(a.x, 'yyyy-MM-dd') x                                           "
					+"	                  from (select h.createdate x                                                 "
					+"	                          from wfl_taskinfo_his h                                              "
					+"	                         where h.tradeno in ('000041','000081') and to_char(h.createdate, 'yyyy-MM-dd') >= ?            "
					+"	                           and to_char(h.createdate, 'yyyy-MM-dd') <= ?) a) t      "
					+"	         group by t.x) b                                                                      "
					+"	 where b.x(+) = a.x                                                                           "
					+"	 order by a.x asc";                                                                        
		} else if ("3".equals(type)) {
			//占用额度购汇
			sql = "select a.x xs, nvl(b.y, 0) y                                                                   "
					+"	  from (select to_char(to_date(?, 'yyyy-MM-dd') + (level - 1), 'yyyy-MM-dd') x     "
					+"	          from dual                                                                           "
					+"	        connect by trunc(to_date(?, 'yyyy-MM-dd') + level - 1) <=                  "
					+"	                   trunc(to_date(?, 'yyyy-MM-dd'))) a,                             "
					+"	       (select t.x, count(1) y                                                                "
					+"	          from (select to_char(a.x, 'yyyy-MM-dd') x                                           "
					+"	                  from (select h.createdate x                                                 "
					+"	                          from wfl_taskinfo_his h                                              "
					+"	                         where h.tradeno in ('000042','000082') and to_char(h.createdate, 'yyyy-MM-dd') >= ?            "
					+"	                           and to_char(h.createdate, 'yyyy-MM-dd') <= ?) a) t      "
					+"	         group by t.x) b                                                                      "
					+"	 where b.x(+) = a.x                                                                           "
					+"	 order by a.x asc";                                                                        
		} else if ("4".equals(type)) {
			//不占用额度购汇
			sql = "select a.x xs, nvl(b.y, 0) y                                                                   "
					+"	  from (select to_char(to_date(?, 'yyyy-MM-dd') + (level - 1), 'yyyy-MM-dd') x     "
					+"	          from dual                                                                           "
					+"	        connect by trunc(to_date(?, 'yyyy-MM-dd') + level - 1) <=                  "
					+"	                   trunc(to_date(?, 'yyyy-MM-dd'))) a,                             "
					+"	       (select t.x, count(1) y                                                                "
					+"	          from (select to_char(a.x, 'yyyy-MM-dd') x                                           "
					+"	                  from (select h.createdate x                                                 "
					+"	                          from wfl_taskinfo_his h                                              "
					+"	                         where h.tradeno in ('000043','000083') and to_char(h.createdate, 'yyyy-MM-dd') >= ?            "
					+"	                           and to_char(h.createdate, 'yyyy-MM-dd') <= ?) a) t      "
					+"	         group by t.x) b                                                                      "
					+"	 where b.x(+) = a.x                                                                           "
					+"	 order by a.x asc";                                                                        
		}
		Object[] param = new Object[]{startDate,startDate,endDate,startDate,endDate};
		return (List<ChartPointVo>) super.findForListBySql(sql, param,ChartPointVo.class);
	}
	
	public List<Map<String, Object>> queryBizVolumeList(String startDate, String endDate)
			throws BaseException {
		String sql = "select ttt.channelname,                                 "
			         +"      nvl(sum(zyjh), 0) + nvl(sum(zyjhbl), 0) zyjh,                                      "
			         +"      nvl(sum(bzyjh), 0) + nvl(sum(bzyjhbl), 0) bzyjh,                                    "
			         +"      nvl(sum(zygh), 0) + nvl(sum(zyghbl), 0) zygh,                                      "
			         +"      nvl(sum(bzygh), 0) + nvl( + sum(bzyghbl), 0) bzygh ,                                     "
			         +"      nvl(sum(zyjh),0) + nvl(sum(bzyjh),0) + nvl(sum(zygh),0) + nvl(sum(bzygh),0) + nvl(sum(zyjhbl),0) + nvl(sum(bzyjhbl),0) + nvl(sum(zyghbl), 0) + nvl(sum(bzyghbl), 0) totalcount "
			         +" from (select tt.channelname,                                      "
			         +"              decode(tt.tradeno, '000040', tt.totalcount) zyjh,    "
			         +"              decode(tt.tradeno, '000041', tt.totalcount) bzyjh,   "
			         +"              decode(tt.tradeno, '000042', tt.totalcount) zygh,    "
			         +"              decode(tt.tradeno, '000043', tt.totalcount) bzygh,    "
			         +"              decode(tt.tradeno, '000080', tt.totalcount) zyjhbl,  "  
                     +"              decode(tt.tradeno, '000081', tt.totalcount) bzyjhbl, "  
                     +"              decode(tt.tradeno, '000082', tt.totalcount) zyghbl,  " 
                     +"              decode(tt.tradeno, '000083', tt.totalcount) bzyghbl "
			         +"         from (select decode(t.channelid, '', '手工', (select channelcnname from pa_channel c where c.channelid=t.channelid)) channelname,"
			         +"                      t.tradeno,                          "
			         +"                      nvl(sum(1),0) totalcount                   "
			         +"                 from wfl_taskinfo_his t                   "
			         +"                where to_char(t.finishdate,'yyyy-mm-dd') >= ?  and to_char(t.finishdate,'yyyy-mm-dd') <= ? "
			         +"                group by t.channelid, t.tradeno) tt) ttt  "
			         +"group by ttt.channelname                                  ";
		return super.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
		
	}
}
