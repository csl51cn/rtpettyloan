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
/*     */ public class WGReqAddUnLimitedIndividualFXSAInfoRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String BANK_SELF_NUM;
/*     */   private String IDTYPE_CODE;
/*     */   private String IDCODE;
/*     */   private String ADD_IDCODE;
/*     */   private String CTYCODE;
/*     */   private String PERSON_NAME;
/*     */   private String PURFX_TYPE_CODE;
/*     */   private String PAY_ORG_CODE;
/*     */   private String AGENT_CORP_CODE;
/*     */   private String AGENT_CORP_NAME;
/*     */   private String INDIV_ORG_CODE;
/*     */   private String INDIV_ORG_NAME;
/*     */   private String CAPITALNO;
/*     */   private String TXCCY;
/*     */   private String PURFX_AMT;
/*     */   private String PURFX_ACCT_CNY;
/*     */   private String PURFX_CASH_AMT;
/*     */   private String FCY_REMIT_AMT;
/*     */   private String LCY_ACCT_NO;
/*     */   private String FCY_ACCT_AMT;
/*     */   private String TCHK_AMT;
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   private String BIZ_TX_TIME;
/*     */   private String REMARK;
/*     */   
/*     */   public String getBANK_SELF_NUM()
/*     */   {
/* 102 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 106 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/* 110 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 114 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/* 118 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 122 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/* 126 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 130 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 134 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 138 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 142 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 146 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getPURFX_TYPE_CODE() {
/* 150 */     return this.PURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setPURFX_TYPE_CODE(String pURFX_TYPE_CODE) {
/* 154 */     this.PURFX_TYPE_CODE = pURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 158 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 162 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 166 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 170 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 174 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 178 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 182 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 186 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 190 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 194 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 198 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 202 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 206 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 210 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT() {
/* 214 */     return this.PURFX_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT(String pURFX_AMT) {
/* 218 */     this.PURFX_AMT = pURFX_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_ACCT_CNY() {
/* 222 */     return this.PURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public void setPURFX_ACCT_CNY(String pURFX_ACCT_CNY) {
/* 226 */     this.PURFX_ACCT_CNY = pURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT() {
/* 230 */     return this.PURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
/* 234 */     this.PURFX_CASH_AMT = pURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT() {
/* 238 */     return this.FCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
/* 242 */     this.FCY_REMIT_AMT = fCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 246 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 250 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT() {
/* 254 */     return this.FCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
/* 258 */     this.FCY_ACCT_AMT = fCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT() {
/* 262 */     return this.TCHK_AMT;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT(String tCHK_AMT) {
/* 266 */     this.TCHK_AMT = tCHK_AMT;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 270 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 274 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 278 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 282 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 286 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 290 */     this.REMARK = rEMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqAddUnLimitedIndividualFXSAInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */