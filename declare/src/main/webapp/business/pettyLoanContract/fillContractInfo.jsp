<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>贷款合同上报合同信息操作</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">


        $(function () {
            //保存或更新时根据返回值给出提示
            var msg = "${msg}";
            if (msg !== "") {
                if (msg == '1') {
                    $.messager.alert("提示消息", "操作成功", "info");
                } else if (msg == '0') {
                    $.messager.alert("提示消息", "操作失败", "warning");
                }
            }

            //双击某条数据后，返回详细信息，根据返回的值，设置不可编辑状态，点"手动录入"按钮后解除锁定
            if ("${disabled}") {
                $("#fo input[type=text]").prop("disabled", "disabled");
                $("#loanCate").combogrid({disabled: true});
                $("#customerType").combogrid({disabled: true});
                $("#certificateType").combogrid({disabled: true});
                $("#conCustomerType").combogrid({disabled: true});
                $("#concertificatetype").combogrid({disabled: true});
            }

            //默认委托人相关项隐藏,如果是查询会数据且贷款类型为委托贷款：530002，展示相关项
            var loanCate = "${model.loanCate}";
            if (loanCate == "" || loanCate == "530001") {

                setDisplayStatus("conCustomerTbody", false);
                $("#fo").prop("action", "${basePath}/contractInfo.do?method=saveContract");
            } else if (loanCate == "530002") {
                setDisplayStatus("conCustomerTbody", true);
                $("#fo").prop("action", "${basePath}/contractInfo.do?method=saveEntrustedLoanContract");
            }

            //默认授信额度协议编号隐藏
            $("#realQuotaNo").hide();
            $("#realQuotaNo").parent().prev("th").hide();

            //如果编辑已申报的记录,保存按钮失效,编辑未申报的记录,已申报修改,已申报删除按钮失效
            if ($("#isSend").val() == '1') {
                $("#saveBtn").linkbutton("disable");
            }else{
                $("#declarebusinessUpdateBtn").linkbutton("disable");
                $("#declarebusinessDeleteBtn").linkbutton("disable");
            }

            $("#contract_no2").change(function(){
                var value = $("#contract_no2").val();
                if(value != ""){
                    $("#sendStatusCode").combogrid({disabled: true});
                    $("#insertStartDate").prop("disabled", "disabled");
                    $("#insertEndDate").prop("disabled", "disabled");
                }else{
                    $("#sendStatusCode").combogrid({disabled: false});
                    $("#insertStartDate").removeProp("disabled");
                    $("#insertEndDate").removeProp("disabled");
                }
            })

            $("#contract_no1").change(function(){
                var value=$("#contract_no1").val();
                if(value != ""){
                    $("#startDate").prop("disabled", "disabled");
                    $("#endDate").prop("disabled", "disabled");
                }else{
                    $("#startDate").removeProp("disabled");
                    $("#endDate").removeProp("disabled");
                }

            })
            //跳转当前页面时设置共借人信息
            var coCustomerTypeArray = new Array();
            var coCustomerType1 = "${model.coCustomerType1}";
            var coCustomerType2 = "${model.coCustomerType2}";
            var coCustomerType3 = "${model.coCustomerType3}";
            var coCustomerType4 = "${model.coCustomerType4}";

            coCustomerTypeArray.push(coCustomerType1);
            coCustomerTypeArray.push(coCustomerType2);
            coCustomerTypeArray.push(coCustomerType3);
            coCustomerTypeArray.push(coCustomerType4);

            for (var j = 0; j < coCustomerTypeArray.length; j++) {
                    var id = "coCustomerTbody"+(j + 1);
                if (coCustomerTypeArray[j] != "") {
                   setDisplayStatus(id,true);
                }else{
                    setDisplayStatus(id,false);
                }
            }
        })

        //点击“手工填写"按钮触发，解除锁定
        function manualFill() {
            $("#fo input[type=text]").removeProp("disabled");
            $("#loanCate").combogrid({disabled: false});
            $("#customerType").combogrid({disabled: false});
            $("#certificateType").combogrid({disabled: false});
            $("#conCustomerType").combogrid({disabled: false});
            $("#concertificatetype").combogrid({disabled: false});

        }

        //设置授信额度协议编号显示状态
        function isRealQuotaLoan(value) {
            if (value == '740001') {//是授信额度下贷款
                $("#realQuotaNo").show();
                $("#realQuotaNo").parent().prev("th").show();
            } else if (value == "740002") {//不是授信额度下贷款
                $("#realQuotaNo").hide();
                $("#realQuotaNo").parent().prev("th").hide();
            }
        }

        //设置委托贷款相关项的显示状态
        function setDisplayStatus(id, flag) {
            //true 代表显示，false 代表隐藏
            if (flag) {
                $("#" + id).show();
            } else {
                $("#" + id).hide();
            }
        }

        //判断是否为委托贷款，530001代表自营贷款，530002代表委托贷款
        function isEntrustedLoan(value) {
            if (value == "530001") {
                setDisplayStatus("conCustomerTbody", false);
                $("#fo").prop("action", "${basePath}/contractInfo.do?method=saveContract");
            } else if (value == "530002") {
                setDisplayStatus("conCustomerTbody", true);
                $("#fo").prop("action", "${basePath}/contractInfo.do?method=saveEntrustedLoanContract");
            }
        }

        //保存记录
        function doSave() {
            $("#fo").submit();
        }

        //动态添加共同借款人相关项
        var i = 1;
        function addCoCustomerInfo() {
            if (i == 5) {
                $.messager.alert("提示消息", "最多添加四个共借人!", "warning");
                return;
            }
            var id = "coCustomerTbody" + i;
            setDisplayStatus(id,true);
            i++;
        }

        //删除共借人信息
        function delCoCustomerInfo() {
            if (i > 0) {
                i--;
            } else {
                return;
            }
            setDisplayStatus("coCustomerTbody" + i,false);
        }


        function openBusinessQueryWindow() {

            //初始化业务查询的datagrid
            $("#businessQueryResultTb").datagrid({
                url: '',
                pagination: true,
                checkOnSelect: true,
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
                },{
                    field:"netSignNo",
                    title:"网签编号",
                    width:220

                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    queryContractByContractNo(rowData.contractNo);
                    $('#businessQueryWindow').window('close');
                },
                onLoadSuccess: function () {
                    $(this).datagrid('clearChecked');
                }
            })
            $('#businessQueryWindow').window('open');
        }

        function queryContractByContractNo(contractNo) {
            window.location.href = "${basePath}/contractInfo.do?method=findContractByContractNo&contractNo=" + contractNo;
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
        //根据签订时间段从实时网签的表中查询,贷款合同信息上报依赖于实时网签,实时网签执行后才做贷款合同信息上报操作
        function doBusinessQuery() {
            var value = $("#contract_no1").val();
            if(value != ""){
                $("#businessQueryResultTb").datagrid({
                    queryParams: {"contractNo":value},
                    url: "${basePath}/contractInfo.do?method=findContractByContractNoFromRealTimeContract&sendStatus=1"
                });
            }else if ($("#businessQueryForm").form('validate') == true) {
                $("#businessCheckMsg").html("");
                if (!checkEndTime("startDate", "endDate")) {
                    $("#businessCheckMsg").html("结束时间必须晚于开始时间！");
                    return;
                } else {
                    $("#businessQueryResultTb").datagrid({
                        queryParams: form2Json("businessQueryForm"),
                        url: "${basePath}/pettyLoanContract.do?method=findPettyLoanContractBySendStatus"
                    });
                }
            }
        }
        function openDeclareQueryWindow() {

            //初始化申报查询的datagrid
            $("#declareQueryResultTb").datagrid({
                url: '',
                singleSelect: true,
                pagination: true,
                pageSize: 15,
                pageList: [5, 10, 15, 20, 30],
                columns: [[{
                    field: "id",
                    title: "主键",
                    hidden: true
                }, {
                    field: "dateId",
                    title: "Date_Id",
                    hidden: true
                } ,{
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
                    field: "reportType",
                    title: "上报类型",
                    width: 80,
                    formatter:function(value,row){
                        if('100001' == value){
                            return '新增记录';
                        }else if ('100002' == value){
                            return '修改记录';
                        }else if('100003' == value){
                            return '删除记录';
                        }
                    }

                }, {
                    field: "netSignNo",
                    title: "网签编号",
                    width: 220
                }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    queryContractByContractId(rowData.id);
                    $('#declareQueryWindow').window('close');
                },
                onLoadSuccess:function(){
                    $(this).datagrid('clearChecked');
                }
            })

            $('#declareQueryWindow').window('open');
        }
        //根据申报状态查询合同简略信息
        function doDeclareQuery() {
            var value = $("#contract_no2").val();
            if( value != ""){
                $("#declareQueryResultTb").datagrid({
                    queryParams: {"contractNo":value},
                    url: "${basePath}/contractInfo.do?method=findContractBriefInfoByContractNo"
                });

            }else if($("#declareQueryForm").form('validate') == true){
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
        function  queryContractByContractId(id){
            window.location.href = "${basePath}/contractInfo.do?method=findContractById&id=" + id;
        }

        //已申报记录更新信息
        function declaredUpdate(){
            $("#fo").submit();
        }

        //已申报记录删除
        function declaredDelete(){
            manualFill();
            $.ajax({
                type: "POST",
                url: "${basePath}/contractInfo.do?method=deleteRecord",
                data: form2Json("fo"),
                dataType: "json",
                success: function (data) {
                 //var result = eval("("+data+")");
                  var status = data.sucesss;
                    if (status) {
                        $.messager.alert("提示消息", "操作成功", "info");
                    } else {
                        $.messager.alert("提示消息", "操作失败,"+data.msg, "warning");
                    }
                }


                }
            )
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
            <a id="addCoCustomerInfoBtn" name="resetBtn"
               href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               iconCls="icon-add" onclick="addCoCustomerInfo();">添加共借人信息</a>
            <a id="deleteCoCustomerInfoBtn" name="resetBtn"
               href="javascript:void(0)" class="easyui-linkbutton" plain="true"
               iconCls="icon-del" onclick="delCoCustomerInfo();">删除共借人信息</a>

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
                    <th width="15%"><span class="warning">*</span>合同编号</th>
                    <td width="32%">
                        <input type="text" id="id" name="id"
                               value="${model.id}" class="inputText" hidden/>
                        <input type="text" id="contractNo" name="contractNo"
                               value="${model.contractNo}" class="inputText"/>
                        <span class="warning">${errors['contractNo']}</span>
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
                    <th width="15">合同名称</th>
                    <td width="32%">
                        <input type="text" id="contractName" name="contractName"
                               value="${model.contractName}" class="inputText">
                    </td>
                    <th width="15%">联系人</th>
                    <td>
                        <input type="text" id="linkman" name="linkman"
                               value="${model.linkman}" class="inputText"/>

                    </td>
                </tr>
                <tr>
                    <th width="15">联系电话</th>
                    <td width="32%">
                        <input type="text" id="telephone" name="telephone"
                               value="${model.telephone}" class="inputText">
                    </td>
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
                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>贷款对象规模</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="loanObjectSize" name="loanObjectSize" style="width:251px;"
                               value="${model.loanObjectSize}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=LOAN_OBJECT_SIZE',
									columns: [[
										{field:'dictCode',title:'贷款对象代码',width:60},
										{field:'dictName',title:'贷款对象',width:195}
									]],
									fitColumns: true,
									nowrap:false">
                        <span class="warning">${errors['loanObjectSize']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>合同签订日期</th>
                    <td>
                        <input type="text" id="contractSignDate" name="contractSignDate"
                               value="${model.contractSignDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractSignDate']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>合同有效起始日期</th>
                    <td width="32%">
                        <input type="text" id="contractBeginDate" name="contractBeginDate"
                               value="${model.contractBeginDate } "
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractBeginDate']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>合同有效结束日期</th>
                    <td>
                        <input type="text" id="contractEndDate" name="contractEndDate"
                               value="${model.contractEndDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               class="inputText"/>
                        <span class="warning">${errors['contractEndDate']}</span>
                    </td>

                </tr>
                <tr>
                    <th width="15"><span class="warning">*</span>贷款余额</th>
                    <td width="32%">
                        <input type="text" id="outstanding" name="outstanding" precision="2"
                               value="${model.outstanding}" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;"
                               class="easyui-numberbox">
                        <span class="warning">${errors['outstanding']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>担保方式</th>
                    <td>
                        <input class="easyui-combogrid" id="guarType" name="guarType" style="width:251px;"
                               value="${model.guarType}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'240001',
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
                    <th width="15%"><span class="warning">*</span>逾期月利率(‰)</th>
                    <td>
                        <input type="text" id="priPltyRate" name="priPltyRate" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.priPltyRate}"
                               class="easyui-numberbox"
                               precision="2"/>
                        <span class="warning">${errors['intRate']}</span>
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
                    <th width="15%"><span class="warning">*</span>合同状态</th>
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
										{field:'dictCode',title:'合同状态代码',width:60},
										{field:'dictName',title:'合同状态',width:195}
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
                    <th width="15%"><span class='warning'>*</span>争议解决方式</th>
                    <td>
                        <input class="easyui-combogrid" id="disputeScheme" name="disputeScheme"
                               style="width:251px;"
                               value="${model.disputeScheme}"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'400001',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=DISPUTE_SCHEME',
									columns: [[
										{field:'dictCode',title:'争议解决方式代码',width:60},
										{field:'dictName',title:'争议解决方式',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['disputeScheme']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class="warning">*</span>合同金额(元)</th>
                    <td width="32%">
                        <input type="text" id="contractAmount" name="contractAmount" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.contractAmount}"
                               class="easyui-numberbox"/>
                        <span class="warning">${errors['contractAmount']}</span>
                    </td>
                    <th width="15%">备注</th>
                    <td width="32%">
                        <input type="text" id="remark" name="remark"
                               value="${model.remark}" class="inputText">
                    </td>


                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>是否额度项下贷款</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="isRealQuotaLoan" name="isRealQuotaLoan"
                               value="${model.isRealQuotaLoan}"
                               style="width:251px;"
                               data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									value:'740002',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=IS_OR_NOT',
									columns: [[
										{field:'dictCode',title:'代码',width:60},
										{field:'dictName',title:'名称',width:195}
									]],
									fitColumns: true,
									nowrap:false,
                                    onChange:function(newValue,oldValue){
                                          isRealQuotaLoan(newValue);
                                        }"/>
                        <span class="warning">${errors['isRealQuotaLoan']}</span>
                    </td>
                    <th width="15%">网签编号</th>

                    <td>
                        <input type="text" id="netSignNo" name="netSignNo"
                               value="${model.netSignNo}" class="inputText"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>授信额度协议编号</th>
                    <td>
                        <input type="text" id="realQuotaNo" name="realQuotaNo"
                               value="${model.realQuotaNo}" class="inputText"/>
                        <span class="warning">${errors['realQuotaNo']}</span>
                    </td>
                </tr>

                </tbody>

                <tbody id="conCustomerTbody">
                <tr>
                    <th width="15%"><span class='warning'>*</span>委托人类别</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="conCustomerType" name="conCustomerType"
                               style="width:251px;"
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
									nowrap:false"/>
                        <span class="warning">${errors['conCustomerType']}</span>
                    </td>
                    <th width="15%"><span class='warning'>*</span>委托人名称</th>
                    <td>
                        <input type="text" id="conCustomerName" name="conCustomerName"
                               value="${model.conCustomerName}" class="inputText">
                        <span class="warning">${errors['conCustomerName']}</span>
                    </td>

                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>委托人证件类型</th>
                    <td width="32%">
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
									nowrap:false"/>
                        <span class="warning">${errors['conCertificateType']}</span>
                    </td>
                    <th width="15%"><span class='warning'>*</span>委托人证件号码</th>
                    <td>
                        <input type="text" id="conCertificateNo" name="conCertificateNo"
                               value="${model.conCertificateNo}" class="inputText"/>
                        <span class="warning">${errors['conCertificateNo']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%">代理费(元)</th>
                    <td width="32%">
                        <input type="text" id="conFee" name="conFee" precision="2" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" value="${model.conFee}" class="easyui-numberbox"/>
                    </td>
                    <th width="15%">委托方法定代表人/负责人</th>
                    <td width="32%">
                        <input type="text" id="conJurisitc" name="conJurisitc"
                               value="${model.conJurisitc}" class="inputText"/>
                    </td>


                </tr>
                <tr>
                    <th width="15%">委托人联系电话</th>
                    <td>
                        <input type="text" id="conTelephone" name="conTelephone"
                               value="${model.conTelephone}" class="inputText"/>

                    </td>
                    <th width="15%">委托人地址</th>
                    <td width="32%">
                        <input type="text" id="conLocus" name="conLocus"
                               value="${model.conLocus}" class="inputText"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%">委托人邮编</th>
                    <td>
                        <input type="text" id="conPostalCode" name="conPostalCode"
                               value="${model.conPostalCode}" class="inputText"/>
                    </td>
                    <th width="15%">委托人开户金融机构</th>
                    <td width="32%">
                        <input type="text" id="conFincalOrg" name="conFincalOrg"
                               value="${model.conFincalOrg}" class="inputText"/>
                    </td>

                </tr>
                <tr>
                    <th width="15%">委托人账户</th>
                    <td>
                        <input type="text" id="conAccountNo" name="conAccountNo"
                               value="${model.conAccountNo}" class="inputText"/>
                        <span class="warning">${errors['conAccountNo']}</span>
                    </td>
                    <th width="15%"><span class="warning">*</span>受托人</th>
                    <td width="32%">
                        <input type="text" id="assCustomerName" name="assCustomerName"
                               value="${model.assCustomerName}" class="inputText"/>

                        <span class="warning">${errors['assCustomerName']}</span>
                    </td>
                </tr>

                <tr>
                    <th width="15%">受托人法定代表人/负责人</th>
                    <td>
                        <input type="text" id="assJuristic" name="assJuristic"
                               value="${model.assJuristic}" class="inputText"/>
                    </td>
                    <th width="15%">受托人地址</th>
                    <td width="32%">
                        <input type="text" id="assTelephone" name="assTelephone"
                               value="${model.assTelephone}" class="inputText"/>
                    </td>


                </tr>
                <tr>
                    <th width="15%">受托人联系电话</th>
                    <td>
                        <input type="text" id="assLocus" name="assLocus"
                               value="${model.assLocus}" class="inputText"/>
                    </td>
                    <th width="15%">受托人邮编</th>
                    <td width="32%">
                        <input type="text" id="assPostalCode" name="assPostalCode"
                               value="${model.assPostalCode}" class="inputText"/>
                    </td>

                </tr>
                </tbody>
                <tbody id="coCustomerTbody1">
                <tr>
                <th width='15%'><span class='warning'>*</span>共同借款人1类别</th>
                <td width="32%"><input class="easyui-combogrid" id="coCustomerType1" name="coCustomerType1"
                           style="width:251px;"
                           value="${model.coCustomerType1}"
                           data-options="
									panelWidth: 255,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'共借人类别代码',width:60},
										{field:'dictName',title:'共借人类别',width:195}
									]],
									fitColumns: true,
									nowrap:false"/>
                    <span class='warning'>${errors['coCustomerType']}</span>
                </td>
                <th width='15%'><span class='warning'>*</span>共同借款人1名称</th>
                <td width="32%">
                    <input type="text" id="coCustomerName1" name="coCustomerName1"
                           value="${model.coCustomerName1}" class="inputText">
                    <span class="warning">${errors['conCustomerName']}</span>
                </td>
            </tr>
            <tr>
                <th width="15%"><span class='warning'>*</span>共同借款人证件类型</th>
                <td width="32%">
                    <input class="easyui-combogrid" id="coCertificateType1" name="coCertificateType1"
                           value="${model.coCertificateType1}"
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
									nowrap:false"/>
                    <span class="warning">${errors['conCertificateType']}</span>
                </td>
                <th width="15%"><span class='warning'>*</span>委托人证件号码</th>
                <td width="32%">
                    <input type="text" id="coCertificateNo1" name="coCertificateNo1"
                           value="${model.coCertificateNo1}" class="inputText"/>
                    <span class="warning">${errors['conCertificateNo']}</span>
                </td>
            </tr>
            <tr>
                <th width='15%'>共同贷款人1联系人</th>
                <td width="32%">
                    <input type='text' id='coLinkman1' name='coLinkman1' value="${model.coLinkman1}"
                           class='inputText'/>
                </td>

                <th width="15%">共借人联系电话</th>
                <td width="32%">
                    <input type="text" id="coTelephone1" name="coTelephone1"
                           value="${model.coTelephone1}" class="inputText"/>
                </td>
            </tbody>
                <tbody id="coCustomerTbody2">
                <tr>
                    <th width='15%'><span class='warning'>*</span>共同借款人2类别</th>
                    <td width="32%"><input class="easyui-combogrid" id="coCustomerType2" name="coCustomerType2"
                               style="width:252px;"
                               value="${model.coCustomerType2}"
                               data-options="
                                            panelWidth: 255,
                                            idField: 'dictCode',
                                            textField: 'dictName',
                                            url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
                                            columns: [[
                                                {field:'dictCode',title:'共借人类别代码',width:60},
                                                {field:'dictName',title:'共借人类别',width:295}
                                            ]],
                                            fitColumns: true,
                                            nowrap:false"/>
                        <span class='warning'>${errors['coCustomerType']}</span>
                    </td>
                    <th width='15%'><span class='warning'>*</span>共同借款人2名称</th>
                    <td>
                        <input type="text" id="coCustomerName2" name=" coCustomerName2"
                               value="${model.coCustomerName2}" class="inputText">
                        <span class="warning">${errors['conCustomerName']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>共同借款人证件类型</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="coCertificateType2" name="coCertificateType2"
                               value="${model.coCertificateType2}"
                               style="width:252px;"
                               data-options="
                                            panelWidth: 255,
                                            idField: 'dictCode',
                                            textField: 'dictName',
                                            url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CERTIFICATE_TYPE',
                                            columns: [[
                                                {field:'dictCode',title:'证件代码',width:60},
                                                {field:'dictName',title:'证件名称',width:295}
                                            ]],
                                            fitColumns: true,
                                            nowrap:false"/>
                        <span class="warning">${errors['conCertificateType']}</span>
                    </td>
                    <th width="15%"><span class='warning'>*</span>委托人证件号码</th>
                    <td>
                        <input type="text" id="coCertificateNo2" name="coCertificateNo2"
                               value="${model.coCertificateNo2}" class="inputText"/>
                        <span class="warning">${errors['conCertificateNo']}</span>
                    </td>
                </tr>
                <tr>
                    <th width='15%'>共同贷款人2联系人</th>
                    <td>
                        <input type='text' id='coLinkman2' name='coLinkman2' value="${model.coLinkman2}"
                               class='inputText'/>
                    </td>

                    <th width="15%">共借人联系电话</th>
                    <td>
                        <input type="text" id="coTelephone2" name="coTelephone2"
                               value="${model.coTelephone2}" class="inputText"/>
                    </td>
                </tbody>
                <tbody id="coCustomerTbody3">
                <tr>
                    <th width='15%'><span class='warning'>*</span>共同借款人3类别</th>
                    <td><input class="easyui-combogrid" id="coCustomerType3" name="coCustomerType3"
                               style="width:251px;"
                               value="${model.coCustomerType3}"
                               data-options="
									panelWidth: 455,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
									columns: [[
										{field:'dictCode',title:'共借人类别代码',width:60},
										{field:'dictName',title:'共借人类别',width:395}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class='warning'>${errors['coCustomerType']}</span>
                    </td>
                    <th width='15%'><span class='warning'>*</span>共同借款人3名称</th>
                    <td>
                        <input type="text" id="coCustomerName3" name="coCustomerName3"
                               value="${model.coCustomerName3}" class="inputText">
                        <span class="warning">${errors['conCustomerName']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>共同借款人证件类型</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="coCertificateType3" name="coCertificateType3"
                               value="${model.coCertificateType3}"
                               style="width:251px;"
                               data-options="
									panelWidth: 455,
									idField: 'dictCode',
									textField: 'dictName',
									url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CERTIFICATE_TYPE',
									columns: [[
										{field:'dictCode',title:'证件代码',width:60},
										{field:'dictName',title:'证件名称',width:395}
									]],
									fitColumns: true,
									nowrap:false"/>
                        <span class="warning">${errors['conCertificateType']}</span>
                    </td>
                    <th width="15%"><span class='warning'>*</span>委托人证件号码</th>
                    <td>
                        <input type="text" id="coCertificateNo3" name="coCertificateNo3"
                               value="${model.coCertificateNo3}" class="inputText"/>
                        <span class="warning">${errors['conCertificateNo']}</span>
                    </td>
                </tr>
                <tr>
                    <th width='15%'>共同贷款人3联系人</th>
                    <td>
                        <input type='text' id='coLinkman3' name='coLinkman3' value="${model.coLinkman3}"
                               class='inputText'/>
                    </td>

                    <th width="15%">共借人联系电话</th>
                    <td>
                        <input type="text" id="coTelephone3" name="coTelephone3"
                               value="${model.coTelephone3}" class="inputText"/>
                    </td>
                </tbody>
                <tbody id="coCustomerTbody4">
                <tr>
                    <th width='15%'><span class='warning'>*</span>共同借款人4类别</th>
                    <td><input class="easyui-combogrid" id="coCustomerType4" name="coCustomerType4"
                               style="width:252px;"
                               value="${model.coCustomerType4}"
                               data-options="
                                            panelWidth: 455,
                                            idField: 'dictCode',
                                            textField: 'dictName',
                                            url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CUSTOMER_TYPE',
                                            columns: [[
                                                {field:'dictCode',title:'共借人类别代码',width:60},
                                                {field:'dictName',title:'共借人类别',width:495}
                                            ]],
                                            fitColumns: true,
                                            nowrap:false"/>
                        <span class='warning'>${errors['coCustomerType']}</span>
                    </td>
                    <th width='15%'><span class='warning'>*</span>共同借款人4名称</th>
                    <td>
                        <input type="text" id="coCustomerName4" name=" coCustomerName4"
                               value="${model.coCustomerName4}" class="inputText">
                        <span class="warning">${errors['conCustomerName']}</span>
                    </td>
                </tr>
                <tr>
                    <th width="15%"><span class='warning'>*</span>共同借款人证件类型</th>
                    <td width="32%">
                        <input class="easyui-combogrid" id="coCertificateType4" name="coCertificateType4"
                               value="${model.coCertificateType4}"
                               style="width:252px;"
                               data-options="
                                            panelWidth: 455,
                                            idField: 'dictCode',
                                            textField: 'dictName',
                                            url: '${basePath }/param/paramCommonController.do?method=getDatadict&code=CERTIFICATE_TYPE',
                                            columns: [[
                                                {field:'dictCode',title:'证件代码',width:60},
                                                {field:'dictName',title:'证件名称',width:495}
                                            ]],
                                            fitColumns: true,
                                            nowrap:false"/>
                        <span class="warning">${errors['conCertificateType']}</span>
                    </td>
                    <th width="15%"><span class='warning'>*</span>委托人证件号码</th>
                    <td>
                        <input type="text" id="coCertificateNo4" name="coCertificateNo4"
                               value="${model.coCertificateNo4}" class="inputText"/>
                        <span class="warning">${errors['conCertificateNo']}</span>
                    </td>
                </tr>
                <tr>
                    <th width='15%'>共同贷款人4联系人</th>
                    <td>
                        <input type='text' id='coLinkman4' name='coLinkman4' value="${model.coLinkman4}"
                               class='inputText'/>
                    </td>

                    <th width="15%">共借人联系电话</th>
                    <td>
                        <input type="text" id="coTelephone4" name="coTelephone4"
                               value="${model.coTelephone4}" class="inputText"/>
                    </td>
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

                    <input type="hidden" name="sendStatusCode" data-options="required:true"
                           value="1"/>
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
                <th> </th>
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
     data-options="modal:true,closed:true,iconCls:'icon-save'"
     style="width:900px;height:600px;padding:10px;">

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
                    <input type="text" id="insertStartDate" name="signStartDate" data-options="required:true"
                           class="easyui-validatebox" style="border:1px solid #95B8E7;
                        *color:#007fca;width:180px;padding:4px 2px;"
                           onclick="WdatePicker()" class="inputText"/> 至
                </td>

                <td>
                    <input type="text" id="insertEndDate" name="signEndDate" data-options="required:true"
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
                <th> </th>
                <td><input id="declareQueryBtn" type="button" class="inputButton" onclick="doDeclareQuery();"
                           value="查询"/></td>
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
