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
/*     */ public class WGReqMakeUpIndividualTradeFXSEInfoRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  17 */   private String BANK_SELF_NUM = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private String IDTYPE_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String IDCODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String ADD_IDCODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String CTYCODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String PERSON_NAME;
/*     */   
/*     */ 
/*     */ 
/*     */   private String SALEFX_TX_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String TXCCY;
/*     */   
/*     */ 
/*     */ 
/*     */   private String SALEFX_AMT;
/*     */   
/*     */ 
/*     */ 
/*     */   private String LCY_ACCTNO_CNY;
/*     */   
/*     */ 
/*     */ 
/*     */   private String SALEFX_SETTLE_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String LCY_ACCT_NO;
/*     */   
/*     */ 
/*     */ 
/*     */   private String AGENT_CORP_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String AGENT_CORP_NAME;
/*     */   
/*     */ 
/*     */ 
/*     */   private String INDIV_ORG_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String INDIV_ORG_NAME;
/*     */   
/*     */ 
/*     */ 
/*     */   private String PAY_ORG_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String CAPITALNO;
/*     */   
/*     */ 
/*     */ 
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   
/*     */ 
/*     */ 
/*     */   private String BIZ_TX_TIME;
/*     */   
/*     */ 
/*     */ 
/*     */   private String REMARK;
/*     */   
/*     */ 
/*     */   private String REIN_REASON_CODE;
/*     */   
/*     */ 
/*     */   private String REIN_REMARK;
/*     */   
/*     */ 
/*     */ 
/*     */   public String getBANK_SELF_NUM()
/*     */   {
/* 109 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 113 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/* 117 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 121 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/* 125 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 129 */     this.IDCODE = iDCODE;
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
/*     */   public String getCTYCODE() {
/* 141 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 145 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 149 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 153 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getSALEFX_TX_CODE() {
/* 157 */     return this.SALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_TX_CODE(String sALEFX_TX_CODE) {
/* 161 */     this.SALEFX_TX_CODE = sALEFX_TX_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 165 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 169 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_AMT() {
/* 173 */     return this.SALEFX_AMT;
/*     */   }
/*     */   
/*     */   public void setSALEFX_AMT(String sALEFX_AMT) {
/* 177 */     this.SALEFX_AMT = sALEFX_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCTNO_CNY() {
/* 181 */     return this.LCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCTNO_CNY(String lCY_ACCTNO_CNY) {
/* 185 */     this.LCY_ACCTNO_CNY = lCY_ACCTNO_CNY;
/*     */   }
/*     */   
/*     */   public String getSALEFX_SETTLE_CODE() {
/* 189 */     return this.SALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public void setSALEFX_SETTLE_CODE(String sALEFX_SETTLE_CODE) {
/* 193 */     this.SALEFX_SETTLE_CODE = sALEFX_SETTLE_CODE;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 197 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 201 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 205 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 209 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 213 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 217 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 221 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 225 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 229 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 233 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 237 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 241 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 245 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 249 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 253 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 257 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 261 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 265 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 269 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 273 */     this.REMARK = rEMARK;
/*     */   }
/*     */   
/*     */   public String getREIN_REASON_CODE() {
/* 277 */     return this.REIN_REASON_CODE;
/*     */   }
/*     */   
/*     */   public void setREIN_REASON_CODE(String rEIN_REASON_CODE) {
/* 281 */     this.REIN_REASON_CODE = rEIN_REASON_CODE;
/*     */   }
/*     */   
/*     */   public String getREIN_REMARK() {
/* 285 */     return this.REIN_REMARK;
/*     */   }
/*     */   
/*     */   public void setREIN_REMARK(String rEIN_REMARK) {
/* 289 */     this.REIN_REMARK = rEIN_REMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqMakeUpIndividualTradeFXSEInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */