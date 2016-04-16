<%@ page import="com.yabe.util.Utils,com.yabe.model.*"%>
<%! 
	String pagePath;// Page context path 
%>
<%
	// Page request parameter
	pagePath = request.getContextPath(); 
	User user = (User)request.getAttribute("user");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YABE - Homepage</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
	type="text/css">
<link href="<%=pagePath%>/vendors/css/grid.css" rel="stylesheet"
	type="text/css">
<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
	type="text/css">

<link href="<%=pagePath%>/resources/css/setup.css" rel="stylesheet"
	type="text/css">
<link href="<%=pagePath%>/resources/css/homepage.css" rel="stylesheet"
	type="text/css">
<link
	href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100'
	rel='stylesheet' type='text/css'>
</head>
<body>
	
	
</body>
</html>