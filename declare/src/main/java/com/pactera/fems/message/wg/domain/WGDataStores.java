/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGDataStores
/*    */   extends SerialBean
/*    */ {
/*    */   private WGDS ds;
/*    */   
/*    */   public WGDataStores() {}
/*    */   
/*    */   public WGDataStores(WGDS ds)
/*    */   {
/* 18 */     this.ds = ds;
/*    */   }
/*    */   
/*    */   public WGDS getDs() {
/* 22 */     return this.ds;
/*    */   }
/*    */   
/*    */   public void setDs(WGDS ds) {
/* 26 */     this.ds = ds;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGDataStores.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */