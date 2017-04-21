if (self != top) {
	parent.location.href = "logon.do";
}

function generateCode() {
	var r = Math.random();
	$("#validateCodeImg").attr("src", "generateCode.do?param=" + r);
}

function showMsg(msg) {
	//$("#msg").html(msg);
	alert(msg);
}

/**校验登录*/
function vaildateLoginForm() {
	$("#submit").attr("disabled", true);
	var userCode = $("#userCode");
	var password = $("#password");
	//var validateCode = $("#validateCode");
	
	if (userCode.val() == undefined || userCode.val() == "") {
		showMsg("柜员号不能为空!");
		userCode.focus();
		$("#submit").removeAttr("disabled");
		return false;
	}
	
	if (password.val() == undefined || password.val() == "") {
		showMsg('密码不能为空!');
		password.focus();
		$("#submit").removeAttr("disabled");
		return false;
	}
	
	var msgCode = -1;
	$.ajax({
		type : "POST",
		dataType : "text",
		url : "checkLogin.do",
		data : "userCode="+userCode.val()+"&password="+password.val(),
		async : false,
		success : function(data) {
			var obj = $.parseJSON(data);
			msgCode = obj.code;
			switch (msgCode) {
			case "5":
				showMsg("柜员号不存在!");
				generateCode();
				password.val("");
				validateCode.val("");
				userid.focus();
				$("#submit").removeAttr("disabled");
				break;
			case "6":
				showMsg("密码错误!");
				generateCode();
				password.val("");
				validateCode.val("");
				password.focus();
				$("#submit").removeAttr("disabled");
				break;
			case "9":
				showMsg("柜员号或密码错误!");
				generateCode();
				password.val("");
				validateCode.val("");
				password.focus();
				$("#submit").removeAttr("disabled");
				break;
			case "2":
				showMsg("柜员号被锁定，请联系系统管理员解锁后登录!");
				$("#submit").removeAttr("disabled");
				break;
			case "3" || "4": // 密码过期 || 首次登陆
				// 跳转修改密码界面
			case "7":
				showMsg('密码输入错误达到最大限制次数，请速联系系统管理员解锁!');
				$("#submit").removeAttr("disabled");
				break;
			case "8":
				showMsg('同一客户端不允许同时登录多个用户，请稍后再试!');
				$("#submit").removeAttr("disabled");
				break;
			case "99":
				showMsg('未知错误,请联系系统管理员!');
				$("#submit").removeAttr("disabled");
				break;
			default:
				break;
			}
		}
	});
	
	if (msgCode == 0) {
		return true;
	}
	
	if (msgCode == 10){
		if(confirm("您的帐号已在其他地方登录，本次登录将踢出对方，如果这不是您本人的操作，建议您立即修改密码!是否立即修改密码?")){
			window.location.href="sys/userController.do?method=toChangePwd&userCode="+userCode.val()+"&flag=Y";
			return false;
		}
		return true;
	}
	return false;
}


