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
<title>关注名单告知书</title>
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
	<div align="center">
		<center class=Noprint>
			<input type="button" value="打  印" onclick="printit()" />
		</center>
	</div>
</form>
</body>