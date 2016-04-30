<%@ page import="com.yabe.util.Utils,com.yabe.model.*"%>
<%! 
	Account account;
	Auction auction;
	String pagePath;// Page context path 
	float minimumPossibleBid;
%>
<%
	// Page request parameter
	 account = (Account) request.getAttribute("account");
	 auction = (Auction) request.getAttribute("auction");
	pagePath = request.getContextPath(); 
	Bid maxBid;
	if ((maxBid=auction.getMaxBid()) != null){
		minimumPossibleBid = auction.getMaxBid().getAmount()+auction.getMinimumIncrement();
	}else{
		minimumPossibleBid = auction.getMinimumIncrement();
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
			<img src="<%=pagePath%>/resources/img/yabe-logo.png">
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
					<form action="bid" method="post" class="bid-form" >
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
								$('#bad-amount-ignore').click(function(){
									$('#bad-amount-alert').removeClass('active');
								});
								$('#place-bid-ignore').click(function(){
									$('#place-bid-confirmation').removeClass('active');
								});
							});
							</script>
						</div>
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
						
						<div class="popup alert clearfix" id="bad-amount-alert">
							<p class="prompt"> Please put in a bid amount &ge; <%= minimumPossibleBid %> ? </p>
							<a class="btn" class="bad-amount-ignore" id="bad-amount-ignore">OK</a>
						</div>
					</form>
					
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
					<div class=" container" style="<%if(auction.isSold()) {%> background-color:rgba(32,90,57,0.1); <% } %>">
						<div class="row permanent-info">
							<div class="col span-2-of-3 auction-info">
								<p class="info-label">Auction</p>
								<div class="open-date"><p>Close Date: <%= auction.getCloseDate().toString() %></p></div>
								<div class="close-date"><p>Open Date: <%= auction.getOpenDate().toString() %></p></div>
								
								<div class="minInc"><p>Minimum Increment: $ <%= auction.getMinimumIncrement() %></p></div>
							</div>
							<div class="col span-1-of-3 seller-info">
								<p class="info-label">Seller</p>
								<img src="<%= auction.getSeller().getProfilePicture() %>" />
								<a href="user.jsp?uname=<%= auction.getSeller().getUsername() %>">
									<p class="seller-name"> <%= auction.getSeller().getName() %></p>
								</a>
							</div>
						</div>
						<div class="row intermediate-info">
							<div class="current-bid">
								<span class="current-bid-label">Current Bid: </span>
								<span class="current-bid">
									<%= auction.getCurrentMaxBixAmount() %>
								</span>
							</div>
						</div>
						<% if(auction.isSold()){%>
						<div class="row buyer-info">
							<div class="sold-date">Sold Date: <%= auction.getSoldTime().toString() %></div>
						</div>
						<% } %>
					</div>
				</div>
			</div>
		
		
		</section>
		<section class="section-item">
			<div class="row">
				<div class="col span-1-of-3">&nbsp;</div>
				<div class="col span-2-of-3">
					<h5 class="section-name">Item Information</h5>
				</div>
			</div>
		</section>
		<section class="section-bid-history ">
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
					for( Bid bid: auction.getBids() ){ 
					%>
					<!-- Profile picture name amount time -->
					<a href="<%= User.USER_PAGE_URL + bid.getBidder().getUsername() %> " >
						<div class="row bid">
							<div class="bid-elem col span-1-of-4">
								<div class="small-profile">
									<img src="<%= bid.getBidder().getProfilePicture() %>">
								</div>
							</div>
							<div class="bid-elem col span-1-of-4"><p><%= bid.getBidder().getName() %></p></div>
							<div class="bid-elem col span-1-of-4"><p>$<%= bid.getAmount() %></p></div>
							<div class="bid-elem col span-1-of-4"><p><%= bid.getTime().toLocalDate() %></p></div>
						</div>
					</a>
					<div class="row link-bid <%if(i== auction.getBids().size()-1){ %>hidden<%}%>">
							<div class="col span-1-of-4 "><span class="link">&nbsp;</span></div>
					</div>
				<% 	i++;
					} %>
				</div>
			</div>
		</section>
	</body>
	
	
	<footer>
	
	
	</footer>
	
	<script src="<%=pagePath%>/resources/js/auction.js"></script>
</body>
</html>