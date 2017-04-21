package com.global.fems.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.fems.business.dao.SpfeCsrDao;
import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeMdf;
import com.global.framework.dbutils.support.BaseDaoSupport;
import com.global.framework.exception.BaseException;

@Repository("spfeCsrDao")
public class SpfeCsrDaoImpl extends BaseDaoSupport implements SpfeCsrDao{

	public SpfeCsr insert(SpfeCsr mode) throws BaseException {
		try {
			return super.insert(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void update(SpfeCsr mode) throws BaseException {
		try {
			super.update(mode);
		} catch (Exception e) {
			throw new BaseException("数据库操作失败", e);
		}
	}

	public void delete(SpfeCsr mode) throws BaseException {
		super.delete(mode);
	}

	public SpfeCsr findById(String id) throws BaseException {
		String sql = "select t1.*,t2.refno from (select a.*,b.SEQNO primarySeqNo,'000040' tradeNo  from " +
				"  BU_SPFE_CSR a, BU_SPFE_LMT b where a.PRIMARYBIZNO = b.bizno and a.seqno=?" +
				" union  " +
				"  select c.*,d.SEQNO primarySeqNo,'000060' tradeNo  from   BU_SPFE_CSR c, BU_SPFE_MDF d where " +
				" c.PRIMARYBIZNO = d.bizno and c.seqno=? " +
				" union " +
				" select c.*,d.SEQNO primarySeqNo,'000080' tradeNo  from   BU_SPFE_CSR c, BU_SPFE_MKUP d where c.PRIMARYBIZNO = d.bizno and c.seqno=? " +
				") t1, (select * from wfl_taskinfo where txnserialno=? union all select * from wfl_taskinfo_his where txnserialno=? ) t2 where t1.seqno=t2.txnserialno";
		return super.findForObjectBySql(sql, new Object[]{id,id, id,id ,id}, SpfeCsr.class);
	}
	
	public List<SpfeCsr> findList(Map<String, Object> prm) throws BaseException {
		if(prm==null) prm = new HashMap<String, Object>();
		return (List<SpfeCsr>)super.findForList(SpfeCsr.class, prm);
	}

	public SpfeCsr findByKey(String txnSerialNo) throws BaseException {
		String sql = "select a.*,b.refNo from bu_spfe_csr a, wfl_taskinfo b where a.seqno=b.txnserialno and a.seqno=?";
		return super.findForObjectBySql(sql, new Object[]{txnSerialNo}, SpfeCsr.class);
	}

	public SpfeMdf findSpfeInfoByCsrId(String txnSerialNo) {
		String sql = "select c.seqno,a.bizno,a.trade_type,a.occupy_lmt_status,a.biz_type_code,a.idtype_code,a.idcode,a.ctycode,a.add_idcode,a.person_name,a.tx_code,a.salefx_settle_code,a.txccy,a.txamt,a.purfx_cash_amt,a.fcy_remit_amt,a.fcy_acct_amt,a.tchk_amt,a.lcy_acctno_cny,a.lcy_acct_no,a.agent_corp_code,a.agent_corp_name,a.indiv_org_code,a.indiv_org_name,a.pay_org_code,a.capitalno,a.remark,a.txamtusd,a.purfx_cash_amtusd,a.amt_usd,a.amt_balance_usd,a.BIZ_TX_CHNL_CODE,c.CANCEL_REASON,c.CANCEL_REMARK,c.RECODE,c.REMSG,a.biz_tx_time from bu_spfe_lmt a, bu_spfe_csr c where a.bizno=c.primarybizno and c.seqno=? " + 
						"union  " +
					 "select c.seqno,a.bizno,a.trade_type,a.occupy_lmt_status,a.biz_type_code,a.idtype_code,a.idcode,a.ctycode,a.add_idcode,a.person_name,a.tx_code,a.salefx_settle_code,a.txccy,a.txamt,a.purfx_cash_amt,a.fcy_remit_amt,a.fcy_acct_amt,a.tchk_amt,a.lcy_acctno_cny,a.lcy_acct_no,a.agent_corp_code,a.agent_corp_name,a.indiv_org_code,a.indiv_org_name,a.pay_org_code,a.capitalno,a.remark,a.txamtusd,a.purfx_cash_amtusd,a.amt_usd,a.amt_balance_usd,a.BIZ_TX_CHNL_CODE,c.CANCEL_REASON,c.CANCEL_REMARK,c.RECODE,c.REMSG,a.biz_tx_time from bu_spfe_mdf a, bu_spfe_csr c where a.bizno=c.primarybizno and c.seqno=? " + 
						"union  " +
					 "select c.seqno,a.bizno,a.trade_type,a.occupy_lmt_status,a.biz_type_code,a.idtype_code,a.idcode,a.ctycode,a.add_idcode,a.person_name,a.tx_code,a.salefx_settle_code,a.txccy,a.txamt,a.purfx_cash_amt,a.fcy_remit_amt,a.fcy_acct_amt,a.tchk_amt,a.lcy_acctno_cny,a.lcy_acct_no,a.agent_corp_code,a.agent_corp_name,a.indiv_org_code,a.indiv_org_name,a.pay_org_code,a.capitalno,a.remark,a.txamtusd,a.purfx_cash_amtusd,a.amt_usd,a.amt_balance_usd,a.BIZ_TX_CHNL_CODE,c.CANCEL_REASON,c.CANCEL_REMARK,c.RECODE,c.REMSG,a.biz_tx_time from bu_spfe_mkup a, bu_spfe_csr c where a.bizno=c.primarybizno and c.seqno=? ";
		return super.findForObjectBySql(sql, new Object[]{txnSerialNo, txnSerialNo, txnSerialNo}, SpfeMdf.class);
	}
}
