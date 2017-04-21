<%@page import="com.global.framework.util.SysUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css" media="print">.Noprint{DISPLAY:none}</style>
<title>确认书签署</title>
<script type="text/javascript">
function printit(){
	if(confirm('确认要打印吗？'))
	wb.execwb(7,1);
}
</script>
</head>
<body>
<OBJECT ID="wb" CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb" height="0" width="0" ></OBJECT>
<form id="fo" action="" method="post">
	<div>
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr><td align="center" style="font-size: 22px"><b>个人外汇管理“重点关注对象”确认书</b><br/></td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><b>${personName }</b></font>(${idType }号码：<font color="red"><b>${idCode }</b></font>)，被列为个人外汇管理“重点关注对象”。</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;关注期限：<font color="blue">${dueTime }</font>。</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;列入原因：<font color="blue">${reason }</font>。</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;本人已知晓并确认因发生异常外汇交易行为，被列为个人外汇管理“重点关注对象”。</td></tr>
			<tr><td style="font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;本人已知晓、理解《中华人民共和国外汇管理条例》及个人外汇管理法规规定，若再次发生违反外汇管理规定行为，将接受外汇局依法实施的调减个人结售汇年度总额、列入个人“关注名单”等管理措施，涉及逃税、洗钱等违法事项将由税务、司法机关等依法处理。</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td align="right" style="font-size: 14px;">确认人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr><td align="right" style="font-size: 14px;">确认日期：<%=SysUtils.getNowDate() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
		</table>
	</div>
	<div align="center">
		<center class=Noprint>
			<input type="button" value="打  印" onclick="printit()" />
		</center>
	</div>
</form>
</body>