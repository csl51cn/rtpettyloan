package com.global.fems.business.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.domain.SpfeMkUp;
import com.global.fems.business.support.ValidateHandler;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.session.SessionManager;

/**
 * 页面调用接口验证
 * @author longjun
 *
 */
@RequestMapping("/validate.do")
@Controller
public class ValidateController {

	/**
	 * 验证登记
	 * @param mode
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeLmt",produces="text/plain;charset=UTF-8")
	public String validateSpfeLmt(SpfeLmt mode, HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mode.setBIZ_TX_TIME(sdf.format(new Date()));
		User user = SessionManager.getSession(request);
		return ValidateHandler.validateSpfeLmt(mode, user);
	}
	
	/**
	 * 验证撤销
	 * @param mode
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeCsr",produces="text/plain;charset=UTF-8")
	public String validateSpfeCsr(SpfeCsr mode) throws BaseException{
		return ValidateHandler.validateSpfeCsr(mode);
	}
	/**
	 * 验证修改
	 * @param mode
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeMdf",produces="text/plain;charset=UTF-8")
	public String validateSpfeMdf(SpfeMdf mode) throws BaseException{
		return ValidateHandler.validateSpfeMdf(mode);
	}
	/**
	 * 验证补录
	 * @param mode
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=spfeMkUp",produces="text/plain;charset=UTF-8")
	public String validateSpfeMkUp(SpfeMkUp mode, HttpServletRequest request) throws BaseException{
		User user = SessionManager.getSession(request);
		return ValidateHandler.validateSpfeMkUp(mode, user);
	}
}
