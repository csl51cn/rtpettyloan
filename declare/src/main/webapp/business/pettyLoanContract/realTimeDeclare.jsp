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
        function doDeclare() {
            $("#dateCheckMsg").html("");
            if (!checkEndTime("startDate", "endDate")) {
                $("#dateCheckMsg").html("结束时间必须晚于开始时间！");
                return;
            } else {

                $("#fo").submit();
            }
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
<body>

<form id="fo" method="post" action="${basePath}/realTimeDeclare.do?method=sendRealTimeDeclare">
    <div region="center" border="false">
        <div class="editBlock">
            <table>
                <tr>
                    <th>类型：</th>
                    <td>
                        实时网签 &nbsp <span id="dateCheckMsg" style="color:red;"/>
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
</body>
</html>