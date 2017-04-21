<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>复核</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">

var stepname="复核";

window.onload=function(){
	switch(window.parent.transState){
		case '1': stepname = "复核"; break;
		case '2': stepname = "授权"; break;
		default: stepname="复核";
	};
	$("#title").text(stepname+"意见");
};

function checkForm() {
	var isAgree = $("input[name='isAgree']:checked").val();
	var opinion = $("#opinion").val();
	if (isAgree) {
		if(isAgree == 'N') {
	    	if(opinion == undefined || opinion == "") {
	    		alert(stepname+"不通过时必须输入"+stepname+"意见");
	    		return false;
	    	}
		}
		var txnSerialNo = $("#txnSerialNo").val();
		var tradeNo = $("#tradeNo").val();
		var exPrm = window.parent.exPrm;
		if(!exPrm){
			exPrm = {};
		}
		var checkMsg = $.extend({},exPrm,{"txnSerialNo":txnSerialNo,"tradeNo":tradeNo,"isAgree":isAgree, "opinion":opinion});
		$.ajaxSetup({async:false});
		$.post(getUrl(tradeNo)+(window.parent.transState=='1'?'method=doCheckTask':'method=doAuthTask'),{checkMsg:JSON.stringify(checkMsg)}, function(data){
			if (data.status) {
				alert(stepname+"操作成功[" + (isAgree == 'Y' ? '通过' : '拒绝') + "]");
				$("#transtate").val(isAgree == 'Y' ? '2' : '5');
			} else {
				alert(stepname+"操作失败：" + data.errorMsg);
			}
		}); 
		$.ajaxSetup({async:true});
		return true;
	} else {
		alert("请确认是否通过");
    	return false;
	}
}


function getUrl(tradeNo){
	var url = "${url}";
	var indexofDo  = url.indexOf("?")+1;
	url = url.substring(0,indexofDo);
	url = "${pageContext.request.contextPath}/"+url;
	return url;
}

function getTranstate() {
	return $("#transtate").val();
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<form id="queryForm" action="#" method="post">
	<input type="hidden" id="txnSerialNo" name="txnSerialNo" value="${txnSerialNo }" />
	<input type="hidden" id="tradeNo" name="tradeNo" value="${tradeNo }" />
	<input type="hidden" id="transtate" name="transtate" />
	<table class="editBlock" style="width: 100%;">
		<tbody>
			<tr>
				<th width="10%">是否通过</th>
				<td width="20%">
					<label><input type="radio" id="isAgree"  name="isAgree" value="Y"/>通过</label>
					<label><input type="radio" id="isAgree" name="isAgree" value="N"/>不通过</label>
				</td>
			</tr>
			<tr>
				<th width="10%" id="title">复核意见</th>
				<td width="20%">
					<textarea cols="100" rows="2" class="textarea" id="opinion" name="opinion" style="width:250px" ></textarea>
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
</html>