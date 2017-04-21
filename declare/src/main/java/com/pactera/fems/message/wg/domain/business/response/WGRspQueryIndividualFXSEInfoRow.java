/*     */ package com.pactera.fems.message.wg.domain.business.response;
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
/*     */ public class WGRspQueryIndividualFXSEInfoRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String REFNO;
/*     */   private String BANK_SELF_NUM;
/*     */   private String BIZ_TYPE_CODE;
/*     */   private String IDTYPE_CODE;
/*     */   private String IDCODE;
/*     */   private String ADD_IDCODE;
/*     */   private String CTYCODE;
/*     */   private String PERSON_NAME;
/*     */   private String SALEFX_TX_CODE;
/*     */   private String TXCCY;
/*     */   private String SALEFX_AMT;
/*     */   private String LCY_ACCTNO_CNY;
/*     */   private String SALEFX_AMT_USD;
/*     */   private String SALEFX_SETTLE_CODE;
/*     */   private String LCY_ACCT_NO;
/*     */   private String AGENT_CORP_CODE;
/*     */   private String AGENT_CORP_NAME;
/*     */   private String INDIV_ORG_CODE;
/*     */   private String INDIV_ORG_NAME;
/*     */   private String PAY_ORG_CODE;
/*     */   private String PAY_ORG_NAME;
/*     */   private String CAPITALNO;
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   private String BIZ_TX_TIME;
/*     */   private String BRANCH_CODE;
/*     */   private String BRANCH_NAME;
/*     */   private String OPERCODE;
/*     */   private String ACTIONTYPE;
/*     */   private String REMARK;
/*     */   
/*     */   public String getREFNO()
/*     */   {
/*  59 */     return this.REFNO;
/*     */   }
/*     */   
/*     */   public void setREFNO(String rEFNO) {
/*  63 */     this.REFNO = rEFNO;
/*     */   }
/*     */   
/*     */   public String getBANK_SELF_NUM() {
/*  67 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/*  71 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getBIZ_TYPE_CODE() {
/*  75 */     return this.BIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
/*  79 */     this.BIZ_TYPE_CODE = bIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/*  83 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/*  87 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/*  91 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/*  95 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/*  99 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 103 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 107 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 111 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 115 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 119 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getSALEFX_TX_CODE() {
/* 123 */     return this.SALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_TX_CODE(String sALEFX_TX_CODE) {
/* 127 */     this.SALEFX_TX_CODE = sALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 131 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 135 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_AMT() {
/* 139 */     return this.SALEFX_AMT;
/*     */   }
/*     */   
/*     */   public void setSALEFX_AMT(String sALEFX_AMT) {
/* 143 */     this.SALEFX_AMT = sALEFX_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCTNO_CNY() {
/* 147 */     return this.LCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) {
/* 151 */     this.LCY_ACCTNO_CNY = lCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_AMT_USD() {
/* 155 */     return this.SALEFX_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setSALEFX_AMT_USD(String sALEFX_AMT_USD) {
/* 159 */     this.SALEFX_AMT_USD = sALEFX_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getSALEFX_SETTLE_CODE() {
/* 163 */     return this.SALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
/* 167 */     this.SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 171 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 175 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 179 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 183 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 187 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 191 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 195 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 199 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 203 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 207 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 211 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 215 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_NAME() {
/* 219 */     return this.PAY_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_NAME(String pAY_ORG_NAME) {
/* 223 */     this.PAY_ORG_NAME = pAY_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 227 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 231 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 235 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 239 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 243 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 247 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public String getBRANCH_CODE() {
/* 251 */     return this.BRANCH_CODE;
/*     */   }
/*     */   
/*     */   public void setBRANCH_CODE(String bRANCH_CODE) {
/* 255 */     this.BRANCH_CODE = bRANCH_CODE;
/*     */   }
/*     */   
/*     */   public String getBRANCH_NAME() {
/* 259 */     return this.BRANCH_NAME;
/*     */   }
/*     */   
/*     */   public void setBRANCH_NAME(String bRANCH_NAME) {
/* 263 */     this.BRANCH_NAME = bRANCH_NAME;
/*     */   }
/*     */   
/*     */   public String getOPERCODE() {
/* 267 */     return this.OPERCODE;
/*     */   }
/*     */   
/*     */   public void setOPERCODE(String oPERCODE) {
/* 271 */     this.OPERCODE = oPERCODE;
/*     */   }
/*     */   
/*     */   public String getACTIONTYPE() {
/* 275 */     return this.ACTIONTYPE;
/*     */   }
/*     */   
/*     */   public void setACTIONTYPE(String aCTIONTYPE) {
/* 279 */     this.ACTIONTYPE = aCTIONTYPE;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 283 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 287 */     this.REMARK = rEMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\response\WGRspQueryIndividualFXSEInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */