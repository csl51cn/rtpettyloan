<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无权限</title>
 
    <style type="text/css">
        <!--
        .con_div {
            position: absolute;
            width: 400px;
            height: 300px;
            left: 50%;
            top: 50%;
            margin-left: -200px;
            margin-top: -150px;
            border: 1;
            vertical-align:middle;
        }
        
        a{color:blue;text-decoration:none; } 
		a:hover {color:#CC3300;text-decoration:none;}
        -->
    </style>
</head>
<body>
<div align="center" class="con_div">
    <table   width="100%" style="border:solid 1px #f07a7a">
        <tr>
            <td align="center" valign="middle">
                <img src="${pageContext.request.contextPath}/resources/images/warn.jpg" border="0">
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                <label style="font-size:12px;color:red;">对不起，您的权限不足，请联系系统管理员为您分配权限！</label>
            </td>
        </tr>
    </table>
</div>
</body>

</html>
