<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="${contextRoot}/css/popup.css">
<link rel="stylesheet"
	href="https://meyerweb.com/eric/tools/css/reset/reset.css">
<link rel="stylesheet" href="${contextRoot}/css/bootstrap.min.css">
<script src="${contextRoot}/js/jquery-3.6.3.min.js"></script>
<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<script src="${contextRoot}/js/bootstrap.bundle.min.js"></script>

<title>刪除信用卡</title>
</head>

<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
		<h1 class="mt-5 mb-4">信用卡</h1>
		<div class="row">
			<div class="col-md-6 mb-2">
				<form:form method="post" action="${contextRoot}/user/deleteCard" modelAttribute="findCard">
					<div class="row">
						<div class="col-md-6 mb-2">
							<select id="cardSelect" name="card" class="form-select">
								<option value="">選擇信用卡</option>
								<c:forEach var="entry" items="${findCard}">
									<option value="${entry.key}">${entry.value}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-6">
							<button id="delete-card" class="btn btn-outline-danger">刪除</button>
						</div>
					</div>
					<div id="msg1" class="mt-2"></div>
				</form:form>
			</div>
		</div>
	</div>

	<br>

	<!--彈出視窗-->
	<div class="container container-sm" id="containerCss">
		<div id="myModal" class="modal">
			<div class="modal-content">
				<span class="close">&times;</span>
				<p>你確定要刪除此項目嗎？</p>
				<button id="confirm-delete">刪除</button>
				<button id="popup-cancel">取消</button>
			</div>
		</div>
	</div>

<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	<script>
	document.addEventListener("DOMContentLoaded", function() {
		  const form = document.querySelector("form");	//找form標籤
		  const modal = document.getElementById("myModal");	//彈出視窗的div
		  const cardSelect = document.getElementById("cardSelect");	//選信用卡的下拉選單
		  const confirmDeleteBtn = document.getElementById("confirm-delete");	//彈出視窗的刪除按鈕
		  const cancelBtn = document.getElementById("popup-cancel");	//彈出視窗的取消按鈕
		  const closeBtn = document.getElementsByClassName("close")[0];	//關掉彈出視窗的X鈕
		  const msg1 = document.getElementById("msg1");	//回應訊息

		  // Prevent page refresh on form submit, open modal
		  /*modal.style.display 是一個 JavaScript 操作，用於控制 HTML 元素的顯示方式。在這段程式碼中，它被用於設置彈出視窗的顯示方式。

具體來說，modal.style.display = "block" 的意思是將彈出視窗的 display 屬性設置為 "block"，這樣該元素就會以塊級元素的方式顯示出來，佔據一個獨立的區塊，而不是作為行內元素出現在文本流中。

這個程式碼是在表單提交事件中觸發，當用戶提交表單時，它會阻止表單的默認行為（即頁面重新加載），然後將彈出視窗的顯示方式設置為 "block"，從而顯示出彈出視窗。*/
		  form.addEventListener("submit", function(event) {
		    event.preventDefault();
		    modal.style.display = "block";
		  });

		  // Close modal when user clicks close button or outside modal
		  closeBtn.onclick = function() {
		    modal.style.display = "none";
		  }
		  window.onclick = function(event) {
		    if (event.target == modal) {
		      modal.style.display = "none";
		    }
		  }

		  // Submit form when user clicks confirm-delete button
		  confirmDeleteBtn.addEventListener("click", function() {
		    const formData = new FormData(form);
		    const url = form.getAttribute("action");
		    fetch(url, {
		      method: "POST",
		      body: formData
		    })
		    .then(response => response.json())
		    .then(data => {
		      if (data.status === "success") {
		         const selectElement = document.getElementById("cardSelect");
		         const findCard = data.findCard;
		         // clear the current options
		         selectElement.options.length = 0;
		         // add new options
		         const defaultOption = document.createElement("option");
		         defaultOption.value = "";
		         defaultOption.text = "選擇信用卡";
		         selectElement.add(defaultOption);
	        	 // Object.entries()取得指定物件的可列舉屬性的鍵-值配對數組。它返回一個數組，其中包含指定物件自身可列舉屬性的 [key, value] 配對數組。
		         for (const [key, value] of Object.entries(findCard)) { 
		             const option = document.createElement("option");
		             option.value = key;
		             option.text = value;
		             selectElement.add(option);
		         }
		        const successMsg = "<div class='alert alert-success alert-dismissible fade show' role='alert'>"
					+ data.status+ "</div>";
		        cardSelect.value = "";
		        modal.style.display = "none";
		        msg1.innerHTML = successMsg;
		        setTimeout(function() {
		          msg1.innerHTML = "";
		        }, 5000);
		      } else {
		        const errorMsg = "<div class='alert alert-danger alert-dismissible fade show' role='alert'>"
					+ response.message + "</div>";
		        cardSelect.value = "";
		        modal.style.display = "none";
		        msg1.innerHTML = errorMsg;
		        setTimeout(function() {w
		          msg1.innerHTML = "";
		        }, 5000);
		      }
		    })
		    .catch(error => {
		      const errorMsg = "<div class='alert alert-danger alert-dismissible fade show' role='alert'>刪除失敗</div>";
		      cardSelect.value = "";
		      modal.style.display = "none";
		      msg1.innerHTML = errorMsg;
		      setTimeout(function() {msg1.innerHTML = "";}, 5000);
		    });
		  });
		  // Close modal when user clicks cancel button
		  cancelBtn.addEventListener("click", function() {
		    modal.style.display = "none";
		  });
		});
	

	</script>
</body>

</html>