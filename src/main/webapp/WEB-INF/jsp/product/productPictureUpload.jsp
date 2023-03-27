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
    </head>
    <body>
      <jsp:include page="../layout/navbar.jsp"></jsp:include>
      <br>
      <form action="${ contextRoot }/product/picture/api" method="post" enctype="multipart/form-data">
       <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" value="${productId}" name="productId">
        <input type="hidden" value="${picNum}" name="picNum">
        <input type="file" name="productPhoto" />
        <input type="submit" value="更新">
      </form>
    </body>

    </html>