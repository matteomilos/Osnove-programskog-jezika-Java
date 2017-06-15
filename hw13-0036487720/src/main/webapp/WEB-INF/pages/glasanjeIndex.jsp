<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<a href="/webapp2">Homepage</a>
	<br>
	<h1>Voting for favourite band:</h1>
	<p>Of the following bands, which is Your favourite? Click on the
		link to vote!</p>
	<ol>
		<c:forEach var="band" items="${bands}">
			<li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>