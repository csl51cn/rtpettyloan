package com.global.fems.business.dao;

import java.util.List;

import com.global.fems.business.domain.Account;
import com.global.framework.exception.BaseException;

public interface AccountDao {

	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public List<Account> findById(String idNo, String cur)  throws BaseException;
	
	
}
