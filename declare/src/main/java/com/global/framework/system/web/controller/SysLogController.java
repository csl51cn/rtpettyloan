package com.global.framework.system.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.system.constants.Constants;
import com.global.framework.system.domain.ExceptionLog;
import com.global.framework.system.service.SysLogService;
import com.global.web.BaseController;
 
/**
 * @author bai.shulun
 * @date 2015-7-28
 * 
 */

@Controller
@RequestMapping("/sys/sysLogController.do")
public class SysLogController extends BaseController{
	
	private static final Log log=LogFactory.getLog(SysLogController.class);
	
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping(params = "method=list")
	public String list(Model model){
		model.addAttribute("operateType", Constants.LogType.values());
		return "sys/log/sysLogList";
	}
	
	@RequestMapping(params = "method=query")
	@ResponseBody
	public Map<String, Object> query(ExceptionLog info, PageBean page, Model model) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		try {
			if (StringUtils.isNotBlank(info.getStartDate()) && StringUtils.isNotBlank(info.getEndDate())) {
				PageBean pageBean = this.sysLogService.queryLogForPage(info, page);
				map.put(LIST_TOTAL, pageBean.getTotalRows());
				map.put(LIST_ROWS, pageBean.getDataList());
			}
		} catch (Exception e) {
			log.error("查询日志信息列表出错：", e);
		}
		return map;
	}
	
	@RequestMapping(params = "method=delete")
	@ResponseBody
	public Map<String, Object> delete(String exceptionLogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.sysLogService.delete(exceptionLogId);
			map.put(OP_MESSAGE, "删除日志成功！");
			map.put(OP_STATUS, true);
			log.info("删除日志成功，logId=" + exceptionLogId);
		} catch (Exception e) {
			map.put(OP_MESSAGE, "删除日志失败！");
			map.put(OP_STATUS, false);
			log.error("删除日志出错：", e);
		}
		return map;
	}
	
	@RequestMapping(params = "method=findById")
	public String findById(String exceptionLogId, Model model){
		try {
			ExceptionLog info = this.sysLogService.findById(exceptionLogId);
			model.addAttribute("exceptionLog", info.getExceptionLog());
		} catch (Exception e) {
			log.error("删除日志出错：", e);
		}

	return "sys/log/exceptionLogView";
	
}
	
}
