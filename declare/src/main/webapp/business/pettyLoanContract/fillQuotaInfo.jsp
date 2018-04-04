<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>授信额度信息上报合同信息操作</title>
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
                $("#fo input[type=text]").prop("disabled", true);
                $("#loanCate").combogrid('disable');
                $("#customerType").combogrid('disable');
                $("#certificateType").combogrid('disable');
                $("#conCustomerType").combogrid('disable');
                $("#concertificatetype").combogrid('disable');
            }



            //如果编辑已申报的记录,保存按钮失效,编辑未申报的记录,已申报修改,已申报删除按钮失效
            if ($("#isSend").val() == '1') {
                $("#saveBtn").linkbutton("disable");
            } else {
                $("#declarebusinessUpdateBtn").linkbutton("disable");
                $("#declarebusinessDeleteBtn").linkbutton("disable");
            }

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
                    ;
                }
            });
        })

        //点击“手工填写"按钮触发，解除锁定
        function manualFill() {
            $("#fo input[type=text]").removeProp("disabled");
            $("#loanCate").combogrid('enable');
            $("#customerType").combogrid('enable');
            $("#certificateType").combogrid('enable');
            $("#conCustomerType").combogrid('enable');
            $("#concertificatetype").combogrid('enable');
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
                    title: "额度协议编号",
                    width: 95
                }, {
                    field: "customerName",
                    title: "借款人名称",
                    width: 95

                }, {
                    field: "contractAmount",
                    title: "额度协议金额",
                    width: 90
                },  {
                    field: "usedAmount",
                    title: "已用额度",
                    width: 90
                }, {
                    field: "remainAmount",
                    title: "剩余额度",
                    width: 90
                },{
                    field: "contractSignDate",
                    title: "协议签订日期",
                    width: 100,
                    formatter: function (value, row) {
                        return formatDatebox(value);
                    }
                }, {
                    field: "contractBeginDate",
                    title: "额度协议起期",
                    width: 100,
                    formatter: function (value, row) {
                        return formatDatebox(value);
                    }
                }, {
                    field: "contractEndDate",
                    title: "额度协议止期",
                    width: 100,
                    formatter: function (value, row) {
                        return formatDatebox(value);
                    }
                }, {
                    field: "isCircle",
                    title: "是否循环额度",
                    width: 120,
                    formatter: function (value, row) {
                        if ('740001' == value) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
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
            if (dateId != undefined) {
                window.location.href = "${basePath}/contractInfo.do?method=findContractByDateId&dateId=" + dateId;
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
            if (ids.length == 0) {
                $.messager.alert("提示消息", "请选择至少一条记录进行操作", "info");
                return;
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/contractInfo.do?method=batchSaveContract",
                data: {"ids": ids.toString()},
                dataType: "json",
                success: function (data) {
                    if (data == "1") {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败", "warning");
                    }
                }
            });
        }
        //根据签订时间段从业务系统中查询
        function doBusinessQuery() {
            var value = $("#contract_no1").val();
            if (value != "") {
                $("#businessQueryResultTb").datagrid({
                    queryParams: {"contractNo": value, "sendStatus": ""},
                    url: "${basePath}/quotaInfo.do?method=findQuotaInfoByContractNoFromBizSys"
                });
            } else if ($("#businessQueryForm").form('validate') == true) {
                $("#businessCheckMsg").html("");
                if (!checkEndTime("startDate", "endDate")) {
                    $("#businessCheckMsg").html("结束时间必须晚于开始时间！");
                    return;
                } else {
                    $("#businessQueryResultTb").datagrid({
                        queryParams: form2Json("businessQueryForm"),
                        url: "${basePath}/quotaInfo.do?method=findQuotaInfoByDateFromBizSys"
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
                    field: "isSend",
                    title: "是否已申报",
                    width: 80,
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
                        });
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
                    url: "${basePath}/contractInfo.do?method=findContractBriefInfoByContractNo"
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
                        url: "${basePath}/contractInfo.do?method=findContractBySendStatus"
                    });
                }
            }
        }

        //根据记录id查询合同信息
        function queryContractByContractId(id) {
            if (id != undefined) {
                window.location.href = "${basePath}/contractInfo.do?method=findContractById&id=" + id;
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
                    url: "${basePath}/contractInfo.do?method=deleteRecord",
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
            if (ids.length == 0) {
                $.messager.alert("提示消息", "请选择至少一条记录进行操作", "info");
                return;
            }
            $.ajax({
                type: "POST",
                url: "${basePath}/contractInfo.do?method=deleteRecordBatch",
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
                url: "${basePath}/contractInfo.do?method=setNotSend",
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
            window.location.href = "${basePath}/contractInfo.do?method=showContractInfo";
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
<form id="fo" action="" method="post">
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
                </tr>
                <tbody>
                <tr>
                    <th width="15%"><span class="warning">*</span>额度协议编号</th>
                    <td width="32%">
                        <input type="text" id="id" name="id"
                               value="${model.id}" class="inputText" hidden/>
                        <input type="text" id="contractNo" name="contractNo"
                               value="${model.contractNo}" class="inputText"/>
                                         <span class="warning">${errors['contractNo']}</span>
                    </td>
                    <th width="15">额度协议名称</th>
                    <td width="32%">
                        <input type="text" id="contractName" name="contractName"
                               value="${model.contractName}" class="inputText">
                    </td>

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

                    <th width="15%"><span class="warning">*</span>合同签订日期</th>
                    <td>
                        <input type="text" id="contractSignDate" name="contractSignDate"
                               value="${model.contractSignDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractSignDate']}</span>
                    </td>
                    <th width="15"><span class="warning">*</span>合同有效起始日期</th>
                    <td width="32%">
                        <input type="text" id="contractBeginDate" name="contractBeginDate"
                               value="${model.contractBeginDate } "
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractBeginDate']}</span>
                    </td>
                </tr>
                <tr>

                    <th width="15%"><span class="warning">*</span>合同有效结束日期</th>
                    <td>
                        <input type="text" id="contractEndDate" name="contractEndDate"
                               value="${model.contractEndDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractEndDate']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>额度协议金额(元)</th>
                    <td width="32%">
                        <input type="text" id="contractAmount" name="contractAmount" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.contractAmount}"
                               class="easyui-numberbox"/>
                        <span class="warning">${errors['contractAmount']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>币种</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="ccy" name="ccy" style="width:251px;"
                               value="${model.ccy}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'730001',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CCY',
									columns: [[
										{field:'dictCode',title:'币种代码',width:60},
										{field:'dictName',title:'币种',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                   "/>
                        <span class="warning">${errors['ccy']}</span>
                    <th width="15%"><span class="warning">*</span>已用额度(元)</th>
                    <td width="32%">
                        <input type="text" id="usedAmount" name="usedAmount" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.usedAmount}"
                               class="easyui-numberbox"/>
                        <span class="warning">${errors['usedAmount']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>剩余额度(元)</th>
                    <td width="32%">
                        <input type="text" id="remainAmount" name="remainAmount" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.remainAmount}"
                               class="easyui-numberbox"/>
                        <span class="warning">${errors['remainAmount']}</span>
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
									nowrap:false
                                   "/>
                        <span class="warning">${errors['guarType']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>是否循环额度</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="isCircle" name="isCircle"
                               value="${model.isCircle}"
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
                        <span class="warning">${errors['isCircle']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>协议状态</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="contractStatus" name="contractStatus"
                               style="width:251px;"
                               value="${model.contractStatus}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'490001',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CONTRACT_STATUS',
									columns: [[
										{field:'dictCode',title:'协议状态代码',width:60},
										{field:'dictName',title:'协议状态',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['contractStatus']}</span>
                    </td>

                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>客户经理</th>
                    <td>
                        <input type="text" id="relationManager" name="relationManager"
                               value='<c:choose><c:when test="${model.relationManager !=null }">${model.relationManager}</c:when><c:otherwise>渠道来源</c:otherwise></c:choose>'
                               class="inputText"/>
                        <span class="warning">${errors['relationManager']}</span>
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
                    <input type="text" id="startDate" name="signStartDate" data-options="required:true"
                           class="easyui-validatebox"
                           style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
                           onclick="WdatePicker()"/>至
                </td>

                <td>

                    <input type="text" id="endDate" name="signEndDate" data-options="required:true" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" onclick="WdatePicker()"
                           class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <th width="5%">额度协议编号:</th>
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
                <th width="5%">额度协议编号:</th>
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
                    <input id="setNotSendBtn" type="button" class="inputButton" onclick="doSetNotSend()"
                           value="设置为未申报"/>
                </td>

            </tr>
        </table>
    </form>
    <span id="declarebusinessCheckMsg" style="color:red;"></span>
    <br/>
    <table id="declareQueryResultTb">
    </table>
</div>
<script type="application/javascript">

</script>
</body>
</html>
