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
			
</head>
<body>
	<jsp:include page="layout/navbarNew.jsp"></jsp:include>
	
  <div class="container">
        <div class="row">

            <!-- 輪播圖 -->
            <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <!-- 第一張圖片 -->
	                    <div class="carousel-item active">
	                    <c:if test="${thumbNail!=null}">
                        <a href="${ contextRoot }/public/product/${thumbNail.picId.productId.PId}">
                            <img style="object-fit: cover;" width="650px" height="450px" src="${ contextRoot }${thumbNail.filePath}" class="d-block w-100" alt="...">
                        </a>              
	                    </c:if>
	                    </div>           
                   
                    <!-- 第二張圖片 -->
                    <c:if test="${picOneForSlideList!=null}">
                         <c:forEach var="slideImg" items="${ picOneForSlideList }">                    	
                    <div class="carousel-item">
	                        <a href="${ contextRoot }/public/product/${slideImg.picId.productId.PId}"><!-- product頁面 -->
	                            <img style="object-fit: cover;" width="650px" height="450px" src="${ contextRoot }${slideImg.filePath}" class="d-block" alt="...">
	                        </a>
                    </div>
	           			 </c:forEach>
                    </c:if>          
                </div>

            
                <button class="carousel-control-prev" type="button" data-target="#carouselExampleControls"
                    data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-target="#carouselExampleControls"
                    data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </button>
            </div>
            <!-- 網站介紹 -->
		<div class="mr-2"></div>
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
                <c:choose>
                	<c:when test="${pageContext.request.userPrincipal == null}">
                		<a href="${contextRoot}/public/user/sign">
                    		<input type="button" class="postbutton btn btn-dark" value="發起專案">
               			</a>
                	</c:when>
                	<c:otherwise>                	
           		     <a href="${contextRoot}/product">
           		         <input type="button" class="postbutton btn btn-dark" value="發起專案">
            	     </a>
                	</c:otherwise>
                </c:choose>
            </article>

        </div>
    </div>
    <!-- 種類大圖 -->
            <div class="box-container">

                <div class="row">

                    <div class="col">
                        <div class="pic">
                            <a href="${contextRoot}/public/category/1">
                                <img src="images/music.jpg" style="object-fit: cover;" width="400px" height="200px" />
                                <div class="info">
                                    <p>music</p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col">
                        <div class="pic">
                            <a href="${contextRoot}/public/category/2">
                                <img src="images/art.jpg" style="object-fit: cover;" width="400px" height="200px" />
                                <div class="info">
                                    <p>art</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>

			<div class="row">
                <div class="col">
                    <div class="pic">
                        <a href="${contextRoot}/public/category/3">
                            <img src="images/travel.jpg" style="object-fit: cover;" width="400px" height="200px" />
                            <div class="info">
                                <p>travel</p>
                            </div>
                        </a>
                    </div>
                </div>

                
                    <div class="col">
                        <div class="pic">
                            <a href="${contextRoot}/public/category/4">
                                <img src="images/game.jpg" style="object-fit: cover;" width="400px" height="200px" />
                                <div class="info">
                                    <p>game</p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col">
                        <div class="pic">
                            <a href="${contextRoot}/public/category/5">
                                <img src="images/craft.jpg" style="object-fit: cover;" width="400px" height="200px" />
                                <div class="info">
                                    <p>craft</p>
                                </div>
                            </a>
                        </div>
                    </div>

                </div>
            </div>

<jsp:include page="layout/footerForHome.jsp"></jsp:include>
	
    <!-- JS -->
    <script src="js/script1.js"></script>
</body>
</html>