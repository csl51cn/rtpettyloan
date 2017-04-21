<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑接口定义</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script language="javascript">
	 $(function(){
		 $(".easyui-combobox").combobox();
		 $(".easyui-datebox").datebox();
		 var checkedFaceCodes = "${channelFaceCodes}";
		 var checkedFaceCodeList = checkedFaceCodes.split(",");
		 $("#faceCodes").combogrid("setValues", checkedFaceCodeList);
	 });

	function save() {
		if ($('#editform').form('validate') == true) {
			var channelId = $("#channelId").val();
			var faceCodeList = $("#faceCodes").combogrid("getValues");
			if (faceCodeList == null || faceCodeList.length == 0) {
				alert("请选择接口名称");
				return false;
			}
			var list = {"channelId":channelId, "faceCodes":faceCodeList.join(",")};
			$.post("${pageContext.request.contextPath}/param/interfaceController.do?method=save", {"faceCodeList":JSON.stringify(list)}, function(data){
				alert("保存成功");
			});
			return true;
		}
		return false;
	}
</script> 


</head>


<body >
	<form id="editform" action="" method="post">
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
						<span class="warning"></span>接口名称
					</th>
					<td colspan=3>
						<select id="faceCodes" name="faceCodes" class="easyui-combogrid" style="width:250px" data-options="
								panelWidth: 450,
								multiple: true,
								sortName : 'sortNo',
								sortOrder : 'asc',
								idField: 'code',
								textField: 'name',
								url: 'interfaceController.do?method=getFaceCodes',
								columns: [[
									{field:'ck',checkbox:true},
									{field:'code',title:'接口代码',width:70},
									{field:'name',title:'接口名称',width:200}
								]],
								fitColumns: true,
								editable: false,
								pageSize: 15,
								pagination: false
							">
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
