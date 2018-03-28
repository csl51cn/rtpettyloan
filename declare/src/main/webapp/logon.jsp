<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>欢迎登录润通贷款业务申报系统</title>
    <link href="resources/css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="resources/js/jquery-1.8.0.min.js"></script>
    <script src="resources/js/cloud.js" type="text/javascript"></script>
    <script src="resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="resources/js/sys.login.js" type="text/javascript"></script>
    <script language="javascript">
        $(function () {
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            $(window).resize(function () {
                $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            });

            $('input').keyup(function (event) {
                if (event.keyCode == "13") {
                    document.getElementById("loginbtn").click();   //服务器控件loginsubmit点击事件被触发
                    return false;
                }
            });

        });

        function login() {
            if (vaildateLoginForm()) {
                $("#loginForm").attr("action", "index.do");
                $("#loginForm").submit();
            }
        }
    </script>

</head>

<body style="background-color:#1c77ac; background-image:url(resources/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">


<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>润通贷款业务申报系统</span>
    <ul>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">

    <span style="height:71px; margin-top:70px;"></span>

    <div class="loginbox">
        <form id="loginForm" method="post" action="">
            <ul>
                <li><input id="userCode" name="userCode" type="text" class="loginuser"
                           onclick="JavaScript:this.value=''"/></li>
                <li><input id="password" name="password" type="password" class="loginpwd"
                           onclick="JavaScript:this.value=''"/></li>
                <li><input id="loginbtn" name="loginbtn" type="button" class="loginbtn" value="登录" onclick="login();"/>
                </li>
            </ul>
        </form>

    </div>

</div>


<div class="loginbm" id="footer"></div>
<script type="application/javascript">
        $(function () {
            var now = new Date();
            var year = now.getFullYear();
            $("#footer").html("版权所有 2011-" + year + " 重庆市两江新区润通小额贷款有限公司 ")

        })
</script>

</body>

</html>
