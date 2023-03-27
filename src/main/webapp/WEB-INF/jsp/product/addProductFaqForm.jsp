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
        html,
        body {
          height: 100%;
        }

        #addedFaqsDiv {
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

        <form id="addProductFaqForm">
          <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
          <input type="hidden" id="productId" name="productId" value="${productId}" /><br>
          <label for="question">新增Question</label> <input id="question" name="question" required /><br>
          <label for="answer">新增Answer</label> <input id="answer" name="answer" required /><br>
          <input type="submit" id="submitBtn" value="送出"></input><br>
        </form>
        <div id="addedFaqsDiv">
          <table id="addedFaqs" class="table">

          </table>
        </div>
        <form action="${contextRoot}/editProduct/${productId}" method="get">
          <input type="submit" value="新增圖片">
        </form>
      </div>

      <script>
        window.onload = function () {
          showFaqs(event);
        }
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
            // <form>
            //   <input type="hidden" name="itemId" value="" />
            //   <input type="submit">刪除</input>
            // </form>
            .then(function (itemsJson) {
              $("#addedFaqs").append("<tr scodeleteFaqpe='col'><th>問題</th><th>回答</th><th>刪除</th></tr>");
              $.each(itemsJson, function (index, value) {
                let deleteFaqForm = `<form id="deleteFaq_` + value.faqid + `" >`;
                deleteFaqForm += `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`;
                deleteFaqForm += `<input type="hidden" name="faqId" value="` + value.faqid + `">`;
                deleteFaqForm += `<input type="submit" value="刪除"></input>`;
                deleteFaqForm += `</form>`;
                $("#addedFaqs").append("<tr scope='row'><td>" + value.question + "</td><td>" + value.answer + "</td><td>" + deleteFaqForm + "</td></tr>");
                document.getElementById("deleteFaq_" + value.faqid).addEventListener('click', deleteFaq);
              });
              // document.getElementById("addedFaqs").innerHTML
              //   = itemsTable;
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

      </script>

    </body>

    </html>