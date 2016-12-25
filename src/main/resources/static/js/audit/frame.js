jQuery(document).ready(function(){
	var index = jQuery("#toIndex").text();
	var model = jQuery("#toModel").text();
	var action = jQuery("#toAction").text();
	if(index != null && index != ''){
		var headerA = jQuery("#headermenu #header"+index);
		headerA.parent().parent().children().removeAttr("class");
		headerA.parent().attr("class","current");
		
		var isOpend = headerA.attr("class");
		if("opened" != isOpend){
			var name = headerA.find("span:eq(2)").text();
			jAlert('您尚未开通【'+name+'】角色，请联系系统管理员', '提示');
		}else{
			jQuery("#leftmenu").load(headerA.children(".leftMenuUrl").text());
			//headerA.click();
		}
		return;
	}
	
	if(jQuery("#headermenu a.opened:first").length == 0){
		jAlert('您尚未开通任何角色，请联系系统管理员', '提示');
		return;
	}
	var header = jQuery("#headermenu a.opened:first");
	header.parent().parent().children().removeAttr("class");
	header.parent().attr("class","current");
	jQuery("#leftmenu").load(header.children(".leftMenuUrl").text());
});

function headerMenuClick(Obj, left, center) {
	return;
//	if(Obj.className == "opened"){
		jQuery("#leftmenu").load(left);
		//jQuery("#centercontent").load(center)
		jQuery(Obj).parent().parent().children().removeAttr("class");
		jQuery(Obj).parent().attr("class","current");
//	} else {
//		notHaveThisRole();
//	}
}

function actionNotOpened(){
	jAlert('当前功能尚未开通', '提示');
}
function notHaveThisRole(){
	jAlert('您尚未开通此角色', '提示');
}

function submitForm(formId, url, toUrl){
	jQuery("#"+formId).validate();
	if(!jQuery("#"+formId).valid()){
		return;
	}
	
	var param = {};
	var paramKeyNum = {};
    var form = jQuery('#'+formId).serializeArray();
    jQuery.each(form, function() {
    	if(paramKeyNum[this.name] != null){
    		if(paramKeyNum[this.name] > 1){
        		param[this.name][param[this.name].length] = this.value;
        	}else{
        		param[this.name] = new Array(param[this.name], this.value);
        	}
    		paramKeyNum[this.name] = 1+paramKeyNum[this.name];
    	} else {
    		type = jQuery('#'+formId).find("input[name="+this.name+"]").attr("type");
    		if("checkbox" == type){
    			param[this.name] = new Array(this.value);
    			paramKeyNum[this.name] = 2;
    		} else {
    			param[this.name] = this.value;
    			paramKeyNum[this.name] = 1;
    		}
    	}
    });
    
    //alert(JSON.stringify(param));
    
    jQuery.ajax({
    	type:'POST',
    	url:url,   
    	contentType: "application/json; charset=UTF-8",
    	data:JSON.stringify(param),
    	dataType:"json",
    	cache : false,
    	success:function(data){
    		if(data.msg != null){
    			jAlert(data.msg, '提示');
    		} else if(error != null){
    			jAlert(data.msg, '警告');
    			return;
    		} else{
    			jAlert("发生未知错误，请求失败", '警告');
    			return;
    		}
    		
    		if(toUrl != null){
    			jQuery("#centercontent").load(toUrl);
    		}
    	},
    	error:function(request, error, e){
    		jAlert("发生未知错误，请求失败", '警告');
    	}
    });
}