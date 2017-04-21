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
/*     */ public class WGReqAddUnLimitedIndividualFXSEInfoRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String BANK_SELF_NUM;
/*     */   private String IDTYPE_CODE;
/*     */   private String IDCODE;
/*     */   private String ADD_IDCODE;
/*     */   private String CTYCODE;
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
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   private String BIZ_TX_TIME;
/*     */   private String REMARK;
/*     */   
/*     */   public String getBANK_SELF_NUM()
/*     */   {
/*  43 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*  46 */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) { this.BANK_SELF_NUM = bANK_SELF_NUM; }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/*  49 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*  52 */   public void setIDTYPE_CODE(String iDTYPE_CODE) { this.IDTYPE_CODE = iDTYPE_CODE; }
/*     */   
/*     */   public String getIDCODE() {
/*  55 */     return this.IDCODE;
/*     */   }
/*     */   
/*  58 */   public void setIDCODE(String iDCODE) { this.IDCODE = iDCODE; }
/*     */   
/*     */   public String getADD_IDCODE() {
/*  61 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*  64 */   public void setADD_IDCODE(String aDD_IDCODE) { this.ADD_IDCODE = aDD_IDCODE; }
/*     */   
/*     */   public String getCTYCODE() {
/*  67 */     return this.CTYCODE;
/*     */   }
/*     */   
/*  70 */   public void setCTYCODE(String cTYCODE) { this.CTYCODE = cTYCODE; }
/*     */   
/*     */   public String getPERSON_NAME() {
/*  73 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*  76 */   public void setPERSON_NAME(String pERSON_NAME) { this.PERSON_NAME = pERSON_NAME; }
/*     */   
/*     */   public String getSALEFX_TX_CODE() {
/*  79 */     return this.SALEFX_TX_CODE;
/*     */   }
/*     */   
/*  82 */   public void setSALEFX_TX_CODE(String sALEFX_TX_CODE) { this.SALEFX_TX_CODE = sALEFX_TX_CODE; }
/*     */   
/*     */   public String getTXCCY() {
/*  85 */     return this.TXCCY;
/*     */   }
/*     */   
/*  88 */   public void setTXCCY(String tXCCY) { this.TXCCY = tXCCY; }
/*     */   
/*     */   public String getSALEFX_AMT() {
/*  91 */     return this.SALEFX_AMT;
/*     */   }
/*     */   
/*  94 */   public void setSALEFX_AMT(String sALEFX_AMT) { this.SALEFX_AMT = sALEFX_AMT; }
/*     */   
/*     */   public String getLCY_ACCTNO_CNY() {
/*  97 */     return this.LCY_ACCTNO_CNY;
/*     */   }
/*     */   
/* 100 */   public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) { this.LCY_ACCTNO_CNY = lCY_ACCTNO_CNY; }
/*     */   
/*     */   public String getSALEFX_SETTLE_CODE() {
/* 103 */     return this.SALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/* 106 */   public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) { this.SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE; }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 109 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/* 112 */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) { this.LCY_ACCT_NO = lCY_ACCT_NO; }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 115 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/* 118 */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) { this.AGENT_CORP_CODE = aGENT_CORP_CODE; }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 121 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/* 124 */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) { this.AGENT_CORP_NAME = aGENT_CORP_NAME; }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 127 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/* 130 */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) { this.INDIV_ORG_CODE = iNDIV_ORG_CODE; }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 133 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/* 136 */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) { this.INDIV_ORG_NAME = iNDIV_ORG_NAME; }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 139 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/* 142 */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) { this.PAY_ORG_CODE = pAY_ORG_CODE; }
/*     */   
/*     */   public String getCAPITALNO() {
/* 145 */     return this.CAPITALNO;
/*     */   }
/*     */   
/* 148 */   public void setCAPITALNO(String cAPITALNO) { this.CAPITALNO = cAPITALNO; }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 151 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/* 154 */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) { this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE; }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 157 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/* 160 */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) { this.BIZ_TX_TIME = bIZ_TX_TIME; }
/*     */   
/*     */   public String getREMARK() {
/* 163 */     return this.REMARK;
/*     */   }
/*     */   
/* 166 */   public void setREMARK(String rEMARK) { this.REMARK = rEMARK; }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddUnLimitedIndividualFXSEInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */