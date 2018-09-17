<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询上报结果</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
</head>
<body style="width: 93.5%;">

<form id="fo" action="" method="post">
    <div region="center" border="false">
        <div class="editBlock">
            <table>
                <tr>
                    <th width="15%">交易类型：</th>
                    <td width="28%">
                        <select class="" id="transactionType" name="transactionType"
                                style="border:1px solid #95B8E7;*color:#007fca;width:251px;padding:4px 2px;">
                            <option value="all">全部</option>
                            <option value="0101">授信额度信息</option>
                            <option value="0102">贷款合同信息</option>
                            <option value="0103">贷款发放信息</option>
                            <option value="0104">贷款回收信息</option>
                            <option value="0105">还款计划信息</option>
                            <option value="0106">非正常客户信息</option>
                            <option value="0107">网签文件</option>
                        </select>
                    </td>
                    <th>批次号：</th>
                    <td>
                        <input type="text" id="batchNo" name="batchNo" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%">签约日期(或还款日期)：</th>
                    <td width="28%">
                        <input type="text" id="startDate" name="startDate"
                               class="easyui-validatebox"
                               style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
                               onclick="WdatePicker()"/> 至
                    </td>
                    <td>
                        <input type="text" id="endDate" name="endDate" style="border:1px solid #95B8E7;
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
<script type="text/javascript">
    $(function () {
        init_table();
    });

    function init_table() {
        //初始化
        $("#declareQueryResultTb").datagrid({
            url: '',
            checkOnSelect: true,
            pagination: true,
            multiple: true,
            singleSelect: true,
            pageSize: 20,
            pageList: [5, 10, 15, 20, 30, 40],
            columns: [[{
                field: "id",
                title: "主键",
                checkbox: true
            }, {
                field: "batchNo",
                title: "批次号"
            }, {
                field: "recordCount",
                title: "记录数"
            }, {
                field: "dataType",
                title: "数据类型",

                formatter: function (value, row) {
                    if (value == "QUOTA_INFO") {
                        return '授信额度信息';
                    } else if (value == "CONTRACT_INFO") {
                        return '贷款合同信息';
                    } else if (value == "ISSUE_INFO") {
                        return '贷款发放信息';
                    } else if (value == "REPAY_INFO") {
                        return '贷款回收信息';
                    } else if (value == "PAYPLAN_INFO") {
                        return '还款计划信息';
                    }
                }
            }, {
                field: "declareResult",
                title: "上报结果"
            }, {
                field: "createId",
                title: "创建人"
            }, {
                field: "gmtCreate",
                title: "创建时间"
            }, {
                field: "modifiedId",
                title: "最后修改人"
            }, {
                field: "gmtModified",
                title: "最后修改时间"
            }
            ]]

        })
    }


    function doQuery() {
        var ids = [];
        var rows = $("#declareQueryResultTb").datagrid("getSelections");
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }
        if (ids.length == 0) {
            $.messager.alert("提示消息", "请选择一条记录进行查询", "info");
            return;
        }
        $.ajax({
            type: "POST",
            url: "${basePath}/queryDeclare.do?method=queryDeclare",
            data: {"id":ids.toString()},
            dataType: "json",
            success: function (data) {
                data = eval(data);
                if (data.sucesss) {
                    $.messager.alert("提示消息", data.msg, "info");
                    doDeclareQuery();
                } else {
                    $.messager.alert("提示消息", data.msg, "warning");
                }
            }
        });

    }

    //查询本地数据库中的数据
    function doDeclareQuery() {
        if ($("#fo").form('validate') == true) {
            var flag = true;
            $("#dateCheckMsg").html("");
            if (!checkEndTime("startDate", "endDate")) {
                $("#dateCheckMsg").html("结束时间必须晚于开始时间！");
                flag = false;
                return;
            }
            if (flag) {
                $("#declareQueryResultTb").datagrid({
                    queryParams: form2Json("fo"),
                    url: "${basePath}/queryDeclare.do?method=queryRawDeclareData"
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
</body>
</html>