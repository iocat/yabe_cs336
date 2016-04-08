<!DOCTYPE html>
<html lang="en-US">
<head>
	<title>YABE - Create a new account</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%! /* Page context path*/ String pagePath;%>
	<% pagePath = request.getContextPath(); %>
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/grid.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/vendors/css/normalize.css" rel="stylesheet"
		type="text/css">

	<link href="<%=pagePath%>/resources/css/setup.css" rel="stylesheet"
		type="text/css">
	<link href="<%=pagePath%>/resources/css/new-account.css"
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
	<section class="new-account-section">
		<div class="row">
			<h1>Create new Account</h1>
		</div>
		<div class="row">
			<div class="new-account-box container ">
				<form method="POST" action="<%=pagePath%>/create-account.jsp">
					<div class="row new-acc-field">
						<div class="col span-1-of-3">
							<label for="username">Username</label>
						</div>
						<div class="col span-2-of-3">
							<input type="text" name="username" id="username"
								placeholder="e.g. y-bae" required>
						</div>
					</div>
					<div class="row new-acc-field">
						<div class="col span-1-of-3">
							<label for="password">Password</label>
						</div>
						<div class="col span-2-of-3">
							<input type="password" name="password" id="password"
								placeholder="Your Password" required>
						</div>
					</div>
					<div class="row new-acc-field">
						<div class="col span-1-of-3">
							<label for="name">Name</label>
						</div>
						<div class="col span-2-of-3">
							<input type="text" name="name" id="name"
								placeholder="e.g. John Doe" required>
						</div>
					</div>
					<div class="row new-acc-field">
						<div class="col span-1-of-3">
							<label for="email">Email</label>
						</div>
						<div class="col span-2-of-3">
							<input type="text" name="email" id="email"
								placeholder="y-bae@domain.com" required>
						</div>
					</div>
					<div class="row new-acc-field">
						<div class="col span-1-of-3">
							<label for="address">Address</label>
						</div>
						<div class="col span-2-of-3">
							<input type="text" name="address" id="address"
								placeholder="Your address" required>
						</div>
					</div>
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
</body>
</html>