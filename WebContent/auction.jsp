<%@ page import="com.yabe.util.Utils,com.yabe.model.*, java.util.ArrayList, java.time.format.DateTimeFormatter,java.time.LocalDateTime"%>
<%! 
	Account account;
	Auction auction;
	String pagePath;// Page context path 
	Computer computer;
	float minimumPossibleBid;
	AutoBid autoBid;
	DateTimeFormatter hourformat = DateTimeFormatter.ofPattern("HH:mm");
	DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
%>
<%
	// Page request parameter
	 account = (Account) request.getAttribute("account");
	 auction = (Auction) request.getAttribute("auction");
	 computer = (Computer) request.getAttribute("computer");
	pagePath = request.getContextPath(); 
	Bid maxBid;
	if ((maxBid=auction.getMaxBid()) != null){
		minimumPossibleBid = auction.getMaxBid().getAmount()+auction.getMinimumIncrement();
	}else{
		minimumPossibleBid = auction.getMinimumIncrement();
	}
	autoBid = new AutoBid();
	if(account instanceof User){
		autoBid.setBidder((User)account);
		autoBid.setItem(computer);
		autoBid.retrieve();
	}
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
	<link href="<%=pagePath%>/resources/css/auction.css" rel="stylesheet"
		type="text/css">
	
	<link href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100'
		rel='stylesheet' type='text/css'>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
	<header>
		<div class="logo">
			<a href="main-page.jsp">
				<img src="<%=pagePath%>/resources/img/yabe-logo.png">
			</a>
		</div>
		<nav class="row main-nav">
			<ul>
				<%if(account instanceof User){%>
					<li class="nav-elem sign-out"> <a href="sign-out">Sign out</a></li>
					<li class="nav-elem user-name"> <a href="#">Hi, <%= ((User) account).getName() %>!</a></li>
				<%}else if(!(account instanceof User || account instanceof Representative)){%>
					<li class="nav-elem sign-in"><a href="login.jsp">Sign In</a>
				<%}%>
			</ul>
		</nav>
	</header>
	<body>
		<section class="section-auction">
			<div class="container span-1-of-3 item-image-and-name">
					<div class="item-image"> <img src="<%= auction.getPictureURL() %>"></img> </div>
					<p class="item-name"> <%= auction.getName() %></p>
					
					<% if(!auction.isSold() ){ 
							if(account instanceof User && !(account != null && auction.getSeller().getUsername().equals(account.getUsername()))){%>
					<form action="bid" method="post" class="bid-form" >
						<input type="hidden" name="item-id" value="<%= auction.getItemId() %>">
						<div class="row ">
							<div class="col span-1-of-2 amount-label">
								<label for="bid-amount">Amount</label>
							</div>
							<div class="col span-1-of-2 bid-amount">
								<input type="text" name="bid-amount" 
								id="bid-amount"  value="<%= minimumPossibleBid %>" required>
							</div>
							<script>
							$(document).ready(function(){
								$('#bid-amount').on("keyup",function(){
									if($(this).val() < <%= minimumPossibleBid %>){
										$(this).css('border','1px solid #e53935');
									}else{
										$(this).css('border','1px solid #369660');
									}
								});
								$('#place-bid').click(function(){
									if( $('#bid-amount').val() < <%=minimumPossibleBid%> ){
										$('#bad-amount-alert').addClass('active');
									}else{
										$('#amount').html(+$('#bid-amount').val());
										$('#place-bid-confirmation').addClass('active');
									}
								});
								$("#set-auto-bid-param").val('false');
								$('#auto-bid').click(function(){
									if( $('#bid-amount').val() < <%=minimumPossibleBid%> ){
										$('#bad-amount-alert').addClass('active');
									}else{
										$("#set-auto-bid-param").val('true');
										$('#auto-bid-amount').html(+$('#bid-amount').val());
										$('#auto-bid-confirmation').addClass('active');
									}
								});
								$('#bad-amount-ignore').click(function(){
									$('#bad-amount-alert').removeClass('active');
								});
								$('#place-bid-ignore').add('#place-bid-confirmed').click(function(){
									$('#place-bid-confirmation').removeClass('active');
								});
								$('#place-auto-bid-confirmed').click(function(){
									$('#auto-bid-confirmation').removeClass('active');
								});
								$('#place-auto-bid-ignore').click(function(){
									$("#set-auto-bid-param").val('false');
									$('#auto-bid-confirmation').removeClass('active');
								});
								
							});
							</script>
						</div>
						<% if(autoBid.getMaxAmount()!=0){%>
							<div class="row">
								<div class="col span-1-of-2">
									<p>Auto Bid set</p>
								</div>
								<div class="col span-1-of-2 auto-amount">
									<p>
										$<%= autoBid.getMaxAmount() %> 
									</p>
								</div>
							</div>
						<% } %>
						<div class="row">
							<a class="btn make-bid-btn" id="place-bid">Place a Bid</a>
						</div>
						<div class="row">
							<a class="btn make-bid-btn" id="auto-bid">Set an Automatic Bid</a>
						</div>
						<!--  POP UP BOXES -->
						<div class="popup clearfix" id="place-bid-confirmation">
							<p class="prompt"> 
								Are you sure you want to bid on this item ? Your amount is $<span id="amount"></span>
							</p>
							
							<input type = "submit" id="place-bid-confirmed" class="btn btn-important place-bid-confirmed" value="Yes">
							<a class="btn" class="place-bid-ignore" id="place-bid-ignore">No</a>
						</div>
						
						<div class="popup clearfix" id="auto-bid-confirmation">
							<input id="set-auto-bid-param" type="hidden" name="set-auto" >
							<p class="prompt"> 
								Are you sure you want to AUTOMATICALLY bid on this item ? Your maximum amount is $<span id="auto-bid-amount"></span>
							</p>
							
							<input type = "submit" id="place-auto-bid-confirmed" class="btn btn-important place-bid-confirmed" value="Yes">
							<a class="btn" class="place-bid-ignore" id="place-auto-bid-ignore">No</a>
						</div>
						
						<div class="popup alert clearfix" id="bad-amount-alert">
							<p class="prompt"> Please put in a bid amount &ge; <%= minimumPossibleBid %> ? </p>
							<a class="btn" class="bad-amount-ignore" id="bad-amount-ignore">OK</a>
						</div>
					</form>
					<% 
							}else if( account!=null && auction.getSeller().getUsername().equals(account.getUsername())){
					%>
							<div class="row">
								<a href="contact-rep" class="btn make-bid-btn">Contact the Representative</a>
							</div>
					
					<%
							}else{
					%>
							<div class="row">
								<a href="login.jsp?item=<%=auction.getItemId()%>" class="btn make-bid-btn">Sign In To Bid</a>
							</div>
					<%
							}
						}else if(auction.isSold()){ 
					%>
						<div class="row">
							<div class="sold-tag"> Sold </div>
						</div>
					
					<% } %>
				</div>
			<div class="row">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<h5 class="section-name"> Auction Info</h5>
				</div>
			</div>
			<div class="row" >
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<div class=" container" style="<%if(auction.isSold()) {%> background-color:rgba(229,57,53,0.03); <% } %>">
						<div class="row permanent-info">
							<div class="col span-2-of-3 auction-info">
								<p class="info-label">Auction</p>
								<div class="row close-date">
									<div class="col span-1-of-2">
										<p>Open</p> 
									</div>
									<div class="col span-1-of-2">
										<p><%= auction.getOpenDate().toLocalDateTime().format(dateformat) %></p>
										<p><%= auction.getOpenDate().toLocalDateTime().format(hourformat) %></p>
									</div>
								</div>
								<div class="row open-date">
									<div class="col span-1-of-2">
										<p>Close</p> 
									</div>
									<div class="col span-1-of-2">
										<p><%= auction.getCloseDate().toLocalDateTime().format(dateformat) %></p>
										<p><%= auction.getCloseDate().toLocalDateTime().format(hourformat) %></p>
									</div>
								</div>
								<div class="row minInc">
									<div class="col span-1-of-2">
										<p>Increment</p> 
									</div>
									<div class="col span-1-of-2">
										<p>$ <%= auction.getMinimumIncrement() %></p>
									</div>
								</div>
							</div>
							<div class="col span-1-of-3 seller-info">
								<div class="row">
									<p class="info-label">Seller</p>
								</div>
								<a class="seller-link" href="user.jsp?uname=<%= auction.getSeller().getUsername() %>">
									<div class="row">
										<div class="seller-img">
											<img src="<%= auction.getSeller().getProfilePicture() %>" />
										</div>
									</div>
									<div class="row">
											<span class="seller-name"> <%= auction.getSeller().getName() %></span>
									</div>
								</a>
							</div>
						</div>
						<div class="row intermediate-info" id="intermediate-info">
							<% if(auction.isSold()){%>
							<div class="current-bid" id="current-bid">
								<span class="current-bid-label">Sold Price: </span>
								<span class="current-bid sold">
									<%= auction.getCurrentMaxBixAmount() %>
								</span>
							</div>
							<div class="row buyer-info">
								<div class="sold-date">Sold Date: <%= auction.getSoldTime().toString() %></div>
							</div>
							<% }else{ %>
							<div class="current-bid" id="current-bid">
								<span class="current-bid-label">Current Bid: </span>
								<span class="current-bid">
									$ <%= auction.getCurrentMaxBixAmount() %>
								</span>
							</div>
							<% } %>
						</div>
					</div>
				</div>
			</div>
		
		
		</section>
		<section class="section-item">
			<div class="row">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<div class="row">
						<h5 class="section-name">Item Information</h5>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<div class="container item">
						<div class="row item-info">
							<div class=" item-desc-value"><%= computer.getDescription() %></div>
						</div>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Brand</p></div>
							<div class="col span-1-of-2 item-info-value"><p><%= computer.getBrandName() %></p></div>
						</div>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Ram</p></div>
							<div class="col span-1-of-2 item-info-value"><p><%= computer.getRam() %> GB</p></div>
						</div>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Weight</p></div>
							<div class="col span-1-of-2 item-info-value"><p><%= computer.getWeight() %> lbs</p></div>
						</div>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Operating System</p></div>
							<div class="col span-1-of-2 item-info-value"><p><%= computer.getOperatingSystem() %></p></div>
						</div>
						<div class="row item-info">
							<div class="col span-1-of-3 item-info-field"> <p>Screen</p> </div>
							<div class="col span-2-of-3">
								<div class="row">
									<div class="col span-1-of-8">&nbsp;</div>
									<div class="col span-7-of-8">
										<div class="row item-info">
											<div class="col span-1-of-2 item-info-field"><p>Type</p></div>
											<div class="col span-1-of-2 item-info-value"><p><%= computer.getScreen().getType() %></p></div>
										</div>
										<div class="row item-info">
											<div class="col span-1-of-2 item-info-field"><p>Size</p></div>
											<div class="col span-1-of-2 item-info-value"><p><%= computer.getScreen().getWidth() %> &#215; <%= computer.getScreen().getHeight() %> </p></div>
										</div>
										<div class="row item-info">
											<div class="col span-1-of-2 item-info-field"><p>Resolution</p></div>
											<div class="col span-1-of-2 item-info-value">
												<p> <%= computer.getScreen().getResolutionX() %> &#215; <%= computer.getScreen().getResolutionY() %> </p>	
											</div>
										</div>
									</div>
									
								</div>
							</div>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Battery Capacity</p></div>
							<div class="col span-1-of-2 item-info-value"><p><%= computer.getBatteryCapacity() %></p></div>
						</div>
						</div>
						<% if( computer.getColor() != null ){ %>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Color</p></div>
							<div class="col span-1-of-2 item-info-value">
								<div style="width:30px; height:100%; background-color: #<%=computer.getColor()%>; margin: 0 auto;">
									&nbsp;
								</div>
							</div>
						</div>
						<% } %>
						<div class="row item-info">
							<div class="col span-1-of-2 item-info-field"><p>Size</p></div>
							<div class="col span-1-of-2 item-info-value">
								<p>
									<%= computer.getWidth() %> &#215; <%= computer.getHeight() %> &#215; <%= computer.getDepth() %>
								</p>
							</div>
						</div>
						
					</div>
				</div>
			
			</div>
		</section>
		<section class="section-bid-history" id="bid-history">
			<div class="row">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<h5 class="section-name">Bidding history</h5>
				</div>
			</div>
			<div class="row bidding-history">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3 bids">
				<% 	int i = 0;
					ArrayList<Bid> bids = auction.getBids();
					if( bids == null || bids.size() == 0){%>
						<div class="container">
							<p> There is no bid. </p>
						</div>
					<% }else{%>
						<div class="row col-name">
							<div class="col span-1-of-5">&nbsp;</div>
							<div class="col span-1-of-5"><p>Name</p></div>
							<div class="col span-1-of-5"><p>Amount</p></div>
							<div class="col span-1-of-5"><p>Time</p></div>
							<div class="col span-1-of-5"><p>Date</p></div>
						</div>
					<%
					
					for( Bid bid: auction.getBids() ){ 
						LocalDateTime time = bid.getTime().toLocalDateTime();
					%>
					<!-- Profile-picture name amount time -->
						<div class="row bid <%if(i==0){%>recent-bid<%}%>">
							<a href="<%= User.USER_PAGE_URL + bid.getBidder().getUsername() %>" class="user-link" >
							<div class="bid-elem col span-1-of-5">
								<div class="small-profile">
									<img src="<%= bid.getBidder().getProfilePicture() %>">
								</div>
							</div>
							<div class="bid-elem col span-1-of-5"><p><%= bid.getBidder().getName() %></p></div>
							<div class="bid-elem col span-1-of-5"><p>$<%= bid.getAmount() %></p></div>
							<div class="bid-elem col span-1-of-5"><p><%= time.format(hourformat) %></p></div>
							<div class="bid-elem col span-1-of-5"><p><%= time.format(dateformat) %></p></div>
							</a>
						</div>
					
					<div class="row link-bid <%if(i== auction.getBids().size()-1){ %>hidden<%}%>">
							<div class="col span-1-of-5 "><span class="link">&nbsp;</span></div>
					</div>
					<% 	i++;
						} 
					}%>
				</div>
			</div>
		</section>
	</body>
	
	
	<footer>
	
	
	</footer>
	<script src="<%= pagePath %>/vendors/js/jquery.waypoints.min.js"></script>
	<script src="<%= pagePath %>/resources/js/auction.js"></script>
	
</body>
</html>