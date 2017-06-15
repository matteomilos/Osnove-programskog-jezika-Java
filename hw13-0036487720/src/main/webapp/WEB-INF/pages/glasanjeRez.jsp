<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor="${pickedBgCol}">

	<a href="/webapp2">Homepage</a>
	<br>
	<h1>Voting results</h1>
	<p>These are voting results.</p>
	<table border="1" class="rez">
		<tr>
			<th>Band</th>
			<th>Number of votes</th>
		</tr>
		<c:forEach var="score" items="${results}">
			<tr>
				<td>${bands[score.key - 1].name}</td>
				<td>${score.value}</td>
			</tr>
		</c:forEach>
	</table>

	<h2>Graphical display of the results</h2>
	<img alt="Pie-chart" src="/webapp2/glasanje-grafika" />

	<h2>Results in XLS format</h2>
	<p>
		Results in XLS format are available for download <a
			href="glasanje-xls">here</a>.
	</p>

	<h2>Other</h2>
	<p>Examples of songs of winning bands</p>
	<ul>
		<c:forEach var="id" items="${winning}">
			<li><a href="${bands[id].song}">${bands[id].name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>