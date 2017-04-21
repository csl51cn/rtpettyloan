<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>渠道管理</title>
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
        url: 'channelController.do?method=query',
        sortName: 'channelCode',
        sortOrder: 'asc',
        idField: 'channelCode',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'channelCnName', title: '渠道中文名称', width: 100, sortable: true },
					{ field: 'channelEnName', title: '渠道英文名称', width: 100, sortable: true },
					{ field: 'channelCode', title: '渠道代码', width: 80, sortable: true },
					{ field: 'reqSysCode', title: '接入系统代码', width: 100, sortable: true },
					{ field: 'bizChnlCode', title: '业务办理渠道', width: 100, sortable: true,
						formatter:function(value, rec){
							return rec.bizChnlCode + "-" + rec.bizChnlName;
						}
					},
					{ field: 'isValid', title: '是否启用', width: 70, sortable: true ,
						formatter:function(value, rec){
							return rec.isValid === 'Y' ? '启用' : '停用';
						}
					},
					{field:'opt',title:'操作',width:150,align:'center', rowspan:2,
						formatter:function(value,rec){
							var validOpt = rec.isValid == "Y" ? "停用" : "启用";
							var validValue = rec.isValid == "Y" ? "N" : "Y";
							var editUrl = "channelController.do?method=edit&channelId=" + rec.channelId;
							var validUrl = "channelController.do?method=save&channelId=" + rec.channelId + "&isValid=" + validValue;
							var delUrl = "channelController.do?method=delete&channelId=" + rec.channelId;
							
							return '<span style="color:blue"><a href="javascript:void(0)" onclick="editCommMode(\''+rec.channelId+'\')">通讯方式</a>&nbsp;'+
							'<a href="javascript:void(0)" onclick="editInterface(\''+rec.channelId+'\')">接口定义</a>&nbsp;'+
							'<a href="javascript:void(0)" onclick="editHandle(\''+editUrl+'\',\'grid\',\'\')">修改</a>&nbsp;'+
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
		$.messager.alert("系统提示","渠道在启用状态下不允许删除！", "error");
		return;
	}
	$.messager.confirm("系统提示", "您确定要执行删除操作吗？", function(r) {
		if (r) {
			deleteHandle(url,gridId);
		}
	});
}

function editInterface(channelId){
	$.dialog({ 
		title: '接口定义',
	    content: 'url:interfaceController.do?method=edit&channelId=' + channelId, 
	    ok: function(){ 
	    	return this.content.save();
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:500,
	    height:300,
	    lock:true 
	});
}

function editCommMode(channelId){
	var txUrl = "commModeController.do?method=toEdit&channelId=" + channelId+"&flag=N";
	$.dialog({ 
		title: '通讯方式定义',
	    content: 'url:'+txUrl, 
	    ok: function(){ 
	    	return this.content.save();
	    }, 
	    cancelVal: '取消', 
	    cancel: true,
	    width:500,
	    height:300,
	    lock:true 
	});
}

</script>


</head>


<body class="easyui-layout" style="overflow-y: hidden;padding:10px;" scroll="no">
<div region="north" style="overflow:hidden;" border="false">
   <div style="padding:1px;background:#EFEFEF;">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('channelController.do?method=edit')">新增</a>
    </div>
</div>
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 92px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">渠道中文名称</th>
						<td width="30%">
							<input id="channelCnName" name="channelCnName" class="inputText" style="width:205px;" />
						</td>
						<th width="10%">渠道代码</th>
						<td>
							<input id="channelCode" name="channelCode" class="inputText" style="width:205px;" />
						</td>
					</tr>
					<tr>
						<th width="10%">状态</th>
						<td colspan="3" >
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
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="渠道列表">
        <div id="grid" > </div>
    </div>
  </div>
</div>
</body>

</html>