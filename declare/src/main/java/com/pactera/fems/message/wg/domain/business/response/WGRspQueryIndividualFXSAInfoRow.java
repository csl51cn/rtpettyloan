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
/*     */ public class WGRspQueryIndividualFXSAInfoRow
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
/*     */   private String PURFX_TYPE_CODE;
/*     */   private String PAY_ORG_NAME;
/*     */   private String PAY_ORG_CODE;
/*     */   private String AGENT_CORP_NAME;
/*     */   private String AGENT_CORP_CODE;
/*     */   private String INDIV_ORG_CODE;
/*     */   private String INDIV_ORG_NAME;
/*     */   private String CAPITALNO;
/*     */   private String TXCCY;
/*     */   private String PURFX_AMT;
/*     */   private String PURFX_AMT_USD;
/*     */   private String PURFX_ACCT_CNY;
/*     */   private String PURFX_CASH_AMT;
/*     */   private String PURFX_CASH_AMT_USD;
/*     */   private String FCY_REMIT_AMT;
/*     */   private String FCY_REMIT_AMT_USD;
/*     */   private String LCY_ACCT_NO;
/*     */   private String FCY_ACCT_AMT;
/*     */   private String FCY_ACCT_AMT_USD;
/*     */   private String TCHK_AMT;
/*     */   private String TCHK_AMT_USD;
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
/* 199 */     return this.REFNO;
/*     */   }
/*     */   
/*     */   public void setREFNO(String rEFNO) {
/* 203 */     this.REFNO = rEFNO;
/*     */   }
/*     */   
/*     */   public String getBANK_SELF_NUM() {
/* 207 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 211 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getBIZ_TYPE_CODE() {
/* 215 */     return this.BIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
/* 219 */     this.BIZ_TYPE_CODE = bIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/* 223 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 227 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/* 231 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 235 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/* 239 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 243 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 247 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 251 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 255 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 259 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getPURFX_TYPE_CODE() {
/* 263 */     return this.PURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setPURFX_TYPE_CODE(String pURFX_TYPE_CODE) {
/* 267 */     this.PURFX_TYPE_CODE = pURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_NAME() {
/* 271 */     return this.PAY_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_NAME(String pAY_ORG_NAME) {
/* 275 */     this.PAY_ORG_NAME = pAY_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 279 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 283 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 287 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 291 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 295 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 299 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 303 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 307 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 311 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 315 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 319 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 323 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 327 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 331 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT() {
/* 335 */     return this.PURFX_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT(String pURFX_AMT) {
/* 339 */     this.PURFX_AMT = pURFX_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT_USD() {
/* 343 */     return this.PURFX_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT_USD(String pURFX_AMT_USD) {
/* 347 */     this.PURFX_AMT_USD = pURFX_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getPURFX_ACCT_CNY() {
/* 351 */     return this.PURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public void setPURFX_ACCT_CNY(String pURFX_ACCT_CNY) {
/* 355 */     this.PURFX_ACCT_CNY = pURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT() {
/* 359 */     return this.PURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
/* 363 */     this.PURFX_CASH_AMT = pURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT_USD() {
/* 367 */     return this.PURFX_CASH_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT_USD(String pURFX_CASH_AMT_USD) {
/* 371 */     this.PURFX_CASH_AMT_USD = pURFX_CASH_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT() {
/* 375 */     return this.FCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
/* 379 */     this.FCY_REMIT_AMT = fCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT_USD() {
/* 383 */     return this.FCY_REMIT_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT_USD(String fCY_REMIT_AMT_USD) {
/* 387 */     this.FCY_REMIT_AMT_USD = fCY_REMIT_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 391 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 395 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT() {
/* 399 */     return this.FCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
/* 403 */     this.FCY_ACCT_AMT = fCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT_USD() {
/* 407 */     return this.FCY_ACCT_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT_USD(String fCY_ACCT_AMT_USD) {
/* 411 */     this.FCY_ACCT_AMT_USD = fCY_ACCT_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT() {
/* 415 */     return this.TCHK_AMT;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT(String tCHK_AMT) {
/* 419 */     this.TCHK_AMT = tCHK_AMT;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT_USD() {
/* 423 */     return this.TCHK_AMT_USD;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT_USD(String tCHK_AMT_USD) {
/* 427 */     this.TCHK_AMT_USD = tCHK_AMT_USD;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 431 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 435 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 439 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 443 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public String getBRANCH_CODE() {
/* 447 */     return this.BRANCH_CODE;
/*     */   }
/*     */   
/*     */   public void setBRANCH_CODE(String bRANCH_CODE) {
/* 451 */     this.BRANCH_CODE = bRANCH_CODE;
/*     */   }
/*     */   
/*     */   public String getBRANCH_NAME() {
/* 455 */     return this.BRANCH_NAME;
/*     */   }
/*     */   
/*     */   public void setBRANCH_NAME(String bRANCH_NAME) {
/* 459 */     this.BRANCH_NAME = bRANCH_NAME;
/*     */   }
/*     */   
/*     */   public String getOPERCODE() {
/* 463 */     return this.OPERCODE;
/*     */   }
/*     */   
/*     */   public void setOPERCODE(String oPERCODE) {
/* 467 */     this.OPERCODE = oPERCODE;
/*     */   }
/*     */   
/*     */   public String getACTIONTYPE() {
/* 471 */     return this.ACTIONTYPE;
/*     */   }
/*     */   
/*     */   public void setACTIONTYPE(String aCTIONTYPE) {
/* 475 */     this.ACTIONTYPE = aCTIONTYPE;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 479 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 483 */     this.REMARK = rEMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\response\WGRspQueryIndividualFXSAInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */