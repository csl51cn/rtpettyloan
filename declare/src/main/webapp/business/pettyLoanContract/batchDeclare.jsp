<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>批量报文发送</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">
        $(function () {
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
                    queryContractByContractId(rowData.id);
                }
            })

        })

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

<form action="" method="post">
    <div region="center" border="false">
        <div class="editBlock">
            <table>
                <tr>
                    <th>类型：</th>
                    <td>
                        <input class="easyui-combogrid"
                               style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
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
									nowrap:false">
                    </td>
                </tr>
                <tr>
                    <th width="15%">签约日期：</th>
                    <td>
                        <input type="text" id="startDate" name="startDate" data-options="required:true"
                               class="easyui-validatebox"
                               style="border:1px solid #95B8E7;*color:#007fca;width:245px;padding:4px 2px;"
                               onclick="WdatePicker()"/>至
                        <input type="text" id="endDate" name="endDate" data-options="required:true" style="border:1px solid #95B8E7;
                        *color:#007fca;width:245px;padding:4px 2px;" onclick="WdatePicker()"
                               class="easyui-validatebox"/>
                        <input id="businessQueryBtn" type="button" class="inputButton" onclick="doDeclare();"
                               value="报文发送"/>
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