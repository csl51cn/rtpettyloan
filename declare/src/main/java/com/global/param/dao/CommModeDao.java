package com.global.param.dao;

import java.util.List;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.param.domain.CommMode;

/**
 * 类描述： 
 *
 * @author chen.feng
 * @date 2015-7-10
 * @version v1.0
 */
public interface CommModeDao {

	PageBean queryForPage(CommMode info, PageBean page) throws BaseException;

	CommMode saveOrUpdate(CommMode info) throws BaseException;

	void delete(CommMode info) throws BaseException;

	CommMode getCommMode(String channelId)throws BaseException;

	List<CommMode> getCommModeList()throws BaseException;

}
