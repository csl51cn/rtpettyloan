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
/*    */ public class WGReqQueryIndividualFXSEInfoParam
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
/* 39 */     return this.REFNO;
/*    */   }
/*    */   
/* 42 */   public void setREFNO(String rEFNO) { this.REFNO = rEFNO; }
/*    */   
/*    */   public String getIDTYPE_CODE() {
/* 45 */     return this.IDTYPE_CODE;
/*    */   }
/*    */   
/* 48 */   public void setIDTYPE_CODE(String iDTYPE_CODE) { this.IDTYPE_CODE = iDTYPE_CODE; }
/*    */   
/*    */   public String getIDCODE() {
/* 51 */     return this.IDCODE;
/*    */   }
/*    */   
/* 54 */   public void setIDCODE(String iDCODE) { this.IDCODE = iDCODE; }
/*    */   
/*    */   public String getCTYCODE() {
/* 57 */     return this.CTYCODE;
/*    */   }
/*    */   
/* 60 */   public void setCTYCODE(String cTYCODE) { this.CTYCODE = cTYCODE; }
/*    */   
/*    */   public String getBIZ_TX_TIME() {
/* 63 */     return this.BIZ_TX_TIME;
/*    */   }
/*    */   
/* 66 */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) { this.BIZ_TX_TIME = bIZ_TX_TIME; }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqQueryIndividualFXSEInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */