/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGMsgTail
/*    */   extends SerialBean
/*    */ {
/*    */   private String SIGNATURE;
/*    */   private String CHECKSUM;
/*    */   
/*    */   public String getSIGNATURE()
/*    */   {
/* 18 */     return this.SIGNATURE;
/*    */   }
/*    */   
/*    */   public void setSIGNATURE(String sIGNATURE) {
/* 22 */     this.SIGNATURE = sIGNATURE;
/*    */   }
/*    */   
/*    */   public String getCHECKSUM() {
/* 26 */     return this.CHECKSUM;
/*    */   }
/*    */   
/*    */   public void setCHECKSUM(String cHECKSUM) {
/* 30 */     this.CHECKSUM = cHECKSUM;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGMsgTail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */