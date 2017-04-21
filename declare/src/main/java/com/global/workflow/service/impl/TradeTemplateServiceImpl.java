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
import com.global.workflow.dao.TradeTemplateDao;
import com.global.workflow.domain.TradeTemplate;
import com.global.workflow.service.TradeTemplateService;

/**
 * 
 * @author chen.feng
 * @date 2015-6-25
 * @version v1.0
 */
@Service("tradeTemplateService")
public class TradeTemplateServiceImpl implements TradeTemplateService {

	private static final Log log = LogFactory.getLog(TradePrivilegeServiceImpl.class);
	
	@Autowired
	private TradeTemplateDao tradeTemplateDao;
	
	
	public List<TradeTemplate> queryTradeTemplate() throws BaseException {
		return tradeTemplateDao.queryTradeTemplate();
	}

	
	public void update(String tradeTempForms) throws BaseException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TradeTemplate[] temps = mapper.readValue(tradeTempForms, TradeTemplate[].class);
			for (TradeTemplate temp : temps) {
				tradeTemplateDao.update(temp);
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
