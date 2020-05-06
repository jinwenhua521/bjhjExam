$().ready(function() {
	validateRule();
});

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			deptName : {
				required : true
			},
			deptCode:{
				required:true
			}
		},
		messages : {
			deptName : {
				required : icon + "请输入部门名称"
			},
			deptCode:{
				required:icon+"请输入部门编码"
			}
		}
	})
}
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var deptFid=$("#odeptFid").val();
	var deptFname=$("#odeptFname").val();
	var deptFcode=$("#odeptFcode").val();
	if(deptFid!="" && deptFname!=""){
		deptFid=deptFid+"/"+$("#deptId").val();
		deptFname=deptFname+"/"+$("#deptName").val();
		deptFcode=deptFcode+"/"+$("#deptCode").val()
	}else{
		deptFid="/"+$("#deptId").val();
		deptFname="/"+$("#deptName").val();
		deptFcode="/"+$("#deptCode").val();
	}
	$("#deptFid").val(deptFid);
	$("#deptFname").val(deptFname);
	$("#deptFcode").val(deptFcode);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/dept/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				parent.refreshNode();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
