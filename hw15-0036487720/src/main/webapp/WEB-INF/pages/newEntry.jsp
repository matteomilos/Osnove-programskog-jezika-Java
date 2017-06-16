<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">

			<c:if test="${empty entry}">
				<form method="post" action="/blog/servleti/createNew">

					<input type="hidden" name="creator"
						value="${sessionScope['current.user.nick']}"> Title: <input
						name="title"> <br> Message:
					<textarea name="text"></textarea>

					<br> <input type="submit">
				</form>
			</c:if>

			<br>
			<a href="/blog/">Go back to homepage</a>
		</c:when>
		<c:otherwise>
			<br>
			<a href="/blog/">Go back to homepage</a>
		</c:otherwise>
	</c:choose>
</body>
</html>