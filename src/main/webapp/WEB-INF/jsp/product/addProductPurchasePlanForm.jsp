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
      <title>新增購買方案</title>
    </head>

    <body>
      <jsp:include page="../layout/navbar.jsp"></jsp:include>
      <input type="hidden" value="${contextRoot}" id="contextRoot">
      <div class="container">
        <h1>新增購買方案</h1>

        <form id="addPurchasePlanForm" action="${contextRoot}/product/purchasePlan/api" method="post">
         <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
         <input id="productId" type="hidden" name="productId" value="${productId}">
          <label for="title">方案名稱:</label> <input name="title" id="title" required /><br>
          <label for="intro">方案介紹:</label> <textarea id="intro" name="intro" ></textarea><br> 
          <label for="price">方案價格:</label> <input name="price" id="price" required /><br>
          <label for="minOrderAmount">方案最小所需銷售量: (留空為不限)</label> <input name="minOrderAmount" id="minOrderAmount" /><br>
          <label for="maxOrderAmount">方案最大限制購買量: (留空為不限)</label> <input name="maxOrderAmount" id="maxOrderAmount" /><br>
          <label for="shipmentDate">方案預定交貨日:</label> <input id="shipmentDate" name="shipmentDate" type="date" required /><br>
          <button id="submitBtn">送出</button><br>
        </form>
      </div>

      <script>
        // window.onload = function () {
        //   // 參考ajax p.56
        // $("#addProductForm").submit(function(event){
        //   event.preventDefault();

        //   fetch('${contextRoot}/product/form',
        //     {
        //       method: 'POST',
        //       body: $(this).serialize(), // 利用serialize自動將form的reqParam轉成xxx-form-urlencoded
        //       headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
        //     }
        //   ).then(response => { // 直接跳轉，此行以下可省略
        //     return response.text();
        //   }).then(respText => {
        //     console.log(respText);
        //   })

        // });
        // }

        window.onload = function () {
          showItems(event);
        }
        const productId = document.getElementById("productId").value;
        function showItems(event) {
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
              $("#addedItems").append("<tr scope='col'><th>項目</th><th>品名</th><th>刪除</th></tr>");
              $.each(itemsJson, function (index, value) {
                let deleteItemForm = `<form id="deleteItem_` + value.itemId + `" >`;
                deleteItemForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                deleteItemForm += `<input type="hidden" name="itemId" value="` + value.itemId + `">`;
                deleteItemForm += `<input type="submit" value="刪除"></input>`;
                deleteItemForm += `</form>`;
                $("#addedItems").append("<tr scope='row'><td>" + index + "</td><td>" + value.itemName + "</td><td>" + deleteItemForm + "</td></tr>");
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
            } else {
              document.getElementById("addedItems").innerHTML = respText;
            }
          })

        });
      </script>

    </body>

    </html>