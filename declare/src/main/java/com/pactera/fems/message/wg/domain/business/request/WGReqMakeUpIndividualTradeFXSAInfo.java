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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGReqMakeUpIndividualTradeFXSAInfo
/*    */   extends WGDS
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private WGReqMakeUpIndividualTradeFXSAInfoParam PARAMETERS;
/*    */   private List ROWSET;
/*    */   
/*    */   public WGReqMakeUpIndividualTradeFXSAInfoParam getPARAMETERS()
/*    */   {
/* 28 */     return this.PARAMETERS;
/*    */   }
/*    */   
/*    */   public void setPARAMETERS(WGReqMakeUpIndividualTradeFXSAInfoParam pARAMETERS) {
/* 32 */     this.PARAMETERS = pARAMETERS;
/*    */   }
/*    */   
/*    */   public List getROWSET() {
/* 36 */     return this.ROWSET;
/*    */   }
/*    */   
/*    */   public void setROWSET(List rOWSET) {
/* 40 */     this.ROWSET = rOWSET;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqMakeUpIndividualTradeFXSAInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */