<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇额度修改</title>
<script type="text/javascript">

/**
 * 初始化 编辑
 */
function initEdit(){
	var disabledcomboids = "#OCCUPY_LMT_STATUS,#BIZ_TYPE_CODE,#IDTYPE_CODE,#CTYCODE,#SIGNSTATUS,#TYPE_STATUS,#BIZ_TX_CHNL_CODE";
	var disableinputids= "#IDCODE,#ADD_IDCODE,#AMT_USD,#AMT_BALANCE_USD";
	$(disableinputids).attr("disabled",true);
	$(disabledcomboids).combobox({"disabled":true});
	$("#BIZ_TX_TIME").datebox({disabled:true});
}


function initEvent(){
	$("#BIZ_TYPE_CODE").combobox({
		onSelect:function(row){
			if(!transState || transState=="0"  || transState=="5"){
				changeBizTypeCode(row.value);
			}
		}
	});
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
	$("#IDTYPE_CODE").combobox({
		onChange:function(n,o){
			switchIdtypeCode(n);
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
	
	$.post("${pageContext.request.contextPath}/spfeQuery.do?method=spfeAmtDataQuery",prm,function(spfeAmtQuery){
		if(spfeAmtQuery && spfeAmtQuery!="null"){
			spfeAmtQuery = $.parseJSON(spfeAmtQuery);
		}else if(spfeAmtQuery.indexOf("#document")!=-1){
			$.messager.alert("系统提示",spfeAmtQuery,"error");
			return;
		}else{
			spfeAmtQuery = {amtBalanceUsd:"", amtUsd:"", typeStatus:"" };
		}
		$("#statusFont").html(spfeAmtQuery.typeStatus+"<input type='hidden' name='TYPE_STATUS' value='"+spfeAmtQuery.typeStatusCode+"'/><input type='hidden' name='SIGNSTATUS' value='"+spfeAmtQuery.signStatusCode+"'/>");
		$("#usedFont").html(spfeAmtQuery.amtUsd+"<input type='hidden' name='AMT_USD' value='"+spfeAmtQuery.amtUsd+"'/>");
		$("#unUsedFont").html(spfeAmtQuery.amtBalanceUsd+"<input type='hidden' name='AMT_BALANCE_USD' value='"+spfeAmtQuery.amtBalanceUsd+"'/>");
	});
	$("#OCCUPY_LMT_STATUQuery").attr("idQuery","Y");
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
		$("#addBtn").attr("onclick", "createHandle('spfeLmt.do?method=occupyJHLmtHandle')");
	} else if (trade_type == "JH" && OCCUPY_LMT_STATUS == "N") {
		$("#addBtn").attr("onclick", "createHandle('spfeLmt.do?method=notOccupyJHLmtHandle')");
	} else if (trade_type == "GH" && OCCUPY_LMT_STATUS == "Y"){
		$("#addBtn").attr("onclick", "createHandle('spfeLmt.do?method=occupyGHLmtHandle')");
	} else if (trade_type == "GH" && OCCUPY_LMT_STATUS == "N"){
		$("#addBtn").attr("onclick", "createHandle('spfeLmt.do?method=notOccupyGHLmtHandle')");
	}
	initEvent();
	initEdit();
	<c:if test="${not empty mode.BIZ_TYPE_CODE}">
		changeBizTypeCode("${mode.BIZ_TYPE_CODE}");
	</c:if>
	if($("#BIZ_TYPE_CODE").combobox("getValue")=="01"){ 
		$("input:not([name=LCY_ACCT_NO],[name=LCY_ACCTNO_CNY])",$("[mrk*=GH05]")).validatebox({ 
			required:true
		});
	}
	
	//显示修改原因
	showSpfeMdfTask();
	$("#refNoSearch").val("${mode.REFNO}");
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
function changeBizTypeCode(code) {
	if (trade_type == "JH") {
		if (code == "02") {//个人贸易结汇/购汇
			hideField();
			$("#JH0201").show();
			$("#JH0202").show();
		} else if (code == "04") {//资本项目结汇
			hideField();
			$("#JH0401").show();
		} else if (code == "05") {//通过支付机构的结汇
			hideField();
			$("#JH0501").show();
			$("#JH0502").hide();
		} else {
			hideField();
			//$("#REMARK").val("请填写审核的凭证信息，同时，若外币现钞结汇当日累计超等值5000美元，需要在备注栏注明现钞来源证明");
		}
		if(code != "05"){
			$("#JH0502").show();
		}
	}else if (trade_type == "GH") {
		if (code == "02") {//个人贸易结汇/购汇
			hideField();
			$("#GH0201").show();
			$("#GH0202").show();
		} else if (code == "04") {//资本项目结汇
			hideField();
			$("#GH0401").show();
		} else if (code == "05") {//通过支付机构的结汇
			hideField();
			$("#GH0502,#GH0503,#GH0504,#GH0505").hide();
			$("#GH0501").show();
			$("input",$("[id^=GH050]:not([id=GH0501])")).validatebox({ 
				required:false
			});
		} else {
			hideField();
			//$("#REMARK").val("请填写审核的凭证信息（境外个人购汇金额单笔不超过等值500美元时，不需要填写审核凭证信息），若当日累计提钞金额超过等值10000美元，需要在备注栏中填写提钞备案表号。");
		}
		if(code != "05"){
			$("#GH0502,#GH0503,#GH0504,#GH0505").show();
			$("input:not([name=LCY_ACCT_NO])",$("[id^=GH050]:not([id=GH0501])")).validatebox({ 
				required:true
			});
		}
	}
}
/*************************************初始化方法启动*************************************************/
init();

</script>
</head>
<body>
	<form id="fo" action="" method="post">
	<input type="hidden" id="tradeNo" name="tradeNo" value="${mode.tradeNo}"/>
		<div region="north" style="overflow: hidden;" border="false"
			data-options=" collapsed:false">
			<div style="padding: 1px; background: #EFEFEF;" id="toolbarid">
			</div>
		</div>
		<div region="center" border="false">
			<div class="editBlock">
				<table width="99%">
					<tr>
						<td colspan="4" class="subtitle">基本信息</td>
						<input type="hidden"  name="primaryBizNo"  id="primaryBizNo"  value="${mode.primaryBizNo }"/>
						<input type="hidden"  name="tradeType"  id="tradeType"  value="${mode.TRADE_TYPE }"/>
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
									diccode="TRADE_TYPE"   expAttribute='data-options="required:true,editable:false"'
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
							<td width="25%"><ebills:select id="BIZ_TYPE_CODE" name="BIZ_TYPE_CODE"
									value="${mode.BIZ_TYPE_CODE}" style="width:250px"
									diccode="BIZ_TYPE_CODE"  expAttribute='data-options="required:true,editable:false" onchange="changeBizTypeCode(this.value);"'
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
							<td width="25%">
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
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${TRADE_TYPE eq 'JH' and OCCUPY_LMT_STATUS eq 'Y' }">
			<jsp:include page="./occupyJHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'JH' and OCCUPY_LMT_STATUS eq 'N' }">
			<jsp:include page="./notOccupyJHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'GH' and OCCUPY_LMT_STATUS eq 'Y' }">
			<jsp:include page="./occupyGHLmtHandle.jsp"></jsp:include>
		</c:if>
		<c:if test="${TRADE_TYPE eq 'GH' and OCCUPY_LMT_STATUS eq 'N' }">
			<jsp:include page="./notOccupyGHLmtHandle.jsp"></jsp:include>
		</c:if>
	</form>
</body>
</html>