<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇信息撤销</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/jquery-ztree-3.0/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/jquery-ztree-3.0/js/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/jquery-ztree-3.0/js/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript"  src="${basePath }/resources/business/spfeValidator.js"></script>
<script type="text/javascript">
var seqno = "${mode.SEQNO}";
var transState = "${transState}";//流程状态
	window.onload = function() {
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
		initCancelBtn();
		initRemsg();
	};
	
$(function(){
	if (seqno != "") {
		showSpfeMdfTask();
		showContentInfo("${mode.SEQNO}","${mode.refNo}", "${mode.tradeNo}");
	}else {
		$("#spfeCsrTask").hide();
	}
});

function showSpfeMdfTask() {
	$("#spfeCsrTask").show();
}

function doCancel() {
	doCancelTask(basePath,seqno,"spfeCsrService");
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
	function initHandle() {
		$("#authBtn,#checkBtn,#printBtn").linkbutton({
			disabled : true
		});
	}

	/**
	 * 初始化 复核
	 */
	function initCheck() {
		$("#addBtn,#saveBtn,#handleBtn,#authBtn").linkbutton({
			disabled : true
		});
		initDisable();
	}

	/**
	 * 初始化 授权
	 */
	function initAuth() {
		$("#addBtn,#saveBtn,#handleBtn,#checkBtn").linkbutton({
			disabled : true
		});
		initDisable();
	}
	
	/**
	 * 初始化 完成信息
	 */
	function initDetail() {
		initDisable();
	}
	/**
	 * 禁止操作
	 */
	function diableAction() {
		$("#addBtn,#saveBtn,#handleBtn,#checkBtn,#authBtn,#cancelBtn").linkbutton({disabled:true});
		$("input,textarea").attr("disabled", true);
		$("select").combobox({
			"disabled" : true
		});
		$(".easyui-datebox").datebox({
			disabled : true
		});
	}
	
	//初始化返回信息
	function initRemsg() {
		if (transState == '4' || transState == '7'){
			$("#remsgid").show();
		} else {
			$("#remsgid").hide();
		}
	}
	
	function initDisable() {
		$("input,textarea").attr("disabled",true);
		$("#businessQuery").attr("disabled",false);
		$("select").combobox({"disabled":true});
		$(".easyui-datebox").datebox({disabled:true});
		$("#printBtn").linkbutton({disabled:false});
	}
	
	var exPrm =  {};
	//获取提交参数
	function getPrm(){
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
		var refno= $("#refNo").val();
		p.refNo = refno;
		return p;
	}
	/**
	 * 经办
	 */
	function saveOrHandle(isHandle) {
		var prm = getPrm();
		if(!prm.primaryBizNo){
			$.messager.alert("系统提示", "没有需要撤销的业务", "error");
			return;
		}
		if(isHandle){
			if(!$('#doTask').form('validate')){
				return;
			}
			if(prm.CANCEL_REASON=="06" && prm.CANCEL_REMARK==''){
				$.messager.alert("系统提示", "撤销原因为“其他”时不允许为空", "warning");
				return;
			}
			$.messager.confirm("系统提示", "确定要提交？", function(r){
				if (r) {
					doSpfeCsrHandle(isHandle,prm);
				}
			});
		}else{
			doSpfeCsrHandle(isHandle,prm);
		}
		
	}

	function doSpfeCsrHandle(isHandle,prm){
		$.post('spfeCsr.do?method=' + (isHandle ? 'doHandle' : 'doSave'), prm, function(data) {
			if(isHandle){
				diableAction();
			}
			$("#SEQNO").val(data.seqno);
			$.messager.alert("系统提示", isHandle?"提交成功":"保存成功", "info");
			initTreeData();
		});
	}
	
	/**
	 * 复核
	 */
	function doCheck() {
		exPrm = getPrm();
		doCheckHandle("${mode.SEQNO}", "000050");
	}

	/**
	 * 授权
	 */
	function doAuth() {
		exPrm = getPrm();
		doAuthHandle("${mode.SEQNO}", "000050");
	}
	
	function showContentInfo(txnSerialNo,refNo,tradeNo,isInit) {
		var prm = {"txnSerialNo":txnSerialNo,"refNo":refNo,"tradeNo":tradeNo, "transState":transState};
		$.post("${basePath}/spfeCsr.do?method=findSpfeInfo",prm,function(d){
			$("#spfeLmtCtxFrame").html(d);
			$.parser.parse();
			initDisable();
			$("#CANCEL_REMARK,#refNoSearch,#bizNosearch").attr("disabled",false);
			$("#CANCEL_REASON").combobox({
				"disabled" : false
			});
			//$("#SEQNO").val(seqno);
		});
	}
	
	/**
	 * 查询业务
	 */
	function queryBusiness(bizNo,refNo){
		if(!refNo && !bizNo){
			$.messager.alert("系统提示", "业务参号与交易流水号不能都为空！", "warning");
			return;
		}
		showContentInfo(bizNo,refNo);
	}

	/**
	 * 从dialog选择任务进行修改
	 */
	function doDialogTask(bizNo,refNo){
		$("#refNoSearch").val(refNo);
		$("#bizNosearch").val(bizNo);
		showContentInfo(bizNo,refNo);
	}
	function printNotify() {
		if (trade_type=="GH") {
			window.open("spfeCsr.do?method=occupyGHPrint&seqNo="+$("#SEQNO").val());
		} else if (trade_type=="JH") {
			window.open("spfeCsr.do?method=occupyJHPrint&seqNo="+$("#SEQNO").val());
		} 
	}
</script>
</head>
<body>

	<div class="easyui-layout" style="width: 100%; height: 520px;">
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
				<c:if test="${empty transState}">
					<div data-options="region:'north',split:false"  title="个人结售汇信息撤消" style="height: 60px;">
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
				<div id="spfeCsrTask" data-options="region:'south',split:false" style="height: 100px;">
					<jsp:include page="./spfeCsrTask.jsp"></jsp:include>
				</div>
				<div data-options="region:'center'">
					<!-- <iframe name="spfeCtxFrame" id="spfeCtxFrame" onload="spfeCtxFrameOnload();"
						style="border: 0; width: 99%; height: 100%" ></iframe> -->
						<div name="spfeLmtCtxFrame" id="spfeLmtCtxFrame"
						style="overflow-x: hidden; border: 0; width: 99%;" ></div>
				</div>
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