<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="e" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人结售汇明细信息</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<body>
<form id="fo" method="post">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="listBtn" href="spfeQuery.do?method=jshMsgList" class="easyui-linkbutton" plain="true" iconCls="icon-list">列表</a>
    </div>
</div>
<div region="center" border="false">
<div class="editBlock">
		<table>
			<tr>
				<td colspan="4" class="subtitle">基本信息</td>
			</tr>
			<tbody>
				<tr>
					<th width="20%">业务类型</th>
					<td width="20%">${jshMsg.TRADETYPENAME}</td>
					<th width="20%">业务参号</th>
					<td>${jshMsg.BANK_SELF_NUM}</td>
				</tr>
				<tr>
					<th width="15%" >业务类型代码</th>
					<td width="30%">${jshMsg.BIZ_TYPE_CODE}</td>
					<th>证件类型代码</th>
					<td>${jshMsg.IDTYPE_CODE}</td>
				</tr>
				<tr>
					<th width="15%" >证件号码</th>
					<td width="30%">${jshMsg.IDCODE}</td>
					<th>补充证件号码</th>
					<td>${jshMsg.ADD_IDCODE}</td>
				</tr>
				<tr>
					<th>国家/地区代码</th>
					<td>${jshMsg.CTYCODE}</td>
					<th>姓名</th>
					<td>${jshMsg.PERSON_NAME}</td>
				</tr>
				<tr>
					<th>结购汇资金属性</th>
					<td>${jshMsg.SALEFX_PURFX_TYPE_CODE}</td>
					<th>支付机构名称</th>
					<td>${jshMsg.PAY_ORG_NAME}</td>
				</tr>
				<tr>
					<th>支付机构组织机构代码</th>
					<td>${jshMsg.PAY_ORG_CODE}</td>
					<th>代理企业名称</th>
					<td>${jshMsg.AGENT_CORP_NAME}</td>
				</tr>
				<tr>
					<th width="15%">代理企业组织机构代码</th>
					<td width="30%">${jshMsg.AGENT_CORP_CODE}</td>
					<th>个体工商户组织机构代码</th>
					<td>${jshMsg.INDIV_ORG_CODE}</td>
				</tr>
				<tr>
					<th width="15%" >个体工商户名称</th>
					<td width="30%">${jshMsg.INDIV_ORG_NAME}</td>
					<th>外汇局批件号/备案表号/业务编号</th>
					<td>${jshMsg.CAPITALNO}</td>
				</tr>
				<tr>
					<th width="15%" >币种</th>
					<td width="30%">${jshMsg.TXCCY}</td>
					<th>结购汇金额</th>
					<td>${jshMsg.SALEFX_PURFX_AMT}</td>
				</tr>
				<tr>
					<th width="15%">结购汇金额折美元</th>
					<td>${jshMsg.SALEFX_PURFX_ACCT_CNY}</td>
					<th>结购汇人民币账户</th>
					<td>
						${jshMsg.SALEFX_PURFX_AMT_USD}
					</td>
				</tr>								
				<tr>					
					<th>购汇提钞金额</th>
					<td>${jshMsg.PURFX_CASH_AMT}</td>
					<th>个人外汇账户账号</th>
					<td>${jshMsg.LCY_ACCT_NO}</td>
				</tr>
				
				<tr>
					<c:if test="${jshMsg.TRADETYPE == 'JH'}">
					<th width="15%">结汇资金形态</th>
					<td>
						${jshMsg.SALEFX_SETTLE_CODE}
					</td>
					</c:if>
					<c:if test="${jshMsg.TRADETYPE == 'GH'}">
					<th width="15%">购汇提钞金额折美元</th>
					<td>${jshMsg.PURFX_CASH_AMT_USD}</td>
					</c:if>
				</tr>
				<c:if test="${jshMsg.TRADETYPE == 'GH'}">			
				<tr>
					<th width="15%">汇出资金（包括外汇票据）金额</th>
					<td>${jshMsg.FCY_REMIT_AMT}</td>
					<th>汇出资金（包括外汇票据）金额折美元</th>
					<td>${jshMsg.FCY_REMIT_AMT_USD}</td>
				</tr>
				<tr>
					<th width="15%">存入个人外汇账户金额</th>
					<td>${jshMsg.FCY_ACCT_AMT}</td>
					<th>存入个人外汇账户金额折美元</th>
					<td>${jshMsg.FCY_ACCT_AMT_USD}</td>
				</tr>
				<tr>
					<th width="15%">存入个人外汇账户金额</th>
					<td>${jshMsg.FCY_ACCT_AMT}</td>
					<th>存入个人外汇账户金额折美元</th>
					<td>${jshMsg.FCY_ACCT_AMT_USD}</td>
				</tr>
				<tr>
					<th width="15%">旅行支票金额</th>
					<td>${jshMsg.TCHK_AMT}</td>
					<th>旅行支票金额折美元</th>
					<td>${jshMsg.TCHK_AMT_USD}</td>
				</tr>
				</c:if>
				<tr>
					<th width="15%">业务办理渠道代码</th>
					<td>${jshMsg.BIZ_TX_CHNL_CODE}</td>
					<th>业务办理时间</th>
					<td>${jshMsg.BIZ_TX_TIME}</td>
				</tr>
				<tr>
					<th width="15%">金融机构标识码</th>
					<td>
						${jshMsg.BRANCH_CODE}
					</td>
					<th>金融机构名称</th>
					<td>
						${jshMsg.BRANCH_NAME}
					</td>
				</tr>
				<tr>
					<th width="15%">经办人代码</th>
					<td>
						${jshMsg.OPERCODE} 
					</td>
					<th>操作类型</th>
					<td>
						${jshMsg.ACTIONTYPE}
					</td>
				</tr>
				<tr>
					<th width="15%">备注</th>
					<td colspan="3">
						${jshMsg.OPERCODE}
					</td>
				</tr>
			</tbody>
		</table>
</div>
</div>
</form>
</body>
</html>
