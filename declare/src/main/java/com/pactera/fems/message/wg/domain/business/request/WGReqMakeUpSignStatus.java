/*    */ package com.pactera.fems.message.wg.domain.business.request;
/*    */ 
/*    */ import com.pactera.fems.message.wg.domain.WGDS;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGReqMakeUpSignStatus
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqMakeUpSignStatusParam PARAMETERS;
/*    */   
/*    */   public WGReqMakeUpSignStatusParam getPARAMETERS()
/*    */   {
/* 21 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqMakeUpSignStatusParam pARAMETERS) {
/* 25 */     this.PARAMETERS = pARAMETERS;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqMakeUpSignStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */