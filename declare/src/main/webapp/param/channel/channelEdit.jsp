<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑渠道</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script language="javascript">
	 $(function(){
		 $(".easyui-combobox").combobox();
		 $(".easyui-datebox").datebox();
	 });

	function save() {
		if ($('#editform').form('validate') == true) {
			$.post('channelController.do?method=save', $("#editform").serializeArray(), function(data) {
				$("#channelId").val(data.channelId);
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
		           onclick="createHandle('channelController.do?method=edit')">新增</a>
		        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
		        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
		           onclick="listHandle('channelController.do?method=list')">列表</a>
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
						<span class="warning">*</span>渠道中文名称
					</th>
					<td colspan=3>
						<input type="text" name='channelCnName' class="easyui-validatebox inputText" 
							 data-options="required:true" value="${channel.channelCnName}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning"></span>渠道英文名称
					</th>
					<td colspan=3>
						<input type="text" name='channelEnName' class="easyui-validatebox inputText"  
						value="${channel.channelEnName}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>渠道代码
					</th>
					<td colspan=3>
						<input type="text" id="channelCode" name='channelCode' class="easyui-validatebox inputText"  
							data-options="required:true"  
							value="${channel.channelCode}" />
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>接入系统代码
					</th>
					<td colspan=3>
						<input type="text" id="reqSysCode" name='reqSysCode' class="easyui-validatebox inputText"  data-options="required:true" 
							value="${channel.reqSysCode}" />
<!-- 							validType="remote['channelController.do?method=checkReqSysCodeExist', 'param']" -->
					</td>
				</tr>
				<tr>
					<th width='15%'>
						<span class="warning">*</span>业务办理渠道代码
					</th>
					<td colspan=3>
						<select id="bizChnlCode" name="bizChnlCode" class="easyui-combobox" style="width:205px;" data-options="required:true">
								<option value=""></option>
								<c:forEach var="bizChnlCode" items="${bizChnlCodeList}">
									<option value="${bizChnlCode.code }" <c:if test="${bizChnlCode.code eq channel.bizChnlCode}">selected</c:if> >${bizChnlCode.code }-${bizChnlCode.value }</option>
								</c:forEach>
							</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</form>
</body>

</html>
