/*     */ package com.pactera.fems.message.wg.domain.business.response;
/*     */ 
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.global.framework.xmlbeans.bean.SerialBean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WGRspQueryIndividualFXSEQuotaRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String ANN_LCYAMT_USD;
/*     */   private String ANN_REM_LCYAMT_USD;
/*     */   private String TODAY_CASH_USD;
/*     */   private String CUSTNAME;
/*     */   private String CUSTTYPE_CODE;
/*     */   private String TYPE_STATUS;
/*     */   private String PUB_DATE;
/*     */   private String END_DATE;
/*     */   private String PUB_REASON;
/*     */   private String PUB_CODE;
/*     */   private String SIGN_STATUS;
/*     */   
/*     */   public String getANN_LCYAMT_USD()
/*     */   {
/*  59 */     return this.ANN_LCYAMT_USD;
/*     */   }
/*     */   
/*     */   public void setANN_LCYAMT_USD(String aNN_LCYAMT_USD) {
/*  63 */     this.ANN_LCYAMT_USD = aNN_LCYAMT_USD;
/*     */   }
/*     */   
/*     */   public String getANN_REM_LCYAMT_USD() {
/*  67 */     return this.ANN_REM_LCYAMT_USD;
/*     */   }
/*     */   
/*     */   public void setANN_REM_LCYAMT_USD(String aNN_REM_LCYAMT_USD) {
/*  71 */     if ((StringUtils.isNotBlank(aNN_REM_LCYAMT_USD)) && 
/*  72 */       (Double.valueOf(aNN_REM_LCYAMT_USD.replaceAll(",", "")).doubleValue() < 0.0D)) {
/*  73 */       aNN_REM_LCYAMT_USD = "0.00";
/*     */     }
/*     */     
/*  76 */     this.ANN_REM_LCYAMT_USD = aNN_REM_LCYAMT_USD;
/*     */   }
/*     */   
/*     */   public String getTODAY_CASH_USD() {
/*  80 */     return this.TODAY_CASH_USD;
/*     */   }
/*     */   
/*     */   public void setTODAY_CASH_USD(String tODAY_CASH_USD) {
/*  84 */     this.TODAY_CASH_USD = tODAY_CASH_USD;
/*     */   }
/*     */   
/*     */   public String getCUSTNAME() {
/*  88 */     return this.CUSTNAME;
/*     */   }
/*     */   
/*     */   public void setCUSTNAME(String cUSTNAME) {
/*  92 */     this.CUSTNAME = cUSTNAME;
/*     */   }
/*     */   
/*     */   public String getCUSTTYPE_CODE() {
/*  96 */     return this.CUSTTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setCUSTTYPE_CODE(String cUSTTYPE_CODE) {
/* 100 */     this.CUSTTYPE_CODE = cUSTTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getTYPE_STATUS() {
/* 104 */     return this.TYPE_STATUS;
/*     */   }
/*     */   
/*     */   public void setTYPE_STATUS(String tYPE_STATUS) {
/* 108 */     this.TYPE_STATUS = tYPE_STATUS;
/*     */   }
/*     */   
/*     */   public String getPUB_DATE() {
/* 112 */     return this.PUB_DATE;
/*     */   }
/*     */   
/*     */   public void setPUB_DATE(String pUB_DATE) {
/* 116 */     this.PUB_DATE = pUB_DATE;
/*     */   }
/*     */   
/*     */   public String getEND_DATE() {
/* 120 */     return this.END_DATE;
/*     */   }
/*     */   
/*     */   public void setEND_DATE(String eND_DATE) {
/* 124 */     this.END_DATE = eND_DATE;
/*     */   }
/*     */   
/*     */   public String getPUB_REASON() {
/* 128 */     return this.PUB_REASON;
/*     */   }
/*     */   
/*     */   public void setPUB_REASON(String pUB_REASON) {
/* 132 */     this.PUB_REASON = pUB_REASON;
/*     */   }
/*     */   
/*     */   public String getPUB_CODE() {
/* 136 */     return this.PUB_CODE;
/*     */   }
/*     */   
/*     */   public void setPUB_CODE(String pUB_CODE) {
/* 140 */     this.PUB_CODE = pUB_CODE;
/*     */   }
/*     */   
/*     */   public String getSIGN_STATUS() {
/* 144 */     return this.SIGN_STATUS;
/*     */   }
/*     */   
/*     */   public void setSIGN_STATUS(String sIGN_STATUS) {
/* 148 */     this.SIGN_STATUS = sIGN_STATUS;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\response\WGRspQueryIndividualFXSEQuotaRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */