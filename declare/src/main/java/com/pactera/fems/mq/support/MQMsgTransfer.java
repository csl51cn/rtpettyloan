package com.pactera.fems.mq.support;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.pactera.fems.message.wg.domain.WGReqMsgHead;
import com.pactera.fems.mq.util.MQConfigUtil;
import com.pactera.fems.mq.util.UUIDGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.global.framework.xmlbeans.bean.DataCheckException;
import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;


public class MQMsgTransfer {
    private static final Logger log = Logger.getLogger(MQMsgTransfer.class);

    private MQQueue sendQueue = null;
    private MQQueue respQueue = null;
    private MQQueueManager qMgr = null;
    private static String hostName;
    private static String channel;
    private static String qManager;
    private static String sendQueueName;
    private static String recvQueueName;
    private static int timeout;
    private static int mqexpiry;
    private static int port;
    private static int ccsid;

    private void init(String bankCode) {
        Map<String, Map<String, String>> m = MQConfigUtil.getInstance().getMQConfigMap();
        Map<String, String> map = (Map) m.get(bankCode);
        log.error("金融机构网点代码为:" + bankCode);

        hostName = (String) map.get("MQServerIP");
        log.error("机构代码[" + bankCode + "]的MQ服务器IP地址:" + hostName);

        channel = (String) map.get("MQServerChannel");
        log.error("机构代码[" + bankCode + "]的MQ服务器通道:" + channel);

        qManager = (String) map.get("QueueManagerName");
        log.error("机构代码[" + bankCode + "]的队列管理器名称:" + qManager);

        sendQueueName = (String) map.get("SendQueueName");
        log.error("机构代码[" + bankCode + "]的发送队列名称:" + sendQueueName);

        recvQueueName = (String) map.get("RecvQueueName");
        log.error("机构代码[" + bankCode + "]的接收队列名称:" + recvQueueName);

        timeout = Integer.parseInt((String) map.get("timeout")) * 1000;
        log.error("机构代码[" + bankCode + "]的MQ连接超时时间:" + (String) map.get("timeout"));

        mqexpiry = Integer.parseInt((String) map.get("mqexpiry")) * 1000;
        log.error("机构代码[" + bankCode + "]的MQ消息过期时间:" + (String) map.get("mqexpiry"));

        port = Integer.parseInt((String) map.get("MQServerPort"));
        log.error("机构代码[" + bankCode + "]的MQ服务器端口:" + (String) map.get("MQServerPort"));

        ccsid = Integer.parseInt((String) map.get("MqServerCCSID"));
        log.error("机构代码[" + bankCode + "]的MQ服务器字符编码:" + (String) map.get("MqServerCCSID"));

        Collection msgComp = new Vector();
        msgComp.add(new Integer(2));
        msgComp.add(new Integer(1));
        msgComp.add(new Integer(4));
        msgComp.add(new Integer(8));
        msgComp.add(new Integer(0));
        msgComp.add(new Integer(-1));

        MQEnvironment.msgCompList = msgComp;
        MQEnvironment.hostname = hostName;
        MQEnvironment.channel = channel;
        MQEnvironment.port = port;
        MQEnvironment.properties.put("transport", "MQSeries Client");
        MQEnvironment.CCSID = ccsid;
        try {
            this.qMgr = new MQQueueManager(qManager);
        } catch (Exception e) {
            closeQMgr();
            log.error("初始化创建队列管理器MQQueueManager出错", e);
        }
    }


    public String sendAndRecvMsg(String message, WGReqMsgHead head)
            throws DataCheckException {
        init(head.getSRC());

        String msgid = head.getMSGNO() + UUIDGenerator.getUUID();
        createQMgr();

        int openOptions = 8208;
        try {
            this.sendQueue = this.qMgr.accessQueue(sendQueueName, openOptions);
        } catch (MQException e1) {
            log.error("调用发送队列MQ出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "调用发送队列MQ出错:" + e1.getMessage());
        }

        MQPutMessageOptions pmo = new MQPutMessageOptions();


        MQMessage putMessage = new MQMessage();
        putMessage.format = "";
        putMessage.messageId = MQC.MQMI_NONE;
        putMessage.expiry = mqexpiry;
        String correlId = "REQ\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000";
        putMessage.correlationId = correlId.getBytes();
        putMessage.messageId = msgid.getBytes();
        try {
            putMessage.write(message.getBytes("GBK"));

            this.sendQueue.put(putMessage, pmo);
        } catch (UnsupportedEncodingException e1) {
            log.error("发送报文字符集转换出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "发送报文字符集转换出错:" + e1.getMessage());
        } catch (IOException e1) {
            log.error("发送报文写入消息缓冲区出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "发送报文写入消息缓冲区出错:" + e1.getMessage());
        } catch (MQException e1) {
            log.error("将发送消息写入消息队列出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "将发送消息写入消息队列出错:" + e1.getMessage());
        } finally {
            closeQueue(this.sendQueue, sendQueueName);
        }


        openOptions = 8226;
        try {
            this.respQueue = this.qMgr.accessQueue(recvQueueName, openOptions);
        } catch (MQException e1) {
            log.error("调用接收队列MQ出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "调用接收队列MQ出错:" + e1.getMessage());
        }
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options += 2;
        gmo.options += 1;
        gmo.matchOptions = 2;
        gmo.waitInterval = timeout;

        MQMessage respMessage = new MQMessage();
        respMessage.messageId = MQC.MQMI_NONE;
        respMessage.messageId = putMessage.messageId;
        try {
            this.respQueue.get(respMessage, gmo);
        } catch (MQException e1) {
            log.error("接收回执消息出错:", e1);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "接收回执消息出错:" + e1.getMessage());
        } finally {
            closeQueue(this.respQueue, recvQueueName);
        }
        try {
            byte[] msgBuffer = new byte[respMessage.getMessageLength()];
            respMessage.readFully(msgBuffer);
            String response = new String(msgBuffer, "GBK");
            return response;
        } catch (UnsupportedEncodingException e) {
            log.error("回执报文字符集转换出错:", e);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "回执报文字符集转换出错:" + e.getMessage());
        } catch (IOException e) {
            log.error("获取消息缓冲区回执信息出错:", e);
            throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "获取消息缓冲区回执信息出错:" + e.getMessage());
        } finally {
            closeQMgr();
        }
    }

    private void closeQueue(MQQueue queue, String queueName) {
        if (queue != null) {
            try {
                queue.close();
            } catch (MQException e) {
                log.error("关闭队列[" + queueName + "]出错:", e);
            }
        }
    }

    private synchronized void createQMgr() throws DataCheckException {
        if (this.qMgr == null) {
            try {
                this.qMgr = new MQQueueManager(qManager);
            } catch (MQException e) {
                closeQMgr();
                log.error("创建队列管理器MQQueueManager[" + qManager + "]出错", e);
                throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), "创建队列管理器MQQueueManager[" + qManager + "]出错" + e.getMessage());
            }
        }
    }


    private void closeQMgr() {
        if (this.qMgr != null) {
            try {
                this.qMgr.disconnect();
                this.qMgr.close();
                this.qMgr = null;
            } catch (MQException e1) {
                log.error("关闭队列管理器[" + qManager + "]出错:", e1);
            }
        }
    }
}


