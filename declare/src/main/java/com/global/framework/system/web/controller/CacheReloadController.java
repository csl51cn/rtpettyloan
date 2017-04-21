package com.global.framework.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.framework.system.web.common.CacheService;
import com.global.web.BaseController;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2012-10-2 下午4:16:46
 * @version v1.0
 */
@Controller
@RequestMapping("/sys/cacheReloadController.do")
public class CacheReloadController extends BaseController {

	@RequestMapping(params = "method=toReload")
	public String toReload() {
		return "sys/cache/cacheReload";
	}

	@ResponseBody
	@RequestMapping(params = "method=reload")
	public void reload() throws BaseException {
		CacheService.getInstance().refresh();
	}
}
