<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>不占用额度的购汇信息录入</title>
</head>
<body>
	<div class="editBlock">
		<table>
			<tr>
				<td colspan="4" class="subtitle">购汇信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="15%"><span class="warning">*</span>姓名</th>
					<td  width="25%"><input type="text" id="PERSON_NAME" name="PERSON_NAME"
						validType="name" value="${mode.PERSON_NAME}" class="inputText easyui-validatebox"
						data-options="required:true" validType="length[1,128]"
						maxlength="128" /></td>
					<th width="15%"><span class="warning">*</span>购汇资金属性</th>
					<td>
					<ebills:select id="TX_CODE" name="TX_CODE"
									value="${mode.TX_CODE}" style="width:250px"
									diccode="GHZJSX_CODE"  expAttribute='data-options="required:true,editable:false" '
									clazz="easyui-combobox" ></ebills:select>
									</td>
				</tr>
				<tr>
					<th width="15%"><span class="warning">*</span>币种</th>
					<td colspan="3"><select name="TXCCY" id="TXCCY"
						data-options="required:true,editable:false" style="width: 250px"
						value="${mode.TXCCY}" class="easyui-combobox" validType="txccy">
							<option></option>
							<c:forEach items="${currencys}" var="currency">
								<option value="${currency.curSign}"
									${mode.TXCCY==currency.curSign?'selected':'' } >${currency.curSign}-${currency.cnName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th width="15%"><span class="warning">*</span>购汇金额</th>
					<td width="25%"><input type="text" id="TXAMT" name="TXAMT"  onblur="querySafeExRate(this)"
						validType="plusNum" value="${mode.TXAMT}" class="inputText easyui-validatebox"
						data-options="required:true" maxlength="18" /></td>
					<th width="15%">购汇金额(折美元)</th>
					<td><input type="text" id="TXAMTUSD" name="TXAMTUSD"
						  value="${mode.TXAMTUSD}" class="inputText" disabled
						  maxlength="18" /></td>
				</tr>
				<tr id="GH0503">
					<th width="15%"><span class="warning">*</span>购汇提钞金额</th>
					<td width="25%"><input type="text" id="PURFX_CASH_AMT" maxlength="18"  onblur="querySafeExRate(this)"
						name="PURFX_CASH_AMT" value="${mode.PURFX_CASH_AMT}"  validType="amt"
						class="inputText"   /></td>
					<th width="15%">购汇提钞金额(折美元)</th>
					<td><input type="text" id="PURFX_CASH_AMTUSD" maxlength="18" disabled
						name="PURFX_CASH_AMTUSD" value="${mode.PURFX_CASH_AMTUSD}"
						class="inputText"  /></td>
				</tr>
				<tr id="GH0504">
					<th width="15%"><span class="warning">*</span>汇出资金（包括外汇票据）金额</th>
					<td width="25%"><input type="text" id="FCY_REMIT_AMT" name="FCY_REMIT_AMT"  validType="amt"
						value="${mode.FCY_REMIT_AMT}" class="inputText"  /></td>
					<th width="15%">存入个人外汇账户金额</th>
					<td><input type="text" id="FCY_ACCT_AMT" name="FCY_ACCT_AMT"  validType="amt"
						value="${mode.FCY_ACCT_AMT}" class="inputText"  /></td>
				</tr>
				<tr  id="GH0505">
					<th width="15%"><span class="warning">*</span>旅行支票金额</th>
					<td colspan="3"><input type="text" id="TCHK_AMT" name="TCHK_AMT"  validType="amt"
						value="${mode.TCHK_AMT}" class="inputText" /></td>
				</tr>
				<tr id="GH0502">
					<th width="15%">个人外汇账户账号</th>
					<td colspan="3"><input type="text" id="LCY_ACCT_NO" name="LCY_ACCT_NO"
						maxlength="32" value="${mode.LCY_ACCT_NO}" class="inputText" /></td>
				</tr>
				<tr >
					<th width="15%">购汇人民币账户</th>
					<td colspan="3"><input type="text" id="LCY_ACCTNO_CNY"
						name="LCY_ACCTNO_CNY" value="${mode.LCY_ACCTNO_CNY}"
						class="inputText" maxlength="32"  />
					</td>
				</tr>
				<tr id="GH0201" >
					<th width="15%">个体工商户组织机构代码</th>
					<td width="25%"><input type="text" id="INDIV_ORG_CODE"
						name="INDIV_ORG_CODE" value="${mode.INDIV_ORG_CODE}"
						class="inputText"  /></td>
					<th width="15%">个体工商户名称</th>
					<td><input type="text" id="INDIV_ORG_NAME"
						name="INDIV_ORG_NAME" value="${mode.INDIV_ORG_NAME}"
						class="inputText"  /></td>
				</tr>
				<tr id="GH0202" >
					<th width="15%">代理企业组织机构代码</th>
					<td width="25%"><input type="text" id="AGENT_CORP_CODE"
						name="AGENT_CORP_CODE" value="${mode.AGENT_CORP_CODE}"
						class="inputText" /></td>
					<th width="15%">代理企业名称</th>
					<td><input type="text" id="AGENT_CORP_NAME"
						name="AGENT_CORP_NAME" value="${mode.AGENT_CORP_NAME}"
						class="inputText" /></td>
				</tr>
				<tr id="GH0401" >
					<th width="15%">外汇局批件号/备案表号/业务编号</th>
					<td colspan="3"><input type="text" id="CAPITALNO" name="CAPITALNO"
						value="${mode.CAPITALNO}" class="inputText" />
					</td>
				</tr>
				<tr id="GH0501" >
					<th width="15%">支付机构组织机构代码</th>
					<td colspan="3"><input type="text" id="PAY_ORG_CODE" name="PAY_ORG_CODE"
						value="${mode.PAY_ORG_CODE}" class="inputText"  maxlength="9"/></td>
				</tr>
				<tr>
					<th width="15%"><span class="warning">*</span>业务办理时间</th>
					<td colspan="3">
						<input  type="text" class="easyui-datebox" name="BIZ_TX_TIME"  data-options="disabled:true,required:true"  style="width:250px"  validType="hisDate"   id="BIZ_TX_TIME"  value="${mode.BIZ_TX_TIME}" />
					</td>
				</tr>
				<tr style="display:none" id="refnotr">
					<th width="15%">业务参号</th>
					<td colspan="3">
						<input  type="text" class="inputText"  id="refNo" validType="hisDate"  data-options="required:true" disabled style="width:250px"  value="${mode.REFNO }" />
					</td>
				</tr>
				<tr>
					<th width="15%">备注</th>
					<td colspan="3" style="border-top: 1px"><textarea rows="1"  ischg='N' onmousedown="this.attributes['ischg'].nodeValue='Y'"
							cols="1" style="height: 50px; width: 98%" id="REMARK"
							maxlength="256" name="REMARK" value="${mode.REMARK}" >${mode.REMARK}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>