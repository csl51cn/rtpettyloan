<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>交易查询</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript" src="${basePath }/resources/business/spfeValidator.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#conCustomerType").change(doAddPrompt());
            $("#conCustomerName").change(doAddPrompt());
            $("#conCertificateType").change(doAddPrompt());
            $("#conCertificateNo").change(doAddPrompt());
            $("#conFeeHd").change(doAddPrompt());

        })
        //委托人相关项有内容时，添加*提示必填
        function doAddPrompt() {

            $("#conCustomerTypeHd").html("委托人类别");
            $("#conCustomerNameHd").html("委托人名称");
            $("#conCertificateTypeHd").html("委托人证件类型");
            $("#conCertificateNoHd").html("委托人证件编号");
            $("#conFeeHd").html("<span class='warning'>*</span>委托人代理费(元)");
            var flag = false;//标志是否需要添加*
            if (!$("#conCertificateType").val() == "") {
                flag = true;
            }
            if (!$("#conCertificateNo").val() == "") {
                flag = true;
            }
            if (!$("#conCustomerType").val() == "") {
                flag = true;
            }
            if (!$("#conCustomerName").val() == "") {
                flag = true;
            }
            if (!$("#conFee").val() == "") {
                flag = true;
            }

            if(flag){
                $("#conCustomerTypeHd").html("<span  class='warning'>*</span>委托人类别");
                $("#conCustomerNameHd").html("<span class='warning'>*</span>委托人名称");
                $("#conCertificateTypeHd").html("<span class='warning'>*</span>委托人证件类型");
                $("#conCertificateNoHd").html("<span class='warning'>*</span>委托人证件编号");
                $("#conFeeHd").html("<span class='warning'>*</span>委托人代理费(元)");
            }

        }

        //手工填写，解除锁定
        function manualFill() {
            $("#fo input[type=text]").removeProp("disabled");
            $("#loanCate").combogrid({disabled: false});
            $("#customerType").combogrid({disabled: false});
            $("#certificateType").combogrid({disabled: false});
            $("#conCustomerType").combogrid({disabled: false});
            $("#concertificatetype").combogrid({disabled: false});
            $("#conFee").numberbox("enable", true);
            $("#contractAmount").numberbox("enable", true);

        }

        //保存记录
        function doSave() {
            $.ajax({
                type: "post",
                url: "${basepath}/pettyLoanContract.do?method=savePettyLoanContract",
                data: $("#fo").serialize(),
                dataType: "json",
                success: function (data) {
                    data = eval(data);
                    $(':input', '#fo')
                        .not(':button, :submit, :reset, :hidden')
                        .val('')
                        .removeAttr('checked')
                        .removeAttr('selected');//清空表单中的内容
                    if (data.sucesss) {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败", "warning");
                    }

                }

            });
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
        
        function openBusinessQueryWindow() {
            //打开窗口时默认查询前五天至当天的数据
            var today = new Date();//当天日期
            var temp=today.getTime()-1000*60*60*24*5;//前五天
            var fiveDaysAgo=new Date(temp);
            //初始化业务查询的datagrid
            $("#businessQueryResultTb").datagrid({
                url: '${basepath}/pettyLoanContract.do?method=findPettyLoanContractByDate',
                singleSelect: true,
                queryParams:{"startDate":fiveDaysAgo,"endDate":today },
                pagination: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30],
                columns: [[{
                    field: "id",
                    title: "主键",
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
                }]],

                onDblClickRow: function (rowIndex, rowData) {

                    queryBusinessById(rowData.id);
                    $('#businessQueryWindow').window('close');
                }
            })
            $('#businessQueryWindow').window('open');

        }
        
        //根据签订时间段查询
        function doBusinessQuery() {

            $("#businessCheckMsg").html("");
            if (!checkEndTime("startDate", "endDate")) {
                $("#businessCheckMsg").html("结束时间必须晚于开始时间！");
                return;
            } else {
                $("#businessQueryResultTb").datagrid({queryParams: form2Json("businessQueryForm")});
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

        
        function queryBusinessById(id) {
            $.ajax({
                type: "post",
                url: "${basepath}/pettyLoanContract.do?method=findpettyLoanContractById",
                data: {"id": id},
                dataType: "json",
                success: function (data) {
                    $("#id").val(data.id);
                    $("#contractNo").val(data.contractNo);
                    $("#loanCate").combogrid("setValue", data.loanCate);
                    $("#customerType").combogrid("setValue", data.customerType);
                    $("#customerName").val(data.customerName);
                    $("#certificateType").combogrid("setValue", data.certificateType);
                    $("#certificateNo").val(data.certificateNo);
                    $("#conCustomerType").combogrid("setValue", data.conCustomerType);
                    $("#conCustomerName").val(data.conCustomerName);
                    $("#conCertificateType").combogrid("setValue", data.conCertificateType);
                    $("#conCertificateNo").val(data.conCertificateNo);
                    $("#conFee").val(data.conFee);
                    $("#contractAmount").val(data.contractAmount);
                    $("#intRate").val(data.intRate);
                    $("#contractSignDate").val(formatDatebox(data.contractSignDate));

                    //请求数据成功后，设置不可编辑状态，点"手动录入"按钮后解除锁定
                    $("#loanCate").combogrid({disabled: false});
                    $("#customerType").combogrid({disabled: false});
                    $("#certificateType").combogrid({disabled: false});
                    $("#conCustomerType").combogrid({disabled: false});
                    $("#concertificatetype").combogrid({disabled: false});
                    $("#fo input[type=text]").prop("disabled", "disabled");
                }

            });
        }
        
        
        
        function openDeclareQueryWindow(){
            //打开窗口时默认查询前五天至当天的未申报的数据
            var today = new Date();//当天日期
            var temp=today.getTime()-1000*60*60*24*5;//前五天
            var fiveDaysAgo=new Date(temp);
            //初始化申报查询的datagrid
            $("#declareQueryResultTb").datagrid({
                url: '${basepath}/pettyLoanContract.do?method=findPettyLoanContractBySendStatus',
                singleSelect: true,
                queryParams:{"insertStartDate":fiveDaysAgo,"insertEndDate":today ,"sendStatusCode":"0"},
                pagination: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30],
                columns: [[{
                    field: "id",
                    title: "主键",
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
                }
                ]],

                onDblClickRow: function (rowIndex, rowData) {
                    queryBusinessById(rowData.id);
                    $('#declareQueryWindow').window('close');
                }
            })
            $('#declareQueryWindow').window('open');
        }
        
        //根据申报状态查询
        function doDeclareQuery() {
            var flag = true;
            $("#businessCheckMsg").html("");
            if ($("#insertEndDate").val() != null && !checkEndTime("insertStartDate", "insertEndDate")) {
                $("#businessCheckMsg").html("结束时间必须晚于开始时间！");
                flag = false;
                return;
            }
            if (flag) {
                $("#declareQueryResultTb").datagrid({queryParams: form2Json("declareQueryForm")});
            }

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

        </div>
    </div>
    <div region="center" border="false">
        <div class="editBlock">
            <table width="99%">
                <tr>
                    <td colspan="4" class="subtitle">合同信息</td>
                    <input type="hidden" name="sendStatus" id="sendStatus" value="${model.sendStatus }"/>
                </tr>
                <tbody>
                <tr>
                    <th width="15%"><span class="warning">*</span>合同编号</th>
                    <td width="25%">
                        <input type="text" id="id" name="id"
                               value="${model.id}" class="inputText" hidden
                        />
                        <input type="text" id="contractNo" name="contractNo"
                               value="${model.contractNo}" class="inputText"
                        /></td>
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
									nowrap:false">
                    </td>
                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>借款人类别</th>
                    <td width="25%">

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
									nowrap:false">
                    </td>

                    <th width="15%"><span class="warning">*</span>借款人名称</th>
                    <td>
                        <input type="text" id="customerName" name="customerName"
                               value="${model.customerName}" class="inputText">
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>借款人证件类型</th>
                    <td width="25%">
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
									nowrap:false">

                    </td>

                    <th width="15%"><span class="warning">*</span>借款人证件号码</th>
                    <td>
                        <input type="text" id="certificateNo" name="certificateNo"
                               value="${model.certificateNo}" class="inputText"/>
                    </td>
                </tr>
                <tr>
                    <th  id="conCustomerTypeHd" width="15%">委托人类别</th>
                    <td width="25%">

                        <input class="easyui-combogrid" id="conCustomerType" name="conCustomerType" style="width:251px;"
                               value="${model.conCustomerType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'委托人类别代码',width:60},
										{field:'dictName',title:'委托人类别',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                    </td>
                    <th id="conCustomerNameHd" width="15%">委托人名称</th>
                    <td>
                        <input type="text" id="conCustomerName" name="conCustomerName"
                               value="${model.conCustomerName}" class="inputText">
                    </td>
                </tr>
                <tr>
                    <th  id="conCertificateTypeHd" width="15%">委托人证件类型</th>
                    <td width="25%">
                        <input class="easyui-combogrid" id="conCertificateType" name="conCertificateType"
                               value="${model.conCertificateType}"
                               style="width:251px;"
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
									nowrap:false">

                    <th id="conCertificateNoHd" width="15%">委托人证件号码</th>
                    <td>
                        <input type="text" id="conCertificateNo" name="conCertificateNo"
                               value="${model.conCertificateNo}" class="inputText"/>
                    </td>
                </tr>
                <tr>
                    <th id="conFeeHd" width="15%">委托人代理费(元)</th>
                    <td width="25%">
                        <input type="text" id="conFee" name="conFee"
                               value="${model.conFee}" class="inputText">
                    </td>
                    <th width="15%"><span class="warning">*</span>合同金额(元)</th>
                    <td>
                        <input type="text" id="contractAmount" name="contractAmount" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.contractAmount}"
                               class="easyui-numberbox"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>月利率(‰)</th>
                    <td width="25%">
                        <input type="text" id="intRate" name="intRate" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.intRate}" class="easyui-numberbox"
                               precision="2">
                    </td>
                    <th width="15%"><span class="warning">*</span>合同签订日期</th>
                    <td>
                        <input type="text" id="contractSignDate" name="contractSignDate"
                               value="${model.contractSignDate }" onclick="WdatePicker()" class="inputText"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</form>

<%--业务查询模态框--%>
<div id="businessQueryWindow" class="easyui-window" title="业务查询"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width:900px;height:600px;padding:10px;">

    <form action="" method="post" id="businessQueryForm">
        <tr>
            <th width="15%">业务办理日期：</th>
            <td>
                <input type="text" id="startDate" name="startDate" data-options="required:true"
                       class="easyui-validatebox"
                       onclick="WdatePicker()"/>
            </td>
            至
            <td>
                <input type="text" id="endDate" name="endDate" data-options="required:true"
                       onclick="WdatePicker()" class="easyui-validatebox"/>
                <input id="businessQueryBtn" type="button" class="inputButton" onclick="doBusinessQuery();" value="查询"/>
            </td>
        </tr>
    </form>
    <span id="businessCheckMsg" style="color:red;"></span>
    <table id="businessQueryResultTb">
    </table>
</div>

<%--申报查询模态框--%>
<div id="declareQueryWindow" class="easyui-window" title="申报查询"
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width:900px;height:600px;padding:10px;">

    <form action="" method="post" id="declareQueryForm">
        <tr>
            <th width="15%">是否已申报：</th>
            <td width="25%">
                <select id="sendStatusCode" name="sendStatusCode" class="easyui-combobox" style="width:75px;">
                    <option>--请选择--</option>
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </td>
        </tr>
        <br/>
        <tr>
            <th width="15%">合同录入日期：</th>
            <td>
                <input type="text" id="insertStartDate" name="insertStartDate" data-options="required:true"
                       class="easyui-validatebox"
                       onclick="WdatePicker()" class="inputText"/>
            </td>
            至
            <td>
                <input type="text" id="insertEndDate" name="insertEndDate" data-options="required:true"
                       class="easyui-validatebox"
                       onclick="WdatePicker()" class="inputText"/>
            </td>
        </tr>
        <input id="declareQueryBtn" type="button" class="inputButton" onclick="doDeclareQuery();" value="查询"/>
    </form>
    <span id="declarebusinessCheckMsg" style="color:red;"></span>
    <table id="declareQueryResultTb">
    </table>
</div>

</body>
</html>