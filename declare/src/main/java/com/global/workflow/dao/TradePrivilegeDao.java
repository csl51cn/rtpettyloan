package com.global.workflow.dao;

import java.util.List;

import com.global.framework.exception.BaseException;
import com.global.workflow.domain.TradePrivilege;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
public interface TradePrivilegeDao {

	List<TradePrivilege> queryTradePrivilege(String tradeNo) throws BaseException;
	
	public List<TradePrivilege> queryTradePrivilegeByMenuID(String menuid)
			throws BaseException ;
		
	/**
	 * 更新
	 * 
	 * @param privilege TradePrivilege
	 * @return
	 * @throws BaseException
	 */
	int update(TradePrivilege privilege) throws BaseException;

}
