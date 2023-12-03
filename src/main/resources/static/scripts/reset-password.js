function checkPasswordIdentical() {
  const password = document.getElementById("password").value;
  var password2 = document.getElementById("password2").value;
  if (password !== password2) {
    alert("两次输入的密码不一致");
    return false;
  }
  return true;
}

function checkPasswordStrength() {
  if (!checkPasswordLength()) {
    return false;
  }
  if (!checkPasswordFormat()) {
    return false;
  }
  return true;
}

function checkPasswordLength() {
  var password = document.getElementById("password").value;
  if (password.length < 8) {
    alert("密码长度不能小于8位");
    return false;
  }
  return true;
}

//ensure password have some numbers and letters
function checkPasswordFormat() {
  var password = document.getElementById("password").value;
  var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
  if (!reg.test(password)) {
    alert("密码必须包含数字和字母");
    return false;
  }
  return true;
}