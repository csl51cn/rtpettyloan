<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>占用额度的结汇信息录入</title>
</head>
<body>
	<div class="editBlock">
		<table width="99%">
			<tr>
				<td colspan="4" class="subtitle">结汇信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%"><span class="warning">*</span>姓名</th>
					<td width="25%"><input type="text" id="PERSON_NAME" name="PERSON_NAME"
						validType="name" value="${mode.PERSON_NAME}" class="easyui-validatebox inputText"
						data-options="required:true" validType="length[1,128]"
						maxlength="128" /></td>
					<th width="15%"><span class="warning">*</span>结汇资金属性</th>
					<td >
						<ebills:select id="TX_CODE" name="TX_CODE"
									value="${mode.TX_CODE}" style="width:250px"
									diccode="JHZJSX_CODE"  expAttribute='data-options="required:true,editable:false" '
									clazz="easyui-combobox" ></ebills:select>
					</td>
				</tr>
				<tr>
					<th width="15%"><span class="warning">*</span>币种</th>
					<td width="25%"><select name="TXCCY" id="TXCCY"
						data-options="required:true,editable:false" style="width: 250px"
						value="${mode.TXCCY}" class="easyui-combobox" validType="txccy" >
							<option></option>
							<c:forEach items="${currencys}" var="currency">
								<option value="${currency.curSign}"
									${mode.TXCCY==currency.curSign?'selected':'' } >${currency.curSign}-${currency.cnName}</option>
							</c:forEach>
					</select></td>
					<th width="15%"><span class="warning">*</span>结汇资金形态</th>
					<td ><ebills:select id="SALEFX_SETTLE_CODE"
							expAttribute='data-options="required:true,editable:false"'
							name="SALEFX_SETTLE_CODE" value="${mode.SALEFX_SETTLE_CODE}"
							style="width:250px" diccode="SALEFX_SETTLE_CODE"
							clazz="easyui-combobox"></ebills:select>
					</td>
				</tr>
				<tr>
					<th width="15%"><span class="warning">*</span>结汇金额</th>
					<td width="25%">
					<input type="text"  name="TXAMT" id="TXAMT"  onblur="querySafeExRate(this)"
						 value="${mode.TXAMT}" class="easyui-validatebox inputText" 
						data-options="required:true" validType="plusNum" 
						maxlength="18" />
						</td>
					<th width="15%">结汇金额(折美元)</th>
					<td ><input type="text"  disabled id="TXAMTUSD" name="TXAMTUSD"
						 value="${mode.TXAMTUSD}"  class="inputText"
						 maxlength="18" /></td>
				</tr>
				<tr>
					<th width="15%">个人外汇账户账号</th>
					<td width="25%"><input type="text" id="LCY_ACCT_NO" name="LCY_ACCT_NO"
						maxlength="32" value="${mode.LCY_ACCT_NO}" class="inputText"
						data-options="required:false" /></td>
					<th width="15%">结汇人民币账户</th>
					<td><input type="text" id="LCY_ACCTNO_CNY"
						name="LCY_ACCTNO_CNY" value="${mode.LCY_ACCTNO_CNY}"
						class="inputText" maxlength="32" data-options="required:false" />
					</td>
				</tr>
				<c:if test="${(tradeNo eq '000080') || (tradeNo eq '000081') || (tradeNo eq '000082') || (tradeNo eq '000083')}">
					<tr>
						<th width="15%"><span class="warning">*</span>业务办理时间</th>
						<td colspan="3">
							<input  type="text" class="easyui-datebox" name="BIZ_TX_TIME"  validType="hisDate"  data-options="required:true"  style="width:250px"  id="BIZ_TX_TIME"  value="${mode.BIZ_TX_TIME }" />
						</td>
					</tr>
				</c:if>
				<tr style="display:none" id="refnotr">
					<th width="15%">业务参号</th>
					<td colspan="3">
						<input  type="text" class="inputText"  id="refNo" validType="hisDate" disabled  data-options="required:true"  style="width:250px"  value="${mode.REFNO }" />
					</td>
				</tr>
				<tr>
					<th width="15%">备注</th>
					<td colspan="3" style="border-top: 1px"><textarea rows="1"  ischg='N' onmousedown="this.attributes['ischg'].nodeValue='Y'"
							cols="1" style="height: 50px; width: 98%" id="REMARK"
							maxlength="256" name="REMARK" value="${mode.REMARK}">${mode.REMARK}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>