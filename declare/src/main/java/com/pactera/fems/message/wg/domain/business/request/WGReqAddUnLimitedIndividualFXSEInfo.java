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
/*    */ public class WGReqAddUnLimitedIndividualFXSEInfo
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqAddUnLimitedIndividualFXSEInfoParam PARAMETERS;
/*    */   private List ROWSET;
/*    */   
/*    */   public WGReqAddUnLimitedIndividualFXSEInfoParam getPARAMETERS()
/*    */   {
/* 24 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqAddUnLimitedIndividualFXSEInfoParam pARAMETERS) {
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


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddUnLimitedIndividualFXSEInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */