<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售明细信息</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
 
$(function(){
	$("input,textarea").attr("disabled",true);
	$("select").combobox({"disabled":true});
	$(".easyui-datebox").datebox({disabled:true});
}); 
 
function getComboboxValue(id){
	return $("#"+id).combobox("getValue");
}

var trade_type = "${mode.TRADETYPE}";

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
		}
		if(code != "05"){
			$("#GH0502,#GH0503,#GH0504,#GH0505").show();
			$("input:not([name=LCY_ACCT_NO])",$("[id^=GH050]:not([id=GH0501])")).validatebox({ 
				required:true
			});
		}
	}
}


</script>
</head>
<body>
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="listBtn" href="spfeQuery.do?method=jshMsgList" class="easyui-linkbutton" plain="true" iconCls="icon-list">列表</a>
    </div>
</div>
	<form id="fo" action="" method="post">
		<div region="center" border="false">
			<div class="editBlock">
				<table width="99%">
					<tr>
						<td colspan="4" class="subtitle">基本信息</td>
					</tr>
					<tbody>
						<tr>
							<th width="15%">业务参号</th>
							<td width="35%"><input type="text" id="REFNO" name="REFNO"
								class="inputText" value="${mode.REFNO}" /></td>
							<th width="15%">业务办理时间</th>
							<td width="34%"><input type="text" id="BIZ_TX_TIME"
								name="BIZ_TX_TIME" class="inputText" value="${mode.BIZ_TX_TIME}" /></td>
						</tr>
						<tr>
							<th width="15%">交易类型</th>
							<td width="35%"><input type="text" id="TRADETYPE"
								name="TRADETYPE" class="inputText" value="${mode.TRADETYPENAME}" /></td>
						</tr>
						<tr>
							<th width="15%">业务类型</th>
							<td width="35%"><input type="text" id="BIZTYPE_CODE"
								name="BIZTYPE_CODE" class="inputText"
								value="${mode.BIZ_TYPE_NAME}" /></td>
							<th width="15%">证件类型</th>
							<td width="35%"><input type="text" id="IDTYPECODE"
								name="IDTYPECODE" class="inputText" value="${mode.IDTYPE_CODE}" />
							</td>
						</tr>
						<tr>
							<th width="15%">证件号码</th>
							<td><input type="text" id="IDCODE" name="IDCODE"
								value="${mode.IDCODE}" class="inputText" /></td>
							<th width="15%">补充证件号码</th>
							<td width="35%"><input type="text" id="ADD_IDCODE"
								name="ADD_IDCODE" value="${mode.ADD_IDCODE}" class="inputText" />
							</td>
						</tr>
						<tr>
							<th width="15%">国家/地区</th>
							<td width="35%"><input type="text" id="CTYNAME"
								name="CTYNAME" value="${mode.CTYCODE}-${mode.CTYNAME}"
								class="inputText" /></td>
							<th>${mode.TRADETYPE}业务办理渠道</th>
							<td><input type="text" id="BIZ_TX_CHNL_CODE"
								name="BIZ_TX_CHNL_CODE" value="${mode.BIZ_TX_CHNL_CODE}"
								class="inputText" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<c:if test="${mode.TRADETYPE eq 'JH'}">
			<jsp:include page="./jhLmtDetail.jsp"></jsp:include>
		</c:if>
		<c:if test="${mode.TRADETYPE eq 'GH'}">
			<jsp:include page="./ghLmtDetail.jsp"></jsp:include>
		</c:if>
	</form>
</body>
</html>