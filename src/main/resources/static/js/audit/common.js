
function limitTableTd(tableId, len){
    var objs = jQuery("#"+tableId+" td.limit");
    for (i=0;i<objs.length;i++){
    	var obj = objs.eq(i)
    	if(obj.text() != null && obj.text().length > len){
    		obj.text(obj.text().substring(0,len)+'…');
    	}
    }
}