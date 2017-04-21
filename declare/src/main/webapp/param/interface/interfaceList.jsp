<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口管理</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
var grid;

$(function() {
	initDataGrid();
	
	$(".click").click(function(){
		  $(".tip").fadeIn(200);
		  });
		  
		  $(".tiptop a").click(function(){
		  $(".tip").fadeOut(200);
		});

		  $(".sure").click(function(){
		  $(".tip").fadeOut(100);
		});

		  $(".cancel").click(function(){
		  $(".tip").fadeOut(100);
		});

		$(".easyui-layout").layout();

			
		$(".easyui-combobox").combobox();
		$(".easyui-datebox").datebox();
			
});


function initDataGrid(){
	grid = $('#grid').datagrid({
        methord: 'post',
        url: 'interfaceController.do?method=query',
        sortName: 'channelId',
        sortOrder: 'asc',
        idField: 'channelId',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'faceCode', title: '接口代码', width: 80, sortable: true },
					{ field: 'faceName', title: '接口名称', width: 80, sortable: true },
					{ field: 'channelCode', title: '渠道代码', width: 100, sortable: true },
					{ field: 'channelName', title: '渠道名称', width: 80, sortable: true },
					{ field: 'isValid', title: '是否启用', width: 80, sortable: true ,
						formatter:function(value, rec){
							return rec.isValid === 'Y' ? '启用' : '停用';
						}
					},
					{field:'opt',title:'操作',width:120,align:'center', rowspan:2,
						formatter:function(value,rec){
							var validOpt = rec.isValid == "Y" ? "停用" : "启用",
								validValue = rec.isValid == "Y" ? "N" : "Y",
								validUrl = "interfaceController.do?method=update&channelId=" + rec.channelId + "&faceCode="+rec.faceCode+"&isValid=" + validValue,
								delUrl = "interfaceController.do?method=delete&channelId=" + rec.channelId+"&faceCode="+rec.faceCode;
							return '<span style="color:blue">'+
							'<span style="color:blue"><a href="javascript:void(0)" onclick="updateValidHandle(\''+validUrl+'\',\'grid\',\''+rec.isValid+'\')">'+ validOpt +'</a>&nbsp;'+
								   '<a href="javascript:void(0)" onclick="del(\''+delUrl+'\',\'grid\',\''+rec.isValid+'\')">删除</a>&nbsp;&nbsp;';
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

function updateValidHandle(url, gridId, isValid) {
	$.messager.confirm("系统提示", "您确定要"+(isValid=='Y'?'停用':'启用')+"吗？", function(r) {
		if (r) {
			$.post(url, function(data) {
				$('#' + gridId).datagrid('reload');
			});
		}
	});
}

function del(url, gridId, isValid) {
	if (isValid == 'Y') {
		$.messager.alert("系统提示","接口在启用状态下不允许删除！", "error");
		return;
	}
	$.messager.confirm("系统提示", "您确定要执行删除操作吗？", function(r) {
		if (r) {
			deleteHandle(url,gridId);
		}
	});
}
</script>


</head>


<body class="easyui-layout" style="overflow-y: hidden;padding:10px;" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 92px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">接口代码</th>
						<td width="30%">
							<input id="faceCode" name="faceCode" class="inputText" style="width:205px;" />
						</td>
						<th width="10%">接口名称</th>
						<td>
							<input id="faceName" name="faceName" class="inputText" style="width:205px;" />
						</td>
					</tr>
					<tr>
					    <th width="10%">渠道代码</th>
						<td width="30%">
							<select id="channelId" name="channelId" class="easyui-combobox" style="width:205px;">
								<option value="">请选择渠道</option>
								<c:forEach var="channel" items="${channelList}">
									<option value="${channel.channelId }">${channel.channelCode }-${channel.channelCnName }</option>
								</c:forEach>
							</select>
						</td>
						<th width="10%">状态</th>
						<td  >
							<select id="isValid" name="isValid" class="easyui-combobox" style="width:205px;">
								<option value="">全部</option>
								<option value="Y">启用</option>
								<option value="N">停用</option>
							</select>
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="渠道接口列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>

</html>