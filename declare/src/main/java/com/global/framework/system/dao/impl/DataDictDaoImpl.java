package com.global.framework.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.dao.DataDictDao;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;
import com.global.framework.system.domain.ZTreeNode;

/**
 * 数据字典DAO
 * @author cqchenf@qq.com
 * @date 2013-2-28 下午11:03:59
 * @version v1.0
 */
@Repository("dataDictDao")
public class DataDictDaoImpl extends BaseDaoSupport implements DataDictDao {

	public PageBean queryDictClassForPage(DataDictClass dictClass,
			PageBean pageBean) throws BaseException {
		StringBuffer sql = new StringBuffer("select * from sys_datadictclass where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(dictClass.getClassCode())){
			sql.append(" and classCode = ? ");
			args.add(dictClass.getClassCode());
		}
		if(StringUtils.isNotBlank(dictClass.getClassName())){
			sql.append(" and className like ? ");
			args.add("%"+dictClass.getClassName()+"%");
		}
		return super.findForPage(sql.toString(), args.toArray(), pageBean, DataDictClass.class);
	}

	public DataDictClass insertDictClass(DataDictClass dictClass) throws BaseException {
		return super.insert(dictClass);
	}

	public int deleteDictClass(DataDictClass dictClass) throws BaseException {
		//查询该类别下是否有字典代码数据
		String sql = "select count(1) from sys_datadict b where b.classid=?";
		int count = super.findForIntBySql(sql, new Object[]{dictClass.getClassId()});
		if (count > 0) {
			throw new BaseException("该类别下存在字典代码，不允许删除！");
		}
		return super.delete(dictClass);
	}

	public DataDictClass getDictClass(DataDictClass dictClass) throws BaseException {
		return (DataDictClass) super.findForObject(dictClass);
	}

	public DataDictClass updateDictClass(DataDictClass dictClass) throws BaseException {
		super.update(dictClass);
		return dictClass;
	}

	@SuppressWarnings("unchecked")
	public List<DataDictClass> getDictClassList() throws BaseException {
		return (List<DataDictClass>) super.findForList(DataDictClass.class);
	}

	//================================================================数据字典代码
	public PageBean queryDataDictForPage(DataDict dataDict,
			PageBean pageBean) throws BaseException {
		StringBuffer sql = new StringBuffer("select t.*,(select className from sys_datadictclass where classid=t.classid) className from sys_datadict t where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(dataDict.getDictCode())){
			sql.append(" and t.dictCode = ? ");
			args.add(dataDict.getDictCode());
		}
		if(StringUtils.isNotBlank(dataDict.getDictName())){
			sql.append(" and t.dictName like ? ");
			args.add("%"+dataDict.getDictName()+"%");
		}
		if(StringUtils.isNotBlank(dataDict.getClassId())){
			sql.append(" and t.classid = ? ");
			args.add(dataDict.getClassId());
		}
		return super.findForPage(sql.toString(), args.toArray(), pageBean, DataDict.class);
	}

	public DataDict insertDataDict(DataDict dataDict) throws BaseException {
		return super.insert(dataDict);
	}

	public void deleteDataDict(DataDict dataDict) throws BaseException {
		super.delete(dataDict);
	}

	public DataDict getDataDict(DataDict dataDict) throws BaseException {
		return (DataDict) super.findForObject(dataDict);
	}

	public DataDict updateDataDict(DataDict dataDict) throws BaseException {
		super.update(dataDict);
		return dataDict;
	}

	@SuppressWarnings("unchecked")
	public List<DataDict> getDataDictList() throws BaseException {
		return (List<DataDict>) super.findForList(DataDict.class);
	}

	@SuppressWarnings("unchecked")
	public List<DataDict> getDataDictList(String code) throws BaseException {
		String sql = "select t.dictid,t.dictcode,t.dictname,t.classid,t.sortno,d.classname from SYS_DATADICT t"
				+ " JOIN Sys_Datadictclass d on t.classid = d.classid"
				+ " WHERE d.classcode = ? order by sortno asc ";
		return (List<DataDict>) super.findForListBySql(sql, new Object[]{code}, DataDict.class);
	}
	
	public List<ZTreeNode> getDataDictListForZTreeNode() throws BaseException {
		String sql = "select a.classid treeId,'-1' pid, a.classname treeName from sys_datadictclass a order by sortno asc";
		return (List<ZTreeNode>) super.findForListBySql(sql, null,
				ZTreeNode.class);
	}
}
