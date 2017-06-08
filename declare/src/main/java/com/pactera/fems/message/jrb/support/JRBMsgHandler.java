package com.pactera.fems.message.jrb.support;


import com.global.fems.client.SocketClient;
import com.pactera.fems.message.jrb.domain.JRBGetTx;
import com.pactera.fems.message.jrb.domain.JRBReqHeaderMsg;
import com.pactera.fems.message.jrb.domain.business.request.ContractInfo;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JRBMsgHandler {
    private static final Logger log = Logger.getLogger(JRBMsgHandler.class);



    public static Object sendMessage(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        String reqMsg = new JRBXmlMsgBuilder().buildXml(getTx, headerMsg);
        log.debug("发送的请求报文:\r\n" + reqMsg);
        String retMsg = null;
        try {
            //发送报文和接收返回的报文
            SocketClient client = new SocketClient();
            retMsg = client.sendMsg(reqMsg);
            log.debug("从服务端接收到的消息 : " + retMsg);
        } catch (Exception e) {
            //抛出异常
            log.error("JRBMsgHandler:sendMessage()", e);
        }
        //将返回的报文转换成javaBean
        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String serviceMethod = (String) map.get("serviceMethod");
        return JRBXmlMsgParser.parseXml(serviceMethod, retMsg.substring(retMsg.indexOf("<")).trim());

    }


    public static void sendBatchFile(List contractInfoParamList,ContractInfo contractInfo) throws Exception {
        String reqMsg = new JRBXmlMsgBuilder().buildXml(contractInfoParamList,contractInfo);
        System.out.println(reqMsg);
    }


    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("D:\\sftp\\2017060700000020.xml");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String temp = "";
        while((temp = bufferedReader.readLine())!= null){
            System.out.println(temp);
        }
    }
}
