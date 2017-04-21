<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇额度登记</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript"  src="${basePath }/resources/business/spfeValidator.js"></script>
<script type="text/javascript">
var seqno = "${mode.SEQNO}";
var transState = "${transState}";//流程状态
window.onload=function(){
	switch(transState){
		case '0': initHandle();break;
		case '1': initCheck();break;
		case '2': initAuth();break;
		case '4': diableAction();break;
		case '5': initHandle();break;
		case '6': diableAction();break;
		case '7': diableAction();break;
		default:initHandle();
	};
	initRemsg();
};

function doCancel() {
	doCancelTask(basePath,seqno,"SpfeMkUpService");
}

function initBizTxTime() {
	var tradeno = "${tradeNo}";
	if (tradeno == '000080' || tradeno == '000081' || tradeno == '000082' || tradeno == '000083') {
		$("#BIZ_TX_TIME").datebox({disabled:false});
	}
}

//初始化取消按钮
function initCancelBtn() {
	if (seqno !='' && (transState =='0' || transState == '5')){
		$("#cancelBtn").linkbutton({disabled:false});
	} else {
		$("#cancelBtn").linkbutton({disabled:true});
	}
}

/**
 * 初始化 经办
 */
function initHandle(){
	$("#authBtn,#checkBtn,#printBtn").linkbutton({disabled:true});
}
 
/**
 * 初始化 复核
 */
function initCheck(){
	$("#addBtn,#saveBtn,#handleBtn,#authBtn").linkbutton({disabled:true});
	$("input:not(#OCCUPY_LMT_STATUQuery),textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
	$("#OCCUPY_LMT_STATUQuery").hide();
}

/**
 * 初始化 授权
 */
function initAuth(){
	$("#addBtn,#saveBtn,#handleBtn,#checkBtn").linkbutton({disabled:true});
	$("input:not(#OCCUPY_LMT_STATUQuery),textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
	$("#OCCUPY_LMT_STATUQuery").hide();
}

/**
 * 初始化 完成信息
 */
function initDetail(){
	$("#toolbarid").html("");
	$("#OCCUPY_LMT_STATUQuery").hide();
	$("input,textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
	$("#printBtn").linkbutton({disabled:false});
}

//初始化返回信息
function initRemsg() {
	if (transState == '4' || transState == '7'){
		$("#remsgid").show();
	} else {
		$("#remsgid").hide();
	}
}

/**
 * 初 禁止操作
 */
function diableAction(){
	$("#addBtn,#saveBtn,#handleBtn,#checkBtn,#authBtn,#cancelBtn").linkbutton({disabled:true});
	$("#OCCUPY_LMT_STATUQuery").hide();
	$("input,textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
	$("#printBtn").linkbutton({disabled:false});
}

/**
 * 经办
 */
function saveOrHandle(isHandle) {
	if(isHandle) {
		if ($('#fo').form('validate')) {
			var prm = getPrm();
			if(!validateSpfeLmt(prm,"spfeMkUp") || !checkAmtQuery()){//接口数据校验
				return;
			}			
			var unUsed = restoreAmt($("#unUsedFont").text());
			var TXAMTUSD = restoreAmt($("#TXAMTUSD").val());
			var tip = "确定要提交？";
			if(parseFloat(TXAMTUSD) > parseFloat(unUsed)){
				if(trade_type = "JH"){
					tip = "结汇金额大于本年度内剩余可结汇金额，请确认是否提交?";
				}
				if(trade_type=="GH"){
					tip = "购汇金额大于本年度内剩余可购汇金额，请确认是否提交?";
				}
			}
			$.messager.confirm("系统提示",tip, function(r){
				if (r) {
					doSpfeMkUpHandle(isHandle,prm);
				}
			});
		}else{
			$("tbody",$('#fo')).show();
		}
	}else{
		var prm = getPrm();
		doSpfeMkUpHandle(isHandle,prm);
	}
}

//获取提交参数
function getPrm(){
	var p = {};	
	setDefalut();
	$("input,textarea").each(function(){
		if(this && this.name){
			p[this.name] = this.value;
		}
	});
	$.each($(".easyui-combobox"),function(){
		if(this && this.name){
			var val = $("#"+this.id).combobox("getValue");
			p[this.name] = val;
		}
	});
	if($("#REMARK").attr("ischg")=="N"){
		p.REMARK='';
	}
	return p;
}

function setDefalut(){
	//不占用额度购汇当业务类型为通过支付机构的结汇/购汇时设置默认值
	if($('#BIZ_TYPE_CODE').combobox('getValue') == '05' && OCCUPY_LMT_STATUS == "N" && $("#TRADE_TYPE").combobox("getValue") == 'GH'){
		$('#FCY_REMIT_AMT').val($('#TXAMT').val());
		$('#PURFX_CASH_AMT').val('0');
		$('#FCY_ACCT_AMT').val('0');
		$('#TCHK_AMT').val('0');
	}
}


function doSpfeMkUpHandle(isHandle,prm){
	$.post('spfeMkUp.do?method='+(isHandle?'doHandle':'doSave'),prm, function(data) {
		if(isHandle){
			diableAction();
		}
		$("#SEQNO").val(data.seqno);
		$("#BIZNO").val(data.bizno);
		$("#REFNO").val(data.refno);
		$.messager.alert("系统提示", isHandle?"提交成功":"保存成功", "info");
	});
}
/**
 * 复核
 */
function doCheck() {
	doCheckHandle("${mode.SEQNO}","${tradeNo}");
}

/**
 * 授权
 */
function doAuth() {
	doAuthHandle("${mode.SEQNO}","${tradeNo}");
}


$(document).ready(function(){
	$("#BIZ_TYPE_CODE").combobox({
		onSelect:function(row){
			if(!transState || transState=="0" || transState=="5"){
				changeBizTypeCode(row.value,false);
			}
			changeJSHZJSX(trade_type, row.value, $("#IDTYPE_CODE").combobox("getValue"));
		}
	});
	initEvent();
	init();	
	<c:if test="${not empty mode.BIZ_TYPE_CODE}">
		changeBizTypeCode("${mode.BIZ_TYPE_CODE}",true);
	</c:if>
	initBizTxTime();
	initCancelBtn();
	if(!transState || transState=="0"  || transState=="5"){
		changeBizChnCode($("#BIZ_TYPE_CODE").combobox("getValue"));
	}
});

function initEvent(){
	if("${OCCUPY_LMT_STATUS}"=="Y"){
		$("#BIZ_TYPE_CODE").combobox({disabled:true}).combobox("setValue", "01");
	}else{
		$("#OCCUPY_LMT_STATUQuery").hide();
		var ary = [];
		$.each($("#BIZ_TYPE_CODE").combobox("getData"),function(){
			if(!this.value || this.value!="01"){
				ary[ary.length] = this;
			}
		});
		$("#BIZ_TYPE_CODE").combobox("loadData",ary);
	}
	
	$("#TXCCY").combobox({
		onSelect:function(row){
			if(trade_type=="JH"){
				changeSafeExRate(row.value, "TXAMT");
			} else if(trade_type=="GH"){
				changeSafeExRate(row.value, "TXAMT");
				changeSafeExRate(row.value, "PURFX_CASH_AMT");
			}
		}
	});
	
	$("#IDTYPE_CODE").combobox({
		onChange:function(n,o){
			switchIdtypeCode(n);
		},
		onSelect:function (row, o) {
			changeJSHZJSX(trade_type, $("#BIZ_TYPE_CODE").combobox("getValue"), row.value);
		}
	});
	
	$("#reinReasonCode").combobox({
		onChange:function(n,o){
			var isR = false;
			if("04"==n){
				isR = true;
			}
			$("#reinRemark").validatebox({ 
				required:isR 
			});
		}
	});
}

var canSubmit = true;//是否可以提交


var exPrm =  {};//流程 doTask 页面 提交扩展传递的 参数
function queryIndividualFXSE(){
	if(OCCUPY_LMT_STATUS=="N") return true;
	var prm = {};
	prm.tradeType = $("#TRADE_TYPE").combobox("getValue");
	prm.idTypeCode = $("#IDTYPE_CODE").combobox("getValue");
	prm.CTYCODE = $("#CTYCODE").combobox("getValue");
	prm.cityCode = prm.CTYCODE;
	prm.idCode = $("#IDCODE").val();
	if (!prm.tradeType) {
		$.messager.alert("系统提示","请选择交易类型","error");
		return;
	}
	if (!prm.idTypeCode){
		$.messager.alert("系统提示","证件类型不能为空","error");
		return;
	}
	if (!prm.CTYCODE) {
		$.messager.alert("系统提示","国家/地区不能为空","error");
		return;
	}
	if(!prm.idCode){
		$.messager.alert("系统提示","证件号码不能为空","error");
		return;
	}
	
	$.post("${basePath}/spfeQuery.do?method=spfeAmtDataQuery",prm,function(spfeAmtQuery){
		if(spfeAmtQuery && spfeAmtQuery!="null"){
			spfeAmtQuery = $.parseJSON(spfeAmtQuery);
		}else if(spfeAmtQuery.indexOf("#document")!=-1){
			$.messager.alert("系统提示",spfeAmtQuery,"error");
			return;
		}else{
			spfeAmtQuery = {amtBalanceUsd:"", amtUsd:"", typeStatus:"" };
		}		
		$("#statusFont").html(spfeAmtQuery.typeStatus+"<input type='hidden' name='TYPE_STATUS' value='"+spfeAmtQuery.typeStatusCode+"'/>");
		$("#usedFont").html(spfeAmtQuery.amtUsd+"<input type='hidden' name='AMT_USD' value='"+spfeAmtQuery.amtUsd+"'/>");
		$("#unUsedFont").html(spfeAmtQuery.amtBalanceUsd+"<input type='hidden' name='AMT_BALANCE_USD' value='"+spfeAmtQuery.amtBalanceUsd+"'/>");
		$("#PERSON_NAME").val(spfeAmtQuery.custName);
		
		$("#pubDate").val(spfeAmtQuery.pubDate);
		$("#endDate").val(spfeAmtQuery.endDate);
		$("#pubReason").val(spfeAmtQuery.pubReason);
		
		var bizTypeCode = $("#BIZ_TYPE_CODE").combobox('getValue');
		var idTypeCode = $("#IDTYPE_CODE").combobox('getValue');					
		var ctyCode = $("#CTYCODE").combobox('getValue');
		$("#BIZ_TYPE_CODE").combobox({disabled:true});
		$("#IDTYPE_CODE").combobox({disabled:true});
		$("#CTYCODE").combobox({disabled:true});
		$("#BIZ_TYPE_CODE").combobox('setValue',bizTypeCode);
		$("#IDTYPE_CODE").combobox('setValue',idTypeCode);
		$("#CTYCODE").combobox('setValue',ctyCode);				
		$("#IDCODE").attr("disabled",true);	
		
		/*if (spfeAmtQuery.signStatusCode == "1") {//已签署,则将是否同意签署状态改为Y
			$("#SIGNSTATUS").val("Y");
			var bizTypeCode = $("#BIZ_TYPE_CODE").combobox('getValue');
			var idTypeCode = $("#IDTYPE_CODE").combobox('getValue');					
			var ctyCode = $("#CTYCODE").combobox('getValue');
			$("#BIZ_TYPE_CODE").combobox({disabled:true});
			$("#IDTYPE_CODE").combobox({disabled:true});
			$("#CTYCODE").combobox({disabled:true});
			$("#BIZ_TYPE_CODE").combobox('setValue',bizTypeCode);
			$("#IDTYPE_CODE").combobox('setValue',idTypeCode);
			$("#CTYCODE").combobox('setValue',ctyCode);				
			$("#IDCODE").attr("disabled",true);
		}
		var SIGNSTATUS = $("#SIGNSTATUS").val();//是否同意预关注风险提示/关注名单告知状态
		//typeStatusCode = 02重点关注对象   signStatusCode = 0未签署
		if(("02"==spfeAmtQuery.typeStatusCode || "03"==spfeAmtQuery.typeStatusCode) && spfeAmtQuery.signStatusCode =="0" && "Y"!=SIGNSTATUS){//是否同意签署状态为N时不允许提交，弹出签署框
			var idTypeName = $("#IDTYPE_CODE").combobox("getText");
			confirmAgentCorpCode(spfeAmtQuery.custName, idTypeName, prm.idCode, spfeAmtQuery.pubDate, spfeAmtQuery.endDate, spfeAmtQuery.pubReason,spfeAmtQuery.typeStatusCode);
		}
		*/
	});
	$("#OCCUPY_LMT_STATUQuery").attr("idQuery","Y");
}
/*检查额度查询是否能够通过*/
function checkAmtQuery(){
	var BIZ_TYPE_CODE =$("#BIZ_TYPE_CODE").combobox("getValue");
	if(!BIZ_TYPE_CODE){
		$.messager.alert("系统提示", "当业务类型不能为空", "error");
		return false;
	}
	var IDTYPE_CODE = $("#IDTYPE_CODE").combobox("getValue");
	if(BIZ_TYPE_CODE=="05"){
		if( IDTYPE_CODE !="01"){
			$.messager.alert("系统提示", "当业务类型为通过支付机构的结汇/购汇时，证件类型只能是居民身份证", "error");
			return false;
		}
		var SALEFX_SETTLE_CODEdom = $("#SALEFX_SETTLE_CODE");
		if(SALEFX_SETTLE_CODEdom && SALEFX_SETTLE_CODEdom.length>0){
			var SALEFX_SETTLE_CODE = SALEFX_SETTLE_CODEdom.combobox("getValue");
			if(SALEFX_SETTLE_CODE!="02"){
				$.messager.alert("系统提示", "当业务类型为通过支付机构的结汇/购汇时，结汇资金形态必须是02-汇入资金（包括外汇票据）", "error");
				return false;
			}
		}
		var BIZ_TX_CHNL_CODE = $("#BIZ_TX_CHNL_CODE").combobox("getValue");
		if(BIZ_TX_CHNL_CODE!="32"){
			$.messager.alert("系统提示", "业务类型为“通过支付机构购汇”时  业务办理渠道代码：BIZ_TX_CHNL_CODE 必须为“32支付机构”", "error");
			return false;
		}
	}
	var unUsed = restoreAmt($("#unUsedFont").text());
	var TXAMTUSD = restoreAmt($("#TXAMTUSD").val());
	var REMARK = $("#REMARK").val();
	var ischg = $("#REMARK").attr("ischg");
	var isTXAMTUSDValid = false;//结汇金额是否验证过
	if (trade_type == "JH") {
		var SALEFX_SETTLE_CODE = $("#SALEFX_SETTLE_CODE").combobox("getValue");//01-外币现钞
		if('01' == SALEFX_SETTLE_CODE ){
			if( parseFloat(TXAMTUSD) > 5000){
				isTXAMTUSDValid = true;
				if ( ischg != 'Y' || REMARK == '') {
					$.messager.alert("系统提示","当结汇资金形态为“外币现钞”且单笔外币现钞结汇金额超过等值5000美元，备注栏不能为空，需要在备注栏注明现钞来源证明材料","info");
					return false;
				}
			}
		}
	}
	if (trade_type == "GH") {
		var PURFX_CASH_AMTUSD = $("#PURFX_CASH_AMTUSD").val();//购汇提吵折美元
		if (parseFloat(PURFX_CASH_AMTUSD) > 10000 && REMARK == '') {
			$.messager.alert("系统提示","单笔提钞金额超过等值1万美元，备注栏不能为空，需要在备注栏注明提钞用途","error");
			return false;
		}
		if("10" != IDTYPE_CODE){ //证件类型为外交官证时，不校验境外个人小额购汇单笔限额。
			if(OCCUPY_LMT_STATUS=="N" && "03" == BIZ_TYPE_CODE && "01" != IDTYPE_CODE && parseFloat(TXAMTUSD) > 500 && !REMARK){
				$.messager.alert("系统提示","境外个人购汇金额单笔超过500美元，备注栏不能为空，需要在备注栏填写凭证信息","error");
				return false;
			}
		}
	}
	
	if(OCCUPY_LMT_STATUS=="N" && trade_type=="JH" && BIZ_TYPE_CODE=="03" && IDTYPE_CODE!="10" && !REMARK){
		$.messager.alert("系统提示", "不占额 并且业务类型为“提供凭证的经常项目其他结汇”,并且证件类型不是“外交官证”时 备注 不允许为空", "error");
		return false;
	}
	
	if(OCCUPY_LMT_STATUS=="N"){
		return true;
	}
	
	if(trade_type=="GH"){
		if(IDTYPE_CODE!="01" && IDTYPE_CODE!="05"){
			$.messager.alert("系统提示", "占额的购汇信息补录 证件类型只能是居民身份证 或者外国人永久居留证", "error");
			return false;
		}
	}
	
	var TYPE_STATUS = $("[name=TYPE_STATUS]").val();
	var SIGNSTATUS = $("[name=SIGNSTATUS]").val();
	//if(("02" == TYPE_STATUS || "03" == TYPE_STATUS) && "Y" != SIGNSTATUS){
		//$.messager.alert("系统提示", "预关注风险提示函或关注名单告知书未告知个人，不能提交", "error");
		//if("02" == TYPE_STATUS){
			//$.messager.alert("系统提示", "个人分类状态是“02-预分类”的个人在首次办理个人购汇或结汇业务时，银行需要以《风险提示函》予以风险提示!", "error");
		//}else{
		//	$.messager.alert("系统提示", "个人分类状态是“03-关注名单”的个人在首次办理个人购汇或结汇业务时，银行需要以《关注名单告知书》予以告知!", "error");
		//}
		//var PERSON_NAME = $("#PERSON_NAME").val();
		//var idCode = $("#IDCODE").val();
		//var pubDate = $('#pubDate').val();
		//var endDate = $('#endDate').val();
		//var pubReason = $('#pubReason').val();
		//var idTypeName = $("#IDTYPE_CODE").combobox("getText");
		//confirmAgentCorpCode(PERSON_NAME, idTypeName, idCode, pubDate, endDate, pubReason, TYPE_STATUS);
		//return false;
	//}
	if(!TXAMTUSD || parseFloat(TXAMTUSD) <=0){
		$.messager.alert("系统提示","结/购汇金额必须大于0","error");
		return false;
	}else if($("#OCCUPY_LMT_STATUQuery").attr("idQuery")=="N"){
		$.messager.alert("系统提示","请先查询本年度内剩余可购结汇金额！","error");
		return false;
	}else if(unUsed && parseFloat(unUsed)>= parseFloat(TXAMTUSD)){
		return true;
	}
	return true;
	/*else{
		var tip = "结汇金额大于本年度内剩余可结汇金额，请确认是否提交?";
		if(trade_type=="GH"){
			tip = "购汇金额大于本年度内剩余可购汇金额，请确认是否提交?";
		}
		return confirm(tip);
	}*/
}

function getComboboxValue(id){
	return $("#"+id).combobox("getValue");
}

var trade_type = "${TRADE_TYPE}";
var OCCUPY_LMT_STATUS = "${OCCUPY_LMT_STATUS}";
function init() {
	$("#TRADE_TYPE").combobox({"disabled":true});
	$("#OCCUPY_LMT_STATUS").combobox({"disabled":true});
	$("#TRADE_TYPE").combobox("setValue", trade_type);
	$("#OCCUPY_LMT_STATUS").combobox("setValue", OCCUPY_LMT_STATUS);
	$("[name=TRADE_TYPE]").val(trade_type);
	$("[name=OCCUPY_LMT_STATUS]").val(OCCUPY_LMT_STATUS);
	hideField();
	if (trade_type == "JH" && OCCUPY_LMT_STATUS == "Y") {
		$("#addBtn").attr("onclick", "createHandle('spfeMkUp.do?method=occupyJHMkUpHandle')");
	} else if (trade_type == "JH" && OCCUPY_LMT_STATUS == "N") {
		$("#addBtn").attr("onclick", "createHandle('spfeMkUp.do?method=notOccupyJHMkUpHandle')");
	} else if (trade_type == "GH" && OCCUPY_LMT_STATUS == "Y"){
		$("#addBtn").attr("onclick", "createHandle('spfeMkUp.do?method=occupyGHMkUpHandle')");
	} else if (trade_type == "GH" && OCCUPY_LMT_STATUS == "N"){
		$("#addBtn").attr("onclick", "createHandle('spfeMkUp.do?method=notOccupyGHMkUpHandle')");
	}
}

//隐藏字段
function hideField() {
	$("#JH0201").hide();
	$("#JH0202").hide();
	$("#JH0401").hide();
	$("#JH0501").hide();
	$("#GH0201").hide();
	$("#GH0202").hide();
	$("#GH0401").hide();
	$("#GH0501").hide();
	$("#GH0502").hide();
	$("#GH0503").hide();
	$("#GH0504").hide();
	$("#GH0505").hide();
}

//当业务类型代码改变时的操作
function changeBizTypeCode(code,isInit) {
	hideField();
	$("#REMARK").val("");
	if (trade_type == "JH") {
		if (code == "02") {//个人贸易结汇/购汇
			$("#JH0201").show();
			$("#JH0202").show();
		} else if (code == "04") {//资本项目结汇
			$("#JH0401").show();
		} else if (code == "05") {//通过支付机构的结汇
			$("#JH0501").show();
			$("#JH0502").hide();
		} else {
			//if(!isInit) $("#REMARK").val("请填写审核的凭证信息，同时，若外币现钞结汇当日累计超等值5000美元，需要在备注栏注明现钞来源证明");
		}
		if(code != "05"){
			$("#JH0502").show();
		}
	}else if (trade_type == "GH") {
		if (code == "02") {//个人贸易结汇/购汇
			$("#GH0201").show();
			$("#GH0202").show();
		} else if (code == "04") {//资本项目结汇
			$("#GH0401").show();
		} else if (code == "05") {//通过支付机构的结汇
			$("#GH0502,#GH0503,#GH0504,#GH0505").hide();
			$("#GH0501").show();
			$("input",$("[id^=GH050]:not([id=GH0501])")).validatebox({ 
				required:false
			});
		} else {
			//if(!isInit) $("#REMARK").val("请填写审核的凭证信息（境外个人购汇金额单笔不超过等值5000美元时，不需要填写审核凭证信息），若当日累计提钞金额超过等值10000美元，需要在备注栏中填写提钞备案表号。");
		}
		if(code != "05"){
			$("#GH0502,#GH0503,#GH0504,#GH0505").show();
			$("input:not([name=LCY_ACCT_NO])",$("[id^=GH050]:not([id=GH0501])")).validatebox({ 
				required:true
			});
		}
	}
}
function printNotify() {
	if (trade_type=="GH") {
		window.open("spfeMkup.do?method=occupyGHPrint&seqNo="+$("#SEQNO").val()+"&sfzy="+$("#OCCUPY_LMT_STATUS").val());
	} else if (trade_type=="JH") {
		window.open("spfeMkup.do?method=occupyJHPrint&seqNo="+$("#SEQNO").val()+"&sfzy="+$("#OCCUPY_LMT_STATUS").val());
	} 
}
</script>
</head>
<body>
	<form id="fo" action="" method="post">
	<input type="hidden" id = "pubDate" name = "pubDate" />
	<input type="hidden" id = "endDate" name = "endDate" />
	<input type="hidden" id = "pubReason" name = "pubReason" />
		<div region="north" style="overflow: hidden;" border="false"
			data-options=" collapsed:false">
			<div style="padding: 1px; background: #EFEFEF;" id="toolbarid">
				<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-add" onclick="">新增</a>
				<a id="saveBtn" name="saveBtn" href="javascript:void(0)"
					class="easyui-linkbutton" plain="true" iconCls="icon-save"
					onclick="saveOrHandle(false);">保存</a> <a id="handleBtn"
					name="handleBtn" href="javascript:void(0)"
					class="easyui-linkbutton" plain="true" iconCls="icon-ok"
					onclick="saveOrHandle(true);">提交</a> <a id="checkBtn"
					name="handleBtn" href="javascript:void(0)"
					class="easyui-linkbutton" plain="true" iconCls="icon-ok"
					onclick="doCheck();">复核</a> <a id="authBtn" name="handleBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-ok" onclick="doAuth();">授权</a>
					<a id="cancelBtn" name="cancelBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-del" onclick='doCancel();'>取消</a>
				<a id="listBtn" name="listBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-list" onclick="showTasklist();">列表</a>
				<a id="printBtn" name="printBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-print" onclick="printNotify()">打印</a>
			</div>
		</div>
		<div region="center" border="false">
			<div class="editBlock">
				<table width="99%">
					<tr>
						<td colspan="4" class="subtitle">基本信息</td>
						<input type='hidden' name='SIGNSTATUS' id='SIGNSTATUS' value='${mode.SIGNSTATUS }'/>
					</tr>
					<tbody>
						<tr>
							<th width="15%">业务流水号</th>
							<td width="25%"><input type="text" id="SEQNO" name="SEQNO"
								style="background-color: #E6E6E6;" value="${mode.SEQNO}"
								readonly class="inputText"
								 /></td>
							<th width="15%">业务参号</th>
							<td ><input type="text" id="REFNO" name="REFNO"
								style="background-color: #E6E6E6;" value="${mode.REFNO}"
								readonly class="inputText"
								 /></td>
						</tr>
						<tr>
						    <th width="15%"><span class="warning">*</span>交易类型</th>
							<td width="25%"><ebills:select id="TRADE_TYPE" name="TRADE_TYPE"
									value="${mode.TRADE_TYPE}" style="width:250px"
									diccode="TRADE_TYPE"  expAttribute='data-options="required:true,editable:false"'
									clazz="easyui-combobox"></ebills:select></td>
							<th width="15%"><span class="warning">*</span>是否占用额度</th>
							<td ><ebills:select id="OCCUPY_LMT_STATUS"  expAttribute='data-options="required:true,editable:false"'
									name="OCCUPY_LMT_STATUS" value="${mode.OCCUPY_LMT_STATUS}"
									style="width:250px" diccode="OCCUPY_LMT_STATUS"  
									clazz="easyui-combobox"></ebills:select>
							</td>
						</tr>
						<tr>
							<th width="15%"><span class="warning">*</span>业务类型</th>
							<td  width="25%"><ebills:select id="BIZ_TYPE_CODE" name="BIZ_TYPE_CODE"
									value="${mode.BIZ_TYPE_CODE}" style="width:250px"
									diccode="BIZ_TYPE_CODE"  expAttribute='data-options="required:true,editable:false" onchange="changeBizTypeCode(this.value,false);"'
									clazz="easyui-combobox" ></ebills:select>
							</td>
							<th width="15%"><span class="warning">*</span>证件类型</th>
							<td><ebills:select id="IDTYPE_CODE" name="IDTYPE_CODE"
									value="${mode.IDTYPE_CODE}" style="width:250px"
									diccode="IDTYPE_CODE"  expAttribute='data-options="required:true,editable:false";onchange="switchIdtypeCode(this.value);"'
									clazz="easyui-combobox"></ebills:select></td>
						</tr>
						<tr>
							<th width="15%"><span class="warning">*</span>证件号码</th>
							<td >
								<input type="text" id="IDCODE" name="IDCODE" validType = "idcode" maxlength="50"
								value="${mode.IDCODE}" class="easyui-validatebox inputText"
								data-options="required:true" />
							</td>
							<th width="15%">补充证件号码</th>
							<td>
								<input type="text" id="ADD_IDCODE" name="ADD_IDCODE"
								value="${mode.ADD_IDCODE}" class="easyui-validatebox inputText"
								   validType="addIdcode" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<th width="15%"><span class="warning">*</span>国家/地区</th>
							<td width="25%">
								<select name="CTYCODE" id="CTYCODE" data-options="required:true"  style="width: 250px" value="${mode.CTYCODE }"
								class="easyui-combobox">
									<option></option>
									<c:forEach  items="${countrys}" var ="country">
										<option value="${country.safeCode}"   ${mode.CTYCODE eq country.safeCode?'selected':''} >${country.safeCode}-${country.cnName}</option>
									</c:forEach>
								</select>
							</td>
							<th><span class="warning">*</span>业务办理渠道</th>
							<td >
								<ebills:select id="BIZ_TX_CHNL_CODE" name="BIZ_TX_CHNL_CODE"
									value="${mode.BIZ_TX_CHNL_CODE}" style="width:250px"
									diccode="bizTxChnlCode"  expAttribute='data-options="required:true,editable:false" '
									clazz="easyui-combobox" ></ebills:select>
									<input id="OCCUPY_LMT_STATUQuery"  type="button" idQuery = "N"
										onclick="queryIndividualFXSE()" class="inputButton"
										value="查询额度" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div>
			<c:if test="${OCCUPY_LMT_STATUS eq 'Y'}">				
				<c:if test="${empty transState or transState eq '0' or  transState eq '5'}">
					<!-- 
					<br/>
					<label><span><img src="resources/image/exclamation.png"  class="helpimg" /></span>&nbsp;个人分类状态为：<b>
						<font color="red" id="statusFont"></font>
				    -->
					</b></label><br>
					<label><span><img src="resources/image/exclamation.png"  class="helpimg" /></span>&nbsp;本年额度内已<c:if test="${TRADE_TYPE eq 'JH'}">结汇</c:if><c:if test="${TRADE_TYPE eq 'GH'}">购汇</c:if>金额（折美元）：<b><font color="red" id="usedFont"></font></b></label><br>
					<label><span><img src="resources/image/exclamation.png"  class="helpimg" /></span>&nbsp;本年额度内剩余可<c:if test="${TRADE_TYPE eq 'JH'}">结汇</c:if><c:if test="${TRADE_TYPE eq 'GH'}">购汇</c:if>金额（折美元）：<b><font color="red" id="unUsedFont"></font></b></label><br>
					<br/>
				</c:if>
			</c:if>
		</div>
		
		<c:if test="${TRADE_TYPE eq 'JH' and OCCUPY_LMT_STATUS eq 'Y' }">
			<jsp:include page="../spfeLmt/occupyJHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'JH' and OCCUPY_LMT_STATUS eq 'N' }">
			<jsp:include page="../spfeLmt/notOccupyJHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'GH' and OCCUPY_LMT_STATUS eq 'Y' }">
			<jsp:include page="../spfeLmt/occupyGHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'GH' and OCCUPY_LMT_STATUS eq 'N' }">
			<jsp:include page="../spfeLmt/notOccupyGHLmtHandle.jsp"></jsp:include>
		</c:if>
		<div class="editBlock">
			<table width="99%">
				<tr>
					<td colspan="4" class="subtitle">补录信息</td>
				</tr>
				<tbody>
					<tr>
						<th width="15%"><span class="warning">*</span>补录原因</th>
						<td style="border-top: 1px"  colspan="3"><ebills:select
								id="REIN_REASON_CODE" name="REIN_REASON_CODE" 
								value="${mode.REIN_REASON_CODE}" style="width:250px"
								diccode="reinReasonCode" expAttribute='data-options="required:true"'
								clazz="easyui-combobox"></ebills:select>
						</td>
					</tr>
					<tr>
						<th width="15%">补录说明</th>
						<td colspan="3" style="border-top: 1px"><textarea rows="1"
								clazz="easyui-validatebox" cols="1"
								style="height: 50px; width: 90%" id="REIN_REMARK"
								name="REIN_REMARK">${mode.REIN_REMARK}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<div class="editBlock" id="remsgid" style="display: none;">
			<table width="99%">
				<tr>
					<td colspan="4" class="subtitle">返回信息</td>
				</tr>
				<tbody>
					<tr>
						<th width="15%">返回代码</th>
						<td colspan="2"><input type="text" id="RECODE" name="RECODE"
							style="background-color: #E6E6E6;" value="${mode.RECODE}"
							disabled="disabled" class="inputText"
							 /></td>
					</tr>
					<tr>
					    <th width="15%">返回信息</th>
						<td colspan="2"><textarea rows="1" 
							cols="1" style="height: 50px; width: 98%" id="REMSG"
							maxlength="256" name="REMSG" value="${mode.REMSG}" disabled="disabled">${mode.REMSG}</textarea></td>
						
					</tr>
				</tbody>
			</table>
		</div>
</body>
</html>