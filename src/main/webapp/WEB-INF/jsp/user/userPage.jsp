<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />        
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>user page</title>
	<!-- css -->
<!--     <link rel="stylesheet" href="css/style1.css"> -->

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

<%-- 	<jsp:include page="../layout/header.jsp"></jsp:include> --%>
<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
<div class="container">
  <h1 class= "mt-5 mb-4">會員頁</h1>
  <div class="d-inline-block align-middle">
    <sec:authorize access="hasRole('ROLE_unchecked')">
      <form:form id="PermissionForm" method="post" modelAttribute="user">
        <button type="submit" id="submitBtnPermission" class="btn btn-outline-dark">重新寄送會員驗證信件</button>
      </form:form>
    </sec:authorize>
  </div>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/users/replace">修改會員資料</a>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/users/replacePassword">修改密碼</a>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/users/insertCard">新增信用卡</a>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/user/findCard">信用卡查詢</a>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/product">我的專案</a>
  <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/public/product/toOrderList">查看訂單</a>
<%--   <a class="btn btn-outline-dark d-inline-block align-middle" href="${contextRoot}/editProduct/1">editProduct</a> --%>
  <br>
  <div id="msg1" class="form-text mt-3"></div>
</div>


<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
<!-- 	<script src="js/script2.js"></script> -->
	
	<script>
	
	const resetForm = document.getElementById('PermissionForm');
	const msg1 = document.getElementById('msg1');
	
	resetForm.addEventListener('submit', (event) => {
	    event.preventDefault();
	
	    const formData = new FormData(resetForm);
	    fetch('${contextRoot}/user/Verification', {
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