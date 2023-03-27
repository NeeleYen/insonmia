<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚無專案</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div class="col">
				<h3>此分類還沒有東西</h3>
				<h5>成為第一個賣家：</h5>
					<c:choose>
                	<c:when test="${pageContext.request.userPrincipal == null}">
                		<a href="${contextRoot}/public/user/sign">
                    		<input type="button" class="btn-bg btn btn-dark" value="發起專案">
               			</a>
                	</c:when>
                	<c:otherwise>                	
           		     <a href="${contextRoot}/product">
           		         <input type="button" class="btn-bg btn btn-dark" value="發起專案">
            	     </a>
                	</c:otherwise>
                </c:choose>					
				
			</div>
		</div>
	</div>
	
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>