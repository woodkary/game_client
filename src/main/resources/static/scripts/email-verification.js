let verificationCode="";
function checkVerificationCode(event) {
  event.preventDefault();
  let xhr=new XMLHttpRequest();
  xhr.open("GET", "http://localhost:8080/sendVeriCode");
  xhr.setRequestHeader("Content-Type", "application/json");

  xhr.onreadystatechange = function () {
    let response = JSON.parse(xhr.responseText);
    if (xhr.readyState===4&&xhr.status === 200) {
      // Handle the response here
      verificationCode=response.data;
    }else{
      console.log("错误"+response);
    }
  };
  xhr.send();
}

function compareVerificationCode(event) {
  event.preventDefault();
  const userInput = document.getElementById('verification-code').value;
  if (verificationCode === userInput) {
    console.log('Verification code is correct');
  } else {
    console.log('Verification code is incorrect');
  }
}
