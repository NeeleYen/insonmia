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

<title>新增信用卡</title>
</head>
<body>
<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class= "mt-5 mb-4">新增信用卡</h1>
		<form:form id="CardForm" method="post" modelAttribute="dto" action="${contextRoot}/user/CreditCard">
			<div class="mb-3">
				<label for="card" class="form-label">卡號</label>
				<form:input path="card" id="card" class="form-control" maxlength="16" minlength="16" required="true"></form:input>
			</div>
			<div class="mb-3 mt-2">
				<label for="cvc" class="form-label">卡驗證碼</label>
				<form:input path="cardCvc" id="cvc" class="form-control" maxlength="3" minlength="3" required="true"></form:input>
			</div>
			<div class="row mb-3 mt-2">
				<div class="col">
					<label for="month" class="form-label">到期年</label>
					<form:input path="cardYear" id="year" class="form-control" required="true" maxlength="2" minlength="2" />
				</div>
				<div class="col">
					<label for="year" class="form-label">到期月</label>
					<form:input path="cardMonth" id="month" class="form-control" required="true" maxlength="2" minlength="2" />
				</div>
			</div>
			<button type="submit" id="submitCard" class="mt-2 btn btn-outline-dark d-inline-block">新增信用卡資訊</button>
			<div id="msg1" class="form-text mt-3" style="position: fixed"></div>
			<div class="mt-5"></div>
		</form:form>

	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	<script>
		const resetForm = document.getElementById('CardForm');
		const msg1 = document.getElementById('msg1');
		
		resetForm.addEventListener('submit', (event) => {
		    event.preventDefault();
		
		    const formData = new FormData(resetForm);
		    fetch('${contextRoot}/user/CreditCard', {
		        method: 'POST',
		        body: formData
		    })
		    .then(response => response.json())
		    .then(data => {
		        if (data.success) {
		        	msg1.innerHTML = `<span class="text-primary" id="span1"></span>`;
		        	const span1 = document.getElementById('span1');
		        	span1.innerHTML = data.success;
		            setTimeout(function () { $("#msg1").html(""); }, 5000);
		            resetForm.reset();
		            
		        } else if (data.error) {
		        	msg1.innerHTML = `<span class="text-danger" id="span2"></span>`;
		        	const span2 = document.getElementById('span2');		        	
		        	span2.innerHTML = data.error;
		            setTimeout(function () { $("#msg1").html(""); }, 5000);  
		            resetForm.reset();
		        }
		    })
		    .catch(error => console.error(error));
		    	setTimeout(function () { $("#msg1").html(""); }, 5000);  
		        resetForm.reset();
		});
		
		
	</script>

</body>
</html>