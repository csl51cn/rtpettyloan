<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇信息列表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function () {
	
	//var curr_time = new Date();
	//var strDate = curr_time.getFullYear()+"-";
	//strDate += curr_time.getMonth()+1+"-";
	//strDate += curr_time.getDate();		
	//$('#BIZ_TX_TIME').datebox('setValue', strDate);
	
	initDataGrid();
	
	$("#IDTYPE_CODE").combogrid({
		onClickRow:function(rowIndex, rowData){
			$("#IDCODE").attr("disabled",false);
			$("#CTYCODE").combogrid({disabled:false});
		}		
	});
		
	$("#jshQuery").click(function(){		
		if(!$("#queryForm").form('validate')){
			return false;
		}
		if($("#REFNO").val() == "" && $("#IDTYPE_CODE").combogrid("getValue") == "" && $("#BIZ_TX_TIME").datebox("getValue") == ""){
			$.messager.alert('系统提示', "请至少输入业务参号或业务办理日期查询！", 'info');
			return false;
		}		
		if($("#IDTYPE_CODE").combogrid("getValue") != ""){
			if($("#IDCODE").val() == "" || $("#CTYCODE").combogrid("getValue") == ""){
				$.messager.alert('系统提示', "证件号码和国家/地区代码必须输入！", 'info');
				return false;
			}		
		}
		queryHandle('grid','queryForm');
	});
	
});

function initDataGrid(){
	$('#grid').datagrid({
        methord: 'post',
        url: 'spfeQuery.do?method=jshMsgQuery',
        //sortName: 'sortNo',
        //sortOrder: 'asc',
        //idField: 'currencyCode',
        pageSize: 15,
        pageList: [10,15,20],
        columns: [[
					{ field: 'tradetype', title: '交易类型', hidden:true, width: 60, sortable: true },
					{ field: 'tradetypename', title: '交易类型', width: 60, sortable: true },
					{ field: 'biz_TYPE_NAME', title: '业务类型', width: 60, sortable: true },
					{ field: 'refno', title: '业务参号', width: 150, sortable: true },
					{ field: 'ctycode', title: '国家/地区代码', width: 150, sortable: true },
					{ field: 'biz_TX_TIME', title: '业务办理时间', width: 120, sortable: true },
					{ field: 'person_NAME', title: '姓名', width: 120, sortable: true },
					{ field: 'txccy', title: '币种', width: 80, sortable: true },
					{ field: 'salefx_PURFX_AMT', title: '结售汇金额', width: 100, sortable: true },
					{ field: 'salefx_PURFX_ACCT_CNY', title: '人民币账户', width: 150, sortable: true },
					{ field: 'lcy_ACCT_NO', title: '外汇账户', width: 150, sortable: true },
					{field:'opt',title:'操作',width:80,align:'center', rowspan:2,
						formatter:function(value, rec) {
							return '<span style="color:blue"><a href="spfeQuery.do?method=jshMsgDetailQuery&refNo='+rec.refno+'&tardeType='+rec.tradetype+'&bizType='+rec.biz_TYPE_CODE+'">详细信息</a>';
						}
					}
		
				]],
        fit:true,
        queryParams:{ 
        		TRADETYPE: $("#TRADETYPE").combobox('getValue')
        	},   //查询条件
        pagination: true,
        rownumbers: true,
        fitColumns: true,
        striped: true 	  //奇偶行颜色不同 
        //remoteSort: true, //服务器端排序
        //singleSelect: false//多选
	});
}
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 121px;" id="north">
		<form id="queryForm" action="#" method="post">
			<input type="hidden" name="page" value="1" />
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">业务参号</th>
						<td width="20%">
							<input type="text" id="REFNO" name="REFNO" class="inputText"/>
						</td>
						<th width="10%"><span class="warning">*</span>证件类型</th>
						<td width="20%">
							<input class="easyui-combogrid" id="IDTYPE_CODE" name="IDTYPE_CODE" style="width:250px;"
								data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${pageContext.request.contextPath}/param/paramCommonController.do?method=getDatadict&code=IDTYPE_CODE',
									columns: [[
										{field:'dictCode',title:'证件代码',width:60},
										{field:'dictName',title:'证件名称',width:195}
									]],
									fitColumns: true,							
									nowrap:false">
						</td>						
					</tr>
					<tr>
						<th width="10%"><span class="warning">*</span>证件号码</th>
						<td width="20%">
							<input type="text" id="IDCODE" name="IDCODE" disabled="disabled" class="inputText"/>
						</td>		
						<th width="10%"><span class="warning">*</span>国家/地区代码</th>
						<td width="20%">
							<input class="easyui-combogrid" id="CTYCODE" name="CTYCODE"  disabled="disabled" style="width:250px;"
								data-options="
									panelWidth: 255,
									idField: 'safeCode',
									textField: 'cnName',
									url: '${pageContext.request.contextPath}/param/paramCommonController.do?method=getCountrys',
									columns: [[
										{field:'safeCode',title:'地区代码',width:60},
										{field:'cnName',title:'地区代码名称',width:195}
									]],
									fitColumns: true,
									nowrap:false">
						</td>	
					</tr>
					<tr>
						<th width="10%"><span class="warning">*</span>交易类型</th>
						<td width="20%">
							<input class="easyui-combobox" id="TRADETYPE" required="true" name="TRADETYPE" style="width:250px;"
							data-options="
								panelWidth: 250,
								panelHeight: 160,
								valueField: 'dictCode',
								textField: 'dictName',
								url: '${pageContext.request.contextPath}/param/paramCommonController.do?method=getDatadict&code=TRADE_TYPE',
								fitColumns: true" /> 
						</td>							
						<th width="10%">业务办理日期</th>
						<td width="20%">
							<input type="text" id="BIZ_TX_TIME" name="BIZ_TX_TIME" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
							<input id="jshQuery" type="button" class="inputButton" value="查询"/>
						</td>						
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="个人结售汇信息列表">
        <div id="grid"></div>
    </div>
  </div>
</div>
</body>
</html>