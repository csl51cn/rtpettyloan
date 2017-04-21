<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<div class="editBlock">
	<table>
		<tbody>
			<tr>
				<th width="15%"><span class="warning">*</span>修改原因代码</th>
				<td colspan="3"><ebills:select id="MOD_REASON_CODE" name="MOD_REASON_CODE"  expAttribute="data-options=\"required:true\""
						style="width:250px" diccode="MOD_REASON_CODE"  value="${mode.MOD_REASON_CODE }"
						clazz=" easyui-combobox"></ebills:select></td>
				</tr>
				<tr>
				<th width="15%"><span class="warning">*</span>修改原因说明</th>
				<td colspan="3" style="border-top: 1px"><textarea rows="1"   
						cols="1" style="height: 50px; width: 80%" id="MODIFY_REMARK"
						name="MODIFY_REMARK">${mode.MODIFY_REMARK }</textarea></td>
			</tr>
	</table>
</div>