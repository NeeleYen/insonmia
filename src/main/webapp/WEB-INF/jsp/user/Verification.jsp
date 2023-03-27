<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>驗證成功</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class="mt-5 mb-4">恭喜你驗證成功，請重新登入</h1>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	<script type="text/javascript">
		setTimeout(function () {
	        window.location.href = "${contextRoot}/logout";
	    }, 5000);
	</script>
	
</body>
</html>