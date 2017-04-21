package com.global.fems.business.service;

import java.util.List;
import java.util.Map;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.message.domain.business.response.RspIndividualLCYRegister;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;

/**
 * 个人结售汇额度登记服务接口
 * @author Administrator
 *
 */
public interface SpfeLmtService {

	
	/**
	 * 经办
	 * @param mode
	 * @param user
	 * @param isHandle 是否经办提交
	 * @param reqSeqNo 请求方业务流水号
	 * @return
	 * @throws Exception
	 */
	public SpfeLmt doHandle(SpfeLmt mode, User user, boolean isHandle, String reqSeqNo) throws Exception;
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
	 * 经办更正
	 * @param mode
	 * @throws BaseException
	 */
	public void doAgain(SpfeLmt mode, User user) throws Exception;
	
	/**
	 * 取消任务执行方法
	 * @param txNo
	 * @param opeid
	 * @throws Exception
	 */
	public void doCancel(String txNo, String opeid) throws Exception;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	SpfeLmt findById(String id)  throws BaseException;
	

	/**
	 * 通过业务编号获取对象
	 * @param bizNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findByBizNo(String bizNo)  throws BaseException;
	
	/**
	 * 列表
	 * @param prm 查询的条件
	 * @return
	 * @throws BaseException
	 */
	List<SpfeLmt> findList(Map<String, Object> prm) throws BaseException;
	
	/**
	 * 流程完成数据迁移
	 * @param seqno
	 * @param rsp
	 * @param transState
	 * @param recode
	 * @param remsg
	 * @return
	 */
	public boolean doFinish(String seqno, RspIndividualLCYRegister rsp, String transState, String recode, String remsg, User user);

	/**
	 * 删除
	 * @param spfeLmt
	 * @return
	 * @throws BaseException
	 */
	public void dodelete(SpfeLmt spfeLmt)  throws Exception;
}
