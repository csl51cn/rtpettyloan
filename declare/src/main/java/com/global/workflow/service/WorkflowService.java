package com.global.workflow.service;

import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TransStateEnum;

/**
 * 
 * @author chen.feng
 * @date 2015-6-20
 * @version v1.0
 */
public interface WorkflowService {

	/**
	 * 暂存操作
	 * @param tradeNo
	 * @param user
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public TaskInfo doSave(String tradeNo, User user, String channelId) throws Exception;

	/**
	 * 经办操作
	 * @param txnSerialNo
	 * @param tradeNo
	 * @param refNo
	 * @param user
	 * @param channelId
	 * @param reqSeqNo
	 * @return
	 * @throws Exception
	 */
	public TaskInfo doHandle(String txnSerialNo, String tradeNo, String refNo, User user, String channelId, String reqSeqNo) throws Exception;

	/**
	 * 复核操作
	 * @param txnSerialNo
	 * @param tradeNo
	 * @param refNo
	 * @param operId
	 * @param isPass
	 * @param opinion
	 * @return
	 * @throws Exception
	 */
	public TaskInfo doCheck(String txnSerialNo, String tradeNo, String refNo, String operId,
                            String isPass, String opinion) throws Exception;

	/**
	 * 授权操作
	 * @param txnSerialNo
	 * @param tradeNo
	 * @param refNo
	 * @param operId
	 * @param isPass
	 * @param opinion
	 * @return
	 * @throws Exception
	 */
	public TaskInfo doAuth(String txnSerialNo, String tradeNo, String refNo, String operId,
                           String isPass, String opinion) throws Exception;

	/**
	 * 经办更正操作
	 * @param txnSerialNo
	 * @param tradeNo
	 * @param refNo
	 * @param operId
	 * @return
	 * @throws Exception
	 */
	public TaskInfo doAgain(String txnSerialNo, String tradeNo, String refNo, String operId)
			throws Exception;

	/**
	 * 取消操作
	 * 
	 * @param txnSerialNo
	 * @param operId
	 * @throws Exception
	 */
	public TaskInfo doCancel(String txnSerialNo, String operId) throws Exception;
	
	/**
	 * 直接完成流程
	 * @param txnSerialNo
	 * @throws Exception
	 */
	public void doFinish(String txnSerialNo, String refNo, TransStateEnum transState, User user) throws Exception;

}
