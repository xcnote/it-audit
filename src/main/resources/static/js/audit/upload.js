
//选择文件之后执行上传
function addChangeInFile(fileid, url, uploadid, showId, follow){
//	jQuery("#"+uploadid).on('click', function() {
//		jQuery('#'+fileid).click();  
//    });
	jQuery('#'+uploadid).on('click', function() {
		if(jQuery('#'+fileid).val() <= 0){
			jAlert("未选择任何文件", '警告');
			return;
		}
		jQuery.ajaxFileUpload({  
			url:url,  
			secureuri:false,  
			fileElementId:fileid,//file标签的id  
			dataType: 'json',//返回数据的类型  
			data:{},//一同上传的数据  
			success: function (data, status) { 
				if(data.code == 0){
					if(data.msg!=null){
	    				jAlert(data.msg, '提示');
	    			}
					if(showId!=null){
						jQuery("#" + showId).show();
					}
					follow(data);
				} else if(data.error != null){
	    			jAlert(data.error, '警告');
	    			return;
	    		} else{
	    			jAlert("发生未知错误，上传失败", '警告');
	    			return;
	    		}
			},  
			error: function (data, status, e) {  
				jAlert("发生未知错误，上传失败", '警告');
			}  
		});  
	});
}

function clearFile(url, clearName){
	jConfirm('是否清空附件?', '提示', function(r) {
		if(r){
			jQuery.ajax({
		    	type:'GET',
		    	url:url,   
		    	contentType: "application/json; charset=UTF-8",
		    	cache : false,
		    	success:function(data){
		    		if(data.code == 0){
		    			if(data.msg!=null){
		    				jAlert(data.msg, '提示');
		    			}
		    			jQuery("."+clearName).remove();
		    		} else if(data.error != null){
		    			jAlert(data.error, '警告');
		    			return;
		    		} else{
		    			jAlert("发生未知错误，请求失败", '警告');
		    			return;
		    		}
		    	},
		    	error:function(request, error, e){
		    		jAlert("发生未知错误，请求失败", '警告');
		    	}
		    });
		}
	});
}