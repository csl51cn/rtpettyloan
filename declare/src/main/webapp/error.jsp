<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>操作失败提示页面</title>
 	<jsp:include page="common/include.jsp" />
    <style type="text/css">
        <!--
        .con_div {
            position: absolute;
            width: 98%;
            height: 300px;
            left: 15%;
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
<%-- <% Exception ex = (Exception)request.getAttribute("ex"); %> --%>
<div align="center" class="con_div">
    <table   width="100%" style="border:solid 1px #f07a7a">
        <tr>
            <td align="center" valign="middle">
                <img src="${pageContext.request.contextPath}/resources/images/msgFalse.gif" border="0" />
            </td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                	错误描述：<label style="color:red;" id='errorlable'>${error }</label>
                    <!-- <a href="javascript:showErrorInfo();" style="font-size: 12px;">[详细信息]</a> -->
                <div ></div>
            </td>
        </tr>
        <%-- <tr>
            <td align="left" valign="middle">
                <div id="errorInfo" style="display: none;">
            		<hr/>
					<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
				</div>
            </td>
        </tr> --%>
    </table>
</div>
<script type="text/javascript">
	function showErrorInfo() {
		var isDisplay = $("#errorInfo").css("display");
		if (isDisplay == 'none'){
			$("#errorInfo").css("display", "block");
		} else {
			$("#errorInfo").css("display", "none");
		}
	}
</script>
</body>
</html>
