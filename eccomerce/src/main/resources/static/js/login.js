function formSubmitValidation(event) {
  let valid = true;

  // Clear previous errors
  $("#error-email, #error-password,#error-read").html("");

  // Store values in variables
  const email = $("#email").val().trim();
  const password = $("#password").val();
  


  if (!email) {
    $("#error-email").html("Email is required");
    valid = false;
  } 

  if (!password) {
    $("#error-password").html("Password is required");
    valid = false;
  }

  if (!valid) {
	  event.preventDefault();
	  return valid;
  }
}



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