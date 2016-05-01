$(document).ready(function(){
	$('#intermediate-info').waypoint({
		handler: function(direction){
			if (direction==="down"){
				$('#current-bid').addClass('current-bid-on-top');
						
			}else if(direction ==="up"){
				
				$('#current-bid').removeClass('current-bid-on-top').removeClass('span-2-of-3').prev().remove();
				
			}
		}
	})
	
	
});