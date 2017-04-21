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
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.service.LogService;
import com.global.web.BaseController;

/**
 * @author chen.feng
 * @date 2013-3-27 
 * @version v1.0
 */
@Controller
@RequestMapping("/sys/logController.do")
public class LogController extends BaseController {

	private static final Log log = LogFactory.getLog(LogController.class);
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(params = "method=list")
	public String list(Model model){
		model.addAttribute("operateType", Constants.LogType.values());
		return "sys/log/logList";
	}
	
	@RequestMapping(params = "method=query")
	@ResponseBody
	public Map<String, Object> query(OperateLog operateLog, PageBean page, Model model) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		try {
			if (operateLog != null && StringUtils.isNotBlank(operateLog.getStartDate())) {
				PageBean pageBean = this.logService.queryLogForPage(operateLog, page);
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
	public Map<String, Object> delete(String logId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.logService.deleteLog(logId);
			map.put(OP_MESSAGE, "删除日志成功！");
			map.put(OP_STATUS, true);
			log.info("删除日志成功，logId="+logId);
		} catch (Exception e) {
			map.put(OP_MESSAGE, "删除日志失败！");
			map.put(OP_STATUS, false);
			log.error("删除日志出错：", e);
		}
		return map;
	}
}
