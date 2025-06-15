document.addEventListener('DOMContentLoaded', function () {
  const fileInput = document.getElementById('file');
  const previewImg = document.getElementById('previewImg');
  const fileError = document.getElementById('fileError');

  // Only run if file input and required elements exist on the page
  if (fileInput) {
    fileInput.addEventListener('change', function (event) {
      const file = event.target.files[0];
      const maxSize = 300 * 1024; // 100 KB

      if (file) {
        const isValidType = ['image/png', 'image/jpeg'].includes(file.type);
        const isValidSize = file.size <= maxSize;

        if (!file.type.startsWith('image/') || !isValidType) {
          fileError.textContent = "Only PNG and JPG images are allowed.";
          fileError.style.display = "block";
          previewImg.style.display = "none";
          fileInput.value = ""; // Clear the file
          return;
        }

        if (!isValidSize) {
          fileError.textContent = "File must be less than 300KB.";
          fileError.style.display = "block";
          previewImg.style.display = "none";
          fileInput.value = ""; // Clear the file
          return;
        }

        // If valid
        fileError.style.display = "none";
        const reader = new FileReader();
        reader.onload = function (e) {
          previewImg.src = e.target.result;
          previewImg.style.display = "block";
        };
        reader.readAsDataURL(file);
		
		
      } else {
        // No file selected
        previewImg.style.display = "none";
        fileError.style.display = "none";
      }
    });
  }
});



(() => {
	    'use strict';
	    const forms = document.querySelectorAll('.needs-validation');
	    Array.from(forms).forEach(form => {
	      form.addEventListener('submit', event => {
	        if (!form.checkValidity()) {
	          event.preventDefault();
	          event.stopPropagation();
	        }
	        form.classList.add('was-validated');
	      }, false);
	    });
	  })();