<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%! // Page context path
	String pagePath;
%>
<%
	pagePath = request.getContextPath();
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
	<%@ page import="com.yabe.Utils"%>
	<%! 	
			// Page request parameter
			String username = new String();
			String password = new String();
			boolean keepedIn = false;
			
			void pageStateInit( HttpServletRequest request){
				if ( request.getParameter("keepSignedIn").equals("true") ){
					this.keepedIn = true;
				}else{
					this.keepedIn =  false;
				}
				this.username = request.getParameter("username");
				this.password = request.getParameter("password");
			}
	%>
	<% 
		pageStateInit(request);
		if(session.isNew()){
			if (Utils.isEmpty(username) || Utils.isEmpty(password)){
				response.sendRedirect("index.jsp");
				return;
			}
			
			// check username and password in the database: if there is no username or password move user back to the index
			// if ok: go on
			// store user name in cookies
			
			// if not: go back to the previous one.
		}else{
			
		}
	%>
</body>
</html>