package com.global.workflow.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.framework.exception.BaseException;
import com.global.workflow.dao.TradePrivilegeDao;
import com.global.workflow.domain.TradePrivilege;
import com.global.workflow.service.TradePrivilegeService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Service("tradePrivilegeService")
public class TradePrivilegeServiceImpl implements TradePrivilegeService {

	private static final Log log = LogFactory.getLog(TradePrivilegeServiceImpl.class);

	@Autowired
	private TradePrivilegeDao tradePrivilegeDao;

	
	public List<TradePrivilege> queryTradePrivilege(String tradeNo) throws BaseException {
		return tradePrivilegeDao.queryTradePrivilege(tradeNo);
	}
	
	public List<TradePrivilege> queryTradePrivilegeByMenuID(String menuid)
			throws BaseException {
		return tradePrivilegeDao.queryTradePrivilegeByMenuID(menuid);
	}

	
	public void update(String privilegeForms) throws BaseException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TradePrivilege[] privileges = mapper.readValue(privilegeForms, TradePrivilege[].class);
			for (TradePrivilege privilege : privileges) {
				tradePrivilegeDao.update(privilege);
			}
		} catch (JsonParseException e) {
			log.error("Json Parse Exception ", e);
		} catch (JsonMappingException e) {
			log.error("Json Mapping Exception ", e);
		} catch (IOException e) {
			log.error("IOException ", e);
		}
	}
}
