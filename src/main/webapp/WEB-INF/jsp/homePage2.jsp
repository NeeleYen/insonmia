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
    <title>主頁</title>
    <!-- css -->
    <link rel="stylesheet" href="css/style1.css">

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<script>
	
		window.onload = fixFooter;
		window.onresize = fixFooter;
	
		function fixFooter() {
	  var windowHeight = window.innerHeight;
	  var documentHeight = document.body.scrollHeight;
	  var footerHeight = document.getElementById('footer').offsetHeight;
	  
	  if (windowHeight > documentHeight - footerHeight) {
	    document.getElementById('footer').style.position = 'fixed';
	    document.getElementById('footer').style.bottom = '0';
	  } else {
	    document.getElementById('footer').style.position = 'static';
	  }
	}
	</script>
	
	
</head>
<body>
 <!-- 第一區header -->
	<jsp:include page="layout/navbarNew.jsp"></jsp:include>

    <div class="container">
        <div class="row">

            <!-- 輪播圖 -->
          <input id="fileInput" hidden="">
            <div class="slidepic">

                <div id="slide">
                    <a href="product page.html">
                        <img src="images/pic1.jpg" />
                        <img src="images/pic2.jpg" />
                        <img src="images/pic3.jpg" />
                    </a>
                </div>

            </div>

            <!-- 網站介紹 -->
            <article class="webintro">
                <h1>網站介紹</h1>
                <p>募資網站是一種在線平台，允許個人、企業和組織在網上
                    募集資金。這些平台允許創業家和項目發起人通過網絡在
                    眾多投資者中尋找投資。募資網站通常提供了一個網上表
                    單，方便創業家們提交他們的計劃，並多種支付方式接受
                    投資。
                    募資網站的願景是成為一個重要的資金募集和投資平台為
                    創新項目、初創企業、和非營利組織提供支持。總之，募
                    資網站的願景是為了幫助那些缺乏資金動社會和經濟發展
                    同時為投資者提供投資機會。</p>

                <!-- 發起專案 -->
                <a href="${contextRoot}/product">
                    <input type="button" class="postbutton btn btn-dark" value="發起專案">
                </a>
            </article>

        </div>
    </div>
    <!-- 種類大圖 -->
    <div class="box-container">
        <div class="pic">
            <a href="#">
                <img src="images/music.jpg" />
                <div class="info">
                    <p>音樂</p>
                </div>
            </a>
        </div>
        <div class="pic">
            <a href="${contextRoot}/public/art">
                <img src="images/art.jpg" />
                <div class="info">
                    <p>藝術</p>
                </div>
            </a>
        </div>
        <div class="pic">
            <a href="#">
                <img src="images/sport.jpg" />
                <div class="info">
                    <p>旅遊</p>
                </div>
            </a>
        </div>
        <div class="pic">
            <a href="#">
                <img src="images/camping.jpg" />
                <div class="info">
                    <p>遊戲</p>
                </div>
            </a>
        </div>
        <div class="pic">
            <a href="#">
                <img src="images/surfing.jpg" />
                <div class="info">
                    <p>工藝</p>
                </div>
            </a>
        </div>
<!--         <div class="pic"> -->
<!--             <a href="#"> -->
<!--                 <img src="images/car.jpg" /> -->
<!--                 <div class="info"> -->
<!--                     <p>汽車</p> -->
<!--                 </div> -->
<!--             </a> -->
<!--         </div> -->
<!--         <div class="pic"> -->
<!--             <a href="#"> -->
<!--                 <img src="images/dog.jpg" /> -->
<!--                 <div class="info"> -->
<!--                     <p>寵物</p> -->
<!--                 </div> -->
<!--             </a> -->
<!--         </div> -->
<!--         <div class="pic"> -->
<!--             <a href="#"> -->
<!--                 <img src="images/furniture.jpg" /> -->
<!--                 <div class="info"> -->
<!--                     <p>家具</p> -->
<!--                 </div> -->
<!--             </a> -->
<!--         </div> -->
<!--         <div class="pic"> -->
<!--             <a href="#"> -->
<!--                 <img src="images/building.jpg" /> -->
<!--                 <div class="info"> -->
<!--                     <p>建築</p> -->
<!--                 </div> -->
<!--             </a> -->
<!--         </div> -->
    </div>

    <!-- 精選專案 -->
    <!-- <div class="last-container">
        <h1>精選專案</h1>
        <li><a href="#" class="text-muted">查看全部</a></li>
    </div>
    <ul class="slides">
        <input type="radio" id="control-1" name="control" checked>
        <input type="radio" id="control-2" name="control">
        <input type="radio" id="control-3" name="control"> -->

    <!--  Left/Right Button  -->
    <!-- <div class="navigator slide-1">
            <label for="control-3">
                <i class="fas fa-chevron-left"></i>
            </label>
            <label for="control-2">
                <i class="fas fa-chevron-right"></i>
            </label>
        </div>

        <div class="navigator slide-2">
            <label for="control-1">
                <i class="fas fa-chevron-left"></i>
            </label>
            <label for="control-3">
                <i class="fas fa-chevron-right"></i>
            </label>
        </div>

        <div class="navigator slide-3">
            <label for="control-2">
                <i class="fas fa-chevron-left"></i>
            </label>
            <label for="control-1">
                <i class="fas fa-chevron-right"></i>
            </label>
        </div> -->
    <!--  /Left/Right Button  -->

    <!-- <li class="slide">1</li>
        <li class="slide">2</li>
        <li class="slide">3</li>

        <div class="controls-visible">
            <label for="control-1"></label>
            <label for="control-2"></label>
            <label for="control-3"></label>
        </div>
    </ul> -->

<jsp:include page="layout/footerForProductPage.jsp"></jsp:include>

	<script type="text/javascript">
		
	</script>

    <!-- JS -->
    <script src="js/script1.js"></script>
</body>
</html>