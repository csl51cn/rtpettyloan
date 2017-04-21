package com.global.fems.message.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
import org.global.framework.xmlbeans.util.PropertyUtils;

import com.global.fems.business.domain.SafeExRate;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.fems.message.domain.business.response.RspIndividualLCYQuery;
import com.global.fems.message.domain.business.response.RspIndividualLCYRegister;
import com.global.fems.message.domain.business.response.RspSafeExRateQuery;
import com.global.fems.message.domain.business.response.RspSafeExRateQueryRec;
import com.global.framework.dbutils.support.PageBean;
import com.pactera.fems.message.wg.constants.BizTypeEnum;

/**
 * 数据转换工具  map =>>实体
 * @author longjun
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IndividualLCYDataConver {

	
	/**
	 * -美元折算率查询  数据转换
	 * @param ret
	 * @return
	 */
	public static RspSafeExRateQuery toRspSafeExRateQuery(PageBean pageBean) throws DataCheckException {
		try {
			RspSafeExRateQuery rsp = new RspSafeExRateQuery();
			List rec = new ArrayList();
			String totalCount = String.valueOf(pageBean.getTotalRows());
			String pageSize = String.valueOf(pageBean.getRows());
			String pageNumber = String.valueOf(pageBean.getPage());
			List rows = (List) pageBean.getDataList();
			if(rows !=null && rows.size()>0){
				for(int i=0;i<rows.size();i++){
					SafeExRate ret = (SafeExRate) rows.get(i);
					RspSafeExRateQueryRec row = new RspSafeExRateQueryRec();
					row.setCURRENCY_CODE(ret.getCurrencyCode());
					row.setEXCHANGE(ret.getExchange());
					row.setYEAR_MONTH(ret.getYearMonth());
					rec.add(row);
				}
			}
			rsp.setRec(rec);
			rsp.setTotalCount(totalCount);
			rsp.setPAGENUMBER(pageNumber);
			rsp.setPAGESIZE(pageSize);
			return rsp;
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"美元折算率查询结果转换失败：" + e.getMessage());
		}
	}
	
	
//	/**
//	 * -美元折算率查询  数据转换
//	 * @param ret
//	 * @return
//	 */
//	public static RspSafeExRateQuery toRspSafeExRateQuery(Map ds) throws DataCheckException {
//		try {
//			RspSafeExRateQuery rsp = new RspSafeExRateQuery();
//			List rec = new ArrayList();
//			String totalCount = (String) ds.get("RECORDCOUNT");
//			String pageSize = (String) ds.get("PAGESIZE");
//			String pageNumber = (String) ds.get("PAGENUMBER");
//			List rows = (List) ds.get("ROWSET");
//			if(rows !=null && rows.size()>0){
//				for(int i=0;i<rows.size();i++){
//					Map rowm = (Map) rows.get(i);
//					if(rowm == null) continue;
//					RspSafeExRateQueryRec row = new RspSafeExRateQueryRec();
//					row.setCURRENCY_CODE((String)rowm.get("CURRENCY_CODE"));
//					row.setEXCHANGE((String)rowm.get("EXCHANGE"));
//					row.setYEAR_MONTH((String)rowm.get("YEAR_MONTH"));
//					rec.add(row);
//				}
//			}
//			rsp.setRec(rec);
//			rsp.setTotalCount(totalCount);
//			rsp.setPAGENUMBER(pageNumber);
//			rsp.setPAGESIZE(pageSize);
//			return rsp;
//		} catch (Exception e) {
//			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"美元折算率查询结果转换失败：" + e.getMessage());
//		}
//	}

	
	/**
	 * 000006-个人结售汇信息修改 请求数据转换
	 * @param ds
	 * @return
	 * @throws Exception 
	 */
	public static Map toRecvIndividualLCYModifyMap(RecvIndividualLCYModify ds, String txnSerialNo, String refNo) throws DataCheckException{
		try {
			Map data = new HashMap();
			Map PARAMETERS = bean2MapPropertiesCopy(ds,new HashMap());
			if ("JH".equals(ds.getTRADE_TYPE())){
				PARAMETERS.put("SALEFX_AMT", ds.getTXAMT());
				PARAMETERS.put("SALEFX_TX_CODE", ds.getTX_CODE());
				PARAMETERS.put("LCY_ACCT_CNY", ds.getLCY_ACCTNO_CNY());//结售汇人民币账户
			}else  if ("GH".equals(ds.getTRADE_TYPE())){
				PARAMETERS.put("PURFX_AMT", ds.getTXAMT());
				PARAMETERS.put("PURFX_TYPE_CODE", ds.getTX_CODE());
				PARAMETERS.put("PURFX_ACCT_CNY", ds.getLCY_ACCTNO_CNY());//结售汇人民币账户
			}
			PARAMETERS.put("BANK_SELF_NUM", txnSerialNo);//修改交易自身流水号
			PARAMETERS.put("REFNO", refNo);
			data.put("PARAMETERS", PARAMETERS);
			return data;
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"个人结售汇信息修改请求数据转换失败:" + e.getMessage());
		}
	}
	
	/**
	 * 000004-个人结售汇额度登记
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public static Map toRecvIndividualLCYRegister(RecvIndividualLCYRegister ds, String txNo, String bizChnlCode) throws DataCheckException{
		try {
			Map data = new HashMap();
			Map PARAMETERS  = new HashMap();
			boolean  isJH = "JH".equals(ds.getTRADE_TYPE())?true:false;
			if("Y".equals(ds.getOCCUPY_LMT_STATUS())){//占用额度
				PARAMETERS = bean2MapPropertiesCopy(ds,new HashMap());
				PARAMETERS.put(isJH?"SALEFX_TX_CODE":"PURFX_TYPE_CODE", ds.getTX_CODE());//结售汇资金属性
				PARAMETERS.put(isJH?"SALEFX_AMT":"PURFX_AMT", ds.getTXAMT());//结售汇金额
				PARAMETERS.put(isJH?"LCY_ACCT_CNY":"PURFX_ACCT_CNY", ds.getLCY_ACCTNO_CNY());//结售汇人民币账户
				PARAMETERS.put("BIZ_TX_CHNL_CODE", bizChnlCode);
				//银行业务流水号
				PARAMETERS.put("BANK_SELF_NUM", txNo);
			}else{//不占用额度
				PARAMETERS.put("BIZ_TYPE_CODE", ds.getBIZ_TYPE_CODE());
				List ROWSET = new ArrayList();
				Map row = bean2MapPropertiesCopy(ds,new HashMap());
				row.put(isJH?"SALEFX_TX_CODE":"PURFX_TYPE_CODE", ds.getTX_CODE());
				row.put(isJH?"SALEFX_AMT":"PURFX_AMT", ds.getTXAMT());
				row.put(isJH?"LCY_ACCT_CNY":"PURFX_ACCT_CNY", ds.getLCY_ACCTNO_CNY());//结售汇人民币账户
				row.put("BIZ_TX_CHNL_CODE", bizChnlCode);
				//银行业务流水号
				row.put("BANK_SELF_NUM", txNo);
				ROWSET.add(row);
				data.put("ROWSET", ROWSET);
			}
			data.put("PARAMETERS", PARAMETERS);
			return data;
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"个人结售汇信息录入请求数据转换失败:"+e.getMessage());
		}
	}
	
	
	/**
	 * 个人结售汇信息补录
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public static Map toRecvIndividualLCYMakeUp(RecvIndividualLCYMakeUp ds, String txNo, String bizChnlCode) throws DataCheckException{
		try {
			Map data = new HashMap();
			Map PARAMETERS  = new HashMap();
			boolean  isJH = "JH".equals(ds.getTRADE_TYPE())?true:false;
			List ROWSET = new ArrayList();
			Map row = bean2MapPropertiesCopy(ds,new HashMap());
			row.put(isJH?"SALEFX_TX_CODE":"PURFX_TYPE_CODE", ds.getTX_CODE());//结售汇资金属性
			row.put(isJH?"SALEFX_AMT":"PURFX_AMT", ds.getTXAMT());//结售汇金额
			row.put(isJH?"LCY_ACCT_CNY":"PURFX_ACCT_CNY", ds.getLCY_ACCTNO_CNY());//结售汇人民币账户
			row.put("BANK_SELF_NUM", txNo);
			row.put("BIZ_TX_CHNL_CODE", bizChnlCode);//业务办理渠道代码
			row.put("BIZ_TYPE_CODE",  ds.getBIZ_TYPE_CODE());
			if(BizTypeEnum.TYPE_05.getCode().equals(ds.getBIZ_TYPE_CODE())){//当为支付机构时， 
				row.put("FCY_REMIT_AMT",  ds.getTXAMT());
			}
			//银行业务流水号
			ROWSET.add(row);
			data.put("ROWSET", ROWSET);
			if("N".equals(ds.getOCCUPY_LMT_STATUS())){//不占用额度
				PARAMETERS.put("BIZ_TYPE_CODE", ds.getBIZ_TYPE_CODE());
				data.put("PARAMETERS", PARAMETERS);
			}
			return data;
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"个人结售汇信息补录请求数据转换失败:"+e.getMessage());
		}
	}
	
	
	/**
	 * 人结售汇额度查询
	 * @param ret 返回的报文数据 
	 * @param type JH 或者 GH
	 * @return
	 */
	public static RspIndividualLCYQuery toRspIndividualLCYQuery(Map ds,String type) throws DataCheckException {
		try {
			RspIndividualLCYQuery rsp = new RspIndividualLCYQuery();
			List list = (List) ds.get("ROWSET");
			if (list != null && list.size() > 0) {
				Map m = (Map) list.get(0);
				rsp.setTRADE_TYPE(type);
				rsp.setAMT_USD((String) m.get("JH".equals(type) ? "ANN_LCYAMT_USD" : "ANN_FCYAMT_USD"));
				rsp.setAMT_BALANCE_USD((String) m.get("JH".equals(type)?"ANN_REM_LCYAMT_USD":"ANN_REM_FCYAMT_USD"));
				rsp.setTODAY_AMT_USD((String) m.get("TODAY_CASH_USD"));
				rsp.setCUSTNAME((String) m.get("CUSTNAME"));
				rsp.setCUSTTYPE_CODE((String) m.get("CUSTTYPE_CODE"));
				rsp.setTYPE_STATUS((String) m.get("TYPE_STATUS"));
				rsp.setPUB_DATE((String) m.get("PUB_DATE"));
				rsp.setEND_DATE((String) m.get("END_DATE"));
				rsp.setPUB_CODE((String) m.get("PUB_CODE"));
				rsp.setPUB_REASON((String) m.get("PUB_REASON"));
				rsp.setSIGN_STATUS((String) m.get("SIGN_STATUS"));
			}
			return rsp;
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),"个人结售汇额度查询失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param ret
	 * @return
	 * @throws Exception 
	 */
	public static RspIndividualLCYRegister toRspIndividualLCYRegister(Map ds,String type,String OCCUPY_LMT_STATUS) throws DataCheckException{
		RspIndividualLCYRegister  rsp = new RspIndividualLCYRegister();
		boolean isJH = "JH".equals(type)?true:false;
		Map row = (Map) ((List)ds.get("ROWSET")).get(0);
		map2BeanPropertiesCopy(rsp,row);
		if("Y".equals(OCCUPY_LMT_STATUS)){
			rsp.setAMT_USD((String)row.get(isJH? "SALEFX_AMT_USD" : "PURFX_AMT_USD"));
			rsp.setAMT_BALANCE_USD((String)row.get(isJH?"ANN_REM_LCYAMT_USD":"ANN_REM_FCYAMT_USD"));
		}
		return rsp;
	}
	
	/**
	 * 
	 * @param ds
	 * @param refno
	 * @return
	 */
	public static Map toRecvIndividualLCYCancelMap(RecvIndividualLCYCancel ds, String txnSerialNo, 
			String refno) {
		Map data = new HashMap();
		Map param = new HashMap();
		param.put("REFNO", refno);//业务参号，通过前置系统业务流水号去数据库查询
		param.put("BANK_SELF_NUM", txnSerialNo);//撤消交易自身流水号
		param.put("CANCEL_REASON", ds.getCANCEL_REASON());//撤销原因代码
		param.put("CANCEL_REMARK", ds.getCANCEL_REMARK());//撤销说明 :撤销原因为“其他”时不允许为空
		data.put("PARAMETERS", param);
		return data;
	}
	
	
	public static void map2BeanPropertiesCopy(Object obj,Map data)throws DataCheckException{
		try {
			Field fs[] = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());
			if(fs == null) return ;
			for(Field f:fs){
				String fname = f.getName();
				String methName = "set"+fname.substring(0,1).toUpperCase()+fname.substring(1,fname.length());
				Method setter = PropertyUtils.getDeclaredMethod(obj.getClass(), methName,new Class[]{f.getType()});
				Object val = data.get(fname);
//				if(f.getType().equals(double.class) ||f.getType().equals(Double.class)){
//					val = Double.valueOf("0").doubleValue();
//				}
				setter.invoke(obj, new Object[]{(val == null) ? "" : val});
			}
		} catch (Exception e) {
			throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e.getMessage());
		}
	}
	
	/**
	 * 字段满足都是大写的 bean 对象 字段值copy 到 map中
	 * @param obj
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static  Map bean2MapPropertiesCopy(Object obj,Map data) throws Exception{
		Field fs[] = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());
		if(fs == null) return data;
		for(Field f:fs){
			String fname = f.getName();
			String methName = "get"+fname.substring(0,1).toUpperCase()+fname.substring(1,fname.length());
			Method getter = PropertyUtils.getDeclaredMethod(obj.getClass(), methName,new Class[]{});
			data.put(fname, getter.invoke(obj, new Object[]{}));
		}
		return data;
	}
	
	public static Map getRetHead(Map map) {
		Map head = (Map) map.get("head");
		return head;
	}
	
	public static Map getDSRetMap(Map ret) {
		Map body = (Map) ret.get("body");
		Map dataStores = (Map) body.get("dataStores");
		Map ds = (Map) dataStores.get("ds");
		return ds;
	}
}
