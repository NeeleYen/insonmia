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
<title>登入</title>
<script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>
<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<style type="text/css">
.btn-github {
  color: #fff;
  background-color: #333;
  border-color: #333;
}
.btn-github:hover {
  color: #fff;
  background-color: #ccc;
  border-color: #333;
}
.btn-github:focus, .btn-github.focus {
  color: #fff;
  background-color: #1d1d1d;
  border-color: #333;
}
.btn-github:active, .btn-github.active,
.open > .dropdown-toggle.btn-github {
  color: #fff;
  background-color: #1d1d1d;
  border-color: #333;
}
.btn-github:active:hover, .btn-github.active:hover,
.open > .dropdown-toggle.btn-github:hover,
.btn-github:active:focus, .btn-github.active:focus,
.open > .dropdown-toggle.btn-github:focus,
.btn-github:active.focus, .btn-github.active.focus,
.open > .dropdown-toggle.btn-github.focus {
  color: #fff;
  background-color: #333;
  border-color: #333;
}

.btn-google {
  color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
}
.btn-google:hover {
  color: #fff;
  background-color: #ccc;
  border-color: #333;
}
.btn-google:focus,
.btn-google.focus {
  color: #fff;
  background-color: #3367d6;
  border-color: #3367d6;
}
.btn-google:active,
.btn-google.active,
.open > .dropdown-toggle.btn-google {
  color: #fff;
  background-color: #3367d6;
  border-color: #3367d6;
}
.btn-google:active:hover,
.btn-google.active:hover,
.open > .dropdown-toggle.btn-google:hover,
.btn-google:active:focus,
.btn-google.active:focus,
.open > .dropdown-toggle.btn-google:focus,
.btn-google:active.focus,
.btn-google.active.focus,
.open > .dropdown-toggle.btn-google.focus {
  color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
}
.btn-google .btn-icon img {
  width: 18px;
  height: 18px;
}


</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/layout/navbarNew.jsp"></jsp:include>

<!-- 		---james added--- -->
		<c:if test="${not empty errorMessage}">
			<h2 style="color:red;">${errorMessage}</h2>
		</c:if>
<!-- 		---james added--- -->
	<div class="container">
		<h1 class= "mt-5 mb-4">登入</h1>
		<div class="mb-3">
		<form action="/my-insomnia/public/user/logIn" method="post">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
			
<%-- 			<form:form id="userForm" method="post" modelAttribute="user"> --%>
				
				<label for="Email" class="form-label">email</label>
				<input name="username" type="email" class="form-control" id="Email" aria-describedby="emailHelp"></input>
				<div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>

				<div class="mt-2 mb-3">
					<label for="Password" class="form-label">password</label>
					<input name="password" type="password" class="form-control" id="Password"></input>
				</div>
						
				<div class="mt-2 mb-3 form-check">
					<input name="remember-me" type="checkbox" class="form-check-input" id="exampleCheck1">
					<label class="form-check-label" for="exampleCheck1">remember-me</label>
				</div>
				<div>
				<button type="submit" id="submitBtn" class="mt-2 btn btn-outline-dark">登入</button>				
				<div id="msg1" class="mt-3"></div>
				</div>
				
				
			<div class="mt-3">
			<!-- /login/oauth2/code/google -->
			<a href="${contextRoot}/oauth2/authorization/google" class="btn btn-google">
				  <span class="btn-icon">
				     <img src="${contextRoot}/images/g-logo.png" alt="Google logo" />
				  </span>
				  <span class="btn-text">Sign in with Google</span>
				</a>
				
				
				<a href="${contextRoot}/oauth2/authorization/github" class="btn btn-github">
				  <i class="fab fa-github"></i> Sign in with GitHub
				</a>
<%-- 				<a class="btn btn-1g btn-secondary" href="${contextRoot}/oauth2/authorization/google">Google快速登入!</a> --%>
<%-- 				<a class="btn btn-1g btn-secondary" href="${contextRoot}/oauth2/authorization/github">Github快速登入!</a> --%>
			</div>
			
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/layout/footerForProductPage.jsp"></jsp:include>

	<script type="text/javascript">
/*
	  const userForm = document.getElementById("userForm"); // 取得form:form表單
	  const msg1 = document.getElementById("msg1"); // 取得非同步請求想放置的位置
	  userForm.addEventListener("submit", (event) => { // 註冊事件處理器，提交時的處理
	    event.preventDefault();	// 不要讓form:form去送，我要用程式自己送
	    const formData = new FormData(event.target); 
	    fetch("${contextRoot}/user/logIn", { // url
	      method: "POST", // post請求
	      body: formData, // 放入使用者提交的資料
	    })
	      .then((response) => {
	        if (response.ok) { // 判斷請求
	          return response.json();
	        } else {
	          throw new Error("Something went wrong.");
	        }
	      })
	      .then((data) => {
	        if (data.status === "success") { // 回應回來的值如果是success，就登入成功
	          window.location.href = "${contextRoot}/"; // 驗證成功會直接回首頁
// 	          msg1.innerHTML = `<span class="text-primary">登入成功!</span>`;
	          userForm.reset();
	        } else {
	          msg1.innerHTML = `<span class="text-danger">登入失敗，測試用喔...請從新登入!</span>`;
	          userForm.reset();
	          setTimeout(function () { $("#msg1").html(""); }, 5000); // 顯示五秒
	        }
	      })
	      .catch((error) => {
	        msg1.innerHTML = `<span class="text-danger">登入失敗，請重新登入!</span>`;
	        userForm.reset();
	        setTimeout(function () { $("#msg1").html(""); }, 5000); // 顯示五秒
	      });
	  });

*/
		
	</script>

</body>
</html>