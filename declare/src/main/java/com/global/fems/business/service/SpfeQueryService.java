package com.global.fems.business.service;

import java.util.List;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.JSHMsg;
import com.global.fems.business.domain.SpfeAmtQuery;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;


/**
 * 个人结售汇查询
 * 
 * @author Sili Jiang
 * @version 2015-07-06
 *
 */
public interface SpfeQueryService {

	/**
	 * 个人结售汇额度查询
	 * 
	 * @param spfeAmtQuery SpfeAmtQuery
	 * @return
	 * @throws Exception
	 */
	public SpfeAmtQuery spfeAmtQuery(SpfeAmtQuery spfeAmtQuery, User user) throws BaseException, DataCheckException;

	/**
	 * 个人结售汇额度登记指令查询
	 * 
	 * @param refNo 
	 * @param seqNo
	 * @return
	 * @throws Exception
	 */
	public List<TaskInfo> spfeRegisterQuery(String refNo, String seqNo) throws BaseException;

	/**
	 * 美元折算率查询
	 * 
	 * @param currencyCode 币种代码
	 * @param yearMonth 年月
	 * @return
	 * @throws DataCheckException 
	 * @throws Exception
	 */
	public PageBean usdCvsRateQuery(String currencyCode, String yearMonth, PageBean page, User user) throws BaseException,
			DataCheckException;
	
	
	
	/**
	 * 个人结售汇信息查询
	 * 
	 * @param jshMag JSHMsg
	 * @return
	 * @throws DataCheckException 
	 * @throws Exception
	 */
	public PageBean jshMsgQuery(JSHMsg jshMsg, PageBean page, User user) throws BaseException, DataCheckException;

	
	/**
	 * 个人结售汇明细信息查询
	 * 
	 * @param refNo 业务参号
	 * @param tardeType 交易类型
	 * @return
	 * @throws DataCheckException 
	 * @throws Exception
	 */
	public JSHMsg jshMsgDetailQuery(String refNo, String tardeType, User user) throws BaseException, DataCheckException;

	
}
