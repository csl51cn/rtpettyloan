/*     */ package com.pactera.fems.message.wg.support;
/*     */ 
/*     */ import com.pactera.fems.message.util.DateTimeUtil;
/*     */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddLimitedIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddLimitedIndividualFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddLimitedIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddLimitedIndividualFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddUnLimitedIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddUnLimitedIndividualFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddUnLimitedIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspAddUnLimitedIndividualFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspDeleteIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspDeleteIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpIndividualTradeFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpIndividualTradeFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpIndividualTradeFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpIndividualTradeFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpOtherIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpOtherIndividualFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpOtherIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpOtherIndividualFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspMakeUpSignStatus;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspModifyIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspModifyIndividualFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspModifyIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspModifyIndividualFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSAInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSAQuota;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSAQuotaRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSEInfoRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSEQuota;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQueryIndividualFXSEQuotaRow;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQuerySafeExRate;
/*     */ import com.pactera.fems.message.wg.domain.business.response.WGRspQuerySafeExRateRow;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RspXMLGenerator
/*     */   extends BaseRspXMLGenerator
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  52 */     String xml = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  72 */     System.out.println(xml);
/*     */   }
/*     */   
/*     */   public static String buildQueryIndividualFXSEQuotaXML(WGReqMsgHead head) throws Exception
/*     */   {
/*  77 */     WGRspQueryIndividualFXSEQuota rsp = new WGRspQueryIndividualFXSEQuota();
/*  78 */     List ROWSET = new ArrayList();
/*  79 */     WGRspQueryIndividualFXSEQuotaRow row = new WGRspQueryIndividualFXSEQuotaRow();
/*  80 */     ROWSET.add(getWGRspQueryIndividualFXSEQuotaRow());
/*  81 */     rsp.setROWSET(ROWSET);
/*  82 */     String rspXml = buildRspXml(rsp, head);
/*  83 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspQueryIndividualFXSEQuotaRow getWGRspQueryIndividualFXSEQuotaRow() {
/*  87 */     WGRspQueryIndividualFXSEQuotaRow row = new WGRspQueryIndividualFXSEQuotaRow();
/*     */     
/*  89 */     row.setANN_LCYAMT_USD("1000.00");
/*  90 */     row.setANN_REM_LCYAMT_USD("2000.01");
/*  91 */     row.setCUSTNAME("张三");
/*  92 */     row.setCUSTTYPE_CODE("01");
/*  93 */     row.setEND_DATE(DateTimeUtil.getCurrentDate());
/*  94 */     row.setPUB_CODE("01");
/*  95 */     row.setPUB_DATE(DateTimeUtil.getCurrentDate());
/*  96 */     row.setPUB_REASON("2121");
/*  97 */     row.setSIGN_STATUS("0");
/*  98 */     row.setTODAY_CASH_USD("11000.00");
/*  99 */     row.setTYPE_STATUS("02");
/* 100 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildQuerySafeExRateXML(WGReqMsgHead head) throws Exception {
/* 104 */     WGRspQuerySafeExRate page = new WGRspQuerySafeExRate();
/*     */     
/* 106 */     List list = getQuerySafeExRateRow();
/*     */     
/* 108 */     page.setROWSET(list);
/* 109 */     page.setPAGENUMBER("1");
/* 110 */     page.setPAGESIZE(String.valueOf(list.size()));
/* 111 */     page.setRECORDCOUNT(String.valueOf(list.size()));
/* 112 */     String rspXml = buildRspXml(page, head);
/*     */     
/* 114 */     return rspXml;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List getQuerySafeExRateRow()
/*     */   {
/* 122 */     List list = new ArrayList();
/*     */     
/* 124 */     WGRspQuerySafeExRateRow row = new WGRspQuerySafeExRateRow();
/* 125 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/*     */     
/* 127 */     row.setCURRENCY_CODE("GBP");
/* 128 */     row.setEXCHANGE("1.6484");
/* 129 */     list.add(row);
/* 130 */     row = new WGRspQuerySafeExRateRow();
/* 131 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/* 132 */     row.setCURRENCY_CODE("EUR");
/* 133 */     row.setEXCHANGE("1.3795");
/* 134 */     list.add(row);
/* 135 */     row = new WGRspQuerySafeExRateRow();
/* 136 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/* 137 */     row.setCURRENCY_CODE("CAD");
/* 138 */     row.setEXCHANGE("0.89063057");
/* 139 */     list.add(row);
/* 140 */     row = new WGRspQuerySafeExRateRow();
/* 141 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/* 142 */     row.setCURRENCY_CODE("AUD");
/* 143 */     row.setEXCHANGE("0.9064");
/* 144 */     list.add(row);
/* 145 */     row = new WGRspQuerySafeExRateRow();
/* 146 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/* 147 */     row.setCURRENCY_CODE("CNY");
/* 148 */     row.setEXCHANGE("0.16316674");
/* 149 */     list.add(row);
/* 150 */     row = new WGRspQuerySafeExRateRow();
/* 151 */     row.setYEAR_MONTH(DateTimeUtil.getCurrentDate("yyyyMM"));
/* 152 */     row.setCURRENCY_CODE("HKD");
/* 153 */     row.setEXCHANGE("0.1");
/* 154 */     list.add(row);
/*     */     
/* 156 */     return list;
/*     */   }
/*     */   
/*     */   public static String buildMakeUpSignStatusXML(WGReqMsgHead head) throws Exception {
/* 160 */     WGRspMakeUpSignStatus rsp = new WGRspMakeUpSignStatus();
/* 161 */     String rspXml = buildRspXml(rsp, head);
/*     */     
/*     */ 
/*     */ 
/* 165 */     return rspXml;
/*     */   }
/*     */   
/*     */   public static String buildMakeUpIndividualTradeFXSAInfoXML(WGReqMsgHead head) throws Exception {
/* 169 */     WGRspMakeUpIndividualTradeFXSAInfo info = new WGRspMakeUpIndividualTradeFXSAInfo();
/*     */     
/* 171 */     List list = new ArrayList();
/* 172 */     list.add(getMakeUpIndividualTradeFXSAInfoRow(head));
/*     */     
/* 174 */     info.setROWSET(list);
/* 175 */     info.setPAGENUMBER("1");
/* 176 */     info.setPAGESIZE("1");
/* 177 */     info.setRECORDCOUNT("1");
/* 178 */     String rspXml = buildRspXml(info, head);
/* 179 */     return rspXml;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static WGRspMakeUpIndividualTradeFXSAInfoRow getMakeUpIndividualTradeFXSAInfoRow(WGReqMsgHead head)
/*     */   {
/* 187 */     WGRspMakeUpIndividualTradeFXSAInfoRow row = new WGRspMakeUpIndividualTradeFXSAInfoRow();
/* 188 */     row.setREFNO(String.valueOf("G000000000000000" + System.currentTimeMillis()));
/* 189 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 190 */     row.setCODE("00000");
/* 191 */     row.setDETAIL("操作成功");
/*     */     
/* 193 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildMakeUpOtherIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception {
/* 197 */     WGRspMakeUpOtherIndividualFXSAInfo info = new WGRspMakeUpOtherIndividualFXSAInfo();
/*     */     
/* 199 */     List list = new ArrayList();
/* 200 */     list.add(getMakeUpOtherIndividualFXSAInfoRow(head));
/*     */     
/* 202 */     info.setROWSET(list);
/* 203 */     info.setPAGENUMBER("1");
/* 204 */     info.setPAGESIZE("1");
/* 205 */     info.setRECORDCOUNT("1");
/* 206 */     String rspXml = buildRspXml(info, head);
/* 207 */     return rspXml;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static WGRspMakeUpOtherIndividualFXSAInfoRow getMakeUpOtherIndividualFXSAInfoRow(WGReqMsgHead head)
/*     */   {
/* 215 */     WGRspMakeUpOtherIndividualFXSAInfoRow row = new WGRspMakeUpOtherIndividualFXSAInfoRow();
/* 216 */     row.setREFNO(String.valueOf("G000000000000000" + System.currentTimeMillis()));
/* 217 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 218 */     row.setCODE("00000");
/* 219 */     row.setDETAIL("操作成功");
/*     */     
/* 221 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildDeleteIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception {
/* 225 */     WGRspDeleteIndividualFXSAInfo rsp = new WGRspDeleteIndividualFXSAInfo();
/* 226 */     String rspXml = buildRspXml(rsp, head);
/* 227 */     return rspXml;
/*     */   }
/*     */   
/*     */   public static String buildModifyIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception {
/* 231 */     WGRspModifyIndividualFXSAInfo info = new WGRspModifyIndividualFXSAInfo();
/*     */     
/* 233 */     List list = new ArrayList();
/* 234 */     list.add(getModifyIndividualFXSAInfoRow(head));
/*     */     
/* 236 */     info.setROWSET(list);
/*     */     
/* 238 */     String rspXml = buildRspXml(info, head);
/* 239 */     return rspXml;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static WGRspModifyIndividualFXSAInfoRow getModifyIndividualFXSAInfoRow(WGReqMsgHead head)
/*     */   {
/* 247 */     WGRspModifyIndividualFXSAInfoRow row = new WGRspModifyIndividualFXSAInfoRow();
/* 248 */     row.setREFNO(String.valueOf("G000000000000000" + System.currentTimeMillis()));
/* 249 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 250 */     row.setCODE("00000");
/* 251 */     row.setDETAIL("操作成功");
/*     */     
/* 253 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildQueryIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception {
/* 257 */     WGRspQueryIndividualFXSAInfo info = new WGRspQueryIndividualFXSAInfo();
/*     */     
/* 259 */     List list = new ArrayList();
/* 260 */     for (int i = 0; i < 2; i++) {
/* 261 */       list.add(getQueryIndividualFXSAInfoRow(head, "I" + i));
/*     */     }
/*     */     
/* 264 */     info.setROWSET(list);
/* 265 */     info.setPAGENUMBER("1");
/* 266 */     info.setPAGESIZE("2");
/* 267 */     info.setRECORDCOUNT("2");
/*     */     
/* 269 */     String rspXml = buildRspXml(info, head);
/* 270 */     return rspXml;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static WGRspQueryIndividualFXSAInfoRow getQueryIndividualFXSAInfoRow(WGReqMsgHead head, String tmp)
/*     */   {
/* 278 */     WGRspQueryIndividualFXSAInfoRow row = new WGRspQueryIndividualFXSAInfoRow();
/* 279 */     row.setREFNO(String.valueOf(System.currentTimeMillis() + "0000000000000000"));
/* 280 */     row.setBANK_SELF_NUM(head.getMSGNO() + ":" + tmp);
/*     */     
/*     */ 
/*     */ 
/* 284 */     row.setBIZ_TYPE_CODE("01");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 289 */     row.setIDTYPE_CODE("01");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 294 */     row.setIDCODE("500382198609202450");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 299 */     row.setADD_IDCODE("500382198609202451");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 304 */     row.setCTYCODE("HKG");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 309 */     row.setPERSON_NAME("小吴");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 314 */     row.setPURFX_TYPE_CODE("110");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 319 */     row.setPAY_ORG_NAME("重庆农商行");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 324 */     row.setPAY_ORG_CODE("200011000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 329 */     row.setAGENT_CORP_NAME("文思海辉");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 335 */     row.setAGENT_CORP_CODE("300001000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 340 */     row.setINDIV_ORG_CODE("400000100");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 345 */     row.setINDIV_ORG_NAME("不求晓得");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 350 */     row.setCAPITALNO("50000010005000001000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 355 */     row.setTXCCY("USD");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 360 */     row.setPURFX_AMT("100000.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */     row.setPURFX_AMT_USD("6.50");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 371 */     row.setPURFX_ACCT_CNY("60000000100000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 376 */     row.setPURFX_CASH_AMT("500000.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 381 */     row.setPURFX_CASH_AMT_USD("1.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 386 */     row.setFCY_REMIT_AMT("20000000.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 391 */     row.setFCY_REMIT_AMT_USD("3000000.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 397 */     row.setLCY_ACCT_NO("7000000100000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 402 */     row.setFCY_ACCT_AMT("10000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 407 */     row.setFCY_ACCT_AMT_USD("12345.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 412 */     row.setTCHK_AMT("400.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 417 */     row.setTCHK_AMT_USD("500.00");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 422 */     row.setBIZ_TX_CHNL_CODE("11");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 427 */     row.setBIZ_TX_TIME("2015-07-15");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 432 */     row.setBRANCH_CODE("890123451234");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 437 */     row.setBRANCH_NAME("cqrcb");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 442 */     row.setOPERCODE("12345678");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 447 */     row.setACTIONTYPE("0");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 452 */     row.setREMARK("没得备注");
/*     */     
/* 454 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildAddLimitedIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 459 */     WGRspAddLimitedIndividualFXSEInfo info = new WGRspAddLimitedIndividualFXSEInfo();
/* 460 */     List list = new ArrayList();
/* 461 */     list.add(getAddLimitedIndividualFXSEInfoRow(head));
/*     */     
/* 463 */     info.setROWSET(list);
/*     */     
/* 465 */     String rspXml = buildRspXml(info, head);
/* 466 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspAddLimitedIndividualFXSEInfoRow getAddLimitedIndividualFXSEInfoRow(WGReqMsgHead head)
/*     */   {
/* 471 */     WGRspAddLimitedIndividualFXSEInfoRow row = new WGRspAddLimitedIndividualFXSEInfoRow();
/* 472 */     row.setREFNO(String.valueOf("J000000000000000" + System.currentTimeMillis()));
/* 473 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 474 */     row.setANN_REM_LCYAMT_USD("1000");
/* 475 */     row.setEND_DATE(DateTimeUtil.getCurrentDate());
/* 476 */     row.setPUB_CODE("01");
/* 477 */     row.setPUB_DATE(DateTimeUtil.getCurrentDate());
/* 478 */     row.setPUB_REASON("dddd");
/* 479 */     row.setSALEFX_AMT_USD("2");
/* 480 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildAddUnLimitedIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 485 */     WGRspAddUnLimitedIndividualFXSEInfo info = new WGRspAddUnLimitedIndividualFXSEInfo();
/* 486 */     List list = new ArrayList();
/* 487 */     list.add(getAddUnLimitedIndividualFXSEInfoRow(head));
/*     */     
/* 489 */     info.setROWSET(list);
/*     */     
/* 491 */     String rspXml = buildRspXml(info, head);
/* 492 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspAddUnLimitedIndividualFXSEInfoRow getAddUnLimitedIndividualFXSEInfoRow(WGReqMsgHead head)
/*     */   {
/* 497 */     WGRspAddUnLimitedIndividualFXSEInfoRow row = new WGRspAddUnLimitedIndividualFXSEInfoRow();
/* 498 */     row.setREFNO(String.valueOf("J000000000000000" + System.currentTimeMillis()));
/* 499 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 500 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildQueryIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 505 */     WGRspQueryIndividualFXSEInfo info = new WGRspQueryIndividualFXSEInfo();
/* 506 */     List list = new ArrayList();
/* 507 */     for (int i = 0; i < 2; i++) {
/* 508 */       list.add(getQueryIndividualFXSEInfoRow(head, i));
/*     */     }
/*     */     
/* 511 */     info.setROWSET(list);
/* 512 */     info.setPAGENUMBER("2");
/* 513 */     info.setPAGESIZE("1");
/* 514 */     info.setRECORDCOUNT("2");
/*     */     
/* 516 */     String rspXml = buildRspXml(info, head);
/* 517 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspQueryIndividualFXSEInfoRow getQueryIndividualFXSEInfoRow(WGReqMsgHead head, int i)
/*     */   {
/* 522 */     WGRspQueryIndividualFXSEInfoRow row = new WGRspQueryIndividualFXSEInfoRow();
/* 523 */     row.setREFNO(String.valueOf(System.currentTimeMillis() + "0000000000000000"));
/* 524 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 525 */     row.setSALEFX_AMT("1000" + i);
/* 526 */     row.setSALEFX_AMT_USD("100");
/* 527 */     row.setBIZ_TX_CHNL_CODE("01");
/*     */     
/* 529 */     row.setSALEFX_TX_CODE("110");
/*     */     
/* 531 */     row.setSALEFX_SETTLE_CODE("01");
/*     */     
/*     */ 
/*     */ 
/* 535 */     row.setBIZ_TYPE_CODE("01");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 540 */     row.setIDTYPE_CODE("01");
/*     */     
/* 542 */     row.setLCY_ACCTNO_CNY("600000012222");
/* 543 */     row.setSALEFX_AMT_USD("12345.67");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 548 */     row.setIDCODE("500382198609202450");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 553 */     row.setADD_IDCODE("500382198609202451");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 558 */     row.setCTYCODE("HKG");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 563 */     row.setPERSON_NAME("小吴");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 568 */     row.setPAY_ORG_NAME("重庆农商行");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 573 */     row.setPAY_ORG_CODE("200011000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 578 */     row.setAGENT_CORP_NAME("文思海辉");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 584 */     row.setAGENT_CORP_CODE("300001000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 589 */     row.setINDIV_ORG_CODE("400000100");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 594 */     row.setINDIV_ORG_NAME("不求晓得");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 599 */     row.setCAPITALNO("50000010005000001000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 604 */     row.setTXCCY("USD");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 610 */     row.setLCY_ACCT_NO("7000000100000");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 615 */     row.setBIZ_TX_CHNL_CODE("11");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 620 */     row.setBIZ_TX_TIME("2015-07-15");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 625 */     row.setBRANCH_CODE("890123451234");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 630 */     row.setBRANCH_NAME("cqrcb");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 635 */     row.setOPERCODE("12345678");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 640 */     row.setACTIONTYPE("0");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 645 */     row.setREMARK("没得备注");
/*     */     
/*     */ 
/* 648 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildModifyIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 653 */     WGRspModifyIndividualFXSEInfo info = new WGRspModifyIndividualFXSEInfo();
/* 654 */     List list = new ArrayList();
/* 655 */     list.add(getModifyIndividualFXSEInfoRow(head));
/*     */     
/* 657 */     info.setROWSET(list);
/*     */     
/* 659 */     String rspXml = buildRspXml(info, head);
/* 660 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspModifyIndividualFXSEInfoRow getModifyIndividualFXSEInfoRow(WGReqMsgHead head)
/*     */   {
/* 665 */     WGRspModifyIndividualFXSEInfoRow row = new WGRspModifyIndividualFXSEInfoRow();
/* 666 */     row.setREFNO(String.valueOf("J000000000000000" + System.currentTimeMillis()));
/* 667 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 668 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildDeleteIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 673 */     WGRspDeleteIndividualFXSEInfo rsp = new WGRspDeleteIndividualFXSEInfo();
/* 674 */     String rspXml = buildRspXml(rsp, head);
/* 675 */     return rspXml;
/*     */   }
/*     */   
/*     */   public static String buildMakeUpOtherIndividualFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 680 */     WGRspMakeUpOtherIndividualFXSEInfo info = new WGRspMakeUpOtherIndividualFXSEInfo();
/* 681 */     List list = new ArrayList();
/* 682 */     list.add(getMakeUpOtherIndividualFXSEInfoRow(head));
/*     */     
/* 684 */     info.setROWSET(list);
/*     */     
/* 686 */     String rspXml = buildRspXml(info, head);
/* 687 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspMakeUpOtherIndividualFXSEInfoRow getMakeUpOtherIndividualFXSEInfoRow(WGReqMsgHead head)
/*     */   {
/* 692 */     WGRspMakeUpOtherIndividualFXSEInfoRow row = new WGRspMakeUpOtherIndividualFXSEInfoRow();
/* 693 */     row.setREFNO(String.valueOf("J000000000000000" + System.currentTimeMillis()));
/* 694 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 695 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildMakeUpIndividualTradeFXSEInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 700 */     WGRspMakeUpIndividualTradeFXSEInfo info = new WGRspMakeUpIndividualTradeFXSEInfo();
/* 701 */     List list = new ArrayList();
/* 702 */     list.add(getMakeUpIndividualTradeFXSEInfoRow(head));
/*     */     
/* 704 */     info.setROWSET(list);
/*     */     
/* 706 */     String rspXml = buildRspXml(info, head);
/* 707 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspMakeUpIndividualTradeFXSEInfoRow getMakeUpIndividualTradeFXSEInfoRow(WGReqMsgHead head)
/*     */   {
/* 712 */     WGRspMakeUpIndividualTradeFXSEInfoRow row = new WGRspMakeUpIndividualTradeFXSEInfoRow();
/* 713 */     row.setREFNO(String.valueOf("J000000000000000" + System.currentTimeMillis()));
/* 714 */     row.setBANK_SELF_NUM(head.getMSGNO());
/* 715 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildQueryIndividualFXSAQuotaXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 720 */     WGRspQueryIndividualFXSAQuota info = new WGRspQueryIndividualFXSAQuota();
/* 721 */     List list = new ArrayList();
/* 722 */     list.add(getQueryIndividualFXSAQuotaRow());
/*     */     
/* 724 */     info.setROWSET(list);
/*     */     
/* 726 */     String rspXml = buildRspXml(info, head);
/* 727 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspQueryIndividualFXSAQuotaRow getQueryIndividualFXSAQuotaRow()
/*     */   {
/* 732 */     WGRspQueryIndividualFXSAQuotaRow row = new WGRspQueryIndividualFXSAQuotaRow();
/* 733 */     row.setANN_FCYAMT_USD("1000");
/* 734 */     row.setANN_REM_FCYAMT_USD("2000");
/* 735 */     row.setCUSTNAME("李四");
/* 736 */     row.setCUSTTYPE_CODE("01");
/* 737 */     row.setEND_DATE(DateTimeUtil.getCurrentDateTime());
/* 738 */     row.setPUB_CODE("01");
/* 739 */     row.setPUB_DATE(DateTimeUtil.getCurrentDateTime());
/* 740 */     row.setPUB_REASON("外管局发布的原因1321");
/* 741 */     row.setSIGN_STATUS("0");
/* 742 */     row.setTODAY_CASH_USD("8000.00");
/* 743 */     row.setTYPE_STATUS("02");
/* 744 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildAddLimitedIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 749 */     WGRspAddLimitedIndividualFXSAInfo info = new WGRspAddLimitedIndividualFXSAInfo();
/* 750 */     List list = new ArrayList();
/* 751 */     list.add(getAddLimitedIndividualFXSAInfoRow());
/*     */     
/* 753 */     info.setROWSET(list);
/*     */     
/* 755 */     String rspXml = buildRspXml(info, head);
/* 756 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspAddLimitedIndividualFXSAInfoRow getAddLimitedIndividualFXSAInfoRow()
/*     */   {
/* 761 */     WGRspAddLimitedIndividualFXSAInfoRow row = new WGRspAddLimitedIndividualFXSAInfoRow();
/* 762 */     row.setREFNO(String.valueOf("G000000000000000" + System.currentTimeMillis()));
/* 763 */     row.setBANK_SELF_NUM("23536347345");
/* 764 */     row.setPURFX_AMT_USD("100");
/* 765 */     row.setANN_REM_FCYAMT_USD("100");
/* 766 */     return row;
/*     */   }
/*     */   
/*     */   public static String buildAddUnLimitedIndividualFXSAInfoXML(WGReqMsgHead head) throws Exception
/*     */   {
/* 771 */     WGRspAddUnLimitedIndividualFXSAInfo info = new WGRspAddUnLimitedIndividualFXSAInfo();
/* 772 */     List list = new ArrayList();
/* 773 */     list.add(getAddUnLimitedIndividualFXSAInfoRow());
/*     */     
/* 775 */     info.setROWSET(list);
/*     */     
/* 777 */     String rspXml = buildRspXml(info, head);
/* 778 */     return rspXml;
/*     */   }
/*     */   
/*     */   private static WGRspAddUnLimitedIndividualFXSAInfoRow getAddUnLimitedIndividualFXSAInfoRow() {
/* 782 */     WGRspAddUnLimitedIndividualFXSAInfoRow row = new WGRspAddUnLimitedIndividualFXSAInfoRow();
/* 783 */     row.setREFNO(String.valueOf("G000000000000000" + System.currentTimeMillis()));
/* 784 */     row.setBANK_SELF_NUM("23536347345");
/* 785 */     return row;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\RspXMLGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */