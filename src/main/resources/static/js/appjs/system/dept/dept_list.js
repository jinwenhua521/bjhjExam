var mainActivity = {};

// 页面初始化
$(document).ready(function() {
	//加载列表
	mainActivity.loadTree();
	mainActivity.loadGrid();
	
	
});

//加载列表
mainActivity.loadGrid=function(){
	$('#exampleTable').bootstrapTable({
			method : 'get', // 服务器数据的请求方式 get or post
			url : "/system/dept/list", // 服务器数据的加载地址
			iconSize : 'outline',
			toolbar : '#exampleToolbar',
			striped : true, // 设置为true会有隔行变色效果
			dataType : "json", // 服务器返回的数据类型
			pagination : true, // 设置为true会在底部显示分页条
			singleSelect : false, // 设置为true将禁止多选
			// //发送到服务器的数据编码类型
			pageSize : 15, // 如果设置了分页，每页数据条数
			pageNumber : 1, // 如果设置了分布，首页页码
			showColumns : false, // 是否显示内容下拉框（选择显示的列）
			sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
			queryParams : function(params) {
				return {
					deptName : $('#searchName').val(),
					parentId:function(){
						var deptTree = $.fn.zTree.getZTreeObj("deptTree"); 
						if(deptTree){
							var treeNode=deptTree.getSelectedNodes();
							return treeNode[0].deptId;
						}else{
							return "0";
						}
					},
					//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					limit : params.limit,
					offset : params.offset,
				};
			},
			columns : [
				{
					checkbox : true
				},
				{
					field : 'deptCode',
					title : '部门编码',
					width:'140px'
				},
				{
					field : 'deptName',
					title : '部门名称',
					width:'200px'
				},
				{field:'deptFname',title:'路径'},
				{
					field : 'createDate',
					title : '创建时间',
					formatter: function (value, row, index) {
				        return changeDateFormat(value)
				    }
				},
				{
					title : '操作',
					field : 'deptId',
					align : 'center',
					formatter : function(value, row, index) {
						var e = '<button class="btn btn-primary btn-sm ' + s_edit_h + '"  title="编辑" onclick="edit(\''+ row.deptId+ '\')"><i class="fa fa-edit"></i>编辑</button> ';
						var d = '<button class="btn btn-warning btn-sm ' + s_remove_h + '"  title="删除"   onclick="remove(\''
							+ row.deptId
							+ '\')"><i class="fa fa-remove"></i>删除</button> ';
						return e + d;
					}
				} ]
		});
}
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}
//加载树节点
mainActivity.loadTree=function(){
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "deptId",
				pIdKey : "parentId",
				rootPId:'0'
			},
			key:{
			name:"deptName"	
			}
		},
		async : {
			enable : true,// 是否异步加载
			url : "/system/dept/deptTree",
			autoParam : [ "deptId=deptId" ],// 异步发送请求时,表示自动传指定属性的参数值
			dataFilter : function(treeId, parentNode, childNodes) {// 这里由于本人设置的节点属性跟zTree不一致所以进行了过滤
	            return childNodes;
			}
		},
		callback : {
			onClick:deptTreeOnclick
		}
	};
	//添加虚根节点
	var zNodes = [{deptId:'0',deptName:'组织机构树',isParent:true,open:false}];
	$.fn.zTree.init($("#deptTree"), setting, zNodes);
	var deptTree = $.fn.zTree.getZTreeObj("deptTree");  
    //调用默认展开第一个结点    
     var nodes = deptTree.getNodes();    
     deptTree.expandNode(nodes[0], true);  
     deptTree.selectNode(nodes[0],false,true);
     var opt = {
 			query : {
 				parentId :nodes[0].deptId
 			}
 		}
 	$('#exampleTable').bootstrapTable('refresh', opt);
};

//添加
function add(){
	var deptTree = $.fn.zTree.getZTreeObj("deptTree"); 
	var treeNode=deptTree.getSelectedNodes();
	if(treeNode){
		var pId=treeNode[0].deptId;
		var pName=treeNode[0].deptName;
		layer.open({
			type : 2,
			title : '添加部门信息',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '400px' ],
			content :'/system/dept/add/' + pId +"/"+pName// iframe的url
		});
	}
}
//编辑
function edit(id){
		layer.open({
			type : 2,
			title : '编辑部门信息',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '400px' ],
			content :'/system/dept/edit/' + id// iframe的url
		});
}

//查询
function reLoad() {
	$("#exampleTable").bootstrapTable('destroy'); 
	mainActivity.loadGrid();
}

/** //刷新菜单树
 * 刷新当前节点 
 */  
function refreshNode() {  
    /*根据 treeId 获取 zTree 对象*/  
    var zTree = $.fn.zTree.getZTreeObj("deptTree"),  
    /*获取 zTree 当前被选中的节点数据集合*/  
    nodes = zTree.getSelectedNodes();  
    var node1=nodes[0];
    if(node1.deptId!=0){
    	node1.isParent=true;
    }
    /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/  
    zTree.reAsyncChildNodes(node1, "refresh", false);  
}  

function deptTreeOnclick(event, treeId, treeNode){
	$("#exampleTable").bootstrapTable('destroy'); 
	mainActivity.loadGrid();
}

//批量删除
function batchRemove(){
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("子部门会一起删除，确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['deptId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url :'/system/dept/batchRemove',
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
	layer.confirm('删除部门后子部门会一起删除，确定要删除吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/system/dept/remove",
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
