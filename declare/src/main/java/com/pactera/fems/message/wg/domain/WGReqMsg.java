/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGReqMsg
/*    */   extends SerialBean
/*    */ {
/*    */   private WGReqMsgHead head;
/*    */   private WGMsgBody body;
/*    */   private WGMsgTail tail;
/*    */   
/*    */   public WGReqMsgHead getHead()
/*    */   {
/* 19 */     return this.head;
/*    */   }
/*    */   
/*    */   public void setHead(WGReqMsgHead head) {
/* 23 */     this.head = head;
/*    */   }
/*    */   
/*    */   public WGMsgBody getBody() {
/* 27 */     return this.body;
/*    */   }
/*    */   
/*    */   public void setBody(WGMsgBody body) {
/* 31 */     this.body = body;
/*    */   }
/*    */   
/*    */   public WGMsgTail getTail() {
/* 35 */     return this.tail;
/*    */   }
/*    */   
/*    */   public void setTail(WGMsgTail tail) {
/* 39 */     this.tail = tail;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGReqMsg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */