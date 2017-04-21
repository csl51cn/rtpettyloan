package com.global.fems.business.service;

import java.util.List;
import java.util.Map;

import org.global.framework.xmlbeans.bean.DataCheckException;

import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.message.domain.business.response.RspIndividualLCYModify;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.domain.TaskInfo;

public interface SpfeMdfService {

	/**
	 * 经办
	 * @param mode
	 * @param user
	 * @param isHandle 是否经办提交
	 * @param reqSeqNo 请求方业务流水号
	 * @return
	 * @throws Exception
	 */
	public SpfeMdf doHandle(SpfeMdf mode, User user, boolean isHandle, String reqSeqNo) throws Exception;
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
	public void doAgain(SpfeMdf mode, User user) throws Exception;
	
	/**
	 * 通过主键获取对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findById(String id)  throws BaseException;
	/**
	 * 
	 * 通过主键获取对象
	 * @param key
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findByPrimaryKey(String key) throws BaseException ;
	/**
	 * 通过主键获取  没有被撤销的对象
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findNotCancelById(String id, String csrId)  throws BaseException;
	/**
	 * 通过业务编号获取 没有撤销的登记信息
	 * @param bizno
	 * @param isDetailQuey 是否是 详细信息页面查询   如果是详细信息页面查询则不会限制 重复使用问题
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findNotCancelByBizNo(String bizno, boolean isDetailQuey)   throws BaseException;
	
	/**
	 * 获取所有 没有被撤销的 额度登记信息
	 * @return
	 * @throws BaseException
	 */
	public List<SpfeLmt> findNotCancelAll() throws BaseException ;
	
	/**
	 * 列表
	 * @param prm 查询的条件
	 * @return
	 * @throws BaseException
	 */
	public List<SpfeMdf> findList(Map<String, Object> prm) throws BaseException;
	
	/**
	 * 更新业务数据状态
	 * @param tradeNo
	 * @param primaryBizNo
	 * @param sfzx
	 * @throws Exception
	 */
	public void doUpdateDataStatus(String tradeNo, String primaryBizNo, String sfzx) throws Exception;
	
	public SpfeMdf findSpfeMdfByMdfSeqNo(String seqNo, String refNo, String sfzx) throws BaseException;

	/**
	 * 
	 * @param bizNo 主业务 业务编号
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findSpfeMdfByLmtBizNo(String bizNo, String sfzx) throws BaseException;
	/**
	 * 
	 * @param seqNo 主业务 业务流水号
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findSpfeMdfByLmtSeqNo(String seqNo) throws BaseException;
	
	/**
	 * 根据表面修改状态
	 * @param seqno
	 * @param tableName
	 * @throws BaseException
	 */
	public void updateDataStatus(String seqno, String tableName, String status) throws BaseException;
	
	
	/**
	 * 取消代办任务执行方法
	 * @param txNo
	 * @param opeid
	 * @throws Exception
	 */
	public void doCancel(String txNo, String opeid) throws Exception;
	/**
	 * 通过业务参号 获取 业务编号
	 * @param refNo
	 * @return
	 * @throws BaseException
	 */
	public String getBizNoByRefNo(String refNo)throws BaseException;
	/**
	 * 查询历史业务数据  可以做修改或者撤销业务的
	 * @param task
	 * @param user
	 * @param page
	 * @return
	 */
	public PageBean queryFinishTaskListByUserID(TaskInfo task,
                                                PageBean page) throws BaseException ;
	
	public Map<String, Object> findRefNoBySeqNo(String seqno);
	
	/**
	 * 流程完成数据迁移
	 * 
	 * @param mode
	 * @param rsp
	 * @param transState
	 * @param recode
	 * @param remsg
	 * @param user
	 * @return
	 */
	public void doFinish(SpfeMdf mode, RspIndividualLCYModify rsp,
                         String transState, String recode, String remsg, User user) throws DataCheckException ;
	public SpfeMdf findSpfeMdfByLmtSeqNo(String txnSerialNo, String refNo, String sfzx);
	public SpfeMdf findSpfeMkupBySeqNo(String txnSerialNo, String refNo,
                                       String string);
}
