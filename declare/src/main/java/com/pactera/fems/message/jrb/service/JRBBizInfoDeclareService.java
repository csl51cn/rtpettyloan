package com.pactera.fems.message.jrb.service;

import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import org.global.framework.xmlbeans.bean.DataCheckException;

import java.util.Map;

/**
 * Created by senlin.deng on 2017/5/12.
 */
public interface JRBBizInfoDeclareService {
    Map doRealTimeDeclare(Map<String, Object > map, JRBReqHeaderMsg headerMsg) throws DataCheckException;
}

