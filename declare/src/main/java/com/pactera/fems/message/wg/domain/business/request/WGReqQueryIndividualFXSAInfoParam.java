/*    */ package com.pactera.fems.message.wg.domain.business.request;
/*    */ 
/*    */ import org.global.framework.xmlbeans.bean.SerialBean;
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
/*    */ public class WGReqQueryIndividualFXSAInfoParam
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String REFNO;
/*    */   private String IDTYPE_CODE;
/*    */   private String IDCODE;
/*    */   private String CTYCODE;
/*    */   private String BIZ_TX_TIME;
/*    */   
/*    */   public String getREFNO()
/*    */   {
/* 42 */     return this.REFNO;
/*    */   }
/*    */   
/*    */   public void setREFNO(String rEFNO) {
/* 46 */     this.REFNO = rEFNO;
/*    */   }
/*    */   
/*    */   public String getIDTYPE_CODE() {
/* 50 */     return this.IDTYPE_CODE;
/*    */   }
/*    */   
/*    */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 54 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*    */   }
/*    */   
/*    */   public String getIDCODE() {
/* 58 */     return this.IDCODE;
/*    */   }
/*    */   
/*    */   public void setIDCODE(String iDCODE) {
/* 62 */     this.IDCODE = iDCODE;
/*    */   }
/*    */   
/*    */   public String getCTYCODE() {
/* 66 */     return this.CTYCODE;
/*    */   }
/*    */   
/*    */   public void setCTYCODE(String cTYCODE) {
/* 70 */     this.CTYCODE = cTYCODE;
/*    */   }
/*    */   
/*    */   public String getBIZ_TX_TIME() {
/* 74 */     return this.BIZ_TX_TIME;
/*    */   }
/*    */   
/*    */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 78 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqQueryIndividualFXSAInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */