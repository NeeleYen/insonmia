<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="contextRoot" value="${ pageContext.request.contextPath }" />    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購買方案列表</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
	  <div class="row">
	<br>
	<h1>購買方案列表</h1>
<!-- 	https://getbootstrap.com/docs/5.0/components/card/ -->
	<c:forEach var="purPlanSet" items="${ pPlanSet }">
		<div class="col-sm mt-4">
       		<div class="card " style="width: 20rem;">
       			<div class="card-body">
       				<h5 class="mb-3 card-title">${ purPlanSet.title }</h5>
       				<h6 class="card-subtitle mb-3 text-secondary">價格：$ ${ purPlanSet.price }</h6>
       				<h6 class="card-subtitle mb-3 text-secondary">預估交貨：${ purPlanSet.shipmentDate }</h6>
<%--         			<c:forEach var="nameAmountStr" items="${ itemNameAmountStrSet }"> --%>
<%-- 	        			<h6 class="card-subtitle mb-3 text-success">${ nameAmountStr }</h6> --%>
<%--         			</c:forEach> --%>
        			
        			<c:forEach var="purchasePlanItem" items="${ purPlanSet.purchasePlanItems }">
<!-- 	                  	purchasePlanItem 是 PurchasePlanItems 物件 -->
	                  <h6 class="card-subtitle mb-3 text-success">
	                  	 	${ purchasePlanItem.purchasePlanItemsPK.itemIdPK.itemName } ${ purchasePlanItem.amount }個
	                  </h6>
	                </c:forEach>
        			
        			 <div id="purPlanArea" class="card-subtitle mb-3 text-secondary">運送地區：
						  <c:forEach var="area" items="${ purPlanSet.areaNo }">
			             	 ${ area.area }
						  </c:forEach>
		             </div>
		             
		             <div class="card-subtitle mb-3 text-secondary">配送門檻：	
		              <c:choose>
					    <c:when test="${empty purPlanSet.minOrderAmount}">
					        不限
					    </c:when>
					    <c:otherwise>
					        ${ purPlanSet.minOrderAmount }	
					    </c:otherwise>
					  </c:choose>	              	              

		              </div>
		              <div class="card-subtitle mb-3 text-secondary">販賣上限：		              	
		                <c:choose>
					      <c:when test="${empty purPlanSet.maxOrderAmount}">
					          不限
					      </c:when>
					      <c:otherwise>
					          ${ purPlanSet.maxOrderAmount }	
					      </c:otherwise>
					    </c:choose>	              	              
		              </div>
		              
        			<p class="card-text">${ purPlanSet.intro }</p>
        			<br />
        			
        			<form class="mb-3" method="get" action="${ contextRoot }/public/product/${ productId }/order"> 
							<label for="purPlanId" class="form-label">專案ID：</label>							
					   		<input type="number" name="purPlanId" id="purPlanId" value="${ purPlanSet.purchasePlanId }"
					  			readonly="readonly" class="form-control">
							<br/>						
							<label for="amount" class="form-label">選擇數量</label>
					   		<input type="number" name="amount" id="amount" required="required" min="0" max="10"
					  			class="form-control">
	        			<button type="submit" class="btn btn-dark mt-3">支持</button>
	        			
 					</form> 
        		</div>
        		
        	</div>
        	
        	</div><!-- col -->
        	
        </c:forEach>
        
      </div><!-- row -->
        
	</div>
	<br>
 <jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
   <script type="text/javascript">
//   	顯示運送地區
    const area = document.getElementById('purPlanArea');
    const purchasePlanId = document.getElementById('purPlanId').value;
    fetch('${contextRoot}/public/product/purchasePlan/'+purchasePlanId+'/area/api', 
    {
      method: 'get'
    }).then(response => {
      return response.json();
    }).then(data => {
      const success = data.success;
      areaMaker(success);
    })

    function areaMaker(data){
      areaData = '';
      data.forEach(element => {
        areaData += '<h6 class="card-subtitle mb-3 text-secondary">' + element.area + '</h6>';
      });
      area.innerHTML += area;
    }
  </script>
</body>
</html>