$(function() {
    tabClose();
    tabCloseEven();
});

function addTab(subtitle, url) {
    if ($('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('select', subtitle);
        refreshTab();
    } else {
    	var alltabs = $('#tabs').tabs("tabs");
    	if (alltabs.length > 8) {
    		$.messager.alert("系统提示", "您打开的标签页面太多，请关闭未用的标签后再重试!","error");
    		return;
    	}
    	$('#tabs').tabs('add', {
            title:subtitle,
            content:createFrame(url),
            closed: true,
            //href:url,
            //cache:true,
            closable:true
        });
    }
    tabClose();
}

function createFrame(url) {
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function refreshTab() {
	var currTab = $('#tabs').tabs('getSelected');
    var url = $(currTab.panel('options').content).attr('src');
    $('#tabs').tabs('update', {
        tab:currTab,
        options:{
            content:createFrame(url)
        }
    });
}

function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function() {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    });
    
    /*为选项卡绑定右键*/
    $(".tabs-inner").bind('contextmenu', function(e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}

//绑定右键菜单事件
function tabCloseEven() {
    //刷新
    $('#mm-tabupdate').click(function() {
    	refreshTab();
    });
    
    //关闭当前
    $('#mm-tabclose').click(function() {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    });
    
    //全部关闭
    $('#mm-tabcloseall').click(function() {
        $('.tabs-inner span').each(function(i, n) {
            var t = $(n).text();
            $('#tabs').tabs('close', t);
        });
    });
    
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function() {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function() {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            msgShow('系统提示','右侧无选项卡关闭！','warning');
            return false;
        }
        nextall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function() {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            msgShow('系统提示','左侧无选项卡关闭！','warning');
            return false;
        }
        prevall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.defaults = {ok:"确定",cancel:"取消"};
    $.messager.alert(title, msgString, msgType);
}

