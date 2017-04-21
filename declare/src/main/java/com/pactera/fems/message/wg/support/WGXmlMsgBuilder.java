/*    */ package com.pactera.fems.message.wg.support;
/*    */ 
/*    */ import com.pactera.fems.message.util.DateTimeUtil;
/*    */ import com.pactera.fems.message.util.WGIntfCodeCfgUtil;
/*    */ import com.pactera.fems.message.wg.domain.WGDS;
/*    */ import com.pactera.fems.message.wg.domain.WGDataStores;
/*    */ import com.pactera.fems.message.wg.domain.WGMsgBody;
/*    */ import com.pactera.fems.message.wg.domain.WGMsgTail;
/*    */ import com.pactera.fems.message.wg.domain.WGReqMsg;
/*    */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*    */ import java.util.Map;
/*    */ import org.global.framework.xmlbeans.bean.DataCheckException;
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
/*    */ public class WGXmlMsgBuilder
/*    */ {
/*    */   public String buildXml(WGDS ds, WGReqMsgHead head)
/*    */     throws DataCheckException
/*    */   {
/* 39 */     Map map = WGIntfCodeCfgUtil.getCfgCache(ds.getClass().getName());
/* 40 */     String serviceMethod = (String)map.get("serviceMethod");
/* 41 */     WGReqMsg msg = new WGReqMsg();
/* 42 */     msg.setHead(buildMsgHead(serviceMethod, head));
/* 43 */     msg.setBody(buildMsgBody(ds));
/* 44 */     msg.setTail(buildMsgTail());
/* 45 */     String reqXmlMapping = (String)map.get("reqXmlMapping");
/* 46 */     String xml = new Bean2XmlHandler().toXml(msg, reqXmlMapping, "GBK");
/* 47 */     xml = "<?xml version='1.0' encoding='GBK'?>\r\n" + xml;
/* 48 */     return xml;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private WGReqMsgHead buildMsgHead(String method, WGReqMsgHead head)
/*    */   {
/* 57 */     head.setSENDTIME(DateTimeUtil.getCurrentDateTime());
/* 58 */     head.setSERVICEPATH(method);
/* 59 */     return head;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private WGMsgBody buildMsgBody(WGDS data)
/*    */   {
/* 69 */     WGMsgBody body = new WGMsgBody();
/* 70 */     body.setDataStores(new WGDataStores(data));
/* 71 */     return body;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private WGMsgTail buildMsgTail()
/*    */   {
/* 80 */     WGMsgTail tail = new WGMsgTail();
/* 81 */     return tail;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\WGXmlMsgBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */