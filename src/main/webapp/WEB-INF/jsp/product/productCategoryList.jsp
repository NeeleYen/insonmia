<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.card-img-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.card-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 250px; /* 根據需要調整最大寬度 */
}

</style>
<title>類型專案清單</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h3>類型專案清單：${ categoryValue }</h3>
		<div class="row">
			<c:forEach var="productPicture" items="${ productPicture }">
				<div class="col-xs">
					<div class="card m-3" style="width: 18rem;">
					<a href="${ contextRoot }/public/product/${productPicture.picId.productId.PId}" class="card-img-container d-flex justify-content-center align-items-center" style="height: 200px; position: relative;">
					  <img src="${contextRoot}${productPicture.filePath}" style="object-position: top center; object-fit: cover; width: 100%; height: 100%; position: absolute; top: 50%; transform: translateY(-50%);" class="card-img-top" alt="...">
					</a>
					  <div class="card-body">
					    <h5 class="card-title text-truncate">${ productPicture.picId.productId.title }</h5>
					    <p class="card-text overflow-hidden" style="height: 15em; text-overflow: ellipsis;">${ productPicture.picId.productId.intro }</p>
					    <a href="${ contextRoot }/public/product/${productPicture.picId.productId.PId}" class="btn btn-dark">進入專案</a>
					  </div>
					</div>
				</div>
			</c:forEach>
				<hr>
		</div>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>
</html>