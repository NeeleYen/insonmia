<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <!-- css -->
    <link rel="stylesheet" href="${contextRoot}/css/style1.css">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
  <header>
  <span style="position: relative; top: -10px;">
	<a href="${contextRoot}/" class="logo" ><img src="${contextRoot}/images/logoEye.jpg"></a>
	</span>
        <nav class="">
			<span>
			<%-- 這邊href要放controller的URL --%>
				<c:if test="${pageContext.request.userPrincipal == null}">
              		<a href="${contextRoot}/public/user/add" class="btn btn-outline-dark text-white">註冊</a>				
					<a href="${contextRoot}/public/user/sign" class="btn btn-dark">登入</a>				
	                <a class="btn btn-outline-dark text-white" href="${contextRoot}/public/user/forget">忘記密碼</a>
				</c:if>

				<c:if test="${pageContext.request.userPrincipal != null}">
              		<a href="${contextRoot}/public/user/add" class="btn btn-outline-dark text-white">新增其他帳戶</a>				
	                <a class="btn btn-dark" href="${contextRoot}/logout">登出</a>
                	<a class="btn btn-dark" href="${contextRoot}/public/user/profile">會員資料</a>					
                	<a class="btn btn-dark" href="${contextRoot}/product">發起專案</a>					
                <!-- 會員照片 -->
				</c:if>
                <a class="btn btn-outline-dark text-white" href="${contextRoot}/public/faq/FAQ">常見問題</a>
				<c:if test="${pageContext.request.userPrincipal != null}">
                	<a href="${contextRoot}/user/icon">
					<img id="imgIcon" src="${ contextRoot }/public/user/avatar" alt="avatar" style="object-fit: cover;" width="80px" height="80px" class="landscape" />				
                	</a>
				</c:if>
            </span>
<!--             <div> -->
<!-- 				<p class="text-light">-登入時顯示為登出，登出時為登入-product都先放藝術類</p> -->
<!--             </div> -->

            <!-- 種類 -->
            <div class="option">
                <ul>
                	<c:forEach var="category" items="${ requestScope.entrySet }">   
                		<div class="mr-3"></div>
                    	<li><a href="${contextRoot}/public/category/${category.key}" class="text-white">${category.value}</a></li>                		             		
                	</c:forEach>
                
<%--                 	<c:forEach var="category" items="${ requestScope.entrySet }">                		 --%>
<%--                     	<li><h6>${category.value}</h6></li> --%>
<%--                     	<li><h6>${category.key}</h6></li> --%>
<%--                 	</c:forEach> --%>
                	
<%--                     <li><a href="${contextRoot}/public/category/2" class="text-white">藝術</a></li> --%>
<%--                     <li><a href="${contextRoot}/public/music" class="text-white">音樂</a></li> --%>
<%--                     <li><a href="${contextRoot}/public/category/1" class="text-white">動態音樂</a></li> --%>
<!--                     <li><a href="#" class="text-white">露營</a></li> -->
<!--                     <li><a href="#" class="text-white">衝浪</a></li> -->
<!--                     <li><a href="#" class="text-white">汽車</a></li> -->
<!--                     <li><a href="#" class="text-white">寵物</a></li> -->
<!--                     <li><a href="#" class="text-white">家具</a></li> -->
<!--                     <li><a href="#" class="text-white">建築</a></li> -->
                </ul>
            </div>
        </nav>
      </header>

	
</body>
</html>