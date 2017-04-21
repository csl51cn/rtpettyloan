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
/*     */ public class WGReqAddLimitedIndividualFXSAInfoParam
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   private String BANK_SELF_NUM = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  23 */   private String BIZ_TYPE_CODE = null;
/*     */   
/*     */ 
/*     */   private String IDTYPE_CODE;
/*     */   
/*     */ 
/*     */   private String IDCODE;
/*     */   
/*     */ 
/*     */   private String ADD_IDCODE;
/*     */   
/*     */ 
/*     */   private String CTYCODE;
/*     */   
/*     */ 
/*     */   private String PERSON_NAME;
/*     */   
/*     */ 
/*     */   private String PURFX_TYPE_CODE;
/*     */   
/*     */ 
/*     */   private String TXCCY;
/*     */   
/*     */ 
/*     */   private String PURFX_AMT;
/*     */   
/*     */ 
/*     */   private String PURFX_CASH_AMT;
/*     */   
/*     */ 
/*     */   private String FCY_REMIT_AMT;
/*     */   
/*     */ 
/*     */   private String FCY_ACCT_AMT;
/*     */   
/*     */ 
/*     */   private String TCHK_AMT;
/*     */   
/*     */ 
/*     */   private String PURFX_ACCT_CNY;
/*     */   
/*     */ 
/*     */   private String LCY_ACCT_NO;
/*     */   
/*     */ 
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   
/*     */   private String REMARK;
/*     */   
/*     */ 
/*     */   public String getBANK_SELF_NUM()
/*     */   {
/*  75 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/*  79 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getBIZ_TYPE_CODE() {
/*  83 */     return this.BIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
/*  87 */     this.BIZ_TYPE_CODE = bIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/*  91 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/*  95 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/*  99 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 103 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/* 107 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 111 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 115 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 119 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 123 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 127 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getPURFX_TYPE_CODE() {
/* 131 */     return this.PURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setPURFX_TYPE_CODE(String pURFX_TYPE_CODE) {
/* 135 */     this.PURFX_TYPE_CODE = pURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 139 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 143 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT() {
/* 147 */     return this.PURFX_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT(String pURFX_AMT) {
/* 151 */     this.PURFX_AMT = pURFX_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT() {
/* 155 */     return this.PURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
/* 159 */     this.PURFX_CASH_AMT = pURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT() {
/* 163 */     return this.FCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
/* 167 */     this.FCY_REMIT_AMT = fCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT() {
/* 171 */     return this.FCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
/* 175 */     this.FCY_ACCT_AMT = fCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT() {
/* 179 */     return this.TCHK_AMT;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT(String tCHK_AMT) {
/* 183 */     this.TCHK_AMT = tCHK_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_ACCT_CNY() {
/* 187 */     return this.PURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public void setPURFX_ACCT_CNY(String pURFX_ACCT_CNY) {
/* 191 */     this.PURFX_ACCT_CNY = pURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 195 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 199 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 203 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 207 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 211 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 215 */     this.REMARK = rEMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddLimitedIndividualFXSAInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */