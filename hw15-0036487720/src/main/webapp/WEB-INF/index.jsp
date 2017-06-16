<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h2>Blog</h2>
	<c:choose>
		<c:when test="${empty sessionScope['current.user.id']}">
			${sessionScope.error}
			<c:remove var="error" scope="session" />
			<form action="login" method="post">
				Nick: <input type="text" name="nickname"> <br>
				Password: <input type="password" name="pass"> <br>
				<button type="submit">Login</button>
			</form>
			<a href="register">Register</a>
			<br>
		</c:when>
		<c:otherwise>
			Your nick is ${sessionScope["current.user.nick"]}, click the button down here to logout.
			<br>
			<a href="logout">Logout</a>
		</c:otherwise>
	</c:choose>
	<p>
		Registered Users:
		<c:forEach items="${registeredUsers}" var="user">
			<br>
			<a href="author/${user.nick}">${user.nick}</a>
		</c:forEach>
		<c:if test="${ empty registeredUsers }">
			<br>
			<i>There are no registered users</i>
		</c:if>
</body>
</html>
