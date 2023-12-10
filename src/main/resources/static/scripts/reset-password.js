function checkPasswordStrength(password) {
  const minLength = 8; // Minimum length of the password
  // Check if the password has at least one number
  const hasNumber = /\d/.test(password);
  // Check if the password has at least one character
  const hasCharacter = /[A-Z]/.test(password) || /[a-z]/.test(password);

  // Get the password strength element
  const passwordStrengthEl = document.getElementById("passwordStrengthMessage");

  // Check if the password meets all the requirements
  if (password.length === 0) {
      passwordStrengthEl.textContent = "";
  } else if (password.length >= minLength && hasNumber && hasCharacter) {
      passwordStrengthEl.textContent = "鲁棒的密码";
  } else {
      passwordStrengthEl.textContent = "拉跨的密码";
  }
}

function checkPasswordConfirmation() {
  const password = document.getElementById("password").value;
  const passwordConfirmation = document.getElementById("retypePassword").value;
  const passwordConfirmationMessage = document.getElementById("passwordConfirmationMessage");
  if (password.length === 0) {
      passwordConfirmationMessage.textContent = null;
  } else if (password === passwordConfirmation) {
      passwordConfirmationMessage.textContent = "密码一致";
  } else {
      passwordConfirmationMessage.textContent = "密码不一致";
  }
}