/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGMsgBody
/*    */   extends SerialBean
/*    */ {
/*    */   private WGDataStores dataStores;
/*    */   
/*    */   public WGDataStores getDataStores()
/*    */   {
/* 17 */     return this.dataStores;
/*    */   }
/*    */   
/*    */   public void setDataStores(WGDataStores dataStores) {
/* 21 */     this.dataStores = dataStores;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGMsgBody.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */