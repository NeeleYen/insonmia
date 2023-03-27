<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />            
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>類型頁</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
<%-- 	<input type="hidden" id="popProductByCate" name="popProductByCate" value="${ popProductByCate }"> --%>
<%-- 	<input type="hidden" id="threeNewest" name="threeNewest" value="${ threeNewest }"> --%>
	
	<div class="container">
		<h2>${ categoryValue } page</h2>
		<h6>-${ categoryValue }頁簡介</h6>
		<br>
		<div class="row">
			<div class="col-6">
				<h5>精選專案(找支持人數最多的？+類型為1的一筆)</h5>
				<hr>
				<c:forEach var="pop" items="${ popthumbNailSet }"> 
					<img style="object-fit: cover;" alt="精選專案" src="${ contextRoot }${pop.filePath}" width="100px" height="100px">       
					<h5>
						<a class="text-dark" href="${ contextRoot }/public/product/${ pop.picId.productId.PId }">
							${ pop.picId.productId.title }
						</a>
					</h5>
				</c:forEach>
			</div>
			<div class="col">
				<h5>最新專案(找id最大+類型為1的三筆)</h5>
				<br>
				<c:forEach var="newest" items="${ newthumbNailSet }">
					<img style="object-fit: cover;" alt="精選專案" src="${ contextRoot }${newest.filePath}" width="100px" height="100px">
					<h5>
						<a class="text-dark" href="${ contextRoot }/public/product/${ newest.picId.productId.PId }">
							${ newest.picId.productId.title }
						</a> 
					</h5>
					<hr>				
				</c:forEach>
				<br>
			</div>
		</div>
	</div>
	
	<jsp:include page="../layout/footerForCategory.jsp"></jsp:include>
</body>
</html>