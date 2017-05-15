package com.pactera.fems.message.jrb.service.impl;

import com.global.framework.util.StringUtil;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.RealTimeOnlineContract;
import com.pactera.fems.message.jrb.service.JRBBizInfoDeclareService;
import com.pactera.fems.message.jrb.support.JRBMsgHandler;
import com.pactera.fems.message.wg.support.WGDSValitorHander;
import org.apache.commons.lang.StringUtils;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.util.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by senlin.deng on 2017/5/12.
 */
public class JRBBizInfoDeclareServiceImpl implements JRBBizInfoDeclareService {


    public Map doRealTimeDeclare(Map<String, Object > map, JRBReqHeaderMsg headerMsg) throws DataCheckException {
        RealTimeOnlineContract realTimeOnlineContract = new RealTimeOnlineContract();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            Object value = next.getValue();
            if(value == null || value == ""){
                iterator.remove();
            }
        }

        //校验数据
        //校验通过,暂时用外管的
       WGDSValitorHander.setFeild(realTimeOnlineContract, map);


        JRBMsgHandler.sendMessage(realTimeOnlineContract,headerMsg,"/xmlcfg/request/JRBReqRealTimeContract.xml");



        return null;
    }



}
