/*     */ package com.pactera.fems.message.wg.service.impl;
/*     */ 
/*     */ import com.pactera.fems.message.wg.domain.WGDS;
/*     */ import com.pactera.fems.message.wg.domain.WGReqMsgHead;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqAddLimitedIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqAddLimitedIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqAddUnLimitedIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqAddUnLimitedIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqDeleteIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqDeleteIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqMakeUpIndividualTradeFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqMakeUpIndividualTradeFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqMakeUpOtherIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqMakeUpOtherIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqMakeUpSignStatus;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqModifyIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqModifyIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqQueryIndividualFXSAInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqQueryIndividualFXSAQuota;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqQueryIndividualFXSEInfo;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqQueryIndividualFXSEQuota;
/*     */ import com.pactera.fems.message.wg.domain.business.request.WGReqQuerySafeExRate;
/*     */ import com.pactera.fems.message.wg.service.WGIndividualLCYService;
/*     */ import com.pactera.fems.message.wg.support.WGDSValitorHander;
/*     */ import com.pactera.fems.message.wg.support.WGMsgHandler;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.global.framework.xmlbeans.bean.DataCheckException;
/*     */ import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
/*     */ import org.global.framework.xmlbeans.util.PropertyUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WGIndividualLCYServiceImpl
/*     */   implements WGIndividualLCYService
/*     */ {
/*     */   public Map doQueryIndividualFXSEQuota(Map data, WGReqMsgHead head)
/*     */     throws DataCheckException
/*     */   {
/*  45 */     WGDS ds = new WGReqQueryIndividualFXSEQuota();
/*  46 */     WGDSValitorHander.valiteWGReqQueryIndividualFXSEQuota(data);
/*  47 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/*  49 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/*  50 */     Map m = new HashMap();
/*     */     try {
/*  52 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/*  54 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/*  56 */     return m;
/*     */   }
/*     */   
/*     */   public Map doAddLimitedIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/*  60 */     WGDS ds = new WGReqAddLimitedIndividualFXSEInfo();
/*  61 */     WGDSValitorHander.valiteWGReqAddLimitedIndividualFXSEInfo(data);
/*  62 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/*  64 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/*  65 */     Map m = new HashMap();
/*     */     try {
/*  67 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/*  69 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/*  71 */     return m;
/*     */   }
/*     */   
/*     */   public Map doAddUnLimitedIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/*  75 */     WGDS ds = new WGReqAddUnLimitedIndividualFXSEInfo();
/*  76 */     WGDSValitorHander.valiteWGReqAddUnLimitedIndividualFXSEInfo(data);
/*  77 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/*  79 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/*  80 */     Map m = new HashMap();
/*     */     try {
/*  82 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/*  84 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/*  86 */     return m;
/*     */   }
/*     */   
/*     */   public Map doQueryIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/*  90 */     WGDS ds = new WGReqQueryIndividualFXSEInfo();
/*  91 */     WGDSValitorHander.valiteWGReqQueryIndividualFXSEInfo(data);
/*  92 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/*  94 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/*  95 */     Map m = new HashMap();
/*     */     try {
/*  97 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/*  99 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 101 */     return m;
/*     */   }
/*     */   
/*     */   public Map doModifyIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 105 */     WGDS ds = new WGReqModifyIndividualFXSEInfo();
/* 106 */     WGDSValitorHander.valiteWGReqModifyIndividualFXSEInfo(data);
/* 107 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 109 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 110 */     Map m = new HashMap();
/*     */     try {
/* 112 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 114 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 116 */     return m;
/*     */   }
/*     */   
/*     */   public Map doDeleteIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 120 */     WGDS ds = new WGReqDeleteIndividualFXSEInfo();
/* 121 */     WGDSValitorHander.valiteWGReqDeleteIndividualFXSEInfo(data);
/* 122 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 124 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 125 */     Map m = new HashMap();
/*     */     try {
/* 127 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 129 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 131 */     return m;
/*     */   }
/*     */   
/*     */   public Map doMakeUpOtherIndividualFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 135 */     WGDS ds = new WGReqMakeUpOtherIndividualFXSEInfo();
/* 136 */     WGDSValitorHander.valiteWGReqMakeUpOtherIndividualFXSEInfo(data);
/* 137 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 139 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 140 */     Map m = new HashMap();
/*     */     try {
/* 142 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 144 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 146 */     return m;
/*     */   }
/*     */   
/*     */   public Map doMakeUpIndividualTradeFXSEInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 150 */     WGDS ds = new WGReqMakeUpIndividualTradeFXSEInfo();
/* 151 */     WGDSValitorHander.valiteWGReqMakeUpIndividualTradeFXSEInfo(data);
/* 152 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 154 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 155 */     Map m = new HashMap();
/*     */     try {
/* 157 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 159 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 161 */     return m;
/*     */   }
/*     */   
/*     */   public Map doQueryIndividualFXSAQuota(Map data, WGReqMsgHead head) throws DataCheckException {
/* 165 */     WGDS ds = new WGReqQueryIndividualFXSAQuota();
/* 166 */     WGDSValitorHander.valiteWGReqQueryIndividualFXSAQuota(data);
/* 167 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 169 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 170 */     Map m = new HashMap();
/*     */     try {
/* 172 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 174 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 176 */     return m;
/*     */   }
/*     */   
/*     */   public Map doAddLimitedIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 180 */     WGDS ds = new WGReqAddLimitedIndividualFXSAInfo();
/* 181 */     WGDSValitorHander.valiteWGReqAddLimitedIndividualFXSAInfo(data);
/* 182 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 184 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 185 */     Map m = new HashMap();
/*     */     try {
/* 187 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 189 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 191 */     return m;
/*     */   }
/*     */   
/*     */   public Map doAddUnLimitedIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 195 */     WGDS ds = new WGReqAddUnLimitedIndividualFXSAInfo();
/* 196 */     WGDSValitorHander.valiteWGReqAddUnLimitedIndividualFXSAInfo(data);
/* 197 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 199 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 200 */     Map m = new HashMap();
/*     */     try {
/* 202 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 204 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 206 */     return m;
/*     */   }
/*     */   
/*     */   public Map doQueryIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 210 */     WGDS ds = new WGReqQueryIndividualFXSAInfo();
/* 211 */     WGDSValitorHander.valiteWGReqQueryIndividualFXSAInfo(data);
/* 212 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 214 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 215 */     Map m = new HashMap();
/*     */     try {
/* 217 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 219 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 221 */     return m;
/*     */   }
/*     */   
/*     */   public Map doModifyIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 225 */     WGDS ds = new WGReqModifyIndividualFXSAInfo();
/* 226 */     WGDSValitorHander.valiteWGReqModifyIndividualFXSAInfo(data);
/* 227 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 229 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 230 */     Map m = new HashMap();
/*     */     try {
/* 232 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 234 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 236 */     return m;
/*     */   }
/*     */   
/*     */   public Map doDeleteIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 240 */     WGDS ds = new WGReqDeleteIndividualFXSAInfo();
/* 241 */     WGDSValitorHander.valiteWGReqDeleteIndividualFXSAInfo(data);
/* 242 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 244 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 245 */     Map m = new HashMap();
/*     */     try {
/* 247 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 249 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 251 */     return m;
/*     */   }
/*     */   
/*     */   public Map doMakeUpOtherIndividualFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 255 */     WGDS ds = new WGReqMakeUpOtherIndividualFXSAInfo();
/* 256 */     WGDSValitorHander.valiteWGReqMakeUpOtherIndividualFXSAInfo(data);
/* 257 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 259 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 260 */     Map m = new HashMap();
/*     */     try {
/* 262 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 264 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 266 */     return m;
/*     */   }
/*     */   
/*     */   public Map doMakeUpIndividualTradeFXSAInfo(Map data, WGReqMsgHead head) throws DataCheckException {
/* 270 */     WGDS ds = new WGReqMakeUpIndividualTradeFXSAInfo();
/* 271 */     WGDSValitorHander.valiteWGReqMakeUpIndividualTradeFXSAInfo(data);
/* 272 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 274 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 275 */     Map m = new HashMap();
/*     */     try {
/* 277 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 279 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 281 */     return m;
/*     */   }
/*     */   
/*     */   public Map doMakeUpSignStatus(Map data, WGReqMsgHead head) throws DataCheckException {
/* 285 */     WGDS ds = new WGReqMakeUpSignStatus();
/* 286 */     WGDSValitorHander.valiteWGReqMakeUpSignStatus(data);
/* 287 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 289 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 290 */     Map m = new HashMap();
/*     */     try {
/* 292 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 294 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 296 */     return m;
/*     */   }
/*     */   
/*     */   public Map doQuerySafeExRate(Map data, WGReqMsgHead head) throws DataCheckException {
/* 300 */     WGDS ds = new WGReqQuerySafeExRate();
/* 301 */     WGDSValitorHander.valiteWGReqQuerySafeExRate(data);
/* 302 */     WGDSValitorHander.setFeild(ds, data);
/*     */     
/* 304 */     Object rsp = WGMsgHandler.sendMessage(ds, head);
/* 305 */     Map m = new HashMap();
/*     */     try {
/* 307 */       PropertyUtils.bean2Map(rsp, m);
/*     */     } catch (Exception e) {
/* 309 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "实体Bean转换为Map出错:" + e.getMessage());
/*     */     }
/* 311 */     return m;
/*     */   }
/*     */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\service\impl\WGIndividualLCYServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */