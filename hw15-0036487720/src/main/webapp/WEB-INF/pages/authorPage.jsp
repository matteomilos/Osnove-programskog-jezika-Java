<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<c:choose>
		<c:when test="${ empty blogEntries }">
			<i>There are no entries for this user.</i>
		</c:when>
		<c:otherwise>
			<p>Here comes list of entries</p>
			<c:forEach items="${blogEntries}" var="blogEntry">
				<a href="${nick}/${blogEntry.id}">${blogEntry.title}</a>
				<br>
			</c:forEach>
		</c:otherwise>
	</c:choose>




	<c:if test="${sessionScope['current.user.nick'] == nick}">
		<br>
		<a href="${nick}/new">Add a new blog entry</a>
	</c:if>

	<p>
		<br> <a href="/blog/">Go to Homepage</a>
</body>
</html>
