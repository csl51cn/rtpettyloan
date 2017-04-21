/*    */ package com.pactera.fems.message.wg.support;
/*    */ 
/*    */ import com.pactera.fems.message.util.WGIntfCodeCfgUtil;
/*    */ import com.pactera.fems.message.wg.domain.WGRspMsg;
/*    */ import java.util.Map;
/*    */ import org.global.framework.xmlbeans.bean.DataCheckException;
/*    */ import org.global.framework.xmlbeans.handler.Xml2BeanHandler;
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
/*    */ public class WGXmlMsgParser
/*    */ {
/*    */   public static Object parseXml(String serviceMethod, String xml)
/*    */     throws DataCheckException
/*    */   {
/* 30 */     Map map = WGIntfCodeCfgUtil.getCfgCache(serviceMethod);
/* 31 */     WGRspMsg rspMsg = (WGRspMsg)new Xml2BeanHandler().toBean(xml, (String)map.get("rspXmlMapping"), "GBK");
/*    */     
/* 33 */     return rspMsg;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\WGXmlMsgParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */