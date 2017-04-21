/**
 * 公共JS类
 * @author cqchenf@163.com
 */
//url.match(/\?/) ? "&" : "?"
$(function() {
	//initForm();
	initToggle();
});
$.ajaxSetup({
	async : false
});

//以subtitle作为需要点击的class
function initToggle() {
	$(".subtitle,.subtitleClose").click(function() {
		var t = $(this).parent().parent().next("tbody");
		t.toggle();
		if (t.get(0).style.display == "none") {
			$(this).attr("class", "subtitleClose");
		} else {
			$(this).attr("class", "subtitle");
		}
	});
}

function initForm() {
	try {
		$("textarea,:text[class=inputText],:password,:file").focus(function() {
			$(this).addClass("onFocus");
		}).blur(function() {
			$(this).removeClass("onFocus");
		});
		//$(".page select").removeClass("sele");
		$(":text").filter(function() {
			return $(this).attr("class") != "Wdate";
		}).eq(0).focus();
		document.onkeydown = function(e) {
			if (!e) {
				var e = window.event;
			}
			if (e.keyCode == 8) {
				history.back();
			} else {
				document.onkeydown = null;
			}
		};
	} catch (e) {
	}
}

/**
 * 选中行时不选中checkbox
 * @param gridId 数据表格ID
 */
function selectRowEvent(gridId) {
	var panel = $('#' + gridId).datagrid('getPanel');
	var rows = panel.find('tr[datagrid-row-index]');
	rows.unbind('click').bind('click', function(e) {
		return false;
	});
	rows.find('div.datagrid-cell-check input[type=checkbox]').unbind().bind(
			'click',
			function(e) {
				var index = $(this).parent().parent().parent().attr(
						'datagrid-row-index');
				if ($(this).attr('checked')) {
					$('#' + gridId).datagrid('selectRow', index);
				} else {
					$('#' + gridId).datagrid('unselectRow', index);
				}
				e.stopPropagation();
			});
	$('#' + gridId).datagrid('clearSelections');
}

/**
 * 初始化机构树
 * @param orgId 机构selectId
 * @param valId 需要默认赋值的input id
 */
function loadOrgTree(orgId, valId) {
	$('#' + orgId).combotree({
		url : basePath + "/sys/orgController.do?method=loadOrgTree",
		lines : "true",
		onSelect : function(node) {
			if (valId) {
				$("#" + valId).val(node.attributes.bankCode);
			}
			//返回树对象  
			//var tree = $(this).tree;  
			//选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
			//var isLeaf = tree('isLeaf', node.target); 
			//alert(node.attributes.isExistDept);
		}
	});
}

/**
 * 初始化机构树
 * @param bankId 机构selectId
 * @param objcode
 * @param orgCode  
 */
function loadOrgDeptTree(bankId, objcode,orgCode) {
	$('#' + bankId).combotree({
		url : basePath + "/sys/orgController?loadOrgTree",
		lines : "true",
		onSelect : function(node) {			
			$("#" + objcode).val(node.attributes.bankCode);
			$("#" + orgCode).val(node.text);
		}
	});
}


/**
 * 查询操作
 * @param gridId 数据表格ID
 * @param queryFormId 查询条件Form ID
 */
function queryHandle(gridId, queryFormId) {
	var params = $('#' + gridId).datagrid('options').queryParams; //先取得 datagrid 的查询参数
	var fields = $('#' + queryFormId).serializeArray(); //自动序列化表单元素为JSON对象
	$.each(fields, function(i, field) {
		params[field.name] = field.value; //设置查询参数
	});
	$('#' + gridId).datagrid('options').pageNumber = 1;
	$('#' + gridId).datagrid('getPager').pagination({
		pageNumber : 1
	});
	$('#' + gridId).datagrid('reload'); //设置好查询参数 reload 一下就可以了
}

/**
 * 清空查询条件
 * @param gridId 数据表格ID
 * @param queryFormId 查询条件Form ID
 */
function clearHandle(gridId, queryFormId) {
	$('#' + queryFormId).form('clear');
	queryHandle(gridId, queryFormId);
}

/**
 * 新增操作
 * @param url 新增页面URL
 */
function createHandle(url) {
	location.href = url;
}

/**
 * 编辑操作
 * @param url
 */
function editHandle(url) {
	location.href = url;
}

/**
 * 查看操作
 * @param url
 */
function viewHandle(url) {
	location.href = url;
}

/**
 * 保存操作
 * @param formid form控件的ID
 * @param action form的action
 */
function saveHandle(formid, action) {
	$.post(action, $("#" + formid).serializeArray(), function(data) {
		//var obj = $.parseJSON(data);
		alert(data.status)
		if (obj.status) {
			$.messager.alert('系统提示', obj.message, 'info');
		} else {
			$.messager.alert('系统提示', obj.message, 'error');
		}
	});
}

/**
 * 删除操作
 * @param url 删除操作URL
 * @param gridId 数据表格控件ID，列表删除操作时用
 */
function deleteHandle(url, gridId) {
	$.messager.confirm("系统提示", "您确定要删除该条记录吗？", function(r) {
		if (r) {
			$.post(url, function(data) {
				$.messager.alert("系统提示", "删除记录成功", "info");
				$('#' + gridId).datagrid('reload');
			});
		}
	});
}

/**
 * TreeGrid删除操作
 * @param url 删除操作URL
 * @param gridId 数据表格控件ID，列表删除操作时用
 */
function deleteTreeGridHandle(url, gridId) {
	$.messager.confirm("系统提示", "您确定要删除该条记录吗？此操作不可逆！", function(r) {
		if (r) {
			$.post(url, function(data) {
				$.messager.alert("系统提示", "删除操作成功", "info");
				if (gridId != '' && gridId != undefined) {
					$('#' + gridId).treegrid('reload');
					return;
				}
			});
		}
	});
}

/**
 * 批量删除操作
 * @param url
 * @param gridId
 */
function batchDelHandle(url, gridId) {
	$.post(url, function(data) {
		var obj = $.parseJSON(data);
		if (obj.status) {
			$.msgtip.show({
				Event : "click",
				timeOut : 3000,
				msg : obj.message,
				speed : 300,
				type : "success"
			});
			if (gridId != '' && gridId != undefined) {
				$('#' + gridId).datagrid('reload');
				return;
			}
		} else {
			$.msgtip.show({
				Event : "click",
				timeOut : 3000,
				msg : obj.message,
				speed : 300,
				type : "error"
			});
		}
	});
}

/**
 * 返回列表
 * @param url
 */
function listHandle(url) {
	location.href = url;
}

function alertMsg(msg, type) {
	$.msgtip.show({
		Event : "click", //响应的事件
		timeOut : 3000, //提示层显示的时间
		msg : msg, //显示的消息
		speed : 300, //滑动速度
		type : type
	//提示类型（1、success 2、error 3、warning）
	});
}

/**
 * 复核通用操作方法
 * @param txnSerialNo 业务流水号
 * @param tradeNo 流程任务类型编码
 */
function doCheckHandle(txnSerialNo, tradeNo) {
	doHandle(txnSerialNo, tradeNo,"checkTask");
}

/**
 * 授权通用操作方法
 * @param txnSerialNo 业务流水号
 * @param tradeNo 流程任务类型编码
 */
function doAuthHandle(txnSerialNo, tradeNo) {
	doHandle(txnSerialNo, tradeNo,"authTask");
}

/**
 * 通用流程表单页面
 * 
 * @param txnSerialNo
 * @param tradeNo
 * @param todo
 */
function doHandle(txnSerialNo, tradeNo,todo){
	$.dialog({
		title : (todo=='authTask'?'授权':'复核')+'操作:: 业务流水号[' + txnSerialNo + ']',
		content : 'url:flow/tasklistController.do?method='+todo+'&txnSerialNo='
				+ txnSerialNo + '&tradeNo=' + tradeNo,
		ok : function() {
			if(this.content.checkForm()){
				//setTransState(formid, status, this.content.getTranstate());
				viewHandle(basePath + "/flow/tasklistController.do?method=waitTaskList");
			}
			return true;
		},
		cancelVal : '取消',
		cancel : true,
		width : 600,
		height : 300,
		lock : true
	});
}

/**
 * 通用流程取消代办任务方法
 * @param txNo
 * @param serviceBeanId
 */
function doCancelTask(ctxPath,txNo,serviceBeanId){
	$.ajaxSetup({async:false});
	$.messager.confirm("系统提示", "确定要取消？", function(r){
		if (r) {
			$.post(ctxPath+"/flow/tasklistController.do?method=doCancelTask",{"txNo":txNo,"serviceBeanId":serviceBeanId},function(){
				$("span.l-btn-focus",$.messager.alert("系统提示", "取消操作成功", "info")).click(function(e){
					viewHandle(basePath + "/flow/tasklistController.do?method=waitTaskList");
				});
			});
		}
	});
	$.ajaxSetup({async:true});
}
/**
 * 修改JSON串
 * @param json 原始JSON串
 * @param name JSON的属性名
 * @param value JSON属性的VALUE
 */
function insertJson(json, name, value) {
	// 如果 value 被忽略
    if(typeof value === "undefined") {
        // 删除属性
        delete json[name];
    } else {
        // 添加 或 修改
        json[name] = value;
    }
}

/**
 * 加载年月选择控件
 * @param formid
 * @param inputid
 */
function loadYearAndMonthDate(formid, inputid) {
	$('#'+formid+' input[id='+inputid+']').datePicker({
		followOffset:[0, 24],
		altFormat : 'yyyymm',
		showMode : 1
	});
}

/**
 * 获取text的text值
 * @param tb
 * @param colName
 * @param editIndex
 * @returns
 */
function getTextValue(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).val();
}

/**
 * 获取ComboGrid的text值
 * @param tb
 * @param colName
 * @param editIndex
 * @returns
 */
function getComboGridValue(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combogrid('getText');
}

/**
 * get comboxgrid value
 * 
 * @param {} tb datagird id
 * @param {} colName datagrid column name
 * @param {} editIndex datagrid edit index
 * @return {} datagrid edit index combogrid column value
 */
function getComboGridVal(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combogrid('getValue');
}

/**
 * 获取combobox的valueField值
 * 
 * @param {} tb
 * @param {} colName
 * @param {} editIndex
 * @return {}
 */
function getComboBoxValue(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combobox('getText');
}

/**
 * get combobox value
 * 
 * @param {} tb datagird id
 * @param {} colName colName datagrid column name
 * @param {} editIndex editIndex datagrid edit index
 * @return {} datagrid edit index combobox column value
 */
function getComboBoxVal(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combobox('getValue');
}

/**
 * 获取ComboTree的text值
 * @param tb
 * @param colName
 * @param editIndex
 * @returns
 */
function getComboTreeValue(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combotree('getText');
}

/**
 * get combotree value
 * 
 * @param {} tb datagird id
 * @param {} colName colName datagrid column name
 * @param {} editIndex edit index datagrid edit index
 * @return {} datagrid edit index combotree column value
 */
function getComboTreeVal(tb, colName, editIndex) {
	var x = $('#' + tb).datagrid('getEditor', {
		index : editIndex,
		field : colName
	});
	return $(x.target).combotree('getValue');
}

/**
 * 序列化对象
 */
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(function() {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [serializeObj[this.name],
							this.value];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};
})(jQuery);

/** 数字小数位数格式化* */
function formatFloat(number, len) {
	var l = Math.pow(10, len);
	return Math.round(number * l) / l;
}

/**金额格式化*/
function formatAmt(amount, n) {
	n = n > 0 && n <= 20 ? n : 2;
	amount = parseFloat((amount + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = amount.split(".")[0].split("").reverse();
	var r = amount.split(".")[1];
	var t = "";
	for (var i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	var ret = t.split("").reverse().join("") + "." + r;
	return ret;
}  

/**金额格式化还原*/
function restoreAmt(amount) {   
	if (amount == undefined || amount == '') {
		return 0.0;
	}
    return parseFloat(amount.replace(/[^\d\.-]/g, ""));   
} 

/**
 * 表格内容全部disabled
 * @param formid
 * @param disValue
 */
function setDisable(formid, disValue) {
	$("#" + formid + " input").attr("disabled", disValue);
	$("#" + formid + " select").attr("disabled", disValue);
	$("#" + formid + " checkbox").attr("disabled", disValue);
	$(".easyui-combotree").combotree('disable', disValue);
	$(".easyui-combogrid").combogrid('disable', disValue);
	$(".easyui-combobox").combobox('disable', disValue);
}

/**
 * get numberbox float value
 * 
 * @param {} elementId 表单元素id 
 * @return {} float value
 */
function getNumberBoxFloatValue(elementId){
  return parseFloat($("#" + elementId).numberbox('getValue'));
}

/**
 * 判断浮点是否相等
 * 
 * @param {} number 比较相等的浮点数
 * @param {} digits 精度
 * @return {}
 */
Number.prototype.isEqual = function(number, digits){
	digits = digits == undefined? 2: digits; // 默认精度为2
	return this.toFixed(digits) === number.toFixed(digits);
};

