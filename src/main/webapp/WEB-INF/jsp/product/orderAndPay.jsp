<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>訂單確認與付款</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
  <div class="container">
    <br>
    <div class="row">
      <h1>訂單確認與付款</h1>
      <div class="col" style="word-wrap: break-word;">
        <!-- <h3>訂單資訊與付款頁面</h3> -->
<!--         <h5 class="text-success">預計左邊訂單資訊右邊付款表格</h5> -->
<!--         <h5 class="text-success">預計在這邊做編輯/刪除訂單</h5> -->
        <div class="d-flex align-items-end">
        	<a href="${ contextRoot }/public/product/${product.PId}">
	        	<img src="<c:url value="${ thumbNailPath }"/>" width="100" class="img-thumbnail border-3 m-3 float-start" alt="..." />
        	</a>
        	<h4 style="word-wrap: break-word;" class="m-3">${ product.title }</h4>
        </div>
        <table class="table">
          <thead>
            <tr>
              <th>項目</th>
              <th>訂單資訊</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>價錢</td>
              <td><fmt:formatNumber pattern="#,###">${order.price}</fmt:formatNumber></td>
            </tr>
            <tr>
              <td>購買方案</td>
              <td>${order.purchasePlan.getTitle()}</td>
            </tr>
            <tr>
              <td>下訂日期</td>
              <td>${strOrderDate}</td>
            </tr>
            <tr>
              <td>運送地址</td>
              <td>${order.detailAddr}</td>
            </tr>
          </tbody>
        </table>
        <a class="btn btn-outline-dark" 
        	href="${contextRoot}/public/product/order${order.orderId}/toOrderDelete">取消訂單</a>
        	
      </div>
      <div class="col">
      	<br />
      	<br />
        <h5>信用卡</h5>
        <form id="payForm" method="post" action="${contextRoot}/public/product/pay">
<%--          action="${contextRoot}/public/product/pay" modelAttribute="payForm" --%>
        	<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        	<label for="orderId" class="form-label mt-2">訂單編號：</label>
        	<input type="text" id="orderId" value="${ order.orderId }" 
        		readonly="readonly" class="form-control" name="orderId">
        	
        	<label for="totalAmount" class="form-label mt-2">交易金額：</label>
        	<input type="text" id="totalAmount" value="${order.price}" 
        		readonly="readonly" class="form-control" name="TotalAmount">
 
        	<label for="tradeDesc" class="form-label mt-2">交易描述：</label>
        	<input type="text" id="tradeDesc" value="${order.purchasePlan.getIntro()}" 
        		readonly="readonly" class="form-control" name="TradeDesc">

        	<label for="itemName" class="form-label mt-2">商品名稱：</label>
        	<textarea rows="" cols="" id="itemName" readonly="readonly" 
        		class="form-control" name="ItemName">${ itemAndAmount }</textarea>
<%--         		<c:forEach items="${itemNameAmount}" var="nameAmount"> --%>
<%--         			${ nameAmount } --%>
<%--         		</c:forEach>  --%>
        	<button id="submitBtn" type="submit" class="btn btn-dark mt-3">確認付款</button>
        </form>
               
      </div>
    </div>
  </div>
  <br>
 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
</body>

</html>