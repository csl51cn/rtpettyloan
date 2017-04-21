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
/*    */ public class WGReqQuerySafeExRateParam
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String CURRENCY_CODE;
/*    */   private String YEAR_MONTH;
/*    */   
/*    */   public String getCURRENCY_CODE()
/*    */   {
/* 25 */     return this.CURRENCY_CODE;
/*    */   }
/*    */   
/*    */   public void setCURRENCY_CODE(String cURRENCY_CODE) {
/* 29 */     this.CURRENCY_CODE = cURRENCY_CODE;
/*    */   }
/*    */   
/*    */   public String getYEAR_MONTH() {
/* 33 */     return this.YEAR_MONTH;
/*    */   }
/*    */   
/*    */   public void setYEAR_MONTH(String yEAR_MONTH) {
/* 37 */     this.YEAR_MONTH = yEAR_MONTH;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqQuerySafeExRateParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */