<%@ page import="com.yabe.util.Utils,com.yabe.model.*"%>
<%! 
	String pagePath;// Page context path 
%>
<%
	// Page request parameter
	Account acc = (Account) request.getAttribute("account");
	pagePath = request.getContextPath(); 
%>


<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>YABE - Homepage</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/grid.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/jquery.nstSlider.min.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/pikaday.css" rel="stylesheet"
		type="text/css">
		
		
	<link href="<%=pagePath%>/resources/css/setup.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/resources/css/main-page.css" rel="stylesheet"
		type="text/css">
	
	<link href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100'
		rel='stylesheet' type='text/css'>
</head>
<body>
	<header>
		<div class="logo">
			<img src="<%=pagePath%>/resources/img/yabe-logo.png">
		</div>
		<nav class="row main-nav">
			<ul>
				<%if(acc instanceof User){%>
					<li class="nav-elem sign-out"> <a href="sign-out">Sign out</a></li>
					<li class="nav-elem user-name"> <a href="#">Hi, <%= ((User) acc).getName() %>!</a></li>
				<%}else if(!(acc instanceof User || acc instanceof Representative)){%>
					<li class="nav-elem sign-in"><a href="login.jsp">Sign In</a>
				<%}%>
			</ul>
		</nav>
	</header>
	
	<div class="clearfix">
		<section class = "section-filter ">
			<% if(acc instanceof User){ %>
			<div class="section-filter-elem create-auction">
				<a class="btn-important" href="<%=pagePath%>/create-auction.jsp">Create New Auction</a>
			</div>
			<div class="section-filter-elem create-alert">
				<a class="btn-important" href="<%=pagePath%>/create-alert.jsp">Create Alert</a>
			</div>
			<%} %>
			<div class="section-filter-elem container-with-tabs">
				<ul>
					<li class="active" data-show="item-filter" id="item-filter-tab">Item </li>
					<li data-show="user-filter"  id="user-filter-tab">User</li>
				</ul>
					
				<div class="container user-filter hidden" id="user-filter">
					<div class="row filter-sub-section">
						<div class="col span-1-of-3"><label for="name">Name</label></div>
						<div class="col span-2-of-3"><input type="text" name="name" id="name" placeholder=""/></div>
					</div>
					<div class="row">
						<input type="submit" class="btn search-btn" value="Find user" id="user-filter-submit">
					</div>
				</div>
				
				<div class="container" id="item-filter">
					<div class ="item-filter" > 
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label for="item-name">Item's name</label></div>
							<div class="col span-2-of-3"><input type="text" id="item-name" name="item-name" placeholder=""/></div>
						</div>
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label>Category</label></div>
							<div class="col span-1-of-3 item-filter-input">
								<div class="category-fields">
									<input type="checkbox" name="desktop" value="true" id="desktop"> <label for="desktop">Desktop</label>
								</div>
								<div class="category-fields">
									<input type="checkbox" name="laptop" value="true" id = "laptop" > <label for="laptop">Laptop</label>
								</div>
							</div>
							<div class="col span-1-of-3 item-filter-input">
								
								<div class="category-fields">
									<input type="checkbox" name="tablet" value="true" id="tablet"><label for="tablet">Tablet</label>
								</div>
								<div class="category-fields">
									<input type="checkbox" name="smartphone" value="true" id="smartphone"> <label for="smartphone">Smartphone</label>
								</div>
							</div>
							
						</div>
						<div class="row filter-sub-section">
							<div class="row">
								<div class="col span-3-of-3 clearfix"><label>Maximum Bid Range</label></div> 
								
							</div>
							<div class="row item-filter-range">
								<div class="row nstSlider rangeSlider" id="maxBidRangePicker" data-range_min="0" data-range_max="5000" 
	                       			 data-cur_min="0"    data-cur_max="4000" >
	
	    							<div class="bar"></div>
	    							<div class="leftGrip"><div class="leftLabel" ></div></div>
	   								<div class="rightGrip"><div class="rightLabel" ></div></div>
								</div>
							</div>
						</div>
						<div class="row filter-sub-section">
							<div class="row">
								<div class="col span-2-of-3 clearfix"><label>Minimum Increment<br>less than</label></div> 
								<div class="col span-1-of-3 " ><span>&nbsp;</span></div>
							</div>
							<div class="nstSlider singleSlider" data-range_min="0" data-range_max="1000"
	                       				data-cur_min="400" data-cur_max="0" id="minIncPicker" >
	    						<div class="bar"></div>
	    						<div class="leftGrip"></div>
							</div>
							<div class="leftLabel" ></div>
						</div>
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label for="conditionPicker">Condition</label></div>
							<div class="col span-2-of-3  item-filter-input">
								<div>
									<input type="checkbox" name="condNew" value="true" id="condNew" > <label for="condNew">New</label>
								</div>
								<div>
									<input type="checkbox" name="condNewOther" value="true" id="condNewOther" > <label for="condNewOther">New Other</label>
								</div>
								<div>
									<input type="checkbox" name="condManuRefur" value="true" id="condManuRefur" > <label for="condManuRefur">Manufacturer Refurbished</label>
								</div>
								<div>
									<input type="checkbox" name="condSellRefur" value="true" id="condSellRefur" > <label for="condSellRefur">Sell Refurbished</label>
								</div>
								<div>
									<input type="checkbox" name="condUsed" value="true" id="condUsed" > <label for="condUsed">Used</label>
								</div>
							</div>
						</div>
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label for="datepicker">Opened Date</label></div> 
							<div class="col span-2-of-3 item-filter-input">
								<input type="text" name="opened-date" id="opened-date-picker">
							</div>
						</div>
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label for="datepicker">Closed Date</label></div> 
							<div class="col span-2-of-3 item-filter-input">
								<input type="text" name="closed-date" id="closed-date-picker">
							</div>
						</div>
						<div class="row filter-sub-section">
							<div class="col span-1-of-3"><label for="brandPicker">Brand</label></div>
							<div class="col span-1-of-3  item-filter-input">
								<div>
									<input type="checkbox" name="brandApple" value="true" id="brandApple" > <label for="brandApple">Apple</label>
								</div>
								<div>
									<input type="checkbox" name="brandSony" value="true" id="brandSony" > <label for="brandSony">Sony</label>
								</div>
								<div>
									<input type="checkbox" name="brandHP" value="true" id="brandHP" > <label for="brandHP">HP</label>
								</div>
								<div>
									<input type="checkbox" name="brandDell" value="true" id="brandDell" > <label for="brandDell">Dell</label>
								</div>
							</div>
							<div class="col span-1-of-3  item-filter-input">
								<div>
									<input type="checkbox" name="brandSamsung" value="true" id="brandSamsung" > <label for="brandSamsung">Samsung</label>
								</div>
								<div>
									<input type="checkbox" name="brandAsus" value="true" id="brandAsus" > <label for="brandAsus">Asus</label>
								</div>
								<div>
									<input type="checkbox" name="brandIBM" value="true" id="brandIBM" > <label for="brandIBM">IBM</label>
								</div>
								<div>
									<input type="checkbox" name="brandLenovo" value="true" id="brandLenovo" > <label for="brandLenovo">Lenovo</label>
								</div>
							</div>
						</div>
						<div class="row filter-sub-section">
						<div class="row">
							<div class="col span-1-of-3 clearfix"><label>Minimum RAM<br>greater than</label></div> 
							<div class="col span-2-of-3 " ><span>&nbsp;</span></div>
						</div>
						<div class="nstSlider singleSlider" data-range_min="0" data-range_max="64"
                       				data-cur_min="0" data-cur_max="0" id="ramPicker">
    						<div class="bar"></div>
    						<div class="leftGrip"></div>
						</div>
						<div class="leftLabel" ></div>
					</div>
					</div>
					<div class="row">
						<a href="#section-result"><input type="submit" class="btn search-btn" value="Search" id="item-filter-submit"></a>
					</div>
				</div>
			</div>
		</section>
		<section class="section-result" id="section-result">
			<div class="container clearfix" id="results">
				<!-- Repetitive ( 3 times ) -->
				<div class="row">
					<div class="col">
					
					
					</div>
				</div>
				<!-- End -->
				<!-- Multiple buttons -->
				<div class="row">
					
				</div>
				<!-- End -->
			</div>
		
		</section>
	</div>
	<div class="popup alert" id="non-supported-ajax">
		<h6>This browser does not support the needed technology.
		Please consider update your browser or choose another browser </h6>
	</div>
	<footer>
		<p>Copyright &copy; 2016 YABE. All Rights Reserved.</p>
	</footer>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script src="<%=pagePath %>/vendors/js/jquery.nstSlider.min.js"></script>
	<script src="<%=pagePath %>/vendors/js/pikaday.js"></script>
	<script src="<%=pagePath %>/resources/js/setup.js"> </script>
	<script src="<%=pagePath %>/resources/js/main-page.js"></script>
	
	
</body>
</html>