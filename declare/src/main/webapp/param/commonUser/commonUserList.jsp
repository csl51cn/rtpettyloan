<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外管用户信息列表</title>
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
        url: 'commonUserController.do?method=queryCommonOrgUser',
        sortName: 'OPERNO',
        sortOrder: 'asc',
        idField: 'OPERNO',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'common_ORG_CODE', title: '金融机构代码', width: 100, sortable: true },
					{ field: 'operno', title: '柜员号', width: 80, sortable: true },
					{ field: 'common_USER_CODE', title: '用户代码', width: 100, sortable: true },
					{ field: 'update_TYPE', title: '更新方式', width: 100, sortable: true,
						formatter:function(value, rec){
							if (rec.update_TYPE == '1') {
								return "手工";
							} else if (rec.update_TYPE == '2') {
								return "接口";
							} else {
								return "";
							}
						}
					},
					{ field: 'update_USERID', title: '更新人', width: 100, sortable: true },
					{ field: 'update_TIME', title: '更新时间', width: 100, sortable: true },
					{field:'opt',title:'操作',width:120,align:'center', rowspan:2,
						formatter:function(value,rec){
							var editUrl = "commonUserController.do?method=editCommonOrgUser&operNo="+rec.operno;
							var delUrl = "commonUserController.do?method=deleteCommonOrgUser&operNo="+rec.operno;
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


<body class="easyui-layout" style="overflow-y: hidden;padding:10px;" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
   <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('commonUserController.do?method=editCommonOrgUser')">新增</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 62px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">柜员号</th>
						<td width="30%">
							<input id="OPERNO" name="OPERNO" class="inputText" style="width:205px;" />
						</td>
						<th width="10%">用户代码</th>
						<td>
							<input id="COMMON_USER_CODE" name="COMMON_USER_CODE" class="inputText" style="width:205px;" />
							<input id="save" type="button" class="inputButton" value="查询" onclick="queryHandle('grid','queryForm');"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="外管用户信息列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>

</html>