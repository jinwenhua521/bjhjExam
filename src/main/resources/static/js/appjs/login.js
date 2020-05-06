$(document).ready(function() {
	validateRule();
	//$("#signupForm").validate();
	$("#rememberMe").on("change",null,function(event){  
		$.cookie("as_remember",this.checked, {expires:7,path:'/'}); 
	})[0].checked="false";
	if($.cookie("as_remember")=="true"){
		$("#username").val($.cookie("yufei_username"));
	}else{
		$("#rememberMe").removeAttr("checked"); 
	}
});
$.validator.setDefaults({
	submitHandler : function() {
		login();
	}
});

function login() {
	var userCode=$("#username").val();
	$.cookie("yufei_username", userCode, {expires:7,path:'/'}); 
	$.ajax({
		type : "POST",
		url : "/login",
		data : $('#signupForm').serialize(),
		success : function(r) {
			if (r.code == 0) {
				parent.location.href = '/index';
			} else {
				document.getElementById("codeImage").src = "/authCode/getAuthCode?"+ Math.random();
				layer.msg(r.msg);
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			username : {
				required : true
			},
			password : {
				required : true
			},
			vcode : {
				required : true
			}
		},
		messages : {
			username : {
				required : icon + "请输入您的用户名",
			},
			password : {
				required : icon + "请输入您的密码",
			},
			vcode : {
				required : icon + "请输入验证码",
			}
		}
	})
}
function chageCode() {
	document.getElementById("codeImage").src = "/authCode/getAuthCode?"+ Math.random();
}