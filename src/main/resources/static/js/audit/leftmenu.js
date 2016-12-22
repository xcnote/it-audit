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