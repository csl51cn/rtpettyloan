package com.global.workflow.controller;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.util.SysUtils;
import com.global.param.service.ChannelService;
import com.global.web.BaseController;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.service.TasklistService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
@Controller
@RequestMapping("/flow/tasklistController.do")
public class TasklistController extends BaseController {
	private static final Logger log = Logger.getLogger(TasklistController.class);

	@Autowired
	private TasklistService tasklistService;
	@Autowired
	private ChannelService channelService;

	@RequestMapping(params = "method=waitTaskList")
	public String waitTaskList() {
		return "flow/task/waitTaskList";
	}

	/**
	 * 查询待办任务列表
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryWaitTaskList")
	public Map<String, Object> queryWaitTaskList(TaskInfo task, PageBean page,
			HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		PageBean pageBean = tasklistService.queryWaitTaskListByUserID(task,
				user, page);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	@RequestMapping(params = "method=partTaskList")
	public String partTaskList() {
		return "flow/task/partTaskList";
	}

	/**
	 * 查询参与任务列表
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryPartTaskList")
	public Map<String, Object> queryPartTaskList(TaskInfo task, PageBean page,
			HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		PageBean pageBean = tasklistService.queryPartTaskListByUserID(task,
				user, page);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	@RequestMapping(params = "method=finishTaskList")
	public String finishTaskList(Model model) throws BaseException {
		//默认显示一个月
		TaskInfo task = new TaskInfo();
		String currentDate = SysUtils.getNowDate();
		String lastDate = SysUtils.getPreDayOfCurrentDate(SysUtils.getCurrentDate(), 30, "yyyy-MM-dd");
		task.setStartFinishTime(lastDate);
		task.setEndFinishTime(currentDate);
		model.addAttribute("channels", channelService.getChannelList());
		model.addAttribute("model", task);
		return "flow/task/finishTaskList";
	}

	/**
	 * 查询历史任务列表
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryFinishTaskList")
	public Map<String, Object> queryFinishTaskList(TaskInfo task,
			PageBean page, HttpServletRequest request) throws Exception {
		User user = super.getSessionUser(request);
		if (StringUtils.isBlank(task.getSfzx())) {
			//初始进入页面时sfzx为空，即默认只加载有效的数据
			task.setSfzx("Y");
		} else if ("ALL".equals(task.getSfzx())) {
			task.setSfzx("");
		}
		PageBean pageBean = tasklistService.queryFinishTaskListByUserID(task,
				user, page);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	/**
	 * 流程历史列表
	 * 
	 * @param model
	 * @param txnSerialNo
	 * @return
	 */
	@RequestMapping(params = "method=processHistoryList")
	public String processHistoryList(Model model, String txnSerialNo) {
		model.addAttribute("txnSerialNo", txnSerialNo);
		return "flow/task/processHistoryList";
	}

	/**
	 * 查询流程历史列表
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryFlowHistoryList")
	public Map<String, Object> queryFlowHistoryList(String txnSerialNo,
			PageBean page, HttpServletRequest request) throws Exception {
		PageBean pageBean = tasklistService.queryFlowHistoryList(txnSerialNo,
				page);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	@RequestMapping(params = "method=checkTask")
	public String checkTask(String txnSerialNo, String tradeNo, Model model) throws BaseException {
		model.addAttribute("txnSerialNo", txnSerialNo);
		model.addAttribute("tradeNo", tradeNo);
		try {
			model.addAttribute("url",this.tasklistService.queryTradeUnionMenu(tradeNo));
		} catch (BaseException e) {
			log.error("获取交易["+tradeNo+"]对应的URL失败", e);
			throw new BaseException("获取交易["+tradeNo+"]对应的URL失败:" + e.getMessage());
		}
		return "flow/task/doTask";
	}
	
	@RequestMapping(params = "method=authTask")
	public String authTask(String txnSerialNo, String tradeNo, Model model) throws BaseException {
		model.addAttribute("txnSerialNo", txnSerialNo);
		model.addAttribute("tradeNo", tradeNo);
		try {
			model.addAttribute("url",this.tasklistService.queryTradeUnionMenu(tradeNo));
		} catch (BaseException e) {
			log.error("获取交易["+tradeNo+"]对应的URL失败", e);
			throw new BaseException("获取交易["+tradeNo+"]对应的URL失败:" + e.getMessage());
		}
		return "flow/task/doTask";
	}

	/**
	 * 公共复核操作
	 * 
	 * @param txnSerialNo
	 *            业务流水号
	 * @param taskId
	 *            任务ID
	 * @param isAgree
	 *            是否同意
	 * @param opinion
	 *            复核意见
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(params = "method=doCheckTask")
	public Map<String, Object> doCheckTask(String checkMsg,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(checkMsg)) {
			throw new BaseException("复核信息不能为空");
		}
		User user = super.getSessionUser(request);
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String isAgree = this.tasklistService.doCheckTask(checkMsg, user.getUserId());
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (BaseException e) {
			ret.put("status", false);
			log.error("复核操作失败:", e);
			throw new BaseException(e);
		}
		return ret;
	}
	
	/**
	 * 公共授权操作
	 * 
	 * @param txnSerialNo
	 *            业务流水号
	 * @param taskId
	 *            任务ID
	 * @param isAgree
	 *            是否同意
	 * @param opinion
	 *            复核意见
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(params = "method=doAuthTask")
	public Map<String, Object> doAuthTask(String checkMsg,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(checkMsg)) {
			throw new BaseException("授权信息不能为空");
		}
		User user = super.getSessionUser(request);
		Map<String, Object> ret = new HashMap<String, Object>();
		try {
			String isAgree = this.tasklistService.doAuthTask(checkMsg, user.getUserId());
			ret.put("isAgree", isAgree);
			ret.put("status", true);
		} catch (BaseException e) {
			ret.put("status", false);
			log.error("授权操作失败:", e);
			throw new BaseException(e);
		}
		return ret;
	}
	
	/**
	 * 取消代办任务
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(params = "method=doCancelTask")
	public void doCancelTask(HttpServletRequest request,String txNo,String serviceBeanId) throws Exception{
		User user = super.getSessionUser(request);
		this.tasklistService.doCancelTask(txNo, serviceBeanId, user.getUserId());
	}
	
}
