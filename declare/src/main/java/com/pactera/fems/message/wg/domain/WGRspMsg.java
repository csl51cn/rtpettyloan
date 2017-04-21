/*    */ package com.pactera.fems.message.wg.domain;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGRspMsg
/*    */   extends SerialBean
/*    */ {
/*    */   private WGRspMsgHead head;
/*    */   private WGMsgBody body;
/*    */   private WGMsgTail tail;
/*    */   
/*    */   public WGRspMsgHead getHead()
/*    */   {
/* 18 */     return this.head;
/*    */   }
/*    */   
/*    */   public void setHead(WGRspMsgHead head) {
/* 22 */     this.head = head;
/*    */   }
/*    */   
/*    */   public WGMsgBody getBody() {
/* 26 */     return this.body;
/*    */   }
/*    */   
/*    */   public void setBody(WGMsgBody body) {
/* 30 */     this.body = body;
/*    */   }
/*    */   
/*    */   public WGMsgTail getTail() {
/* 34 */     return this.tail;
/*    */   }
/*    */   
/*    */   public void setTail(WGMsgTail tail) {
/* 38 */     this.tail = tail;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\WGRspMsg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */