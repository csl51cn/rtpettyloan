package com.global.fems.business.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.AccountDao;
import com.global.fems.business.domain.Account;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;

@Repository("AccountDao")
public class AccountDaoImpl extends BaseDaoSupport implements AccountDao{

	public List<Account> findById(String idNo,String cur) throws BaseException {
		String sql ="select * from dc_PA_ACCOUNT where IDNO = ? and CUR=?";
		return (List<Account>)super.findForListBySql(sql, new Object[]{idNo,cur}, Account.class);
	}
	
}
