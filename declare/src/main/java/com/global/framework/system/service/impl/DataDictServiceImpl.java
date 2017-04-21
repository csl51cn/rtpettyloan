package com.global.framework.system.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.DataDictDao;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;
import com.global.framework.system.domain.ZTreeNode;
import com.global.framework.system.service.DataDictService;
import com.global.framework.system.web.common.CacheService;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2012-8-28 下午5:08:19
 * @version v1.0
 */
@Service("dataDictService")
public class DataDictServiceImpl implements DataDictService {
	@Autowired
	private DataDictDao dataDictDao;

	
	public PageBean queryDictClassForPage(DataDictClass dictClass,
			PageBean pageBean) throws BaseException {
		return this.dataDictDao.queryDictClassForPage(dictClass, pageBean);
	}

	
	public DataDictClass saveOrUpdateDictClass(DataDictClass dictClass) throws BaseException {
		if (dictClass != null && StringUtils.isNotBlank(dictClass.getClassId())) {
			dictClass = this.dataDictDao.updateDictClass(dictClass);
		}
		dictClass = this.dataDictDao.insertDictClass(dictClass);
		CacheService.getInstance().loadDataDictAll();
		return dictClass;
	}

	
	public int deleteDictClass(DataDictClass dictClass) throws BaseException {
		int count = this.dataDictDao.deleteDictClass(dictClass);
		CacheService.getInstance().loadDataDictAll();
		return count;
	}

	
	public DataDictClass getDictClass(DataDictClass dictClass)
			throws BaseException {
		return this.dataDictDao.getDictClass(dictClass);
	}

	
	public void updateDictClass(DataDictClass dictClass) throws BaseException {
		this.dataDictDao.updateDictClass(dictClass);
		CacheService.getInstance().loadDataDictAll();
	}

	
	public List<DataDictClass> getDictClassList() throws BaseException {
		return this.dataDictDao.getDictClassList();
	}

	// ===============================================================数据字典代码
	
	public PageBean queryDataDictForPage(DataDict dataDict, PageBean pageBean)
			throws BaseException {
		return this.dataDictDao.queryDataDictForPage(dataDict, pageBean);
	}

	
	public DataDict saveOrUpdateDataDict(DataDict dataDict) throws BaseException {
		if (dataDict != null && StringUtils.isNotBlank(dataDict.getDictId())) {
			dataDict = this.dataDictDao.updateDataDict(dataDict);
		}
		dataDict = this.dataDictDao.insertDataDict(dataDict);
		CacheService.getInstance().loadDataDictAll();
		return dataDict;
	}

	
	public void deleteDataDict(DataDict dataDict) throws BaseException {
		this.dataDictDao.deleteDataDict(dataDict);
		CacheService.getInstance().loadDataDictAll();
	}

	
	public DataDict getDataDict(DataDict dataDict) throws BaseException {
		return this.dataDictDao.getDataDict(dataDict);
	}

	
	public void updateDataDict(DataDict dataDict) throws BaseException {
		this.dataDictDao.updateDataDict(dataDict);
		CacheService.getInstance().loadDataDictAll();
	}

	
	public List<DataDict> getDataDictList() throws BaseException {
		return this.dataDictDao.getDataDictList();
	}

	
	public List<DataDict> getDataDictList(String code) throws BaseException {
		return dataDictDao.getDataDictList(code);
	}
	
	
	public List<String> getDictCatelog() throws BaseException {
		List<ZTreeNode> list = this.dataDictDao.getDataDictListForZTreeNode();
		List<String> rtList = new LinkedList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("{").append("id:-1,")
		.append("pId:-99,")
		.append("name:\"数据字典\",")
		.append("open:true")
		.append(",checked:false");
		sb.append("}");
		rtList.add(sb.toString());
		for (ZTreeNode rt : list) {
			sb = new StringBuffer();
			sb.append("{").append("id:" + rt.getTreeId() + ",")
				.append("pId:" + rt.getPid() + ",")
				.append("name:\"" + rt.getTreeName() + "\",")
				.append("open:true")
				.append(",checked:false");
			sb.append("}");
			rtList.add(sb.toString());
		}
		return rtList;
	}
}
