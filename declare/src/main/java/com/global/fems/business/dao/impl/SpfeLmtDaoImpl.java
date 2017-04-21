package com.global.fems.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SpfeLmtDao;
import com.global.fems.business.domain.SpfeLmt;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;
import com.global.framework.util.SysUtils;

@Repository("spfeLmtDao")
public class SpfeLmtDaoImpl extends BaseDaoSupport implements SpfeLmtDao{

	public SpfeLmt insert(SpfeLmt mode) throws BaseException {
		try {
			mode.setSfzx("Y");
			return super.insert(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void update(SpfeLmt mode) throws BaseException {
		try {
			super.update(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void delete(SpfeLmt mode) throws BaseException {
		super.delete(mode);
	}

	public SpfeLmt findById(String id) throws BaseException {
		String sql ="select lmt.*,(select refno from wfl_taskinfo_his h where h.bizno = lmt.bizno) refno   from bu_spfe_lmt lmt where seqno=?";
		return super.findForObjectBySql(sql, new Object[]{id},SpfeLmt.class);
	}
	
	public List<SpfeLmt> findList(Map<String, Object> prm) throws BaseException {
		if(prm==null) prm = new HashMap<String, Object>();
		return (List<SpfeLmt>)super.findForList(SpfeLmt.class, prm);
	}

	public SpfeLmt findByBizNo(String bizNo) throws BaseException {
		return super.findForObjectBySql("select * from bu_spfe_lmt where bizno=?", new Object[]{bizNo}, SpfeLmt.class);
	}

	public void updateBySql(SpfeLmt mode) throws BaseException {
		String sql = "update BU_SPFE_LMT set amt_usd=amt_usd + ?,AMT_BALANCE_USD=?,TYPE_STATUS=?,RECODE=?,REMSG=? where seqno=? ";
		super.updateBySql(sql, new Object[]{SysUtils.replaceAll(mode.getAMT_USD(),",",""), SysUtils.replaceAll(mode.getAMT_BALANCE_USD(),",",""), mode.getTYPE_STATUS(), mode.getRECODE(), mode.getREMSG(), mode.getSEQNO()});
	}

}
