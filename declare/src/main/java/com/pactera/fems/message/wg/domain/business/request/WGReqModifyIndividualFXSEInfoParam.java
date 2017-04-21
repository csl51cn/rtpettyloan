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
/*     */ public class WGReqModifyIndividualFXSEInfoParam
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String REFNO;
/*     */   private String BANK_SELF_NUM;
/*     */   private String PERSON_NAME;
/*     */   private String SALEFX_TX_CODE;
/*     */   private String TXCCY;
/*     */   private String SALEFX_AMT;
/*     */   private String LCY_ACCTNO_CNY;
/*     */   private String SALEFX_SETTLE_CODE;
/*     */   private String LCY_ACCT_NO;
/*     */   private String AGENT_CORP_CODE;
/*     */   private String AGENT_CORP_NAME;
/*     */   private String INDIV_ORG_CODE;
/*     */   private String INDIV_ORG_NAME;
/*     */   private String PAY_ORG_CODE;
/*     */   private String CAPITALNO;
/*     */   private String REMARK;
/*     */   private String MOD_REASON_CODE;
/*     */   private String MODIFY_REMARK;
/*     */   
/*     */   public String getREFNO()
/*     */   {
/*  72 */     return this.REFNO;
/*     */   }
/*     */   
/*     */   public void setREFNO(String rEFNO) {
/*  76 */     this.REFNO = rEFNO;
/*     */   }
/*     */   
/*     */   public String getBANK_SELF_NUM() {
/*  80 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/*  84 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/*  88 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/*  92 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getSALEFX_TX_CODE() {
/*  96 */     return this.SALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_TX_CODE(String sALEFX_TX_CODE) {
/* 100 */     this.SALEFX_TX_CODE = sALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 104 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 108 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_AMT() {
/* 112 */     return this.SALEFX_AMT;
/*     */   }
/*     */   
/*     */   public void setSALEFX_AMT(String sALEFX_AMT) {
/* 116 */     this.SALEFX_AMT = sALEFX_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCTNO_CNY() {
/* 120 */     return this.LCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) {
/* 124 */     this.LCY_ACCTNO_CNY = lCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_SETTLE_CODE() {
/* 128 */     return this.SALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
/* 132 */     this.SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 136 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 140 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 144 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 148 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 152 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 156 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 160 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 164 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 168 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 172 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 176 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 180 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 184 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 188 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 192 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 196 */     this.REMARK = rEMARK;
/*     */   }
/*     */   
/*     */   public String getMOD_REASON_CODE() {
/* 200 */     return this.MOD_REASON_CODE;
/*     */   }
/*     */   
/*     */   public void setMOD_REASON_CODE(String mOD_REASON_CODE) {
/* 204 */     this.MOD_REASON_CODE = mOD_REASON_CODE;
/*     */   }
/*     */   
/*     */   public String getMODIFY_REMARK() {
/* 208 */     return this.MODIFY_REMARK;
/*     */   }
/*     */   
/*     */   public void setMODIFY_REMARK(String mODIFY_REMARK) {
/* 212 */     this.MODIFY_REMARK = mODIFY_REMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqModifyIndividualFXSEInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */