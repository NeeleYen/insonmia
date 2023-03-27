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
<title>修改會員資料</title>
</head>

<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class= "mt-5 mb-4">修改會員資料</h1>
		<form:form id="userForm" method="post" modelAttribute="user" action="${contextRoot}/users/alter">

			<div class="mb-3 mt-2">
				<label for="Address" class="form-label">地址</label>
				<form:input path="address" id="Address" class="form-control"></form:input>
			</div>

			<button type="submit" id="submitBtn" class="mt-2 btn btn-outline-dark d-inline-block">修改</button>
			<div id="msg1" class="mt-3"></div>
		</form:form>
	</div>
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	
	<script>
	document.getElementById("userForm").addEventListener("submit", function(event) {
	    event.preventDefault();
	    const formData = new FormData(event.target);
	    fetch(event.target.action, {
	        method: "POST",
	        body: formData
	    })
	    .then(response => response.json())
	    .then(data => {
	        const msg1 = document.getElementById("msg1");
	        if (data.status === "update") {
	        	msg1.innerHTML = `<span class="text-primary">修改成功</span>`;
	            setTimeout(function () { $("#msg1").html(""); }, 5000);
	            document.querySelectorAll('#userForm input.clearable[type="password"]').forEach((input) => input.value = '');
	        } else {
	            msg1.innerHTML = `<span class="text-danger">修改失敗</span>`;
	            setTimeout(function () { $("#msg1").html(""); }, 5000);
	            document.querySelectorAll('#userForm input.clearable[type="password"]').forEach((input) => input.value = '');

	        }
	    })
	    .catch(error => {
	        console.error("Error:", error);
	        msg1.innerHTML = `<span class="text-danger">修改失敗</span>`;
	        setTimeout(function () { $("#msg1").html(""); }, 5000);
	        document.querySelectorAll('#userForm input.clearable[type="password"]').forEach((input) => input.value = '');
	    });
	});

	</script>
	
	
	
</body>

</html>