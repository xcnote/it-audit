jQuery(document).ready(function(){
	jQuery("#headermenu a.opened:first").click();
	if(jQuery("#headermenu a.opened:first").length == 0){
		jAlert('您尚未开通任何角色，请联系系统管理员', '提示');
		//alert("您尚未开通任何角色，请联系系统管理员");
	}
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
	//alert("当前功能尚未开通");
}
function notHaveThisRole(){
	jAlert('您尚未开通此角色', '提示');
	//alert("您尚未开通此角色");
}