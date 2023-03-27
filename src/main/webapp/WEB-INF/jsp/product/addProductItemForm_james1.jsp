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
        html, body{
          height: 100%;
        }
        #addedItems {
          margin: 0 auto;
          height: 600px;
          overflow: auto;
        }
      </style>
      <title>Ajax 版本</title>
    </head>

    <body>
      <jsp:include page="../layout/navbar.jsp"></jsp:include>
      <div class="container">
        <h1>新增專案品項</h1>

        <form id="addProductItemForm" action="${contextRoot}/product/item/api" method="post">
         <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
          <input type="hidden" id="productId" name="productId" value="${productId}" /><br>
          <label for="title">新增品項</label> <input id="itemName" name="itemName" required /><br>
          <input type="submit" id="submitBtn" value="送出"></input><br>
        </form>
      </div>
      <div id="addedItems">

      </div>
      <form action="${contextRoot}/createProductFAQ" method="get">
        <input type="hidden" name="productId" value="${productId}">
        <input type="submit" value="新增FAQ">
      </form>

      <script>
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
            .then(function (itemsJson) {
              let itemsTable = document.createElement("table");
              itemsTable.className = "table";
              let textNodeStr = "<tr scope='col'><th>項目</th><th>品名</th><th>刪除</th></tr>";
                $.each(itemsJson, function (index, value) {
                // <form>
                //   <input type="hidden" name="itemId" value="" />
                //   <input type="submit">刪除</input>
                // </form>
                let deleteItemForm = `<form id="deleteItem_` + value.itemId + `" >` ;
                  deleteItemForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                  deleteItemForm += `<input type="hidden" name="itemId" value="` + value.itemId + `">`;
                  deleteItemForm += `<input type="submit" value="刪除"></input>`;
                  deleteItemForm += `</form>`;
                  textNodeStr += "<tr scope='row'><td>" + index + "</td><td>" + value.itemName + "</td><td>" + deleteItemForm + "</td></tr>";
                document.getElementById( "deleteItem_" + value.itemId ).addEventListener('click', deleteItem);
              });
              let textNode = document.createTextNode(textNodeStr);
              itemsTable.appendChild(textNode);
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
            .then(function(resolve){
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