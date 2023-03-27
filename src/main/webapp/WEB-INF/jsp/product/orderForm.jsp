<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單資訊表單</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
  <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
  
  <div class="container">
    <h3>訂單資訊表單</h3>
<!--     <h5>選擇運送地區、地址、數量等</h5> -->
    <h5>用戶：${ user.username }</h5>
    <h5>購買方案：${ purPlan.title }</h5>
    <h5>購買數量：${ amount }</h5>
    <h5>purPlanId：${ purPlanId }</h5>
<%--     <c:forEach var="areaNo" items="${ areaNo }"> --%>
<%--       <h5>${ areaNo.areaId }</h5> --%>
<%--       <h5>${ areaNo.area }</h5> --%>
<%--     </c:forEach> --%>
    <form action="#" method="post" id="orderForm">
      <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

      <%-- user信用卡 --%>
      <label for="creditCard" class="form-label">信用卡</label>
      <select id="creditCard" class="form-select" aria-label="Default select example">
        <option selected>選擇信用卡</option>
        <c:forEach var="creditCardOption" items="${ cardNumxx }">
          <option value="${ creditCardOption.key }">${ creditCardOption.value }</option>
        </c:forEach>
      </select>

      <%-- 應該要傳地區ID --%>
      <label for="selectArea" class="form-label mt-2">運送地區</label>
      <select id="selectArea" class="form-select" aria-label="Default select example">
        <option selected>選擇地區</option>
        <c:forEach var="areaNo" items="${ areaNo }">
          <option value="${ areaNo.areaId }">${ areaNo.area }</option>
        </c:forEach>
      </select>
      <label for="address" class="form-label mt-2">運送地址</label>
      <input id="address" type="text" class="form-control">

      <label for="noteArea" class="form-label mt-2">備註</label>
      <textarea id="noteArea" rows="3" cols="" class="form-control" placeholder="請不輸入超過150字" maxlength="150"></textarea>

      <hr />
      <h5>小計：<fmt:formatNumber pattern="#,###">${ price }</fmt:formatNumber> 元</h5>
      <br>
      <button id="submit" class="btn btn-outline-dark mb-5">確認下訂</button>
      <div id="msg"></div>
      <%-- <a href="${ contextRoot }/product/${ productId }/toPay">跳頁</a> --%>

    </form>

    <script type="text/javascript">
      const orderForm = document.getElementById('orderForm');
      const submitBtn = document.getElementById('submit');
      const msg = document.getElementById('msg');
      submitBtn.addEventListener('click', function (e) {
        e.preventDefault();

        const csrfToken = document.getElementById('csrf').value;
        const inputCreditCard = document.getElementById('creditCard').value;
        const inputArea = document.getElementById('selectArea').value;
        const inputAddress = document.getElementById('address').value;
        const inputNote = document.getElementById('noteArea').value;
        const dtoOrder = {
          cardId: inputCreditCard,
          purPlanId: "${purPlanId}",
          intAreaId: inputArea,
          userId: "${user.id}",
          amount: "${amount}",
          price: "${purPlan.price}",
          note: inputNote,
          detailAddr: inputAddress
        }
        const dtoOrderStr = JSON.stringify(dtoOrder);

        // let formData = new FormData();
        // formData.append("creditCard", inputCreditCard); //要處理
        // formData.append("purchasePlan", "${purPlan}");
        // formData.append("areaId", inputArea); //要處理
        // formData.append("userId", "${user}");
        // formData.append("amount", "${amount}");
        // formData.append("price", "${purPlan}"); //要處理
        // formData.append("note", inputNote);
        // formData.append("detailAddr", inputAddress);


        fetch('${contextRoot}/public/product/${productId}/makeOrder',
          {
            method: 'post',
            headers: {
              'Content-type': 'application/json',
              'X-CSRF-Token': csrfToken
            },
            body: dtoOrderStr
          }).then((response) => {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error("something wrong at front end");
            }
          }).then((data) => {
            if (data.status === "success") {
              window.location.href = "${ contextRoot }/public/product/${ productId }/toPay";
              
              orderForm.reset();
            } else if (data.status === "have problem") {
              msg.innerHTML = `<span class="text-danger">problem with order in controller</span>`;
            } else {
              throw new Error("something wrong in front end");
            }
            console.log(`${data}`);
          }).catch((err) => {
            msg.innerHTML = `<span class="text-danger">下訂失敗</span>`;
            orderForm.reset();
            console.log(`${err}`);
          })
      })
    </script>

  </div>
 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>

</html>