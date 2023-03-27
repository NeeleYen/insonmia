<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet"
	href="https://meyerweb.com/eric/tools/css/reset/reset.css">
<link rel="stylesheet" href="${contextRoot}/css/bootstrap.min.css">
<script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>
<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<script src="${contextRoot}/js/bootstrap.bundle.min.js"></script>
<meta charset="UTF-8">
<title>新增專案</title>
</head>

<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<input type="hidden" value="${contextRoot}" id="contextRoot">
	<div class="container">
		<h1 class= "mt-5 mb-4">新增一個專案</h1>

		<form id="addProductForm" action="${contextRoot}/product/form"
			method="post">
			<input id="csrf" type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">

			<div class="form-group mt-2">
				<label for="title">專案主題</label> <input name="title" id="title"
					required class="form-control">
			</div>

			<!-- <label for="category">專案類別:</label> <input id="category" required /> -->
			<div class="form-group mt-2">
				<label for="intro">專案介紹</label>
				<textarea id="intro" name="intro" required class="form-control"></textarea>
			</div>

			<div class="d-flex">
			  <div class="form-group mt-2 mr-4">
			    <label for="category">專案類別</label>
<!-- 			    <select name="category" id="category" class="form-select"> -->
<!-- 			      <option value="1">音樂</option> -->
<!-- 			      <option value="2">遊戲</option> -->
<!-- 			      <option value="3">旅遊</option> -->
<!-- 			      <option value="4">工藝</option> -->
<!-- 			    </select> -->
			    <select name="category" id="category" class="form-select">
					<c:forEach var="category" items="${ requestScope.entrySet }">   
                    	<option value="${category.key}" >${category.value}</option>          		             		
                	</c:forEach>
			    </select>
			  </div>
			  
			    <div class="form-group mt-2 ml-5">
			    <label for="status">專案狀態</label>
			    <select name="status" id="status" class="form-select">
			      <option value="0">正常販售</option>
			      <option value="1">已截止</option>
			    </select>
			  </div>
			</div>

			<div class="d-flex">
			  <div class="form-group mt-2 mr-4">
			    <label for="closeDate">專案截止日</label>
				<input id="closeDate" name="closeDate" type="date" required class="form-control">
			  </div>
			</div>


			<button id="submitBtn" class="btn btn-primary mt-2">送出</button>
			<div class="mt-4"></div>
		</form>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	<script>
		// window.onload = function () {
		//   // 參考ajax p.56
		// $("#addProductForm").submit(function(event){
		//   event.preventDefault();

		//   fetch('${contextRoot}/product/form',
		//     {
		//       method: 'POST',
		//       body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
		//       headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
		//     }
		//   ).then(response => { // 直接跳轉，此行以下可省略
		//     return response.text();
		//   }).then(respText => {
		//     console.log(respText);
		//   })

		// });
		// }
	</script>

</body>

</html>