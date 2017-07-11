<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询上报结果</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">
            var  arr = new Array();
            arr.push(null);//留空,后面的元素索引正好对应交易类型尾数
            arr.push(null);//0101交易类型 授信额度信息上报
            arr.push("${basePath}/contractInfo.do?method=findLastContractBySendStatus");//0102交易类型 贷款合同信息上报
            arr.push("${basePath}/contractIssueInfo.do?method=findLastContractBySendStatus");//0103交易类型 贷款放款信息上报
            arr.push("${basePath}/repayInfo.do?method=findLastRepayInfoSendStatus");//0104交易类型 贷款回收信息上报
            arr.push("${basePath}/payPlanInfo.do?method=findLastPayPlanInfoBySendStatus");//0105交易类型 还款计划信息信息上报

        $(function () {
            $("#transactionType").combogrid( {
                onChange:function(n,o){
                    if(n == '0102' || n =='0103'){
                        init_a();
                    }else if(n == '0105'){
                        init_b();
                    }else if(n == '0104'){
                        init_c();
                    }
                }
            })
        })
        function  init_a(){
            //初始化申报查询的datagrid 0102/0103上报类型使用
            $("#declareQueryResultTb").datagrid({
                url: '',
                checkOnSelect: true,
                pagination: true,
                multiple:true,
                singleSelect:false,
                pageSize: 20,
                pageList: [5, 10, 15, 20, 30 ,40],
                columns: [[{
                    field: "id",
                    title: "主键",
                    checkbox: true
                }, {
                    field: "dateId",
                    title: "Date_Id",
                    hidden: true
                }, {
                    field: "contractNo",
                    title: "合同编号",
                    width: 100
                }, {
                    field: "customerName",
                    title: "借款人名称",
                    width: 100

                }, {
                    field: "loanCate",
                    title: "贷款类型",
                    width: 100,
                    formatter:function(value,row){
                        if(value == '530001'){
                            return '自营贷款';
                        }else if (value =='530002'){
                            return '委托贷款';
                        }

                    }
                }, {
                    field: "contractAmount",
                    title: "合同金额",
                    width: 100,
                }, {
                    field: "contractSignDate",
                    title: "合同签订日期",
                    width: 100,
                    formatter: function (value, row) {
                        return formatDatebox(value);
                    }
                }, {
                    field: "isSend",
                    title: "是否已申报",
                    width: 100,
                    formatter: function (value, row) {
                        if (1 == value) {
                            return "是";
                        } else {
                            return "否";
                        }

                    }
                },{
                    field: "isLast",
                    title: "是否是最新",
                    width: 80,
                    formatter:function(value,row){
                        if(value = 'Y'){
                            return '是';
                        }else{
                            return '否';
                        }
                    }
                },{
                    field:"reportType",
                    title:"上报类型",
                    width:100,
                    formatter:function(value,row){
                        if('100001' == value){
                            return '新增记录';
                        }else if ('100002' == value){
                            return '修改记录';
                        }else if('100003' == value){
                            return '删除记录';
                        }
                    }
                },{
                    field: "batchNo",
                    title: "批次号",
                    width: 150,
                }
                ]]

            })
        }

            function  init_b(){
                //初始化申报查询的datagrid 0105上报类型使用
                $("#declareQueryResultTb").datagrid({
                    url: '',
                    checkOnSelect: true,
                    pagination: true,
                    singleSelect:false,
                    multiple:true,
                    pageSize: 20,
                    pageList: [5, 10, 15, 20, 30,40],
                    columns: [[{
                        field: "id",
                        title: "主键",
                        checkbox: true
                    }, {
                        field: "dateId",
                        title: "Date_Id",
                        hidden: true
                    }, {
                        field: "contractNo",
                        title: "合同编号",
                        width: 100
                    }, {
                        field: "dueBillNo",
                        title: "发放编号",
                        width: 100

                    }, {
                        field: "counter",
                        title: "还款期数",
                        width: 100
                    }, {
                        field: "totalCounter",
                        title: "总期数",
                        width: 100,
                    }, {
                        field: "repayDate",
                        title: "应还日期",
                        width: 100,
                        formatter: function (value, row) {
                            return formatDatebox(value);
                        }
                    }, {
                        field: "repayPriAmt",
                        title: "应还本金",
                        width: 80
                    }, {
                        field: "repayIntAmt",
                        title: "应还利息",
                        width: 80
                    }, {
                        field: "startDate",
                        title: "起息日期",
                        width: 85,
                        formatter: function (value, row) {
                            return formatDatebox(value);
                        }
                    }, {
                        field: "endDate",
                        title: "止息日期",
                        width: 85,
                        formatter: function (value, row) {
                            return formatDatebox(value);
                        }
                    }, {
                        field: "isSend",
                        title: "是否申报",
                        width: 70,
                        formatter: function (value, row) {
                            if (1 == value) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    }, {
                        field: "isLast",
                        title: "是否最新",
                        width: 70,
                        formatter: function (value, row) {
                            if (value == 'Y') {
                                return '是';
                            } else if (value == 'N') {
                                return '否';
                            }
                        }
                    }, {
                        field: "reportType",
                        title: "上报类型",
                        width: 65,
                        formatter: function (value, row) {
                            if ('100001' == value) {
                                return '新增记录';
                            } else if ('100002' == value) {
                                return '修改记录';
                            } else if ('100003' == value) {
                                return '删除记录';
                            }
                        }
                    },{
                        field: "batchNo",
                        title: "批次号",
                        width: 150,
                    }
                    ]],
                    onClickRow:function(index,row){
                        if(index != selectIndexs.firstSelectRowIndex && !inputFlags.isShiftDown ){
                            selectIndexs.firstSelectRowIndex = index; //alert('firstSelectRowIndex, sfhit = ' + index);
                        }
                        if(inputFlags.isShiftDown ) {
                            $('#declareQueryResultTb').datagrid('clearSelections');
                            selectIndexs.lastSelectRowIndex = index;
                            var tempIndex = 0;
                            if(selectIndexs.firstSelectRowIndex > selectIndexs.lastSelectRowIndex ){
                                tempIndex = selectIndexs.firstSelectRowIndex;
                                selectIndexs.firstSelectRowIndex = selectIndexs.lastSelectRowIndex;
                                selectIndexs.lastSelectRowIndex = tempIndex;
                            }
                            for(var i = selectIndexs.firstSelectRowIndex ; i <= selectIndexs.lastSelectRowIndex ; i++){
                                $('#declareQueryResultTb').datagrid('selectRow', i);
                            }
                        }
                    }
                })
            }
        function init_c(){
            //初始化申报查询的datagrid 0104上报类型使用
            $("#declareQueryResultTb").datagrid({
                url: '',
                checkOnSelect: true,
                pagination: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30],
                columns: [[{
                    field: "id",
                    title: "主键",
                    checkbox: true
                }, {
                    field: "dateId",
                    title: "Date_Id",
                    hidden: true
                }, {
                    field: "contractNo",
                    title: "合同编号",
                    width: 90
                }, {
                    field: "customerName",
                    title: "借款人名称",
                    width: 80

                }, {
                    field: "counter",
                    title: "还款期数",
                    width: 80,
                }, {
                    field: "repayDate",
                    title: "还款日期",
                    width: 90,
                    formatter: function (value, row) {
                        return formatDatebox(value);
                    }
                }, {
                    field: "repayPriAmt",
                    title: "实还本金",
                    width: 80
                }, {
                    field: "repayIntAmt",
                    title: "实还利息",
                    width: 80

                },{
                    field: "receiptType",
                    title: "回收类型",
                    width: 80,
                    formatter: function (value, row) {
                        if(value == 550001){
                            return '正常还款';
                        }else if(value == 550002){
                            return '逾期还款';
                        }
                    }
                },{
                    field: "delayDays",
                    title: "逾期天数",
                    width: 80
                }, {
                    field: "isSend",
                    title: "是否申报",
                    width: 70,
                    formatter: function (value, row) {
                        if (1 == value) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                }, {
                    field: "isLast",
                    title: "是否最新",
                    width: 70,
                    formatter: function (value, row) {
                        if (value == 'Y') {
                            return '是';
                        } else if (value == 'N') {
                            return '否';
                        }
                    }
                }, {
                    field: "reportType",
                    title: "上报类型",
                    width: 65,
                    formatter: function (value, row) {
                        if ('100001' == value) {
                            return '新增记录';
                        } else if ('100002' == value) {
                            return '修改记录';
                        } else if ('100003' == value) {
                            return '删除记录';
                        }
                    }
                },{
                    field: "batchNo",
                    title: "批次号",
                    width: 150,
                }
                ]]
            })

        }


        function doQuery() {
            var ids = [];
            var rows = $("#declareQueryResultTb").datagrid("getSelections");
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].batchNo);
            }
            if (ids.length == 0){
                return;
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/queryDeclare.do?method=queryDeclare",
                data: {"batchNo": ids.toString(),"transactionType":$("#transactionType").combobox("getValue")},
                dataType: "json",
                success: function (data) {
                    data = eval(data);
                    if (data.sucesss) {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", data.msg, "warning");
                    }
                }
            });

        }

        //根据申报状态查询
        function doDeclareQuery() {
            if($("#fo").form('validate') == true){
                var flag = true;
                $("#dateCheckMsg").html("");
                if (!checkEndTime("startDate", "endDate")) {
                    $("#dateCheckMsg").html("结束时间必须晚于开始时间！");
                    flag = false;
                    return;
                }
                if (flag) {
                    var code  = $("#transactionType").combobox("getValue").substr(3);
                    var url =arr[code];
                    $("#declareQueryResultTb").datagrid({
                       queryParams: form2Json("fo"),
                      url: url
                    });
                }
            }

        }
        //将表单数据转为json
        function form2Json(id) {

            var arr = $("#" + id).serializeArray();
            var jsonStr = "";

            jsonStr += '{';
            for (var i = 0; i < arr.length; i++) {
                jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
            }
            jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
            jsonStr += '}';

            var json = JSON.parse(jsonStr);
            return json;
        }




        //格式化时间
        Date.prototype.format = function (format) {
            var o = {
                "M+": this.getMonth() + 1, // month
                "d+": this.getDate(), // day
                "h+": this.getHours(), // hour
                "m+": this.getMinutes(), // minute
                "s+": this.getSeconds(), // second
                "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                "S": this.getMilliseconds()
                // millisecond
            }
            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            return format;
        }
        function formatDatebox(value) {
            if (value == null || value == '') {
                return '';
            }
            var dt;
            if (value instanceof Date) {
                dt = value;
            } else {
                dt = new Date(value);
            }

            return dt.format("yyyy-MM-dd"); //扩展的Date的format方法
        }

        //检查结束时间是否大于等于开始时间
        function checkEndTime(dateId1, dateId2) {
            var startDate = $("#" + dateId1).val();
            var start = new Date(startDate.replace("-", "/").replace("-", "/"));
            var endDate = $("#" + dateId2).val();
            var end = new Date(endDate.replace("-", "/").replace("-", "/"));
            if (end < start) {
                return false;
            }
            return true;
        }
    </script>
</head>
<body  style="width: 93.5%;">

<form id="fo" action="" method="post">
    <div region="center" border="false">
        <div class="editBlock">
            <table>
                <tr>
                    <th width="15%">交易类型：</th>
                    <td width="28%">
                        <input class="easyui-combogrid" id="transactionType" name="transactionType"
                               style="border:1px solid #95B8E7;*color:#007fca;width:251px;padding:4px 2px;"
                               data-options="
                                    panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=MESSAGE_TYPE',
									columns: [[
										{field:'dictCode',title:'报文代码',width:60},
										{field:'dictName',title:'报文名称',width:195}
									]],
									fitColumns: true,
									nowrap:false,
								">
                        <input type="hidden" name="sendStatusCode" id="sendStatus" value="1"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%">签约日期(或还款日期)：</th>
                    <td  width="28%">
                        <input type="text" id="startDate" name="startDate" data-options="required:true"
                               class="easyui-validatebox"
                               style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
                               onclick="WdatePicker()"/>  至
                    </td>
                    <td>
                        <input type="text" id="endDate" name="endDate" data-options="required:true" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" onclick="WdatePicker()"
                               class="easyui-validatebox"/>
                        <input id="declareQueryBtn" type="button" class="inputButton" onclick="doDeclareQuery();"
                               value="查询"/>
                        <input id="businessQueryBtn" type="button" class="inputButton" onclick="doQuery();"
                               value="查询上报结果"/>
                     <span id="dateCheckMsg" style="color:red;"/>
                    </td>

                </tr>
            </table>
        </div>
    </div>
</form>
<div>
    <table id="declareQueryResultTb"></table>
</div>
</body>
</html>