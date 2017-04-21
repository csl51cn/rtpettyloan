<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务量统计报表</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript" src="${basePath}/resources/highcharts/highcharts.js"></script>
<style type="text/css">
   .subtotal { font-weight: bold; }/*合计单元格样式*/
</style>
<script type="text/javascript">
var grid;
$(function () {
	initDataGrid();    
});

function initDataGrid(){
	var grid = $('#grid').datagrid({
        methord: 'post',
        url: '${basePath}/charts.do?method=queryBizVolumeList',
        columns: [[
					{ field: 'CHANNELNAME', title: '业务办理渠道', width: 100, sortable: true },
					{ field: 'ZYJH', title: '占用额度的结汇', width: 100, sortable: true },
					{ field: 'BZYJH', title: '不占用额度的结汇', width: 100, sortable: true },
					{ field: 'ZYGH', title: '占用额度的购汇', width: 100, sortable: true },
					{ field: 'BZYGH', title: '不占用额度的购汇', width: 100, sortable: true },
					{ field: 'TOTALCOUNT', title: '总计', width: 100, sortable: true },
				]],
        fit:true,
        queryParams:{}, //查询条件
        pagination: false,
        rownumbers: true,
        fitColumns: true,
        striped: true, //奇偶行颜色不同 
        remoteSort: true, //服务器端排序
        singleSelect: false,//多选
        onLoadSuccess: function(){
            setTimeout(function(){
            	selectRowEvent('grid');
            }, 10);    
            onLoadSuccess();
    	}
    });
}

function onLoadSuccess() {
    //添加“合计”列
   $('#grid').datagrid('appendRow', {
	   	CHANNELNAME: '<span class="subtotal">合计</span>',
        ZYJH: '<span class="subtotal">' + compute("ZYJH") + '</span>',
        BZYJH: '<span class="subtotal">' + compute("BZYJH") + '</span>',
        ZYGH: '<span class="subtotal">' + compute("ZYGH") + '</span>',
        BZYGH: '<span class="subtotal">' + compute("BZYGH") + '</span>',
        TOTALCOUNT: '<span class="subtotal">' + compute("TOTALCOUNT") + '</span>'
    });
    
}
//指定列求和
function compute(colName) {
    var rows = $('#grid').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

function queryData() {
	if(!$("#queryForm").form('validate')){
		return;
	}
	queryHandle('grid','queryForm');
}

var option ={div:'chart-div',title:'业务量统计曲线(按天)',ytitle: '业务量（单位：笔）'};

var _chart;

queryLineData(true);
//-----------------------------------------查询数据-------------------------------------------------------------------------------
function queryLineData(isInit){
	if(!isInit && !$("#queryForm").form('validate')){
		return;
	}
	var startDate = $("[name=startDate]").val();
	var endDate =  $("[name=endDate]").val();
	queryHandle('grid','queryForm');
	$.post("${basePath}/charts.do?method=queryBusinessLineData",{"startDate":startDate,"endDate":endDate},function(data){
		showChart($.parseJSON(data));
	});
}

//-----------------------------------------显示曲线-------------------------------------------------------------------------------
 //显示图形
 /* function showChart(data){
	 $('#chart-div').highcharts({
	        title: {
	            text: '业务量统计表(按天)',
	            x: -20 //center
	        },
	        xAxis: {
	            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	        },
	        yAxis: {
	            title: {
	                text: '业务量(单位:笔)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '笔'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: data
	    });

 } */
 
 function showChart(data){
		new Highcharts.Chart({ 
			chart : {
				type :  'spline' ,
				renderTo : document.getElementById('chart-div'),
				height : 420,
				width:1120
			},
			credits : {
				enabled : false
			},
			plotOptions:{
				spline:{
					marker:{
						enabled:false					
					}
				}
			},
			title : {
				text : option.title
			},
			rangeSelector : {
				enabled : true
			},
			series : data,
			tooltip : {
				xDateFormat : '%Y-%m-%d %H:%M:%S',
				useHTML : true,
				share:true,
				crosshairs:true,
				formatter : function() {
					var tips =  Highcharts.dateFormat('%Y-%m-%d', this.x)+" 业务量："+this.y+"<br/>";
					return tips;
				}
			},
			xAxis : {
				title : {
					text : '日期/时间'
				},
				type : 'datetime',
				gridLineWidth : 1,// 默认是0，即在图上没有纵轴间隔线
				lineColor : '#990000',
				Interval: 1,
				// x轴时间的格式化
				dateTimeLabelFormats : {
					day : '%m月%d',
					month : '%Y年%m',
					year : '%Y'
				}
			},
			yAxis : {
				gridLineWidth : 1,
				min:-30,
				max:150,
				title : {
					text : option.ytitle
				},
				tickPixelInterval : 15,
				lineWidth : 2,
				labels : {
					align : 'right',
					x : -3,
					y : 6
				}
			},
			legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
		    exporting: {
		    	enabled: false
		    }
		});
	}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<div region="center" border="false" >
<div id="center-div" class="easyui-layout" fit="true" >
 	<div region="north" title="查询条件" style="height: 60px;" id="north">
		<form id="queryForm" action="#" method="post">
			<table class="editBlock" style="width: 100%;">
				<tbody>
					<tr>
						<th width="10%">开始日期</th>
						<td width="20%" >
							<input  type="text" class="easyui-datebox" name="startDate"   data-options="required:true"  style="width:250px"   id="startDate"  />
						</td>
						<th width="10%">结束日期</th>
						<td width="20%">
							<input  type="text" class="easyui-datebox" name="endDate"   data-options="required:true"  style="width:250px"   id="endDate"  />
							&nbsp;&nbsp;<input  onclick="queryLineData(false)" type="button" class="inputButton" value="查询"/> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden" title="业务量统计表">
    	<div class="easyui-tabs" style="height:450px">
			<div title="数据列表" style="padding:10px; ">
				<div id="grid" ></div>
			</div>
			<div title="曲线图" style="padding:10px">
				<div id="chart-div" ></div>
			</div>
		</div>
    </div>
  </div>
</div>
</body>
</html>