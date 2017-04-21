package com.global.fems.business.dao;

import java.util.List;
import java.util.Map;

import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.ZTreeNode;
import com.global.workflow.domain.TaskInfo;

public interface SpfeMdfDao {

	/**
	 * 插入
	 * @param mode
	 * @return
	 */
	public SpfeMdf insert(SpfeMdf mode) throws BaseException;
	
	/**
	 * 修改
	 * @param mode
	 * @throws BaseException
	 */
	public void update(SpfeMdf mode) throws BaseException;
	
	/**
	 * 删除
	 * @param mode
	 * @throws BaseException
	 */
	public void delete(SpfeMdf mode) throws BaseException;
	
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
	 * 列表
	 * @param prm
	 * @return
	 * @throws BaseException
	 */
	List<SpfeMdf> findList(Map<String, Object> prm) throws BaseException;
	/**
	 * 通过主键获取  没有被撤销的额度登记信息
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findNotCancelById(String id, String csrId) throws BaseException ;
	/**
	 * 获取所有 没有被撤销的 额度登记信息
	 * @return
	 * @throws BaseException
	 */
	public List<SpfeLmt> findNotCancelAll() throws BaseException ;
	/**
	 * 通过修改业务编号  获取 需要展示的业务信息  spfeMdf对象
	 * @param bizNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findSpfeMdfByMdfBizNo(String bizNo, String sfzx) throws BaseException;

	/**
	 * 通过修改业务编号  获取  spfeMdf对象
	 * @param bizNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findByBizNo(String bizNo) throws BaseException;
	/**
	 * 
	 * @param bizNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findSpfeMdfByLmtBizNo(String bizNo, String sfzx) throws BaseException;

	public SpfeMdf findSpfeMdfByMdfSeqNo(String seqNo, String refNo, String sfzx) throws BaseException;

	/**
	 * 
	 * @param seqNo
	 * @return
	 * @throws BaseException
	 */
	public SpfeMdf findSpfeMdfByLmtSeqNo(String seqNo) throws BaseException;
	/**
	 * 获取左树数据
	 * @return
	 * @throws BaseException
	 */
	public List<ZTreeNode> getSpfeListForZTreeNode(String orgs)throws BaseException ;
	
	/**
	 * 查询可以做修改或者撤销业务的历史业务数据
	 * @param task
	 * @param user
	 * @param page
	 * @param orgs
	 * @return
	 * @throws BaseException
	 */
	public PageBean queryFinishTaskListByUserID(TaskInfo task,
                                                PageBean page) throws BaseException ;
	/**
	 * 更新录入表更新状态
	 * @param primaryBizNo
	 * @throws BaseException
	 */
	public void updateLmtDataStatus(String primaryBizNo, String sfzx) throws BaseException;
	
	/**
	 * 更新修改表更新状态
	 * @param primaryBizNo
	 * @throws BaseException
	 */
	public void updateMdfDataStatus(String primaryBizNo, String sfzx) throws BaseException;
	/**
	 * 根据表面修改状态
	 * @param seqno
	 * @param tableName
	 * @throws BaseException
	 */
	public void updateDataStatus(String seqno, String tableName, String status) throws BaseException;
	
	/**
	 * 根据表面修改状态
	 * @param seqno
	 * @param tableName
	 * @throws BaseException
	 */
	public void updateDataStatusByBizNo(String bizNo, String tableName, String status) throws BaseException;
	/**
	 * 通过业务参号 获取 业务编号
	 * @param refNo
	 * @return
	 * @throws BaseException
	 */
	public String getBizNoByRefNo(String refNo)throws BaseException;
	/**
	 * 通过业务编号获取 没有撤销的登记信息
	 * @param bizno
	 * @param isDetailQuey 是否是 详细信息页面查询   如果是详细信息页面查询则不会限制 重复使用问题
	 * @return
	 * @throws BaseException
	 */
	public SpfeLmt findNotCancelByBizNo(String bizno, boolean isDetailQuey)   throws BaseException;

	public Map<String, Object> findRefNoBySeqNo(String seqno);

	public SpfeMdf findSpfeMdfByLmtSeqNo(String txnSerialNo, String refNo, String sfzx);

	public SpfeMdf findSpfeMkupBySeqNo(String txnSerialNo, String refNo,
                                       String string);

}
