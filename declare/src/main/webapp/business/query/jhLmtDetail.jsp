<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结汇信息明细</title>
</head>
<body>
	<div class="editBlock">
		<table width="99%">
			<tr>
				<td colspan="4" class="subtitle">结汇信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%">姓名</th>
					<td width="35%"><input type="text" id="PERSON_NAME" name="PERSON_NAME"
						validType="name" value="${mode.PERSON_NAME}" class="easyui-validatebox inputText"
						data-options="required:true" validType="length[1,128]"
						maxlength="128" /></td>
					<th width="15%">结汇资金属性</th>
					<td width="34%"><input type="text" id="SALEFX_PURFX_TYPE_CODE"
						name="SALEFX_PURFX_TYPE_CODE"
						value="${mode.SALEFX_PURFX_TYPE_CODE}" class="inputText" /></td>
				</tr>
				<tr>
					<th width="15%">币种</th>
					<td width="35%"><input type="text" id="TXCCY" name="TXCCY"
						value="${mode.TXCCY}" class="inputText" /></td>
					<th width="15%">结汇资金形态</th>
					<td width="34%">					
					<input type="text" id="SALEFX_SETTLE_CODE"
						name="SALEFX_SETTLE_CODE"
						value="${mode.SALEFX_SETTLE_CODE}" class="inputText" />
					</td>
				</tr>
				<tr>
					<th width="15%">结汇金额</th>
					<td width="34%">					
					<input type="text" id="SALEFX_PURFX_AMT"
						name="SALEFX_PURFX_AMT"
						value="${mode.SALEFX_PURFX_AMT}" class="inputText" />
						</td>
					<th width="15%">结汇金额(折美元)</th>
					<td width="35%"><input type="text" id="SALEFX_PURFX_AMT_USD"
						name="SALEFX_PURFX_AMT_USD" value="${mode.SALEFX_PURFX_AMT_USD}"
						class="inputText" /></td>
				</tr>
				<tr>
					<th width="15%">个人外汇账户账号</th>
					<td width="35%"><input type="text" id="LCY_ACCT_NO"
						name="LCY_ACCT_NO" value="${mode.LCY_ACCT_NO}" class="inputText" /></td>
					<th width="15%">结汇人民币账户</th>
					<td width="34%"><input type="text" id="SALEFX_PURFX_ACCT_CNY"
						name="SALEFX_PURFX_ACCT_CNY" value="${mode.SALEFX_PURFX_ACCT_CNY}"
						class="inputText"/>
					</td>
				</tr>
				<tr>
					<th width="15%">备注</th>
					<td colspan="3" style="border-top: 1px"><textarea rows="1"
							cols="1" style="height: 50px; width: 98%" id="REMARK"
							maxlength="256" name="REMARK" value="${mode.REMARK}"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>