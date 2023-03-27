<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>訂單列表</title>
    <link rel="stylesheet" href="${contextRoot}/css/popup.css" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
  </head>
  <body>
    <jsp:include page="../layout/navbarNew.jsp"></jsp:include>
    <div class="container">
      <br />
      <h3>${user.username}的訂單列表</h3>
      <table class="table" style="width: 1200px; table-layout: fixed;" >
        <thead>
          <tr>
            <%-- 往下複製：shift+alt+下 --%>
            <!-- <th>訂單id</th> -->
            <th style="width: 10%">購買方案</th>
            <th style="width: 10%">訂單價格</th>
            <th style="width: 10%">購買數量</th>
            <!-- <th>信用卡</th> -->
            <th style="width: 10%">下訂日期</th>
            <!-- <th>運送地區</th> -->
            <!-- <th>購買人</th> -->
            <th style="width: 10%">運送地址</th>
            <th style="width: 10%">運送狀態</th>
            <th style="width: 10%">付款狀態</th>
            <th style="width: 10%">備註</th>
<!--             <th>取消狀態</th> -->
            <th style="width: 10%">付款</th>
            <th style="width: 10%">取消訂單</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="orderData" items="${ orderPage.content }">
            <tr class="text-wrap">
              <td style="width: 10%; word-wrap: break-word;" >${ orderData.purchasePlan.getTitle() }</td>
              <td style="width: 10%; word-wrap: break-word;">NT$ 
              	<fmt:formatNumber pattern="#,###">${ orderData.price }</fmt:formatNumber> 
              </td>
              <td style="width: 10%; word-wrap: break-word;">
              	<fmt:formatNumber pattern="#,###">${ orderData.amount }</fmt:formatNumber>
              </td>
              <td style="width: 10%; word-wrap: break-word;">${ orderData.orderDate }</td>
              <td style="width: 10%; word-wrap: break-word;">${ orderData.detailAddr }</td>
              <td style="width: 10%; word-wrap: break-word;">${ orderData.shippingstatus }</td>
              <td style="width: 10%; word-wrap: break-word;">${ orderData.paymentStatus }</td>
              <td style="width: 10%; word-wrap: break-word;">${ orderData.note }</td>
<%--               <td>${ orderData.cancelStatus }</td> --%>
              <c:choose>
                <c:when test="${ orderData.paymentStatus == 1 }">
                  <td>
                    <a
                      class="btn btn-outline-warning disabled"
                      aria-disabled="true"
                      href=""
                      >已付款</a
                    >
                  </td>
                </c:when>
                <c:when test="${ orderData.cancelStatus == 1 }">
                  <td>
                    <a
                      class="btn btn-outline-danger disabled"
                      aria-disabled="true"
                      href=""
                      >已刪除</a
                    >
                  </td>
                </c:when>
                <c:otherwise>
                  <td>
                    <a
                      class="btn btn-outline-warning"
                      href="${contextRoot}/public/product/order${orderData.orderId}/toPayFromList"
                      >付款</a
                    >
                  </td>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${ orderData.paymentStatus == 1 }">
                  <td>
                    <a
                      class="btn btn-outline-dark disabled"
                      aria-disabled="true"
                      href=""
                      >已付款</a
                    >
                  </td>
                </c:when>
                <c:when test="${ orderData.cancelStatus == 1 }">
                  <td>
                    <a
                      class="btn btn-outline-danger disabled"
                      aria-disabled="true"
                      href=""
                      >已刪除</a
                    >
                  </td>
                </c:when>
                <c:otherwise>
                  <td>
                    <a
                      id="delBtn"
                      class="delBtn btn btn-outline-danger"
                      href="${contextRoot}/public/product/order${orderData.orderId}/toOrderDelete"
                      >刪除</a
                    >
                  </td>

                  <!--                   <td><a id="delBtn" class="btn btn-outline-dark" href="#">刪除</a></td>   -->
                  <!-- <td> -->
                  <!-- <form:form id="delForm" class="delForm" modelAttribute="delForm">
							<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                  	   		<input type="hidden" id="orderId" name="orderId" value =${ orderData.orderId}>
	                  	   	<button id="delBtn" class="delBtn btn btn-outline-dark" type="submit">刪除</button>
                  	   	</form:form> -->
                  <!-- </td>   -->
                </c:otherwise>
              </c:choose>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <%-- 頁數 --%>
      <c:forEach var="pageNum" begin="1" end="${ orderPage.totalPages }">
        <c:choose>
          <c:when test="${ pageNum != orderPage.number+1 }">
            <a
              class="btn btn-dark"
              href="${contextRoot}/public/product/toOrderList?p=${pageNum}"
              >${ pageNum }</a
            >
          </c:when>
          <c:otherwise>
            <button class="btn btn-dark" type="button" disabled>
              ${ pageNum }
            </button>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <div id="msg"></div>
    </div>
    <br />
    <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
  </body>
</html>
