<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人购汇信息明细</title>
</head>
<body>
	<div class="editBlock">
		<table width="99%">
			<tr>
				<td colspan="4" class="subtitle">购汇信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%">姓名</th>
					<td width="35%"><input type="text" id="PERSON_NAME" name="PERSON_NAME"
						validType="name" value="${mode.PERSON_NAME}" class="inputText"/></td>
					<th width="15%">购汇资金属性</th>
					<td width="34%">					
					<input type="text" id="SALEFX_PURFX_TYPE_CODE" name="SALEFX_PURFX_TYPE_CODE"
						validType="name" value="${mode.SALEFX_PURFX_TYPE_CODE}" class="inputText"/></td>
				</tr>
				<tr>
					<th width="15%">币种</th>
					<td><input type="text" id="TXCCY" name="TXCCY"
						validType="name" value="${mode.TXCCY}" class="inputText" /></td>
				</tr>
				<tr>
					<th width="15%">购汇金额</th>
					<td width="35%"><input type="text"  name="SALEFX_PURFX_AMT"
						value="${mode.SALEFX_PURFX_AMT}" class="inputText"/></td>
					<th width="15%">购汇金额(折美元)</th>
					<td width="35%"><input type="text" name="SALEFX_PURFX_AMT_USD"
						id="SALEFX_PURFX_AMT_USD" value="${mode.SALEFX_PURFX_AMT_USD}"
						class="inputText" /></td>
				</tr>
				<tr>
					<th width="15%">购汇提钞金额</th>
					<td><input type="text" id="PURFX_CASH_AMT" maxlength="18"
						name="PURFX_CASH_AMT" value="${mode.PURFX_CASH_AMT}"
						class="inputText  easyui-validatebox" /></td>
					<th width="15%">购汇提钞金额(折美元)</th>
					<td><input type="text" id="PURFX_CASH_AMT_USD" maxlength="18"
						name="PURFX_CASH_AMT_USD" value="${mode.PURFX_CASH_AMT_USD}"
						class="inputText" data-options="required:false" /></td>
				</tr>
				<tr>
					<th width="15%">汇出资金（包括外汇票据）金额</th>
					<td><input type="text" id="FCY_REMIT_AMT" name="FCY_REMIT_AMT"
						value="${mode.FCY_REMIT_AMT}" class="inputText"
						data-options="required:false" /></td>
					<th width="15%">存入个人外汇账户金额</th>
					<td><input type="text" id="FCY_ACCT_AMT" name="FCY_ACCT_AMT"
						value="${mode.FCY_ACCT_AMT}" class="inputText"
						data-options="required:false" /></td>
				</tr>
				<tr>
					<th width="15%">旅行支票金额</th>
					<td><input type="text" id="TCHK_AMT" name="TCHK_AMT"
						value="${mode.TCHK_AMT}" class="inputText"
						data-options="required:false" /></td>
					<th width="15%">个人外汇账户账号</th>
					<td width="35%"><input type="text" id="LCY_ACCT_NO" name="LCY_ACCT_NO"
						maxlength="32" value="${mode.LCY_ACCT_NO}" class="inputText"
						data-options="required:false" /></td>
				</tr>
				<tr>
					<th width="15%">购汇人民币账户</th>
					<td colspan="3"><input type="text" id="SALEFX_PURFX_ACCT_CNY"
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