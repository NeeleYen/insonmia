<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

		<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<link href="${contextRoot}/css/bootstrap.min.css" rel="stylesheet" />

		</head>

		<body>

			<nav class="navbar navbar-expand-lg bg-light">
				<div class="container-fluid">
					<a class="navbar-brand" href="${contextRoot}/">首頁圖片(?)</a>
					<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
						aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarNav">
						<ul class="navbar-nav">
							<li class="nav-item"><a class="nav-link active" aria-current="page" href="${contextRoot}/">首頁</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/public/user/add">註冊(還沒防呆)</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/public/user/sign">登入</a>
							</li>
							<li class="nav-item">
							
<%-- 								<form id="logout-form" action="<c:url value="/logout"/>" method="get"> --%>
									<a class="nav-link" href="${contextRoot}/logout">登出</a>
<%-- 	   							 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
<%-- 								</form> --%>
								
<%-- 								<form:form action="${contextRoot}/logout" method="POST"> --%>
<%-- 									<a class="nav-link" href="${contextRoot}/logout">登出</a> --%>
<!-- 									<input type="submit" value="Logout"> -->
<%-- 								</form:form> --%>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/users/replace">修改(還沒好)</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/users/insertCard">新增信用卡</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/product">add product</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/public/user/forget">忘記密碼</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/user/findCard">信用卡查詢</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/public/product/toOrderList">查看訂單</a>
							</li>
							<li class="nav-item"><a class="nav-link disabled" href="">Disabled</a>
							</li>
							<li class="nav-item"><a class="nav-link disabled" href="">Disabled</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/editProduct/1">editProduct</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/message/add">新增留言</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/message/page">查看訊息</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="${contextRoot}/faq/FAQ">常見問題</a>
							</li>	
						</ul>
					</div>
				</div>
			</nav>
			<!-- https://github.com/axios/axios -->
			<script src="https://unpkg.com/axios@1.1.2/dist/axios.min.js"></script>
			<script src="${contextRoot}/js/bootstrap.bundle.min.js"></script>
			<script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>

		</body>

		</html>