<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
    <!-- css -->
    <link rel="stylesheet" href="${contextRoot}/css/navbar.css">
    <!-- bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</head>
<body>
   <nav class="navbar navbar-expand-lg navbar-light">

        <div class="container-fluid">
            <!-- logo -->
            <a class="navbar-brand" href="#"><img width="50px" height="50px" src="images/logoEye.jpg"></a>

            <!-- 第一部份：手機版 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#linkbar">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- 第二部份:電腦版 -->

            <div class="collapse navbar-collapse" id="linkbar">
                <!-- 列出內容 -->
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">art</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">music</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">travel</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">game</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#">craft</a>
                    </li>
                </ul>


                <!-- 會員 -->
                <form style="width:300px;" class="input-group">
                    <div id="space2">
                        <button onclick="window.location.href='Login.html'" class="btn btn-dark ">Login</button>
                        <button onclick="window.location.href='Login.html'"
                            class="btn btn-outline-dark text-white">SignUp</button>
                        <!-- 會員照片 -->
                        <img src="/images/dog.jpg" class="landscape">


                </form>


            </div>



        </div>
    </nav>
	
</body>
</html>