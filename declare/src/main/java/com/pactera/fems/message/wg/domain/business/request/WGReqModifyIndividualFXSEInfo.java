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
/*    */ public class WGReqModifyIndividualFXSEInfo
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqModifyIndividualFXSEInfoParam PARAMETERS;
/*    */   
/*    */   public WGReqModifyIndividualFXSEInfoParam getPARAMETERS()
/*    */   {
/* 19 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqModifyIndividualFXSEInfoParam pARAMETERS) {
/* 23 */     this.PARAMETERS = pARAMETERS;
/*    */   }
/*    */   
/* 26 */   public static long getSerialversionuid() { return 1L; }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqModifyIndividualFXSEInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */