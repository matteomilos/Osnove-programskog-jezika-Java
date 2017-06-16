<html>
<head></head>
<body>

	${sessionScope["registrationError"]}
	<form action="register" method="post">
		First name: <input type="text" name="name"> <br> Last
		name: <input type="text" name="surname"> <br> Email: <input
			type="email" name="email"> <br> Nickname: <input
			type="text" name="nickname"> <br> Password: <input
			type="password" name="pass"> <br>
		<button type="submit">Register</button>
	</form>

	<p>
		<a href="/blog/">Go to Homepage</a>
</body>

</html>