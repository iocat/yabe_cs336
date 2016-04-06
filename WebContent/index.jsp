<!DOCTYPE html>
<html>
	<head lang="en-US">
		<title>Login</title>
		<meta name="viewport" content="max-width=device-width, initial-scale=1.0"/>
		<link href="${pageContext.request.contextPath}/vendors/css/normalize.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/vendors/css/grid.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/vendors/css/normalize.css" rel="stylesheet" type="text/css">
		
		<link href="${pageContext.request.contextPath}/resources/css/setup.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css">
		<link href='https://fonts.googleapis.com/css?family=Lato:400,700,300,100' rel='stylesheet' type='text/css'>
	</head>
	<body>
		<header>
			<div class="logo"><img src="${pageContext.request.contextPath}/resources/img/yabe-logo.png"></div>
		</header>
		<section class="login-section">
			<div class="row">
				<div class="col span-1-of-4">
					<!--  blank div -->
				</div>
				<div class="col span-3-of-4 container">
					<form method="POST" action="/homepage">
						<div class="row">
							<h1>Yabe</h1>
						</div>
						<div class= "row signinfield">
							<div class="col span-1-of-3">
								<label for="username">Username</label>
							</div>
							<div class="col span-2-of-3">
								<input type="text" name="username" placeholder="Happy user" required>
							</div>
						</div>
						<div class= "row signinfield">
							<div class="col span-1-of-3">
								<label for="password">Password</label>
							</div>
							<div class="col span-2-of-3">
								<input type="password" name="password" required>
							</div>
						</div>
						<input class=" btn btn-important" type="submit" value="Sign In">
						<a href="${pageContext.request.contextPath}/new-account.jsp" class="register-link"> Create a new Account ?</a>
					</form>
				</div>
			</div>
		</section>
		<footer>
			<p> Copyright &copy; 2016 YABE. All Rights Reserved.</p>
		</footer>
	</body>
</html>