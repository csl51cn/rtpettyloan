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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WGReqMakeUpSignStatusParam
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String IDTYPE_CODE;
/*    */   private String IDCODE;
/*    */   private String CTYCODE;
/*    */   private String PERSON_NAME;
/*    */   private String BIZ_TX_CHNL_CODE;
/*    */   
/*    */   public String getIDTYPE_CODE()
/*    */   {
/* 49 */     return this.IDTYPE_CODE;
/*    */   }
/*    */   
/*    */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 53 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*    */   }
/*    */   
/*    */   public String getIDCODE() {
/* 57 */     return this.IDCODE;
/*    */   }
/*    */   
/*    */   public void setIDCODE(String iDCODE) {
/* 61 */     this.IDCODE = iDCODE;
/*    */   }
/*    */   
/*    */   public String getCTYCODE() {
/* 65 */     return this.CTYCODE;
/*    */   }
/*    */   
/*    */   public void setCTYCODE(String cTYCODE) {
/* 69 */     this.CTYCODE = cTYCODE;
/*    */   }
/*    */   
/*    */   public String getPERSON_NAME() {
/* 73 */     return this.PERSON_NAME;
/*    */   }
/*    */   
/*    */   public void setPERSON_NAME(String pERSON_NAME) {
/* 77 */     this.PERSON_NAME = pERSON_NAME;
/*    */   }
/*    */   
/*    */   public String getBIZ_TX_CHNL_CODE() {
/* 81 */     return this.BIZ_TX_CHNL_CODE;
/*    */   }
/*    */   
/*    */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 85 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqMakeUpSignStatusParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */