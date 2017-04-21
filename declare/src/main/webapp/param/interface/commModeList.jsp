<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯方式定义</title>
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
        url: 'commModeController.do?method=search',
        sortName: 'channelId',
        sortOrder: 'asc',
        idField: 'channelId',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'channelCode', title: '渠道代码', width: 100, sortable: true },
					{ field: 'channelName', title: '渠道名称', width: 100, sortable: true },
					{ field: 'commMode', title: '通讯方式', width: 100, sortable: true },
					{ field: 'format', title: '报文格式', width: 100, sortable: true },
					{ field: 'ip', title: '接入IP', width: 100, sortable: true },
					{ field:'opt',title:'操作',width:120,align:'center', rowspan:2,
						formatter:function(value,rec){
								var editUrl = "commModeController.do?method=toEdit&channelId=" + rec.channelId+"&flag=Y";
								var delUrl = "commModeController.do?method=del&channelId=" + rec.channelId;
							return '<span style="color:blue"><a href="javascript:void(0)" onclick="editHandle(\''+editUrl+'\',\'grid\',\'\')">修改</a>&nbsp;'+
								   '<a href="javascript:void(0)" onclick="del(\''+delUrl+'\',\'grid\',\'\')">删除</a>&nbsp;&nbsp;';
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

function del(url, gridId) {
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
    <div region="north" title="查询条件" style="height: 62px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">渠道名称</th>
						<td colspan="3" >
							<select id="channelId" name="channelId" class="easyui-combobox" style="width:205px;">
								<option value="">请选择渠道</option>
								<c:forEach var="channel" items="${channelList}">
									<option value="${channel.channelId }">${channel.channelCode }-${channel.channelCnName }</option>
								</c:forEach>
							</select>
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="渠道通讯方式列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>

</html>