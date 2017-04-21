<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇信息修改</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript"  src="${pageContext.request.contextPath }/resources/business/spfeValidator.js"></script>
<script type="text/javascript">
var transState = "${transState}";//流程状态
var seqno = "${mode.SEQNO}";
window.onload=function(){
	switch(transState){
		case '0': initHandle();break;
		case '1': initCheck();break;
		case '2': initAuth();break;
		case '4': initDetail();break;
		case '5': initHandle();break;
		case '6': initDetail();break;
		case '7': initDetail();break;
		default:initHandle();
	};
	
	initCancelBtn();
	initRemsg();
};

function doCancel() {
	doCancelTask(basePath,seqno,"spfeMdfService");
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
	initDisable();
}

/**
 * 初始化 授权
 */
function initAuth(){
	$("#addBtn,#saveBtn,#handleBtn,#checkBtn").linkbutton({disabled:true});
	initDisable();
}
/**
 * 初始化 完成信息
 */
function initDetail(){
	diableAction();
}

function initDisable() {
	$("input,textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
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
 *  禁止操作
 */
function diableAction(){
	$("#addBtn,#saveBtn,#handleBtn,#checkBtn,#authBtn,#cancelBtn").linkbutton({disabled:true});
	$("#OCCUPY_LMT_STATUQuery").hide();
	$("input,textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
	$("#printBtn").linkbutton({disabled:false});
}

//获取提交参数
function getPrm(){
	setDefalut();
	var p = {};
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

/**
 * 经办
 */
function saveOrHandle(isHandle) {
	var prm = getPrm();
	if(!prm.primaryBizNo){
		$.messager.alert("系统提示", "没有需要修改的业务", "error");
		return;
	}
	if(isHandle){
		if ($('#fo').form('validate')) {
			if(!validateSpfeLmt(prm,"spfeMdf")){//接口数据校验
				return;
			}
			if(!spfeMdfValit(prm)){
				return;
			}
			var REMARK = $("#REMARK").val();
			if (trade_type == "JH") {
				//5）	当结汇资金形态为“外币现钞”，且单笔外币现钞结汇金额超过等值5000美元（目前设定值，根据法规调整）时，备注栏不能为空，需要在备注栏注明现钞来源证明材料。
				//此种情况返回的回执报文中的状态码为“450020”，错误详细信息为“单笔现钞结汇金额超过等值5000美元，请审核现钞来源证明材料，并在备注栏注明！”。
				var TXAMTUSD = restoreAmt($("#TXAMTUSD").val());
				var SALEFX_SETTLE_CODE = $("#SALEFX_SETTLE_CODE").combobox("getValue");
				if (SALEFX_SETTLE_CODE == '01' && parseFloat(TXAMTUSD) > 5000 && !REMARK) {
					$.messager.alert("系统提示", "当结汇资金形态为“外币现钞”，单笔现钞结汇金额超过等值5000美元，请审核现钞来源证明材料，并在备注栏注明！", "error");
					return;
				}
			} 
			
			if (trade_type == "GH") {
				//5）	单笔提钞金额超过等值1万美元，备注栏不能为空，需要在备注栏注明提钞用途。
				//此种情况返回的回执报文中的状态码为“450021”，错误详细信息为“单笔提钞超过等值10000美元，请在备注栏注明提钞用途”。
				var PURFX_CASH_AMTUSD = restoreAmt($("#PURFX_CASH_AMTUSD").val());
				if (parseFloat(PURFX_CASH_AMTUSD) > 10000 && !REMARK) {
					$.messager.alert("系统提示", "单笔提钞超过等值10000美元，请在备注栏注明提钞用途！", "error");
					return;
				}
			}
			
			var BIZ_TYPE_CODE = $("#BIZ_TYPE_CODE").combobox("getValue");
			var IDTYPE_CODE = $("#IDTYPE_CODE").combobox("getValue");
			if(BIZ_TYPE_CODE=="05" && IDTYPE_CODE !="01"){
				$.messager.alert("系统提示", "当业务类型为通过支付机构的结汇/购汇时，证件类型只能是居民身份证", "error");
				return;
			}
			if(isHandle && (!validateSpfeLmt(prm,"spfeMdf"))){//接口数据校验
				return;
			}
			$.messager.confirm("系统提示", "确定要提交？", function(r){
				if (r) {
					doSpfeMdfHandle(isHandle,prm);
				}
			});
		}else{
			$("tbody",$('#fo')).show();
		}
	}else{
		doSpfeMdfHandle(isHandle,prm);
	}
}

function doSpfeMdfHandle(isHandle,prm){
	 $.post('spfeMdf.do?method='+(isHandle?'doHandle':'doSave'),prm, function(data) {
		 if(isHandle){
			diableAction();
		}
		$("#SEQNO").val(data.seqno);
		$("#BIZNO").val(data.bizno);
		$.messager.alert("系统提示", isHandle?"提交成功":"保存成功", "info");
		initTreeData();
	});
}


function spfeMdfValit(prm){
	if(prm.CANCEL_REASON=="06" && prm.CANCEL_REMARK==''){
		$.messager.alert("系统提示", "撤销原因为“其他”时不允许为空", "warning");
		return false;
	}
	if(prm.primaryBizNo ==""){
		$.messager.alert("系统提示", "请选择需要修改的业务编号", "warning");
		return false;
	}
	if(prm.MOD_REASON_CODE=='' && !prm.MODIFY_REMARK){
		$.messager.alert("系统提示", "修改原因代码为其他时，修改原因说明不能为空", "warning");
		return false;
	}
	return true;
}

var  exPrm ={};
/**
 * 复核
 */
function doCheck() {
	exPrm = getPrm();
	doCheckHandle("${mode.SEQNO}","000060");
}

/**
 * 授权
 */
function doAuth() {
	exPrm = getPrm();
	doAuthHandle("${mode.SEQNO}","000060");
}

/**
 * 查询业务
 */
function queryBusiness(txnSerialNo,refNo){
	if(!refNo && !txnSerialNo){
		$.messager.alert("系统提示", "业务参号与业务流水号不能都为空！", "warning");
		return;
	}
	if(!(refNo.length==29||txnSerialNo.length==20))
		{
		$.messager.alert("系统提示", "请您输入正确的业务参号或流水号!", "warning");
		return;
	}
	showContentInfo(txnSerialNo,refNo);
}
var old_LCY_ACCT_NO = "";
$(document).ready(function(){
	if (seqno != "") {
		showSpfeMdfTask();
		showContentInfo(seqno,"${mode.REFNO}","${mode.tradeNo}",true);
	}else {
		$("#spfeMdfTask").hide();
	}
	
	//修改原因为币种错误时，不允许修改个人外汇账户账号
	$("#MOD_REASON_CODE").combobox({
		onSelect:function(row){
			if (row.value == '02') {
				$("#LCY_ACCT_NO").val(old_LCY_ACCT_NO);
				$("#LCY_ACCT_NO").attr("readonly", "readonly");
			}
		}
	});
});

function showSpfeMdfTask() {
	$("#spfeMdfTask").show();
}

function showContentInfo(txnSerialNo,refNo,tradeNo,isInit) {
	var prm = {"txnSerialNo":txnSerialNo,"refNo":refNo,"tradeNo":tradeNo, "transState":transState};
	$.post("${basePath}/spfeMdf.do?method=spfeLmtEdit",prm,function(d){
		$("#spfeLmtCtxFrame").html(d);
		$.parser.parse();
		if (isInit) {
			initDisable();
		}
		var txcode = $("#TX_CODE").val();
		changeJSHZJSX(trade_type, $("#BIZ_TYPE_CODE").combobox("getValue"), $("#IDTYPE_CODE").combobox("getValue"));
		$("#TX_CODE").combobox("setValue", txcode);
		old_LCY_ACCT_NO = $("#LCY_ACCT_NO").val();//取得修改前的个人外汇账户账号
	});
}
/**
 * 从dialog选择任务进行修改
 */
function doDialogTask(txnSerialNo,refNo, tradeNo){
	$("#refNoSearch").val(refNo);
	$("#bizNosearch").val(txnSerialNo);
	showContentInfo(txnSerialNo,refNo,tradeNo);
}
function printNotify() {
	if (trade_type=="GH") {
		window.open("spfeMdf.do?method=occupyGHPrint&seqNo="+$("#SEQNO").val());
	} else if (trade_type=="JH") {
		window.open("spfeMdf.do?method=occupyJHPrint&seqNo="+$("#SEQNO").val());
	} 
}
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 100%; height: 520px;"  id="viewContext">
		<div data-options="region:'north',split:false" style="height: 30px;"
			id="toolbarid">
			<div style="padding: 1px; background: #EFEFEF;">
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
					<a id="listBtn" name="listBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-list" onclick="showTasklistHis();">查询业务数据</a>
					<a id="printBtn" name="printBtn"
					href="javascript:void(0)" class="easyui-linkbutton" plain="true"
					iconCls="icon-print" onclick="printNotify()">打印</a>
			</div>
		</div>
		<div data-options="region:'center'" style="background: #eee;">
			<div class="easyui-layout" style="width: 100%; height: 100%;">
			<form id="fo" action="" method="post">
			<c:if test="${empty transState}">
					<div data-options="region:'north',split:false"  title="个人结售信息修改" style="height: 60px;">
					<div class="editBlock">
					<table>
						<tr>
							<th width="15%">前序业务参号</th>
							<td width="25%">
								<input type="text"  name="refNoSearch"  id="refNoSearch"  class="inputText" />
							</td>
							<th width="15%">前序业务交易流水号</th>
							<td>
								<input type="text"  name="bizNosearch" id="bizNosearch" class="inputText" />
								<input id="businessQuery" type="button" class="inputButton" value="查询"  onclick="queryBusiness(bizNosearch.value,refNoSearch.value);"  />
							</td>
						</tr>
					</table>
					</div>
				</div>
			</c:if>
				<div id="spfeMdfTask" data-options="region:'south',split:false" style="height: 100px;" >
					<jsp:include page="./spfeMdfTask.jsp"></jsp:include>
				</div>
				<div data-options="region:'center'">
					<div name="spfeLmtCtxFrame" id="spfeLmtCtxFrame"
						style="overflow-x: hidden; border: 0; width: 99%;" ></div>
				</div>
				</form>
			</div>
		</div>
	</div>
	
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