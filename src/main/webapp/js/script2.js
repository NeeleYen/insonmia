// 上傳會員照片
const fileInput = document.getElementById('fileInput');
const preview = document.getElementById('preview');

fileInput.addEventListener('change', function () {
    const file = fileInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', function () {
        preview.setAttribute('src', reader.result);
        preview.style.display = 'block';
    });

    reader.readAsDataURL(file);
});
// 上傳會員照片