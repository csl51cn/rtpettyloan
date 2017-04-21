<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑数据字典代码</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
function save() {
	if ($('#fo').form('validate') == true) {
		$.post('dataDictController.do?method=saveDataDict', $("#fo").serializeArray(), function(data) {
			$("#dictId").val(data.dictId);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
}
</script>
<body>
<form id="fo" action="" method="post">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('dataDictController.do?method=editDataDict')">新增</a>
        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
           onclick="listHandle('dataDictController.do?method=listDataDict')">列表</a>
    </div>
</div>
<div region="center" border="false">
<div class="editBlock">
	<input type="hidden" id="dictId" name="dictId" value="${dataDict.dictId}" />
		<table>
			<tr>
				<td colspan="4" class="subtitle">基本信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%" ><span class="warning">*</span>字典类别</th>
					<td colspan="3" >
						<input class="easyui-combogrid" id="classId" name="classId" style="width:250px;"
								data-options="
									panelWidth: 400,
									idField: 'classId',
									textField: 'className',
									url: 'dataDictController.do?method=getDataDictClassList',
									columns:[[
										{field:'classCode',title:'类别代码',width:100},
										{field:'className',title:'类别名称',width:180}
									]],
									fitColumns: true,
									required:true" value="${dataDict.classId}"/>
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>选项编码</th>
					<td colspan="3" >
						<input type="text" id="dictCode" name="dictCode" value="${dataDict.dictCode}" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>选项名称</th>
					<td colspan="3" >
						<input type="text" id="dictName" name="dictName" value="${dataDict.dictName}" class="easyui-validatebox inputText" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th width="15%" >排序</th>
					<td colspan="3" >
						<input type="text" id="sortNo" name="sortNo" value="${dataDict.sortNo}" class="easyui-numberbox inputText" data-options="required:false"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</form>
</body>
</html>
