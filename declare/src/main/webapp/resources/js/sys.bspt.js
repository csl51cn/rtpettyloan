/**
 * 校验选中的操作类型
 * @param sbFlag 申报标识Y:已报送,N:未报送
 * @param srcType 原始操作类型
 * @param selectedType 选中的操作类型
 */
function banActionType(sbFlag, srcType, selectedType) {
	if(sbFlag=='N' && srcType != selectedType) {
		//如果sfysb为N，表示该数据未报送，不允许更改初始选中的操作类型
		$.messager.alert('系统提示', '未报送的数据不能更改操作类型', 'error');
		return true;
	}
	if (sbFlag == 'Y' && selectedType == 'A') {
		$.messager.alert('系统提示', '已报送的数据操作类型不能选择新建', 'error');
		return true;
	}
	return false;
}

/**
 * 设置交易状态
 * 
 * @param formid
 * @param inputName
 * @param value
 * @param toolbar
 */
function setTransState(formid, inputName, value, toolbar) {
	$('#'+formid+' input[name='+inputName+']:checkbox').each(function() {
		if ($(this).val() == value) {
			$(this).attr('checked', true);
		} else {
			$(this).attr('checked', false);
		}
	});	
	//待复核和已复核状态时隐藏 增加行，删除行按钮
	if (value == "2") {
		$("#" + toolbar).hide();
		$("#save").linkbutton("disable");
		$("#submit").linkbutton("disable");
		$("#delete").linkbutton("disable");
	} else if (value == "4"){
		$("#" + toolbar).hide();
		$("#save").linkbutton("disable");
		$("#submit").linkbutton("disable");
		$("#delete").linkbutton("disable");
		$("#check").linkbutton("disable");
	} else  if((value == "1" || value == "5") && getActiontype("actiontype") != "A"){
		$("#" + toolbar).hide();	
	} else if (value == "5") {
		$("#check").linkbutton("disable");
	} else {
		$("#" + toolbar).show();
		$("#check").linkbutton("disable");
	}
}

/**
 * 
 * 
 * @param {} formid
 * @param {} transtate
 * @param {} value
 * @param {} toolbar
 * @param {} taskId
 */
function setDetailTransState(formid, inputName, transtate, toolbar,taskId){
	$('#'+formid+' input[name='+inputName+']:checkbox').each(function() {
		if ($(this).val() == transtate) {
			$(this).attr('checked', true);
		} else {
			$(this).attr('checked', false);
		}
	});
	//待复核和已复核状态时隐藏 增加行，删除行按钮
	if (transtate == "2") {
		$("#" + toolbar).hide();
		$("#save").linkbutton("disable");
		$("#submit").linkbutton("disable");
		$("#delete").linkbutton("disable");
	} else if (transtate == "4"){
		if(taskId != ""){
			$("#save").linkbutton("disable");
			$("#submit").linkbutton("disable");
		}
		$("#" + toolbar).hide();
		$("#delete").linkbutton("disable");
		$("#check").linkbutton("disable");
		
	} else  if((transtate == "1" || transtate == "5") && $("#actiontype").combobox('getValue') != "A"){
		$("#" + toolbar).hide();	
	}else{
		$("#" + toolbar).show();
		$("#check").linkbutton("disable");
	}
	//停用操作类型
	if(transtate != "4" || (transtate == "4" && $("#taskId").val() != "")){
		$("#actiontype").combobox("disable",true);	
	}
}

/**
 * 获取操作类型
 */
function getActiontype(actiontypeName){
	return $("input[name='"+actiontypeName+"']:checked").val();
}

/**
 * 设置操作类型
 * 
 * @param formid
 * @param inputName
 * @param value
 * @param toolbar
 */
function setActiontype(formid, inputName, value, toolbar) {
	$('#'+formid+' input[name='+inputName+']:checkbox').each(function() {
		if ($(this).val() == value) {
			$(this).attr('checked', true);
		} else {
			$(this).attr('checked', false);
		}
	});	
	//已申报修改和删除操作时隐藏 增加行，删除行按钮
	if (value == "C" || value == "D") {
		$("#" + toolbar).hide();
	} else {
		$("#" + toolbar).show();
	}
}

/**
 * 校验修改或删除原因
 * @param dg
 * @returns {Boolean}
 */
function validActionDesc(dg) {
	var actionType = $('#' + dg).datagrid('getEditor', {
		index : editIndex,
		field : 'actiontype'
	}).target.combobox('getValue');
	var actiondesc = getTextValue(dg, 'actiondesc', editIndex);
	if (actionType != 'A' && (actiondesc == undefined || actiondesc == '')) {
		$.messager.alert("系统提示", "操作类型不为新建时，修改或删除原因必须填写", "error");
		return false;
	}
	return true;
}

var bsptAZ = {

	/**
	 * 设置按钮不可用
	 * 
	 * @param {}
	 *            transtate 状态
	 * @param {}
	 *            actiontype 操作类型
	 */
	setDisable : function(transtate, actiontype) {
		if (transtate == 2) {
			bsptAZ.disable();
		}
		if (transtate == 4) {
			if (actiontype == undefined || actiontype == "C") {
				$("#save").linkbutton("disable");
				$("#delete").linkbutton("disable");
			} else {
				bsptAZ.disable();
			}
		}
	},
	
	disable : function() {
		$("#save").linkbutton("disable");
		$("#submit").linkbutton("disable");
		$("#delete").linkbutton("disable");
	}

};

/**
 * 添加右击菜单内容
 * @param e
 * @param rowIndex
 * @param rowData
 */
function onRowContextMenu(e, rowIndex, rowData) {
	e.preventDefault();
	var allRows = $("#dg").datagrid('getRows');
	var nguid = allRows[rowIndex].nguid; 
	if (nguid == null || nguid == "" || getTranstate() != "4") {
		return;
	}
	$("#dg").datagrid('selectRow', rowIndex);  
	$('#declareModifyAndDel').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
}
