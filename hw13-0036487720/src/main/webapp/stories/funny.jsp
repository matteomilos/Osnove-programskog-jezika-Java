
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true" import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<a href="/webapp2">Homepage</a>
	<br>
	<%
		String[] colors = new String[] {
				"black", "blue", "cyan", "darkGray", "gray", "green", "lightGray", "magenta", "orange", "pink", "red", "yellow"
		};
		Random rand = new Random();
	%>
	<font color=<%=colors[rand.nextInt(colors.length)]%>>A navy
		captain is alerted by his First Mate that there is a pirate ship
		coming towards his position. He asks a sailor to get him his red
		shirt.<br> The captain was asked, “Why do you need a red shirt?”
		<br>The Captain replies, “So that when I bleed, you guys don’t
		notice and aren’s discouraged.” <br>They fight off the pirates
		eventually.<br> The very next day, the Captain is alerted that 50
		pirate ships are coming towards their boat. He yells, “Get me my brown
		pants!”
	</font>


</body>
</html>