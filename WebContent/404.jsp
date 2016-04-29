<!DOCTYPE html>
<html>
<%! 
	String pagePath;// Page context path 
%>
<%
	pagePath = request.getContextPath(); 
%>
<head>
	<meta charset="UTF-8">
	<title>Resources Not Found</title>
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/grid.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/ionicons.min.css" rel="stylesheet"
		type="text/css">
		
	<link href="<%=pagePath%>/resources/css/setup.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/resources/css/404.css" rel="stylesheet"
		type="text/css">
		
	<link href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100'
		rel='stylesheet' type='text/css'>
</head>
<body>
	<section class="section-not-found">
		<div class="row">
			<div class="col span-2-of-9">&nbsp;</div>
			<div class="col span-1-of-9 ">
				<div class="icon ion-sad-outline icon-404" id="icon-404"></div>
			</div>
			<div class="col span-4-of-9 message">
				<div>
					<p class="error">Error 404</p>
					<br>
					<p>The requested resource is not available.</p>
				</div>
			</div>
			<div class="col span-2-of-9">&nbsp;</div>
		</div>
	</section>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
	<script>
		$('#icon-404').hover(
			function(){
				$(this).removeClass('ion-sad-outline').addClass('ion-happy-outline');
			},
			function(){
				$(this).removeClass('ion-happy-outline').addClass('ion-sad-outline');
			}
		
		
		);
		
		
		
	
	</script>
</body>
</html>