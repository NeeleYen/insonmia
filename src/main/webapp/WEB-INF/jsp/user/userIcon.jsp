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
<link href="${contextRoot}/css/bootstrap.min.css" rel="stylesheet" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.css" />
<style type="text/css">
	#cropper-wrapper {
	  position: relative;
	  top: 10px;
	  width: 300px;
	  height: 300px;
	/*   overflow: hidden; */
	}
	
	#cropper-image { 
	  width: 100%; 
	  height: 100%; 
	  object-fit: cover; 
	} 
	
	.cropper-view-box,
	.cropper-face,
	.cropper-line,
	.cropper-point {
	  border-radius: 50%;
	}
</style>


<title>會員圖片上傳</title>
</head>
<body>
	<jsp:include page="../layout/navbarNew.jsp"></jsp:include>
	<div class="container">
	<h1 class= "mt-5 mb-4">會員圖片上傳</h1>
	<form id="photo" method="post" enctype="multipart/form-data">
		<input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<div class="mb-3  input-group">
<!-- 				<label class="btn btn-dark" for="picture">picture</label> -->
 				<input class="form-control" type="file" id="picture" name="picture" multiple>
 				<!-- style="display: none;"  -->
 				<!-- 文字: placeholder -->
<!-- 				<button type="submit" class="btn btn-primary" id="btn1">上傳</button> -->
			</div>
	</form>
	<div class="mt-5"></div>
	<div id="msg1" class="mt-3 d-flex justify-content-center"></div>
	<div id="upload_result" class="d-flex justify-content-center"></div>
	</div>

	
<jsp:include page="../layout/footerForProductPage.jsp"></jsp:include>
	
	<script
	src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.js"></script>
	<script type="text/javascript">
// 	const form = document.querySelector('#photo');
// 	form.addEventListener('submit', e => {
// 	  e.preventDefault(); // 阻止表單的默認提交行為
// 	  const formData = new FormData(form);
// 	  fetch('${contextRoot}/user/priture', {
// 	    method: 'POST',
// 	    body: formData
// 	  })
// 	  .then(response => {
// 	    if (!response.ok) {
// 	      throw new Error('Network response was not ok');
// 	    }
// 	    return response.blob(); // 將二進制數據轉換為Blob對象
// 	  })
// 	  .then(blob => {
// 	    const img = document.createElement('img');
// 	    img.src = URL.createObjectURL(blob); // 將Blob對象轉換為URL
// 	    img.width=300;
// 	    img.height=300;
// 	    img.style.objectFit = 'cover';
// 	    img.style.position = 'relative;';
// 	    img.style.borderRadius = '50%';
// 	    document.querySelector('#upload_result').appendChild(img);
// 	  })
// 	  .catch(error => {
// 	    console.error('Error:', error);
// 	  });
// 	});	
	
// let originalBlob;
	/////////////////////////////////
const form = document.querySelector('#photo');
let cropperExist = false;

form.addEventListener('change', () => {
  const formData = new FormData(form);
  const file = formData.get('picture');

  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
	if (cropperExist) {
		const cropperWrapper = document.querySelector('#cropper-wrapper');
		removeChild(cropperWrapper); 
        // 如果剪裁框已經存在，就不需要再創建新的剪裁框了
	}
  	  
    const img = document.createElement('img');
    img.src = reader.result;
    img.onload = () => {
      const cropperWrapper = document.createElement('div');
      cropperWrapper.id = 'cropper-wrapper';
      const cropperImage = document.createElement('img');
      cropperImage.id = 'cropper-image';
      cropperImage.src = img.src;
      cropperWrapper.appendChild(cropperImage);
      const cropBtn = document.createElement('button');
      cropBtn.id = 'crop-btn';
      cropBtn.innerText = '裁剪並上傳';
      cropBtn.classList.add('btn', 'btn-outline-dark', 'm-3');
      cropperWrapper.appendChild(cropBtn);
      const div1 = document.createElement('div');
      div1.id = 'div1';
      div1.classList.add('mt-5');
      cropperWrapper.appendChild(div1);
      cropperExist = true;

      form.parentNode.insertBefore(cropperWrapper, form.nextSibling);
      const cropper = new Cropper(cropperImage, {
        aspectRatio: 1,
        viewMode: 1,
        autoCropArea: 1,
        cropBoxResizable: true,
        minCropBoxWidth: 300,
        minCropBoxHeight: 300,
      });

      cropBtn.addEventListener('click', () => {
        form.reset();
        const croppedCanvas = cropper.getCroppedCanvas();
        croppedCanvas.toBlob(blob => {
          const croppedImg = new Image();
          croppedImg.src = URL.createObjectURL(blob);
          croppedImg.width = 300;
          croppedImg.height = 300;
          croppedImg.style.objectFit = 'cover';
          croppedImg.style.position = 'relative;';
          croppedImg.style.borderRadius = '50%';
          
          cropperWrapper.parentNode.removeChild(cropperWrapper);
          const croppedFormData = new FormData();
          croppedFormData.append('picture', blob);
		  const csrfToken = document.getElementById('csrf').value;

          fetch('${contextRoot}/user/priture', {
            method: 'POST',
            body: croppedFormData,
            headers: {
                'X-CSRF-TOKEN': csrfToken
              }
          })
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.blob();
          })
          .then(blob => {
        	const imgIcon = document.getElementById('imgIcon');
        	imgIcon.src = URL.createObjectURL(blob); // 將Blob對象轉換為URL
        	
            const img = document.createElement('img');
            img.src = URL.createObjectURL(blob); // 將Blob對象轉換為URL
            img.width=300;
            img.height=300;
            img.style.objectFit = 'cover';
            img.style.position = 'relative;';
            img.style.borderRadius = '50%';
            document.querySelector('#upload_result').appendChild(img);
            const msg1 = document.getElementById('msg1');
            msg1.innerHTML = `<p class="text-primary">圖片顯示5秒後清除</p>`
            setTimeout(()=>{
            	cropperExist = false;
          		removeImage(img); msg1.innerHTML = '';
            }, 5000);
          })
          .catch(error => {
            console.error('Error:', error);
          });
        });
      });
    };
  };
});

function removeChild(cropperWrapper){
	cropperWrapper.parentNode.removeChild(cropperWrapper);
}

function removeImage(img) {
	  img.parentNode.removeChild(img);
}
	
	////////////////////////////////

	</script>
	
	
</body>
</html>