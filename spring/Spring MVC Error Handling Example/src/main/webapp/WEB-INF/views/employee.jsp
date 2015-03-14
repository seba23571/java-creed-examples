<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Java Creed | Spring MVC Error Handling Example - Employee Page</title>
	</head>
	<body>
		<h1>Employee Details</h1>
		<table>
			<tr><th>Id</th><td>${employee.id}</td></tr>
			<tr><th>Name</th><td>${employee.name}</td></tr>
			<tr><th>Department</th><td>${employee.department}</td></tr>
		</table>
	</body>
</html>