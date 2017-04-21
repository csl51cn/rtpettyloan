package com.global.fems.business.support;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;

import com.global.fems.business.domain.SpfeCsr;
import com.global.fems.business.domain.SpfeLmt;
import com.global.fems.business.domain.SpfeMdf;
import com.global.fems.business.domain.SpfeMkUp;
import com.global.fems.business.enums.TradeTypeEnum;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYCancel;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYMakeUp;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYModify;
import com.global.fems.message.domain.business.receive.RecvIndividualLCYRegister;
import com.global.fems.message.service.impl.IndividualMsgBizManager;
import com.global.fems.message.support.IndMessageValidator;
import com.global.fems.message.support.IndividualLCYDataConver;
import com.global.framework.system.domain.User;
import com.global.framework.util.SpringContextUtil;
import com.pactera.fems.message.wg.support.WGDSValitorHander;

@SuppressWarnings("rawtypes")
public class ValidateHandler {
	private static final Logger log = Logger.getLogger(ValidateHandler.class);
	private static IndividualMsgBizManager individualMsgBizManager = (IndividualMsgBizManager) SpringContextUtil.getBean("individualMsgBizManager");

	public static String validateSpfeCsr(SpfeCsr mode){
		RecvIndividualLCYCancel ds = new RecvIndividualLCYCancel();
		ds.setCANCEL_REASON(mode.getCANCEL_REASON());
		ds.setCANCEL_REMARK(mode.getCANCEL_REMARK());
		ds.setSEQNO(mode.getPrimaryBizNo());
		ds.setTRADE_TYPE(mode.getTRADE_TYPE());
		try {
			IndMessageValidator.validateIndividualLCYCancel(IndividualLCYDataConver.toRecvIndividualLCYCancelMap(ds, null, null));
		} catch (DataCheckException e) {
			log.error(e.getCode(), e);
			return e.getMessage();
		}
		return "";
	}
	
	public static String validateSpfeLmt(SpfeLmt mode, User user){
		RecvIndividualLCYRegister ds = new RecvIndividualLCYRegister();
		try {
			BeanUtils.copyProperties(ds, mode);
		} catch (Exception e) {
			log.error(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e);
			return MsgErrorCodeEnum.ERRCODE_999999.getCode()+e.getMessage();
		}
		try {
			Map ret = individualMsgBizManager.doQueryIndividualFXSEAQuota(ds.getIDTYPE_CODE(),
					ds.getIDCODE(), ds.getCTYCODE(), ds.getTRADE_TYPE(), user.getUserCode());
			
			IndMessageValidator.validateIndividualLCYRegister(ds,mode.getBIZ_TX_CHNL_CODE(),ret);
		} catch (DataCheckException e) {
			log.error(e.getCode(), e);
			return e.getMessage();
		}
		return "";
	}
	public static String validateSpfeMdf(SpfeMdf mode){
		RecvIndividualLCYModify ds = new RecvIndividualLCYModify();
		try {
			BeanUtils.copyProperties(ds, mode);
		} catch (Exception e) {
			log.error(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e);
			return MsgErrorCodeEnum.ERRCODE_999999.getCode()+e.getMessage();
		}
		try {
			IndMessageValidator.validateIndividualLCYModify(ds);
		} catch (DataCheckException e) {
			log.error(e.getCode(), e);
			return e.getMessage();
		}
		return "";
	}
	public static String validateSpfeMkUp(SpfeMkUp mode, User user){
		RecvIndividualLCYMakeUp ds = new RecvIndividualLCYMakeUp();
		try {
			BeanUtils.copyProperties(ds, mode);
		} catch (Exception e) {
			log.error(MsgErrorCodeEnum.ERRCODE_999999.getCode(),e);
			return MsgErrorCodeEnum.ERRCODE_999999.getCode()+e.getMessage();
		}
		try {
			Map ret = individualMsgBizManager.doQueryIndividualFXSEAQuota(ds.getIDTYPE_CODE(),
					ds.getIDCODE(), ds.getCTYCODE(), ds.getTRADE_TYPE(), user.getUserCode());
			
			IndMessageValidator.validateIndividualLCYMakeUp(ds,mode.getBIZ_TX_CHNL_CODE(),ret);
		} catch (DataCheckException e) {
			log.error(e.getCode(), e);
			return e.getMessage();
		}
		return "";
	}
}
