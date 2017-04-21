/*    */ package com.pactera.fems.message.wg.domain.business.response;
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
/*    */ public class WGRspQuerySafeExRateRow
/*    */   extends SerialBean
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String YEAR_MONTH;
/*    */   private String CURRENCY_CODE;
/*    */   private String EXCHANGE;
/*    */   
/*    */   public String getYEAR_MONTH()
/*    */   {
/* 34 */     return this.YEAR_MONTH;
/*    */   }
/*    */   
/*    */   public void setYEAR_MONTH(String yEAR_MONTH) {
/* 38 */     this.YEAR_MONTH = yEAR_MONTH;
/*    */   }
/*    */   
/*    */   public String getCURRENCY_CODE() {
/* 42 */     return this.CURRENCY_CODE;
/*    */   }
/*    */   
/*    */   public void setCURRENCY_CODE(String cURRENCY_CODE) {
/* 46 */     this.CURRENCY_CODE = cURRENCY_CODE;
/*    */   }
/*    */   
/*    */   public String getEXCHANGE() {
/* 50 */     return this.EXCHANGE;
/*    */   }
/*    */   
/*    */   public void setEXCHANGE(String eXCHANGE) {
/* 54 */     this.EXCHANGE = eXCHANGE;
/*    */   }
/*    */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\response\WGRspQuerySafeExRateRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */