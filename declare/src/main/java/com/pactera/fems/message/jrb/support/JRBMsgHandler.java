package com.pactera.fems.message.jrb.support;


import com.pactera.fems.message.jrb.domain.JRBGetTx;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;

public class JRBMsgHandler {

    private static final Logger log = Logger.getLogger(JRBMsgHandler.class);


    public  static  Object sendMessage(JRBGetTx getTx, JRBReqHeaderMsg headerMsg, String reqXmlMapping) throws DataCheckException {

        String reqMsg = new JRBXmlMsgBuilder().buildXml(getTx, headerMsg, reqXmlMapping);
        log.error("发送的请求报文:\r\n" + reqMsg);
        String retMsg = null;
        try{
            //发送报文和接收返回的报文

           // retMsg = new
        }catch (Exception e){
            //抛出异常
        }
        //解析返回的报文
        return null;
    }


}
