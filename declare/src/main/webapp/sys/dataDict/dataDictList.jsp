<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代码列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<link rel="stylesheet" href="../resources/jquery-ztree-3.0/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../resources/jquery-ztree-3.0/js/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="../resources/jquery-ztree-3.0/js/jquery.ztree.excheck-3.0.min.js"></script>
<script type="text/javascript">

var setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},callback:{
            onClick:onClick
        }
	};  
		  
var treeNodes;
var grid;
$(function () {
	initDictCatelog();
	initDataGrid('dataDictController.do?method=queryDataDict');    
});
function initDictCatelog() {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : basePath + "/sys/dataDictController.do?method=loadDictCatelog",//请求的action路径  
		success : function(data) { //请求成功后处理函数。    
			treeNodes = eval("[" + data + "]");//把后台封装好的简单Json格式赋给treeNodes  
		}
	});
	
	$.fn.zTree.init($("#dictCatelog"), setting, treeNodes);
}

function initDataGrid(url){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: url,
        sortName: 'classId',
        sortOrder: 'asc',
        idField: 'dictId',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'dictCode', title: '字典编码', width: 100, sortable: true },
					{ field: 'dictName', title: '字典名称', width: 150, sortable: true },
					{ field: 'className', title: '所属分类', width: 150, sortable: true },
					{ field: 'sortNo', title: '排序', width: 80, sortable: true },
					{field:'opt',title:'操作',width:80,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "dataDictController.do?method=editDataDict&dictId="+rec.dictId;
							var delUrl = "dataDictController.do?method=deleteDataDict&dictId="+rec.dictId;
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

function onClick(event, treeId, treeNode){
	initDataGrid('dataDictController.do?method=queryDataDict&classId='+treeNode.id);
}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('dataDictController.do?method=editDictClass')">新增字典类别</a>
        <a id="add" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('dataDictController.do?method=editDataDict')">新增字典选项</a>
    </div>
</div>
<div region="center" border="false" >
<div id="center-div" class="easyui-layout" fit="true">
    <div region="west" style="width: 200px; height: 300px; padding: 1px;overflow-y: hidden" title="字典分类">
        <ul id="dictCatelog" style="margin-top: 5px;" class="ztree"></ul>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="字典列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>