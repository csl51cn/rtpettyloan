/*    */ package com.pactera.fems.message.wg.support;
/*    */ 
/*    */ import com.pactera.fems.message.util.WGIntfCodeCfgUtil;
/*    */ import com.pactera.fems.message.wg.domain.WGDS;
/*    */ import com.pactera.fems.message.wg.domain.WGDataStores;
/*    */ import com.pactera.fems.message.wg.domain.WGMsgBody;
/*    */ import com.pactera.fems.message.wg.domain.WGMsgTail;
/*    */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*    */ import com.pactera.fems.message.wg.domain.WGRspMsg;
/*    */ import com.pactera.fems.message.wg.domain.WGRspMsgHead;
/*    */ import java.util.Map;
/*    */ import org.global.framework.xmlbeans.handler.Bean2XmlHandler;
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
/*    */ public class BaseRspXMLGenerator
/*    */ {
/*    */   public static String buildRspXml(WGDS ds, WGReqMsgHead head)
/*    */     throws Exception
/*    */   {
/* 38 */     Map map = WGIntfCodeCfgUtil.getCfgCache(ds.getClass().getName());
/*    */     
/* 40 */     WGRspMsg msg = new WGRspMsg();
/* 41 */     msg.setHead(buildRspMsgHead((String)map.get("serviceMethod"), head.getMSGNO()));
/* 42 */     msg.setBody(buildMsgBody(ds));
/* 43 */     msg.setTail(buildMsgTail());
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 50 */     String xml = new Bean2XmlHandler().toXml(msg, (String)map.get("rspXmlMapping"), "GBK");
/*    */     
/* 52 */     return xml;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static WGRspMsgHead buildRspMsgHead(String method, String msgNo)
/*    */   {
/* 61 */     WGRspMsgHead head = new WGRspMsgHead();
/* 62 */     head.setVER("2014");
/* 63 */     head.setSRC("000133");
/* 64 */     head.setDES("1000");
/* 65 */     head.setSRCAPP("CORPMIS");
/* 66 */     head.setDESAPP("PMIS");
/* 67 */     head.setCODE("00000");
/* 68 */     head.setDETAIL("操作成功");
/* 69 */     head.setMSGNO(msgNo);
/* 70 */     return head;
/*    */   }
/*    */   
/*    */   private static WGMsgBody buildMsgBody(WGDS data) {
/* 74 */     WGMsgBody body = new WGMsgBody();
/* 75 */     body.setDataStores(new WGDataStores(data));
/* 76 */     return body;
/*    */   }
/*    */   
/*    */   private static WGMsgTail buildMsgTail() {
/* 80 */     WGMsgTail tail = new WGMsgTail();
/* 81 */     return tail;
/*    */   }
/*    */   
/*    */   static {}
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\BaseRspXMLGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */