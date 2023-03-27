<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />    
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品頁</title>
    <!-- css -->
    <link rel="stylesheet" href="../css/style2.css">

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<!-- 第一區header -->
<!--     <header> -->
<!--         logo主頁(超連結) -->
<%--         <a href="${ contextRoot }/" class="logo"><img src="../images/logo.jpg"></a> --%>
<!--         <nav class=""> -->
<!--             <ul> -->
<!--                 <button onclick="window.location.href='Login.html'" class="btn btn-dark ">Login</button> -->
<!--                 <button onclick="window.location.href='Login.html'" -->
<!--                     class="btn btn-outline-dark text-white">SignUp</button> -->
<!--                 會員照片 -->
<!--                 <img id="preview" class="border"> -->
<!--                 <input type="file" id="fileInput"> -->
<!--             </ul> -->


<!--             種類 -->
<!--             <div class="option "> -->
<!--                 <ul> -->
<!--                     <li><a href="#" class="text-white">藝術</a></li> -->
<!--                     <li><a href="#" class="text-white">音樂</a></li> -->
<!--                     <li><a href="#" class="text-white">體育</a></li> -->
<!--                     <li><a href="#" class="text-white">露營</a></li> -->
<!--                     <li><a href="#" class="text-white">衝浪</a></li> -->
<!--                     <li><a href="#" class="text-white">汽車</a></li> -->
<!--                     <li><a href="#" class="text-white">寵物</a></li> -->
<!--                     <li><a href="#" class="text-white">家具</a></li> -->
<!--                     <li><a href="#" class="text-white">建築</a></li> -->
<!--                 </ul> -->
<!--             </div> -->
<!--         </nav> -->

<!--     </header> -->
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>

	
    <div class="container">
        <div class="row">
            <!-- 專案照片 -->
            <div class="pic col">
            	
            	<c:forEach var="entry" items="${productPictures}" varStatus="status">
					<c:if test="${status.index == 0}">
						<c:set var="picturePath" value="${entry.value}" />
						<img src="${contextRoot}${picturePath}" width="500px" height="300px"  alt="..." />
					</c:if>
				</c:forEach> 
            	
<!--                 <img src="../images/pic1.jpg" width="700px" height="500px" class="img-thumbnail border-3 m-3"> -->
            </div>
            <div class="col">
                <!-- 進度條 -->
                <div class="progress">
                    <div class="progress-bar bg-dark" role="progressbar" style="width:75%" aria-valuenow="75"
                        aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <!-- 目標金額 -->
                <div class="space">
                    <h1>NT$60000</h1>
                </div>
                <!-- 累積金額 -->
                <div>
                    <h6>累積金額 (總目標NT$8,0000)</h>
                </div>
                <!-- 人數 -->
                <div class="space">
                    <h2>20</h2>
                </div>
                <!-- 位支持者 -->
                <div>
                    <h6>位支持者</h6>
                </div>
                <!-- 天數 -->
                <div class="space">
                    <h2>13</h2>
                </div>
                <!-- 天剩餘 -->
                <div>
                    <h6>天 剩餘</h6>
                </div>
                <!-- 支持此專案 -->
                <a href="#">
                    <input type="button" class="postbutton btn btn-dark" value="支持此專案">
                </a>

            </div>
        </div>
		<div class="row">
            <div class="productintro">
                <h1>${ product.title }</h1>
                <p>(產品介紹先不加)智能化家庭監控系統是一個基於人工智慧技術的家庭監控系統，旨在提供家庭安全和便利性。該系統可以偵測和識別家庭中的設備和人員，並提供適當的控制和監控，以確保家庭的安全。系統的核心是一個智能控制中心，該中心可以集成各種傳感器和監控設備，如門鎖、攝像頭、煙霧報警器等，以及連接到互聯網，從而可以隨時隨地進行遠程監控。
                </p>
            </div>
		</div>



    <div class="row">
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <!-- 產品介紹 -->
                <button type="button" class="nav-link active btn-light" id="nav-home-tab" data-toggle="tab"
                    data-target="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">產品介紹</button>
                <!-- 留言板 -->
                <button type="button" class="nav-link btn-light" id="nav-profile-tab" data-toggle="tab"
                    data-target="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">留言板</button>
                <!-- FAQ -->
                <button type="button" class="nav-link btn-light" id="nav-contact-tab" data-toggle="tab"
                    data-target="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">FAQ</button>
            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                1322324245
            </div>
            <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab"> aaaaaaaaaaaa
            	<a class="btn btn-outline-dark" href="${contextRoot}/message/add">新增留言</a>
            	<a class="btn btn-outline-dark" href="${contextRoot}/message/page">查看訊息</a>
            </div>
            <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                bbbbbbbbbbbbbbbbbb</div>
        </div>
    </div>



    </div>

 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
<!--     <footer class="bg-dark text-center text-white"> -->
<!--         <div class="p-4"> -->
<!--             社群連結 -->
<!--             <section class="mb-4"> -->
<!--                 Gmail -->
<!--                 <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i -->
<!--                         class="fa-solid fa-envelope"></i></a> -->

<!--                 Twitter -->
<!--                 <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i class="fab fa-twitter"></i></a> -->


<!--                 Facebook -->
<!--                 <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i -->
<!--                         class="fa-brands fa-facebook"></i></a> -->
<!--             </section> -->
<!--             <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0);"> -->
<!--                 高雄市前金區中正四路211號8號樓之1 -->
<!--             </div> -->
<!--         </div> -->

<!--     </footer> -->




    <!-- JS -->
    <script src="js/script2.js"></script>
</body>
</html>