<%@ page import="com.yabe.util.Utils, com.yabe.model.*"%>
    
<%! 
	// Page variable declaration 
	String pagePath;				// Page context path
	Account user;
%>
<%	
	// Page variable initialization
	pagePath = request.getContextPath();
	user = (User)request.getAttribute("account");
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
		<div class="row">
		<div class="create-auction-box container">
		<form method="POST" action="<%=pagePath%>/create-auction">
				<input name="seller" value="<%=user.getUsername() %>" type="hidden"> 
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="isDesktop">Check here for Desktop</label>
					</div>
					<div class="col span-2-of-3">
						<input type="checkbox" name="isDesktop" id="isDesktop" value="true">
					</div>
				</div>
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
						<input type="text" name="openDate" id="opened-date-picker"
						placeholder="Pick Date" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3"><label for="datepicker">Close Date</label></div> 
					<div class="col span-2-of-3 item-filter-input">
						<input type="text" name="closeDate" id="closed-date-picker"
						placeholder="Pick Date" required>
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
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="Condition">Condition</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="cond" id="cond"
						placeholder="e.g. New, Used..." required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="manufacturer">Manufacturer </label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="manufacturer" id="manufacturer"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="description">Item Description</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="description" id="description"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="ram">RAM</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="ram" id="ram"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="brandName">Brand Name</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="brandName" id="brandName"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="weight">Weight</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="weight" id="weight"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="operatingSystem">Operating System</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="operatingSystem" id="operatingSystem"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="screenType">Screen Type</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="screenType" id="screenType"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="screenWidth">Screen Width</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="screenWidth" id="screenWidth"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="screenHeight">Screen Height</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="screenHeight" id="screenHeight"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="screenResolutionX">Screen Resolution X</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="screenResolutionX" id="screenResolutionX"
						placeholder="X-Axis" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="screenResolutionY">Screen Resolution Y</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="screenResolutionY" id="screenResolutionY"
						placeholder="Y-Axis" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="sizeWidth">Size Width</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="sizeWidth" id="sizeWidth"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="sizeHeight">Size Height</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="sizeHeight" id="sizeHeight"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="sizeDepth">Size Depth</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="sizeDepth" id="sizeDepth"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="color">Color</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="color" id="color"
						placeholder="" required>
					</div>
				</div>
				<div class="row create-auc-field">
					<div class="col span-1-of-3">
						<label for="batteryCapacity">Battery Capacity</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="batteryCapacity" id="batteryCapacity"
						placeholder="" required>
					</div>
				</div>
				<!--
				<div class="row handheld-field">
					<div class="col span-1-of-3">
						<label for="isTablet">Tablet?</label>
					</div>
					<div class="col span-2-of-3">
						<input type="checkbox" name="isTablet" id="isTablet" required>
					</div>
				</div>
				<div class="row handheld-field">
					<div class="col span-1-of-3">
						<label for="externalMemoryType ">Ext. Memory Type</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="externalMemoryType" id="externalMemoryType"
						placeholder="" required>
					</div>
				</div>
				<div class="row handheld-field">
					<div class="col span-1-of-3">
						<label for="externalMemoryMaxSize ">Max Memory Size</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="externalMemoryMaxSize" id="externalMemoryMaxSize"
						placeholder="" required>
					</div>
				</div>
				<div class="row handheld-field">
					<div class="col span-1-of-3">
						<label for="networkProvider">Network Provider</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="networkProvider" id="networkProvider"
						placeholder="" required>
					</div>
				</div>
				<div class="row handheld-field">
					<div class="col span-1-of-3">
						<label for="simType">Sim Card Type</label>
					</div>
					<div class="col span-2-of-3">
						<input type="text" name="simType" id="simType"
						placeholder="STANDARD,MICRO,NANO" required>
					</div>
				</div>	
				-->				
				<div class="row">
					<input type="submit" value="Create" class="btn btn-important">
				</div>
				</form>
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
