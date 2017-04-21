<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<script type="text/javascript">
<!--
var state = "${transState}";//流程状态
if (state == '4' || state == '6' || state == '7') {
	$("#CANCEL_REMARK").attr("disabled", true);
	$("#CANCEL_REASON").combobox({
		"disabled" : true
	});
}
//-->
</script>
<div class="editBlock">
	<table id="doTask">
		<tr>
			<th width="15%"><span class="warning">*</span>撤销原因</th>
			<td><ebills:select id="CANCEL_REASON" name="CANCEL_REASON"  expAttribute="data-options=\"required:true\""
					style="width:250px" diccode="CANCEL_REASON" value="${mode.CANCEL_REASON}"
					clazz=" easyui-combobox"></ebills:select></td>
		</tr>
		<tr>
			<th width="15%"><span class="warning">*</span>撤销说明</th>
			<td colspan="3" style="border-top: 1px"><textarea rows="1"
					cols="1" style="height: 50px; width: 80%" id="CANCEL_REMARK"
					name="CANCEL_REMARK">${mode.CANCEL_REMARK}</textarea></td>
		</tr>
	</table>
</div>