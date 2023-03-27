<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>首頁</title>
</head>

<body>
	<jsp:include page="layout/navbar.jsp"></jsp:include>
	<h1>首頁</h1>
	<a href="${ contextRoot }/public/product/1">專案1</a>
	<br>
	<a href="${ contextRoot }/public/product/2">專案2</a>
	<br>
	<a href="${ contextRoot }/public/product/12">專案12(有購買方案)</a>

	<c:if test="${pageContext.request.userPrincipal != null}">
		<div>
			<img src="${ contextRoot }/public/user/avatar" alt="avatar" width="80px" height="80px" />
		</div>
	</c:if>
  <a id="homeBtn" href="${contextRoot}/public/homePage">home</a>

  <script>
    const homeBtn = document.getElementById("homeBtn");
    homeBtn.addEventListener('click', function(e){

    })
  </script>
</body>

</html>