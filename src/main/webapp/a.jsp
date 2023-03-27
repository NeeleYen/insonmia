<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>提交</h1>
	<form action="/my-insomnia/user/logIn" method="post">
		信箱:<input type="text" name="username" />
		<br /> 
		密碼:<input type="password" name="password" />
		<br /> 
		<input type="submit" value="login" />
	</form>

</body>
</html>