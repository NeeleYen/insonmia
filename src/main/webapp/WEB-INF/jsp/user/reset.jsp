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
<title>重設密碼</title>
</head>
<body>
<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
	<h1 class= "mt-5 mb-4">重設密碼</h1>
	
	<form id="userForm" method="post" action="${contextRoot}/public/user/resetPassword">
			<input type="hidden" name="_csrf" value="${_csrf.token}">
			<div class="mb-3">
				<input type="hidden" name="token" value="${user.resetPasswordToken}">
			</div>
			
			<div class="mb-3">
				<label for="Password" class="form-label">密碼</label>
				<input name="password" type="password" class="form-control" id="Password" required="required" onblur="checkPassword()" ></input>
			</div>
			
			<div class="mb-3">
				<label for="Password2" class="form-label">確認密碼</label>
				<input type="password" class="form-control" id="Password2" required="required" onblur="checkPassword()" ></input>
			</div>
			
			<button type="submit" id="submitBtn" class="btn btn-outline-dark">重設密碼</button>
			<div id="msg1" class="mt-3 text-danger"></div>
		</form>
	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	
	<script>
	function checkPassword() {
	var password1 = document.getElementById("Password").value;
	var password2 = document.getElementById("Password2").value;
	var msg1 = document.getElementById("msg1");
		  if (password1 != password2) {
			  msg1.innerText = "兩次密碼輸入不一致";
			  setTimeout(function(){ $("#msg1").html("");}, 5000);
		  } else {
			  msg1.innerText = "";
		  }
		}	
	
	// 綁定事件處理程序
	document.getElementById("submitBtn").addEventListener("click", function(event) {
	    // 防止表單提交
	    event.preventDefault();
	var password1 = document.getElementById("Password").value;
	var password2 = document.getElementById("Password2").value;
	var msg1 = document.getElementById("msg1");
	    // 執行密碼驗證
	    if (password1 !== password2) {
	        msg1.innerText = "兩次密碼輸入不一致";
	        setTimeout(function(){ $("#msg1").html("");}, 5000);
	    } else {
	        // 驗證成功，提交表單
	        document.getElementById("userForm").submit();
	    }
	});

</script>
	
	
</body>
</html>