<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextRoot}/css/bootstrap.min.css" rel="stylesheet" />
<title>修改密碼</title>
</head>
<body>
<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class= "mt-5 mb-4">修改密碼</h1>
		<form:form id="userForm" method="post" modelAttribute="dto" action="${contextRoot}/users/alterPassword">

			<div class="mb-3 mt-2">
				<label for="passwordNow" class="form-label">目前密碼</label>
				<form:input path="passwordNow" id="passwordNow" class="form-control" type="password"></form:input>
			</div>
			
			<div class="mb-3 mt-2">
				<label for="passwordAlter" class="form-label">修改密碼</label>
				<form:input path="passwordAlter" id="passwordAlter" class="form-control" type="password"></form:input>
			</div>
			<div class="mb-3 mt-2">
				<label for="passwordCheck" class="form-label">修改確認密碼</label>
				<form:input path="passwordCheck" id="passwordCheck" class="form-control" type="password"></form:input>
			</div>

			<button type="submit" id="submitBtn" class="mt-2 btn btn-outline-dark d-inline-block">修改</button>
			<div id="msg1" class="mt-3 text-primary"></div>
			<div class="m-5"></div>
		</form:form>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	
	<script type="text/javascript">
	const form = document.getElementById('userForm');
	const submitBtn = document.getElementById('submitBtn');
	const msg1 = document.getElementById('msg1');

	submitBtn.addEventListener('click', (event) => {
	  event.preventDefault();
	  const formData = new FormData(form);
	  fetch('${contextRoot}/users/alterPassword', {
	    method: 'POST',
	    body: formData
	  })
	  .then(response => response.json())
	  .then(data => {
	    if (data.status === 'update') {
	      msg1.textContent = '密碼修改成功，請重新登入'; // 設置的內容只會是純文字(不看標籤) vs .innerText
	      setTimeout(function(){ $("#msg1").html(""); window.location.href = '${contextRoot}/logout';}, 5000); // 修改成功，重新登入
	    } else {
	      msg1.textContent = '密碼修改失敗';
	      setTimeout(function(){ $("#msg1").html("");}, 5000);
	    }
	  })
	  .catch(error => {
	    console.error(error);
	    msg1.textContent = '發生錯誤';
        setTimeout(function(){ $("#msg1").html("");}, 5000);
	  });
	});

	</script>
	
	
	
</body>
</html>