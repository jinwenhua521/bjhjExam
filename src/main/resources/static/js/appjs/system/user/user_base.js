$(function() {
	//选项卡点击事件
	 var currentTab = 'upload';
	 var webcamAvailable = false;
    $('#avatar-tab li').click(function () {
        if (currentTab != this.id) {
            currentTab = this.id;
            $(this).addClass('active');
            $(this).siblings().removeClass('active');
            if(this.id == "swf1")
			{
				$('#webcamPanelButton').hide();
				$('#editorPanelButtons').show();
			}
            //如果是点击“相册选取”
            if (this.id === 'albums') {
                //隐藏flash
            	 hideSWF();
                showAlbums();
            }
            else {
            	showSWF();
                hideAlbums();
                if (this.id === 'webcam') {
                    if (webcamAvailable) {
                        $('.button_shutter').removeClass('Disabled');
                        $('#webcamPanelButton').show();
                        $('#editorPanelButtons').hide();
                    }
                }
                else {
                    //隐藏所有按钮
                	 $('#editorPanelButtons,#webcamPanelButton').hide();
                }
            }
        }
    });
});
function hideSWF() {
    $('#webcamPanelButton').hide();
}
function showSWF() {
	 $('#webcamPanelButton').show();
}

//显示相册的函数
function showAlbums() {
    $('#userAlbums').show();
}
//隐藏相册的函数
function hideAlbums() {
    $('#userAlbums').hide();
}