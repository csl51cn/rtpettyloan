package com.pactera.fems.message.jrb.support;


import com.global.fems.client.SocketClient;
import com.pactera.fems.message.jrb.domain.JRBGetTx;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;

import java.util.Map;

public class JRBMsgHandler {

    private static final Logger log = Logger.getLogger(JRBMsgHandler.class);


    public static Object sendMessage(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        String reqMsg = new JRBXmlMsgBuilder().buildXml(getTx, headerMsg);
        log.debug("发送的请求报文:\r\n" + reqMsg);
        String retMsg = null;
        //retMsg="00000590<?xml version=\"1.0\" encoding=\"UTF-8\"?><transaction><header><msg><RET><RET_MSG>Success</RET_MSG><RET_CODE>000000</RET_CODE></RET><TRAN_TIMESTAMP>135132134</TRAN_TIMESTAMP><MESSAGE_TYPE>1210</MESSAGE_TYPE><SOURCE_BRANCH_NO>0001</SOURCE_BRANCH_NO><BRANCH_ID>91500000584252884K</BRANCH_ID><MESSAGE_CODE>0001</MESSAGE_CODE><RET_STATUS>S</RET_STATUS><SERVICE_CODE>SVR_PTLN</SERVICE_CODE><DEST_BRANCH_NO>0001</DEST_BRANCH_NO><TRAN_DATE>20170526</TRAN_DATE><SEQ_NO>2017052600000020</SEQ_NO></msg></header><body><RtrTx><NET_SIGN_ID>305014-20160416-02378690</NET_SIGN_ID></RtrTx></body></transaction>";
        try {
            //发送报文和接收返回的报文
            SocketClient client = new SocketClient();
            retMsg = client.sendMsg(reqMsg);
            log.debug("从服务端接收到的消息 : " + reqMsg);
        } catch (Exception e) {
            //抛出异常
            log.error("JRBMsgHandler:sendMessage()", e);
        }
        //将返回的报文转换成javaBean
        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String serviceMethod = (String) map.get("serviceMethod");
        return JRBXmlMsgParser.parseXml(serviceMethod, retMsg.substring(retMsg.indexOf("<")).trim());

    }


}
