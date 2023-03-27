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
      <style>
      
      input[type="file"] {
		  width: 500px;
		}
      
        html,
        body {
          height: 100%;
        }

        #addedItemsDiv {
          margin: 0 auto;
          /* height: 600px; */
          overflow: auto;
        }

        #addedFaqsDiv {
          margin: 0 auto;
          /* height: 600px; */
          overflow: auto;
        }

        #PurchasePlansDiv {
          margin: 0 auto;
          /* height: 600px; */
          overflow: auto;
        }

        .card {
          float: left;
        }
      </style>
      <title>新增購買方案</title>
    </head>

    <body>
      <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
      <form action="#">
        <!-- 放contextRoot供js用 -->
        <input name="contextRoot" value="${ contextRoot }" type="hidden" id="contextRoot">
      </form>
      <div class="container">
        <a class="btn btn-outline-dark" href="${ contextRoot }/public/product/${productId}">查看結果</a>
        <a class="btn btn-outline-dark" href="${ contextRoot }/product">我的專案列表</a>
        <h1 class="mt-5 mb-4 text-center">新增購買方案</h1>

        <div class="accordion" id="accordionExample">
          <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                新增專案品項
              </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
              data-bs-parent="#accordionExample">
              <div class="accordion-body">

                <!--         <h2 class= "mt-5 mb-4">新增專案品項</h2> -->
                <div class="container">
                  <div id="addedItemsDiv">
                    <form id="addProductItemForm" action="${contextRoot}/product/item/api" method="post">
                      <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                      <div class="mb-3">
                        <input type="hidden" id="productId" name="productId" value="${productId}" /><br>
                        <label for="title" class="form-label">新增品項</label> <input id="itemName" class="form-control"
                          name="itemName" required /><br>
                        <input type="submit" id="submitBtn" class="mt-2 btn btn-outline-dark" value="送出"></input><br>
                      </div>
                    </form>
                    <table id="addedItems" class="table"> <!--  table-hover沒效? -->

                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="accordion mt-2" id="accordionExample2">
          <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">新增專案FAQ
              </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
              data-bs-parent="#accordionExample2">
              <div class="accordion-body">


                <div class="container">
                  <div id="addedFaqsDiv">
                    <form id="addProductFaqForm">
                      <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                      <input type="hidden" name="productId" value="${productId}" /><br>
                      <label for="question" class="form-label">新增Question</label> <input class="form-control"
                        id="question" name="question" required /><br>
                      <label for="answer" class="form-label">新增Answer</label> <input class="form-control" id="answer"
                        name="answer" required /><br>
                      <input type="submit" id="submitBtn" value="送出" class="mt-2 btn btn-outline-dark"></input><br>
                    </form>
                    <table id="addedFaqs" class="table table-hover mt-3">

                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="accordion mt-2" id="accordionExample3">
          <div class="accordion-item">
            <h2 class="accordion-header" id="headingThree">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">新增購買方案
              </button>
            </h2>
            <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree"
              data-bs-parent="#accordionExample3">
              <div class="accordion-body">
                <div class="container">
                  <div id="PurchasePlansDiv">
                    <div id="addedPurchasePlans">
                    </div>
                    <div class="card" style="width: 20rem;">
                      <div class="card-body">
                        <form id="addPurchasePlanForm">
                          <input type="hidden" name="_csrf" value="${_csrf.token}">
                          <input type="hidden" name="productId" value="${productId}" /><br>

                          <label for="title" class="form-label">方案名稱</label>
                          <h5><input class="card-title form-control" id="title" name="title" placeholder="名稱"
                              type="text" required="required"></h5>

                          <label for="price" class="form-label">方案價格</label>
                          <h6><input class="card-subtitle mb-2 text-secondary form-control" id="price" name="price"
                              placeholder="價格" type="text" required="required"></h6>

                          <label for="shipmentDate" class="form-label">預計交貨日</label>
                          <h6><input class="card-subtitle mb-2 text-secondary form-control" id="shipmentDate"
                              name="shipmentDate" placeholder="交貨日" type="date" required="required"></h6>

                          <label for="intro" class="form-label">方案介紹</label>
                          <textarea class="card-text form-control" id="purchasePlanIntro" name="intro"
                            placeholder="介紹"></textarea>


                          <label for="minOrderAmount" class="form-label">最小所需銷售量(留空為不限)</label>
                          <h6><input class="card-subtitle mb-2 text-secondary form-control"
                              id="purchasePlan_minOrderAmount" name="minOrderAmount" placeholder="最小銷售量" type="text">
                          </h6>

                          <label for="maxOrderAmount" class="form-label">最大限制購買量(留空為不限)</label>
                          <h6><input class="card-subtitle mb-2 text-secondary form-control"
                              id="purchasePlan_maxOrderAmount" name="maxOrderAmount" placeholder="最大購買量" type="text">
                          </h6>

                          <input type="submit" class="btn btn-outline-dark" value="新增">
                        </form>
                      </div>
                    </div>
                  </div>
                </div>

                <div id="productPictureDiv">
                </div>



                <!-- here to add new -->
                <!-- 暫時提供上傳100張圖片 -->


              </div>
            </div>
          </div>
        </div>

        <div class="accordion mt-2" id="accordionExample4">
          <div class="accordion-item">
            <h2 class="accordion-header" id="headingFour">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">編輯專案圖片
              </button>
            </h2>
            <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour"
              data-bs-parent="#accordionExample4">
              <div class="accordion-body">

                <div class="container">
                   <h2 class="mt-5 mb-4">編輯專案圖片</h2>
                   <div id="addedProductPictures">

	               </div>
<%--                   <form action="${contextRoot}/editProduct/${productId}" method="get"> --%>
<!--                     <input type="submit" value="新增圖片"> -->
<!--                   </form> -->
<%--                   <form action="${contextRoot}/createProductFAQ" method="get"> --%>
<%--                     <input type="hidden" name="productId" value="${productId}"> --%>
<!--                     <input type="submit" value="新增FAQ"> -->
<!--                   </form> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>

      <script>
        let temp = null;
        window.onload = function () {
          // show all datas at initial
          showItems(event);
          showFaqs(event);
          showPurchasePlans(event);
          showProductPictures(event);
        }
        // ===== productPicture part start =====
        function showProductPictures(event) {
          const productId = document.getElementById("productId").value;
          const contextRoot = document.getElementById("contextRoot").value;
          document.getElementById("addedProductPictures").innerHTML = "";
          fetch("${contextRoot}/public/product/" + productId + "/picture/api")
            .then(function (response) {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error(response.status + ":" + response.statusText);
              }
            }).then(function (itemsJson) {
              if (itemsJson.status == 'success') {
                for (let i = 1; i <= 100; i++) {
                  let editProductPictureForm = `<form id="editProductPicture_` + i + `" >`;
                  // editProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                  editProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                  editProductPictureForm += `<input type="hidden" name="picNum" value="` + i + `">`;
                  editProductPictureForm += `<input class="form-control" type="file" name="productPhoto" required="required">`;
                  editProductPictureForm += `<input type="submit" value="編輯" class="btn btn-outline-warning"></input>`;
                  editProductPictureForm += `</form>`;
                  if (itemsJson.result[i] == undefined) { // 該位置無圖片
                    let editProductPictureForm = `<form id="editProductPicture_` + i + `" >`;
                    // editProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                    editProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                    editProductPictureForm += `<input type="hidden" name="picNum" value="` + i + `">`;
                    editProductPictureForm += `<input class="form-control" type="file" name="productPhoto" required="required">`;
                    editProductPictureForm += `<input type="submit" value="編輯" class="btn btn-outline-warning"></input>`;
                    editProductPictureForm += `</form>`;
                    $("#addedProductPictures").append(`<div id="productPictureDiv_` + i + `"></div>` +
                      `<div id="productPictureEditButtonDiv_` + i + `">` + editProductPictureForm + `</div>` +
                      `<div id="productPictureDeleteButtonDiv_` + i + `"></div>`);
                  } else {
                    let deleteProductPictureForm = `<form id="deleteProductPicture_` + i + `" >`;
                    deleteProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                    deleteProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                    deleteProductPictureForm += `<input type="hidden" name="picNum" value="` + i + `">`;
                    deleteProductPictureForm += `<input type="submit" value="刪除" class="btn btn-outline-danger"></input>`;
                    deleteProductPictureForm += `</form>`;
                    $("#addedProductPictures").append(`<div id="productPictureDiv_` + i + `">
                      <img id="pic_` + i + `" src="` + contextRoot + itemsJson.result[i] + `" width="500" ></div>` +
                      `<div id="productPictureEditButtonDiv_` + i + `">` + editProductPictureForm + `</div>` +
                      `<div id="productPictureDeleteButtonDiv_` + i + `">` + deleteProductPictureForm + `</div>`);
                    document.getElementById("deleteProductPicture_" + i).removeEventListener('click', deleteProductPicture);
                    document.getElementById("deleteProductPicture_" + i).addEventListener('click', deleteProductPicture);
                  }
                  document.getElementById("editProductPicture_" + i).removeEventListener('click', editProductPicture);
                  document.getElementById("editProductPicture_" + i).addEventListener('click', editProductPicture);
                  console.log(itemsJson.result[i]);
                }
              } else if (itemsJson.status == 'empty') { // 如果該product完全沒有圖片
                for (let i = 1; i <= 100; i++) {
                  let editProductPictureForm = `<form id="editProductPicture_` + i + `" >`;
                  // editProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                  editProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                  editProductPictureForm += `<input type="hidden" name="picNum" value="` + i + `">`;
                  editProductPictureForm += `<input class="form-control" type="file" name="productPhoto" required="required">`;
                  editProductPictureForm += `<input type="submit" value="編輯" class="btn btn-outline-warning"></input>`;
                  editProductPictureForm += `</form>`;
                  $("#addedProductPictures").append(`<div id="productPictureDiv_` + i + `"></div>` +
                    `<div id="productPictureEditButtonDiv_` + i + `">` + editProductPictureForm + `</div>` +
                    `<div id="productPictureDeleteButtonDiv_` + i + `"></div>`);
                  document.getElementById("editProductPicture_" + i).removeEventListener('click', editProductPicture);
                  document.getElementById("editProductPicture_" + i).addEventListener('click', editProductPicture);
                }
              } else { // 其他錯誤 // TODO 錯誤顯示方式(有空的話)
                console.log(itemsJson);
              }
            })
        }
        function deleteProductPicture(event) {
          let contextRoot = document.getElementById("contextRoot").value;
          const productId = document.getElementById("productId").value;
          $(this).submit(function (event) {
            event.preventDefault();
            const csrfToken = document.getElementById('csrf').value;
            var formData = new FormData(this);
            let picNum = formData.get('picNum');
            fetch('${contextRoot}/product/picture/api', {
              method: 'DELETE',
              body: formData,
              headers: {
                'X-CSRF-Token': csrfToken
              }
              // headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            })
              .then(function (resolve) {
                return resolve.text();
                // showPurchasePlans(event);
              })
              .then(function (statusText) {
                console.log(statusText);
                if (statusText == 'success') {
                  let editProductPictureForm = `<form id="editProductPicture_` + picNum + `" >`;
                  // editProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                  editProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                  editProductPictureForm += `<input type="hidden" name="picNum" value="` + picNum + `">`;
                  editProductPictureForm += `<input type="file" name="productPhoto" required="required">`;
                  editProductPictureForm += `<input type="submit" value="編輯" class="btn btn-outline-warning"></input>`;
                  editProductPictureForm += `</form>`;
                  document.getElementById(`productPictureDiv_` + picNum).innerHTML = "";
                  document.getElementById(`productPictureDeleteButtonDiv_` + picNum).innerHTML = "";
                  // $("#addedProductPictures").append(`<div id="productPictureDiv_` + picNum + `"></div>` +
                  // `<div id="productPictureEditButtonDiv_` + picNum + `">` + editProductPictureForm + `</div>` +
                  // `<div id="productPictureDeleteButtonDiv_` + picNum + `"></div>`);
                }
              })
          })
        }
        function editProductPicture(event) {
          let contextRoot = document.getElementById("contextRoot").value;
          const productId = document.getElementById("productId").value;
          $(this).submit(function (event) {
            event.preventDefault();
            const csrfToken = document.getElementById('csrf').value;
            var formData = new FormData(this);
            this.reset();
            let picNum = formData.get('picNum');
            fetch("${contextRoot}/product/picture/api", {
              method: 'POST',
              body: formData,
              headers: {
                'X-CSRF-Token': csrfToken
              }
            }) // TODO here
              .then(function (resp) {
                // 上傳完就從資料庫用ajax撈圖片回來
                return (async function () {
                  let respStatus = await resp.text();
                  console.log(respStatus);
                  if (resp.ok) {
                    // 要fetch新圖片路徑!!!
                    // 如果原來圖片尚不存在，append在指定元素內
                    if (document.getElementById("pic_" + picNum) == null) {
                      console.log(`新增` + picNum);
                      let deleteProductPictureForm = `<form id="deleteProductPicture_` + picNum + `" >`;
                      deleteProductPictureForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                      deleteProductPictureForm += `<input type="hidden" name="productId" value="` + productId + `">`;
                      deleteProductPictureForm += `<input type="hidden" name="picNum" value="` + picNum + `">`;
                      deleteProductPictureForm += `<input type="submit" value="刪除" class="btn btn-outline-danger"></input>`;
                      deleteProductPictureForm += `</form>`;
                      document.getElementById("productPictureDiv_" + picNum).innerHTML = "";
                      $("#productPictureDiv_" + picNum).append(`
                        <img id="pic_` + picNum + `" src="` + contextRoot + respStatus + `" width="500" >
                      `);
                      document.getElementById("productPictureDeleteButtonDiv_" + picNum).innerHTML = "";
                      $("#productPictureDeleteButtonDiv_" + picNum).append(deleteProductPictureForm);
                      document.getElementById("deleteProductPicture_" + picNum).removeEventListener('click', deleteProductPicture);
                      document.getElementById("deleteProductPicture_" + picNum).addEventListener('click', deleteProductPicture);
                    } else {
                      document.getElementById("pic_" + picNum).src = contextRoot + respStatus;
                    }
                  } else {
                    throw new Error(resp.status + resp.statusText + respStatus);
                  }
                })();
              })
              .catch(function (errMsg) {
                console.log(errMsg);
              })
          })
        }
        // ===== productPicture part end =====
        // ===== purchasePlan part start =====
        async function showPurchasePlans(event) {
          const productId = document.getElementById("productId").value;
          document.getElementById("addedPurchasePlans").innerHTML = "";
          // 撈取該product下所有items(by promise?)
          await fetch("${contextRoot}/public/product/" + productId + "/purchasePlan/api")
            .then(function (response) {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error(response.status + ":" + response.statusText);
              }
            })
            .then(function (itemsJson) {
              $.each(itemsJson.result, function (index, value) {
                let deletePurchasePlanForm = `<form id="deletePurchasePlan_` + value.purchasePlanId + `" >`;
                deletePurchasePlanForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                deletePurchasePlanForm += `<input type="hidden" name="purchasePlanId" value="` + value.purchasePlanId + `">`;
                // TODO 刪除修改
                deletePurchasePlanForm += `<input type="submit" value="刪除" class="btn btn-outline-danger ml-2"></input>`;
                deletePurchasePlanForm += `</form></div>`;


                let addPurchasePlanItemForm = `<form id="addPurchasePlanItem_` + value.purchasePlanId + `" method="post" action="${contextRoot}/product/purchasePlan/purchasePlanItem/form" >`; // need id?
                addPurchasePlanItemForm += `<h6><label class="card-subtitle mb-2 text-secondary mt-3" for="addPurchasePlanItemFormOptions_` + value.purchasePlanId + `">新增品項至購買方案:</label></h6>`;
                addPurchasePlanItemForm += `<select id="addPurchasePlanItemFormOptions_` + value.purchasePlanId + `" class="form-select form-select-sm" name="itemId" required="required"></select>`;
                addPurchasePlanItemForm += `<label class="mt-2 form-label">數量:</label><input  class="form-control" type="number" name="amount" min="1" required="required">`;
                addPurchasePlanItemForm += `<input type="hidden" name="purchasePlanId" value="` + value.purchasePlanId + `">`;
                addPurchasePlanItemForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                addPurchasePlanItemForm += `<input type="submit" value="新增" class="btn btn-outline-dark mt-2"></input></form>`;

                let addPurchasePlanShippingAreaForm = `<form id="addPurchasePlanShippingArea_` + value.purchasePlanId + `" method="post" action="${contextRoot}/product/purchasePlan/area/form" >`; // need id?
                addPurchasePlanShippingAreaForm += `<h6><label class="card-subtitle mb-2 text-secondary mt-3" for="addPurchasePlanShippingAreaFormOptions_` + value.purchasePlanId + `">新增運送地區至購買方案:</label></h6>`;
                addPurchasePlanShippingAreaForm += `<select id="addPurchasePlanShippingAreaFormOptions_` + value.purchasePlanId + `" class="form-select form-select-sm" name="areaId" required="required"></select>`;
                addPurchasePlanShippingAreaForm += `<input type="hidden" name="purchasePlanId" value="` + value.purchasePlanId + `">`;
                addPurchasePlanShippingAreaForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                addPurchasePlanShippingAreaForm += `<input type="submit" value="新增" class="btn btn-outline-dark mt-2"></input></form>`;
				
             	// TODO 編輯修改
                let editPurchasePlanBtn = `<div style="display: flex;"><a href="${contextRoot}/product/` + productId + `/purchasePlan/edit/` + value.purchasePlanId + `" class="btn btn-outline-warning">編輯</a>`

                $("#addedPurchasePlans").append(
                  `<div class="card" style="width: 20rem;"><div class="card-body">` +
                  `<div class="mb-4"></div>` +
                  `<h5 class="mb-3 card-title">` + value.title + `</h5>` +
                  `<h6 class="card-subtitle mb-3 text-secondary">` + "價格：$" + value.price + `</h6>` +
                  `<h6 class="card-subtitle mb-3 text-secondary">` + "預估交貨：" + value.shipmentDate + `</h6>` +
                  `<div style="justify-content: space-between;" id="addedPurchasePlans_` + value.purchasePlanId + `" class="card-subtitle mb-2 text-success"></div>` +
                  `<div id="purchasePlanArea_` + value.purchasePlanId + `" class="card-subtitle mb-3 text-secondary">運送地區：</div>` +
                  `<p class="card-text mb-3 ">` + value.intro + `</p>` +
                  `<label for="purPlanId" class="form-label">專案ID：</label>` +
                  `<input type="number" name="purPlanId" id="purPlanId" value="` + value.purchasePlanId + `" readonly="readonly" class="form-control" />` +
                  `<h6 class="card-subtitle mb-3 text-secondary mt-3">` + `購買量限制：` + value.minOrderAmount + ` ~ ` + value.maxOrderAmount + `</h6>` +
                  editPurchasePlanBtn + deletePurchasePlanForm + addPurchasePlanItemForm + addPurchasePlanShippingAreaForm +

                  `</div></div>`
                );
                document.getElementById("deletePurchasePlan_" + value.purchasePlanId).removeEventListener('click', deletePurchasePlan);
                document.getElementById("deletePurchasePlan_" + value.purchasePlanId).addEventListener('click', deletePurchasePlan);
                // 使用fetch會發送多重request多次新增同筆purchasePlanShippingArea狀況暫時無法解決，改用一般的form方法新增
                // document.getElementById("addPurchasePlanShippingArea_" + value.purchasePlanId).removeEventListener('click', addPurchasePlanShippingArea);
                // document.getElementById("addPurchasePlanShippingArea_" + value.purchasePlanId).addEventListener('click', addPurchasePlanShippingArea);
                // 使用fetch會發送多重request多次新增同筆purchasePlanItem狀況暫時無法解決，改用一般的form方法新增
                // document.getElementById("addPurchasePlanItem_" + value.purchasePlanId).removeEventListener('click', addPurchasePlanItem);
                // document.getElementById("addPurchasePlanItem_" + value.purchasePlanId).addEventListener('click', addPurchasePlanItem);
              });
              temp = itemsJson;
            })
          // 使用global temp傳遞fetch result
          // use for of (foreach) to fetch purchasePlanItems
          for (let oneResult of temp.result) {
            // console.log(oneResult.purchasePlanId);
            let purchasePlanId = oneResult.purchasePlanId;
            await fetch("${contextRoot}/public/product/purchasePlan/" + purchasePlanId + "/purchasePlanItem/api")
              .then(function (response) {
                if (response.ok) {
                  return response.json();
                } else {
                  throw new Error(response.status + ":" + response.statusText);
                }
              })
              .then(function (itemsJson) {
                if (itemsJson.result != null) {
                  for (let oneItem of itemsJson.result) {
                    let itemsToAppend =
                      `<div style="display: flex; justify-content: space-between;">` +
                      `<h6>` + oneItem.fk_itemName + `-` + oneItem.amount + `</h6>`;
                    let deletePurchasePlanItemForm = `<form id="deletePurchasePlanItem_` + oneItem.fk_purchasePlanId + `" >`;
                    deletePurchasePlanItemForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                    deletePurchasePlanItemForm += `<input type="hidden" name="purchasePlanId" value="` + oneItem.fk_purchasePlanId + `">`;
                    deletePurchasePlanItemForm += `<input type="hidden" name="itemId" value="` + oneItem.fk_itemId + `">`;
                    deletePurchasePlanItemForm += `<input style="margin-top: -0.8rem; padding: 0.1rem 0.5rem; font-size: 0.8rem; " type="submit" value="-" class="btn btn-outline-danger"></input>`;
                    deletePurchasePlanItemForm += `</form></div>`;
                    $("#addedPurchasePlans_" + purchasePlanId).append(itemsToAppend + deletePurchasePlanItemForm);
                    // remove and add eventListener
                    document.getElementById("deletePurchasePlanItem_" + oneItem.fk_purchasePlanId).removeEventListener('click', deletePurchasePlanItem);
                    document.getElementById("deletePurchasePlanItem_" + oneItem.fk_purchasePlanId).addEventListener('click', deletePurchasePlanItem);
                  }
                }
                // console.log(itemsJson);
                // reset temp value
                // temp = null;
                return itemsJson;
              })
          }
          for (let oneResult of temp.result) {
            let purchasePlanId = oneResult.purchasePlanId;
            await fetch("${contextRoot}/public/product/purchasePlan/" + purchasePlanId + "/unusedPurchasePlanItem/api")
              .then(function (response) {
                if (response.ok) {
                  return response.json();
                } else {
                  throw new Error(response.status + ":" + response.statusText);
                }
              })
              .then(function (itemsJson) {
                if (itemsJson.result != null) {
                  for (let oneItem of itemsJson.result) { // here
                    // <option value="volvo">Volvo</option>
                    let itemsToAppend = `<option value="` + oneItem.itemId + `">` + oneItem.itemName + `</option>`;
                    $("#addPurchasePlanItemFormOptions_" + purchasePlanId).append(itemsToAppend);
                  }
                }
                // console.log(itemsJson);
                // reset temp value
                return itemsJson;
              })
          }
          await showShippingAreas(temp);
          await showUnusedPurchasePlanShippingArea(temp);
        }
        function deletePurchasePlan(event) {
          $(this).submit(function (event) {
            event.preventDefault();
            fetch('${contextRoot}/product/purchasePlan/api', {
              method: 'DELETE',
              body: $(this).serialize(),
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            })
              .then(function (resolve) {
                showPurchasePlans(event);
              })
          })
        }
        // 參考ajax p.56
        $("#addPurchasePlanForm").submit(function (event) {
          event.preventDefault();

          fetch('${contextRoot}/product/purchasePlan/api',
            {
              method: 'POST',
              body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
            }
          ).then(response => { // 直接跳轉，此行以下可省略
            return response.json();
          }).then(respJson => {
            console.log(respJson);
            if (respJson.status == "success") {
              showPurchasePlans(event);
            } else {
              document.getElementById("addedPurchasePlans").innerHTML = respJson;
            }
          })

        });
        // sub part purchasePlanItem start
        function deletePurchasePlanItem(event) {
          $(this).submit(function (event) {
            event.preventDefault();
            fetch('${contextRoot}/product/purchasePlan/purchasePlanItem/api', {
              method: 'DELETE',
              body: $(this).serialize(),
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            })
              .then(function (resolve) {
                showPurchasePlans(event);
              })
          })
        }
        // 參考ajax p.56
        // 使用fetch會發送多重request多次新增同筆purchasePlanItem狀況暫時無法解決，改用一般的form方法新增
        function addPurchasePlanItem(event) { // fetch問題(是因為js的同步執行問題?)暫時不用
          $(this).submit(function (event) {
            event.preventDefault();
            fetch('${contextRoot}/product/purchasePlan/purchasePlanItem/api',
              {
                method: 'POST',
                body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
              }
            ).then(response => { // 直接跳轉，此行以下可省略
              return response.text();
            }).then(respJson => {
              console.log(respJson);
              if (respJson == "新增成功") {
                showPurchasePlans(event);
              } else {
                document.getElementById("addedPurchasePlans").innerHTML = respJson;
              }
            })
          })
        }
        // sub part purchasePlanItem end
        // ===== purchasePlan part end =====
        // ==== item part start =====
        function showItems(event) {
          const productId = document.getElementById("productId").value;
          document.getElementById("addedItems").innerHTML = "";
          // 撈取該product下所有items(by promise?)
          fetch("${contextRoot}/product/items/api/" + productId)
            .then(function (response) {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error(response.status + ":" + response.statusText);
              }
            })
            // <form>
            //   <input type="hidden" name="itemId" value="" />
            //   <input type="submit">刪除</input>
            // </form>
            .then(function (itemsJson) {
              $("#addedItems").append(`<tr scope='col'><th scope="col" class="text-center">項目</th><th scope="col" class="text-center">品名</th><th scope="col" class="text-center">刪除</th></tr>`);
              $.each(itemsJson, function (index, value) {
                let deleteItemForm = `<form id="deleteItem_` + value.itemId + `" >`;
                deleteItemForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                deleteItemForm += `<input type="hidden" name="itemId" value="` + value.itemId + `">`;
                deleteItemForm += `<input type="submit" value="刪除" class="btn btn-outline-danger"></input>`;
                deleteItemForm += `</form>`;
                $("#addedItems").append(`<tr scope='row'><td scope="row" class="text-center">` + index + `</td><td scope="row" class="text-center">` + value.itemName + `</td><td scope="row" class="text-center">` + deleteItemForm + "</td></tr>");
                document.getElementById("deleteItem_" + value.itemId).removeEventListener('click', deleteItem);
                document.getElementById("deleteItem_" + value.itemId).addEventListener('click', deleteItem);
              });
              // document.getElementById("addedItems").innerHTML
              //   = itemsTable;
            })
            .catch(function (errMsg) {
              document.getElementById("addedItems").innerHTML
                = "<p>" + errMsg + "</p>";
            })
        }
        function deleteItem(event) {
          $(this).submit(function (event) {
            event.preventDefault();
            fetch('${contextRoot}/product/item/api', {
              method: 'DELETE',
              body: $(this).serialize(),
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            }
            )
              .then(function (resolve) {
                showItems(event);
                showPurchasePlans(event); // TODO 刷新purchasePlan可新增item列表(有時間改做個別刷新不做全體刷新? 還是這樣就好，避免已有使用該item的purchasePlan沒有更新到?)
              })
          })
        }
        // 參考ajax p.56
        $("#addProductItemForm").submit(function (event) {
          event.preventDefault();

          fetch('${contextRoot}/product/item/api',
            {
              method: 'POST',
              body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
            }
          ).then(response => { // 直接跳轉，此行以下可省略
            return response.text();
          }).then(respText => {
            console.log(respText);
            if ("儲存成功" == respText) {
              showItems(event);
              showPurchasePlans(event); // TODO 刷新purchasePlan可新增item列表(有時間改做個別刷新不做全體刷新? 還是這樣就好，避免已有使用該item的purchasePlan沒有更新到?)
            } else {
              document.getElementById("addedItems").innerHTML = respText;
            }
          })

        });
        // ===== item part end =====
        // ====== FAQ part start =====
        function showFaqs(event) {
          const productId = document.getElementById("productId").value;
          document.getElementById("addedFaqs").innerHTML = "";
          // 撈取該product下所有items(by promise?)
          fetch("${contextRoot}/public/product/faqs/api/" + productId)
            .then(function (response) {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error(response.status + ":" + response.statusText);
              }
            })
            .then(function (itemsJson) {
              // <form>
              //   <input type="hidden" name="itemId" value="" />
              //   <input type="submit">刪除</input>
              // </form>
              $("#addedFaqs").append(`<tr scope='col'><th scope="col" class="text-center">問題</th><th scope="col" class="text-center">回答</th><th scope="col" class="text-center">刪除</th></tr>`);
              $.each(itemsJson, function (index, value) {
                let deleteFaqForm = `<form id="deleteFaq_` + value.faqid + `" >`;
                deleteFaqForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                deleteFaqForm += `<input type="hidden" name="faqId" value="` + value.faqid + `">`;
                deleteFaqForm += `<input type="submit" class="btn btn-outline-danger" value="刪除"></input>`;
                deleteFaqForm += `</form>`;
                $("#addedFaqs").append(`<tr scope="row" class="text-center"><td>` + value.question + `</td><td scope="row" class="text-center">` + value.answer + `</td><td scope="row" class="text-center">` + deleteFaqForm + "</td></tr>");
                document.getElementById("deleteFaq_" + value.faqid).removeEventListener('click', deleteFaq);
                document.getElementById("deleteFaq_" + value.faqid).addEventListener('click', deleteFaq);
              });
            })
            .catch(function (errMsg) {
              document.getElementById("addedFaqDiv").innerHTML
                = "<p>" + errMsg + "</p>";
            })
        }
        function deleteFaq(event) {
          $(this).submit(function (event) {
            event.preventDefault();
            fetch('${contextRoot}/product/faq/api', {
              method: 'DELETE',
              body: $(this).serialize(),
              headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            })
              .then(function (resolve) {
                showFaqs(event);
              })
          })
        }
        // 參考ajax p.56
        $("#addProductFaqForm").submit(function (event) {
          event.preventDefault();
          //   let myTable = document.getElementById('list_table_json');
          //   myTable.getElementsByTagName('tbody')[0].innerHTML = "";

          const csrfToken = document.getElementById('csrf').value;
          const productId = document.getElementById('productId').value;
          const inputQuestion = document.getElementById('question').value;
          const inputAnswer = document.getElementById('answer').value;

          const inputJson = {
            "productId": productId,
            "question": inputQuestion,
            "answer": inputAnswer,
          }
          const inputJsonString = JSON.stringify(inputJson);
          console.log('unsent yet json stringify: ' + inputJsonString);
          //   // const dtoObject = { content: inputText }
          //   // const dtoJsonString = JSON.stringify(dtoObject);

          fetch('${contextRoot}/product/faq/api',
            {
              method: 'POST',
              body: inputJsonString, // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
              headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                'X-CSRF-Token': csrfToken
              }
            }
          ).then(response => { // 直接跳轉，此行以下可省略
            return response.text();
          }).then(respText => {
            console.log(respText);
            if ("儲存成功" == respText) {
              showFaqs(event);
              this.reset();
            } else {
              document.getElementById("addedFaqs").innerHTML = respText;
            }
          })

        });
        // ====== FAQ part end =====
        // ===== shipping area start =====
        async function showShippingAreas(temp) {
          for (let oneResult of temp.result) {
            let purchasePlanId = oneResult.purchasePlanId;
            await fetch("${contextRoot}/public/product/purchasePlan/" + purchasePlanId + "/area/api")
              .then(function (response) {
                if (response.ok) {
                  return response.json();
                } else {
                  throw new Error(response.status + ":" + response.statusText);
                }
              })
              .then(function (itemsJson) {
                if (itemsJson.result != null) {
                  for (let oneItem of itemsJson.result) { // here
                    console.log(oneItem)
                    let deletePurchasePlanShippingAreaForm =  
                            `<div style="display: flex; justify-content: space-between;">
        			            <div style="float: left;">` + oneItem.area + `</div>
        			            <div style="float: right;">
        			                <form id="deletePurchasePlanShippingArea_p_` + purchasePlanId + `a_` + oneItem.areaId + `" >
        			                    <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        			                    <input type="hidden" name="purchasePlanId" value="` + purchasePlanId + `">
        			                    <input type="hidden" name="areaId" value="` + oneItem.areaId + `">
        			                    <input type="submit" style="padding: 0.1rem 0.5rem; font-size: 0.8rem; " value="-" class="btn btn-outline-danger"></input>
        			                </form>
        			            </div>
        			        </div>`;
//                     `<form id="deletePurchasePlanShippingArea_p_` + purchasePlanId + `a_` + oneItem.areaId + `" >`;
//                     deletePurchasePlanShippingAreaForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
//                     deletePurchasePlanShippingAreaForm += `<input type="hidden" name="purchasePlanId" value="` + purchasePlanId + `">`;
//                     deletePurchasePlanShippingAreaForm += `<input type="hidden" name="areaId" value="` + oneItem.areaId + `">`;
//                     deletePurchasePlanShippingAreaForm += `<input type="submit" style="margin-top: -0.8rem; padding: 0.1rem 0.5rem; font-size: 0.8rem; " value="-" class="btn btn-outline-danger"></input>`;
//                     deletePurchasePlanShippingAreaForm += `</form>`;
                    // <option value="volvo">Volvo</option>
//                     let itemsToAppend = `<br>` + oneItem.area + deletePurchasePlanShippingAreaForm;
					let itemsToAppend = deletePurchasePlanShippingAreaForm;
//                     <div style="display: inline-flex; justify-content: space-between;">
//                     </div>
                    // console.log(itemsToAppend);
                    $("#purchasePlanArea_" + purchasePlanId).append(itemsToAppend);
                    document.getElementById("deletePurchasePlanShippingArea_p_" + purchasePlanId + "a_" + oneItem.areaId).removeEventListener('click', deletePurchasePlanShippingArea);
                    document.getElementById("deletePurchasePlanShippingArea_p_" + purchasePlanId + "a_" + oneItem.areaId).addEventListener('click', deletePurchasePlanShippingArea);
                  }
                }
                // console.log(itemsJson);
                // reset temp value
                // temp = null;
                return itemsJson;
              })
          }
        }

        async function showUnusedPurchasePlanShippingArea(temp) {
          for (let oneResult of temp.result) {
            let purchasePlanId = oneResult.purchasePlanId;
            await fetch("${contextRoot}/public/product/purchasePlan/" + purchasePlanId + "/unusedPurchasePlanArea/api")
              .then(function (response) {
                if (response.ok) {
                  return response.json();
                } else {
                  throw new Error(response.status + ":" + response.statusText);
                }
              })
              .then(function (itemsJson) {
                if (itemsJson.result != null) {
                  for (let oneItem of itemsJson.result) { // here
                    // <option value="volvo">Volvo</option>
                    let itemsToAppend = `<option value="` + oneItem.areaId + `">` + oneItem.area + `</option>`;
                    $("#addPurchasePlanShippingAreaFormOptions_" + purchasePlanId).append(itemsToAppend);
                  }
                }
                // console.log(itemsJson);
                // reset temp value
                return itemsJson;
              })
          }
        }

        // 因multiple fetch request，改用form新增
        // function addPurchasePlanShippingArea(event) {
        //   $(this).submit(function (event) {
        //     event.preventDefault();
        //     fetch('${contextRoot}/product/purchasePlan/area/api', {
        //       method: 'POST',
        //       body: $(this).serialize(),
        //       headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        //     })
        //       .then(function (resolve) {
        //         return resolve.text();
        //       })
        //       .then(function(resolveText){
        //         console.log(resolveText);
        //         if ( resolveText == '儲存成功' ) {
        //           showPurchasePlans(event);
        //         }
        //       })
        //   })
        // }
        function deletePurchasePlanShippingArea(event){
          $(this).submit(function (event) {
            event.preventDefault();
  
            fetch('${contextRoot}/product/purchasePlan/area/api',
              {
                method: 'DELETE',
                body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
              }
            ).then(response => { // 直接跳轉，此行以下可省略
              return response.text();
            }).then(respText => {
              console.log(respText);
              if ("刪除地區成功" == respText) {
                showPurchasePlans(event); // TODO 刷新purchasePlan可新增item列表(有時間改做個別刷新不做全體刷新? 還是這樣就好，避免已有使用該item的purchasePlan沒有更新到?)
              } else {
                ;
              }
            })
  
          });
        }
        // ===== shipping area end =====
      </script>

    </body>

    </html>