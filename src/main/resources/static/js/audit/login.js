
jQuery(document).ready(function(){
	var url = window.location.href;
	if(url.indexOf('/login') <= 0){
		window.location.replace("/");
	}
});
