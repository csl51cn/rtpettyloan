package com.global.fems.business.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SpfeMdfDao;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.ZTreeNode;
import com.global.workflow.domain.TaskInfo;

@Repository("spfeMdfDao")
public class SpfeMdfDaoImpl extends BaseDaoSupport implements SpfeMdfDao{

	private static final Logger log = Logger.getLogger(SpfeMdfDaoImpl.class);
	
	public SpfeMdf insert(SpfeMdf mode) throws BaseException {
		try {
			mode.setSfzx("Y");
			return super.insert(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void update(SpfeMdf mode) throws BaseException {
		try {
			super.update(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}
	
	public void delete(SpfeMdf mode) throws BaseException {
		super.delete(mode);
	}

	public SpfeMdf findById(String id) throws BaseException {
		String sql = "select a.*,b.refNo,b.tradeNo from bu_spfe_mdf a, wfl_taskinfo b where a.seqno=b.txnserialno and a.seqno=? ";
		sql += " union ";
		sql += "select a.*,b.refNo,b.tradeNo from bu_spfe_mdf a, wfl_taskinfo_his b where a.seqno=b.txnserialno and a.seqno=? ";
		return super.findForObjectBySql(sql, new Object[]{id, id}, SpfeMdf.class);
	}
	
	public SpfeMdf findByPrimaryKey(String key) throws BaseException {
		String sql ="select a.*,(select t.txnserialno from wfl_taskinfo_his t where t.BIZNO=a.PRIMARYBIZNO) primarySeqNo from bu_spfe_mdf a where a.SEQNO=?";
		return super.findForObjectBySql(sql, new Object[]{key}, SpfeMdf.class);
	}
	
	public List<SpfeMdf> findList(Map<String, Object> prm) throws BaseException {
		if(prm==null) prm = new HashMap<String, Object>();
		return (List<SpfeMdf>)super.findForList(SpfeMdf.class, prm);
	}

	public SpfeLmt findNotCancelById(String id,String csrId) throws BaseException {
		String sql = "select a.*,b.refno from BU_spfe_lmt a, wfl_taskinfo_his b where b.txnserialno=a.seqno and b.transstate='4' and SEQNO=? and not exists(select 1 from BU_spfe_csr where seqno !=? and primarybizno=a.bizno)";
		return super.findForObjectBySql(sql.toString(), new Object[]{id,csrId}, SpfeLmt.class);
	}

	public List<SpfeLmt> findNotCancelAll() throws BaseException {
		String sql = "select a.*,b.refno from BU_spfe_lmt a, wfl_taskinfo_his b where b.txnserialno=a.seqno and b.transstate='4' and not exists(select 1 from BU_spfe_csr where primarybizno=a.bizno)";
		return (List<SpfeLmt>) super.findForListBySql(sql, new Object[]{}, SpfeLmt.class);
	}

	public List<ZTreeNode> getSpfeListForZTreeNode(String orgs) throws BaseException {//decode(a.trade_type,'JH','-1','GH','-2')
		String sql = " select b.bizno treeId, case when  a.trade_type= 'JH' then case when a.OCCUPY_LMT_STATUS='Y' then '-11' else '-12' end else case when a.OCCUPY_LMT_STATUS='Y' then '-21' else '-22' end end  pid,b.refno treeName" +
				"  from (select LMT.SEQNO,LMT.BIZNO,LMT.TRADE_TYPE,LMT.BIZNO,LMT.OCCUPY_LMT_STATUS from BU_SPFE_LMT lmt where " +
				//"  not EXISTS(select 1 from BU_SPFE_CSR csr where CSR.PRIMARYBIZNO=LMT.BIZNO  UNION all select 1 from BU_SPFE_MDF mdf where mdf.PRIMARYBIZNO=LMT.BIZNO )" +
				" lmt.sfzx='Y'"+
				"  union ALL" +
				"  select MDF.SEQNO,MDF.BIZNO,MDF.TRADE_TYPE,MDF.BIZNO,'Y' from BU_SPFE_MDF mdf where " +
				//"  not EXISTS(SELECT 1 from BU_SPFE_CSR csr where csr.PRIMARYBIZNO = MDF.BIZNO  UNION all select 1 from BU_SPFE_MDF mdf where mdf.PRIMARYBIZNO= MDF.BIZNO)" +
				" mdf.sfzx='Y'"+
				"  and EXISTS(select 1 from BU_SPFE_LMT lmt1 where LMT1.bizno = MDF.PRIMARYBIZNO UNION all select 1 from BU_SPFE_MDF mdf1 where MDF1.bizno = MDF.PRIMARYBIZNO) " +
				"  ) a ,wfl_taskinfo_his b where b.txnserialno=a.seqno and b.transstate='4' and b.TRANSORGNO in ('"+orgs+"') order by b.createdate desc";
		return (List<ZTreeNode>) super.findForListBySql(sql, new Object[]{},
			ZTreeNode.class);
	}

	public PageBean queryFinishTaskListByUserID(TaskInfo task,PageBean page) throws BaseException {
		List<String> args = new ArrayList<String>();
		String sql = "select tt.txnserialno,tt.bizno,tt.refno,tt.tradeNo, (select tradename from wfl_tradecode c where c.tradeno=tt.tradeno) tradeName from wfl_taskinfo_his tt where tt.txnserialno in (select l.seqno from bu_spfe_lmt l where l.sfzx='Y' union all select m.seqno from bu_spfe_mdf m where m.sfzx='Y' union all select m.seqno from bu_spfe_mkup m where m.sfzx = 'Y') and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=tt.bizno) and tt.transstate='4' ";
				
		sql += " and to_char(tt.finishdate,'yyyy-MM-dd') >= ? ";
		args.add(task.getStartFinishTime());
		sql += " and to_char(tt.finishdate,'yyyy-MM-dd') <= ? ";
		args.add(task.getEndFinishTime());
		sql += " and tt.belongorgno = ? ";
		args.add(task.getBelongOrgNo());
		
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
		return super.findForPage(sql, args.toArray(), page, TaskInfo.class);
	}
	
	public SpfeMdf findSpfeMdfByMdfBizNo(String bizNo,String sfzx) throws BaseException {
		String sql ="select a.* , '' SEQNO,'' BIZNO, a.BIZNO primaryBizNo from BU_SPFE_MDF a where  a.bizno=? and a.sfzx=?";
		return super.findForObjectBySql(sql, new Object[]{bizNo,sfzx}, SpfeMdf.class);
	}

	public SpfeMdf findSpfeMdfByLmtBizNo(String bizNo,String sfzx) throws BaseException {
		String sql ="select t.*,'' SEQNO,'' BIZNO,t.seqno primarySeqNo,t.bizno primaryBizNo from  BU_SPFE_LMT t where t.bizno =?  and t.sfzx=?";
		SpfeMdf mode = super.findForObjectBySql(sql, new Object[]{bizNo,sfzx}, SpfeMdf.class);
		return mode;
	}
	public SpfeMdf findSpfeMdfByMdfSeqNo(String seqNo,String refNo,String sfzx) throws BaseException {
		List<String> list = new ArrayList<String>();
		String sql="select a.*,"
				+"a.seqno primarySeqNo,"
				+"a.bizno primaryBizNo,"
				+"b.OCCUPY_LMT_STATUS,"
				+"b.BIZ_TYPE_CODE,"
				+"b.IDTYPE_CODE,"
				+"b.IDCODE,"
				+"b.CTYCODE,"
				+"b.ADD_IDCODE,"
				+"b.SIGNSTATUS,"
				+"b.BIZ_TX_TIME,"
				+"b.AMT_USD,"
				+"b.AMT_BALANCE_USD,"
				+"b.TYPE_STATUS,"
				+"C.REFNO "
				+"from BU_SPFE_MDF a,BU_SPFE_LMT b,wfl_taskinfo_his c "
				+"where a.PRIMARYBIZNO=b.BIZNO "
				+"and c.transState = '4' "
				+"and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=c.bizno) "
				+"and a.seqno=c.txnserialNo "
				+"and a.sfzx=? ";  
		
		list.add(sfzx);
		if (StringUtils.isNotBlank(seqNo)) {
			sql += "and a.seqno=? ";
			list.add(seqNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += "and c.refNo=? ";
			list.add(refNo);
		}
		
		sql += " union ";
		sql +="select a.*,"
				+"a.seqno primarySeqNo,"
				+"a.bizno primaryBizNo,"
				+"b.OCCUPY_LMT_STATUS,"
				+"b.BIZ_TYPE_CODE,"
				+"b.IDTYPE_CODE,"
				+"b.IDCODE,"
				+"b.CTYCODE,"
				+"b.ADD_IDCODE,"
				+"b.SIGNSTATUS,"
				+"b.BIZ_TX_TIME,"
				+"b.AMT_USD,"
				+"b.AMT_BALANCE_USD,"
				+"b.TYPE_STATUS,"
				+"C.REFNO "
				+"from BU_SPFE_MDF a,BU_SPFE_mdf b,wfl_taskinfo_his c "
				+"where a.PRIMARYBIZNO=b.BIZNO "
				+"and c.transState = '4' "
				+"and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=c.bizno) "
				+"and a.seqno=c.txnserialNo "
				+"and a.sfzx=? "; 
		
		list.add(sfzx);
		if (StringUtils.isNotBlank(seqNo)) {
			sql += "and a.seqno=? ";
			list.add(seqNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += "and c.refNo=? ";
			list.add(refNo);
		}
		
		sql += " union ";
		sql += "select a.*,"
				+"a.seqno primarySeqNo,"
				+"a.bizno primaryBizNo,"
				+"b.OCCUPY_LMT_STATUS,"
				+"b.BIZ_TYPE_CODE,"
				+"b.IDTYPE_CODE,"
				+"b.IDCODE,"
				+"b.CTYCODE,"
				+"b.ADD_IDCODE,"
				+"b.SIGNSTATUS,"
				+"b.BIZ_TX_TIME,"
				+"b.AMT_USD,"
				+"b.AMT_BALANCE_USD,"
				+"b.TYPE_STATUS,"
				+"C.REFNO "
				+"from BU_SPFE_MDF a,BU_SPFE_MKUP b,wfl_taskinfo_his c "
				+"where a.PRIMARYBIZNO=b.BIZNO "
				+"and c.transState = '4' "
				+"and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=c.bizno) "
				+"and a.seqno=c.txnserialNo "
				+"and a.sfzx=? ";  
		
		list.add(sfzx);
		if (StringUtils.isNotBlank(seqNo)) {
			sql += "and a.seqno=? ";
			list.add(seqNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += "and c.refNo=? ";
			list.add(refNo);
		}
		
		return super.findForObjectBySql(sql, list.toArray(), SpfeMdf.class);
	}

	public SpfeMdf findSpfeMdfByLmtSeqNo(String seqNo) throws BaseException {
		String sql ="select t.*,'' SEQNO,'' BIZNO,t.seqno primarySeqNo,t.bizno primaryBizNo from  BU_SPFE_LMT t where t.seqNo =?";
		SpfeMdf mode = super.findForObjectBySql(sql, new Object[]{seqNo}, SpfeMdf.class);
		return mode;
	}

	public void updateLmtDataStatus(String primaryBizNo, String sfzx) throws BaseException {
		String sql ="update bu_spfe_lmt set sfzx= ?  where bizNo=?";
		Object params[] =  new Object[]{sfzx, primaryBizNo};
		super.updateBySql(sql,params);
	}

	public void updateMdfDataStatus(String primaryBizNo, String sfzx) throws BaseException {
		String sql ="update bu_spfe_mdf set sfzx= ?  where bizNo=?";
		Object params[] =  new Object[]{sfzx, primaryBizNo};
		super.updateBySql(sql,params);
	}

	public void updateDataStatus(String seqno, String tableName,String status) throws BaseException {
		String sql ="update "+tableName+" set sfzx= ?  where seqno=?";
		super.updateBySql(sql, new Object[]{status,seqno});
	}

	public SpfeMdf findByBizNo(String bizNo) throws BaseException {
		String sql ="select * from bu_spfe_mdf where bizno=?";
		return super.findForObjectBySql(sql, new Object[]{bizNo}, SpfeMdf.class);
	}

	public void updateDataStatusByBizNo(String bizNo, String tableName,
			String status) throws BaseException {
		String sql ="update "+tableName+" set sfzx= ?  where bizNo=?";
		super.updateBySql(sql, new Object[]{status,bizNo});
	}

	public String getBizNoByRefNo(String refNo) throws BaseException {
		String sql ="select bizno from  wfl_taskinfo_his where refno=?";
		Map result = super.getJdbcTemplate().queryForMap(sql, new Object[]{refNo});
		return (String) result.get(result.keySet().toArray()[0]);
	}
	public SpfeLmt findNotCancelByBizNo(String bizno,boolean isDetailQuey) throws BaseException {
		String sql = "select t.*,(select refno from wfl_taskinfo_his h where h.bizno = t.bizno) refno from bu_spfe_lmt t where  ";
		Object param[] =new Object[]{bizno}; 
				if(!isDetailQuey){
					sql +="not exists(select 1 from (select bizno from  bu_spfe_csr csr where  csr.primaryBizNo=? union all select bizno from" +
							" bu_spfe_mdf mdf where mdf.primaryBizNo=?) a , wfl_taskinfo_his h where a.bizno=h.bizno) and" ;
					param =new Object[]{bizno,bizno,bizno}; 
				}
				sql += " exists (select 1 from  wfl_taskinfo_his wf where wf.bizno = t.bizno) and t.bizno = ? ";
			
		return super.findForObjectBySql(sql, param, SpfeLmt.class);
	}
	
	public Map<String, Object> findRefNoBySeqNo(String seqno) {
		try {
			StringBuffer sql = new StringBuffer();
			//添加tradeNo返回值，撤销接口要用到 泉州修改 2015-12-04  chao.jiang
			sql.append("select distinct refNo,bizNo,transOrgNo,tradeNo from wfl_taskinfo_his t where t.transstate='4' and not exists(select 1 from bu_spfe_csr c where c.primarybizno=t.bizno) and t.txnserialno=?");
			Map<String, Object> map = super.getJdbcTemplate().queryForMap(sql.toString(), seqno);
			return map;
		} catch (DataAccessException e) {
			log.error("通过交易流水号获取业务参号失败", e);
			return new HashMap<String, Object>();
		}
	}
	
	public SpfeMdf findSpfeMdfByLmtSeqNo(String txnSerialNo, String refNo, String sfzx) {
		List<String> list = new ArrayList<String>();
		String sql ="select t.*,t.seqno primarySeqNo,t.bizno primaryBizNo, B.REFNO from  BU_SPFE_LMT t, wfl_taskinfo_his b where t.seqno=b.txnserialno and b.transState='4' and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=t.bizno) and t.sfzx=? ";
		list.add(sfzx);
		if (StringUtils.isNotBlank(txnSerialNo)) {
			sql += "and t.seqno=? ";
			list.add(txnSerialNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += "and b.refNo=? ";
			list.add(refNo);
		}
		SpfeMdf mode = super.findForObjectBySql(sql, list.toArray(), SpfeMdf.class);
		return mode;
	}
	
	public SpfeMdf findSpfeMkupBySeqNo(String txnSerialNo, String refNo,
			String sfzx) {
		List<String> list = new ArrayList<String>();
		String sql ="select t.*,t.seqno primarySeqNo,t.bizno primaryBizNo, B.REFNO from  BU_SPFE_MKUP t, wfl_taskinfo_his b where t.seqno=b.txnserialno and b.transState='4' and not exists (select 1 from bu_spfe_csr csr where csr.primarybizno=t.bizno) and t.sfzx=? ";
		list.add(sfzx);
		if (StringUtils.isNotBlank(txnSerialNo)) {
			sql += "and t.seqno=? ";
			list.add(txnSerialNo);
		}
		if (StringUtils.isNotBlank(refNo)) {
			sql += "and b.refNo=? ";
			list.add(refNo);
		}
		SpfeMdf mode = super.findForObjectBySql(sql, list.toArray(), SpfeMdf.class);
		return mode;
	}
}
