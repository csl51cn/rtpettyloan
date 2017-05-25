package com.pactera.fems.message.jrb.support;


import com.global.fems.client.SocketClient;
import com.pactera.fems.message.jrb.domain.JRBGetTx;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;

public class JRBMsgHandler {

    private static final Logger log = Logger.getLogger(JRBMsgHandler.class);



    public  static  Object sendMessage(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        String reqMsg = new JRBXmlMsgBuilder().buildXml(getTx, headerMsg);
        log.debug("发送的请求报文:\r\n" + reqMsg);


        String retMsg = null;

        try{
            //发送报文和接收返回的报文
            SocketClient client = new SocketClient();
            retMsg = client.sendMsg(reqMsg);
            System.out.println(retMsg);

            // retMsg = new
        }catch (Exception e){
            //抛出异常
        }finally {

        }
        //解析返回的报文
        return null;
    }


}
