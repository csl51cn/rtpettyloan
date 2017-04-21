/*     */ package com.pactera.fems.message.wg.domain.business.request;
/*     */ 
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
/*     */ public class WGReqAddLimitedIndividualFXSEInfoParam
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String BANK_SELF_NUM;
/*     */   private String BIZ_TYPE_CODE;
/*     */   private String IDTYPE_CODE;
/*     */   private String IDCODE;
/*     */   private String CTYCODE;
/*     */   private String ADD_IDCODE;
/*     */   private String PERSON_NAME;
/*     */   private String SALEFX_TX_CODE;
/*     */   private String TXCCY;
/*     */   private String SALEFX_AMT;
/*     */   private String LCY_ACCTNO_CNY;
/*     */   private String SALEFX_SETTLE_CODE;
/*     */   private String LCY_ACCT_NO;
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   private String REMARK;
/*     */   
/*     */   public String getBANK_SELF_NUM()
/*     */   {
/*  93 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/*  97 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getBIZ_TYPE_CODE() {
/* 101 */     return this.BIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
/* 105 */     this.BIZ_TYPE_CODE = bIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/* 109 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 113 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/* 117 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 121 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 125 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 129 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/* 133 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 137 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 141 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 145 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getSALEFX_TX_CODE() {
/* 149 */     return this.SALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_TX_CODE(String sALEFX_TX_CODE) {
/* 153 */     this.SALEFX_TX_CODE = sALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 157 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 161 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_AMT() {
/* 165 */     return this.SALEFX_AMT;
/*     */   }
/*     */   
/*     */   public void setSALEFX_AMT(String sALEFX_AMT) {
/* 169 */     this.SALEFX_AMT = sALEFX_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCTNO_CNY() {
/* 173 */     return this.LCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) {
/* 177 */     this.LCY_ACCTNO_CNY = lCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_SETTLE_CODE() {
/* 181 */     return this.SALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
/* 185 */     this.SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 189 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 193 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 197 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 201 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 205 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 209 */     this.REMARK = rEMARK;
/*     */   }
/*     */   
/*     */   public static long getSerialversionuid() {
/* 213 */     return 1L;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddLimitedIndividualFXSEInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */