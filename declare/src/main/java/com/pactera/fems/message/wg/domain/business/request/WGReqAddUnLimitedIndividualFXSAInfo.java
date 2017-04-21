/*    */ package com.pactera.fems.message.wg.domain.business.request;
/*    */ 
/*    */ import com.pactera.fems.message.wg.domain.WGDS;
/*    */ import java.util.List;
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
/*    */ public class WGReqAddUnLimitedIndividualFXSAInfo
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqAddUnLimitedIndividualFXSAInfoParam PARAMETERS;
/*    */   private List ROWSET;
/*    */   
/*    */   public WGReqAddUnLimitedIndividualFXSAInfoParam getPARAMETERS()
/*    */   {
/* 24 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqAddUnLimitedIndividualFXSAInfoParam pARAMETERS) {
/* 28 */     this.PARAMETERS = pARAMETERS;
/*    */   }
/*    */   
/*    */   public List getROWSET() {
/* 32 */     return this.ROWSET;
/*    */   }
/*    */   
/*    */   public void setROWSET(List rOWSET) {
/* 36 */     this.ROWSET = rOWSET;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddUnLimitedIndividualFXSAInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */