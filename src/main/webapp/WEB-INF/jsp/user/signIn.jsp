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
<script src="https://cdn.jsdelivr.net/gh/github/fetch@v4.1.1/fetch.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/octicons/4.4.0/font/octicons.min.css">

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
<title>登入</title>
<script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>
<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<script>
	
</script>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>

	<div class="container">
		<h1 class= "mt-5 mb-4">登入</h1>

		<div class="mb-3">
		<input type="hidden" name="_csrf" value="${_csrf.token}">
			<form:form id="userForm" method="post" modelAttribute="user" action="${contextRoot}/public/user/logIn">
				
				<label for="Email" class="form-label">信箱</label>
				<form:input path="username" type="email" class="form-control" id="Email" aria-describedby="emailHelp"></form:input>
				<div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>

				<div class="mt-2 mb-3">
					<label for="Password" class="form-label">密碼</label>
					<form:input path="password" type="password" class="form-control" id="Password"></form:input>
				</div>

				<div class="mt-2 mb-3 form-check">
					<input name="remember-me" type="checkbox" class="form-check-input" id="exampleCheck1">
					<label class="form-check-label" for="exampleCheck1">記住我</label>
				</div>

				<button type="submit" id="submitBtn" class="mt-2 btn btn-outline-dark">登入</button>
				<div id="msg1" class="mt-3"></div>
			</form:form>
			
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
			
		</div>

	</div>
	
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	

	<script type="text/javascript">
	
	/*
	設定 Facebook JavaScript SDK
	Facebook JavaScript SDK 沒有任何需要下載或安裝的獨立檔案，您只需要將一小段一般的 JavaScript 置入 HTML 中，就會以非同步的方式將 SDK 載入頁面中。非同步載入是指不會阻擋頁面中其他元素的載入。
	  window.fbAsyncInit = function() {
	    FB.init({
	      appId      : '{your-app-id}',
	      cookie     : true,
	      xfbml      : true,
	      version    : '{api-version}'
	    });
	      
	    FB.AppEvents.logPageView();   
	      
	  };

	  (function(d, s, id){
	     var js, fjs = d.getElementsByTagName(s)[0];
	     if (d.getElementById(id)) {return;}
	     js = d.createElement(s); js.id = id;
	     js.src = "https://connect.facebook.net/en_US/sdk.js";
	     fjs.parentNode.insertBefore(js, fjs);
	   }(document, 'script', 'facebook-jssdk'));
	  
	  
	  
	  //////////////////////////////////////
	  檢查登入狀態
	  載入您的網頁時，第一個步驟是確認用戶是否已經使用「Facebook 登入」來登入您的應用程式。呼叫 FB.getLoginStatus 來啟動這個程序。這個函式會觸發對 Facebook 的呼叫來取得登入狀態，接著呼叫您的回呼函式來傳回結果。
	  以下擷取自上述程式碼範例，為在頁面載入時用來檢查用戶登入狀態所執行的部分程式碼：
	  FB.getLoginStatus(function(response) {
	      statusChangeCallback(response);
	  });複製代碼
	  提供給回呼的 response 物件含有數個欄位：
	  {
	      status: 'connected',
	      authResponse: {
	          accessToken: '...',
	          expiresIn:'...',
	          signedRequest:'...',
	          userID:'...'
	      }
	  }複製代碼
	  status 說明此應用程式用戶的登入狀態。狀態可能為以下其中一項：
	  connected - 這位用戶已登入 Facebook，也已經登入您的應用程式。
	  not_authorized - 這位用戶已登入 Facebook，但尚未登入您的應用程式。
	  unknown - 這位用戶沒有登入 Facebook，因此您無法得知用戶是否已登入您的應用程式，或者之前已呼叫 FB.logout()，因此無法連結至 Facebook。
	  如果狀態是 connected，就會包含 authResponse，且由以下資料所構成：
	  accessToken - 含有這位應用程式用戶的存取權杖。
	  expiresIn - 以 UNIX 時間顯示權杖何時到期並需要再次更新。
	  signedRequest - 已簽署的參數，其中包含這位應用程式用戶的資訊。
	  userID - 這位應用程式用戶的編號。
	  您的應用程式知道這位應用程式用戶的登入狀態後，就會進行以下其中一項動作：
	  如果用戶已登入 Facebook 和您的應用程式，就會將用戶重新導向至已登入的應用程式體驗。
	  如果用戶沒有登入您的應用程式，或是沒有登入 Facebook，則使用 FB.login() 以「登入」對話方塊提示用戶，或向用戶顯示「登入」按鈕。
	  
	  
	  
	  //////////////////////////////
	  新增「Facebook 登入」按鈕
	  將「登入」按鈕加入您的頁面非常容易，參閱「登入」按鈕文件，並依照您要的方式設定按鈕。然後點擊取得程式碼，即會顯示所需程式碼，以便在您的頁面上顯示這個按鈕。
	  按鈕上的 onlogin 屬性是用於設定檢查登入狀態的 JavaScript 回呼，以瞭解用戶是否已經成功登入：
	  <fb:login-button 
		scope="public_profile,email"
		onlogin="checkLoginState();">
	  </fb:login-button>複製代碼
	  就是這個回呼，它會呼叫 FB.getLoginStatus() 來取得最新的登入狀態（範例中處理這個回應的是 statusChangeCallback() 函式）。
	  function checkLoginState() {
		 FB.getLoginStatus(function(response) {
		   statusChangeCallback(response);
		 });
	  }
	  https://developers.facebook.com/docs/facebook-login/web/login-button 
	  https://developers.facebook.com/docs/facebook-login/web#logindialog
	  
	*/
	
	
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