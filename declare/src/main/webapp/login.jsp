<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="cqchenf@qq.com" />
<meta name="Copyright" content="2007-2012 All Rights Reserved " />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>黑名单管理系统v1.0</title>
<link href="resources/css/login.css" rel="stylesheet" type="text/css" />
<script src="resources/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="resources/js/sys.login.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#userCode").focus();
	});
	
	
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		/* if (e && e.keyCode == 27) {
			// 按 Esc             
		}
		if (e && e.keyCode == 113) { // 按 F2  
		} */
		if (e && e.keyCode == 13) { 
			login();
		}
	};

	function login() {
		if (vaildateLoginForm()) {
			$("#loginForm").attr("action", "main.do");
			$("#loginForm").submit();
		}
	}
</script>
</head>

<BODY>
	<DIV class=second_body>
		<FORM id="loginForm" method="post" action="">
			<TABLE border=0 width=300>
				<TBODY>
				<div id="msg" style="color:#f00;" ></div>
					<TR>
						<TD width=55><SPAN>操作员：</SPAN></TD>
						<TD colSpan=2><INPUT class=inputText type=text id=userCode name=userCode value=super></TD>
					</TR>
					<TR>
						<TD>密&nbsp;&nbsp;&nbsp;码：</TD>
						<TD colSpan=2><INPUT class=inputText type=password id=password name=password value=123456></TD>
					</TR>
					<TR>
						<TD>验证码：</TD>
						<TD colSpan=2>
							<input type="text" class=validateText name="validateCode" id="validateCode" maxlength="4">
							<img id="validateCodeImg" src="generateCode.do" onclick="this.src='generateCode.do?param='+Math.random();"
								width="60" height="23" align="top" style="cursor: pointer;" title="换一张">
							<a onclick="generateCode();" style="cursor: pointer"> 看不清?</a>
						</TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
					</TR>
					<TR align=middle>
						<TD colSpan=3><INPUT class="login_button" value="登录" type="button" onclick="login();"><INPUT
								class=reset_botton onclick=this.form.reset(); value=重置
								type=button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
					</TR>
				</TBODY>
			</TABLE>
		</FORM>
	</DIV>
	<div align="center" style="color: #FFF">Copyright &copy ThinkTerm All rights reserved.</div>
</BODY>
</html>