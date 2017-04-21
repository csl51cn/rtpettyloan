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
/*    */ 
/*    */ public class WGReqAddLimitedIndividualFXSEInfo
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqAddLimitedIndividualFXSEInfoParam PARAMETERS;
/*    */   
/*    */   public WGReqAddLimitedIndividualFXSEInfoParam getPARAMETERS()
/*    */   {
/* 22 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqAddLimitedIndividualFXSEInfoParam pARAMETERS) {
/* 26 */     this.PARAMETERS = pARAMETERS;
/*    */   }
/*    */   
/*    */   public static long getSerialversionuid() {
/* 30 */     return 1L;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddLimitedIndividualFXSEInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */