<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.dz14.model.Poll"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>

	<h1>
		<b>Choose one of the following polls:</b>
	</h1>
	<br>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><h2>
					<a href="glasanje?pollid=${poll.id}">${poll.title}</a>
				</h2></li>

		</c:forEach>
	</ol>
</body>
</html>