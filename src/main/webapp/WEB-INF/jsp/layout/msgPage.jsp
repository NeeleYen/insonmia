<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訊息頁</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container text-center">

		<h3>${ message }</h3>
		<br>
		<a href="${ contextRoot }/" class="btn btn-dark">回首頁</a>	

	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>