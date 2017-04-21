<%@page import="com.global.framework.util.SysUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../common/include.jsp"></jsp:include>
<title>风险提示函</title>
<script type="text/javascript">
	function getIsAgree(){
		return $("#isAgree").attr("checked") == "checked" ?"Y":"N";
	}
	function doPrint(){
		var personName = encodeURI(encodeURI($('#personName').val()));
		var reason = encodeURI(encodeURI("${reason}"));
		var idType = encodeURI(encodeURI("${idType}"));
		window.open("spfeLmt.do?method=riskCuePrint&idCode="+$('#idCode').val()+"&idType="+idType+"&personName=" + personName+"&dueTime=${dueTime}"+"&reason="+reason);
	}
</script>
</head>
<body>
<form id="fo" action="" method="post">
	<div style="height:300px;overflow-y:scroll;" >
		<input type="hidden" id="personName" value="${personName}">
		<input type="hidden" id="idCode" value="${idCode}">
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr><td align="center"><font size="24px"><b>“关注名单”告知书</b></font><br/></td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><b>${personName}</b></font>(${idType}号码：<font color="red"><b>${idCode}</b></font>)</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;据个人外汇管理相关规定，您办理的个人外汇业务，
				涉嫌以借用他人额度等方式规避额度及真实性管理，已被外汇局列入“关注名单”管理，
				列入期限自：<font color="blue">${dueTime }</font>。</td></tr>	
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;在关注期限内，您应凭相关真实性证明材料到银行办理个人结售汇业务。</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp注：本告知书仅用于对相关个人进行告知，无需银行及个人签章留存。</td></tr>
		</table>
	</div>
	<div class="editBlock" align="center">
		<input type="checkbox" name="isAgree"  id="isAgree" style="width:18px;height: 18px"> <font style="font-size: 20px">已告知</font>
		&nbsp;&nbsp;&nbsp;&nbsp;		
	</div>
</form>
</body>