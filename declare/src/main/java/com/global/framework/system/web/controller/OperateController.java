package com.global.framework.system.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.Operate;
import com.global.framework.system.service.OperateService;
import com.global.web.BaseController;

/**
 * @author chen.feng
 * @date 2013-3-25
 * @version v1.0
 */
@Controller
@RequestMapping("/sys/operateController.do")
public class OperateController extends BaseController {

	@Autowired
	private OperateService operateService;

	/**
	 * 新增权限
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=edit")
	public String edit(Model model) {
		return "sys/operate/operateEdit";
	}

	/**
	 * 保存权限
	 * 
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=save")
	public Operate save(Operate operate) throws BaseException {
		return this.operateService.saveOrUpdateOperate(operate);
	}

	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(String operateIds) throws BaseException {
		this.operateService.deleteOperate(operateIds);
	}

	/**
	 * 转到权限列表页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=list")
	public String list() {
		return "sys/operate/operateList";
	}

	/**
	 * 分页查询权限列表
	 * 
	 * @param user
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(Operate right, PageBean page)
			throws BaseException {
		Map<String, Object> result = new HashMap<String, Object>(2);
		PageBean pageBean = this.operateService
				.queryOperateForPage(right, page);
		result.put(LIST_TOTAL, pageBean.getTotalRows());
		result.put(LIST_ROWS, pageBean.getDataList());
		return result;
	}

	@ResponseBody
	@RequestMapping(params = "method=loadRightTree")
	public List<String> loadRightTree(String roleId) throws BaseException {
		return this.operateService.getRightTree(roleId);
	}

}
