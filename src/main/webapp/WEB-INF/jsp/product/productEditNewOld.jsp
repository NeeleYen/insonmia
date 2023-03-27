<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

      <c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
      <!DOCTYPE html>
      <html>

      <head>
        <meta charset="UTF-8">
        <title>測試產品頁</title> <%--預計ajax抓商品名 --%>
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
      </head>

      <body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
  <form action="#">
    <!-- 放contextRoot供js用 -->
    <input name="contextRoot" value="${ contextRoot }" type="hidden" id="contextRoot"> 
  </form>
        <br>
        <div class="container">
          <div class="row">

            <div class="col-8">
              <div>
                <%-- <img src="<c:url value=" ${ productPictures[(1).intValue()] }" />" width="200" class="img-thumbnail
                border-3 m-3" --%>
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
                    <c:forEach var="pPlanMapEach" items="${ pPlanMap }">
                      <h6 class="card-subtitle mb-2 text-success">${ pPlanMapEach.key }</h6>
                      <h6 class="card-subtitle mb-2 text-success">${ pPlanMapEach.value }</h6>
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

              <form id="addPurchasePlanForm">
                <div class="card" style="width: 20rem;">
                  <div class="card-body">
                    <h5><input class="card-title" id="title" name="title" placeholder="方案名稱" type="text"></h5>
                    <h6><input class="card-subtitle mb-2 text-secondary" id="price" name="price" placeholder="方案價格" type="text"></h6>
                    <h6><input class="card-subtitle mb-2 text-secondary" id="shipmentDate" name="shipmentDate" placeholder="預計交貨日" type="date"></h6>
                    <c:forEach var="pPlanMapValue" items="${ pPlanMap }">
                      <h6 class="card-subtitle mb-2 text-success">${ pPlanMapValue.key }</h6>
                      <h6 class="card-subtitle mb-2 text-success">${ pPlanMapValue.value }</h6>
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
              </form>
            </div>
          </div>

        </div>
 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
      </body>

      </html>