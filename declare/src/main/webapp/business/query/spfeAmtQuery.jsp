<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">	
	$(function(){		
		$("#spfeAmtQuery").click(function() {	
			if(!$("#queryForm").form('validate')){
				return false;
			}
			$.post('spfeQuery.do?method=spfeAmtDataQuery', $("#queryForm").serializeArray(), function(data) {
				data = $.parseJSON(data);
				if(data.amtBalanceUsd == undefined && data.todayAmtUsd == undefined){
					$("#_tradeType").html("");				
					$("#amtUsd").html("");		
					$("#amtBalanceUsd").html("");	
					$("#todayAmtUsd").html("");	
					$("#custName").html("");	
					$("#custType").html("");	
					$("#typeStatus").html("");	
					$("#pubDate").html("");	
					$("#endDate").html("");	
					$("#pubReason").html("");	
					$("#pubCode").html("");	
					$("#signStatus").html("");
					$.messager.alert('系统提示', "未查询到数据！", 'info');
				}else{
					$("#_tradeType").html(data.tradeType);				
					$("#amtUsd").html(data.amtUsd);		
					$("#amtBalanceUsd").html(data.amtBalanceUsd);	
					$("#todayAmtUsd").html(data.todayAmtUsd);	
					$("#custName").html(data.custName);	
					$("#custType").html(data.custType);	
					$("#typeStatus").html(data.typeStatus);	
					$("#pubDate").html(data.pubDate);	
					$("#endDate").html(data.endDate);	
					$("#pubReason").html(data.pubReason);	
					$("#pubCode").html(data.pubCode);	
					$("#signStatus").html(data.signStatus);
				}
			});
		});
	});
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false">
<div id="center-div" class="easyui-layout" fit="true">
    <div region="north" title="查询条件" style="height: 90px;" id="north">
		<form id="queryForm" action="#" method="post">
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%"><span class="warning">*</span>证件类型代码</th>
						<td width="20%">
							<input class="easyui-combogrid" id="idTypeCode" name="idTypeCode" required="true" style="width:251px;"
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
						<th width="10%"><span class="warning">*</span>国家/地区代码</th>
						<td width="20%">
							<input class="easyui-combogrid" id="cityCode" name="cityCode" required="true" style="width:250px;"
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
						<th width="10%"><span class="warning">*</span>证件号码</th>
						<td width="20%" >
							<input type="text" id="idCode" name="idCode" required="true" class="inputText easyui-validatebox" />
						</td>
						<th width="10%"><span class="warning">*</span>交易类型</th>
						<td width="20%">
							<input class="easyui-combobox" id="tradeType" required="true" name="tradeType" style="width:250px;"
							data-options="
								panelWidth: 250,
								panelHeight: 160,
								valueField: 'dictCode',
								textField: 'dictName',
								url: '${pageContext.request.contextPath}/param/paramCommonController.do?method=getDatadict&code=TRADE_TYPE',
								fitColumns: true" />
							&nbsp;&nbsp;<input id="spfeAmtQuery" type="button" class="inputButton" value="查询"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden;" title="个人结售汇额度信息"> 
			<table id="amtInfo" class="editBlock" style="width:100%;">
				<tbody>
					<tr>
						<th width="12%">交易类型</th>
						<td id="_tradeType" width="20%"></td>
						
						<th width="12%">本年额度内已结售汇金额折美元</th>
						<td id="amtUsd" width="20%"></td>	
					</tr>
					<tr>
						<th width="10%">本年额度内剩余可结售汇金额折美元</th>
						<td id="amtBalanceUsd" width="20%"></td>
						
						<th width="10%">当日已发生的现钞结售汇金额折美元</th>
						<td id="todayAmtUsd" width="20%"></td>
					</tr>
					<tr>
						<th width="10%">交易主体姓名</th>
						<td id="custName" width="20%"></td>
						
						<th width="10%">交易主体类型</th>
						<td id="custType" width="20%"></td>
					</tr>					
					<tr>					
						<th width="10%">个人主体分类状态</th>
						<td id="typeStatus" width="20%"></td>
						
						<th width="10%">发布日期</th>
						<td id="pubDate" width="20%"></td>
					</tr>					
					<tr>
						<th width="10%">到期日期</th>
						<td id="endDate" width="20%"></td>
					
						<th width="10%">发布原因</th>
						<td id="pubReason" width="20%"></td>
					</tr>
					<tr>		
						<th width="10%">发布原因</th>
						<td id="pubCode" width="20%"></td>
									
						<th width="10%">确认书签署状态</th>
						<td id="signStatus" width="20%"></td>					
					</tr>
				</tbody>
			</table>
	</div>
  </div>
</div>
</body>
</html>