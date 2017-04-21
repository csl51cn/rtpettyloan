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
/*    */ public class WGReqQueryIndividualFXSAQuotaParam
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String IDTYPE_CODE;
/*    */   private String CTYCODE;
/*    */   private String IDCODE;
/*    */   
/*    */   public String getIDTYPE_CODE()
/*    */   {
/* 30 */     return this.IDTYPE_CODE;
/*    */   }
/*    */   
/* 33 */   public void setIDTYPE_CODE(String iDTYPE_CODE) { this.IDTYPE_CODE = iDTYPE_CODE; }
/*    */   
/*    */   public String getCTYCODE() {
/* 36 */     return this.CTYCODE;
/*    */   }
/*    */   
/* 39 */   public void setCTYCODE(String cTYCODE) { this.CTYCODE = cTYCODE; }
/*    */   
/*    */   public String getIDCODE() {
/* 42 */     return this.IDCODE;
/*    */   }
/*    */   
/* 45 */   public void setIDCODE(String iDCODE) { this.IDCODE = iDCODE; }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqQueryIndividualFXSAQuotaParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */