package com.global.fems.business.service;

import java.util.List;
import java.util.Map;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.SpfeMkUp;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;

public interface SpfeMkUpService {

	

	/**
	 * 经办
	 * @param mode
	 * @throws BaseException
	 */
	public SpfeMkUp doHandle(SpfeMkUp mode, User user, boolean isHandle, String reqSeqNo) throws Exception;
	/**
	 * 复核
	 * @param mode
	 * @throws BaseException
	 */
	public String doCheck(String checkMsg, User user)  throws DataCheckException,Exception;
	
	/**
	 * 授权
	 * @param txnSerialNo
	 * @param tradeNo
	 * @param user
	 * @param isPass
	 * @param opinion
	 * @throws Exception
	 */
	public String doAuth(String checkMsg, User user)  throws DataCheckException,Exception;
	
	/**
	 * 再次经办
	 * @param mode
	 * @throws BaseException
	 */
	public void doAgain(SpfeMkUp mode, User user) throws Exception;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	SpfeMkUp findById(String id)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm 查询的条件
	 * @return
	 * @throws BaseException
	 */
	List<SpfeMkUp> findList(Map<String, Object> prm) throws BaseException;
	
	/**
	 * 流程完成数据迁移
	 * @param txnSerialNo
	 * @param refNo
	 * @param transState
	 * @param recode
	 * @param remsg
	 * @param user
	 * @return
	 */
	public boolean doFinish(String txnSerialNo, String refNo,
                            String transState, String recode, String remsg, User user);
	
	public void doCancel(String txNo, String opeid) throws Exception ;
}
