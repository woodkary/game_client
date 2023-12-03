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

function compareVerificationCode(event,doc) {
  event.preventDefault();
  let xhr=new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/typeVeriCode/1");
  xhr.setRequestHeader("Content-Type", "application/json");

  xhr.onreadystatechange = function () {
    let response = JSON.parse(xhr.responseText);
    if (xhr.readyState===4&&xhr.status === 200) {
      // Handle the response here
      alert("注册成功");
      window.location.href="login.html";
    }else{
      console.log("错误"+response);
    }
  };
  let inputVericode=JSON.parse(doc.getElementById("verification-code").value);
  xhr.send(inputVericode);
}
