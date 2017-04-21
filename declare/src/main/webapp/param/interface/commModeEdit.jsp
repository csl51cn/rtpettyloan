<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑渠道通讯方式</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script language="javascript">
	 $(function(){
		 $(".easyui-combobox").combobox();
		 $(".easyui-datebox").datebox();
		 
		 var flag = "${flag}";
		 if (flag == "Y") {
			 $("#north").show();
		 } else {
			 $("#north").hide();
		 }
	 });

	function save() {
		if ($('#editform').form('validate') == true) {
			$.post('commModeController.do?method=saveOrUpdate', $("#editform").serializeArray(), function(data) {
				$.messager.alert('系统提示', "保存成功", 'info'); 
			});
		}
	}
	
	
</script> 


</head>


<body >
	<form id="editform" action="" method="post">
		<div id="north" region="north" style="overflow:hidden;" border="false">
		    <div style="padding:1px;background:#EFEFEF;">
		        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
		        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
		           onclick="listHandle('commModeController.do?method=toList')">列表</a>
		    </div>
		</div>
		<div region="center" border="false">
		<div  class="editBlock">
		<input type="hidden" id="channelId" name="channelId" value="${channel.channelId}" />
		<table>
			<tbody>
				<tr>
					<td colspan=4 class="subtitle">基本信息</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>渠道名称
					</th>
					<td colspan=3>
						<input type="text" name='channelCnName' class="easyui-validatebox inputText" 
							 disabled="disabled" value="${channel.channelCnName}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning"></span>通讯方式
					</th>
					<td colspan=3>
						<select id="commMode" name="commMode" class="easyui-combobox" style="width:205px;">
								<option value="SOCKET" 
									<c:if test="${commMode.commMode eq 'SOCKET'}">selected</c:if>
								>SOCKET</option>
								<option value="HTTP" <c:if test="${commMode.commMode eq 'HTTP'}">selected</c:if>>HTTP</option>
								<option value="MQ" <c:if test="${commMode.commMode eq 'MQ'}">selected</c:if>>MQ</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>报文格式
					</th>
					<td colspan=3>
						<select id="format" name="format" class="easyui-combobox" style="width:205px;">
								<option value="XML">XML</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>接入IP
					</th>
					<td colspan=3>
						<input type="text" name='ip' class="easyui-validatebox inputText"  data-options="required:true" value="${commMode.ip }"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</form>
</body>

</html>
