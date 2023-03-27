<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登入成功</title>
</head>
<body>
<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class= "mt-5 mb-4">修改成功</h1>
	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>

</body>
</html>