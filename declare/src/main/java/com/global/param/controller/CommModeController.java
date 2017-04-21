package com.global.param.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.Channel;
import com.global.param.domain.CommMode;
import com.global.param.service.ChannelService;
import com.global.param.service.CommModeService;
import com.global.web.BaseController;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Controller
@RequestMapping("/param/commModeController.do")
public class CommModeController extends BaseController {

	@Autowired
	private CommModeService commModeService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping(params = "method=toList")
	public String toList(Model model) throws BaseException {
		List<Channel> list = this.channelService.getChannelList();
		model.addAttribute("channelList", list);
		return "param/interface/commModeList";
	}

	@ResponseBody
	@RequestMapping(params = "method=search")
	public Map<String, Object> search(CommMode info, PageBean page) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.commModeService.queryForPage(info, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}
	
	@RequestMapping(params = "method=toEdit")
	public String toEdit(String channelId, Model model, String flag) throws BaseException {
		if(StringUtils.isNotBlank(channelId)){
			Channel info = this.channelService.queryChannelById(channelId);
			model.addAttribute("channel", info);
		}
		CommMode info = this.commModeService.getCommMode(channelId);
		model.addAttribute("commMode", info);
		model.addAttribute("flag", flag);
		return "param/interface/commModeEdit";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=saveOrUpdate")
	public void saveOrUpdate(CommMode info) throws BaseException {
		this.commModeService.saveOrUpdate(info);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=del")
	public void del(CommMode info) throws BaseException {
		this.commModeService.delete(info);
	}
}
