var prefix = "/system/menu"
$(function() {
	validateRule();
	//打开图标列表
    $("#ico-btn").click(function(){
        layer.open({
            type: 2,
			title:'图标列表',
			 content: '/FontIcoList.html',
            area: ['480px', '90%'],
            success: function(layero, index){
                //var body = layer.getChildFrame('.ico-list', index);
                //console.log(layero, index);
            }
        });
    });
    $('input[type=radio][name=type]').change(function() {
    	if(this.value==0){
    		$("#form-group-perms").hide();
    		$("#form-group-icon").show();
    		$("#form-group-url").hide();
    	}else if(this.value==1){
    		$("#form-group-perms").show();
    		$("#form-group-icon").show();
    		$("#form-group-url").show();
    	}else if(this.value==2){
    		$("#form-group-perms").show();
    		$("#form-group-icon").hide();
    		$("#form-group-url").hide();
    	}
    });
});
$.validator.setDefaults({
	submitHandler : function() {
		submit();
	}
});
function submit() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/save",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			laryer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功");
				parent.refreshNode();
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				layer.alert(data.msg)
			}
		}
	});
}



function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			label : {
				required : true
			},
			type : {
				required : true
			}
		},
		messages : {
			label : {
				required : icon + "请输入菜单名称"
			},
			type : {
				required : icon + "请选择菜单类型"
			}
		}
	})
}