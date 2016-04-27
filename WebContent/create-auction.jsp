<%@ page import="com.yabe.util.Utils, com.yabe.model.*"%>
    
<%! 
	// Page variable declaration 
	String pagePath;				// Page context path
%>
<%	
	// Page variable initialization
	pagePath = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>YABE - Create a new Auction</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/grid.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/pikaday.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/resources/css/setup.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/resources/css/create-auction.css"
		rel="stylesheet" type="text/css">
	<link
		href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100'
	rel='stylesheet' type='text/css'>
</head>

<body>
	<header>
		<div class="logo">
			<img src="<%=pagePath%>/resources/img/yabe-logo.png">
		</div>
	</header>
	<section class="create-auction-section">
		<div class="row">
			<h1>Create New Auction</h1>
		</div>
		<div class="create-auction-box container">
			<div class="row">
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="name">Item Name</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="name" id="name"
						placeholder="Place Item Name Here" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="minimumPrice">Secret Minimum Price</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="minimumPrice" id="minimumPrice"
						placeholder="Place Minimum Bid Price Here" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3"><label for="datepicker">Open Date</label></div> 
					<div class="col span-2-of-3 item-filter-input">
						<input type="text" name="openDate" id="opened-date-picker">
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3"><label for="datepicker">Close Date</label></div> 
					<div class="col span-2-of-3 item-filter-input">
						<input type="text" name="closeDate" id="closed-date-picker">
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="minimumIncrement">Minimum Increment</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="minimumIncrement" id="minimumIncrement"
						placeholder="" required>
					</div>
				</div>
				<div class="row">
					<input type="submit" value="Create" class="btn btn-important">
				</div>
			</div>
		</div>
	</section>
	
	<footer>
		<p>Copyright &copy; 2016 YABE. All Rights Reserved.</p>
	</footer>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script src="<%=pagePath %>/vendors/js/jquery.nstSlider.min.js"></script>
	<script src="<%=pagePath %>/vendors/js/pikaday.js"></script>
	<script src="<%=pagePath %>/resources/js/setup.js"> </script>
	<script src="<%=pagePath %>/resources/js/create-auction.js"></script>
</body>
</html>
