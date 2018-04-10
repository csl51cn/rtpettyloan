<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>实时报文发送</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">

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

        //报文申报
        function doDeclare() {
            var ids = [];
            var rows = $("#declareQueryResultTb").datagrid("getSelections");
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            if (ids.length == 0){
                return;
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/realTimeDeclare.do?method=sendRealTimeDeclare",
                data: {"ids": ids.toString()},
                dataType: "json",
                success: function (data) {
                    data = eval(data);
                    if (data.sucesss ) {
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
                if (!checkEndTime("signStartDate", "signEndDate")) {
                    $("#dateCheckMsg").html("结束时间必须晚于开始时间！");
                    flag = false;
                    return;
                }
                if (flag) {
                    $("#declareQueryResultTb").datagrid({
                        queryParams: form2Json("fo"),
                        url: "${basePath}/pettyLoanContract.do?method=findPettyLoanContractBySendStatus"
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
                jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
            }
            jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
            jsonStr += '}'

            var json = JSON.parse(jsonStr)
            return json
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
        $(function () {
            //初始化申报查询的datagrid
            $("#declareQueryResultTb").datagrid({
                url: '',
                pagination: true,
                checkOnSelect: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30,50],
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
                    field: "sendStatus",
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
                    field: "isRealQuotaLoan",
                    title: "是否额度项下贷款",
                    width: 120,
                    formatter: function (value, row) {
                        if ('740001' == value) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                }, {
                    field: "realQuotaNo",
                    title: "授信额度协议编号",
                    width: 230
                }
                ]]
            })

        })
    </script>
</head>
<body style="width: 93.5%;">

<form id="fo" method="post" action="${basePath}/realTimeDeclare.do?method=sendRealTimeDeclare" >
    <div region="center" border="false">
        <div class="editBlock">
            <table>
                <tr>
                    <th>交易类型：</th>
                    <td colspan="3">
                        实时网签 &nbsp <span id="dateCheckMsg" style="color:red;"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%">签约日期：</th>
                    <td>
                        <input type="text" id="signStartDate" name="signStartDate"
                               style="border:1px solid #95B8E7;*color:#007fca;width:230px;padding:4px 2px;"
                               onclick="WdatePicker()"/>至
                        <input type="text" id="signEndDate" name="signEndDate"  style="border:1px solid #95B8E7;
                        *color:#007fca;width:230px;padding:4px 2px;" onclick="WdatePicker()"/>

                    </td>
                    <th >合同编号:</th>
                    <td>
                        <input type="text" id="contractNo" name="contractNo" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;">
                        <input type="hidden" name="sendStatusCode" id="sendStatus" value="0"/>
                        <input id="declareQueryBtn" type="button" class="inputButton" onclick="doDeclareQuery();"
                               value="查询"/>
                        <input id="declareBtn" type="button" class="inputButton" onclick="doDeclare();"
                               value="报文发送"/>
                    </td>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
<div >
    <table id="declareQueryResultTb" ></table>
</div>
</body>
</html>