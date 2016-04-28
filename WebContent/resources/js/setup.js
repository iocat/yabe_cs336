// this set up needs jquery to run
$(document).ready(function(){
	$(function() {
		  $('a[href*="#"]:not([href="#"])').click(function() {
		    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
		      var target = $(this.hash);
		      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
		      if (target.length) {
		        $('html, body').animate({
		          scrollTop: target.offset().top
		        }, 500);
		        return false;
		      }
		    }
		  });
		});
	
	var CTW = $('.container-with-tabs');
	
	CTW.find('ul:first-child>li').click(
			function(){
				var $data = $(this).data('show')
				$(this).siblings().removeClass('active')
				$(this).addClass('active')
				$(this).parent().siblings().addClass("hidden")
				$(this).parent().siblings('#'+$data).removeClass("hidden")
			}
	);
	

});

function getAjax(){
	var ajaxRequest;
	try{
		ajaxRequest = new XMLHttpRequest();
		return ajaxRequest;
	}catch(e){
		try {
			ajaxRequest = new ActiveXObject("Msxml12.XMLHTTP");
			return ajaxRequest;
		}catch(e){
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
				return ajaxRequest;
			}catch(e){
				$("#non-supported-ajax").addClass("active");
				return null;
			}
		}
	}
}
