
$(document).ready(function(){
	var $maxBidRangePicker = $('#maxBidRangePicker');
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
	
	var $ramPicker = $('#ramPicker');
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
	
	new Pikaday({ field: $('#opened-date-picker')[0] });
	new Pikaday({ field: $('#closed-date-picker')[0] });
	
	// FILTER ITEM AJAX REQUEST
	var filterItemAjaxRequest = getAjax();
	filterItemAjaxRequest.onreadystatechange = function(){
		// Receive the data from server
		if (filterItemAjaxRequest.readyState === 4){
			// Show the result
			$('#results').html("");
			var items = JSON.parse(filterItemAjaxRequest.responseText)["items"];
			if(items.length===0){
				$('#results').html("<div class=\"row\" style=\"margin-top: 100px;\">There is no items.</div>");
				return;
			}
			for( var i = 0; i < items.length ; i++){
				$('.section-result>.container').append(
					"<div class=\"res-item col span-1-of-3\">" + 
						"<a href=\""+ items[i]["url"]+" \">"+
							"<div class=\"item-img\">" +
								"<img class=\"item-image\" src=\""+ items[i]["pictureURL"] + " \">"+
							"</div>"+
							"<p class=\"item-name\">"+items[i]["name"]+"</p>"+
						"</a>"+
					"</div>");
			}
		}
	};
	// Bind the ajax item Request to the submit button
	$('#item-filter-submit').click(function(){
		var query = "";
		var itemName;
		if ((itemName = $('#item-name').val()) != ""){
			query += "name="+itemName+"&";
		}
		
		$("input[name^='cond']").each(function(){
			if($(this).is(':checked')){
				var str = $(this).attr('name');
				var strM = str.match(/cond(.*)/);
				query+= "condition="+ strM[1] +"&";
			}
		});

		$("input[name^='brand']").each(function(){
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
		query += "min-bid="+$maxBidRangePicker.nstSlider('get_current_min_value')+"&";
		query += "max-bid="+$maxBidRangePicker.nstSlider('get_current_max_value')+"&";
		query += "min-increment="+$minIncPicker.nstSlider('get_current_min_value')+"&";
		query += "min-ram="+$ramPicker.nstSlider('get_current_min_value')+"&";
		query += "desktop="+$('#desktop').is(':checked')+"&";
		query +="laptop="+ $('#laptop').is(':checked')+"&";
		query += "tablet="+ $('#tablet').is(':checked')+"&";
		query += "smartphone="+$('#smartphone').is(':checked');
		filterItemAjaxRequest.open("POST",  "filter/item", true);
		filterItemAjaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		filterItemAjaxRequest.send(encodeURI(query));
		
	});
	
	// FILTER USER AJAX REQUEST
	var filterUserAjaxRequest = getAjax();
	filterUserAjaxRequest = getAjax();
	filterUserAjaxRequest.onreadystatechange = function(){
		if (filterUserAjaxRequest.readyState === 4){
			$('.section-result>.container').html("");
			var users = JSON.parse(filterUserAjaxRequest.responseText)["users"];
			if(users.length==0){
				$('#results').html("<div class=\"row\" style=\"margin-top: 100px;\">There is no users.</div>");
				return;
			}
			for( var i = 0 ; i < users.length; i++){
				$('#results').append(
					"<div class=\"res-user col span-1-of-4 \">"+
						"<a href=\""+users[i]["url"]+"\">"+
							"<div class=\"res-user-img\">" +
								"<img src=\""+ users[i]["profile-picture"] +"\"\>"+
							"</div>"+
							"<p class=\"res-user-name\">"+
									users[i]["name"]+
							"</p>"+
						"</a>"+
					"</div>"	
				);
			}
		}
	};
	
	// Bind the ajax user request to the submit button
	$('#user-filter-submit').click(function(){
		var query = "name="+$('#name').val();
		filterUserAjaxRequest.open("GET","filter/user?"+query,true);
		filterUserAjaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		filterUserAjaxRequest.send();
	});
	
	// Initiate the page
	$('#item-filter-submit').trigger("click");
	
	$('#item-filter-tab').click(function(){
		$('#item-filter-submit').trigger("click");
	});
	
	$('#user-filter-tab').click(function(){
		$('#user-filter-submit').trigger("click");
	});
});