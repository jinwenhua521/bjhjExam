var mainActivity = {};

// 页面初始化
$(document).ready(function() {
	mainActivity.loadGrid();
	mainActivity.loadTree();
	//加载列表
	
});

//加载列表
mainActivity.loadGrid=function(){
	$('#exampleTable').bootstrapTable({
			method : 'get', // 服务器数据的请求方式 get or post
			url : "/system/menu/list", // 服务器数据的加载地址
			iconSize : 'outline',
			toolbar : '#exampleToolbar',
			striped : true, // 设置为true会有隔行变色效果
			dataType : "json", // 服务器返回的数据类型
			pagination : true, // 设置为true会在底部显示分页条
			singleSelect : false, // 设置为true将禁止多选
			// //发送到服务器的数据编码类型
			pageSize : 20, // 如果设置了分页，每页数据条数
			pageNumber : 1, // 如果设置了分布，首页页码
			showColumns : false, // 是否显示内容下拉框（选择显示的列）
			sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
			queryParams : function(params) {
				return {
					//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					limit : params.limit,
					offset : params.offset,
					label : $('#searchName').val(),
					parentId:function(){
						var menuTree = $.fn.zTree.getZTreeObj("menuTree"); 
						if(menuTree){
							var treeNode=menuTree.getSelectedNodes();
							return treeNode[0].menuId;
						}else{
							return "0";
						}
					},
				};
			},
			columns : [
				{
					checkbox : true
				},
				{
					field : 'label',
					title : '名称',
					width:'200px'
				},
				{
					field : 'type',
					title : '类型',
					width : '100px',
					formatter:function(value,row,index){
						//类型 0：目录 1：菜单 2：按钮
						var s="";
						if(value==1){
							s="<span  class='button ico_docu' style='background:url(/js/appjs/system/menu/img/menu03.png) 0 0 no-repeat;'>&nbsp;   &nbsp;&nbsp;&nbsp;</span><font color='green'>菜单<font>";
						}else if(value==2){
							s="<span  class='button ico_docu' style='background:url(/js/appjs/system/menu/img/menu04.png) 0 0 no-repeat;'>&nbsp;   &nbsp;&nbsp;&nbsp;</span><font color='green'>按钮<font>";
						}else if(value==0){
							s="<span  class='button ico_docu' style='background:url(/js/appjs/system/menu/img/menu02.png) 0 0 no-repeat;'>&nbsp;   &nbsp;&nbsp;&nbsp;</span><font color='green'>目录<font>";
						}
						return s;
					}
				},
				{field:'perms',title:'权限'},
				{
					field : 'surl',
					title : '地址',
					width:'200px'
				},
				{
					field : 'createDate',
					title : '创建时间'
				},
				{
					title : '操作',
					field : 'menuId',
					align : 'center',
					formatter : function(value, row, index) {
						var e = '<button class="btn btn-primary btn-sm ' + s_edit_h + '"  title="编辑" onclick="edit(\''+ row.menuId+ '\')"><i class="fa fa-edit"></i>编辑</button> ';
						var d = '<button class="btn btn-warning btn-sm ' + s_remove_h + '"  title="删除"   onclick="remove(\''
							+ row.menuId
							+ '\')"><i class="fa fa-remove"></i>删除</button> ';
						return e + d;
					}
				} ]
		});
}

//加载树节点
mainActivity.loadTree=function(){
	var setting = {
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
			url : "/system/menu/menuTree",
			autoParam : [ "menuId=menuId" ],// 异步发送请求时,表示自动传指定属性的参数值
			dataFilter : function(treeId, parentNode, childNodes) {// 这里由于本人设置的节点属性跟zTree不一致所以进行了过滤
	            return childNodes;
			}
		},
		callback : {
			onClick:menuTreeOnclick
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
     var opt = {
 			query : {
 				parentId :nodes[0].menuId
 			}
 		}
 	$('#exampleTable').bootstrapTable('refresh', opt);
};

//添加
function add(){
	var menuTree = $.fn.zTree.getZTreeObj("menuTree"); 
	var treeNode=menuTree.getSelectedNodes();
	if(treeNode){
		var pId=treeNode[0].menuId;
		var pName=treeNode[0].label;
		layer.open({
			type : 2,
			title : '增加菜单',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '520px' ],
			content :'/system/menu/add/' + pId +"/"+pName// iframe的url
		});
	}
}
//编辑
function edit(id){
	var menuTree = $.fn.zTree.getZTreeObj("menuTree"); 
	var treeNode=menuTree.getSelectedNodes();
	if(treeNode){
		var pId=treeNode[0].menuId;
		var pName=treeNode[0].label;
		layer.open({
			type : 2,
			title : '编辑菜单',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '520px' ],
			content :'/system/menu/edit/' + id+"/"+pName// iframe的url
		});
	}
}

//查询
function reLoad() {
	var menuTree = $.fn.zTree.getZTreeObj("menuTree"); 
	var treeNode=menuTree.getSelectedNodes();
	var opt = {
		query : {
			label : $('#searchName').val(),
			parentId:treeNode[0].menuId
		}
	}
	$('#exampleTable').bootstrapTable('refresh', opt);
}

/** //刷新菜单树
 * 刷新当前节点 
 */  
function refreshNode() {  
    /*根据 treeId 获取 zTree 对象*/  
    var zTree = $.fn.zTree.getZTreeObj("menuTree"),  
    /*获取 zTree 当前被选中的节点数据集合*/  
    nodes = zTree.getSelectedNodes();  
    var node1=nodes[0];
    if(node1.menuId!=0){
    	node1.isParent=true;
    }
    /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/  
    zTree.reAsyncChildNodes(node1, "refresh", false);  
}  

function menuTreeOnclick(event, treeId, treeNode){
	var id=treeNode.menuId;
	var opt = {
			query : {
				label : $('#searchName').val(),
				parentId :id
			}
		}
	$('#exampleTable').bootstrapTable('refresh', opt);
}

//批量删除
function batchRemove(){
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['menuId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url :'/system/menu/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
					refreshNode();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}
//删除
function remove(id){
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/system/menu/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
					refreshNode();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
