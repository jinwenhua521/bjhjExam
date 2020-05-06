var menuIds;
$(function() {
	getMenuTreeData();
	validateRule();
});
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true
			},
			roleCode : {
				required : true
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名"
			},
			roleCode : {
				required : icon + "请输入角色编码"
			}
		
		}
	});
}
$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function getMenuTreeData() {
	var roleId = $('#roleId').val();
	var setting = {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "menuId",
					pIdKey : "parentId",
					rootPId:'0'
				},
				key:{
				name:"label"	
				}
			},
			async : {
				enable : true,// 是否异步加载
				url : "/system/menu/checkMenuTree",
				autoParam : [ "menuId=menuId" ],// 异步发送请求时,表示自动传指定属性的参数值
				otherParam: {"roleId":roleId},
				dataFilter : function(treeId, parentNode, childNodes) {// 这里由于本人设置的节点属性跟zTree不一致所以进行了过滤
		            return childNodes;
				}
			},
			callback : {
				onAsyncSuccess: menuTreeOnAsyncSuccess,
				onCheck:funTreeOncheck
			}
		};
		//添加虚根节点
		var zNodes = [{menuId:'0',label:'功能菜单管理树',isParent:true,open:false,icon:'/js/appjs/system/menu/img/menu01.png'}];
		$.fn.zTree.init($("#menuTree"), setting, zNodes);
		var menuTree = $.fn.zTree.getZTreeObj("menuTree");  
	    //调用默认展开第一个结点    
	     var nodes = menuTree.getNodes();    
	     menuTree.expandNode(nodes[0], true);  
	     menuTree.selectNode(nodes[0],false,true);
}

//异步加载节点
function menuTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
    // 这个方法是将标准 JSON 嵌套格式的数据转换为简单 Array 格式
    var nodes = zTreeObj.transformToArray(zTreeObj.getNodes()); 
    for (var i = 0; i < nodes.length; i++) {
        // 判断节点是否已经加载过，如果已经加载过则不需要再加载
        if (!nodes[i].zAsync) {
            zTreeObj.reAsyncChildNodes(nodes[i], '', true);
        }
    }
}

function funTreeOncheck(event, treeId, treeNode){
	var menuIds=new Array();
	var funTree=$.fn.zTree.getZTreeObj("menuTree");
	var checkNodes=funTree.getCheckedNodes(true);
    for(var i=0;i<checkNodes.length;i++){
    	menuIds.push(checkNodes[i].menuId);
    }
    $('#menuIds').val(menuIds);
}
function update() {
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/role/update",
		data : role, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
			}

		}
	});
}
