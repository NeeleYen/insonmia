<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>忘記密碼</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
	<h1 class= "mt-5 mb-4">忘記密碼</h1>
	<form:form id="reset" method="post" modelAttribute="dto" action="#">
    <div class="mb-3">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <label for="email" class="form-label">Email: </label>
        <form:input class="form-control" type="email" id="email" path="stringEmail" required="required" />
    </div>
    <button type="submit" class="mt-2 btn btn-outline-dark">重設密碼，請至信箱收件</button>
    <div id="msg1" class="form-text mt-3"></div>
	</form:form>

	
	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	
	<script>
	const resetForm = document.getElementById('reset');
	const msg1 = document.getElementById('msg1');
	
	resetForm.addEventListener('submit', (event) => {
	    event.preventDefault();
	
	    const formData = new FormData(resetForm);
	    fetch('${contextRoot}/public/forgotPassword', {
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
	            
	        } else if (data.error) {
	            msg1.innerHTML = data.error;
	        }
	    })
	    .catch(error => console.error(error));
	});





	
	</script>
	
	
</body>
</html>