package com.global.fems.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SpfeSynOdrDao;
import com.global.fems.business.domain.SpfeSynOdr;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;

@Repository("spfeSynOdrDao")
public class SpfeSynOdrDaoImpl extends BaseDaoSupport implements SpfeSynOdrDao{

	public SpfeSynOdr insert(SpfeSynOdr mode) throws BaseException {
		return super.insert(mode);
	}

	public void update(SpfeSynOdr mode) throws BaseException {
		super.update(mode);
	}

	public void delete(SpfeSynOdr mode) throws BaseException {
		super.delete(mode);
	}

	public SpfeSynOdr findById(String id) throws BaseException {
		return super.findForObject(SpfeSynOdr.class, id);
	}
	
	public List<SpfeSynOdr> findList(Map<String, Object> prm) throws BaseException {
		if(prm==null) prm = new HashMap<String, Object>();
		return (List<SpfeSynOdr>)super.findForList(SpfeSynOdr.class, prm);
	}


}
