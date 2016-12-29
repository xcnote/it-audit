jQuery(document).ready(function(){
	jQuery("#leftmenu li").click(function(){
		jQuery(this).parent().children().removeAttr("class");
		jQuery(this).attr("class","current");
	});
	jQuery("#leftmenu li:first").click();
	jQuery("#leftmenu li:first a").click();
});

function loadCenterContent(url){
	jQuery("#centercontent").load(url);
}
function loadTabContent(tabId, url){
	jQuery("#"+tabId).load(url);
}

function loadCenterContentQuery(url){
	var key = jQuery("#query_param_key :selected").val();
	var value = jQuery("#query_param_value").val();
	if(key == null || key == undefined || key == ''){
		jQuery("#centercontent").load(url);
		return;
	}
	jQuery("#centercontent").load(url + "&queryKey=" + key + "&queryValue=" + value);
}

function loadCenterContentBySubLeft(subKey, subValue){
	jQuery("#"+subKey).text(subValue);
	jQuery("#"+subKey).next("ul").children("li:eq(0)").children("a").click();
	jQuery("#"+subKey).next("ul").children("li").children("a").removeAttr("style");
}
function disableSubMenu(subKey){
	jQuery("#"+subKey).text("");
	jQuery("#"+subKey).next("ul").children("li").removeAttr("class");
	jQuery("#"+subKey).next("ul").children("li").children("a").removeAttr("style");
}
function loadCenterContentBySubClick(subKey, url){
	var subValue = jQuery("#"+subKey).text();
	if(subValue == null || subValue == ''){
		jAlert('请先选定项目编号或项目名称', '提示');
		jQuery("#"+subKey).next("ul").children("li").children("a").attr("style","color: #999;");
		return false;
	}
	loadCenterContent(url);
}