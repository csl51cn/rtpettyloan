<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>贷款发放信息上报合同信息操作</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">


        $(function () {
            //保存或更新时根据返回值给出提示
            var msg = "${msg}";
            if (msg !== "") {
                if (msg == '1') {
                    $.messager.alert("提示消息", "操作成功", "info");
                } else {
                    $.messager.alert("提示消息", "操作失败," + msg, "warning");
                }
            }

            //双击某条数据后，返回详细信息，根据返回的值，设置不可编辑状态，点"手动录入"按钮后解除锁定
            if ("${disabled}") {
                $("#fo input[type=text]").prop("disabled", "disabled");
                $("#loanCate").combogrid('disable');
                $("#customerType").combogrid('disable');
                $("#certificateType").combogrid('disable');
            }

            //如果编辑已申报的记录,保存按钮失效,编辑未申报的记录,已申报修改,已申报删除按钮失效
            if ($("#isSend").val() == '1') {
                $("#saveBtn").linkbutton("disable");
            } else {
                $("#declarebusinessUpdateBtn").linkbutton("disable");
                $("#declarebusinessDeleteBtn").linkbutton("disable");
            }

            $("#contract_no2").change(function () {
                var value = $("#contract_no2").val();
                if (value != "") {
                    $("#sendStatusCode").combogrid('disable');
                    $("#signStartDate").prop("disabled",true);
                    $("#signEndDate").prop("disabled", true);
                } else {
                    $("#sendStatusCode").combogrid('enable');
                    $("#signStartDate").removeProp("disabled");
                    $("#signEndDate").removeProp("disabled");
                }
            })

            $("#contract_no1").change(function () {
                var value = $("#contract_no1").val();
                if (value != "") {
                    $("#startDate").prop("disabled", true);
                    $("#endDate").prop("disabled",true);
                } else {
                    $("#startDate").removeProp("disabled");
                    $("#endDate").removeProp("disabled");
                }
            })
            $("#declaredBatchDelete").hide();
            $("#setNotSendBtn").hide();
            $("#sendStatusCode").combobox({
                onChange: function (newValue, oldValue) {
                    if (newValue == 0) {
                        $("#declaredBatchDelete").hide();
                        $("#setNotSendBtn").hide();
                    } else {
                        $("#declaredBatchDelete").show();
                        $("#setNotSendBtn").show();
                    }
                }
            });

        })

        //点击“手工填写"按钮触发，解除锁定
        function manualFill() {
            $("#fo input[type=text]").removeProp("disabled");
            $("#loanCate").combogrid('enable');
            $("#customerType").combogrid('enable');
            $("#certificateType").combogrid('enable');
        }
        //保存记录
        function doSave() {
            manualFill();
            $("#fo").submit();
        }
        function openBusinessQueryWindow() {

            //初始化业务查询的datagrid
            $("#businessQueryResultTb").datagrid({
                url: '',
                pagination: true,
                checkOnSelect: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30,50,100,300],
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
                    field: "isLast",
                    title: "是否是最新",
                    width: 80,
                    formatter: function (value, row) {
                        if (value == 'Y') {
                            return '是';
                        } else if (value == 'N') {
                            return '否';
                        }
                    }
                }, {
                    field: "netSignNo",
                    title: "网签编号",
                    width: 220

                }, {
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
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    queryContractByDateId(rowData.dateId);
                },
                onLoadSuccess: function (data) {
                    if (data.total == 0) {
                        //添加一个新数据行
                        $(this).datagrid('appendRow', {contractNo: '<div style="text-align:center;color:red">没有相关记录！</div>'}).datagrid('mergeCells', {
                            index: 0,
                            field: 'contractNo',
                            colspan: 6
                        });
                    }
                    $(this).datagrid('clearChecked');
                }
            })
            $('#businessQueryWindow').window('open');
        }

        function queryContractByDateId(dateId) {
            if(dateId != undefined){
                 window.location.href = "${basePath}/contractIssueInfo.do?method=findContractByDateId&dateId=" + dateId;
                $('#businessQueryWindow').window('close');
            }
        }
        //批量保存
        function doBatchSave() {
            var ids = [];
            var rows = $("#businessQueryResultTb").datagrid("getSelections");
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].dateId);
            }

            $.ajax({
                type: "POST",
                url: "${basePath}/contractIssueInfo.do?method=batchSaveContract",
                data: {"ids": ids.toString()},
                dataType: "json",
                success: function (data) {
                    if (data == "1") {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败," + data, "warning");
                    }
                }
            });
        }
        //根据签订时间段从贷款合同信息的表中查询
        function doBusinessQuery() {
            var value = $("#contract_no1").val();
            if (value != "") {
                $("#businessQueryResultTb").datagrid({
                    queryParams: {"contractNo": value, "sendStatus": ''},
                    url: "${basePath}/contractIssueInfo.do?method=findContractInfoByContractNo"
                });
            } else if ($("#businessQueryForm").form('validate') == true) {
                $("#businessCheckMsg").html("");
                if (!checkEndTime("startDate", "endDate")) {
                    $("#businessCheckMsg").html("结束时间必须晚于开始时间！");
                    return;
                } else {
                    $("#businessQueryResultTb").datagrid({
                        queryParams: form2Json("businessQueryForm"),
                        url: "${basePath}/contractInfo.do?method=findLastContractBySendStatus"
                    });
                }
            }
        }
        function openDeclareQueryWindow() {

            //初始化申报查询的datagrid
            $("#declareQueryResultTb").datagrid({
                url: '',
                checkOnSelect: true,
                pagination: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30,50,100,300],
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
                    field: "ddAmt",
                    title: "发放金额",
                    width: 100,
                }, {
                    field: "signDate",
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
                }, {
                    field: "isLast",
                    title: "是否是最新",
                    width: 80,
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
                    width: 80,
                    formatter: function (value, row) {
                        if ('100001' == value) {
                            return '新增记录';
                        } else if ('100002' == value) {
                            return '修改记录';
                        } else if ('100003' == value) {
                            return '删除记录';
                        }
                    }

                }, {
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
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    queryContractByContractId(rowData.id);
                },
                onLoadSuccess: function (data) {
                    if (data.total == 0) {
                        //添加一个新数据行
                        $(this).datagrid('appendRow', {contractNo: '<div style="text-align:center;color:red">没有相关记录！</div>'}).datagrid('mergeCells', {
                            index: 0,
                            field: 'contractNo',
                            colspan: 8
                        })
                    }
                    $(this).datagrid('clearChecked');
                }
            })

            $('#declareQueryWindow').window('open');
        }
        //根据申报状态查询合同简略信息
        function doDeclareQuery() {
            var value = $("#contract_no2").val();
            if (value != "") {
                $("#declareQueryResultTb").datagrid({
                    queryParams: {"contractNo": value},
                    url: "${basePath}/contractIssueInfo.do?method=findContractBriefInfoByContractNo"
                });

            } else if ($("#declareQueryForm").form('validate') == true) {
                var flag = true;
                $("#businessCheckMsg").html("");
                if ($("#insertEndDate").val() != null && !checkEndTime("insertStartDate", "insertEndDate")) {
                    $("#declarebusinessCheckMsg").html("结束时间必须晚于开始时间！");
                    flag = false;
                    return;
                }
                if (flag) {
                    $("#declareQueryResultTb").datagrid({
                        queryParams: form2Json("declareQueryForm"),
                        url: "${basePath}/contractIssueInfo.do?method=findContractIssueInfoBySendStatus"
                    });
                }
            }
        }

        //根据记录id查询合同信息
        function queryContractByContractId(id) {
            if (id != undefined) {
                window.location.href = "${basePath}/contractIssueInfo.do?method=findContractIssueInfoById&id=" + id;
                $('#declareQueryWindow').window('close');
            }
        }

        //已申报记录更新信息
        function declaredUpdate() {
            manualFill();
            $("#fo").submit();
        }

        //已申报记录删除
        function declaredDelete() {
            manualFill();
            $.ajax({
                    type: "POST",
                    url: "${basePath}/contractIssueInfo.do?method=deleteRecord",
                    data: form2Json("fo"),
                    dataType: "json",
                    success: function (data) {

                        if (data.sucesss) {
                            $.messager.alert("提示消息", "操作成功", "info");
                        } else {
                            $.messager.alert("提示消息", "操作失败," + data.msg, "warning");
                        }
                    }


                }
            )
        }
        //已申报批量删除
        function doDeclaredBatchDelete() {
            var ids = [];
            var rows = $("#declareQueryResultTb").datagrid("getSelections");
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].dateId);
            }

            $.ajax({
                type: "POST",
                url: "${basePath}/contractIssueInfo.do?method=deleteRecordBatch",
                data: {"ids": ids.toString()},
                dataType: "json",
                success: function (data) {
                    if (data.sucesss) {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败," + data.msg, "warning");
                    }
                }
            });
        }
        //设置为未申报
        function doSetNotSend() {
            var ids = [];
            var rows = $("#declareQueryResultTb").datagrid("getSelections");
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            if (ids.length == 0) {
                $.messager.alert("提示消息", "请选择至少一条记录进行操作", "info");
                return;
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/contractIssueInfo.do?method=setNotSend",
                data: {"ids": ids.toString()},
                dataType: "json",
                success: function (data) {
                    if (data.sucesss) {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败 " + data.msg, "warning");
                    }
                }
            });
        }

        //刷新当前页
        function doReset() {
            window.location.href = "${basePath}/contractIssueInfo.do?method=showContractIssueInfo";
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
    </script>
</head>
<body>
<form id="fo" action="${basePath}/contractIssueInfo.do?method=saveContractIssueInfo" method="post">
    <div region="north" style="overflow: hidden;" border="false"
         data-options=" collapsed:false">
        <div style="padding: 1px; background: #EFEFEF;" id="toolbarid">
            <a id="addBtn" href="###" class="easyui-linkbutton" plain="true"
               iconCls="icon-add" onclick="manualFill()">手工填写</a>
            <a id="businessbusinessQueryBtn" name="businessbusinessQueryBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-search"
               onclick="openBusinessQueryWindow()">业务查询</a>
            <a id="declarebusinessQueryBtn"
               name="declarebusinessQueryBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-search"
               onclick="openDeclareQueryWindow()">申报查询</a>
            <a id="saveBtn"
               name="saveBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-save"
               onclick="doSave();">保存</a>
            <a id="resetBtn" name="resetBtn"
               href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               iconCls="icon-reload" onclick="doReset();">重置</a>
            <a id="declarebusinessUpdateBtn"
               name="declarebusinessUpdateBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-save"
               onclick="declaredUpdate()">已申报修改</a>
            <a id="declarebusinessDeleteBtn"
               name="declarebusinessDeleteBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-del"
               onclick="declaredDelete()">已申报删除</a>
        </div>
    </div>
    <div region="center" border="false">
        <div class="editBlock">
            <table width="99%">
                <tr>
                    <td colspan="4" class="subtitle">合同信息</td>
                    <input type="hidden" name="isSend" id="isSend" value="${model.isSend }"/>
                    <input type="hidden" name="dateId" id="dateId" value="${model.dateId }"/>
                    <input type="hidden" name="isLast" id="isLast" value="${model.isLast }"/>
                    <input type="hidden" name="netSignNo" id="netSignNo" value="${model.netSignNo }">
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>合同编号</th>
                    <td width="32%">
                        <input type="text" id="id" name="id"
                               value="${model.id}" class="inputText" hidden/>
                        <input type="text" id="contractNo" name="contractNo"
                               value="${model.contractNo}" class="inputText"/>
                        <span class="warning">${errors['contractNo']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>发放编号</th>
                    <td width="32%">
                        <input type="text" id="dueBillNo" name="dueBillNo"
                               value="${model.dueBillNo}" class="inputText">
                         <span class="warning">${errors['dueBillNo']}</span>
                    </td>
                </tr>

                <tr>
                    <th width="15"><span class="warning">*</span>借款人类别</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="customerType" name="customerType" style="width:251px;"
                               value="${model.customerType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'借款人类别代码',width:60},
										{field:'dictName',title:'借款人类别',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['customerType']}</span>
                    </td>

                    <th width="15%"><span class="warning">*</span>借款人名称</th>
                    <td>
                        <input type="text" id="customerName" name="customerName"
                               value="${model.customerName}" class="inputText">
                        <span class="warning">${errors['customerName']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>借款人证件类型</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="certificateType" name="certificateType" style="width:251px;"
                               value="${model.certificateType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CERTIFICATE_TYPE',
									columns: [[
										{field:'dictCode',title:'证件代码',width:60},
										{field:'dictName',title:'证件名称',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['certificateType']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>借款人证件号码</th>
                    <td>
                        <input type="text" id="certificateNo" name="certificateNo"
                               value="${model.certificateNo}" class="inputText"/>
                        <span class="warning">${errors['certificateNo']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>发放金额(元)</th>
                    <td width="32%">
                        <input type="text" id="ddAmt" name="ddAmt" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.ddAmt}"
                               class="easyui-numberbox"/>
                        <span class="warning">${errors['ddAmt']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>利率性质</th>
                    <td>
                        <input class="easyui-combogrid" id="rateType" name="rateType" style="width:251px;"
                               value="${model.rateType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'520001',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=RATE_TYPE',
									columns: [[
										{field:'dictCode',title:'利率性质代码',width:60},
										{field:'dictName',title:'利率性质',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['rateType']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>月利率(‰)</th>
                    <td width="32%">
                        <input type="text" id="intRate" name="intRate" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.intRate}" class="easyui-numberbox"
                               precision="2"/>
                        <span class="warning">${errors['intRate']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>逾期月利率(‰)</th>
                    <td>
                        <input type="text" id="priPltyRate" name="priPltyRate" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.priPltyRate}"
                               class="easyui-numberbox"
                               precision="2"/>
                        <span class="warning">${errors['priPltyRate']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>合同签订日期</th>
                    <td width="32%">
                        <input type="text" id="signDate" name="signDate"
                               value="${model.signDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['signDate']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>发放日期</th>
                    <td width="32%">
                        <input type="text" id="ddDate" name="ddDate"
                               value="${model.ddDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['ddDate']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>到期日期</th>
                    <td width="32%">
                        <input type="text" id="matureDate" name="matureDate"
                               value="${model.matureDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['matureDate']}</span>
                    </td>
                    <th width="15%">展期余额</th>
                    <td>
                        <input type="text" id="extOutstanding" name="extOutstanding" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.extOutstanding}"
                               class="easyui-numberbox"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%">展期开始日期</th>
                    <td width="32%">
                        <input type="text" id="extStartDate" name="extStartDate"
                               value="${model.extStartDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                    </td>
                    <th width="15%">展期到期日期</th>
                    <td width="32%">
                        <input type="text" id="extEndDate" name="extEndDate"
                               value="${model.extEndDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>投放区域</th>
                    <td>
                        <input class="easyui-combogrid" id="zone" name="zone" style="width:251px;"
                               value="${model.zone}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=ZONE',
									columns: [[
										{field:'dictCode',title:'投放区域代码',width:60},
										{field:'dictName',title:'投放区域',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['zone']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>担保方式</th>
                    <td>
                        <input class="easyui-combogrid" id="guarType" name="guarType" style="width:251px;"
                               value="${model.guarType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=GUAR_TYPE',
									columns: [[
										{field:'dictCode',title:'担保方式代码',width:60},
										{field:'dictName',title:'担保方式',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['guarType']}</span>
                    </td>
                </tr>
                <tr>
                    <th><span class="warning">*</span>贷款期限</th>
                    <td>
                        <input class="easyui-combogrid" id="term" name="term" style="width:251px;"
                               value="${model.term}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=TERM',
									columns: [[
										{field:'dictCode',title:'贷款期限代码',width:190},
										{field:'dictName',title:'贷款期限',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['term']}</span>
                    </td>
                    <th><span class="warning">*</span>贷款用途</th>
                    <td>
                        <input class="easyui-combogrid" id="purpose" name="purpose" style="width:251px;"
                               value="${model.purpose}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=PURPOSE',
									columns: [[
										{field:'dictCode',title:'贷款用途代码',width:190},
										{field:'dictName',title:'贷款用途',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['purpose']}</span>
                    </td>

                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>贷款对象</th>
                    <td>
                        <input class="easyui-combogrid" id="loanObject" name="loanObject" style="width:251px;"
                               value="${model.loanObject}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=LOAN_OBJECT',
									columns: [[
										{field:'dictCode',title:'贷款对象代码',width:60},
										{field:'dictName',title:'贷款对象',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['loanObject']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>贷款对象规模</th>
                    <td>
                        <input class="easyui-combogrid" id="loanObjectSize" name="loanObjectSize" style="width:251px;"
                               value="${model.loanObjectSize}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=LOAN_OBJECT_SIZE',
									columns: [[
										{field:'dictCode',title:'贷款对象规模代码',width:60},
										{field:'dictName',title:'贷款对象规模',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                        <span class="warning">${errors['loanObjectSize']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>计息方式</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="rateCalcMode" name="rateCalcMode" style="width:251px;"
                               value="${model.rateCalcMode}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=RATE_CALC_MODE',
									columns: [[
										{field:'dictCode',title:'计息方式代码',width:100},
										{field:'dictName',title:'计息方式',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                        <span class="warning">${errors['rateCalcMode']}</span>
                    </td>
                    <th width="15"><span class="warning">*</span>扣款方式</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="repayMode" name="repayMode" style="width:251px;"
                               value="${model.repayMode}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'430002',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=REPAY_MODE',
									columns: [[
										{field:'dictCode',title:'扣款方式代码',width:100},
										{field:'dictName',title:'扣款方式',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                        <span class="warning">${errors['repayMode']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>投放行业</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="industry" name="industry" style="width:251px;"
                               value="${model.industry}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=INDUSTRY',
									columns: [[
										{field:'dictCode',title:'投放行业代码',width:100},
										{field:'dictName',title:'投放行业',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                        <span class="warning">${errors['industry']}</span>
                    </td>

                    <th width="15%"><span class="warning">*</span>五级分类</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="riskLevel" name="riskLevel" style="width:251px;"
                               value="${model.riskLevel}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'510001',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=RISK_LEVEL',
									columns: [[
										{field:'dictCode',title:'分类代码',width:100},
										{field:'dictName',title:'五级分类',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['riskLevel']}</span>
                    </td>
                </tr>
                <tr>

                    <th width="15%"><span class="warning">*</span>发放状态</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="issueStatus" name="issueStatus" style="width:251px;"
                               value="${model.issueStatus}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'540002',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=ISSUE_STATUS',
									columns: [[
										{field:'dictCode',title:'发放状态代码',width:100},
										{field:'dictName',title:'发放状态',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['issueStatus']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>贷款类别</th>
                    <td>
                        <input class="easyui-combogrid" id="loanCate" name="loanCate" style="width:251px;"
                               value="${model.loanCate}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=LOAN_CATE',
									columns: [[
										{field:'dictCode',title:'贷款类别',width:60},
										{field:'dictName',title:'贷款名称',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                    onChange:function(newValue,oldValue){
                                          isEntrustedLoan(newValue);
                                        }"/>
                        <span class="warning">${errors['loanCate']}</span>
                    </td>

                </tr>
                <tr>
                    <th width="15%">是否额度项下贷款</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="isRealQuotaLoan" name="isRealQuotaLoan"
                               value="${model.isRealQuotaLoan}"
                               style="width:251px;"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=IS_OR_NOT',
									columns: [[
										{field:'dictCode',title:'代码',width:60},
										{field:'dictName',title:'名称',width:195}
									]],
									fitColumns: true,
									nowrap:false
                                   "/>
                        <span class="warning">${errors['isRealQuotaLoan']}</span>
                    </td>
                    <th width="15%">授信额度协议编号</th>
                    <td>
                        <input type="text" id="realQuotaNo" name="realQuotaNo"
                               value="${model.realQuotaNo}" class="inputText"/>

                    </td>
                </tr>
                <tr>
                    <th width="15%">公允价值</th>
                    <td width="32%">
                        <input type="text" id="fairAmt" name="fairAmt" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.fairAmt}"
                               class="easyui-numberbox"/>
                    </td>
                    <th width="15%">备注</th>
                    <td width="32%">
                        <input type="text" id="remark" name="remark"
                               value="${model.remark}" class="inputText">
                    </td>
                </tr>
            </table>
        </div>
    </div>

</form>

<%--业务查询模态框--%>
<div id="businessQueryWindow" class="easyui-window" title="业务查询"
     data-options="modal:true,closed:true,iconCls:'icon-save',left:'100px',top:'100px'"
     style="width:1100px;height:600px;padding:10px;">

    <form action="" method="post" id="businessQueryForm">
        <table>
            <tr>
                <th width="15%">签约日期：</th>
                <td>
                    <input type="text" id="startDate" name="startDate" data-options="required:true"
                           class="easyui-validatebox"
                           style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
                           onclick="WdatePicker()"/>至
                </td>

                <td>

                    <input type="text" id="endDate" name="endDate" data-options="required:true" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" onclick="WdatePicker()"
                           class="easyui-validatebox"/>


                </td>
            </tr>
            <tr>
                <th width="5%">合同编号:</th>
                <td>
                    <input type="text" id="contract_no1" name="contract_no" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;">
                </td>

            </tr>
            <tr>
                <th></th>
                <td>
                    <input id="businessQueryBtn" type="button" class="inputButton" onclick="doBusinessQuery();"
                           value="查询"/>
                    <input id="batchSaveBtn" type="button" class="inputButton" onclick="doBatchSave();"
                           value="保存"/>
                </td>
            </tr>
        </table>
    </form>
    <span id="businessCheckMsg" style="color:red;"></span>
    <br/>
    <table id="businessQueryResultTb">
    </table>
</div>

<%--申报查询模态框--%>
<div id="declareQueryWindow" class="easyui-window" title="申报查询"
     data-options="modal:true,closed:true,iconCls:'icon-save',left:'100px',top:'100px'"
     style="width:1100px;height:600px;padding:10px;">

    <form action="" method="post" id="declareQueryForm">
        <table>
            <tr>
                <th width="15%">是否已申报：</th>
                <td width="25%">
                    <select id="sendStatusCode" name="sendStatusCode" class="easyui-combobox" style="width:75px;">
                        <option value="0" selected> 否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <br/>
            <tr>
                <th width="15%">签约日期：</th>
                <td>
                    <input type="text" id="signStartDate" name="signStartDate" data-options="required:true"
                           class="easyui-validatebox" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;"
                           onclick="WdatePicker()" class="inputText"/> 至
                </td>

                <td>
                    <input type="text" id="signEndDate" name="signEndDate" data-options="required:true"
                           class="easyui-validatebox" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;"
                           onclick="WdatePicker()" class="inputText"/>
                </td>
            </tr>
            <tr>
                <th width="5%">合同编号:</th>
                <td>
                    <input type="text" id="contract_no2" name="contract_no" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;">
                </td>

            </tr>
            <tr>
                <th></th>
                <td><input id="declareQueryBtn" type="button" class="inputButton" onclick="doDeclareQuery();"
                           value="查询"/>
                    <input id="declaredBatchDelete" type="button" class="inputButton" onclick="doDeclaredBatchDelete()"
                           value="已申报删除"/>
                    <input id="setNotSendBtn" type="button" class="inputButton" onclick="doSetNotSend()" value="设置为未申报" />
                </td>

            </tr>
        </table>
    </form>
    <span id="declarebusinessCheckMsg" style="color:red;"></span>
    <br/>
    <table id="declareQueryResultTb">
    </table>
</div>
</body>
</html>
