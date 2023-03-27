<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:set var="contextRoot" value="${pageContext.request.contextPath}" />
    <!DOCTYPE html>
    <html>

    <head>
      <link rel="stylesheet" href="https://meyerweb.com/eric/tools/css/reset/reset.css">
      <link rel="stylesheet" href="${contextRoot}/css/bootstrap.min.css">
      <script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>
      <script src="${contextRoot}/js/jquery-ui.min.js"></script>
      <script src="${contextRoot}/js/bootstrap.bundle.min.js"></script>
      <meta charset="UTF-8">
      <title>修改專案</title>
    </head>

    <body>
      <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
      <input type="hidden" value="${contextRoot}" id="contextRoot">
      <div class="container">
        <h1 class="mt-5 mb-4">修改購賣方案${purchasePlan.purchasePlanId}</h1>
          
          <div class="card" style="width: 20rem;">
            <div class="card-body">
              <form id="editPurchasePlanForm" action="${contextRoot}/product/purchasePlan/form" method="post">
                <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="productId" value="${productId}" />
                <input type="hidden" name="purchasePlanId" value="${purchasePlan.purchasePlanId}" />
                <input type="hidden" name="_method" value="put" />
                
                <label for="title" class="form-label">方案名稱</label>
                <h5><input class="card-title form-control" id="title" name="title" placeholder="名稱"
                    type="text"></h5>

                <label for="price" class="form-label">方案價格</label>
                <h6><input class="card-subtitle mb-2 text-secondary form-control" id="price" name="price"
                    placeholder="價格" type="text"></h6>

                <label for="shipmentDate" class="form-label">預計交貨日</label>
                <h6><input class="card-subtitle mb-2 text-secondary form-control" id="shipmentDate"
                    name="shipmentDate" placeholder="交貨日" type="date"></h6>

                <label for="intro" class="form-label">方案介紹</label>
                <textarea class="card-text form-control" id="intro" name="intro"
                  placeholder="介紹"></textarea>


                <label for="minOrderAmount" class="form-label">最小所需銷售量(留空為不限)</label>
                <h6><input class="card-subtitle mb-2 text-secondary form-control"
                    id="minOrderAmount" name="minOrderAmount" placeholder="最小銷售量" type="text">
                </h6>

                <label for="maxOrderAmount" class="form-label">最大限制購買量(留空為不限)</label>
                <h6><input class="card-subtitle mb-2 text-secondary form-control"
                    id="maxOrderAmount" name="maxOrderAmount" placeholder="最大購買量" type="text">
                </h6>

                <input type="submit" class="btn btn-primary" value="修改">
              </form>
            </div>
          </div>
      </div>
      <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
      <script>
        window.onload = function (event) {
          fillPurchasePlanForm(event);
        }
        // 參考ajax p.56
        function fillPurchasePlanForm(event) {
          fetch('${contextRoot}/public/product/purchasePlan/${purchasePlan.purchasePlanId}/api',
            {
              method: 'get'
            }
          ).then(response => { // 直接跳轉，此行以下可省略
            return response.json();
          }).then(respJson => {
            console.log(respJson);
            if (respJson.result == null || respJson.result == undefined) {
              console.log(respJson.status);
              return respJson;
            }
            let result = respJson.result;
            document.getElementById("title").value = result.title;
            document.getElementById("intro").value = result.intro;
            document.getElementById("price").value = result.price;
            document.getElementById("minOrderAmount").value = result.minOrderAmount;
            document.getElementById("maxOrderAmount").value = result.maxOrderAmount;
            document.getElementById("shipmentDate").value = result.shipmentDate;
          })
        }
      </script>

    </body>

    </html>