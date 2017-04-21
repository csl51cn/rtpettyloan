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
/*    */ public class WGReqDeleteIndividualFXSAInfoParam
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String REFNO;
/*    */   private String BANK_SELF_NUM;
/*    */   private String CANCEL_REASON;
/*    */   private String CANCEL_REMARK;
/*    */   
/*    */   public String getREFNO()
/*    */   {
/* 37 */     return this.REFNO;
/*    */   }
/*    */   
/*    */   public void setREFNO(String rEFNO) {
/* 41 */     this.REFNO = rEFNO;
/*    */   }
/*    */   
/*    */   public String getBANK_SELF_NUM() {
/* 45 */     return this.BANK_SELF_NUM;
/*    */   }
/*    */   
/*    */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 49 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*    */   }
/*    */   
/*    */   public String getCANCEL_REASON() {
/* 53 */     return this.CANCEL_REASON;
/*    */   }
/*    */   
/*    */   public void setCANCEL_REASON(String cANCEL_REASON) {
/* 57 */     this.CANCEL_REASON = cANCEL_REASON;
/*    */   }
/*    */   
/*    */   public String getCANCEL_REMARK() {
/* 61 */     return this.CANCEL_REMARK;
/*    */   }
/*    */   
/*    */   public void setCANCEL_REMARK(String cANCEL_REMARK) {
/* 65 */     this.CANCEL_REMARK = cANCEL_REMARK;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqDeleteIndividualFXSAInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */