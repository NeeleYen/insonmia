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
      <title>我的專案</title>
      <style type="text/css">
      .one-line {
		  display: block;
		  overflow: hidden;
		  white-space: nowrap;
		  text-overflow: ellipsis;
		  max-width: 100%;
		}

      </style>

    </head>

    <body>
      <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
      <input id="contextRoot" type="hidden" value="${contextRoot}">
      <c:forEach var="category" items="${ requestScope.entrySet }">   
      	<input type="hidden" id="category_${category.key}" value="${category.value}" data-categoryKey="${category.key}" data-categoryValue="${category.value}">
      </c:forEach>
      <div class="container mt-5 mb-4">
        <div class="d-flex justify-content-between align-items-baseline">
          <h1 class="mt-5 mb-4">我的專案</h1>
          <a class="btn btn-outline-dark btn-lg mr-5" href="${contextRoot}/createProduct">新增專案</a>
        </div>

        <!-- <form id="addProductForm">
          <label for="title">專案主題:</label> <input id="title" required />
          <label for="category">專案類別:</label> <select name="category" id="category">
            <option value="1">音樂</option>
            <option value="2">遊戲</option>
            <option value="3">旅遊</option>
            <option value="4">工藝</option>
          </select> <label for="intro">專案介紹:</label> <input id="intro" required /> <label for="closeDate">專案截止日:</label> <input id="closeDate" type="date" required />
          <label for="status">專案狀態:</label> <select name="status" id="status">
            <option value="0">正常販售</option>
            <option value="1">已截止</option>
          </select>
          <button id="submitBtn">送出</button>
        </form> -->

        <div>
          <table id="list_table_json" class="table table-hover">
            <thead>
              <tr>
                <th scope="col" class="text-center">編號</th>
                <th scope="col" class="text-center col-2">主題</th>
                <th scope="col" class="text-center">類別</th>
                <th scope="col" class="text-center col-4">介紹</th>
                <th scope="col" class="text-center">狀態</th>
                <th scope="col" class="text-center">截止日</th>
                <th scope="col" class="text-center">編輯子項目</th>
                <th scope="col" class="text-center">修改</th>
                <th scope="col" class="text-center">刪除</th>
              </tr>
            </thead>
            <tbody>

            </tbody>
          </table>
          <div id="msg2"></div>
        </div>
      </div>


      <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
      <script>
        window.onload = function () {
          fetch('${contextRoot}/product/api',
            {
              method: 'get',
              // headers: { 'Content-Type': 'application/json' },
            }
          ).then(response => {
            return response.json();
          }).then(springPage => {
            // document.getElementById('addProductForm').reset();
            console.log(springPage)
            htmlMaker(springPage)
          })
        }

        const submitBtn = document.getElementById('submitBtn');

        submitBtn.addEventListener('click', function (e) {
          e.preventDefault();
          // 清空顯示用table(還是就直接先顯示所有目前由自己建立的專案?)
          let myTable = document.getElementById('list_table_json');
          myTable.getElementsByTagName('tbody')[0].innerHTML = "";

          const inputStatus = document.getElementById('status').value;
          const inputTitle = document.getElementById('title').value;
          const inputCategory = document.getElementById('category').value;
          const inputIntro = document.getElementById('intro').value;
          const inputCloseDate = document.getElementById('closeDate').value;

          const inputJson = {
            "status": inputStatus,
            "title": inputTitle,
            "category": inputCategory,
            "intro": inputIntro,
            "closeDate": inputCloseDate
          }
          const inputJsonString = JSON.stringify(inputJson);
          // const dtoObject = { content: inputText }
          // const dtoJsonString = JSON.stringify(dtoObject);

          fetch('${contextRoot}/product/api',
            {
              method: 'post',
              headers: { 'Content-Type': 'application/json' },
              body: inputJsonString
            }
          ).then(response => {
            return response.json();
          }).then(springPage => {
            document.getElementById('addProductForm').reset();
            console.log(springPage)
            htmlMaker(springPage)
          })

        })



        function htmlMaker(data) {
          msg_data = '<tbody>'
          data.content.forEach(el => {
            let deleteProductForm = `<form action="${contextRoot}/product/form" method="post">` +
              `<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">` +
              `<input type="hidden" name="_method" value="delete" />` +
              `<input type="hidden" name="pid" value="` + el.pid + `">` +
              `<input type="submit" class="btn btn-outline-danger" value="刪除">` +
              `</form>`;
            // console.log(deleteProductForm);
            msg_data += '<tr>'
            msg_data += '<td scope="row" class="text-center">' + el.pid + '</td>'
            msg_data += '<td scope="row" class="text-center col-2"><a class="one-line" href="${contextRoot}/public/product/' + el.pid + '">' + el.title.substring(0, 10) + '</a></td>'
            msg_data += '<td scope="row" class="text-center">' + document.getElementById("category_" + el.category).value + '</td>'
            msg_data += '<td scope="row" class="text-center col-4">' + el.intro.substring(0, 20) + '</td>'
            msg_data += '<td scope="row" class="text-center">' + el.status + '</td>'
            msg_data += '<td scope="row" class="text-center">' + el.closeDate + '</td>'
            msg_data += '<td scope="row" class="text-center">' + '<a class="btn btn-outline-warning" href="${contextRoot}/editProduct/' + el.pid + '">編輯</a>' + '</td>'
            msg_data += '<td scope="row" class="text-center">' + '<a class="btn btn-outline-warning" href="${contextRoot}/product/edit/' + el.pid + '">修改</a>' + '</td>'
            msg_data += '<td scope="row" class="text-center">' + deleteProductForm + '</td>'
            msg_data += '</tr>'
          })
          msg_data += '</tbody>'

          let totalPages = data.totalPages;

          let myTable = document.getElementById('list_table_json');

          myTable.getElementsByTagName('tbody')[0].innerHTML = msg_data;

          const msg2 = document.getElementById('msg2');
          msg2.innerHTML = ''; // 初始化 msg2

          for (var i = 1; i <= totalPages; i++) {
            msg2.innerHTML += '<button class="pageBtn btn btn-outline-secondary mx-1" data-pagenum="' + i + '">' + i + '</button>';
          }

          msg2.innerHTML = '<div class="pagination justify-content-center">' + msg2.innerHTML + '</div>';

          let pageBtns = document.getElementsByClassName('pageBtn');
          for (i = 0; i < pageBtns.length; i++) {
            pageBtns[i].addEventListener('click', function (e) {
              let pageNumber = this.getAttribute('data-pagenum');
              loadThatPage(pageNumber);
            });
          }
        }

        ///////////    選頁數 AJAX   //////////////////
        function loadThatPage(thatPage) {
          fetch('${contextRoot}/product/api?p=' + thatPage,
            { method: 'get' })
            .then(res => {
              return res.json()
            })
            .then(springPage => {
              console.log(springPage)
              htmlMaker(springPage)
            })
        }

      </script>

    </body>

    </html>