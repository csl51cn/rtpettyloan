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
/*     */ public class WGReqModifyIndividualFXSAInfoParam
/*     */   extends SerialBean
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String REFNO;
/*     */   private String BANK_SELF_NUM;
/*     */   private String PERSON_NAME;
/*     */   private String PURFX_TYPE_CODE;
/*     */   private String PAY_ORG_CODE;
/*     */   private String AGENT_CORP_NAME;
/*     */   private String AGENT_CORP_CODE;
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
/*     */   private String REMARK;
/*     */   private String MOD_REASON_CODE;
/*     */   private String MODIFY_REMARK;
/*     */   
/*     */   public String getREFNO()
/*     */   {
/* 122 */     return this.REFNO;
/*     */   }
/*     */   
/*     */   public void setREFNO(String rEFNO) {
/* 126 */     this.REFNO = rEFNO;
/*     */   }
/*     */   
/*     */   public String getBANK_SELF_NUM() {
/* 130 */     return this.BANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public void setBANK_SELF_NUM(String bANK_SELF_NUM) {
/* 134 */     this.BANK_SELF_NUM = bANK_SELF_NUM;
/*     */   }
/*     */   
/*     */   public String getPERSON_NAME() {
/* 138 */     return this.PERSON_NAME;
/*     */   }
/*     */   
/*     */   public void setPERSON_NAME(String pERSON_NAME) {
/* 142 */     this.PERSON_NAME = pERSON_NAME;
/*     */   }
/*     */   
/*     */   public String getPURFX_TYPE_CODE() {
/* 146 */     return this.PURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public void setPURFX_TYPE_CODE(String pURFX_TYPE_CODE) {
/* 150 */     this.PURFX_TYPE_CODE = pURFX_TYPE_CODE;
/*     */   }
/*     */   
/*     */   public String getPAY_ORG_CODE() {
/* 154 */     return this.PAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setPAY_ORG_CODE(String pAY_ORG_CODE) {
/* 158 */     this.PAY_ORG_CODE = pAY_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_NAME() {
/* 162 */     return this.AGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_NAME(String aGENT_CORP_NAME) {
/* 166 */     this.AGENT_CORP_NAME = aGENT_CORP_NAME;
/*     */   }
/*     */   
/*     */   public String getAGENT_CORP_CODE() {
/* 170 */     return this.AGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public void setAGENT_CORP_CODE(String aGENT_CORP_CODE) {
/* 174 */     this.AGENT_CORP_CODE = aGENT_CORP_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_CODE() {
/* 178 */     return this.INDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_CODE(String iNDIV_ORG_CODE) {
/* 182 */     this.INDIV_ORG_CODE = iNDIV_ORG_CODE;
/*     */   }
/*     */   
/*     */   public String getINDIV_ORG_NAME() {
/* 186 */     return this.INDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public void setINDIV_ORG_NAME(String iNDIV_ORG_NAME) {
/* 190 */     this.INDIV_ORG_NAME = iNDIV_ORG_NAME;
/*     */   }
/*     */   
/*     */   public String getCAPITALNO() {
/* 194 */     return this.CAPITALNO;
/*     */   }
/*     */   
/*     */   public void setCAPITALNO(String cAPITALNO) {
/* 198 */     this.CAPITALNO = cAPITALNO;
/*     */   }
/*     */   
/*     */   public String getTXCCY() {
/* 202 */     return this.TXCCY;
/*     */   }
/*     */   
/*     */   public void setTXCCY(String tXCCY) {
/* 206 */     this.TXCCY = tXCCY;
/*     */   }
/*     */   
/*     */   public String getPURFX_AMT() {
/* 210 */     return this.PURFX_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_AMT(String pURFX_AMT) {
/* 214 */     this.PURFX_AMT = pURFX_AMT;
/*     */   }
/*     */   
/*     */   public String getPURFX_ACCT_CNY() {
/* 218 */     return this.PURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public void setPURFX_ACCT_CNY(String pURFX_ACCT_CNY) {
/* 222 */     this.PURFX_ACCT_CNY = pURFX_ACCT_CNY;
/*     */   }
/*     */   
/*     */   public String getPURFX_CASH_AMT() {
/* 226 */     return this.PURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public void setPURFX_CASH_AMT(String pURFX_CASH_AMT) {
/* 230 */     this.PURFX_CASH_AMT = pURFX_CASH_AMT;
/*     */   }
/*     */   
/*     */   public String getFCY_REMIT_AMT() {
/* 234 */     return this.FCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_REMIT_AMT(String fCY_REMIT_AMT) {
/* 238 */     this.FCY_REMIT_AMT = fCY_REMIT_AMT;
/*     */   }
/*     */   
/*     */   public String getLCY_ACCT_NO() {
/* 242 */     return this.LCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public void setLCY_ACCT_NO(String lCY_ACCT_NO) {
/* 246 */     this.LCY_ACCT_NO = lCY_ACCT_NO;
/*     */   }
/*     */   
/*     */   public String getFCY_ACCT_AMT() {
/* 250 */     return this.FCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public void setFCY_ACCT_AMT(String fCY_ACCT_AMT) {
/* 254 */     this.FCY_ACCT_AMT = fCY_ACCT_AMT;
/*     */   }
/*     */   
/*     */   public String getTCHK_AMT() {
/* 258 */     return this.TCHK_AMT;
/*     */   }
/*     */   
/*     */   public void setTCHK_AMT(String tCHK_AMT) {
/* 262 */     this.TCHK_AMT = tCHK_AMT;
/*     */   }
/*     */   
/*     */   public String getREMARK() {
/* 266 */     return this.REMARK;
/*     */   }
/*     */   
/*     */   public void setREMARK(String rEMARK) {
/* 270 */     this.REMARK = rEMARK;
/*     */   }
/*     */   
/*     */   public String getMOD_REASON_CODE() {
/* 274 */     return this.MOD_REASON_CODE;
/*     */   }
/*     */   
/*     */   public void setMOD_REASON_CODE(String mOD_REASON_CODE) {
/* 278 */     this.MOD_REASON_CODE = mOD_REASON_CODE;
/*     */   }
/*     */   
/*     */   public String getMODIFY_REMARK() {
/* 282 */     return this.MODIFY_REMARK;
/*     */   }
/*     */   
/*     */   public void setMODIFY_REMARK(String mODIFY_REMARK) {
/* 286 */     this.MODIFY_REMARK = mODIFY_REMARK;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\domain\business\request\WGReqModifyIndividualFXSAInfoParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */