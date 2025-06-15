function formSubmitValidation(event) {
  let valid = true;

  // Clear previous errors
  $("#error-name, #error-email, #error-password, #error-confirmPassword, #error-read").html("");

  // Store values in variables
  const fullName = $("#fullName").val().trim();
  const email = $("#email").val().trim();
  const password = $("#password").val();
  const confirmPassword = $("#confirmPassword").val();
  const termsChecked = $("#rememberMe").prop("checked");

  if (!fullName) {
    $("#error-name").html("Full Name is required");
    valid = false;
  }

  if (!email) {
    $("#error-email").html("Email is required");
    valid = false;
  } 

  if (!password) {
    $("#error-password").html("Password is required");
    valid = false;
  }

  if (password !== confirmPassword) {
    $("#error-confirmPassword").html("Passwords do not match");
    valid = false;
  }

  if (!termsChecked) {
    $("#error-read").html("Please agree to terms");
    valid = false;
  }

  if (!valid) {
	  event.preventDefault();
	  return valid;
  }
}