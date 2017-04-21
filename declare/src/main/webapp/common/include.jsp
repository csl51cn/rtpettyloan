<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sys" uri="http://com.global.framework/buttonRight"%>
<%
String basePath = request.getContextPath();
request.setAttribute("basePath", basePath);
%>
<link href="${basePath}/resources/css/main.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/resources/css/msgtip.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/resources/css/validform.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/resources/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/resources/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/resources/DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/resources/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/mathcontext.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/bigdecimal.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<script src="${basePath}/resources/lhgdialog/lhgdialog.min.js?skin=iblue" charset="UTF-8" type="text/javascript"></script>
<script src="${basePath}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${basePath}/resources/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>
<script src="${basePath}/resources/DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${basePath}/resources/jqDatePicker/jquery.DatePicker.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.msg.tip.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/Validform_v5.3.1.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.main.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.utils.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.common.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.datetime.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.eui.extend.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.constants.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/sys.bspt.js" type="text/javascript"></script>
<script src="${basePath}/resources/js/json2.js" type="text/javascript"></script>

<script type="text/javascript">
<!--
var basePath = "${basePath}";
//-->
</script>

<!-- 已申报修改和删除操作的右键菜单定义 -->
<div id="declareModifyAndDel" class="easyui-menu" style="width: 200px;display: none;">
	<div id="declareModify" onclick="editDeclare('C')">已申报修改</div>
	<div id="declareDel" onclick="editDeclare('D')">已申报删除</div>
</div>
