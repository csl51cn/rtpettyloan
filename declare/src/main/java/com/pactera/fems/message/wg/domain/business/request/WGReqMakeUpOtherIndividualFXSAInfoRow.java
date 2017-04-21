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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WGReqMakeUpOtherIndividualFXSAInfoRow
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String BIZ_TYPE_CODE;
/*     */   private String BANK_SELF_NUM;
/*     */   private String IDTYPE_CODE;
/*     */   private String IDCODE;
/*     */   private String CTYCODE;
/*     */   private String ADD_IDCODE;
/*     */   private String PERSON_NAME;
/*     */   private String PURFX_TYPE_CODE;
/*     */   private String TXCCY;
/*     */   private String PURFX_AMT;
/*     */   private String PURFX_CASH_AMT;
/*     */   private String FCY_REMIT_AMT;
/*     */   private String FCY_ACCT_AMT;
/*     */   private String TCHK_AMT;
/*     */   private String PURFX_ACCT_CNY;
/*     */   private String LCY_ACCT_NO;
/*     */   private String BIZ_TX_CHNL_CODE;
/*     */   private String BIZ_TX_TIME;
/*     */   private String REMARK;
/*     */   private String REIN_REASON_CODE;
/*     */   private String REIN_REMARK;
/*     */   
/*     */   public String getBIZ_TYPE_CODE()
/*     */   {
/* 129 */     return this.BIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TYPE_CODE(String bIZ_TYPE_CODE) {
/* 133 */     this.BIZ_TYPE_CODE = bIZ_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getBANK_SELF_NUM() {
/* 137 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 141 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getIDTYPE_CODE() {
/* 145 */     return this.IDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setIDTYPE_CODE(String iDTYPE_CODE) {
/* 149 */     this.IDTYPE_CODE = iDTYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getIDCODE() {
/* 153 */     return this.IDCODE;
/*     */   }
/*     */   
/*     */   public void setIDCODE(String iDCODE) {
/* 157 */     this.IDCODE = iDCODE;
/*     */   }
/*     */   
/*     */   public String getCTYCODE() {
/* 161 */     return this.CTYCODE;
/*     */   }
/*     */   
/*     */   public void setCTYCODE(String cTYCODE) {
/* 165 */     this.CTYCODE = cTYCODE;
/*     */   }
/*     */   
/*     */   public String getADD_IDCODE() {
/* 169 */     return this.ADD_IDCODE;
/*     */   }
/*     */   
/*     */   public void setADD_IDCODE(String aDD_IDCODE) {
/* 173 */     this.ADD_IDCODE = aDD_IDCODE;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 177 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 181 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getPURFX_TYPE_CODE() {
/* 185 */     return this.PURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setPURFX_TYPE_CODE(String pURFX_TYPE_CODE) {
/* 189 */     this.PURFX_TYPE_CODE = pURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 193 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 197 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT() {
/* 201 */     return this.PURFX_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT(String pURFX_AMT) {
/* 205 */     this.PURFX_AMT = pURFX_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT() {
/* 209 */     return this.PURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
/* 213 */     this.PURFX_CASH_AMT = pURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT() {
/* 217 */     return this.FCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
/* 221 */     this.FCY_REMIT_AMT = fCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT() {
/* 225 */     return this.FCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
/* 229 */     this.FCY_ACCT_AMT = fCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT() {
/* 233 */     return this.TCHK_AMT;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT(String tCHK_AMT) {
/* 237 */     this.TCHK_AMT = tCHK_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_ACCT_CNY() {
/* 241 */     return this.PURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public void setPURFX_ACCT_CNY(String pURFX_ACCT_CNY) {
/* 245 */     this.PURFX_ACCT_CNY = pURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 249 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 253 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_CHNL_CODE() {
/* 257 */     return this.BIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_CHNL_CODE(String bIZ_TX_CHNL_CODE) {
/* 261 */     this.BIZ_TX_CHNL_CODE = bIZ_TX_CHNL_CODE;
/*     */   }
/*     */   
/*     */   public String getBIZ_TX_TIME() {
/* 265 */     return this.BIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public void setBIZ_TX_TIME(String bIZ_TX_TIME) {
/* 269 */     this.BIZ_TX_TIME = bIZ_TX_TIME;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 273 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 277 */     this.REMARK = rEMARK;
/*     */   }
/*     */   
/*     */   public String getREIN_REASON_CODE() {
/* 281 */     return this.REIN_REASON_CODE;
/*     */   }
/*     */   
/*     */   public void setREIN_REASON_CODE(String rEIN_REASON_CODE) {
/* 285 */     this.REIN_REASON_CODE = rEIN_REASON_CODE;
/*     */   }
/*     */   
/*     */   public String getREIN_REMARK() {
/* 289 */     return this.REIN_REMARK;
/*     */   }
/*     */   
/*     */   public void setREIN_REMARK(String rEIN_REMARK) {
/* 293 */     this.REIN_REMARK = rEIN_REMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqMakeUpOtherIndividualFXSAInfoRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */