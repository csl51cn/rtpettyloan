package com.global.fems.business.service;

import java.util.List;
import java.util.Map;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.message.domain.business.response.RspIndividualLCYCancel;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;

public interface SpfeCsrService {

	
	/**
	 * 经办
	 * 
	 * @param mode
	 * @param user
	 * @param isHandle 是否经办提交
	 * @param reqSeqNo 请求方业务流水号
	 * @return
	 * @throws Exception
	 */
	public SpfeCsr doHandle(SpfeCsr mode, User user, boolean isHandle, String reqSeqNo) throws Exception;
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
	public void doAgain(SpfeCsr mode, User user) throws Exception;
	
	/**
	 * 取消代办任务执行方法
	 * @param txNo
	 * @param opeid
	 * @throws Exception
	 */
	public void doCancel(String txNo, String opeid) throws Exception;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void doDelte(SpfeCsr mode) throws BaseException;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	SpfeCsr findById(String id)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm 查询的条件
	 * @return
	 * @throws BaseException
	 */
	List<SpfeCsr> findList(Map<String, Object> prm) throws BaseException;
	
	/**
	 * 流程完成数据迁移
	 * @param spfeCsr
	 * @param rsp
	 * @param transState
	 * @param recode
	 * @param remsg
	 * @param user
	 * @return
	 */
	public boolean doFinish(SpfeCsr spfeCsr, RspIndividualLCYCancel rsp,
                            String transState, String recode, String remsg, User user);
	public SpfeMdf findSpfeInfoByCsrId(String txnSerialNo);
	
}
