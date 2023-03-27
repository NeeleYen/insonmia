<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>

<head>
<!--   <meta charset="UTF-8"> -->
<%--   <title>測試產品頁</title> 預計ajax抓商品名 --%>
<!--     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>product page</title>
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
  <jsp:include page="../layout/navbar.jsp"></jsp:include>
<%-- 	<jsp:include page="../layout/header.jsp"></jsp:include> --%>
  <br>
  <div class="container">
    <div class="row">

         <div class="col-8">
           <div>
              <%--                 <img src="<c:url value="${ productPictures[(1).intValue()] }" />" width="200" class="img-thumbnail border-3 m-3" --%>
              <!--                 alt="..." /> -->
              <!--                 <br> -->
					    <c:forEach var="entry" items="${productPictures}" varStatus="status">
					      <c:if test="${status.index == 0}">
					          <c:set var="picturePath" value="${entry.value}" />
					          <img src="${contextRoot}${picturePath}" width="200" class="img-thumbnail border-3 m-3" alt="..." />
					      </c:if>
					    </c:forEach>                
                
            </div>
            <div>
              <h3>${ product.title }</h3>
              <p>產品介紹先不加</p>
              <p>tab：產品動態、介紹圖文</p>
              <br />
              <h3>募資狀態：${fundationStatus}</h3>
              <h5>總目標：${fundationGoal}</h5>
              <h3>支持人數：${supporterCount}</h3>
              <h5>${dayLeft}天剩餘</h5>
              <h5>完成百分比${ (fundationStatus/fundationGoal) * 100}</h5>
              <!--           <h5>介紹：</h5> -->

            </div>
            <form action="${ contextRoot }/public/product/${ product.PId }/purchasePlanList">
              <button type="submit" class="btn btn-dark">支持這個專案</button>
            </form>
            <br />
            <ul class="nav nav-tabs" id="productPageTab" role="tablist">
              <li class="nav-item" role="presentation">
                <button class="nav-link active" id="intro-tab" data-bs-toggle="tab" data-bs-target="#intro"
                  type="button" role="tab" aria-controls="intro" aria-selected="true">產品介紹</button>
              </li>
              <li class="nav-item" role="presentation">
                <button class="nav-link" id="status-tab" data-bs-toggle="tab" data-bs-target="#status" type="button"
                  role="tab" aria-controls="status" aria-selected="false">產品動態</button>
              </li>
              <li class="nav-item" role="presentation">
                <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button"
                  role="tab" aria-controls="contact" aria-selected="false">Contact</button>
              </li>
            </ul>
            <div class="tab-content" id="productPageContent">
              <div class="tab-pane fade show active" id="intro" role="tabpanel" aria-labelledby="intro-tab">
                <br />
                <h5>產品介紹</h5>
                <p>${ product.intro }</p>
              </div>
              <div class="tab-pane fade" id="status" role="tabpanel" aria-labelledby="status-tab">
                <br />
                <h5>產品動態</h5>
              </div>
              <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
            </div>
          </div>

          <div class="col">
            <h3>購買方案</h3>
            <c:forEach var="purPlanSet" items="${ pPlanSet }">
              <div class="card" style="width: 20rem;">
                <div class="card-body">
                  <h5 class="card-title">${ purPlanSet.title }</h5>
                  <h6 class="card-subtitle mb-2 text-secondary">${ purPlanSet.price }</h6>
                  <h6 class="card-subtitle mb-2 text-secondary">${ purPlanSet.shipmentDate }</h6>
                  <c:forEach var="pPlanMap" items="${ pPlanMap }">
                    <h6 class="card-subtitle mb-2 text-success">${ pPlanMap }</h6>
                  </c:forEach>
                  <p class="card-text">${ purPlanSet.intro }</p>
                  <br />
                  <input type="hidden" name="_csrf" value="${_csrf.token}">
                  <form class="mb-3" method="get" action="${ contextRoot }/public/product/${ product.PId }/order">
                    <label for="purPlanId" class="form-label">專案ID：</label>
                    <input type="number" name="purPlanId" id="purPlanId" value="${ purPlanSet.purchasePlanId }"
                      readonly="readonly" class="form-control">
                    <br />
                    <label for="amount" class="form-label">選擇數量</label>
                    <input type="number" name="amount" id="amount" required="required" min="0" max="10"
                      class="form-control">
                    <button type="submit" class="btn btn-dark mt-3">支持</button>
                  </form>
                </div>
              </div>
            </c:forEach>
          </div>
    </div>

  </div>
</body>

</html>