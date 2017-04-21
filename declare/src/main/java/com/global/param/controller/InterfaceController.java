package com.global.param.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.global.fems.interfaces.FaceCodeEnum;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.Channel;
import com.global.param.domain.ChannelInterface;
import com.global.param.service.ChannelService;
import com.global.param.service.InterfaceService;
import com.global.web.BaseController;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
@Controller("/param/interfaceController.do")
public class InterfaceController extends BaseController {

	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping(params = "method=list")
	public String list(Model model) throws BaseException {
		List<Channel> list = this.channelService.getChannelList();
		model.addAttribute("channelList", list);
		return "param/interface/interfaceList";
	}

	@ResponseBody
	@RequestMapping(params = "method=query")
	public Map<String, Object> query(ChannelInterface info, PageBean page) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.interfaceService.queryChannelInterfaceForPage(info, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}
	
	@RequestMapping(params = "method=edit")
	public String edit(String channelId, Model model) throws BaseException {
		if(StringUtils.isNotBlank(channelId)){
			Channel info = this.channelService.queryChannelById(channelId);
			model.addAttribute("channel", info);
		}
		List<ChannelInterface> list = this.interfaceService.queryChannelInterface(channelId);
		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (ChannelInterface c : list) {
				sb.append(c.getFaceCode());
				sb.append(",");
			}
			model.addAttribute("channelFaceCodes", sb.deleteCharAt(sb.lastIndexOf(",")).toString());
		}
		return "param/interface/interfaceEdit";
	}
	
	@ResponseBody
	@RequestMapping(params = "method=getFaceCodes")
	public List<Map<String, String>> getFaceCodes() {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (FaceCodeEnum _enum : FaceCodeEnum.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", _enum.getCode());
			map.put("name", _enum.getValue());
			list.add(map);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(params = "method=save")
	public void save(String faceCodeList) throws BaseException {
		JSONObject json = JSON.parseObject(faceCodeList);
		String channelId = json.getString("channelId");
		String faceCode = json.getString("faceCodes");
		if (StringUtils.isBlank(faceCode)) {
			throw new BaseException("请选择接口名称");
		}
		String[] faceCodes = faceCode.split(",");
		List<ChannelInterface> list = new ArrayList<ChannelInterface>();
		for (int i = 0; i < faceCodes.length; i++) {
			ChannelInterface info = new ChannelInterface();
			info.setChannelId(channelId);
			info.setFaceCode(faceCodes[i]);
			info.setFaceName(FaceCodeEnum.getValueByCode(faceCodes[i]));
			info.setIsValid("Y");
			list.add(info);
		}
		this.interfaceService.saveChannelInterface(channelId, list);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=update")
	public void update(String channelId, String faceCode, String isValid) throws BaseException {
		ChannelInterface info = new ChannelInterface();
		info.setChannelId(channelId);
		info.setFaceCode(faceCode);
		info.setIsValid(isValid);
		this.interfaceService.updateChannelInterface(info);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=delete")
	public void delete(ChannelInterface info) throws BaseException {
		this.interfaceService.deleteChannelInterface(info);
	}
}
