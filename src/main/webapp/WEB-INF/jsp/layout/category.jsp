<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />                
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>類型頁</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	
    <div class="container">
        <h2>${ categoryValue } page <a class="btn btn-outline-dark float-right" href="${ contextRoot }/public/category/${category}/list">清單</a></h2>
        <h6>-${ categoryValue } intro</h6>
        <div class="row space1">
<!--           精選專案 -->
<%--           <c:forEach var="pop" items="${ popthumbNailSet }"> --%>
            <div class="m-3">
                <a href="${ contextRoot }/public/product/${ popthumbNailSet.picId.productId.PId }">
                	<img src="${ contextRoot }${popthumbNailSet.filePath}" style="object-fit: cover;" alt="精選專案"  width="600px"
                        height="350px">
                </a>
                <h3 class="space1 text-truncate" style="white-space: nowrap; overflow: hidden; width: 600px;">${ popthumbNailSet.picId.productId.title }</h3>
                <p class="space2 text-truncate" style="white-space: nowrap; overflow: hidden; width: 600px;">${ popthumbNailSet.picId.productId.intro }
                </p>
                <h6 class="spce1">發起人：${ popthumbNailSet.picId.productId.user.username }</h6>
            </div>
<%-- 		  </c:forEach> --%>

            <!-- 垂直分割線 -->
            <div class="vertical-rule"></div>


            <div class="suggest col">
                <div class="row">                

<%-- 				  <c:choose> --%>
<%-- 				  	<c:when test="${ empty entrySetNew }"> --%>
<!-- 				  		有東西 -->
<%-- 				  		${ entrySetNew.toString() } --%>
<%-- 				  	</c:when> --%>
<%-- 				  	<c:otherwise>沒東西</c:otherwise> --%>
<%-- 				  </c:choose> --%>
				  
<%-- 				  <c:forEach items="${ forNewestMap }" var="map"> --%>
<%-- 				  	<h3>${ map.key }</h3> --%>
<%-- 				  	<h3>${ map.value }</h3> --%>
<%-- 				  </c:forEach> --%>
<%-- 				  <c:forEach items="${ forNewestMap2 }" var="map"> --%>
<%-- 				  	<h3>${ map.key.filePath }</h3> --%>
<%-- 				  	<h3>${ map.value }</h3> --%>
<%-- 				  </c:forEach> --%>
				  
                    <!-- 最新專案 -->
                  <c:forEach var="newest" items="${ forNewestMap2 }">
                  	
                    <a href="${ contextRoot }/public/product/${ newest.key.picId.productId.PId }" class="m-3">
                    	<img src="${ contextRoot }${newest.key.filePath}" style="object-fit: cover;" alt="最新專案" 
                            width="200px" height="100px">
                    </a>
                    <div class="space4">
                        <div>
                            <h5 class="text-truncate">
                              <a href="${ contextRoot }/public/product/${ newest.key.picId.productId.PId }" class="text-dark">
                                ${ newest.key.picId.productId.title }
                              </a>
                            </h5>
                        </div>
                        <div>
                            <h6 class="space text-success text-truncate">${ newest.value }</h6><!-- 已籌集  -->
                        </div>
                        <div>
                            <h6 class="space text-truncate">發起人：${ newest.key.picId.productId.user.username }</h6>
                        </div>
                    </div>
				  </c:forEach>

                </div>


            </div>
        </div>
    </div>
<!--     </div> -->


    <hr>


    <div class="container space3">
        <div class="row">
            <a href="${contextRoot}/product"><img src="${ contextRoot }/images/logoBrain.jpg" style="object-fit: cover;" width="600px" height="300px"></a>
            <div class="col">
                <h2 class="m-3">發起專案</h2>
                <p class="m-3">募資網站是一種在線平台，允許個人、企業和組織在網上
                    募集資金。這些平台允許創業家和項目發起人通過網絡在
                    眾多投資者中尋找投資。募資網站通常提供了一個網上表
                    單，方便創業家們提交他們的計劃，並多種支付方式接受
                    投資。</p>
                <a href="${contextRoot}/product">
                    <input type="button" class="postbutton btn btn-dark m-1" value="發起專案">                   
                </a>
            </div>





        </div>
    </div>
	
	<jsp:include page="../layout/footerForCategory.jsp"></jsp:include>
</body>
</html>