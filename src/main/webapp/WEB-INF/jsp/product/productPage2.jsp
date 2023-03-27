<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />    
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品頁</title>
    <!-- css -->
<%--     <link rel="stylesheet" href="${ contextRoot }/css/style2.css"> --%>

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<input id="productId" type="hidden" value="${product.PId}">
	<input id="contextRoot" type="hidden" value="${ contextRoot }">
	<div class="container">
		<div class="row">
			<div class="col-6 m-3">
<%-- 				<c:forEach var="entry" items="${productPictures}" varStatus="status"> --%>
<%-- 					<c:if test="${status.index == 0}"> --%>
<%-- 						<c:set var="picturePath" value="${entry.value}" /> --%>
						<img style="object-fit: cover;" src="${contextRoot}${thumbNailPath}" width="350px" height="250px"  alt="..." />
<%-- 					</c:if> --%>
<%-- 				</c:forEach>  --%>
				<br>
				<div class="mt-3 productintro">
	                <h1 style="word-wrap: break-word;">${ product.title }</h1>
	                <p>${ product.intro }
	                </p>
	            </div>
			</div>
			<div class="col-1"></div>
			<div class="col-4 m-3">
				  <!-- 進度條 -->
                <div class="progress">
                    <div class="progress-bar bg-dark" role="progressbar" style="width:${ fundationPercent }%" aria-valuenow="${fundationPercent}"
                        aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <!-- 累積金額 -->
                <div id="space">
                    <h1>NT$ ${fundationStatus}</h1>
                </div>
                <!-- 目標金額 -->
                <div>
                    <h6>累積金額 (總目標NT$ ${fundationGoal})</h6>
                </div>
                <!-- 人數 -->
                <div id="space">
                    <h2>${supporterCount}</h2>
                </div>
                <!-- 位支持者 -->
                <div>
                    <h6>位支持者</h6>
                </div>
                
                <div id="space">
                    <h2>${closeDateStr}</h2>
                </div>
                <!-- 位支持者 -->
                <div>
                    <h6>截止</h6>
                </div>
                
                <!-- 天數 -->
                <c:choose>
                	<c:when test="${ dayLeft == -1 }">
		                <div id="space">
		                    <h2>專案已截止</h2>
<!-- 		                </div>              -->
<!-- 		                <div> -->
<!-- 		                    <h6>天 剩餘</h6> -->
		                </div>
                	</c:when>
                	<c:otherwise>
		                <div id="space">
		                    <h2><fmt:formatNumber pattern="#,###">${dayLeft}</fmt:formatNumber></h2>
		                </div>             
		                <div>
		                    <h6>天 剩餘</h6>
		                </div>
                	</c:otherwise>
                </c:choose>
                
                
                
                <!-- 支持此專案 -->
                <c:choose>
                	<c:when test="${ dayLeft == -1 }">                		
		                <form action="${ contextRoot }/public/product/${ product.PId }/purchasePlanList">
			              <button type="submit" class="postbutton btn btn-dark" disabled="disabled">支持這個專案</button>
			            </form>
                	</c:when>
                	<c:otherwise>
		                <form action="${ contextRoot }/public/product/${ product.PId }/purchasePlanList">
			              <button type="submit" class="postbutton btn btn-dark">支持這個專案</button>
			            </form>
                	</c:otherwise>
                </c:choose>
			</div>
		</div>
		
<!-- 		tab -->
		<br>
		<div class="row">
			<div class="col-6 m-3">
				<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
					<li class="nav-item" role="presentation">
						<button class="nav-link active btn-light" id="pills-home-tab" data-toggle="pill"
							data-target="#pills-home" type="button" role="tab" aria-controls="pills-home"
							aria-selected="true">產品介紹</button>
					</li>
					<li class="nav-item" role="presentation">
						<button class="nav-link btn-light" id="pills-profile-tab" data-toggle="pill"
							data-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile"
							aria-selected="false">留言板</button>
					</li>
					<li class="nav-item" role="presentation">
						<button class="nav-link btn-light" id="pills-FAQ-tab" data-toggle="pill"
							data-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact"
							aria-selected="false">FAQ</button>
					</li>
				</ul>
				<div class="tab-content" id="pills-tabContent">
					<div class="tab-pane fade show active" id="pills-home" role="tabpanel"
						aria-labelledby="pills-home-tab">
						<div style=" width: 600px;">
						<div class="card" style="border: none;  width: 100%;">
<%--                 		<p>${ product.intro }</p> --%>
						</div>
						</div>
                    <!-- 圖片樣式改這(改用js) -->
                		<div id="introPics"></div>
<!--                 		{c:forEach items="{ introPic }" var="introPic"} -->
<!-- 	                		<img alt="產品介紹圖" src="{ contextRoot }{ introPic.filePath }" width="500px"> -->
<!--                 		{/c:forEach>} -->
					</div>
					<div class="tab-pane fade" id="pills-profile" role="tabpanel"
						aria-labelledby="pills-profile-tab">
						
						<div class="card-body">
<%-- 							<form:form action="${contextRoot}/product/bulletin/api" modelAttribute="dtoText"> --%>
<!-- 								<div class="input-group"> -->
<%--                     				<form:input type="hidden" id="productId" path="productId" value="${product.PId}"></form:input> --%>
<%-- 									<form:textarea path="text" id="text" class="form-control" rows="" cols="" /> --%>
<!-- 								</div> -->
<!-- 								<br /> -->
<!-- 								<button type="submit" class="btn btn-primary">送出</button> -->
<%-- 							</form:form> --%>
						</div>
	
						<a class="btn btn-outline-dark" href="${contextRoot}/message/add">新增留言</a>
						<a class="btn btn-outline-dark" href="${contextRoot}/message/page">查看訊息</a>
						
					</div>
					<div class="tab-pane fade" id="pills-contact" role="tabpanel"
						aria-labelledby="pills-contact-tab">		            
<!-- //////////////////////////////////////////////////////////////// -->
						<div class="card">
							<div class="card-header">
							    Featured
							</div>
							<div class="card-body">
								<h5 class="card-title">Special title treatment</h5>
								<p class="card-text">With supporting text below as a natural lead-in to additional content.</p>							    
							</div>
						</div>
<!-- //////////////////////////////////////////////////////////////// -->
						
					</div>
				</div>
			</div>
			<div class="col-1"></div>
			<div class="col-4 m-3">
				<h3>購買方案</h3>
	            <c:forEach var="purPlanSet" items="${ pPlanSet }">
	              <div class="card" style="width: 20rem;">
	                <div class="card-body">
                    <!-- 抓購買方案id for 購買方案貨品清單api -->
                    <input type="hidden" id="purPlanId" name="purPlanId" value="${purPlanSet.purchasePlanId}">

	                  <h5 class="mb-3 card-title">${ purPlanSet.title }</h5>
	                  <h6 class="card-subtitle mb-3 text-secondary">價格：$ 
	                  	<fmt:formatNumber pattern="#,###">${ purPlanSet.price }</fmt:formatNumber> 
	                  </h6>
	                  <h6 class="card-subtitle mb-3 text-secondary">預估交貨：${ purPlanSet.shipmentDate }</h6>
	                  
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
					       <fmt:formatNumber pattern="#,###">${ purPlanSet.minOrderAmount }</fmt:formatNumber> 	
					    </c:otherwise>
					  </c:choose>	              	              

		              </div>
		              <div class="card-subtitle mb-3 text-secondary">販賣上限：		              	
		                <c:choose>
					      <c:when test="${empty purPlanSet.maxOrderAmount}">
					          不限
					      </c:when>
					      <c:otherwise>
					         <fmt:formatNumber pattern="#,###">${ purPlanSet.maxOrderAmount }</fmt:formatNumber> 	
					      </c:otherwise>
					    </c:choose>	              	              
		              </div>
                    
	                  <p class="card-text" style="word-wrap: break-word;">${ purPlanSet.intro }</p>
<!-- 	                  <br /> -->
	                  <input type="hidden" name="_csrf" value="${_csrf.token}">
	                  <form class="mb-3" method="get" action="${ contextRoot }/public/product/${ product.PId }/order">
	                    <label for="purPlanId" class="form-label">專案ID：</label>
	                    <input type="number" name="purPlanId" id="purPlanId" value="${ purPlanSet.purchasePlanId }"
	                      readonly="readonly" class="form-control">
	                    <br />
	                    <label for="amount" class="form-label">選擇數量</label>
	                    <input type="number" name="amount" id="amount" required="required" min="0" max="1500"
	                      class="form-control">
	                    <c:choose>
	                    	<c:when test="${ dayLeft == -1 }">
			                    <button type="submit" class="btn btn-dark mt-3" disabled="disabled">支持</button>
	                    	</c:when>
	                    	<c:otherwise>
			                    <button type="submit" class="btn btn-dark mt-3">支持</button>
	                    	</c:otherwise>
	                    </c:choose>
	                  </form>
	                </div>
	              </div>
	              <br>
	            </c:forEach>
			</div>
			
		</div>
		
	</div>
	
	
	<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>

    <script>
      window.onload = function (event){
        showProductPictures(event);
      }
    </script>
    
    <script type="text/javascript">
    const bulletinTab = document.getElementById("pills-profile-tab");
    bulletinTab.addEventListener('click', function(e){
        fetchBulletinData();
    });

    function fetchBulletinData() {
        fetch(`${contextRoot}/public/product/bulletin/api/${product.PId}`, {
            method: "get",
            headers: { 'Content-Type': 'application/json', 'X-CSRF-TOKEN': '${csrfToken}' },
        }).then(response => {
            return response.json();
        }).then(data => {
            htmlMakerBulletin(data.bulletins, data.usernames, data.myUsername);
        }).catch(error => {
            console.error('Error:', error);
        });
    }

    function htmlMakerBulletin(data, usernames, myUsername) {
        let bulletinData = '';

        // 加入表單HTML
        let formHtml = `
            <form id="bulletinForm">
                <input type="hidden" name="productId" value="${product.PId}">
                <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-group">
                    <textarea name="text" class="form-control"></textarea>
                </div>
                <button id="textsubmit" type="submit" class="btn btn-primary">送出</button>
            </form>
        `;
        bulletinData += formHtml;

        // 加入即時訊息顯示區域
        let messageHtml = `
            <div id="message"></div>
        `;
        bulletinData += messageHtml;

        const myUsernameIndex = usernames.findIndex((username) => username === myUsername);

        for (var i = 0; i < data.length; i++) {
            bulletinData += '<div class="card mt-3">';
            bulletinData += '<div class="card-header';
            if (myUsername.length > 0 && myUsername[i] === usernames[i]) {
                bulletinData += ' bg-warning'; // 設定背景顏色為藍色
            }
            bulletinData += '">';
            bulletinData += '<div class="row">';
            bulletinData += '<div class="col">' + data[i].addedTime + '</div>';
            bulletinData += '<div class="col text-right">' + '<div class="username">' + usernames[i] + '</div>' + '</div>';
            bulletinData += '</div>';
            bulletinData += '</div>';
            bulletinData += '<div class="card-body"><p class="card-text">' + data[i].content + '</p></div>';
            bulletinData += '</div>';
        }

        console.log(bulletinData); // 印出最終產生的 HTML，檢查是否正確
        let pillsBulletin = document.getElementById('pills-profile');
        pillsBulletin.innerHTML = bulletinData;

        // 監聽表單提交事件
        const form = document.getElementById('bulletinForm');
        form.addEventListener('submit', function(e){
            e.preventDefault();
            submitBulletinForm(form);
        });
    }

    function submitBulletinForm(form) {
    	  const formData = new FormData(form);
    	  const csrfToken = document.getElementById('csrf').value;
    	  fetch(`${contextRoot}/product/bulletin/api`, {
    	    method: "post",
    	    body: formData,
    	    headers: { 'X-CSRF-TOKEN': csrfToken },
    	  })
    	  .then(response => {
    	    if (response.status === 401) {
    	      window.location.href = "${contextRoot}/public/signIn";
    	    }
    	    return response.json();
    	  })
    	  .then(data => {
    		  const messageElement = document.getElementById('message');
    		  if (data.error) {
    		    messageElement.innerText = data.error;
    		  } else {
    		    messageElement.innerText = data.message;
    		    form.reset(); // 清空表單輸入欄位
    		    setTimeout(() => {
    		      messageElement.innerText = '';
    		      fetchBulletinData();
    		    }, 3000);
    		  }
    		})
    		.catch(error => {
    		  console.error('Error:', error);
    		});
    	}




	</script>
    
    
	<script type="text/javascript">
		const faqTab = document.getElementById("pills-FAQ-tab");
		faqTab.addEventListener('click', function(e){
			fetch('${contextRoot}/public/product/faqs/api/${product.PId}',
				{
					method: 'get',
          // headers: {'Content-Type': 'application/json'}
				}
			).then(response => {
        return response.json();
      }).then(data =>{
        console.log(data);
        
        htmlMaker(data);
      })
		})

    function htmlMaker(data){
      faqData = '<div class="card">';
      for(var el of data){
        faqData += '<div class="card-header">'+ el.question +'</div>';
        faqData += '<div class="card-body"><p class="card-text">'+ el.answer +'</p></div>';        
      }      
      faqData += '</div>';
      console.log(faqData);
      let pillsFaq = document .getElementById('pills-contact');
      pillsFaq.innerHTML = faqData;
    }
  </script>

  <script type="text/javascript">
// //   	顯示運送地區 // 已改用model
//     const area = document.getElementById('purPlanArea');
//     const purchasePlanId = document.getElementById('purPlanId').value;
//     fetch('${contextRoot}/public/product/purchasePlan/'+purchasePlanId+'/area/api', 
//     {
//       method: 'get'
//     }).then(response => {
//       return response.json();
//     }).then(data => {
//       const success = data.success;
//       areaMaker(success);
//     })

//     function areaMaker(data){
//       areaData = '';
//       data.forEach(element => {
//         areaData += '<h6 class="card-subtitle mb-3 text-secondary">' + element.area + '</h6>';
//       });
//       area.innerHTML += area;
//     }
  </script>
  
  <script type="text/javascript">
//===== productPicture part start =====
  function showProductPictures(event) {
    const productId = document.getElementById("productId").value;
    const contextRoot = document.getElementById("contextRoot").value;
    document.getElementById("introPics").innerHTML = "";
    fetch("${contextRoot}/public/product/" + productId + "/picture/api")
      .then(function (response) {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error(response.status + ":" + response.statusText);
        }
      }).then(function (itemsJson) {
        if (itemsJson.status == 'success') {
          for (let i = 1; i <= 100; i++) {
            if (itemsJson.result[i] == undefined || i == 1) { // 該位置無圖片
              ;
            } else {
              // 圖片樣式改這
              $("#introPics").append(`<img id="pic_` + i + `" src="` + contextRoot + itemsJson.result[i] + `" width="500" >`);
            }
            console.log(itemsJson.result[i]);
          }
        } else if (itemsJson.status == 'empty') { // 如果該product完全沒有圖片
          ;
        } else { // 其他錯誤 // TODO 錯誤顯示方式(有空的話)
          console.log(itemsJson);
        }
      })
  }
  </script>
</body>
</html>