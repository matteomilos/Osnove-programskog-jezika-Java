<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
</head>
<body>
	<a href="index.html">Homepage</a>
	<br>
	<h1>Voting results</h1>
	<p>These are voting results.</p>
	<table border="1">
		<tr>
			<th>Band</th>
			<th>Number of votes</th>
		</tr>
		<c:forEach var="choice" items="${choices}">
			<tr>
				<td align="center">${choice.name}</td>
				<td align="center">${choice.score}</td>
			</tr>
		</c:forEach>
	</table>

	<h2>Graphical display of the results</h2>
	<img alt="Pie-chart" src="glasanje-grafika" />

	<h2>Results in XLS format</h2>
	<p>
		Results in XLS format are available for download <a
			href="glasanje-xls">here</a>.
	</p>

	<h2>Other</h2>
	<p>Examples of winning choices</p>
	<ul>
		<c:forEach var="choice" items="${winning}">
			<li><a href="${choice.link}">${choice.name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>