<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑外管用户信息</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script language="javascript">
	 $(function(){
		 $(".easyui-combobox").combobox();
		 $(".easyui-datebox").datebox();
	 });

	function save() {
		if ($('#editform').form('validate') == true) {
			$.post('commonUserController.do?method=saveCommonOrgUser', $("#editform").serializeArray(), function(data) {
				$.messager.alert('系统提示', "保存成功", 'info'); 
			});
		}
	}
	
</script> 


</head>


<body >
	<form id="editform" action="" method="post">
		<div region="north" style="overflow:hidden;" border="false">
		    <div style="padding:1px;background:#EFEFEF;">
		        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
		           onclick="createHandle('commonUserController.do?method=editCommonOrgUser')">新增</a>
		        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
		        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
		           onclick="listHandle('commonUserController.do?method=commonUserList')">列表</a>
		    </div>
		</div>
		<div region="center" border="false">
		<div  class="editBlock">
		<table>
			<tbody>
				<tr>
					<td colspan=4 class="subtitle">基本信息</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>金融机构标识码
					</th>
					<td colspan=3>
						<input type="text" name='COMMON_ORG_CODE' maxlength="12" class="easyui-validatebox inputText"  
						 data-options="required:true" value="${commonUser.COMMON_ORG_CODE}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>柜员号
					</th>
					<td colspan=3>
						<input type="text" id="OPERNO" name='OPERNO' class="easyui-validatebox inputText"  
							data-options="required:true"  
							value="${commonUser.OPERNO}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>用户代码
					</th>
					<td colspan=3>
						<input type="text" id="COMMON_USER_CODE" name='COMMON_USER_CODE' maxlength="8" class="easyui-validatebox inputText"  data-options="required:true" 
							value="${commonUser.COMMON_USER_CODE}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>用户密码
					</th>
					<td colspan=3>
						<input type="text" id="PASSWORD" name='PASSWORD' class="easyui-validatebox inputText"  data-options="required:true" 
							value="" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</form>
</body>

</html>
