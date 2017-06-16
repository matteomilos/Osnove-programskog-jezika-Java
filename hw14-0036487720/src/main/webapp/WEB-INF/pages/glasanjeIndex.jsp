<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<a href="index.html">Homepage</a>
	<br>
	<h1>${title}</h1>
	<p>${message}</p>
	<ol>
		<c:forEach var="choice" items="${choices}">
			<li><a href="glasanje-glasaj?id=${choice.id}">${choice.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>