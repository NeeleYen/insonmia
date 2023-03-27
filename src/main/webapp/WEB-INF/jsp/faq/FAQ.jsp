<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
		<title>常見問題</title>
	</head>
	<body>
		<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
		<div class="container mt-5 mb-4">
			<div class="accordion accordion-flush" id="accordionFlushExample">
			
				<!-- 問題一 -->
				<div class="accordion-item">
					<h1 class="accordion-header" id="flush-headingOne">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
							忘記密碼怎麼辦？
						</button>
					</h1>
					<div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">當您忘記其登入本網站的密碼時，可以點擊“忘記密碼”按鈕，以通過電子郵件進行密碼重置。</div>
					</div>
				</div>
				
				<!-- 問題二 -->
				<div class="accordion-item">
					<h1 class="accordion-header" id="flush-headingTwo">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
							無法收到驗證信怎麼辦？
						</button>
					</h1>
					<div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">成功送出資料但卻無法收到驗證信。這時候可以確認信箱是否正確、檢查垃圾郵件箱等解決方法。</div>
					</div>
				</div>
				
				<!-- 問題三 -->
				<div class="accordion-item">
					<h1 class="accordion-header" id="flush-headingThree">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
							認繳後如何付款？
						</button>
					</h1>
					<div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">目前可使用綠界ecpay來付款。</div>
					</div>
				</div>
				
				<!-- 問題四 -->
				<div class="accordion-item">
					<h1 class="accordion-header" id="flush-headingFour">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseFour" aria-expanded="false" aria-controls="flush-collapseFour">
							付款失敗怎麼辦？
						</button>
					</h1>
					<div id="flush-collapseFour" class="accordion-collapse collapse" aria-labelledby="flush-headingFour" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">當您在本網站上進行付款時，可能會遇到付款失敗、付款被拒絕或付款後未收到訂單等問題。解決方法包括檢查信用卡資訊是否正確、聯繫銀行或支付平台以獲得幫助、或聯繫網站客戶服務部門以進行補救措施。</div>
					</div>
				</div>
				
				<!-- 問題五 -->
				<div class="accordion-item">
					<h2 class="accordion-header" id="flush-headingFive">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseFive" aria-expanded="false" aria-controls="flush-collapseFive">
							認繳後如果反悔了是否能退款？
						</button>
					</h2>
					<div id="flush-collapseFive" class="accordion-collapse collapse" aria-labelledby="flush-headingFive" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">目前暫無退款選項，因此需請您確定支持該專案後再認繳。</div>
					</div>
				</div>
				
				<!-- 問題六 -->
				<div class="accordion-item">
					<h2 class="accordion-header" id="flush-headingSix">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseSix" aria-expanded="false" aria-controls="flush-collapseSix">
							商品是否需額外負擔運費？
						</button>
					</h2>
					<div id="flush-collapseSix" class="accordion-collapse collapse" aria-labelledby="flush-headingSix" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">台灣本島皆不須負擔運費，離島及國外則須負擔運費。</div>
					</div>
				</div>
				
				<!-- 問題七 -->
				<div class="accordion-item">
					<h2 class="accordion-header" id="flush-headingSeven">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseSeven" aria-expanded="false" aria-controls="flush-collapseSeven">
							無法找到所需內容？
						</button>
					</h2>
					<div id="flush-collapseSeven" class="accordion-collapse collapse" aria-labelledby="flush-headingSeven" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">如果您無法找到需要的內容，可能是由於網站結構不清晰或搜索引擎問題所導致。解決方法包括儘可能提供清晰的導航和搜索功能、聯繫網站管理員以獲取幫助。</div>
					</div>
				</div>
				
				<!-- 問題八 -->
				<div class="accordion-item">
					<h2 class="accordion-header" id="flush-headingEight">
						<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseEight" aria-expanded="false" aria-controls="flush-collapseEight">
							我要怎麼回首頁?
						</button>
					</h2>
					<div id="flush-collapseEight" class="accordion-collapse collapse" aria-labelledby="flush-headingEight" data-bs-parent="#accordionFlushExample">
						<div class="accordion-body">點擊左上角的圖示即可回首頁。</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	</body>
</html>