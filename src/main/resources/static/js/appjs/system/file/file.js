var total;
var type,label;
$(function() {
	changeType('');

});

var clipboard = new Clipboard('button.copy', {
	text : function(trigger) {
		parent.layer.msg('文件路径已复制到粘贴板');
		return $(trigger).attr('url');

	}
});

layui.use('upload', function() {
	var upload = layui.upload;
	// 执行实例
	var uploadInst = upload.render({
		elem : '#up_btn', // 绑定元素
		url : '/system/file/upload', // 上传接口
		size :0,
		accept : 'file',
		done : function(r) {
			parent.layer.msg(r.msg);
			bindList(0);
		},
		error : function(r) {
			layer.msg(r.msg);
		}
	});
});

function changeType(i) {
	if(i==0){
		type = "";
	}else if(i==1){
		type="图片";
	}else if(i==2){
		type="文件";
	}else if(i==3){
		type="视频";
	}else if(i==4){
		type="音乐";
	}else if(i==5){
		type="书籍";
	}
	bindList(0)
}

function changeType_label(obj) {
	label=$(obj).text();
	bind_label_List(0)
}
function doUpload() {
	if ($("#file").val() == "") {
		layer.msg("请要上传的文件");
		return;
	}
	var formData = new FormData($("#uploadForm")[0]);
	$.ajax({
		url : '/system/file/upload',
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(r) {
			parent.layer.msg(r.msg);
			bindList(0);
		},
		error : function(r) {
			layer.alert("文件大小超限,最大支持30MB");
		}
	});
}

function bindList(offset) {
	$.ajax({
			url : '/system/file/list?limit=10&offset=' + offset + '&filetype=' + type,
			method : 'get',
			dataType : 'json',
			async : false,
			cache : false,
			success : function(data) {
				$(".file-box").empty();
				total = data.total;
				var rows = data.rows;
				var htmlText = "";
				for (i = 0; i < rows.length; i++) {
					htmlText += '<div class="file-box">';
					htmlText += '<div class="file">';
					htmlText += '<a href="javascript:void(0)"> <span class="corner"></span>';
					
					if(rows[i].filetype =='图片'){
					htmlText += '<div class="image">';
					htmlText += '<img alt="image" class="img-responsive" src="' + rows[i].fileurl + '">';
					htmlText += '</div>';
					}else{
						if(rows[i].filetype=="文件"){
							htmlText+='<div class="icon"><i class="fa fa-file"></i></div>';
						}else if(rows[i].filetype=="音乐"){
							htmlText+='<div class="icon"><i class="fa fa-music"></i></div>';
                        }else if(rows[i].filetype=="视频"){
                        	htmlText+='<div class="icon"><i class="img-responsive fa fa-film"></i></div>';
                        }else if(rows[i].filetype=="书籍"){
                        	htmlText+='<div class="icon"><i class="img-responsive fa fa-file-text"></i></div>';	
                        }
					}
					
					htmlText += '<div class="file-name">'+rows[i].filename+'<br>';
					htmlText += '<small>' + rows[i].upDate + '</small><br/>'
					htmlText += '</div>';
					htmlText += '</a>'
					htmlText += '&nbsp; &nbsp;<button class="btn btn-warning btn-xs copy" url="' + rows[i].fileurl + '">复制</button>&nbsp; &nbsp; ';
					if(rows[i].filetype=="文件" || rows[i].filetype=="书籍"){
						htmlText+='<button id="download_btn" class="btn btn-info btn-xs" name="' + rows[i].fileurl + '" onclick="download(this)" >下载</button>&nbsp; &nbsp;'
					}else{
						htmlText+='<button id="download_btn" class="btn btn-info btn-xs" name="' + rows[i].fileurl + '" onclick="download(this)" >打开</button>&nbsp; &nbsp;'
					}
					htmlText+='<button class="btn btn-danger btn-xs" id="' + rows[i].sid + '" onclick="remove(this)">删除</button>&nbsp; &nbsp;';
					htmlText+='<button class="btn btn-success btn-xs" id="' + rows[i].sid + '" onclick="edit_label(this)">标签</button>';
					htmlText += '</div>';
					htmlText += '</div>';
				}
				$("#incomeNum").append(htmlText);
				page();

			}
		});
}

function bind_label_List(offset) {
	$.ajax({
			url : '/system/file/list?limit=10&offset=' + offset + '&fileLabel=' + label,
			method : 'get',
			dataType : 'json',
			async : false,
			cache : false,
			success : function(data) {
				$(".file-box").empty();
				total = data.total;
				var rows = data.rows;
				var htmlText = "";
				for (i = 0; i < rows.length; i++) {
					htmlText += '<div class="file-box">';
					htmlText += '<div class="file">';
					htmlText += '<a href=""> <span class="corner"></span>';
					
					if(rows[i].filetype =='图片'){
					htmlText += '<div class="image">';
					htmlText += '<img alt="image" class="img-responsive" src="' + rows[i].fileurl + '">';
					htmlText += '</div>';
					}else{
						if(rows[i].filetype=="文件"){
							htmlText+='<div class="icon"><i class="fa fa-file"></i></div>';
						}else if(rows[i].filetype=="音乐"){
							htmlText+='<div class="icon"><i class="fa fa-music"></i></div>';
                        }else if(rows[i].filetype=="视频"){
                        	htmlText+='<div class="icon"><i class="img-responsive fa fa-film"></i></div>';
                        }else if(rows[i].filetype=="书籍"){
                        	htmlText+='<div class="icon"><i class="img-responsive fa fa-file-text"></i></div>';	
                        }
					}
					
					htmlText += '<div class="file-name">'+rows[i].filename+'<br>';
					htmlText += '<small>' + rows[i].upDate + '</small><br/>'
					htmlText += '</div>';
					htmlText += '</a>'
						htmlText += '&nbsp; &nbsp;<button class="btn btn-warning btn-xs copy" url="' + rows[i].fileurl + '">复制</button>&nbsp; &nbsp; ';
					if(rows[i].filetype=="文件" || rows[i].filetype=="书籍"){
						htmlText+='<button id="download_btn" class="btn btn-info btn-xs" name="' + rows[i].fileurl + '" onclick="download(this)" >下载</button>&nbsp; &nbsp;'
					}else{
						htmlText+='<button id="download_btn" class="btn btn-info btn-xs" name="' + rows[i].fileurl + '" onclick="download(this)" >打开</button>&nbsp; &nbsp;'
					}
					htmlText+='<button class="btn btn-danger btn-xs" id="' + rows[i].sid + '" onclick="remove(this)">删除</button>&nbsp; &nbsp;';
					htmlText+='<button class="btn btn-success btn-xs" id="' + rows[i].sid + '" onclick="edit_label(this)">标签</button>';
					htmlText += '</div>';
					htmlText += '</div>';
				}
				$("#incomeNum").append(htmlText);
				page();

			}
		});
	
}
//下载
function download(obj){
	var url=$(obj).attr("name");
	 var iWidth=720;                          //弹出窗口的宽度; 
     var iHeight=600;                         //弹出窗口的高度; 
     //获得窗口的垂直位置 
     var iTop = (window.screen.availHeight - 30 - iHeight) / 2; 
     //获得窗口的水平位置 
     var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; 
     window.open(url, "new window", 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
}
//编辑标签
function edit_label(obj){
	var id=$(obj).attr("id");
	parent.layer.open({
		type : 2,
		title : '添加标签',
		maxmin: true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '600px', '360px' ],
		content : '/system/file/edit/' + id // iframe的url
	});
	
}

function update_label() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/file/update",
		data : $('#signupForm').serialize(), // 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function remove(obj) {
	var id=$(obj).attr("id");
	parent.layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/system/file/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					parent.layer.msg(r.msg);
					bindList(0);
				} else {
					parent.layer.msg(r.msg);
					bindList(0);
				}
			}
		});
	})
}

function setLabel(obj){
	var text=$(obj).text();
	$("#fileLabel").val(text);
}
function page() {
	var options = {
		currentPage : 1, // 当前页
		totalPages : total / (10 + 1) + 1, // 总页数
		numberofPages : 4, // 显示的页数
		bootstrapMajorVersion : 3,
		alignment : 'center',
		size : 'normal',
		shouldShowPage : true,
		itemTexts : function(type, page, current) { // 修改显示文字
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "尾页";
			case "page":
				return page;
			}
		},
		onPageClicked : function(event, originalEvent, type, page) {
			var offset = (page - 1) * 10;
			bindList(offset);
		}
	};
	$('#page').bootstrapPaginator(options);
}