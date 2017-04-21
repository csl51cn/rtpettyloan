package com.global.param.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.fems.interfaces.BizChnlCodeEnum;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.Channel;
import com.global.param.service.ChannelService;
import com.global.web.BaseController;

@Controller
@RequestMapping("/param/channelController.do")
public class ChannelController extends BaseController {
	
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping(params = "method=list")
	public String list() {
		return "param/channel/channelList";
	}

	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(Channel channel, PageBean page) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.channelService.queryChannelForPage(channel, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}
	
	@RequestMapping(params = "method=edit")
	public String edit(Channel channel, Model model) throws BaseException {
		if(channel != null && StringUtils.isNotBlank(channel.getChannelId())){
			channel = this.channelService.queryChannelById(channel.getChannelId());
			model.addAttribute("channel", channel);
		}
		model.addAttribute("bizChnlCodeList", BizChnlCodeEnum.values());
		return "param/channel/channelEdit";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=save")
	public Channel save(Channel channel) throws BaseException {
		return this.channelService.saveOrUpdateChannel(channel);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(Channel channel) throws BaseException {
		this.channelService.deleteChannel(channel);
	}
	
	/**
	 * 检查渠道代码是否存在
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=checkChannelCodeExist")
	public boolean checkChannelCodeExist(HttpServletRequest request) throws BaseException {
		String channelCode = request.getParameter("param");
		return this.channelService.checkChannelCodeExist(channelCode);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=checkReqSysCodeExist")
	public boolean checkReqSysCodeExist(HttpServletRequest request) throws BaseException {
		String reqSysCode = request.getParameter("param");
		return this.channelService.checkReqSysCodeExist(reqSysCode);
	}

}
