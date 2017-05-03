<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>交易查询</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript" src="${basePath }/resources/business/spfeValidator.js"></script>
    <script type="text/javascript">
        function manualFill() {
            $("input[type=text]").removeProp("readonly");
            $("input[type=text]").each(function () {
                this.style.backgroundColor = "#FFFFFF";
            });
            $("input[disabled=disabled]").removeProp("disabled");

        }

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
        function checkEndTime() {
            var startDate = $("#startDate").val();
            var start = new Date(startDate.replace("-", "/").replace("-", "/"));
            var endDate = $("#endDate").val();
            var end = new Date(endDate.replace("-", "/").replace("-", "/"));
            if (end < start) {
                return false;
            }
            return true;
        }

        function doBusinessQuery() {
            /*if ($("#startDate").val() == "") {
             $("#checkMsg").html("请输入开始时间！");
             return;
             }
             if ($("#endDate").val() == "") {
             $("#checkMsg").html("请输入结束时间！");
             return;
             }*/
            if (!checkEndTime()) {
                $("#checkMsg").html("结束时间必须晚于开始时间！");
                return;
            } else {
                $("#queryResultTb").datagrid({
                    url: '${basepath}/pettyLoanContract.do?method=findPettyLoanContractByDate',
                    singleSelect: true,
                    pagination: true,
                    pageSize: 15,
                    pageList: [5, 10, 15, 20, 30, 50, 80, 200],
                    queryParams: form2Json("businessQueryForm"),
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
                        formatter: function(value,row){
                            return formatDatebox(value);
                        }
                    }]],

                    onDblClickRow:function(rowIndex, rowData){

                        queryBusinessById(rowData.id);
                        $('#businessQueryWindow').window('close');
                    }
                })
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

        function queryBusinessById(id){

            window.location.href = "${basepath}/pettyLoanContract.do?method=findpettyLoanContractById&id="+id;

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
            <a id="businessQueryBtn" name="businessQueryBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-search"
               onclick="$('#businessQueryWindow').window('open')">业务查询</a>
            <a id="declareQueryBtn"
               name="declareQueryBtn" href="javascript:void(0)"
               class="easyui-linkbutton" plain="true" iconCls="icon-search"
               onclick=" ">申报查询</a>
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
                    <th width="15%">合同编号</th>
                    <td width="25%">
                        <input type="text" id="id" name="id"
                        value="${model.id}" class="inputText" hidden
                        />
                        <input type="text" id="contractNo" name="contractNo"
                               value="${model.contractNo}" class="inputText"
                        /></td>
                    <th width="15%">贷款类型</th>
                    <td>

                        <input class="easyui-combogrid" id="loanCate" name="loanCate" style="width:251px;"
                               value="${model.loanCate}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=LOAN_CATE',
									columns: [[
										{field:'dictCode',title:'贷款类型',width:60},
										{field:'dictName',title:'贷款名称',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                    </td>
                </tr>
                <tr>
                    <th width="15">借款人类型</th>
                    <td width="25%">

                        <input class="easyui-combogrid" id="customerType" name="customerType" style="width:251px;"
                               value="${model.customerType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'借款人类型代码',width:60},
										{field:'dictName',title:'借款人类型',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                    </td>

                    <th width="15%">借款人名称</th>
                    <td>
                        <input type="text" id="customerName" name="customerName"
                               value="${model.customerName}" class="inputText">
                    </td>
                </tr>
                <tr>
                    <th width="15%">借款人证件类型</th>
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

                    <th width="15%">借款人证件号码</th>
                    <td>
                        <input type="text" id="certificateNo" name="certificateNo"
                               value="${model.certificateNo}" class="inputText"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%"></span>委托人类型</th>
                    <td width="25%">

                        <input class="easyui-combogrid" id="conCustomerType" name="conCustomerType" style="width:251px;"
                               value="${model.conCustomerType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'委托人类型代码',width:60},
										{field:'dictName',title:'委托人类型',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                    </td>
                    <th width="15%">委托人名称</th>
                    <td>
                        <input type="text" id="conCustomerName" name="conCustomerName"
                               value="${model.conCustomerName}" class="inputText">
                    </td>
                </tr>
                <tr>
                    <th width="15%">委托人证件类型</th>
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

                    <th width="15%">委托人证件号码</th>
                    <td>
                        <input type="text" id="conCertificateNo" name="conCertificateNo"
                               value="${model.conCertificateNo}" class="inputText"/>
                    </td>
                </tr>
                <tr>
                    <th width="15%">委托人代理费</th>
                    <td width="25%">
                        <input type="text" id="conFee" name="conFee"
                               value="${model.conFee}" class="inputText">
                    </td>
                    <th width="15%">合同金额</th>
                    <td>
                        <input type="text" id="contractAmount" name="contractAmount"
                               value="${model.contractAmount}" class="inputText"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%">月利率</th>
                    <td width="25%">
                        <input type="text" id="intRate" name="intRate"
                               value="${model.intRate}" class="inputText">
                    </td>
                    <th width="15%">合同签订日期</th>
                    <td>
                        <input type="text" id="contractSignDate" name="contractSignDate"
                               value="${model.contractSignDate}" onclick="WdatePicker()" class="inputText"/>
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
        <th width="15%">业务办理日期：</th>
        <td>
            <input type="text" id="startDate" name="startDate"
                   onclick="WdatePicker()" class="inputText"/>
        </td>
        至
        <td>
            <input type="text" id="endDate" name="endDate"
                   onclick="WdatePicker()" class="inputText"/>
            <input id="queryBtn" type="button" class="inputButton" onclick="doBusinessQuery();" value="查询"/>
        </td>

    </form>
    <span id="checkMsg" style="color:red;"></span>
    <table id="queryResultTb"></table>
</div>
</body>
</html>