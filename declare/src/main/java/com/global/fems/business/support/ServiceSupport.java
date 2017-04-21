package com.global.fems.business.support;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.global.framework.exception.BaseException;

public class ServiceSupport {

	/**
	 * 解析前台审批信息
	 * @param checkMsg
	 * @return
	 * @throws BaseException
	 * @throws Exception
	 */
	public static String[] analyticalMsg(String checkMsg) throws BaseException, Exception {
		JSONObject json = JSON.parseObject(checkMsg);
		String tradeNo = json.getString("tradeNo");
		String txnSerialNo = json.getString("txnSerialNo");
		String isAgree = json.getString("isAgree");
		String opinion = json.getString("opinion");
		String bizChnlCode =  json.getString("bizChnlCode");
		String channelId =  json.getString("channelId");
		if (StringUtils.isBlank(txnSerialNo)) {
			throw new BaseException("复核时业务流水号不能为空");
		}
		if (StringUtils.isBlank(isAgree)) {
			throw new BaseException("请选择是否通过复核");
		}
		if ("N".equals(isAgree) && StringUtils.isBlank(opinion)) {
			throw new BaseException("复核不通过时复核意见不能为空");
		}
		return new String[] { txnSerialNo, isAgree, tradeNo, opinion,bizChnlCode,channelId};
	}

}
