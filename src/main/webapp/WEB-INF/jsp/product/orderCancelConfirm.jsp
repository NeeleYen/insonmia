<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>訂單取消確認</title>
<style type="text/css">
	.item{
	width: 10%; 
	word-wrap: break-word;
	}
	.orderInformation{
	width: 90%; 
	word-wrap: break-word;
	}
</style>

</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h3 class="text-center">確定要取消訂單嗎？</h3>
		<h5>訂單資訊：</h5>
		<table class="table m-3" style="width: 1000px; table-layout: fixed;">
          <thead>
            <tr>
              <th class="item">項目</th>
              <th class="orderInformation">訂單資訊</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="item">價錢</td>
              <td class="orderInformation">
              	<fmt:formatNumber pattern="#,###">${order.price}</fmt:formatNumber> 
              </td>
            </tr>
            <tr>
              <td class="item">購買方案</td>
              <td class="orderInformation">${order.purchasePlan.getTitle()}</td>
            </tr>
            <tr>
              <td class="item">貨品項目</td>
              <td class="orderInformation">
              	<c:forEach var="item" items="${ purchasePlan.purchasePlanItems }">
              		<h6>${ item.purchasePlanItemsPK.itemIdPK.itemName }：${ item.amount }個</h6>
              	</c:forEach>
              </td>
            </tr>
            <tr>
              <td class="item">下訂日期</td>
              <td class="orderInformation">${strOrderDate}</td>
            </tr>
            <tr>
              <td class="item">運送地址</td>
              <td class="orderInformation">${order.detailAddr}</td>
            </tr>
            <tr>
              <td class="item">備註</td>
              <td class="orderInformation">${order.note}</td>
            </tr>
          </tbody>
        </table>
        <div class="d-flex justify-content-center">
	        <a href="${ contextRoot }/public/product/order${order.orderId}/delete" 
	        	class="btn btn-outline-danger">確定取消訂單</a>
        </div>
        
	</div>
	<br>
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>