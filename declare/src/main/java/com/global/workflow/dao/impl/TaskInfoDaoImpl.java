package com.global.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.User;
import com.global.workflow.dao.TaskInfoDao;
import com.global.workflow.domain.ProcessHistory;
import com.global.workflow.domain.TaskInfo;
import com.global.workflow.domain.TaskInfoHis;

/**
 * 
 * @author chen.feng
 * @date 2013-6-17
 * @version v1.0
 */
@Repository("taskInfoDao")
public class TaskInfoDaoImpl extends BaseDaoSupport implements TaskInfoDao {

	
	public void saveTaskInfo(TaskInfo taskInfo) throws BaseException {
		taskInfo.setSfzx("Y");
		super.insert(taskInfo);
	}

	
	public TaskInfo getTaskInfo(String txnSerialNo) throws BaseException {
		return (TaskInfo) super.findForObject(TaskInfo.class, txnSerialNo);
	}
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo) throws BaseException {
		return (TaskInfoHis) super.findForObject(TaskInfoHis.class, txnSerialNo);
	}

	public TaskInfo getTaskInfoUnionHis(String refNo,String txnSerialNo) throws BaseException{
		
		String sql ="";
		Object param[] = null;
		if (StringUtils.isBlank(refNo) && StringUtils.isBlank(txnSerialNo)) {
			throw new BaseException("业务参号和业务流水号不能同时为空!");
		}
		if(StringUtils.isNotBlank(txnSerialNo) && StringUtils.isBlank(refNo)){
			sql = "select t.* from wfl_taskinfo t  where  txnserialno=? and sfzx='Y' union  select t_his.* from wfl_taskinfo_his t_his  where txnserialno=? and sfzx='Y'";
			param = new Object[]{txnSerialNo,txnSerialNo};
		}else if(StringUtils.isNotBlank(refNo) && StringUtils.isBlank(txnSerialNo)){
			sql = "select t.* from wfl_taskinfo t  where  refNo=?  and sfzx='Y' union  select t_his.* from wfl_taskinfo_his t_his  where refNo=?  and sfzx='Y'";
			param = new Object[]{refNo,refNo};
		}else{
			sql ="select t.* from wfl_taskinfo t  where refno=? and txnserialno=?  and sfzx='Y' union  select t_his.* from wfl_taskinfo_his t_his  where refno=? and txnserialno=?  and sfzx='Y'";
			param = new Object[]{refNo,txnSerialNo,refNo,txnSerialNo};
		}
		return super.findForObjectBySql(sql, param, TaskInfo.class);
	}
	
	public TaskInfo getTaskInfoForCancel(String txnSerialNo) throws BaseException{
		String sql ="select c.* from (select t.* from wfl_taskinfo t  where  txnserialno=? union  select t_his.* from wfl_taskinfo_his t_his  where txnserialno=?) c where  not exists(select 1 from BU_spfe_csr where SEQNO = c.txnserialno )";
		return super.findForObjectBySql(sql, new Object[]{txnSerialNo,txnSerialNo}, TaskInfo.class);
	}
	
	public void updateTaskInfo(TaskInfo taskInfo) throws BaseException {
		super.update(taskInfo);
	}

	
	public void deleteTaskInfo(String txnSerialNo) throws BaseException {
		super.delete(TaskInfo.class, txnSerialNo);
	}

	public void doDeleteTaskInfoHis(String txnSerialNo) throws BaseException {
		super.delete(TaskInfoHis.class, txnSerialNo);
	}
	
	public void saveTaskInfoHis(TaskInfoHis taskInfoHis) throws BaseException {
		taskInfoHis.setSfzx("Y");
		super.insert(taskInfoHis);
	}

	
	public PageBean queryWaitTaskListByUserId(TaskInfo task, User user,
			List<String> orgPrivList, PageBean page) throws BaseException {
		List<String> args = new ArrayList<String>();

		String sql = "select tt.txnSerialNo, " +
						"tt.bizNo, " +
						"tt.createDate, " +
						"tt.tradeNo, " +
						"tt.transstate,"+
						"(select username from sys_user where userid = tt.createUser) as createUser, " +
						"(select tradename from wfl_tradecode where tradeno = tt.tradeno) as tradename, " +
						"decode(tt.transstate,'0','新建','1','待复核','2','待授权','3','授权','5','经办更正') transstatename, " +
						"(select accessurl from sys_menu m where menuid=(select distinct menuid from wfl_tradeprivilege where tradeno = tt.tradeno)) url " +
					 "from (";
		// 查询当前用户保存或经办更正的任务列表
		sql += "select * from wfl_taskinfo t where t.createuser=? and t.transstate in (0,5) ";
		args.add(user.getUserId());

		// 查询当前用户有权限的复核任务
		sql += " UNION ";
		sql += "select * "
				+ "  from wfl_taskinfo t "
				+ " where t.createuser <> ? "
				+ "   and t.transstate = '1' "
				+ "   and exists  "
				+ " (select 1 "
				+ "          from sys_userrole u, sys_roleright r, wfl_tradeprivilege p "
				+ "         where u.roleid = r.roleid "
				+ "           and r.rightid = p.privid "
				+ "           and p.tradeno = t.tradeno "
				+ "           and u.userid = ?" +
				"             and p.opeid='2')";
		
		// 查询当前用户有权限的授权任务
		sql += " UNION ";
		sql += "select * "
				+ "  from wfl_taskinfo t "
				+ " where t.createuser <> ? "
				+ "   and t.transstate = '2' "
				+ "   and exists  "
				+ " (select 1 "
				+ "          from sys_userrole u, sys_roleright r, wfl_tradeprivilege p "
				+ "         where u.roleid = r.roleid "
				+ "           and r.rightid = p.privid "
				+ "           and p.tradeno = t.tradeno "
				+ "           and u.userid = ?" +
				"             and p.opeid='3')";
				
		sql += ") tt where 1=1 ";
		//机构数据权限
		if (orgPrivList != null && orgPrivList.size() > 0) {
			sql += "and tt.belongorgno in ("
					+ StringUtils.join(orgPrivList.toArray(), ",") + ")";
		}
		args.add(user.getUserId());
		args.add(user.getUserId());
		args.add(user.getUserId());
		args.add(user.getUserId());

		if (StringUtils.isNotBlank(task.getTxnSerialNo())) {
			sql += " and tt.txnserialno=? ";
			args.add(task.getTxnSerialNo());
		}
		if (StringUtils.isNotBlank(task.getBizNo())) {
			sql += " and tt.bizno=? ";
			args.add(task.getBizNo());
		}
		if (StringUtils.isNotBlank(task.getTradeNo())) {
			sql += " and tt.tradeno=? ";
			args.add(task.getTradeNo());
		}
		if (StringUtils.isNotBlank(task.getStartCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') >= ? ";
			args.add(task.getStartCreateTime());
		}
		if (StringUtils.isNotBlank(task.getEndCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') <= ? ";
			args.add(task.getEndCreateTime());
		}
		return super.findForPage(sql, args.toArray(), page, TaskInfo.class);
	}
	
	
	public PageBean queryFinishTaskListByUserID(TaskInfo task, User user,
			PageBean page) throws BaseException {
		List<String> args = new ArrayList<String>();
		String sql = "select tt.txnSerialNo, " +
					"tt.reqSeqNo, " +
					"tt.bizNo, " +
					"tt.createDate, " +
					"tt.tradeNo, " +
					"tt.transstate," +
					"tt.refNo,"+
					"(select username from sys_user where userid = tt.createUser) as createUser, " +
					"(select tradename from wfl_tradecode where tradeno = tt.tradeno) as tradename, " +
					"(select accessurl from sys_menu m where menuid=(select distinct menuid from wfl_tradeprivilege where tradeno = tt.tradeno)) url ," +
					"decode(tt.transstate,'4','成功','6','删除','7','失败') transstatename, " +
					"tt.finishDate " +
				"from wfl_taskinfo_his tt where (tt.actors like ? or tt.createuser is null)   ";
		args.add("%," + user.getUserId() + ",%");
		
		if (StringUtils.isNotBlank(task.getTxnSerialNo())) {
			sql += " and tt.txnserialno=? ";
			args.add(task.getTxnSerialNo());
		}
		if (StringUtils.isNotBlank(task.getBizNo())) {
			sql += " and tt.bizno=? ";
			args.add(task.getBizNo());
		}
		if (StringUtils.isNotBlank(task.getTradeNo())) {
			sql += " and tt.tradeno=? ";
			args.add(task.getTradeNo());
		}
		if (StringUtils.isNotBlank(task.getChannelId())) {
			sql += " and tt.channelid=? ";
			args.add(task.getChannelId());
		}
		if (StringUtils.isNotBlank(task.getReqSeqNo())) {
			sql += " and tt.reqseqno=? ";
			args.add(task.getReqSeqNo());
		}
		if (StringUtils.isNotBlank(task.getStartCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') >= ? ";
			args.add(task.getStartCreateTime());
		}
		if (StringUtils.isNotBlank(task.getEndCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') <= ? ";
			args.add(task.getEndCreateTime());
		}
		if (StringUtils.isNotBlank(task.getStartFinishTime())) {
			sql += " and to_char(tt.finishdate,'yyyy-MM-dd') >= ? ";
			args.add(task.getStartFinishTime());
		}
		if (StringUtils.isNotBlank(task.getEndFinishTime())) {
			sql += " and to_char(tt.finishdate,'yyyy-MM-dd') <= ? ";
			args.add(task.getEndFinishTime());
		}
		if (StringUtils.isNotBlank(task.getRefNo())) {
			sql += " and tt.refno=? ";
			args.add(task.getRefNo());
		}
		if (StringUtils.isNotBlank(task.getSfzx())) {
			sql += " and tt.sfzx=? ";
			args.add(task.getSfzx());
		}
		
		return super.findForPage(sql, args.toArray(), page, TaskInfo.class);
	}
	
	
	public PageBean queryPartTaskListByUserID(TaskInfo task, User user,
			PageBean page) throws BaseException {
		List<String> args = new ArrayList<String>();
		String sql = "select tt.txnSerialNo, " +
					"tt.bizNo, " +
					"tt.createDate, " +
					"tt.tradeNo, " +
					"tt.transstate,"+
					"(select username from sys_user where userid = tt.createUser) as createUser, " +
					"(select tradename from wfl_tradecode where tradeno = tt.tradeno) as tradename, " +
					"(select accessurl from sys_menu m where menuid = (select distinct menuid " +
					" from wfl_tradeprivilege where tradeno = tt.tradeno)) url,"+
					"decode(tt.transstate,'1','待复核','2','待授权','3','授权') transstatename " +
				"from wfl_taskinfo tt where tt.transstate in ('1','2','3') and tt.actors like ? ";
		args.add("%," + user.getUserId() + ",%");
		
		if (StringUtils.isNotBlank(task.getTxnSerialNo())) {
			sql += " and tt.txnserialno=? ";
			args.add(task.getTxnSerialNo());
		}
		if (StringUtils.isNotBlank(task.getBizNo())) {
			sql += " and tt.bizno=? ";
			args.add(task.getBizNo());
		}
		if (StringUtils.isNotBlank(task.getTradeNo())) {
			sql += " and tt.tradeno=? ";
			args.add(task.getTradeNo());
		}
		if (StringUtils.isNotBlank(task.getStartCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') >= ? ";
			args.add(task.getStartCreateTime());
		}
		if (StringUtils.isNotBlank(task.getEndCreateTime())) {
			sql += " and to_char(tt.createdate,'yyyy-MM-dd') <= ? ";
			args.add(task.getEndCreateTime());
		}
		return super.findForPage(sql, args.toArray(), page, TaskInfo.class);
	}
	
	
	public PageBean queryFlowHistoryList(String txnSerialNo, PageBean page)
			throws BaseException {
		String sql = "select p.*,u.username operName from wfl_processhistory p"
				+ "	join sys_user u on p.operid = u.userid"
				+ " where p.txnserialno=?";
		return (PageBean) super.findForPage(sql, new Object[]{txnSerialNo},page, ProcessHistory.class);
	}
	
	
	public String queryTradeUnionMenu(String tradeNo) throws BaseException {
		String sql = "select distinct m.accessurl from wfl_tradeprivilege wf inner join sys_menu m on wf.menuid = m.menuid and wf.tradeno=? ";
		List<Map<String, Object>> list = super.findForListMapBySql(sql, new Object[]{tradeNo});
		return (String) list.get(0).get("ACCESSURL");
	}

	
	public TaskInfoHis queryTaksInfoHis(String txnSerialNo) throws BaseException {
		String sql = "SELECT * FROM wfl_taskinfo_his t WHERE t.txnserialno = ? AND t.transState in ('4','7')";
		return super.findForObjectBySql(sql, new Object[] { txnSerialNo }, TaskInfoHis.class);
	}
	
	
	public int queryTaskCountByUserID(String userId, String flag, List<String> orgPrivList)
			throws BaseException {
		User user = new User();
		user.setUserId(userId);
		if ("1".equals(flag)) {
			PageBean pb = queryWaitTaskListByUserId(new TaskInfo(), user, orgPrivList, new PageBean());
			return Integer.parseInt(String.valueOf(pb.getTotalRows()));
		} else if ("2".equals(flag)) {
			PageBean pb = queryPartTaskListByUserID(new TaskInfo(), user, new PageBean());
			return Integer.parseInt(String.valueOf(pb.getTotalRows()));
		} else if ("3".equals(flag)){
			PageBean pb = queryFinishTaskListByUserID(new TaskInfo(), user, new PageBean());
			return Integer.parseInt(String.valueOf(pb.getTotalRows()));
		}
		return 0;
	}
	
	public int getTaskCountByReqSeqNo(String reqSeqNo) {
		try {
			String sql = "select sum(tt.n) from (select count(1) n from wfl_taskinfo t where t.reqseqno = ? union all select count(1) n from wfl_taskinfo_his t where t.reqseqno = ?) tt";
			return super.findForIntBySql(sql, new Object[]{reqSeqNo, reqSeqNo});
		} catch (Exception e) {
			return 0;
		}
	}
	
	public TaskInfoHis getTaskInfoHis(String txnSerialNo, String refNo) {
		String sql = "select * from wfl_taskinfo_his t where t.sfzx='Y' ";
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotBlank(txnSerialNo)) {
			sql += " and t.txnserialno=? ";
			list.add(txnSerialNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += " and t.refNo=? ";
			//sql += " and NOT EXISTS (SELECT 1 FROM wfl_taskinfo_his t1 WHERE t1.finishdate > t.finishdate) ";
			list.add(refNo);
		}
		return super.findForObjectBySql(sql, list.toArray(), TaskInfoHis.class);
	}
	
	public void updateTaskInfoHisStatus(String primaryBizNo, String sfzx) {
		String sql = "update wfl_taskinfo_his set sfzx=? where bizno=? ";
		super.updateBySql(sql, new Object[]{sfzx, primaryBizNo});
	}
	
	public TaskInfo getTaskInfobyReqSeqNo(String reqSeqNo) throws BaseException{
		
		String sql ="";
		Object param[] = null;
		sql ="select t.* from wfl_taskinfo t  where reqSeqNo=? ";
		param = new Object[]{reqSeqNo};
		return super.findForObjectBySql(sql, param, TaskInfo.class);
	}
	
	public TaskInfoHis getTaskInfoHisByReqSeqNo(String reqSeqNo)
			throws BaseException {
		String sql ="";
		Object param[] = null;
		sql ="select t.* from wfl_taskinfo_his t  where reqSeqNo=? and sfzx='Y'";
		param = new Object[]{reqSeqNo};
		return super.findForObjectBySql(sql, param, TaskInfoHis.class);
	}
}
