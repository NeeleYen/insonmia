<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>footer</title>
	<!-- css 抓不到 -->
    <link rel="stylesheet" href="${ contextRoot }/css/style1 copy.css">

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<footer class="bg-dark text-center text-white footer" id="footer">
        <div class="p-4">
            <!-- 社群連結 -->
            <section class="mb-4">
                <!-- Gmail -->
                <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i
                        class="fa-solid fa-envelope"></i></a>

                <!-- Twitter -->
                <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i class="fab fa-twitter"></i></a>


                <!-- Facebook -->
                <a class="btn btn-light btn-floating m-1" href="#!" role="button"><i
                        class="fa-brands fa-facebook"></i></a>
            </section>
            <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0);">
                高雄市前金區中正四路211號8號樓之1
            </div>
        </div>

    </footer>
</body>
</html>