<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單已取消</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<br>
		<h2 class="text-center">訂單已取消</h2>
		<a class="btn btn-dark position-absolute top-50 start-50 translate-middle" href="${ contextRoot }/">回首頁</a>
	</div>
<!-- 	<h3>訂單已取消</h3> -->
<%-- 	<a href="${contextRoot}/" class="btn btn-outline-dark">回首頁</a> --%>
	<jsp:include page="../layout/footer.jsp"></jsp:include>v
</body>
</html>