<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="e" uri="/WEB-INF/tld/dicitem.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增机构</title>
<jsp:include page="../../common/include.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	loadParentOrg();
	
	//机构级别为总行时，上级机构为空不可选
	var orgLevel = "${org.orgLevel}";
	if (orgLevel == "0") {
		$("#parentOrgId").combotree({required:false});
		$("#parentOrgId").combotree("disable");
	}else{
		$("#parentOrgId").combotree({required:true});
	}
	//$("#orgLevel").combobox("disable");//机构层级不可选，根据选择的上级机构来判断
});

function loadParentOrg() {
	$('#parentOrgId').combotree({
		url : "orgController.do?method=loadOrgTree",
		lines : "true",
		onSelect : function(node) {
			//根据选择的机构计算机构层级:选择机构层级+1
			//var curOrgLevel = new Number(node.attributes.orgLevel) + 1;
			//校验是否末级机构
			//$("#orgLevel").combobox("setValue", curOrgLevel);
		}
	});
}

function save() {
	if ($('#fo').form('validate') == true) {
		$.post('orgController.do?method=save', $("#fo").serializeArray(), function(data) {
			$("#orgId").val(data.orgId);
			$.messager.alert('系统提示', "保存成功", 'info');
		});
	}
}

var IS_DECLARE_ORG = [{"code":"Y","name":"是"},{"code":"N","name":"否"}];
</script>
<body>
<form id="fo" method="post">
<div region="north" style="overflow:hidden;" border="false">
    <div style="padding:1px;background:#EFEFEF;">
        <a id="addBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-add"
           onclick="createHandle('orgController.do?method=edit')">新增</a>
        <a id="saveBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="save();">保存</a>
        <a id="listBtn" href="###" class="easyui-linkbutton" plain="true" iconCls="icon-list"
           onclick="listHandle('orgController.do?method=list')">列表</a>
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
					<th width="15%" ><span class="warning">*</span>机构编码</th>
					<td width="30%">
						<input type="hidden" id="orgId" name="orgId" value="${org.orgId}" />
						<input type="text" id="orgCode" name="orgCode" value="${org.orgCode}" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
					<th><span class="warning">*</span>中文名称</th>
					<td>
						<input type="text" id="orgName" name="orgName" value="${org.orgName}" class="easyui-validatebox inputText" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th width="15%" >机构简称</th>
					<td width="30%">
						<input type="text" id="orgShortName" name="orgShortName" value="${org.orgShortName}" class="inputText" />
					</td>
					<th>英文名称</th>
					<td>
						<input type="text" id="enName" name="enName" class="inputText" value="${org.enName}" />
					</td>
				</tr>
				<tr>
					<th width="15%" ><span class="warning">*</span>上级机构</th>
					<td width="30%">
						<input id="parentOrgId" name="parentOrgId" class="easyui-combotree" data-options="required:true" style="width:250px;"  value="${org.parentOrgId}" />
					</td>
					<th ><span class="warning">*</span>机构层级</th>
					<td >
						<e:select id="orgLevel" name="orgLevel"
									value="${org.orgLevel}" style="width:250px"
									diccode="ORG_LEVEL"
									clazz="  easyui-combobox" expAttribute="data-options=\"required:true\""></e:select>
					</td>
				</tr>
				<tr>
					<th >证照类别</th>
					<td >
						<e:select id="certType" name="certType"
									value="${org.certType}" style="width:250px"
									diccode="CERT_TYPE"
									clazz="  easyui-combobox" expAttribute="data-options=\"required:true\""></e:select>
					</td>
					<th >证照号码</th>
					<td >
						<input type="text" id="certCode" name="certCode" class="easyui-validatebox inputText" value="${org.certCode}" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th >SWIFTCODE</th>
					<td >
						<input type="text" id="swiftCode" name="swiftCode" class="inputText"  value="${org.swiftCode}" />
					</td>
					<th >金融机构标识码</th>
					<td >
						<input type="text" id="bankCode" name="bankCode" class="inputText" value="${org.bankCode}" />
					</td>
				</tr>
				<tr>
					<th width="15%" >中文地址</th>
					<td colspan="3">
						<input type="text" id="address" name="address" class="inputText"  style="width:750px" value="${org.address}"/>
					</td>
				</tr>
				<tr>
					<th width="15%" >英文地址</th>
					<td colspan="3">
						<input type="text" id="enAddr" name="enAddr" class="inputText"  style="width:750px" value="${org.enAddr}" />
					</td>
				</tr>
				<tr>
					<th width="15%" >备注</th>
					<td colspan="3">
						<textarea cols="100" rows="2" class="textarea" id="remark" name="remark" style="width:750px" >${org.remark}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
</div>
</div>
</form>
</body>
</html>
