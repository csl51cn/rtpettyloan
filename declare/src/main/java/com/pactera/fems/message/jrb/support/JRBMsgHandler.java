package com.pactera.fems.message.jrb.support;


import com.global.fems.business.enums.ReturnMsgCodeEnum;
import com.global.fems.client.SFTPClient;
import com.global.fems.client.SocketClient;
import com.pactera.fems.message.jrb.domain.*;
import com.pactera.fems.message.jrb.domain.business.request.BatchFileInfo;
import com.pactera.fems.message.util.JRBIntfCodeCfgUtil;
import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JRBMsgHandler {
    private static final Logger log = Logger.getLogger(JRBMsgHandler.class);


    /**
     * 发送实时报文
     *
     * @param getTx
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public static Object sendMessage(JRBGetTx getTx, JRBReqHeaderMsg headerMsg) throws DataCheckException {

        String reqMsg = new JRBXmlMsgBuilder().buildXml(getTx, headerMsg);
        log.debug("发送的请求报文:\r\n" + reqMsg);
        String retMsg = null;
        // TODO: 2018/4/2 生产环境需要将下面的注释打开
        retMsg = sendMessageBySocket(reqMsg, retMsg);
        //将返回的报文转换成javaBean
        Map map = JRBIntfCodeCfgUtil.getCfgCache(getTx.getClass().getName());
        String serviceMethod = (String) map.get("serviceMethod");
        try {
            return JRBXmlMsgParser.parseXml(serviceMethod, retMsg.substring(retMsg.indexOf("<")).trim());
        } catch (DataCheckException e) {
            e.printStackTrace();
            return JRBXmlMsgParser.xml2JSON(retMsg.substring(retMsg.indexOf("<")).trim());
        }
    }

    /**
     * 发送批量文件
     *
     * @param batchFileInfo
     * @param dataType
     * @param batchNo
     * @return
     * @throws Exception
     */
    public static Map<String,String> sendBatchFile(BatchFileInfo batchFileInfo, String dataType, String batchNo) throws Exception {
        HashMap<String,String> map = new HashMap<>(2);
        JRBXmlMsgBuilder jrbXmlMsgBuilder = new JRBXmlMsgBuilder();
        //生成xml
        String reqMsg = jrbXmlMsgBuilder.buildXml(batchFileInfo);
        //保存xml到本地
        String pathAndFileName = jrbXmlMsgBuilder.saveBatchFile(reqMsg, dataType, batchNo);
        //保存xml到SFTP服务器
        FileInputStream fileInputStream = new FileInputStream(pathAndFileName);
        String destinationFileName = pathAndFileName.substring(pathAndFileName.lastIndexOf("/") + 1);
        // TODO: 2018/4/2 生产环境需要将下面的注释打开
        try {
            SFTPClient.put(fileInputStream, destinationFileName);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            map.put("error", "文件上传失败");
            return map;
        }
        map.put("fileName", destinationFileName);
        return map;
    }


    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("D:\\sftp\\2017060700000020.xml");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String temp = "";
        while ((temp = bufferedReader.readLine()) != null) {
            System.out.println(temp);
        }
    }

    /**
     * 批量文件的实时报文
     *
     * @param headerMsg
     * @return
     * @throws DataCheckException
     */
    public static Object sendMessage(JRBReqBatchFileMsg headerMsg) throws DataCheckException {
        String reqMsg = new JRBXmlMsgBuilder().buildXml(headerMsg);
        log.debug("发送的请求报文:\r\n" + reqMsg);
        String retMsg = null;
        // TODO: 2018/4/2 生产环境需要将下面的注释打开
        retMsg = sendMessageBySocket(reqMsg, retMsg);
        //将返回的报文转换成javaBean
        JRBRespBatchFileMsg jrbRespBatchFileMsg;
        if (retMsg.contains(ReturnMsgCodeEnum.RETURNCODE_1708.getCode())) {
            JRBRET jrbret = new JRBRET();
            jrbret.setRetCode(ReturnMsgCodeEnum.RETURNCODE_1708.getCode());
            jrbret.setRetMsg(ReturnMsgCodeEnum.RETURNCODE_1708.getValue());
            JRBRespHeaderMsg jrbRespHeaderMsg = new JRBRespHeaderMsg();
            jrbRespHeaderMsg.setRet(jrbret);
            JRBRespHeader jrbRespHeader = new JRBRespHeader();
            jrbRespHeader.setMsg(jrbRespHeaderMsg);
            jrbRespBatchFileMsg = new JRBRespBatchFileMsg();
            jrbRespBatchFileMsg.setHeader(jrbRespHeader);
        } else if (retMsg.contains(ReturnMsgCodeEnum.RETURNCODE_1709.getCode())) {
            JRBRET jrbret = new JRBRET();
            jrbret.setRetCode(ReturnMsgCodeEnum.RETURNCODE_1709.getCode());
            jrbret.setRetMsg(ReturnMsgCodeEnum.RETURNCODE_1709.getValue());
            JRBRespHeaderMsg jrbRespHeaderMsg = new JRBRespHeaderMsg();
            jrbRespHeaderMsg.setRet(jrbret);
            JRBRespHeader jrbRespHeader = new JRBRespHeader();
            jrbRespHeader.setMsg(jrbRespHeaderMsg);
            jrbRespBatchFileMsg = new JRBRespBatchFileMsg();
            jrbRespBatchFileMsg.setHeader(jrbRespHeader);
        } else {
            Map map = JRBIntfCodeCfgUtil.getCfgCache(headerMsg.getClass().getName());
            String serviceMethod = (String) map.get("serviceMethod");
            jrbRespBatchFileMsg = (JRBRespBatchFileMsg) JRBXmlMsgParser.parseXml(serviceMethod, retMsg.substring(retMsg.indexOf("<")).trim());
        }

        return jrbRespBatchFileMsg;

    }

    /**
     * 发送报文
     *
     * @param reqMsg
     * @param retMsg
     * @return
     */
    private static String sendMessageBySocket(String reqMsg, String retMsg) {
        try {
            //发送报文和接收返回的报文
            SocketClient client = new SocketClient();
            retMsg = client.sendMsg(reqMsg);
            log.debug("从服务端接收到的消息 : " + retMsg);
        } catch (Exception e) {
            //抛出异常
            log.error("JRBMsgHandler:sendMessage()", e);
        }
        return retMsg;
    }
}
