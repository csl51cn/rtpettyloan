<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ebills" uri="/WEB-INF/tld/dicitem.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
							<c:if test="${mode.BIZ_TYPE_CODE=='01' }">占用额度的购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='02' }">个人贸易购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='03' }">提供凭证的经常项目其他购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='04' }">资本项目购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='05' }">通过支付机构的购汇</c:if>
						 	<c:if test="${sfbl=='Y' }">补录</c:if>
						 	<c:if test="${sfbl=='N' }">录入</c:if>通知书

</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<style type="text/css" media="print">.Noprint{DISPLAY:none}</style>
<script type="text/javascript">
function printit(){
	$.messager.confirm("系统提示", "确认要打印吗?", function (r) {
		if (r) {
			wb.execwb(7,1);
		}
	});
}

</script>
</head>
<body>
<OBJECT ID="wb" CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb" height="0" width="0" ></OBJECT>

	<div region="north" style="overflow: hidden;" border="false"
		data-options=" collapsed:false"></div>
	<div region="center" border="false">
		<div class="editBlock">
			<table width="99%">				
				<tbody>
					<tr>						
						<td colspan="4" style="height: 50px; text-align: center; font-size: 16px; font-weight: bold;">
							<c:if test="${mode.BIZ_TYPE_CODE=='01' }">占用额度的购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='02' }">个人贸易购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='03' }">提供凭证的经常项目其他购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='04' }">资本项目购汇</c:if>
						 	<c:if test="${mode.BIZ_TYPE_CODE=='05' }">通过支付机构的购汇</c:if>
						 	<c:if test="${sfbl=='Y' }">补录</c:if>
						 	<c:if test="${sfbl=='N' }">录入</c:if>通知书	
						</td>					
					</tr>
					<c:if test="${sfzy eq 'Y' }">
					<tr>						
						<td colspan="4" style="height: 30px;">
							<span class="warning">*</span>&nbsp;&nbsp;本年额度内已购汇金额（折美元&nbsp;包括本次）：<font color="red">${mode.AMT_USD}</font>
						</td>						
					</tr>
					</c:if>
					<tr>
						<th width="25%">业务参号</th>
						<td>${mode.REFNO}</td>
						<th width="25%">业务类型</th>
						<td width="25%">
						 <c:if test="${mode.BIZ_TYPE_CODE=='01' }">占用额度的购汇</c:if>
						 <c:if test="${mode.BIZ_TYPE_CODE=='02' }">个人贸易购汇</c:if>
						 <c:if test="${mode.BIZ_TYPE_CODE=='03' }">提供凭证的经常项目其他购汇</c:if>
						 <c:if test="${mode.BIZ_TYPE_CODE=='04' }">资本项目购汇</c:if>
						 <c:if test="${mode.BIZ_TYPE_CODE=='05' }">通过支付机构的购汇</c:if> 
						</td>
					</tr>
					<tr>
						<th width="25%">证件类型</th>
						<td>${mode.IDTYPE_CODE}</td>
						<th>证件号码</th>
						<td>${mode.IDCODE}</td>
					</tr>
					<tr>
						<th>国家/地区</th>
						<td>${mode.CTYCODE}</td>
						<th>补充证件号码</th>
						<td>${mode.ADD_IDCODE}</td>
					</tr>
					<tr>
						
						<th>姓名</th>
						<td>${mode.PERSON_NAME}</td>
						<th>购汇资金属性</th>
						<td>${mode.TX_CODE}</td>
					</tr>
					<tr>
						<th>币种</th>
						<td>${mode.TXCCY}</td>
						<th>购汇金额</th>
						<td>${mode.TXAMT}</td>
					</tr>
					<tr>
						<th>购汇金额(折美元)</th>
						<td>${mode.TXAMTUSD}</td>
					</tr>
					<c:if test="${mode.BIZ_TYPE_CODE!='05' }">
					
					<tr>
						<th>购汇提钞金额</th>
						<td>${mode.PURFX_CASH_AMT}</td>
						<th>购汇提钞金额(折美元)</th>
						<td>${mode.PURFX_CASH_AMTUSD}</td>
					</tr>
					<tr>
						<th>汇出资金（包括外汇票据）金额</th>
						<td>${mode.FCY_REMIT_AMT}</td>
						<th>存入个人外汇账户金额</th>
						<td>${mode.FCY_ACCT_AMT}</td>
					</tr>
					<tr>
						<th>旅行支票金额</th>
						<td>${mode.TCHK_AMT}</td>
						<td colspan="2"></td>
					</tr>
					<c:if test="${mode.BIZ_TYPE_CODE=='02' }">
					<tr>
						<th>个人外汇账户账号</th>
						<td colspan="3">${mode.LCY_ACCT_NO}</td>
					</tr>
					<tr>
						<th>购汇人民币账户</th>
						<td colspan="3">${mode.LCY_ACCTNO_CNY}</td>
					</tr>
					<tr>
						<th>个体商户组织机构代码</th>
						<td>${mode.INDIV_ORG_CODE}</td>
						<th>个体商户名称</th>
						<td>${mode.INDIV_ORG_NAME}</td>
					</tr>
					<tr>
						<th>代理企业组织机构代码</th>
						<td>${mode.AGENT_CORP_CODE}</td>
						<th>代理企业名称</th>
						<td>${mode.AGENT_CORP_NAME}</td>
					</tr>
					</c:if>
					<c:if test="${mode.BIZ_TYPE_CODE=='03'||mode.BIZ_TYPE_CODE=='01' }">
					<tr>
						<th>个人外汇账户账号</th>
						<td colspan="3">${mode.LCY_ACCT_NO}</td>
					</tr>
					<tr>
						<th>购汇人民币账户</th>
						<td colspan="3">${mode.LCY_ACCTNO_CNY}</td>
					</tr>
					
					</c:if>
					<c:if test="${mode.BIZ_TYPE_CODE=='04' }">
					<tr>
						<th>个人外汇账户账号</th>
						<td colspan="3">${mode.LCY_ACCT_NO}</td>
					</tr>
					<tr>
						<th>购汇人民币账户</th>
						<td colspan="3">${mode.LCY_ACCTNO_CNY}</td>
					</tr>
					<tr>
						<th>外汇局批件号/备案表号/业务编号</th>
						<td colspan="3">${mode.CAPITALNO}</td>
					</tr>
					</c:if>
					</c:if>
					<c:if test="${mode.BIZ_TYPE_CODE=='05' }">
					<tr>
						<th>购汇人民币账户</th>
						<td colspan="3">${mode.LCY_ACCTNO_CNY}</td>
					</tr>
					<tr>
						<th>支付机构组织机构代码</th>
						<td colspan="3">${mode.PAY_ORG_CODE}</td>
					</tr>
					</c:if>
					<tr>
						<th>金融机构名称</th>
						<td>${orgName}</td>
						<th>操作员代码</th>
						<td>${commonOrgUser.COMMON_USER_CODE}</td>
					</tr>
					
					<c:if test="${sfbl=='N' }">
					<tr>
						<th>业务办理日期</th>
						<td colspan="3">${bizTxTime}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3" style="border-top: 1px;">${mode.REMARK}</td>
					</tr>
					</c:if>
					<c:if test="${sfbl=='Y' }">
					<tr>
						<th>业务办理日期</th>
						<td>${mode.BIZ_TX_TIME}</td>
						<th>补录时间</th>
						<td>${bizTxTime}</td>
					</tr>
					<tr>
						<th>补录原因</th>
						<td>
						<c:if test="${mode.REIN_REASON_CODE=='01'}">应急预案启动后补录</c:if>
						<c:if test="${mode.REIN_REASON_CODE=='02'}">脱机操作</c:if>
						<c:if test="${mode.REIN_REASON_CODE=='03'}">差错业务撤销后补录</c:if>
						<c:if test="${mode.REIN_REASON_CODE=='04'}">其它</c:if>
						</td>
						<th>补录说明</th>
						<td>${mode.REIN_REMARK}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3" style="border-top: 1px;">${mode.REMARK}</td>
					</tr>
					</c:if>
				</tbody>
			</table>
			<div class="editBlock" align="center">
				<center class=Noprint>
 				<input type="button" value="打  印" onclick="printit()" />
 				</center>
 			</div>
		</div>
	</div>

</body>
</html>