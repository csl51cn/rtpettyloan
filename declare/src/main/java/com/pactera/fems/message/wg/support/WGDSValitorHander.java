/*      */ package com.pactera.fems.message.wg.support;
/*      */ 
/*      */ import com.pactera.fems.message.util.DateTimeUtil;
/*      */ import com.pactera.fems.message.wg.constants.BizChannelTypeEnum;
/*      */ import com.pactera.fems.message.wg.constants.BizTypeEnum;
/*      */ import com.pactera.fems.message.wg.constants.CertTypeEnum;
/*      */ import com.pactera.fems.message.wg.constants.GHDataModifyResonEnum;
/*      */ import com.pactera.fems.message.wg.constants.JHZJXTCodeEnum;
/*      */ import com.pactera.fems.message.wg.constants.JSHCancelResonEnum;
/*      */ import com.pactera.fems.message.wg.constants.JSHDataImportResonEnum;
/*      */ import com.thoughtworks.xstream.core.util.Primitives;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.math.BigDecimal;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.global.framework.xmlbeans.bean.DataCheckException;
/*      */ import org.global.framework.xmlbeans.bean.MsgErrorCodeEnum;
/*      */ import org.global.framework.xmlbeans.util.PropertyUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WGDSValitorHander
/*      */ {
/*      */   public static void valiteWGReqQueryIndividualFXSEQuota(Map data)
/*      */     throws DataCheckException
/*      */   {
/*   44 */     Map PARAMETERS = (Map)data.get("PARAMETERS");
/*   45 */     if (PARAMETERS == null) {
/*   46 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个人结汇额度和分类查询参数节点[PARAMETERS]不能为空");
/*      */     }
/*   48 */     String IDTYPE_CODE = (String)PARAMETERS.get("IDTYPE_CODE");
/*   49 */     String IDCODE = (String)PARAMETERS.get("IDCODE");
/*      */     try {
/*   51 */       valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*      */     } catch (DataCheckException e) {
/*   53 */       throw new DataCheckException(e.getCode(), "个人结汇额度和分类查询:" + e.getReason());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddLimitedIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*   66 */     NullVilator nv = new NullVilator();
/*   67 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*   68 */     nv.hasNull();
/*   69 */     String PERSON_NAME = (String)nv.PopMap(PARAMETERS, "PERSON_NAME", "姓名");
/*      */     
/*   71 */     String IDTYPE_CODE = (String)nv.PopMap(PARAMETERS, "IDTYPE_CODE", "证件类型代码");
/*      */     
/*   73 */     String SALEFX_SETTLE_CODE = (String)nv.PopMap(PARAMETERS, "SALEFX_SETTLE_CODE", "结汇资金形态代码");
/*      */     
/*   75 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*      */     
/*   77 */     String ADD_IDCODE = (String)PARAMETERS.get("ADD_IDCODE");
/*   78 */     String TXCCY = (String)nv.PopMap(PARAMETERS, "TXCCY", "币种");
/*      */     
/*   80 */     String LCY_ACCT_NO = (String)PARAMETERS.get("LCY_ACCT_NO");
/*   81 */     String IDCODE = (String)PARAMETERS.get("IDCODE");
/*   82 */     String CTYCODE = (String)nv.PopMap(PARAMETERS, "CTYCODE", "国家/地区代码");
/*   83 */     nv.hasNull();
/*      */     
/*   85 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*   86 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*   88 */     String bizChnlCode = (String)nv.PopMap(PARAMETERS, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*   89 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*   91 */     if (!BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*   92 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 必须是 占额度结汇");
/*      */     }
/*   94 */     if ((StringUtils.isBlank(PERSON_NAME)) || (!PERSON_NAME.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+")))
/*      */     {
/*      */ 
/*   97 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "姓名格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
/*      */     }
/*   99 */     if (!Arrays.asList(new String[] { CertTypeEnum.TYPE_01.getCode(), CertTypeEnum.TYPE_04.getCode(), CertTypeEnum.TYPE_05.getCode(), CertTypeEnum.TYPE_06.getCode(), CertTypeEnum.TYPE_07.getCode(), CertTypeEnum.TYPE_09.getCode() }).contains(IDTYPE_CODE))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  106 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "占用额度的结汇信息录入 证件类型 必须是 身份证/外国护照/港澳通行证/台湾通行证/中国护照/外国人永久居留证！");
/*      */     }
/*  108 */     valitTXCCY(TXCCY);
/*      */     
/*  110 */     if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (StringUtils.isBlank(LCY_ACCT_NO)))
/*  111 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当结汇资金形态为“账户资金”时，个人外汇账户账号不能为空");
/*  112 */     if ((!JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (StringUtils.isNotBlank(LCY_ACCT_NO))) {
/*  113 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当结汇资金形态不为“账户资金”时，个人外汇账户账号必须为空");
/*      */     }
/*      */     
/*  116 */     String SALEFX_TX_CODE = (String)nv.PopMap(PARAMETERS, "SALEFX_TX_CODE", "结汇资金属性代码");
/*      */     
/*  118 */     validateJHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, SALEFX_TX_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddUnLimitedIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  131 */     NullVilator nv = new NullVilator();
/*  132 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  133 */     nv.hasNull();
/*  134 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求参数循环体");
/*  135 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*      */     
/*  137 */     nv.hasNull();
/*  138 */     if (BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*  139 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 不能是 占额度的结汇");
/*      */     }
/*  141 */     for (int i = 0; i < ROWSET.size(); i++) {
/*  142 */       Map row = (Map)ROWSET.get(i);
/*  143 */       valiteWGReqAddUnLimitedIndividualFXSEInfoRow(row, BIZ_TYPE_CODE, i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddUnLimitedIndividualFXSEInfoRow(Map row, String BIZ_TYPE_CODE, int i)
/*      */     throws DataCheckException
/*      */   {
/*  158 */     NullVilator nv = new NullVilator();
/*  159 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/*  160 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/*  161 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/*  162 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/*  163 */     String SALEFX_AMT = parseAmt((String)nv.PopMap(row, "SALEFX_AMT", "结汇金额"));
/*  164 */     String SALEFX_SETTLE_CODE = (String)nv.PopMap(row, "SALEFX_SETTLE_CODE", "结汇资金形态");
/*  165 */     String BIZ_TX_CHNL_CODE = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  166 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/*  167 */     String AGENT_CORP_CODE = (String)row.get("AGENT_CORP_CODE");
/*  168 */     String AGENT_CORP_NAME = (String)row.get("AGENT_CORP_NAME");
/*  169 */     String INDIV_ORG_CODE = (String)row.get("INDIV_ORG_CODE");
/*  170 */     String INDIV_ORG_NAME = (String)row.get("INDIV_ORG_NAME");
/*  171 */     String PAY_ORG_CODE = (String)row.get("PAY_ORG_CODE");
/*  172 */     String CAPITALNO = (String)row.get("CAPITALNO");
/*  173 */     String IDCODE = (String)row.get("IDCODE");
/*  174 */     String BIZ_TX_TIME = (String)nv.PopMap(row, "BIZ_TX_TIME", "业务办理时间");
/*  175 */     String REMARK = (String)row.get("REMARK");
/*      */     
/*  177 */     nv.hasNull();
/*  178 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  179 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*  181 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  182 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*  184 */     if ("CNY".equals(TXCCY)) {
/*  185 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种：TXCCY 不能为“人民币”");
/*      */     }
/*  187 */     if ((SALEFX_AMT == null) || ("".equals(SALEFX_AMT)) || (parseDouble(SALEFX_AMT) <= 0.0D))
/*      */     {
/*  189 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  212 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (AGENT_CORP_CODE.length() != 9) && (AGENT_CORP_CODE.length() != 18) && (!AGENT_CORP_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  215 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "代理企业组织机构代码：AGENT_CORP_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*  217 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && (INDIV_ORG_CODE.length() != 9) && (INDIV_ORG_CODE.length() != 18) && (!INDIV_ORG_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  220 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个体工商户组织机构代码：INDIV_ORG_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*      */     
/*  223 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/*  224 */       if ((PAY_ORG_CODE == null) || ("".equals(PAY_ORG_CODE)) || ((PAY_ORG_CODE.length() != 9) && (PAY_ORG_CODE.length() != 18))) {
/*  225 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构结汇”时  支付机构组织机构代码：PAY_ORG_CODE不允许为空，且长度应为9位或18位。");
/*      */       }
/*  227 */       if (!BizChannelTypeEnum.TYPE_32.getCode().equals(BIZ_TX_CHNL_CODE)) {
/*  228 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构购汇”时  业务办理渠道代码：BIZ_TX_CHNL_CODE 必须为“32支付机构”。");
/*      */       }
/*  230 */       if (!JHZJXTCodeEnum.CODE_02.getCode().equals(SALEFX_SETTLE_CODE)) {
/*  231 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为通过支付机构的结汇时，结汇资金形态:SALEFX_SETTLE_CODE 限定为“02-汇入资金（包括外汇票据）。");
/*      */       }
/*      */     }
/*  234 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/*  235 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“资本项目结汇”时  外汇局批件号/备案表号/业务编号：CAPITALNO  不允许为空。");
/*      */     }
/*      */     
/*      */ 
/*  239 */     if (StringUtils.isBlank(LCY_ACCT_NO)) {
/*  240 */       if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */       {
/*  242 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态为“账户资金”时或者业务类型为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/*      */       }
/*  244 */     } else if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && 
/*  245 */       (!JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */     {
/*  247 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态不为“账户资金”时或者业务类型不为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*  250 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  251 */     String currentTime = sdf.format(new Date()).substring(0, 10);
/*  252 */     if (BIZ_TX_TIME.length() < 10)
/*  253 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理时间:BIZ_TX_TIME必须为当天时间并且格式满足：yyyy-MM-dd HH:mm:ss");
/*  254 */     if (!BIZ_TX_TIME.substring(0, 10).equals(currentTime)) {
/*  255 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理时间:BIZ_TX_TIME必须为当天时间并且格式满足：yyyy-MM-dd HH:mm:ss");
/*      */     }
/*  257 */     if ((BizTypeEnum.TYPE_03.getCode().equals(BIZ_TYPE_CODE)) && (!IDTYPE_CODE.equals("10")) && ((REMARK == null) || ("".equals(REMARK))))
/*      */     {
/*  259 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“提供凭证的经常项目其他结汇”,并且证件类型不是“外交官证”时 备注:REMARK不允许为空");
/*      */     }
/*      */     
/*  262 */     String SALEFX_TX_CODE = (String)nv.PopMap(row, "SALEFX_TX_CODE", "结汇资金属性代码");
/*      */     
/*  264 */     validateJHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, SALEFX_TX_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqQueryIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  276 */     NullVilator nv = new NullVilator();
/*  277 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  278 */     nv.hasNull();
/*  279 */     String BIZ_TX_TIME = (String)PARAMETERS.get("BIZ_TX_TIME");
/*      */     
/*  281 */     String IDTYPE_CODE = (String)nv.PopMap(PARAMETERS, "IDTYPE_CODE", "证件类型代码");
/*      */     
/*  283 */     String IDCODE = (String)nv.PopMap(PARAMETERS, "IDCODE", "证件号码");
/*  284 */     String CTYCODE = (String)nv.PopMap(PARAMETERS, "CTYCODE", "国家/地区代码");
/*      */     
/*  286 */     nv.synJuge();
/*      */     
/*  288 */     nv.addToAllMap(PARAMETERS, "BIZ_TX_TIME", "业务办理日期");
/*  289 */     nv.addToAllMap(PARAMETERS, "REFNO", "业务参号");
/*  290 */     nv.isAllNull();
/*  291 */     if (StringUtils.isNotEmpty(IDTYPE_CODE)) {
/*  292 */       valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  293 */       valitCpRelate(IDTYPE_CODE, CTYCODE, null, "00");
/*      */     }
/*      */     
/*  296 */     if (StringUtils.isNotEmpty(BIZ_TX_TIME)) {
/*      */       try {
/*  298 */         if (DateTimeUtil.formatToDate(BIZ_TX_TIME).getTime() > DateTimeUtil.getNowFormat().getTime()) {
/*  299 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理日期：BIZ_TX_TIME不能大于当前日期并且格式满足：yyyy-MM-dd");
/*      */         }
/*  301 */         if (DateTimeUtil.formatToDate(BIZ_TX_TIME, DateTimeUtil.defaultYearPattern).getTime() != DateTimeUtil.getNowFormat(DateTimeUtil.defaultYearPattern).getTime())
/*      */         {
/*  303 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理日期：BIZ_TX_TIME 只能是本年内并且格式满足：yyyy-MM-dd");
/*      */         }
/*      */       } catch (ParseException e) {
/*  306 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), " 日期格式转换异常:" + e.getMessage());
/*      */       }
/*      */     }
/*  309 */     if (CertTypeEnum.TYPE_05.getCode().equals(IDTYPE_CODE)) {
/*  310 */       if (StringUtils.isNotEmpty(CTYCODE)) {
/*  311 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型为05的业务信息，只能通过业务参号或者业务办理日期进行查询，不能通过证件号码进行查询");
/*      */       }
/*  313 */       nv.addToAllMap(PARAMETERS, "BIZ_TX_TIME", "业务办理日期");
/*  314 */       nv.addToAllMap(PARAMETERS, "REFNO", "业务参号");
/*  315 */       nv.isAllNull();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqModifyIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  328 */     String msgprefix = "个人结汇信息修改  请求报文中 ";
/*  329 */     NullVilator nv = new NullVilator();
/*  330 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", msgprefix + "PARAMETERS");
/*      */     
/*  332 */     nv.hasNull();
/*      */     
/*  334 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*  335 */     String TXCCY = (String)nv.PopMap(PARAMETERS, "TXCCY", "币种");
/*  336 */     String SALEFX_AMT = parseAmt((String)nv.PopMap(PARAMETERS, "SALEFX_AMT", "结汇金额"));
/*      */     
/*  338 */     String SALEFX_SETTLE_CODE = (String)nv.PopMap(PARAMETERS, "SALEFX_SETTLE_CODE", "结汇资金形态");
/*      */     
/*  340 */     String LCY_ACCT_NO = (String)nv.PopMap(PARAMETERS, "LCY_ACCT_NO", "个人外汇账户账号");
/*      */     
/*  342 */     String MOD_REASON_CODE = (String)nv.PopMap(PARAMETERS, "MOD_REASON_CODE", "修改原因代码");
/*      */     
/*  344 */     String MODIFY_REMARK = (String)PARAMETERS.get("MODIFY_REMARK");
/*  345 */     String AGENT_CORP_CODE = (String)PARAMETERS.get("AGENT_CORP_CODE");
/*  346 */     String AGENT_CORP_NAME = (String)PARAMETERS.get("AGENT_CORP_NAME");
/*  347 */     String INDIV_ORG_CODE = (String)PARAMETERS.get("INDIV_ORG_CODE");
/*  348 */     String INDIV_ORG_NAME = (String)PARAMETERS.get("INDIV_ORG_NAME");
/*  349 */     String PAY_ORG_CODE = (String)PARAMETERS.get("PAY_ORG_CODE");
/*  350 */     String CAPITALNO = (String)PARAMETERS.get("CAPITALNO");
/*  351 */     String REMARK = (String)PARAMETERS.get("REMARK");
/*  352 */     if ("CNY".equals(TXCCY)) {
/*  353 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种：TXCCY 不能为“人民币”");
/*      */     }
/*  355 */     if ((SALEFX_AMT == null) || ("".equals(SALEFX_AMT)) || (parseDouble(SALEFX_AMT) <= 0.0D))
/*      */     {
/*  357 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*  360 */     if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && ((LCY_ACCT_NO == null) || ("".equals(LCY_ACCT_NO)))) {
/*  361 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当结汇资金形态为“账户资金”时，个人外汇账户账号不能为空");
/*      */     }
/*  363 */     if ((JSHCancelResonEnum.CODE_06.getCode().equals(MOD_REASON_CODE)) && ((MODIFY_REMARK == null) || ("".equals(MODIFY_REMARK))))
/*      */     {
/*  365 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "修改原因为“其他”时  修改原因说明不允许为空");
/*      */     }
/*  367 */     if (StringUtils.isBlank(BIZ_TYPE_CODE)) {
/*  368 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "业务类型代码 为空");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  389 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (AGENT_CORP_CODE.length() != 9) && (AGENT_CORP_CODE.length() != 18) && (!AGENT_CORP_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  392 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "代理企业组织机构代码：AGENT_CORP_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*  394 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && (INDIV_ORG_CODE.length() != 9) && (INDIV_ORG_CODE.length() != 18) && (!INDIV_ORG_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  397 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个体工商户组织机构代码：INDIV_ORG_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*      */     
/*  400 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/*  401 */       if ((PAY_ORG_CODE == null) || ("".equals(PAY_ORG_CODE)) || ((PAY_ORG_CODE.length() != 9) && (PAY_ORG_CODE.length() != 18))) {
/*  402 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构结汇”时  支付机构组织机构代码：PAY_ORG_CODE不允许为空，且长度应为9位或18位。");
/*      */       }
/*  404 */       if (!JHZJXTCodeEnum.CODE_02.getCode().equals(SALEFX_SETTLE_CODE)) {
/*  405 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为通过支付机构的结汇时，结汇资金形态:SALEFX_SETTLE_CODE 限定为“02-汇入资金（包括外汇票据）。");
/*      */       }
/*      */     }
/*  408 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/*  409 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“资本项目结汇”时  外汇局批件号/备案表号/业务编号：CAPITALNO  不允许为空。");
/*      */     }
/*      */     
/*  412 */     if (StringUtils.isBlank(LCY_ACCT_NO)) {
/*  413 */       if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */       {
/*  415 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态为“账户资金”时或者业务类型为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/*      */       }
/*  417 */     } else if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && 
/*  418 */       (!JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */     {
/*  420 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态不为“账户资金”时或者业务类型不为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqDeleteIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  439 */     NullVilator nv = new NullVilator();
/*  440 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  441 */     nv.hasNull();
/*  442 */     String CANCEL_REASON = (String)nv.PopMap(PARAMETERS, "CANCEL_REASON", "撤销原因");
/*      */     
/*  444 */     String CANCEL_REMARK = (String)PARAMETERS.get("CANCEL_REMARK");
/*  445 */     nv.hasNull();
/*  446 */     if ((JSHCancelResonEnum.CODE_06.getCode().equals(CANCEL_REASON)) && ((null == CANCEL_REMARK) || ("".equals(CANCEL_REMARK))))
/*      */     {
/*  448 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "撤销原因为“其他”时  撤销说明不允许为空");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpOtherIndividualFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  461 */     NullVilator nv = new NullVilator();
/*  462 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求参数循环体");
/*  463 */     nv.hasNull();
/*  464 */     for (int i = 0; i < ROWSET.size(); i++) {
/*  465 */       Map row = (Map)ROWSET.get(i);
/*  466 */       valiteWGReqMakeUpOtherIndividualFXSEInfoRow(row);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpOtherIndividualFXSEInfoRow(Map row)
/*      */     throws DataCheckException
/*      */   {
/*  479 */     NullVilator nv = new NullVilator();
/*  480 */     String BIZ_TYPE_CODE = (String)nv.PopMap(row, "BIZ_TYPE_CODE", "业务类型代码");
/*      */     
/*  482 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/*  483 */     String IDCODE = (String)row.get("IDCODE");
/*  484 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/*  485 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/*  486 */     String PERSON_NAME = (String)nv.PopMap(row, "PERSON_NAME", "姓名");
/*      */     
/*      */ 
/*  489 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/*  490 */     String SALEFX_AMT = (String)nv.PopMap(row, "SALEFX_AMT", "结汇金额");
/*      */     
/*  492 */     String SALEFX_SETTLE_CODE = (String)nv.PopMap(row, "SALEFX_SETTLE_CODE", "结汇资金形态代码");
/*      */     
/*  494 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/*      */     
/*      */ 
/*  497 */     String BIZ_TX_TIME = (String)nv.PopMap(row, "BIZ_TX_TIME", "业务办理时间");
/*      */     
/*  499 */     String REIN_REASON_CODE = (String)nv.PopMap(row, "REIN_REASON_CODE", "补录原因代码");
/*      */     
/*  501 */     String REIN_REMARK = (String)row.get("REIN_REMARK");
/*      */     
/*  503 */     nv.hasNull();
/*  504 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  505 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*  507 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  508 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*  510 */     if (!BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*  511 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 必须是 占额度的结汇");
/*      */     }
/*  513 */     if ((StringUtils.isBlank(PERSON_NAME)) || (!PERSON_NAME.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+")))
/*      */     {
/*      */ 
/*  516 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "姓名格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
/*      */     }
/*  518 */     if (!Arrays.asList(new String[] { CertTypeEnum.TYPE_01.getCode(), CertTypeEnum.TYPE_04.getCode(), CertTypeEnum.TYPE_05.getCode(), CertTypeEnum.TYPE_06.getCode(), CertTypeEnum.TYPE_07.getCode(), CertTypeEnum.TYPE_09.getCode() }).contains(IDTYPE_CODE))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  525 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "占用额度的结汇信息补录 证件类型 必须是 身份证/外国护照/港澳通行证/台湾通行证/中国护照/外国人永久居留证！");
/*      */     }
/*  527 */     if ((CertTypeEnum.TYPE_09.getCode().equals(IDTYPE_CODE)) && ((ADD_IDCODE == null) || (!ADD_IDCODE.matches("([A-Z]|[0-9]|[一-龥])+"))))
/*      */     {
/*      */ 
/*  530 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "当证件类型为“中国护照”时,补充证件号码不允许为空，需填写境外永久居留证号码；只允许输入大写字母和数字、中文。");
/*      */     }
/*  532 */     valitTXCCY(TXCCY);
/*      */     
/*  534 */     if ((SALEFX_AMT == null) || ("".equals(SALEFX_AMT)) || (parseDouble(SALEFX_AMT) <= 0.0D))
/*      */     {
/*  536 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/*  540 */     if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (StringUtils.isBlank(LCY_ACCT_NO)))
/*  541 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当结汇资金形态为“账户资金”时，个人外汇账户账号不能为空");
/*  542 */     if ((!JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (StringUtils.isNotBlank(LCY_ACCT_NO))) {
/*  543 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当结汇资金形态不为“账户资金”时，个人外汇账户账号必须为空");
/*      */     }
/*  545 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*  546 */     long currentTime = 0L;
/*  547 */     long BIZ_TX_TIME_L = 0L;
/*      */     try {
/*  549 */       currentTime = sdf.parse(sdf.format(new Date())).getTime();
/*  550 */       BIZ_TX_TIME_L = sdf.parse(BIZ_TX_TIME.substring(0, 10)).getTime();
/*      */     } catch (ParseException e) {
/*  552 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
/*      */     }
/*  554 */     if ((BIZ_TX_TIME.length() < 10) || (currentTime < BIZ_TX_TIME_L))
/*      */     {
/*  556 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理时间:BIZ_TX_TIME不能大于当前日期并且格式必须满足：yyyy-MM-dd");
/*      */     }
/*  558 */     if (("04".equals(REIN_REASON_CODE)) && ((REIN_REMARK == null) || ("".equals(REIN_REMARK)))) {
/*  559 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 当补录原因为“其他”时  补录说明不允许为空");
/*      */     }
/*      */     
/*      */ 
/*  563 */     String SALEFX_TX_CODE = (String)nv.PopMap(row, "SALEFX_TX_CODE", "结汇资金属性代码");
/*      */     
/*  565 */     validateJHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, SALEFX_TX_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpIndividualTradeFXSEInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  576 */     NullVilator nv = new NullVilator();
/*  577 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  578 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求参数循环体");
/*  579 */     nv.hasNull();
/*  580 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*  581 */     nv.hasNull();
/*  582 */     if (BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*  583 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 不能是 占额度的结汇");
/*      */     }
/*  585 */     for (int i = 0; i < ROWSET.size(); i++) {
/*  586 */       Map row = (Map)ROWSET.get(i);
/*  587 */       valiteWGReqMakeUpIndividualTradeFXSEInfoRow(row, BIZ_TYPE_CODE, i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpIndividualTradeFXSEInfoRow(Map row, String BIZ_TYPE_CODE, int i)
/*      */     throws DataCheckException
/*      */   {
/*  600 */     NullVilator nv = new NullVilator();
/*  601 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/*  602 */     String IDCODE = (String)nv.PopMap(row, "IDCODE", "证件号码");
/*  603 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/*  604 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/*  605 */     String PERSON_NAME = (String)nv.PopMap(row, "PERSON_NAME", "姓名");
/*  606 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/*  607 */     String SALEFX_AMT = parseAmt((String)nv.PopMap(row, "SALEFX_AMT", "结汇金额"));
/*  608 */     String SALEFX_SETTLE_CODE = (String)nv.PopMap(row, "SALEFX_SETTLE_CODE", "结汇资金形态");
/*  609 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/*  610 */     String AGENT_CORP_CODE = (String)row.get("AGENT_CORP_CODE");
/*  611 */     String AGENT_CORP_NAME = (String)row.get("AGENT_CORP_NAME");
/*  612 */     String INDIV_ORG_CODE = (String)row.get("INDIV_ORG_CODE");
/*  613 */     String INDIV_ORG_NAME = (String)row.get("INDIV_ORG_NAME");
/*  614 */     String PAY_ORG_CODE = (String)row.get("PAY_ORG_CODE");
/*  615 */     String CAPITALNO = (String)row.get("CAPITALNO");
/*  616 */     String BIZ_TX_TIME = (String)nv.PopMap(row, "BIZ_TX_TIME", "业务办理时间");
/*  617 */     String REMARK = (String)row.get("REMARK");
/*  618 */     String REIN_REASON_CODE = (String)nv.PopMap(row, "REIN_REASON_CODE", "补录原因代码");
/*  619 */     String REIN_REMARK = (String)row.get("REIN_REMARK");
/*      */     
/*  621 */     nv.hasNull();
/*  622 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  623 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*  625 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  626 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*  628 */     if ((StringUtils.isBlank(PERSON_NAME)) || (!PERSON_NAME.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+")))
/*      */     {
/*      */ 
/*  631 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "姓名格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
/*      */     }
/*  633 */     if ((CertTypeEnum.TYPE_09.getCode().equals(IDTYPE_CODE)) && ((ADD_IDCODE == null) || (!ADD_IDCODE.matches("([A-Z]|[0-9]|[一-龥])+"))))
/*      */     {
/*      */ 
/*  636 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当证件类型为“中国护照”时,补充证件号码不允许为空，需填写境外永久居留证号码；只允许输入大写字母和数字、中文。");
/*      */     }
/*  638 */     valitTXCCY(TXCCY);
/*  639 */     if ((SALEFX_AMT == null) || ("".equals(SALEFX_AMT)) || (parseDouble(SALEFX_AMT) <= 0.0D))
/*      */     {
/*  641 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  661 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (AGENT_CORP_CODE.length() != 9) && (AGENT_CORP_CODE.length() != 18) && (!AGENT_CORP_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  664 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "代理企业组织机构代码：AGENT_CORP_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*  666 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && (INDIV_ORG_CODE.length() != 9) && (INDIV_ORG_CODE.length() != 18) && (!INDIV_ORG_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  669 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个体工商户组织机构代码：INDIV_ORG_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*      */     
/*  672 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/*  673 */       if ((PAY_ORG_CODE == null) || ("".equals(PAY_ORG_CODE)) || ((PAY_ORG_CODE.length() != 9) && (PAY_ORG_CODE.length() != 18))) {
/*  674 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构结汇”时  支付机构组织机构代码：PAY_ORG_CODE不允许为空，且长度应为9位或18位。");
/*      */       }
/*  676 */       if (!JHZJXTCodeEnum.CODE_02.getCode().equals(SALEFX_SETTLE_CODE)) {
/*  677 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型为通过支付机构的结汇时，结汇资金形态:SALEFX_SETTLE_CODE 限定为“02-汇入资金（包括外汇票据）。");
/*      */       }
/*      */     }
/*  680 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/*  681 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“资本项目结汇”时  外汇局批件号/备案表号/业务编号：CAPITALNO  不允许为空。");
/*      */     }
/*      */     
/*      */ 
/*  685 */     if (StringUtils.isBlank(LCY_ACCT_NO)) {
/*  686 */       if ((JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */       {
/*  688 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态为“账户资金”时或者业务类型为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/*      */       }
/*  690 */     } else if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && 
/*  691 */       (!JHZJXTCodeEnum.CODE_03.getCode().equals(SALEFX_SETTLE_CODE)) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE)))
/*      */     {
/*  693 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "结汇资金形态不为“账户资金”时或者业务类型不为“个人贸易结汇”时,个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*      */ 
/*  697 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*  698 */     long currentTime = 0L;
/*  699 */     long BIZ_TX_TIME_L = 0L;
/*      */     try {
/*  701 */       currentTime = sdf.parse(sdf.format(new Date())).getTime();
/*  702 */       BIZ_TX_TIME_L = sdf.parse(BIZ_TX_TIME.substring(0, 10)).getTime();
/*      */     } catch (ParseException e) {
/*  704 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
/*      */     }
/*  706 */     if ((BIZ_TX_TIME.length() < 10) || (currentTime < BIZ_TX_TIME_L))
/*      */     {
/*  708 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), " 业务办理时间:BIZ_TX_TIME不能大于当前日期并且格式必须满足：yyyy-MM-dd");
/*      */     }
/*  710 */     if ((JSHDataImportResonEnum.CODE_04.getCode().equals(REIN_REASON_CODE)) && ((REIN_REMARK == null) || ("".equals(REIN_REMARK)))) {
/*  711 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 当补录原因为“其他”时  补录说明:REIN_REMARK不允许为空");
/*      */     }
/*  713 */     if ((BizTypeEnum.TYPE_03.getCode().equals(BIZ_TYPE_CODE)) && (!IDTYPE_CODE.equals("10")) && ((REMARK == null) || ("".equals(REMARK))))
/*      */     {
/*  715 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“提供凭证的经常项目其他结汇”,并且证件类型不是“外交官证”时 备注:REMARK  不允许为空");
/*      */     }
/*      */     
/*  718 */     String SALEFX_TX_CODE = (String)nv.PopMap(row, "SALEFX_TX_CODE", "结汇资金属性代码");
/*      */     
/*  720 */     validateJHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, SALEFX_TX_CODE);
/*      */   }
/*      */   
/*      */   public static void valitTXCCY(String TXCCY) throws DataCheckException
/*      */   {
/*  725 */     if ("CNY".equals(TXCCY)) {
/*  726 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种:TXCCY不能为“人民币”");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqQueryIndividualFXSAQuota(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  738 */     NullVilator nv = new NullVilator();
/*  739 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  740 */     nv.hasNull();
/*  741 */     String IDTYPE_CODE = (String)nv.PopMap(PARAMETERS, "IDTYPE_CODE", "证件类型代码");
/*  742 */     String CTYCODE = (String)nv.PopMap(PARAMETERS, "CTYCODE", "国家/地区代码");
/*  743 */     String IDCODE = (String)nv.PopMap(PARAMETERS, "IDCODE", "证件号码");
/*  744 */     nv.hasNull();
/*  745 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  746 */     valitCpRelate(IDTYPE_CODE, CTYCODE, null, "00");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddLimitedIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  757 */     NullVilator nv = new NullVilator();
/*  758 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  759 */     nv.hasNull();
/*  760 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*  761 */     String IDTYPE_CODE = (String)nv.PopMap(PARAMETERS, "IDTYPE_CODE", "证件类型代码");
/*  762 */     String IDCODE = (String)nv.PopMap(PARAMETERS, "IDCODE", "证件号码");
/*  763 */     String CTYCODE = (String)nv.PopMap(PARAMETERS, "CTYCODE", "国家/地区代码");
/*  764 */     String PERSON_NAME = (String)nv.PopMap(PARAMETERS, "PERSON_NAME", "姓名");
/*  765 */     String TXCCY = (String)nv.PopMap(PARAMETERS, "TXCCY", "币种");
/*  766 */     String PURFX_AMT = parseAmt((String)nv.PopMap(PARAMETERS, "PURFX_AMT", "购汇金额"));
/*  767 */     String FCY_ACCT_AMT = parseAmt((String)nv.PopMap(PARAMETERS, "FCY_ACCT_AMT", "存入个人外汇账户金额"));
/*  768 */     String ADD_IDCODE = (String)PARAMETERS.get("ADD_IDCODE");
/*  769 */     String LCY_ACCT_NO = (String)PARAMETERS.get("LCY_ACCT_NO");
/*  770 */     String PURFX_CASH_AMT = parseAmt((String)PARAMETERS.get("PURFX_CASH_AMT"));
/*  771 */     String FCY_REMIT_AMT = parseAmt((String)PARAMETERS.get("FCY_REMIT_AMT"));
/*  772 */     String TCHK_AMT = parseAmt((String)PARAMETERS.get("TCHK_AMT"));
/*  773 */     nv.hasNull();
/*  774 */     if (!BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*  775 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 必须是 占额度的结汇");
/*      */     }
/*  777 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  778 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*  780 */     String bizChnlCode = (String)nv.PopMap(PARAMETERS, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  781 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*  783 */     if ("CNY".equals(TXCCY)) {
/*  784 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种：TXCCY 不能为“人民币”");
/*      */     }
/*  786 */     if ((PURFX_AMT == null) || ("".equals(PURFX_AMT)) || (parseDouble(PURFX_AMT) <= 0.0D))
/*      */     {
/*  788 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/*  792 */     if ((parseDouble(FCY_ACCT_AMT) > 0.0D) && (StringUtils.isBlank(LCY_ACCT_NO)))
/*  793 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”大于零时，个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/*  794 */     if ((parseDouble(FCY_ACCT_AMT) <= 0.0D) && (StringUtils.isNotBlank(LCY_ACCT_NO))) {
/*  795 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”小于或等于零时，个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*  798 */     if ((StringUtils.isBlank(PERSON_NAME)) || (!PERSON_NAME.matches("(([一-龥]|[a-z]|[A-Z]|\\.\\-\\·)+\\s*)+")))
/*      */     {
/*      */ 
/*  801 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "姓名格式要求：姓名不能为空并且只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格");
/*      */     }
/*      */     
/*  804 */     if ((StringUtils.isBlank(PURFX_CASH_AMT)) || (parseDouble(PURFX_CASH_AMT) < 0.0D)) {
/*  805 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450061.getCode(), "购汇提钞金额:PURFX_CASH_AMT 应大于或等于零");
/*      */     }
/*      */     
/*      */ 
/*  809 */     if ((StringUtils.isBlank(FCY_REMIT_AMT)) || (parseDouble(FCY_REMIT_AMT) < 0.0D)) {
/*  810 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*  814 */     if ((StringUtils.isBlank(FCY_ACCT_AMT)) || (parseDouble(FCY_ACCT_AMT) < 0.0D)) {
/*  815 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "存入个人外汇账户金额:FCY_ACCT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*  819 */     if ((StringUtils.isBlank(TCHK_AMT)) || (parseDouble(TCHK_AMT) < 0.0D)) {
/*  820 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "旅行支票金额:TCHK_AMT 应大于等于零。");
/*      */     }
/*      */     
/*  823 */     BigDecimal totalamt_big = new BigDecimal("0");
/*  824 */     if (StringUtils.isNotBlank(PURFX_CASH_AMT)) {
/*  825 */       totalamt_big = totalamt_big.add(new BigDecimal(PURFX_CASH_AMT));
/*      */     }
/*  827 */     if (StringUtils.isNotBlank(FCY_REMIT_AMT)) {
/*  828 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_REMIT_AMT));
/*      */     }
/*  830 */     if (StringUtils.isNotBlank(TCHK_AMT)) {
/*  831 */       totalamt_big = totalamt_big.add(new BigDecimal(TCHK_AMT));
/*      */     }
/*  833 */     if (StringUtils.isNotBlank(FCY_ACCT_AMT)) {
/*  834 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_ACCT_AMT));
/*      */     }
/*  836 */     BigDecimal purfx_amt_big = new BigDecimal(PURFX_AMT);
/*  837 */     if (totalamt_big.subtract(purfx_amt_big).doubleValue() != 0.0D) {
/*  838 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "占用额度的个人购汇信息录入:【购汇金额】与【提钞金额】、【汇出资金（包括外汇票据）金额】、【存入个人外汇账户金额】、【旅行支票金额】之和不一致，请核对");
/*      */     }
/*      */     
/*  841 */     String PURFX_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "PURFX_TYPE_CODE", "购汇资金属性");
/*  842 */     validateGHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, PURFX_TYPE_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddUnLimitedIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/*  853 */     NullVilator nv = new NullVilator();
/*  854 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "PARAMETERS");
/*  855 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求参数循环体");
/*  856 */     nv.hasNull();
/*  857 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*  858 */     nv.hasNull();
/*  859 */     if (BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/*  860 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), " 业务类型代码:BIZ_TYPE_CODE 不能是 占额度的结汇");
/*      */     }
/*  862 */     for (int i = 0; i < ROWSET.size(); i++) {
/*  863 */       Map row = (Map)ROWSET.get(i);
/*  864 */       valiteWGReqAddUnLimitedIndividualFXSAInfoRow(row, BIZ_TYPE_CODE, i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqAddUnLimitedIndividualFXSAInfoRow(Map row, String BIZ_TYPE_CODE, int i)
/*      */     throws DataCheckException
/*      */   {
/*  877 */     NullVilator nv = new NullVilator();
/*  878 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/*  879 */     String IDCODE = (String)nv.PopMap(row, "IDCODE", "证件号码");
/*  880 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/*  881 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/*  882 */     String PURFX_AMT = parseAmt((String)nv.PopMap(row, "PURFX_AMT", "购汇金额"));
/*  883 */     String BIZ_TX_CHNL_CODE = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  884 */     String FCY_ACCT_AMT = parseAmt((String)row.get("FCY_ACCT_AMT"));
/*  885 */     String BIZ_TX_TIME = (String)row.get("BIZ_TX_TIME");
/*  886 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/*  887 */     String PAY_ORG_CODE = (String)row.get("PAY_ORG_CODE");
/*  888 */     String AGENT_CORP_NAME = (String)row.get("AGENT_CORP_NAME");
/*  889 */     String AGENT_CORP_CODE = (String)row.get("AGENT_CORP_CODE");
/*  890 */     String INDIV_ORG_CODE = (String)row.get("INDIV_ORG_CODE");
/*  891 */     String INDIV_ORG_NAME = (String)row.get("INDIV_ORG_NAME");
/*  892 */     String CAPITALNO = (String)row.get("CAPITALNO");
/*  893 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/*  894 */     String REMARK = (String)row.get("REMARK");
/*      */     
/*  896 */     String PURFX_CASH_AMT = parseAmt((String)row.get("PURFX_CASH_AMT"));
/*  897 */     String FCY_REMIT_AMT = parseAmt((String)row.get("FCY_REMIT_AMT"));
/*  898 */     String TCHK_AMT = parseAmt((String)row.get("TCHK_AMT"));
/*      */     
/*  900 */     nv.hasNull();
/*  901 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*  902 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/*  904 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/*  905 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*  907 */     if ("CNY".equals(TXCCY)) {
/*  908 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种不能为“人民币”");
/*      */     }
/*  910 */     if ((PURFX_AMT == null) || ("".equals(PURFX_AMT)) || (parseDouble(PURFX_AMT) <= 0.0D))
/*      */     {
/*  912 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/*  916 */     if ((StringUtils.isBlank(LCY_ACCT_NO)) && ((parseDouble(FCY_ACCT_AMT) > 0.0D) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))))
/*  917 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”大于零或当业务类型为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/*  918 */     if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && (parseDouble(FCY_ACCT_AMT) <= 0.0D) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))) {
/*  919 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”小于等于零时或者当业务类型不为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  937 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (AGENT_CORP_CODE.length() != 9) && (AGENT_CORP_CODE.length() != 18) && (!AGENT_CORP_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  940 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "代理企业组织机构代码：AGENT_CORP_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*  942 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && (INDIV_ORG_CODE.length() != 9) && (INDIV_ORG_CODE.length() != 18) && (!INDIV_ORG_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/*  945 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个体工商户组织机构代码：INDIV_ORG_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*      */     
/*  948 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/*  949 */       if ((PAY_ORG_CODE == null) || ("".equals(PAY_ORG_CODE)) || ((PAY_ORG_CODE.length() != 9) && (PAY_ORG_CODE.length() != 18))) {
/*  950 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构结汇”时  支付机构组织机构代码：PAY_ORG_CODE不允许为空，且长度应为9位或18位。");
/*      */       }
/*  952 */       if (!BizChannelTypeEnum.TYPE_32.getCode().equals(BIZ_TX_CHNL_CODE)) {
/*  953 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构购汇”时  业务办理渠道代码：BIZ_TX_CHNL_CODE 必须为“32-支付机构”。");
/*      */       }
/*      */     }
/*  956 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/*  957 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“资本项目购汇”时  外汇局批件号/备案表号/业务编号：CAPITALNO  不允许为空。");
/*      */     }
/*      */     
/*      */ 
/*  961 */     if ((BizTypeEnum.TYPE_03.getCode().equals(BIZ_TYPE_CODE)) && (CertTypeEnum.TYPE_01.getCode().equals(IDTYPE_CODE)) && (StringUtils.isBlank(REMARK))) {
/*  962 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "业务类型“提供凭证的经常项目其他购汇”并且对应交易主体是境内个人，备注栏不允许为空");
/*      */     }
/*      */     
/*      */ 
/*  966 */     if ((StringUtils.isBlank(PURFX_CASH_AMT)) || (parseDouble(PURFX_CASH_AMT) < 0.0D)) {
/*  967 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450061.getCode(), "购汇提钞金额:PURFX_CASH_AMT 应大于或等于零");
/*      */     }
/*      */     
/*      */ 
/*  971 */     if ((StringUtils.isBlank(FCY_REMIT_AMT)) || (parseDouble(FCY_REMIT_AMT) < 0.0D)) {
/*  972 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应大于等于零。");
/*      */     }
/*  974 */     if ((BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) && (!StringUtils.equals(FCY_REMIT_AMT, PURFX_AMT))) {
/*  975 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450009.getCode(), "当业务类型为“通过支付机构的购汇”时，汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应等于购汇金额。");
/*      */     }
/*      */     
/*      */ 
/*  979 */     if ((StringUtils.isBlank(FCY_ACCT_AMT)) || (parseDouble(FCY_ACCT_AMT) < 0.0D)) {
/*  980 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "存入个人外汇账户金额:FCY_ACCT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*  984 */     if ((StringUtils.isBlank(TCHK_AMT)) || (parseDouble(TCHK_AMT) < 0.0D)) {
/*  985 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "旅行支票金额:TCHK_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*  989 */     BigDecimal b_PURFX_CASH_AMT = null;
/*  990 */     BigDecimal b_FCY_REMIT_AMT = null;
/*  991 */     BigDecimal b_TCHK_AMT = null;
/*  992 */     BigDecimal b_FCY_ACCT_AMT = null;
/*  993 */     BigDecimal b_PURFX_AMT = new BigDecimal(PURFX_AMT);
/*  994 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/*  995 */       b_FCY_REMIT_AMT = b_PURFX_AMT;
/*  996 */       b_PURFX_CASH_AMT = new BigDecimal("0");
/*  997 */       b_TCHK_AMT = new BigDecimal("0");
/*  998 */       b_FCY_ACCT_AMT = new BigDecimal("0");
/*      */     } else {
/* 1000 */       b_PURFX_CASH_AMT = new BigDecimal(PURFX_CASH_AMT);
/* 1001 */       b_FCY_REMIT_AMT = new BigDecimal(FCY_REMIT_AMT);
/* 1002 */       b_TCHK_AMT = new BigDecimal(TCHK_AMT);
/* 1003 */       b_FCY_ACCT_AMT = new BigDecimal(FCY_ACCT_AMT);
/*      */     }
/*      */     
/* 1006 */     if (!b_PURFX_CASH_AMT.add(b_FCY_REMIT_AMT).add(b_TCHK_AMT).add(b_FCY_ACCT_AMT).equals(b_PURFX_AMT)) {
/* 1007 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "不占用额度的个人购汇信息录入:【购汇金额】与【提钞金额】、【汇出资金（包括外汇票据）金额】、【存入个人外汇账户金额】、【旅行支票金额】之和不一致，请核对");
/*      */     }
/*      */     
/* 1010 */     Date bizDate = null;
/* 1011 */     Date sysDate = null;
/*      */     try {
/* 1013 */       bizDate = DateTimeUtil.toSQLDate(BIZ_TX_TIME, "yyyy-MM-dd");
/* 1014 */       sysDate = DateTimeUtil.toSQLDate(DateTimeUtil.getCurrentDate(), "yyyy-MM-dd");
/*      */     } catch (Exception e) {
/* 1016 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务实际办理日期格式必须为“yyyy-MM-dd HH:mm:ss”!");
/*      */     }
/*      */     
/*      */ 
/* 1020 */     if (DateTimeUtil.calcTwoDateDays(bizDate, sysDate) != 0) {
/* 1021 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务办理日期必须为当前日期!");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1026 */     String PURFX_TYPE_CODE = (String)nv.PopMap(row, "PURFX_TYPE_CODE", "购汇资金属性");
/* 1027 */     validateGHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, PURFX_TYPE_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqQueryIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1039 */     NullVilator nv = new NullVilator();
/*      */     
/* 1041 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "请求参数");
/* 1042 */     String REFNO = (String)PARAMETERS.get("REFNO");
/* 1043 */     String IDTYPE_CODE = (String)PARAMETERS.get("IDTYPE_CODE");
/* 1044 */     String IDCODE = (String)PARAMETERS.get("IDCODE");
/* 1045 */     String CTYCODE = (String)PARAMETERS.get("CTYCODE");
/* 1046 */     String BIZ_TX_TIME = (String)PARAMETERS.get("BIZ_TX_TIME");
/*      */     
/*      */ 
/* 1049 */     if (StringUtils.isNotBlank(BIZ_TX_TIME)) {
/* 1050 */       long currentTime = System.currentTimeMillis();
/*      */       long bizTxTime;
/*      */       try {
/* 1053 */         bizTxTime = new SimpleDateFormat("yyyy-MM-dd").parse(BIZ_TX_TIME).getTime();
/*      */       } catch (ParseException e) {
/* 1055 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
/*      */       }
/* 1057 */       if (bizTxTime > currentTime) {
/* 1058 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), "业务实际办理日期:BIZ_TX_TIME 不能大于当前日期。");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1063 */     if (((StringUtils.isNotBlank(IDTYPE_CODE)) && ((StringUtils.isBlank(IDCODE)) || (StringUtils.isBlank(CTYCODE)))) || ((StringUtils.isNotBlank(IDCODE)) && ((StringUtils.isBlank(IDTYPE_CODE)) || (StringUtils.isBlank(CTYCODE)))) || ((StringUtils.isNotBlank(CTYCODE)) && ((StringUtils.isBlank(IDCODE)) || (StringUtils.isBlank(IDTYPE_CODE)))))
/*      */     {
/*      */ 
/* 1066 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "证件类型、证件号码、国家/地区三项中如输入一项，其他两项都不能为空。");
/*      */     }
/*      */     
/*      */ 
/* 1070 */     if ((StringUtils.isBlank(REFNO)) && (StringUtils.isBlank(IDTYPE_CODE)) && (StringUtils.isBlank(IDCODE)) && (StringUtils.isBlank(CTYCODE)) && (StringUtils.isBlank(BIZ_TX_TIME)))
/*      */     {
/* 1072 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "查询条件不能全部为空。");
/*      */     }
/*      */     
/*      */ 
/* 1076 */     if (StringUtils.isNotEmpty(IDTYPE_CODE)) {
/* 1077 */       valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqModifyIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1091 */     NullVilator nv = new NullVilator();
/*      */     
/* 1093 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "请求参数");
/* 1094 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/* 1095 */     String TXCCY = (String)nv.PopMap(PARAMETERS, "TXCCY", "币种");
/* 1096 */     String PURFX_AMT = parseAmt((String)nv.PopMap(PARAMETERS, "PURFX_AMT", "购汇金额"));
/* 1097 */     String FCY_ACCT_AMT = parseAmt((String)PARAMETERS.get("FCY_ACCT_AMT"));
/* 1098 */     String LCY_ACCT_NO = (String)PARAMETERS.get("LCY_ACCT_NO");
/* 1099 */     String PURFX_CASH_AMT = parseAmt((String)PARAMETERS.get("PURFX_CASH_AMT"));
/* 1100 */     String FCY_REMIT_AMT = parseAmt((String)PARAMETERS.get("FCY_REMIT_AMT"));
/* 1101 */     String TCHK_AMT = parseAmt((String)PARAMETERS.get("TCHK_AMT"));
/* 1102 */     String MOD_REASON_CODE = (String)nv.PopMap(PARAMETERS, "MOD_REASON_CODE", "修改原因代码");
/* 1103 */     String MODIFY_REMARK = (String)PARAMETERS.get("MODIFY_REMARK");
/* 1104 */     String BIZ_TX_TIME = (String)PARAMETERS.get("BIZ_TX_TIME");
/* 1105 */     String PAY_ORG_CODE = (String)PARAMETERS.get("PAY_ORG_CODE");
/* 1106 */     String AGENT_CORP_NAME = (String)PARAMETERS.get("AGENT_CORP_NAME");
/* 1107 */     String AGENT_CORP_CODE = (String)PARAMETERS.get("AGENT_CORP_CODE");
/* 1108 */     String INDIV_ORG_CODE = (String)PARAMETERS.get("INDIV_ORG_CODE");
/* 1109 */     String INDIV_ORG_NAME = (String)PARAMETERS.get("INDIV_ORG_NAME");
/* 1110 */     String CAPITALNO = (String)PARAMETERS.get("CAPITALNO");
/* 1111 */     nv.hasNull();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1118 */     if ("CNY".equals(TXCCY)) {
/* 1119 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "币种:TXCCY 不能为“人民币”。");
/*      */     }
/*      */     
/*      */ 
/* 1123 */     if (parseDouble(PURFX_AMT) <= 0.0D) {
/* 1124 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额:PURFX_AMT 大于0。");
/*      */     }
/*      */     
/*      */ 
/* 1128 */     if ((StringUtils.isNotBlank(PURFX_CASH_AMT)) && (parseDouble(PURFX_CASH_AMT) < 0.0D)) {
/* 1129 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450061.getCode(), "购汇提钞金额: PURFX_CASH_AMT 大于0。");
/*      */     }
/*      */     
/*      */ 
/* 1133 */     if ((StringUtils.isNotBlank(FCY_REMIT_AMT)) && (parseDouble(FCY_REMIT_AMT) < 0.0D)) {
/* 1134 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450008.getCode(), "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 大于等于0。");
/*      */     }
/*      */     
/*      */ 
/* 1138 */     if ((GHDataModifyResonEnum.CODE_06.getCode().equals(MOD_REASON_CODE)) && (StringUtils.isBlank(MODIFY_REMARK))) {
/* 1139 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "修改原因为“其他”时, 修改说明:MODIFY_REMARK 不允许为空。");
/*      */     }
/*      */     
/* 1142 */     if ("CNY".equals(TXCCY)) {
/* 1143 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "参见银行币种代码表，币种不能为“人民币”");
/*      */     }
/* 1145 */     if ((PURFX_AMT == null) || ("".equals(PURFX_AMT)) || (parseDouble(PURFX_AMT) <= 0.0D))
/*      */     {
/* 1147 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额：SALEFX_AMT 必须大于0");
/*      */     }
/*      */     
/*      */ 
/* 1151 */     if ((StringUtils.isBlank(LCY_ACCT_NO)) && ((parseDouble(FCY_ACCT_AMT) > 0.0D) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))))
/* 1152 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”大于零或当业务类型为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/* 1153 */     if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && (parseDouble(FCY_ACCT_AMT) <= 0.0D) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))) {
/* 1154 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”小于等于零时或者当业务类型不为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1177 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (AGENT_CORP_CODE.length() != 9) && (AGENT_CORP_CODE.length() != 18) && (!AGENT_CORP_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/* 1180 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "代理企业组织机构代码：AGENT_CORP_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/* 1182 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && (INDIV_ORG_CODE.length() != 9) && (INDIV_ORG_CODE.length() != 18) && (!INDIV_ORG_CODE.matches("([a-z]|[A-Z]|[0-9])*")))
/*      */     {
/*      */ 
/* 1185 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "个体工商户组织机构代码：INDIV_ORG_CODE只允许输入数字或字母，长度应为9位或18位");
/*      */     }
/*      */     
/* 1188 */     if ((BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) && (
/* 1189 */       (PAY_ORG_CODE == null) || ("".equals(PAY_ORG_CODE)) || ((PAY_ORG_CODE.length() != 9) && (PAY_ORG_CODE.length() != 18)))) {
/* 1190 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构结汇”时  支付机构组织机构代码：PAY_ORG_CODE不允许为空，且长度应为9位或18位。");
/*      */     }
/*      */     
/* 1193 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/* 1194 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“资本项目购汇”时  外汇局批件号/备案表号/业务编号：CAPITALNO  不允许为空。");
/*      */     }
/* 1196 */     if (StringUtils.isNotBlank(BIZ_TX_TIME)) {
/* 1197 */       Date bizDate = null;
/* 1198 */       Date sysDate = null;
/*      */       try {
/* 1200 */         bizDate = DateTimeUtil.toSQLDate(BIZ_TX_TIME, "yyyy-MM-dd");
/* 1201 */         sysDate = DateTimeUtil.toSQLDate(DateTimeUtil.getCurrentDate(), "yyyy-MM-dd");
/*      */       } catch (Exception e) {
/* 1203 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务实际办理日期格式必须为“yyyy-MM-dd”!");
/*      */       }
/*      */       
/*      */ 
/* 1207 */       if (DateTimeUtil.calcTwoDateDays(bizDate, sysDate) != 0) {
/* 1208 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务办理日期必须为当前日期!");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1214 */     BigDecimal totalamt_big = new BigDecimal("0");
/* 1215 */     if (StringUtils.isNotBlank(PURFX_CASH_AMT)) {
/* 1216 */       totalamt_big = totalamt_big.add(new BigDecimal(PURFX_CASH_AMT));
/*      */     }
/* 1218 */     if (StringUtils.isNotBlank(FCY_REMIT_AMT)) {
/* 1219 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_REMIT_AMT));
/*      */     }
/* 1221 */     if (StringUtils.isNotBlank(TCHK_AMT)) {
/* 1222 */       totalamt_big = totalamt_big.add(new BigDecimal(TCHK_AMT));
/*      */     }
/* 1224 */     if (StringUtils.isNotBlank(FCY_ACCT_AMT)) {
/* 1225 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_ACCT_AMT));
/*      */     }
/* 1227 */     BigDecimal purfx_amt_big = new BigDecimal(PURFX_AMT);
/* 1228 */     if (totalamt_big.subtract(purfx_amt_big).doubleValue() != 0.0D) {
/* 1229 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "个人购汇信息修改:【购汇金额】与【提钞金额】、【汇出资金（包括外汇票据）金额】、【存入个人外汇账户金额】、【旅行支票金额】之和不一致，请核对");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqDeleteIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1242 */     NullVilator nv = new NullVilator();
/*      */     
/* 1244 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "请求参数");
/* 1245 */     String CANCEL_REASON = (String)nv.PopMap(PARAMETERS, "CANCEL_REASON", "撤销原因");
/* 1246 */     String CANCEL_REMARK = (String)PARAMETERS.get("CANCEL_REMARK");
/* 1247 */     nv.hasNull();
/*      */     
/*      */ 
/* 1250 */     if ((JSHCancelResonEnum.CODE_06.getCode().equals(CANCEL_REASON)) && (StringUtils.isBlank(CANCEL_REMARK))) {
/* 1251 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "撤销原因为“其他”时, 撤销说明:CANCEL_REMARK 不允许为空。");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpOtherIndividualFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1265 */     NullVilator nv = new NullVilator();
/*      */     
/* 1267 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求循环体");
/* 1268 */     nv.hasNull();
/*      */     
/* 1270 */     for (int i = 0; i < ROWSET.size(); i++) {
/* 1271 */       Map ROW = (Map)ROWSET.get(i);
/* 1272 */       valiteWGReqMakeUpOtherIndividualFXSAInfoRow(ROW);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpOtherIndividualFXSAInfoRow(Map row)
/*      */     throws DataCheckException
/*      */   {
/* 1282 */     NullVilator nv = new NullVilator();
/*      */     
/* 1284 */     String BIZ_TYPE_CODE = (String)nv.PopMap(row, "BIZ_TYPE_CODE", "业务类型代码");
/* 1285 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/* 1286 */     String IDCODE = (String)nv.PopMap(row, "IDCODE", "证件号码");
/* 1287 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/* 1288 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/* 1289 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/* 1290 */     String PURFX_AMT = parseAmt((String)nv.PopMap(row, "PURFX_AMT", "购汇金额"));
/* 1291 */     String PURFX_CASH_AMT = parseAmt((String)nv.PopMap(row, "PURFX_CASH_AMT", "购汇提钞金额"));
/* 1292 */     String FCY_REMIT_AMT = parseAmt((String)nv.PopMap(row, "FCY_REMIT_AMT", "汇出资金（包括外汇票据）金额"));
/* 1293 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/* 1294 */     String FCY_ACCT_AMT = parseAmt((String)nv.PopMap(row, "FCY_ACCT_AMT", "存入个人外汇账户金额"));
/* 1295 */     String TCHK_AMT = parseAmt((String)nv.PopMap(row, "TCHK_AMT", "旅行支票金额"));
/* 1296 */     String BIZ_TX_TIME = (String)nv.PopMap(row, "BIZ_TX_TIME", "业务办理时间");
/* 1297 */     String REIN_REASON_CODE = (String)nv.PopMap(row, "REIN_REASON_CODE", "补录原因代码");
/* 1298 */     String REIN_REMARK = (String)row.get("REIN_REMARK");
/* 1299 */     String REMARK = (String)row.get("REMARK");
/*      */     
/* 1301 */     nv.hasNull();
/*      */     
/*      */ 
/* 1304 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*      */     
/*      */ 
/* 1307 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/* 1309 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/* 1310 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*      */ 
/* 1313 */     if (!BizTypeEnum.TYPE_01.getCode().equals(BIZ_TYPE_CODE)) {
/* 1314 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型:BIZ_TYPE_CODE 只能是“占用额度的购汇”。");
/*      */     }
/*      */     
/*      */ 
/* 1318 */     if ("CNY".equals(TXCCY)) {
/* 1319 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "币种:TXCCY 不能为“人民币”。");
/*      */     }
/*      */     
/*      */ 
/* 1323 */     if (parseDouble(PURFX_AMT) <= 0.0D) {
/* 1324 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额:PURFX_AMT 必须大于0。");
/*      */     }
/*      */     
/*      */ 
/* 1328 */     if ((StringUtils.isBlank(PURFX_CASH_AMT)) || (parseDouble(PURFX_CASH_AMT) < 0.0D)) {
/* 1329 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450061.getCode(), "购汇提钞金额:PURFX_CASH_AMT 应大于或等于零");
/*      */     }
/*      */     
/*      */ 
/* 1333 */     if ((StringUtils.isBlank(FCY_REMIT_AMT)) || (parseDouble(FCY_REMIT_AMT) < 0.0D)) {
/* 1334 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/* 1338 */     if ((StringUtils.isBlank(FCY_ACCT_AMT)) || (parseDouble(FCY_ACCT_AMT) < 0.0D)) {
/* 1339 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "存入个人外汇账户金额:FCY_ACCT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/* 1343 */     if ((StringUtils.isBlank(TCHK_AMT)) || (parseDouble(TCHK_AMT) < 0.0D)) {
/* 1344 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "旅行支票金额:TCHK_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1349 */     if ((parseDouble(FCY_ACCT_AMT) > 0.0D) && (StringUtils.isBlank(LCY_ACCT_NO)))
/* 1350 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”大于零时 ，个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/* 1351 */     if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && (parseDouble(FCY_ACCT_AMT) <= 0.0D)) {
/* 1352 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”小于或等于零时，个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*      */ 
/* 1356 */     long currentTime = System.currentTimeMillis();
/* 1357 */     long bizTxTime = 0L;
/*      */     try {
/* 1359 */       bizTxTime = new SimpleDateFormat("yyyy-MM-dd").parse(BIZ_TX_TIME).getTime();
/*      */     } catch (ParseException e) {
/* 1361 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换出错：" + e.getMessage());
/*      */     }
/* 1363 */     if (bizTxTime > currentTime) {
/* 1364 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), "业务实际办理日期:BIZ_TX_TIME 不能大于当前日期。");
/*      */     }
/*      */     
/*      */ 
/* 1368 */     if ((JSHDataImportResonEnum.CODE_04.getCode().equals(REIN_REASON_CODE)) && (StringUtils.isBlank(REIN_REMARK))) {
/* 1369 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当补录原因为“其他”时, 补录说明:REIN_REMARK 不允许为空 ");
/*      */     }
/*      */     
/* 1372 */     BigDecimal totalamt_big = new BigDecimal("0");
/* 1373 */     if (StringUtils.isNotBlank(PURFX_CASH_AMT)) {
/* 1374 */       totalamt_big = totalamt_big.add(new BigDecimal(PURFX_CASH_AMT));
/*      */     }
/* 1376 */     if (StringUtils.isNotBlank(FCY_REMIT_AMT)) {
/* 1377 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_REMIT_AMT));
/*      */     }
/* 1379 */     if (StringUtils.isNotBlank(TCHK_AMT)) {
/* 1380 */       totalamt_big = totalamt_big.add(new BigDecimal(TCHK_AMT));
/*      */     }
/* 1382 */     if (StringUtils.isNotBlank(FCY_ACCT_AMT)) {
/* 1383 */       totalamt_big = totalamt_big.add(new BigDecimal(FCY_ACCT_AMT));
/*      */     }
/* 1385 */     BigDecimal purfx_amt_big = new BigDecimal(PURFX_AMT);
/* 1386 */     if (totalamt_big.subtract(purfx_amt_big).doubleValue() != 0.0D) {
/* 1387 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "占用额度的个人购汇信息补录:【购汇金额】与【提钞金额】、【汇出资金（包括外汇票据）金额】、【存入个人外汇账户金额】、【旅行支票金额】之和不一致，请核对");
/*      */     }
/*      */     
/* 1390 */     String PURFX_TYPE_CODE = (String)nv.PopMap(row, "PURFX_TYPE_CODE", "购汇资金属性");
/* 1391 */     validateGHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, PURFX_TYPE_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpIndividualTradeFXSAInfo(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1403 */     NullVilator nv = new NullVilator();
/*      */     
/* 1405 */     Map PARAMETERS = (Map)nv.PopMap(data, "PARAMETERS", "请求参数");
/* 1406 */     String BIZ_TYPE_CODE = (String)nv.PopMap(PARAMETERS, "BIZ_TYPE_CODE", "业务类型代码");
/*      */     
/* 1408 */     List ROWSET = (List)nv.PopMap(data, "ROWSET", "请求循环体");
/* 1409 */     nv.hasNull();
/*      */     
/* 1411 */     for (int i = 0; i < ROWSET.size(); i++) {
/* 1412 */       Map ROW = (Map)ROWSET.get(i);
/* 1413 */       valiteWGReqMakeUpIndividualTradeFXSAInfoRow(ROW, BIZ_TYPE_CODE);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpIndividualTradeFXSAInfoRow(Map row, String BIZ_TYPE_CODE)
/*      */     throws DataCheckException
/*      */   {
/* 1424 */     NullVilator nv = new NullVilator();
/*      */     
/* 1426 */     String IDTYPE_CODE = (String)nv.PopMap(row, "IDTYPE_CODE", "证件类型代码");
/* 1427 */     String IDCODE = (String)nv.PopMap(row, "IDCODE", "证件号码");
/* 1428 */     String ADD_IDCODE = (String)row.get("ADD_IDCODE");
/* 1429 */     String CTYCODE = (String)nv.PopMap(row, "CTYCODE", "国家/地区代码");
/* 1430 */     String PAY_ORG_CODE = (String)row.get("PAY_ORG_CODE");
/* 1431 */     String AGENT_CORP_NAME = (String)row.get("AGENT_CORP_NAME");
/* 1432 */     String AGENT_CORP_CODE = (String)row.get("AGENT_CORP_CODE");
/* 1433 */     String INDIV_ORG_CODE = (String)row.get("INDIV_ORG_CODE");
/* 1434 */     String INDIV_ORG_NAME = (String)row.get("INDIV_ORG_NAME");
/* 1435 */     String CAPITALNO = (String)row.get("CAPITALNO");
/* 1436 */     String TXCCY = (String)nv.PopMap(row, "TXCCY", "币种");
/* 1437 */     String PURFX_AMT = parseAmt((String)nv.PopMap(row, "PURFX_AMT", "购汇金额"));
/* 1438 */     String PURFX_CASH_AMT = parseAmt((String)row.get("PURFX_CASH_AMT"));
/* 1439 */     String FCY_REMIT_AMT = parseAmt((String)row.get("FCY_REMIT_AMT"));
/* 1440 */     String LCY_ACCT_NO = (String)row.get("LCY_ACCT_NO");
/* 1441 */     String FCY_ACCT_AMT = parseAmt((String)row.get("FCY_ACCT_AMT"));
/* 1442 */     String TCHK_AMT = parseAmt((String)row.get("TCHK_AMT"));
/* 1443 */     String BIZ_TX_TIME = (String)row.get("BIZ_TX_TIME");
/* 1444 */     String REIN_REASON_CODE = (String)nv.PopMap(row, "REIN_REASON_CODE", "补录原因代码");
/* 1445 */     String BIZ_TX_CHNL_CODE = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/* 1446 */     String REIN_REMARK = (String)row.get("REIN_REMARK");
/* 1447 */     String REMARK = (String)row.get("REMARK");
/*      */     
/* 1449 */     nv.hasNull();
/*      */     
/*      */ 
/* 1452 */     valitIDTYPE_CODE(IDTYPE_CODE, IDCODE);
/*      */     
/*      */ 
/* 1455 */     valitCpRelate(IDTYPE_CODE, CTYCODE, BIZ_TYPE_CODE, ADD_IDCODE);
/*      */     
/* 1457 */     String bizChnlCode = (String)nv.PopMap(row, "BIZ_TX_CHNL_CODE", "业务办理渠道代码");
/* 1458 */     validateBizChnlCode(BIZ_TYPE_CODE, bizChnlCode);
/*      */     
/*      */ 
/* 1461 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/* 1462 */       if (StringUtils.isBlank(PAY_ORG_CODE)) {
/* 1463 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当业务类型为“通过支付机构的购汇”时,支付机构组织机构代码:PAY_ORG_CODE 不允许为空。");
/*      */       }
/* 1465 */       if (!BizChannelTypeEnum.TYPE_32.getCode().equals(BIZ_TX_CHNL_CODE)) {
/* 1466 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), " 业务类型为“通过支付机构购汇”时  业务办理渠道代码：BIZ_TX_CHNL_CODE 必须为“32-支付机构”。");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1479 */     if ((StringUtils.isNotBlank(AGENT_CORP_CODE)) && (!AGENT_CORP_CODE.matches("([A-Z]|[a-z]|[0-9])+"))) {
/* 1480 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型为“个人贸易购汇”时,代理企业组织机构代码:AGENT_CORP_CODE 可以输入;只允许输入数字、字母。");
/*      */     }
/*      */     
/*      */ 
/* 1484 */     if ((StringUtils.isNotBlank(INDIV_ORG_CODE)) && 
/* 1485 */       (!INDIV_ORG_CODE.matches("([A-Z]|[a-z]|[0-9])+"))) {
/* 1486 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型为“个人贸易购汇”时,个体工商户组织机构代码:INDIV_ORG_CODE 可以输入;只允许输入数字、字母。");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1496 */     if ((BizTypeEnum.TYPE_04.getCode().equals(BIZ_TYPE_CODE)) && (StringUtils.isBlank(CAPITALNO))) {
/* 1497 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "业务类型为“资本项目购汇”时, 外汇局批件号/备案表号/业务编号:CAPITALNO 不允许为空。");
/*      */     }
/*      */     
/*      */ 
/* 1501 */     if ("CNY".equals(TXCCY)) {
/* 1502 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450003.getCode(), "币种:TXCCY 不能为“人民币”。");
/*      */     }
/*      */     
/*      */ 
/* 1506 */     if ((StringUtils.isBlank(PURFX_AMT)) || (!PURFX_AMT.matches("[0-9]+(.[0-9]+)?")) || (parseDouble(PURFX_AMT) <= 0.0D)) {
/* 1507 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450062.getCode(), "购汇金额:PURFX_AMT 大于0。");
/*      */     }
/*      */     
/*      */ 
/* 1511 */     BigDecimal b_PURFX_CASH_AMT = null;
/* 1512 */     BigDecimal b_FCY_REMIT_AMT = null;
/* 1513 */     BigDecimal b_TCHK_AMT = null;
/* 1514 */     BigDecimal b_FCY_ACCT_AMT = null;
/* 1515 */     BigDecimal b_PURFX_AMT = new BigDecimal(PURFX_AMT);
/* 1516 */     if (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) {
/* 1517 */       b_FCY_REMIT_AMT = b_PURFX_AMT;
/* 1518 */       b_PURFX_CASH_AMT = new BigDecimal("0");
/* 1519 */       b_TCHK_AMT = new BigDecimal("0");
/* 1520 */       b_FCY_ACCT_AMT = new BigDecimal("0");
/*      */     } else {
/* 1522 */       b_PURFX_CASH_AMT = new BigDecimal(PURFX_CASH_AMT);
/* 1523 */       b_FCY_REMIT_AMT = new BigDecimal(FCY_REMIT_AMT);
/* 1524 */       b_TCHK_AMT = new BigDecimal(TCHK_AMT);
/* 1525 */       b_FCY_ACCT_AMT = new BigDecimal(FCY_ACCT_AMT);
/*      */     }
/*      */     
/* 1528 */     if (!b_PURFX_CASH_AMT.add(b_FCY_REMIT_AMT).add(b_TCHK_AMT).add(b_FCY_ACCT_AMT).equals(b_PURFX_AMT)) {
/* 1529 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "不占用额度的个人购汇信息补录:【购汇金额】与【提钞金额】、【汇出资金（包括外汇票据）金额】、【存入个人外汇账户金额】、【旅行支票金额】之和不一致，请核对");
/*      */     }
/*      */     
/*      */ 
/* 1533 */     if ((BizTypeEnum.TYPE_03.getCode().equals(BIZ_TYPE_CODE)) && (CertTypeEnum.TYPE_01.getCode().equals(IDTYPE_CODE)) && (StringUtils.isBlank(REMARK))) {
/* 1534 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "业务类型“提供凭证的经常项目其他购汇”并且对应交易主体是境内个人，备注栏不允许为空");
/*      */     }
/*      */     
/*      */ 
/* 1538 */     if ((StringUtils.isBlank(PURFX_CASH_AMT)) || (parseDouble(PURFX_CASH_AMT) < 0.0D)) {
/* 1539 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450061.getCode(), "购汇提钞金额:PURFX_CASH_AMT 应大于或等于零");
/*      */     }
/*      */     
/*      */ 
/* 1543 */     if ((StringUtils.isBlank(FCY_REMIT_AMT)) || (parseDouble(FCY_REMIT_AMT) < 0.0D)) {
/* 1544 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应大于等于零。");
/*      */     }
/* 1546 */     if ((BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE)) && (!StringUtils.equals(FCY_REMIT_AMT, PURFX_AMT))) {
/* 1547 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450009.getCode(), "当业务类型为“通过支付机构的购汇”时，汇出资金（包括外汇票据）金额:FCY_REMIT_AMT 应等于购汇金额。");
/*      */     }
/*      */     
/*      */ 
/* 1551 */     if ((StringUtils.isBlank(FCY_ACCT_AMT)) || (parseDouble(FCY_ACCT_AMT) < 0.0D)) {
/* 1552 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "存入个人外汇账户金额:FCY_ACCT_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/* 1556 */     if ((StringUtils.isBlank(TCHK_AMT)) || (parseDouble(TCHK_AMT) < 0.0D)) {
/* 1557 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "旅行支票金额:TCHK_AMT 应大于等于零。");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1562 */     if ((StringUtils.isBlank(LCY_ACCT_NO)) && ((parseDouble(FCY_ACCT_AMT) > 0.0D) || (BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))))
/* 1563 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”大于零或当业务类型为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 不允许为空");
/* 1564 */     if ((StringUtils.isNotBlank(LCY_ACCT_NO)) && (parseDouble(FCY_ACCT_AMT) <= 0.0D) && (!BizTypeEnum.TYPE_02.getCode().equals(BIZ_TYPE_CODE))) {
/* 1565 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "“存入个人外汇账户金额”小于等于零时或当业务类型不为“个人贸易购汇”时 ，个人外汇账户账号:LCY_ACCT_NO 必须为空");
/*      */     }
/*      */     
/*      */ 
/* 1569 */     long currentTime = System.currentTimeMillis();
/* 1570 */     long bizTxTime = 0L;
/*      */     try {
/* 1572 */       bizTxTime = new SimpleDateFormat("yyyy-MM-dd").parse(BIZ_TX_TIME).getTime();
/*      */     } catch (ParseException e) {
/* 1574 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), "日期转换异常：" + e.getMessage());
/*      */     }
/* 1576 */     if (bizTxTime > currentTime) {
/* 1577 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450040.getCode(), "业务实际办理日期:BIZ_TX_TIME 不能大于当前日期。");
/*      */     }
/*      */     
/*      */ 
/* 1581 */     if ((JSHDataImportResonEnum.CODE_04.getCode().equals(REIN_REASON_CODE)) && (StringUtils.isBlank(REIN_REMARK))) {
/* 1582 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "当补录原因为“其他”时, 补录说明:REIN_REMARK 不允许为空 ");
/*      */     }
/*      */     
/* 1585 */     String PURFX_TYPE_CODE = (String)nv.PopMap(row, "PURFX_TYPE_CODE", "购汇资金属性");
/* 1586 */     validateGHZJSX(IDTYPE_CODE, BIZ_TYPE_CODE, PURFX_TYPE_CODE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqMakeUpSignStatus(Map data)
/*      */     throws DataCheckException
/*      */   {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valiteWGReqQuerySafeExRate(Map data)
/*      */     throws DataCheckException
/*      */   {
/* 1608 */     Map PARAMETERS = (Map)data.get("PARAMETERS");
/*      */     
/* 1610 */     if (PARAMETERS != null) {
/* 1611 */       String CURRENCY_CODE = (String)PARAMETERS.get("CURRENCY_CODE");
/* 1612 */       String YEAR_MONTH = (String)PARAMETERS.get("YEAR_MONTH");
/* 1613 */       if ((StringUtils.isBlank(CURRENCY_CODE)) && (StringUtils.isBlank(YEAR_MONTH))) {
/* 1614 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400001.getCode(), "币种:CURRENCY_CODE 和年月:YEAR_MONTH 不能同时为空");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valitIDTYPE_CODE(String IDTYPE_CODE, String IDCODE)
/*      */     throws DataCheckException
/*      */   {
/* 1630 */     if (!IDTYPE_CODE.matches("\\d+")) {
/* 1631 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件类型代码 必须是 数字");
/*      */     }
/* 1633 */     int idtypeCode = Integer.valueOf(IDTYPE_CODE).intValue();
/* 1634 */     switch (idtypeCode) {
/*      */     case 1: 
/* 1636 */       if ((IDCODE == null) || (!IDCODE.matches("[0-9]{17}([A-Z]|[0-9]){1}"))) {
/* 1637 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为身份证：证件号码不能为空并且长度18位，由全部数字或数字加最末一位大写英文字符组成。");
/*      */       }
/*      */       
/*      */       break;
/*      */     case 4: 
/* 1642 */       if ((IDCODE == null) || (!IDCODE.matches("([A-Z]|[0-9]|[一-龥])+")))
/*      */       {
/* 1644 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外国护照：证件号码不能为空并且只允许输入大写字母和数字、中文。");
/*      */       }
/*      */       break;
/*      */     case 5: 
/* 1648 */       if ((IDCODE == null) || (!IDCODE.matches("[A-Z]{3}[0-9]{12}"))) {
/* 1649 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外国人持中国永久居留证：证件号码不能为空并且15位（国别码3位大写字母+ 12位数字）。");
/*      */       }
/*      */       
/*      */       break;
/*      */     case 6: 
/* 1654 */       if ((IDCODE == null) || (!IDCODE.matches("(H|M){1}[0-9]{8}"))) {
/* 1655 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为港澳居民来往内地通行：证件号码不能为空并且第一个字母是H或者M，后面共8位数字。当首字母为“H”时国家/地区为“香港”，当首字母为“M”时国家/地区为“澳门”。");
/*      */       }
/*      */       
/*      */       break;
/*      */     case 7: 
/* 1660 */       if ((IDCODE == null) || (!IDCODE.matches("[0-9]{7,8}"))) {
/* 1661 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为台湾居民来往内地通行证：证件号码不能为空并且为7或8位数字。");
/*      */       }
/*      */       break;
/*      */     case 9: 
/* 1665 */       if ((IDCODE == null) || (!IDCODE.matches("([A-Z]|[0-9]|[一-龥])+")))
/*      */       {
/* 1667 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为中国护照：证件号码不能为空并且只允许输入大写字母和数字、中文。");
/*      */       }
/*      */       break;
/*      */     case 10: 
/* 1671 */       if ((IDCODE == null) || (!IDCODE.matches("([A-Z]|[0-9]|[一-龥])+")))
/*      */       {
/* 1673 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外交官证：证件号码不能为空并且只允许输入大写字母和数字、中文。");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void valitCpRelate(String IDTYPE_CODE, String CTYCODE, String BIZ_TYPE_CODE, String ADD_IDCODE)
/*      */     throws DataCheckException
/*      */   {
/* 1692 */     if (!IDTYPE_CODE.matches("\\d+")) {
/* 1693 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件类型代码 必须是 数字");
/*      */     }
/* 1695 */     int idtypeCode = Integer.valueOf(IDTYPE_CODE).intValue();
/* 1696 */     switch (idtypeCode) {
/*      */     case 1: 
/* 1698 */       if ((CTYCODE != null) && (!"CHN".equals(CTYCODE))) {
/* 1699 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为身份证：国家/地区 必须填写中国。采用国标，3位大写字母");
/*      */       }
/*      */       break;
/*      */     case 4: 
/* 1703 */       if ((CTYCODE != null) && ("CHN".equals(CTYCODE))) {
/* 1704 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外国护照：国家/地区 必须填写中国之外的其他国家/地区。采用国标，3位大写字母");
/*      */       }
/*      */       
/* 1707 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1708 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/*      */       break;
/*      */     case 5: 
/* 1712 */       if ((CTYCODE != null) && ("CHN".equals(CTYCODE))) {
/* 1713 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外国人持中国永久居留证：国家/地区 必须填写中国之外的其他国家/地区。采用国标，3位大写字母");
/*      */       }
/*      */       
/* 1716 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1717 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/*      */       break;
/*      */     case 6: 
/* 1721 */       if (CTYCODE != null) if (!Arrays.asList(new String[] { "HKG", "MAC" }).contains(CTYCODE))
/*      */         {
/*      */ 
/* 1724 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为港澳通行证：国家/地区 必须填写香港/澳门。采用国标，3位大写字母");
/*      */         }
/* 1726 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1727 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/*      */       break;
/*      */     case 7: 
/* 1731 */       if ((CTYCODE != null) && (!"TWN".equals(CTYCODE))) {
/* 1732 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为台湾通行证：国家/地区 必须填写台湾。采用国标，3位大写字母");
/*      */       }
/* 1734 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1735 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/*      */       break;
/*      */     case 9: 
/* 1739 */       if ((CTYCODE != null) && (!"CHN".equals(CTYCODE))) {
/* 1740 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为中国护照：国家/地区 必须填写中国。采用国标，3位大写字母");
/*      */       }
/* 1742 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1743 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/* 1745 */       if ((ADD_IDCODE == null) || (!ADD_IDCODE.matches("([A-Z]|[0-9]|[一-龥])+")))
/*      */       {
/* 1747 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "当证件类型为“中国护照”时,补充证件号码不允许为空，需填写境外永久居留证号码；只允许输入大写字母和数字、中文。");
/*      */       }
/*      */       
/*      */       break;
/*      */     case 10: 
/* 1752 */       if ((CTYCODE != null) && ("CHN".equals(CTYCODE))) {
/* 1753 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "证件为外国人持中国永久居留证：国家/地区 必须填写中国之外的其他国家/地区。采用国标，3位大写字母");
/*      */       }
/*      */       
/* 1756 */       if ((BIZ_TYPE_CODE != null) && (BizTypeEnum.TYPE_05.getCode().equals(BIZ_TYPE_CODE))) {
/* 1757 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "业务类型代码为“通过支付机构的结汇/购汇”，证件类型只能为“居民身份证”。");
/*      */       }
/*      */       
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */   public static void setFeild(Object obj, Map data)
/*      */     throws DataCheckException
/*      */   {
/*      */     try
/*      */     {
/* 1772 */       Field[] fs = PropertyUtils.getDeclaredFields(obj.getClass(), new ArrayList());
/*      */       
/* 1774 */       for (int i = 0; i < fs.length; i++) {
/* 1775 */         Field f = fs[i];
/* 1776 */         if (!f.getName().equals("serialVersionUID"))
/*      */         {
/* 1778 */           f.setAccessible(true);
/* 1779 */           f.set(obj, getVal(obj, data, i, f));
/*      */         }
/*      */       }
/* 1782 */     } catch (Exception e) { throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Object getVal(Object obj, Map data, int i, Field f)
/*      */     throws DataCheckException
/*      */   {
/* 1798 */     if (Collection.class.isAssignableFrom(f.getType()))
/* 1799 */       return dealCollection(obj, data, i, f);
/* 1800 */     if ((f.getType().isPrimitive()) || (Primitives.unbox(f.getType()) != null))
/*      */     {
/* 1802 */       return dealPrimitive(data, f); }
/* 1803 */     if (String.class.equals(f.getType()))
/* 1804 */       return getMapStringVal(data, f.getName());
/* 1805 */     if (Object.class.isAssignableFrom(f.getType())) {
/* 1806 */       return dealSingleObject(data, f);
/*      */     }
/* 1808 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Object dealSingleObject(Map data, Field f)
/*      */     throws DataCheckException
/*      */   {
/*      */     try
/*      */     {
/* 1823 */       Object entity = f.getType().newInstance();
/* 1824 */       Map sub = (Map)data.get(f.getName());
/* 1825 */       setFeild(entity, sub == null ? new HashMap() : sub);
/* 1826 */       return entity;
/*      */     } catch (Exception e) {
/* 1828 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Object dealPrimitive(Map data, Field f)
/*      */     throws DataCheckException
/*      */   {
/*      */     try
/*      */     {
/* 1844 */       Class boxClass = f.getType();
/* 1845 */       if (!f.getType().isPrimitive()) {
/* 1846 */         boxClass = Primitives.unbox(f.getType());
/*      */       }
/* 1848 */       String val = getMapStringVal(data, f.getName());
/* 1849 */       return boxClass.getMethod("valueOf", new Class[] { String.class }).invoke(null, new Object[] { val });
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1853 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   private static String getMapStringVal(Map m, String key) {
/* 1858 */     Object obj = m.get(key);
/* 1859 */     return obj == null ? "" : String.valueOf(obj);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List dealCollection(Object obj, Map data, int i, Field f)
/*      */     throws DataCheckException
/*      */   {
/*      */     try
/*      */     {
/* 1877 */       List list = (List)data.get(f.getName());
/* 1878 */       list = list == null ? new ArrayList() : list;
/* 1879 */       List feldlst = new ArrayList();
/* 1880 */       Class entityClass = getEntityClass(obj.getClass());
/* 1881 */       for (int j = 0; j < list.size(); j++) {
/* 1882 */         Object listEnTity = entityClass.newInstance();
/* 1883 */         Map sub = (Map)list.get(j);
/* 1884 */         setFeild(listEnTity, sub == null ? new HashMap() : sub);
/* 1885 */         feldlst.add(listEnTity);
/*      */       }
/* 1887 */       return feldlst;
/*      */     } catch (Exception e) {
/* 1889 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Class getEntityClass(Class entityClass)
/*      */     throws DataCheckException
/*      */   {
/*      */     try
/*      */     {
/* 1902 */       return Class.forName(entityClass.getName() + "Row");
/*      */     } catch (ClassNotFoundException e) {
/* 1904 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_999999.getCode(), e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Map toMap(Object rsp)
/*      */   {
/* 1915 */     return new HashMap();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void validateBizChnlCode(String bizType, String bizChnlCode)
/*      */     throws DataCheckException
/*      */   {
/* 1925 */     if (BizTypeEnum.TYPE_01.getCode().equals(bizType)) {
/* 1926 */       if (BizChannelTypeEnum.TYPE_32.getCode().equals(bizChnlCode)) {
/* 1927 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "占用额度的结汇/购汇录入或补录时业务办理渠道不能为32-支付机构（接口模式）");
/*      */       }
/*      */     }
/* 1930 */     else if ((!BizChannelTypeEnum.TYPE_32.getCode().equals(bizChnlCode)) && (!BizChannelTypeEnum.TYPE_12.getCode().equals(bizChnlCode))) {
/* 1931 */       throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_400002.getCode(), "不占用额度的结汇/购汇录入或补录时业务办理渠道只能为12-柜台渠道（接口模式）或32-支付机构（接口模式）");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void validateJHZJSX(String idTypeCode, String bizType, String code)
/*      */     throws DataCheckException
/*      */   {
/* 1945 */     if (BizTypeEnum.TYPE_01.getCode().equals(bizType)) {
/* 1946 */       if (CertTypeEnum.TYPE_01.getCode().equals(idTypeCode))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1967 */         if ((!"110".equals(code)) && (!"121".equals(code)) && (!"122".equals(code)) && (!"123".equals(code)) && (!"124".equals(code)) && (!"125".equals(code)) && (!"126".equals(code)) && (!"131".equals(code)) && (!"132".equals(code)) && (!"133".equals(code)) && (!"210".equals(code)) && (!"22A".equals(code)) && (!"232".equals(code)) && (!"24A".equals(code)) && (!"250".equals(code)) && (!"270".equals(code)) && (!"222".equals(code)) && (!"231".equals(code)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1986 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */         }
/*      */       }
/* 1989 */       else if ((!"110".equals(code)) && (!"121".equals(code)) && (!"122".equals(code)) && (!"123".equals(code)) && (!"124".equals(code)) && (!"125".equals(code)) && (!"126".equals(code)) && (!"131".equals(code)) && (!"132".equals(code)) && (!"133".equals(code)) && (!"210".equals(code)) && (!"22A".equals(code)) && (!"232".equals(code)) && (!"24A".equals(code)) && (!"250".equals(code)) && (!"270".equals(code)) && (!"221".equals(code)) && (!"223".equals(code)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2008 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */       }
/*      */     }
/*      */     else {
/* 2012 */       if ((BizTypeEnum.TYPE_02.getCode().equals(bizType)) && (!"110".equals(code)))
/*      */       {
/* 2014 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */       }
/* 2016 */       if ((BizTypeEnum.TYPE_03.getCode().equals(bizType)) && (!"121".equals(code)) && (!"122".equals(code)) && (!"123".equals(code)) && (!"124".equals(code)) && (!"125".equals(code)) && (!"126".equals(code)) && (!"131".equals(code)) && (!"132".equals(code)) && (!"133".equals(code)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2035 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */       }
/* 2037 */       if ((BizTypeEnum.TYPE_05.getCode().equals(bizType)) && (!"110".equals(code)) && (!"121".equals(code)) && (!"122".equals(code)) && (!"123".equals(code)) && (!"124".equals(code)) && (!"125".equals(code)) && (!"126".equals(code)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2052 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */       }
/*      */       
/* 2055 */       if (BizTypeEnum.TYPE_04.getCode().equals(bizType))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2066 */         if (CertTypeEnum.TYPE_01.getCode().equals(idTypeCode)) {
/* 2067 */           if ((!"210".equals(code)) && (!"22A".equals(code)) && (!"232".equals(code)) && (!"24A".equals(code)) && (!"250".equals(code)) && (!"270".equals(code)) && (!"222".equals(code)) && (!"231".equals(code)))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2076 */             throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */           }
/*      */         }
/* 2079 */         else if ((!"210".equals(code)) && (!"22A".equals(code)) && (!"232".equals(code)) && (!"24A".equals(code)) && (!"250".equals(code)) && (!"270".equals(code)) && (!"221".equals(code)) && (!"223".equals(code)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2088 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450038.getCode(), MsgErrorCodeEnum.ERRCODE_450038.getValue());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void validateGHZJSX(String idTypeCode, String bizType, String code)
/*      */     throws DataCheckException
/*      */   {
/* 2104 */     if (BizTypeEnum.TYPE_01.getCode().equals(bizType))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2120 */       if (CertTypeEnum.TYPE_01.getCode().equals(idTypeCode))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2126 */         if ((!"310".equals(code)) && (!"321".equals(code)) && (!"3225".equals(code)) && (!"323".equals(code)) && (!"324".equals(code)) && (!"325".equals(code)) && (!"326".equals(code)) && (!"331".equals(code)) && (!"332".equals(code)) && (!"333".equals(code)) && (!"410".equals(code)) && (!"42A".equals(code)) && (!"44A".equals(code)) && (!"450".equals(code)) && (!"470".equals(code)) && (!"3221".equals(code)) && (!"3222".equals(code)) && (!"3223".equals(code)) && (!"421".equals(code)) && (!"431".equals(code)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2147 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/* 2154 */       else if ((!"310".equals(code)) && (!"321".equals(code)) && (!"3225".equals(code)) && (!"323".equals(code)) && (!"324".equals(code)) && (!"325".equals(code)) && (!"326".equals(code)) && (!"331".equals(code)) && (!"332".equals(code)) && (!"333".equals(code)) && (!"410".equals(code)) && (!"42A".equals(code)) && (!"44A".equals(code)) && (!"450".equals(code)) && (!"470".equals(code)) && (!"3224".equals(code)) && (!"422".equals(code)) && (!"423".equals(code)) && (!"432".equals(code)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2174 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */       }
/*      */     }
/*      */     else {
/* 2178 */       if ((BizTypeEnum.TYPE_02.getCode().equals(bizType)) && (!"310".equals(code)))
/*      */       {
/* 2180 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */       }
/* 2182 */       if (BizTypeEnum.TYPE_03.getCode().equals(bizType))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2192 */         if (CertTypeEnum.TYPE_01.getCode().equals(idTypeCode))
/*      */         {
/*      */ 
/*      */ 
/* 2196 */           if ((!"121".equals(code)) && (!"3225".equals(code)) && (!"323".equals(code)) && (!"324".equals(code)) && (!"325".equals(code)) && (!"326".equals(code)) && (!"321".equals(code)) && (!"331".equals(code)) && (!"332".equals(code)) && (!"333".equals(code)) && (!"3221".equals(code)) && (!"3222".equals(code)) && (!"3223".equals(code)))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2210 */             throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 2215 */         else if ((!"121".equals(code)) && (!"3225".equals(code)) && (!"321".equals(code)) && (!"323".equals(code)) && (!"324".equals(code)) && (!"325".equals(code)) && (!"326".equals(code)) && (!"331".equals(code)) && (!"332".equals(code)) && (!"333".equals(code)) && (!"3224".equals(code)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2227 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */         }
/*      */       }
/*      */       
/* 2231 */       if ((BizTypeEnum.TYPE_05.getCode().equals(bizType)) && (!"310".equals(code)) && (!"321".equals(code)) && (!"322".equals(code)) && (!"3221".equals(code)) && (!"3222".equals(code)) && (!"3223".equals(code)) && (!"3225".equals(code)) && (!"323".equals(code)) && (!"324".equals(code)) && (!"325".equals(code)) && (!"326".equals(code)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2253 */         throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */       }
/*      */       
/* 2256 */       if (BizTypeEnum.TYPE_04.getCode().equals(bizType))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2263 */         if (CertTypeEnum.TYPE_01.getCode().equals(idTypeCode))
/*      */         {
/*      */ 
/* 2266 */           if ((!"410".equals(code)) && (!"42A".equals(code)) && (!"44A".equals(code)) && (!"450".equals(code)) && (!"470".equals(code)) && (!"421".equals(code)) && (!"431".equals(code)))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2274 */             throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 2280 */         else if ((!"410".equals(code)) && (!"42A".equals(code)) && (!"44A".equals(code)) && (!"450".equals(code)) && (!"470".equals(code)) && (!"422".equals(code)) && (!"423".equals(code)) && (!"432".equals(code)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2289 */           throw new DataCheckException(MsgErrorCodeEnum.ERRCODE_450031.getCode(), MsgErrorCodeEnum.ERRCODE_450031.getValue());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static double parseDouble(String number)
/*      */   {
/* 2297 */     if (StringUtils.isNotBlank(number)) {
/* 2298 */       return Double.valueOf(number.replaceAll(",", "")).doubleValue();
/*      */     }
/* 2300 */     return 0.0D;
/*      */   }
/*      */   
/*      */   public static String parseAmt(String number) {
/* 2304 */     if (StringUtils.isNotBlank(number)) {
/* 2305 */       return number.replaceAll(",", "");
/*      */     }
/* 2307 */     return number;
/*      */   }
/*      */ }


/* Location:              D:\sili.chen\IdeaProjects\runtongdeclare\lib\ebills.fems.interface.jar!\com\pactera\fems\message\wg\support\WGDSValitorHander.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */