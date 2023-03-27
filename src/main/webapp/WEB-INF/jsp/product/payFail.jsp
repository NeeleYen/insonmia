<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>付款失敗頁</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
<!-- 		<h3>伺服端已接收到從用戶端(付款者)送出的付款通知(但付款資料有誤！)。</h3> -->
		<h2 class="text-center">付款資料有誤</h2>
		<h4 class="text-center">請至會員資料的查看訂單付款</h4>		
		<br>
		<a class="btn btn-dark position-absolute top-50 start-50 translate-middle" href="${ contextRoot }/">回首頁</a>
	</div>
 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>