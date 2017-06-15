
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<a href="/webapp2">Homepage</a>
	<br>
	<br>
	<table border="1">
		<tr>
			<th>Sin(x)</th>
			<th>Cos(x)</th>
			<th>x</th>
		</tr>
		<c:forEach var="item" items="${results}">

			<tr>
				<td>${item.value.sinus}</td>
				<td>${item.value.cosinus}</td>
				<td>${item.key}</td>
			</tr>


		</c:forEach>
	</table>


</body>
</html>