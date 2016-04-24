
$(document).ready(function(){
	var $maxBidRangePicker = $('#maxBidRangePicker')
	$maxBidRangePicker.nstSlider({
	    "rounding": {
	        "100": "1000",
	    },
	    "crossable_handles": false,
	    "left_grip_selector": ".leftGrip",
	    "right_grip_selector": ".rightGrip",
	    "value_bar_selector": ".bar",
	    "value_changed_callback": function(cause, leftValue, rightValue) {
	        var $container = $(this).parent();
	        $container.find('.leftLabel').text('$'+leftValue);
	        $container.find('.rightLabel').text('$'+rightValue);
	    }
	});
	
	var $ramPicker = $('#ramPicker')
	$ramPicker.nstSlider({
		"rounding":{
			"2":"4",
			"4":"8",
			"8":"16",
			"16":"32",
			"32":"64",
			"64":"128",
		},
	    "left_grip_selector": ".leftGrip",
	    "value_bar_selector": ".bar",
	    "value_changed_callback": function(cause, leftValue, rightValue) {
	        var $container = $(this).parent(),
	            g = 255 - 127 + leftValue,
	            r = 255 - g,
	            b = 0;
	        $container.find('.leftLabel').text(leftValue);
	    },
	});
	
	$minIncPicker = $('#minIncPicker');
	var minBidPickParameter = {
		    "left_grip_selector": ".leftGrip",
		    "value_bar_selector": ".bar",
		    "value_changed_callback": function(cause, leftValue, rightValue) {
		        var $container = $(this).parent(),
		            g = 255 - 127 + leftValue,
		            r = 255 - g,
		            b = 0;
		        $container.find('.leftLabel').text('$'+leftValue);
		    }
	};
	$minIncPicker.nstSlider(minBidPickParameter);
	
	var picker = new Pikaday({ field: $('#opened-date-picker')[0] });
	var picker2 = new Pikaday({ field: $('#closed-date-picker')[0] });
	
	// Get the ajax Request object for item filter
	var filterItemAjaxRequest = getAjax();
	//Configure the ajax Request 
	filterItemAjaxRequest.onreadystatechange = function(){
		// Receive the data from server
		if (filterItemAjaxRequest.readyState === 4){
			// Show the result
		}
	};
	
	// Bind the ajax item Request to the submit button
	$('#item-filter-submit').click(function(){
		var query = "";
		var itemName;
		if ((itemName = $('#item-name').val()) != ""){
			query += "name="+itemName+"&";
		}
		query += "min-bid="+$maxBidRangePicker.nstSlider('get_current_min_value')+"&";
		query += "max-bid="+$maxBidRangePicker.nstSlider('get_current_max_value')+"&";
		query += "min-increment="+$minIncPicker.nstSlider('get_current_min_value')+"&";
		query += "min-ram="+$ramPicker.nstSlider('get_current_min_value')+"&";
		query +="desktop="+$('#desktop').is(':checked')+"&";
		query +="laptop="+ $('#laptop').is(':checked')+"&";
		query += "tablet="+ $('#tablet').is(':checked')+"&";
		query += "smartphone="+$('#smartphone').is(':checked')+"&";
		$("input[name^='cond'").each(function(){
			if($(this).is(':checked')){
				var str = $(this).attr('name');
				var strM = str.match(/cond(.*)/);
				query+= "condition="+ strM[1] +"&";
			}
		});

		$("input[name^='brand'").each(function(){
			if($(this).is(':checked')){
				var str = $(this).attr('name');
				var strM = str.match(/brand(.*)/);
				query+= "brand="+ strM[1] +"&";
			}
		});
		var openedDate = $('#opened-date-picker').val();
		if (openedDate !== ""){
			query += "opened-date=" + openedDate+"&";
		}
		var closedDate = $('#closed-date-picker').val();
		if (closedDate !== ""){
			query += "closed-date="+closedDate+"&";
		}
		alert(query);
		filterItemAjaxRequest.open("POST",  "filter/item", true);
		filterItemAjaxRequest.send(encodeURI(query));
	})
	
	
	var filterUserAjaxRequest = getAjax();
	filterUserAjaxRequest = getAjax();
	filterUserAjaxRequest.onreadystatechange = function(){
		if (filterUserAjaxRequest.readyState === 4){
			// Show the Result
		}
	};
	
	// Bind the ajax user request to the submit button
	$('#user-filter-submit').click(function(){
		alert("usersearch");
		filterUserAjaxRequest.open("GET","filter/user",true)
		filterUserAjaxRequest.send(/* Add send parameter*/)
	})
	
});