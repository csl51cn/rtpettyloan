
var sys = $.extend({}, sys);/* 全局对象 */

$.parser.auto = false;
$(function() {
//	$.messager.progress({
//		text : '页面加载中，请稍候...',
//		interval : 100
//	});
	$.parser.parse(window.document);
//	window.setTimeout(function() {
//		$.messager.progress('close');
//		if (self != parent) {
//			window.setTimeout(function() {
//				parent.$.messager.progress('close');
//			}, 500);
//		}
//	}, 1);
	$.parser.auto = true;
	
});

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}
};

$.fn.panel.defaults.loadingMessage = '页面加载中，请稍候...';
$.fn.datagrid.defaults.loadMsg = '页面加载中，请稍候...';

var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/* alert(XMLHttpRequest.responseText.split('<script')[0]); */
//	$.messager.alert('系统提示', XMLHttpRequest.responseText.split('<script')[0].error, 'error');
	var error = XMLHttpRequest.responseText;
	var msg;
	if(error.indexOf("html") !=-1 || error.indexOf("HTML") !=-1 ){
		msg = $("#errorlable",$("<div/>").html(error)).html();
	}else{
		msg = $.parseJSON(XMLHttpRequest.responseText).error;
		if(msg.lastIndexOf("Exception:") !=-1){
			//msg = msg.split("Exception:");
			//msg = msg[msg.length-1];
			msg = msg.substring(msg.lastIndexOf("Exception:"));
		}
	}
	//$.messager.alert('系统提示',"<span style='color:red'>"+msg+"</span>");
	
	$.dialog({ 
		title: '异常信息提示',
	    content: "<span style='color:red;font-size:16px;'>"+msg+"</span>", 
	    ok: function(){ 
	    	return true;
	    }, 
	    width:400,
	    height:150,
	    maxBtn:false,
	    minBtn:false,
	    lock:true 
	});
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', sys.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});

/**
 * 获得项目根路径
 * 
 * 使用方法：sys.bp();
 */
sys.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sys.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sys.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 生成UUID
 */
sys.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
sys.UUID = function() {
	return (sys.random4() + sys.random4() + "-" + sys.random4() + "-" + sys.random4() + "-" + sys.random4() + "-" + sys.random4() + sys.random4() + sys.random4());
};

/**
 * 获得URL参数
 */
sys.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

sys.getList = function(value) {
	if (value) {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sys.png = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/blank.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sys.bgPng = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sys.isLessThanIe8 = function() {/* 判断浏览器是否是IE并且版本小于8 */
	return ($.browser.msie && $.browser.version < 8);
};

$.ajaxSetup({
	type : 'POST',
	async : false,
	error : function(XMLHttpRequest, textStatus, errorThrown) {/* 扩展AJAX出现错误的提示 */
		$.messager.progress('close');
		var msg = $.parseJSON(XMLHttpRequest.responseText).error;
		//var msg = $.parseJSON(XMLHttpRequest.responseText.split('<script')[0]).error;
		//$.messager.alert('系统提示', "操作失败:<br/>" + msg, 'error');
		$.dialog({ 
			title: '异常信息提示',
		    content: "<span style='color:red;font-size:16px;'>"+msg+"</span>", 
		    ok: function(){ 
		    	return true;
		    }, 
		    width:400,
		    height:150,
		    maxBtn:false,
		    minBtn:false,
		    lock:true 
		});
	}
});

