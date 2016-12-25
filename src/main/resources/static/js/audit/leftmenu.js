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

function loadCenterContentQuery(url){
	var key = jQuery("#query_param_key :selected").val();
	var value = jQuery("#query_param_value").val();
	if(key == null || key == undefined || key == ''){
		jQuery("#centercontent").load(url);
		return;
	}
	jQuery("#centercontent").load(url + "&queryKey=" + key + "&queryValue=" + value);
}