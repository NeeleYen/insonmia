<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>專案上限到達已關閉</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h3>專案上限到達已關閉</h3>
		<h6>此專案訂單上限：${ maxOrderAmount }</h6>
		<h6>已有訂單數：${ currentOrderAmount }</h6>
		<h6>${ username }下的訂單數：${ amount }</h6>
		<a class="btn btn-dark" href="${ contextRoot }/">回首頁</a>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>