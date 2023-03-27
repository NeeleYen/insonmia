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
<title>註冊</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>

	<div class="container">
		<h1 class= "mt-5 mb-4">註冊</h1>
			 <form:form id="userForm" method="post" modelAttribute="user" action="${contextRoot}/public/user/post" >
<%-- 		<form:form id="userForm" method="post" modelAttribute="user"> --%>

			<div class="mb-2">
				<label for="Name" class="form-label">會員名</label>
				<form:input path="username" id="Name" class="form-control"
					aria-describedby="nameHelp" required="required"></form:input>
				<div id="nameError" class="form-text"></div>
			</div>

			<div class="mt-2 mb-2">
				<label for="Email" class="form-label">信箱</label>
				<form:input path="email" type="email" class="form-control"
					id="Email" aria-describedby="emailHelp" required="required"></form:input>
				<div id="emailError" class="form-text"></div>
			</div>

			<div class="mt-2 mb-2">
				<label for="Password" class="form-label">密碼</label>
				<form:input path="password" type="password" class="form-control"
					id="Password" required="required"></form:input>
			</div>
			<div class="mt-2 mb-2">
				<label for= "password2" class="form-label">確認密碼</label>
				<input name="password2" type="password" class ="form-control"
				 id="password2" required="required"></input>
				<div id="passwordError"></div>
			</div>

			<div class="mt-2 mb-2">
				<label for="Address" class="form-label">地址</label>
				<form:input path="address" id="Address" class="form-control"></form:input>
			</div>
			<div style="display: flex; align-items: flex-end">
				<button type="submit" id="submitBtn" class="mt-3 btn btn-outline-dark">註冊</button>
				<div id="msg1" class="ml-3" style="padding-bottom: 3px;"></div>
			</div>
			<div class="m-5"></div>
			<div class="m-5"></div>
		</form:form>
	</div>
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>


	<script type="text/javascript">
		  const userForm = document.getElementById("userForm"); // 取得form:form表單
		  const msg1 = document.getElementById("msg1"); // 取得非同步請求想放置的位置
		  userForm.addEventListener("submit", (event) => { // 註冊事件處理器，提交時的處理
		    event.preventDefault();	// 不要讓form:form去送，我要用程式自己送
		    const formData = new FormData(event.target); 
		    // 先比對兩個密碼欄位是否相同
		    const password1 = document.getElementById("Password").value;
		    const password2 = document.getElementById("password2").value;
// 		    console.log(password1);
// 		    console.log(password2);
		    if (password1 !== password2) {
		        // 密碼不相同，顯示錯誤訊息並退出函式
		        const msg = document.getElementById("passwordError");
		        msg.innerHTML = `<span class="text-danger">兩次輸入的密碼不相同！</span>`;
		        setTimeout(function () { $("#passwordError").html(""); }, 5000);
		        return;
		    }
		    fetch("${contextRoot}/public/user/post", { // url
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
		          msg1.innerHTML = `<span id="span1" class="text-primary">註冊成功!請至您的email進行會員驗證，5秒後跳轉回首頁</span>`;
		          userForm.reset();
		       	  setTimeout(function () { window.location.href = "${contextRoot}/"; }, 5000);  	  
		        } else if (data.status === "errorEmailName") {
		          msg1.innerHTML = `<span id="span1" class="text-danger">errorEmailName!</span>`;        
		        } else if (data.status === "errorName") {
		          const msg2 = document.getElementById("nameError");
			      msg2.innerHTML = `<span id="span1" class="text-danger">errorName!</span>`;
			    } else if (data.status === "errorEmail") {
			      const msg3 = document.getElementById("emailError");	
			      msg3.innerHTML = `<span id="span1" class="text-danger">errorEmail!</span>`;
			    } else {
			      throw new Error("Something went wrong.");
				}
		      })
		      .catch((error) => {
		        msg1.innerHTML = `<span class="text-danger">註冊失敗，請重新註冊!</span>`;
		        userForm.reset();
		        setTimeout(function () { $("#span1").html(""); }, 5000); // 顯示五秒
		      });
			 setTimeout(function () { $("#span1").html(""); }, 5000);
		  });
		
	</script>


</body>
</html>