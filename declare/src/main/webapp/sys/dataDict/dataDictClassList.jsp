<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典分类列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'dataDictController.do?method=queryDictClass',
        sortName: 'sortNo',
        sortOrder: 'asc',
        idField: 'classId',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'classCode', title: '字典分类编码', width: 100, sortable: true },
					{ field: 'className', title: '字典分类名称', width: 200, sortable: true },
					{ field: 'sortNo', title: '排序', width: 80, sortable: true },
					{field:'opt',title:'操作',width:80,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "dataDictController.do?method=editDictClass&classId="+rec.classId;
							var delUrl = "dataDictController.do?method=deleteDictClass&classId="+rec.classId;
							return '<span style="color:blue"><a href="###" onclick="editHandle(\''+editUrl+'\')">编辑</a>&nbsp;&nbsp;'+
								   '<a href="###" onclick="deleteHandle(\''+delUrl+'\',\'grid\')">删除</a></span>';
						}
					}
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onLoadSuccess: function(){
            setTimeout(function(){
            	selectRowEvent('grid');
            }, 10);    
    	}
    });
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('dataDictController.do?method=editDictClass')">新增</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 61px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">字典分类编码</th>
						<td widht="20%">
							<input type="text" id="classCode" name="classCode" class="inputText" value="${dictClass.classCode}" />
						</td>
						<th width="10%">字典分类名称</th>
						<td colspan="3">
							<input type="text" id="className" name="className" class="inputText" value="${dictClass.className}" />&nbsp;&nbsp;
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="代码类别列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>