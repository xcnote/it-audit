jQuery(document).ready(function(){
	var index = jQuery("#toIndex").text();
	var model = jQuery("#toModel").text();
	var action = jQuery("#toAction").text();
	if(index != null && index != ''){
		var headerA = jQuery("#headermenu #header"+index);
		var isOpend = headerA.attr("class");
		if("opened" != isOpend){
			var name = headerA.find("span:eq(1)").text();
			jAlert('您尚未开通【'+name+'】角色，请联系系统管理员', '提示');
		}else{
			headerA.click();
		}
		return;
	}
	
	if(jQuery("#headermenu a.opened:first").length == 0){
		jAlert('您尚未开通任何角色，请联系系统管理员', '提示');
		return;
	}
	jQuery("#headermenu a.opened:first").click();
});

function headerMenuClick(Obj, left, center) {
	if(Obj.className == "opened"){
		jQuery("#leftmenu").load(left);
		//jQuery("#centercontent").load(center)
		jQuery(Obj).parent().parent().children().removeAttr("class");
		jQuery(Obj).parent().attr("class","current");
	} else {
		notHaveThisRole();
	}
}

function actionNotOpened(){
	jAlert('当前功能尚未开通', '提示');
}
function notHaveThisRole(){
	jAlert('您尚未开通此角色', '提示');
}

function submitForm(formId, url, toUrl){
	var param = {};
	var paramKeyNum = {};
    var form = jQuery('#'+formId).serializeArray();
    jQuery.each(form, function() {
    	if(param[this.name] != null){
    		if(paramKeyNum[this.name] > 1){
    			param[this.name][paramKeyNum[this.name]] = this.value;
    		} else{
    			param[this.name] = new Array(this.value, param[this.name]);
    		}
    		paramKeyNum[this.name] = paramKeyNum[this.name] + 1;
    	}else{
    		param[this.name] = this.value;
    		paramKeyNum[this.name] = 1;
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
    		} else if(toUrl != null){
    			jQuery("#centercontent").load(toUrl);
    		}
    	}                   
    });
}