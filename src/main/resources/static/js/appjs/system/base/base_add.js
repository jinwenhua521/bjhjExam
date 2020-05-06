$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	var layerIndex=parent.layer.load(1);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/base/save",
		data : $('#signupForm').serialize(), // 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data,textStatus) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}
			parent.layer.close(layerIndex);

		}
	});

}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			code:{
				required:true
			},
			type:{
				required:true
			},
			state:{
				required:true
			}
		},
		messages : {
			name : {
				required : icon + "请输入名称"
			},
			code:{
				required:icon+"请输入编码"
			},
			type:{
				required:icon+"请输入类型"
			},
		    state:{
		    	required:icon+"请选择状态"
		    }
		}
	})
}