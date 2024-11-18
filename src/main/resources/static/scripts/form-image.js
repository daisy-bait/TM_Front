function previewImage(event) {
    const input = event.target;
    const file = input.files[0];
    const reader = new FileReader();

    reader.onload = function() {
        const preview = document.getElementById('preview');
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
        const fileNameSpan = document.getElementById('fileName');
        let fileName = file.name;
        if (fileName.length > 20) {
            fileName = fileName.substring(0, 10) + '...' + fileName.substring(fileName.length - 4);
        }
        fileNameSpan.textContent = fileName;
    }
}