<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<style type="text/css">
	table{border-collapse:separate;}
</style>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	$('#grid').treegrid({
		fit:true,
		nowrap: false,
		rownumbers: true,
		animate:true,
		fitColumn:true,
		collapsible:true,
		url:'menuController.do?method=query',
		idField:'menuId',
		treeField:'menuName',
		columns:[[
			{ field: 'menuName', title: '菜单名称', width: 300},
			{ field: 'parentMenuName', title: '上级菜单', width: 150},
			{ field: 'sortNo', title: '排序', width: 60},
			{ field: 'isFunction', title: '是否功能', width: 80, formatter:function(rowIndex){
				if(rowIndex=='Y'){
					return '是';
				}else{
					return '否';
				}
			}},
			{ field: 'accessUrl', title: '请求URL', width: 300},
			{field:'opt',title:'操作',width:200,align:'center', rowspan:2,
				formatter:function(value,rec){
					var delUrl = "menuController.do?method=delete&menuId="+rec.menuId;
					return '<span style="color:blue"><a href="###" onclick="createSubMenu('+rec.menuId+',\''+rec.menuName+'\',\''+rec.isFunction+'\')">增加下级菜单</a>&nbsp;&nbsp;'+
						'<a href="###" onclick="editMenu(\''+rec.menuId+'\', \'\', \'\')">编辑</a>&nbsp;&nbsp;'+ 
						'<a href="###" onclick="deleteTreeGridHandle(\''+delUrl+'\',\'grid\',\'\')">删除</a></span>';
				}
			}
		]]
	});
}

function createSubMenu(menuId, menuName, isFunction){
	if(isFunction == 'Y') {
		$.messager.confirm("系统提示","["+menuName+"] 菜单是功能菜单，增加下级菜单后将变为目录菜单，您确定要操作吗？",function(r){
			if(r){
				editMenu("", menuId, menuName);
			}
		});
	}else{
		editMenu("", menuId, menuName);
	} 
}

function editMenu(menuId, parentMenuId, parentMenuName) {
	$.dialog({ 
		title: '编辑菜单信息',
	    content: 'url:menuController.do?method=edit&menuId='+menuId+'&parentMenuId='+parentMenuId+'&parentMenuName='+encodeURI(encodeURI(parentMenuName)), 
	    ok: function(){
	    	var succ = this.content.saveMenu();
	    	if (succ) {
	    		$("#grid").treegrid("reload");
	    		return true;
	    	}
	    	return false;
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:550,
	    height:180,
	    lock:true 
	});
}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="editMenu('','-1','');">增加一级菜单</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="菜单列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>