<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />        
    
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>art page</title>
	<!-- css -->
<!--     <link rel="stylesheet" type="text/css" href="css/style1.css"> -->

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h3>藝術頁</h3>
		<a href="${ contextRoot }/public/product/1">專案1</a>
		<br>
		<a href="${ contextRoot }/public/product/2">專案2</a>
		<br>
		<a href="${ contextRoot }/public/product/5">專案5</a>
	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>