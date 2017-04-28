package com.global.fems.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SpfeMkUpDao;
import com.global.fems.business.domain.SpfeMkUp;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;

@Repository("spfeMkUpDao")
public class SpfeMkUpDaoImpl extends BaseDaoSupport  implements SpfeMkUpDao{

	public SpfeMkUp insert(SpfeMkUp mode) throws BaseException {
		try {
			return super.insert(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void update(SpfeMkUp mode) throws BaseException {
		try {
			super.update(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void delete(SpfeMkUp mode) throws BaseException {
		super.delete(mode);
	}

	public SpfeMkUp findById(String id) throws BaseException {
		String sql ="select t.*,(select refno from dc_wfl_taskinfo_his h where h.txnserialno = t.seqno) refno   from dc_bu_spfe_mkup t where t.seqno=?";
		return super.findForObjectBySql(sql, new Object[]{id}, SpfeMkUp.class);
	}
	
	public List<SpfeMkUp> findList(Map<String, Object> prm) throws BaseException {
		if(prm==null) prm = new HashMap<String, Object>();
		return (List<SpfeMkUp>)super.findForList(SpfeMkUp.class, prm);
	}
}
