jQuery(document).ready(function(){

});

function headerMenuClick(Obj, left, center) {
	if(Obj.className == "opened"){
		jQuery("#leftmenu").load(left);
		jQuery("#centercontent").load(center)
	} else {
		notHaveThisRole();
	}
}

function actionNotOpened(){
	alert("当前功能尚未开通");
}
function notHaveThisRole(){
	alert("您尚未开通此角色");
}