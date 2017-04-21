<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户修改密码</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script src="${basePath}/resources/js/passwordStrength-min.js" type="text/javascript"></script>
<style type="text/css">
	.passwordStrength{

	}
	.passwordStrength b{
		font-weight:normal;
	}
	.passwordStrength b,.passwordStrength span{
		display:inline-block; 
		vertical-align:middle;
		line-height:16px;
		line-height:18px\9;
		height:16px;
	}
	.passwordStrength span{
		width:45px; 
		text-align:center; 
		background-color:#d0d0d0; 
		border-right:1px solid #fff;
	}
	.passwordStrength .last{
		border-right:none;
	}
	.passwordStrength .bgStrength{
		color:#fff;
		background-color:#71b83d;
	}
</style>
<script type="text/javascript">
$(function(){
	$("#fo").Validform({
		tiptype:3,
		btnSubmit:"#btn_save",
		usePlugin:{
			passwordstrength:{
				minLen:6,
				maxLen:16
			}
		},
		callback:function(){
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "userController.do?method=changePwd",
				data : $("#fo").serializeArray(),
				async : true,
				success : function(data) {
					alert("保存成功");
					if(data.status){
						frameElement.api.get("changePwd",1).close();
					}
				}
			});
		}
	});
});

function toLogin() {
	window.location.href="${basePath}/logon.do";
}
</script>
<body>
<form id="fo" method="post" action="#" >
	<table cellpadding="0" cellspacing="0" class="editBlock" width="100%">
		<tbody>
			<tr>
				<th width="10%">用户名</th>
				<td><input type="text" id="userCode" name="userCode" class="inputText_disabled" value="<%=request.getParameter("userCode") %>" readonly="readonly" /></td>
			</tr>
			<tr>
				<th width="10%"><span class="warning">*</span>当前密码</th>
				<td><input type="password" id="oldPwd" name="oldPwd" class="inputText" ajaxurl="userController.do?method=checkOldPwd" datatype="*6-16" nullmsg="请输入当前密码" errormsg="当前密码输入错误"/></td>
			</tr>
			<tr>
				<th width="10%"><span class="warning">*</span>新密码</th>
				<td><input type="password" id="newPwd" name="newPwd" class="inputText" plugin="passwordStrength" datatype="*6-16" nullmsg="请输入新密码" errormsg="密码为6-16位长度的字符"/>
				<br><span class="passwordStrength">密码强度： <span>弱</span><span>中</span><span class="last">强</span></span>
				</td>
			</tr>
			<tr>
				<th width="25%"><span class="warning">*</span>重复密码</th>
				<td><input type="password" id="password" name="password" class="inputText" recheck="newPwd"  datatype="*6-16" nullmsg="请输入重复密码" errormsg="两次输入的密码不一致！"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" id="btn_save" value="保存"/>
				<c:if test="${flag eq 'Y' }">
					<input type="button" id="btn_login" onclick="toLogin()" value="返回登录"/>
				</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</form>
</body>
</html>
