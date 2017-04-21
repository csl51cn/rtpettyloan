<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程历史列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: '${pageContext.request.contextPath}/flow/tasklistController.do?method=queryFlowHistoryList&txnSerialNo=${txnSerialNo}',
        sortName: 'dealTime',
        sortOrder: 'asc',
        idField: 'txnSerialNo',
        pageSize: 20,
        pageList: [10,20,50],
        columns: [[
					{ field: 'txnSerialNo', title: '业务流水号', width: 120, sortable: false },
					{ field: 'stepName', title: '步骤', width: 100, sortable: true },
					{ field: 'dealTime', title: '处理时间', width: 150, sortable: true },
					{ field: 'operName', title: '处理人', width: 100, sortable: true }
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
        <a id="back" href="javascript:history.go(-1)" class="easyui-linkbutton" plain="true" iconCls="icon-back">返回</a>
    </div>
</div>
<div region="center" border="false" >
<div id="center-div" class="easyui-layout" fit="true" >
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="流程历史列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>
</html>