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
          <style>
            .productPicture {
              outline: 2px red solid;
            }

            form {
              outline: red solid 2px;
            }

            .productPictureContainer {
              position: relative;
            }

            .editPicBtn {
              outline: greenyellow solid 2px;
              position: absolute;
              top: 50%;
              left: 50%;
            }
          </style>
          <script src="${ contextRoot }/js/jquery-3.6.3.min.js"></script>
      </head>

      <body>
        <jsp:include page="../layout/navbar.jsp"></jsp:include>
        <a href="${ contextRoot }/public/product/${ product.PId }">查看結果</a>
        <form action="#">
          <!-- 放contextRoot供js用 -->
          <input name="contextRoot" value="${ contextRoot }" type="hidden" id="contextRoot"> 
        </form>
        <br>
        <div class="container">
          <div class="productPictureContainer">
            <img id="pic_1" src="<c:url value="${ productPictures[(1).intValue()] }" />" width="200" class="img-thumbnail border-3
            m-3" alt="..." />
            <c:if test="${productPictures[(1).intValue()] != null}">
              <form action="${ contextRoot }/product/picture/api" method="post" class="deletePicBtn"
                onsubmit="return confirm('確定刪除?');">
                 <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="_method" value="delete" />
                <input name="productId" value="${ product.PId }" type="hidden">
                <input name="picNum" value="1" type="hidden">
                <input type="submit" value="刪除">
              </form>
            </c:if>
            <form action="${ contextRoot }/product/picture/api" method="post" class="editPicBtn" id="editPicBtn_1">
             <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
              <input name="productId" value="${ product.PId }" type="hidden">
              <input name="picNum" value="1" type="hidden">
              <input type="file" name="productPhoto" id="productPhoto_1" />
              <input type="submit" value="編輯">
            </form>
            <!-- <form action="{ contextRoot }/productPicture/edit" method="post" class="editPicBtn">
              <input name="productId" value="{ product.PId }" type="hidden">
              <input name="picNum" value="1" type="hidden">
              <input type="submit" value="編輯">
            </form> -->
          </div>
          <!--      could query out all pictures, but how to put to the proper position. how to show when swith tabs or need ajax? -->
          <c:forEach var="productPicture" items="${ productPictures }" varStatus="loop">
            <c:if test="${productPicture.key != 1}">
              <div class="productPictureContainer">
                <img src="<c:url value="${ productPicture.value }" />" width="200"
                class="img-thumbnail border-3 m-3 productPicture" alt="..." />
                <!-- <p>{{ productPictures[(loop.index).intValue()] }}</p> -->
                <!-- <img src="<c:url value="{{ productPictures[(loop.index).intValue()] }}" />" width="200" -->
                <!-- class="img-thumbnail border-3 m-3 productPicture" alt="..." /> -->
                <!-- 如果有圖才顯示刪除(foreach map需要?) 上傳圖片順序性 -->
                <c:if test="${ productPicture.value != null}">
                  <!-- js confirm before submit -->
                  <!-- https://stackoverflow.com/questions/6515502/javascript-form-submit-confirm-or-cancel-submission-dialog-box -->
                  <form action="${ contextRoot }/product/picture/api" method="post" class="deletePicBtn"
                    onsubmit="return confirm('確定刪除?');">
                     <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="hidden" name="_method" value="delete" />
                    <input name="productId" value="${ product.PId }" type="hidden">
                    <input name="picNum" value="${productPicture.key}" type="hidden">
                    <input type="submit" value="刪除">
                  </form>
                </c:if>
                <!-- 傳回productId、picNum、principle -->
                <form action="${ contextRoot }/productPicture/edit" method="post" class="editPicBtn">
                 <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                  <input name="productId" value="${ product.PId }" type="hidden">
                  <input name="picNum" value="${productPicture.key}" type="hidden">
                  <input type="submit" value="編輯">
                </form>
              </div>

            </c:if>
            <c:if test="${loop.last}">
              <form action="${ contextRoot }/productPicture/edit" method="post" class="addPicBtn">
               <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input name="productId" value="${ product.PId }" type="hidden">
                <input name="picNum" value="${productPicture.key + 1}" type="hidden">
                <input type="submit" value="新增圖片">
              </form>
            </c:if>
          </c:forEach>


          <div>
            <h3>${ product.title }</h3>
            <p>產品介紹先不加</p>
            <p>tab：產品動態、介紹圖文</p>
            <br />
            <h5>介紹：</h5>
            <p>${ product.intro }</p>
          </div>
          <form action="${ contextRoot }/product/${ product.PId }/purchasePlanList">
            <button type="submit" class="btn btn-dark">支持這個專案</button>
            <%-- <a class="btn btn-dark" href="${ contextRoot }/product/purchasePlanList">支持這個專案</a> --%>
          </form>
          <br />
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

                <form class="mb-3" method="get" action="${ contextRoot }/product/${ product.PId }/order">

                  <label for="purPlanId" class="form-label">專案ID：</label>

                  <input type="number" name="purPlanId" id="purPlanId" value="${ purPlanSet.purchasePlanId }"
                    readonly="readonly" class="form-control">
                  <br />

                  <label for="amount" class="form-label">選擇數量</label>
                  <input type="number" name="amount" id="amount" required="required" min="0" max="10"
                    class="form-control">
                  <button type="submit" class="btn btn-dark mt-3">支持</button>

                </form>
                <!-- 					<label for="amount" class="form-label">選擇數量</label> -->
                <!-- 						<input type="number" name="amount" id="amount" class="form-control" aria-describedby="basic-addon3"> -->
              </div>

            </div>
          </c:forEach>

        </div>
        <script>
          window.onload = function () {
            // 參考ajax p.63
            $("#editPicBtn_1").submit(function(event){
              event.preventDefault();
              var formData = new FormData(this);
              fetch($(this).attr("action"), {
                method: 'POST',
                body: formData
              })
              .then(function(resp){
                // 上傳完就從資料庫用ajax撈圖片回來
                return (async function(){
                  let respStatus = await resp.text();
                  console.log(respStatus);
                  if ( resp.ok ) {
                    // 要fetch新圖片路徑!!!
                    // let newImg = document.getElementById("pic_1").src;
                    let contextRoot = document.getElementById("contextRoot").value;
                    document.getElementById("pic_1").src = contextRoot + respStatus;
                  } else {
                    throw new Error(resp.status + resp.statusText + respStatus);
                  }
                })();
              })
              .catch(function(errMsg){
                console.log(errMsg);
              })
            })
          }
        </script>
      </body>

      </html>