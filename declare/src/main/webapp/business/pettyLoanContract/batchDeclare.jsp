<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>批量报文发送</title>
    <jsp:include page="../../common/include.jsp"></jsp:include>
    <script type="text/javascript">


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
</body>
</html>