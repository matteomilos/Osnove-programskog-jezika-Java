<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<body bgcolor="${pickedBgCol}">
	<a href="colors">Background color chooser</a>
	<hr>
	<a href="trigonometric?a=0&b=90">Trigonometric</a>
	<hr>
	<form action="trigonometric" method="GET">
		Početni kut: <input type="number" name="a" min="0" max="360" step="1"
			value="0"><br>Završni kut: <input type="number" name="b"
			min="0" max="360" step="1" value="360"> <br> <input
			type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>
	<hr>
	<a href="stories/funny.jsp">Stories</a>
	<hr>
	<a href="report">Report</a>
	<hr>
	<a href="powers?a=1&b=100&n=3">Powers</a>
	<hr>
	<form action="powers" method="GET">
		First number: <input type="number" name="a" min="-100" max="100"
			step="1" value="0"><br>Second number: <input
			type="number" name="b" min="-100" max="100" step="1" value="0">
		<br> Number of pages: <input type="number" name="n" min="1"
			max="5" step="1" value="1"> <br> <input type="submit"
			value="Create XLS document"><input type="reset" value="Reset">
	</form>

	<hr>
	<a href="appinfo">AppInfo</a>
	<hr>
	<a href="glasanje">Voting</a>
</body>
</head>
</html>