function checkVerificationCode() {
  // Make an API call to the backend to get the verification code
  // Replace the API_URL with the actual URL of your backend API
  fetch(API_URL)
    .then(response => response.json())
    .then(data => {
      // Store the received verification code in a variable
      const verificationCode = data.code;
      compareVerificationCode(verificationCode);
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function compareVerificationCode(verificationCode) {
  const userInput = document.getElementById('verification-code').value;
  if (verificationCode === userInput) {
    console.log('Verification code is correct');
  } else {
    console.log('Verification code is incorrect');
  }
}

receiveVerificationCode();
