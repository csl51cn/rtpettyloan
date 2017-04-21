/*    */ package com.pactera.fems.message.wg.support;
/*    */ 
/*    */ import com.pactera.fems.message.util.WGIntfCodeCfgUtil;
/*    */ import com.pactera.fems.message.wg.domain.WGDS;
/*    */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*    */ import com.pactera.fems.mq.support.MQMsgTransfer;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.global.framework.xmlbeans.bean.DataCheckException;
/*    */ import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGMsgHandler
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(WGMsgHandler.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Object sendMessage(WGDS ds, WGReqMsgHead head)
/*    */     throws DataCheckException
/*    */   {
/* 36 */     String reqMsg = new WGXmlMsgBuilder().buildXml(ds, head);
/* 37 */     log.error("发送的请求报文:\r\n" + reqMsg);
/* 38 */     String retMsg = null;
/*    */     try {
/* 40 */       retMsg = new MQMsgTransfer().sendAndRecvMsg(reqMsg, head);
/*    */     } catch (Exception e) {
/* 42 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400003.getCode(), e.getMessage());
/*    */     }
/* 44 */     Map map = WGIntfCodeCfgUtil.getCfgCache(ds.getClass().getName());
/* 45 */     String serviceMethod = (String)map.get("serviceMethod");
/*    */     
/*    */ 
/*    */ 
/* 49 */     log.error("接收的回执报文:\r\n" + retMsg);
/* 50 */     return WGXmlMsgParser.parseXml(serviceMethod, retMsg);
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\WGMsgHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */