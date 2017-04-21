package com.global.workflow.service;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradePrivilege;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
public interface TradePrivilegeService {

	List<TradePrivilege> queryTradePrivilege(String tradeNo) throws BaseException;
	
	public List<TradePrivilege> queryTradePrivilegeByMenuID(String menuid)
			throws BaseException ;
	
	void update(String privilege) throws BaseException;

}
