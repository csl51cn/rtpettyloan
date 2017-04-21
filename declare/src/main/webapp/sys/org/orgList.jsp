<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'orgController.do?method=query',
        sortName: 'sortNo',
        sortOrder: 'asc',
        idField: 'orgId',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'orgId', title: '机构编号', width: 60, sortable: true },
					{ field: 'orgCode', title: '核心机构号', width: 60, sortable: true },
					{ field: 'orgName', title: '机构名称', width: 150, sortable: true },
					{ field: 'orgShortName', title: '机构简称', width: 100, sortable: true },
					{ field: 'parentOrgName', title: '上级机构', width: 100, sortable: true },
					{ field: 'orgLevel', title: '机构层级', width: 60, sortable: true, formatter:function(value){
						var name = '';
						$.post('${pageContext.request.contextPath}/param/paramCommonController.do?method=getDatadictByValue&catalogCode=ORG_LEVEL&dictCode='+value, function(data){
							name = data.dictName;
						});
						return name; 
					} },
					{ field: 'swiftCode', title: 'swiftCode', width: 100, sortable: true },
					//{ field: 'bankCode', title: '金融机构码', width: 100, sortable: true },
					/* { field: 'isDeclareOrg', title: '是否申报机构', width: 80, sortable: true, formatter:function(value){
						if (value == "Y") {
							return "是";
						}
						return "否";
					} }, */
					{ field: 'status', title: '状态', width: 30, sortable: true, sortable: true, formatter:function(value){
						if(value=='Y'){
							return '<span><img src="${basePath}/resources/images/onSuccess.gif" title="激活"/></span>';
						}else{
							return '<span><img src="${basePath}/resources/images/onError.gif" title="停用"/></span>';
						} 
					} },
					{field:'opt',title:'操作',width:80,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "orgController.do?method=edit&orgId="+rec.orgId;
							return '<span style="color:blue"><a href="###" onclick="editHandle(\''+editUrl+'\')">编辑</a>&nbsp;&nbsp;'+
								   '<a href="###" onclick="deleteOrg('+rec.orgId+', '+rec.parentOrgId+')">删除</a></span>';
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

function deleteOrg(orgid, parentOrgId) {
	if (parentOrgId == "") {
		$.messager.alert("系统提示", "该机构为顶级机构不允许删除", "error");
		return ;
	}
	$.messager.confirm("系统提示", "删除机构会删除该机构下的所有用户信息,您确定要删除该机构吗？", function(r) {
		if (r) {
			$.post("orgController.do?method=delete&orgId="+orgid, function(data) {
				$.messager.alert("系统提示", "删除操作成功", "info");
				$('#grid').datagrid('reload');
			});
		}
	});
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('orgController.do?method=edit')">新增</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 91px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">机构编号</th>
						<td width="20%">
							<input type="text" id="orgId" name="orgId" class="inputText" value="${org.orgId}" />
						</td>
						<th width="10%">核心机构号</th>
						<td>
							<input type="text" id="orgCode" name="orgCode" class="inputText" value="${org.orgCode}" />
						</td>
					</tr>
					<tr>
						<th width="10%">机构名称</th>
						<td colspan="3">
							<input type="text" id="orgName" name="orgName" class="inputText" value="${org.orgName}" />
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="机构列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>