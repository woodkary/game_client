document.getElementById("change-password").addEventListener("click", function() {
  alert("密码重设成功");
  window.location.href = "../login/login.html"
});
document.getElementById("send-code").addEventListener("click", function() {
  alert("验证码已发送,请查看你的邮箱");
});

function checkPassword() {
  const password = document.getElementById("password").value;
  var password2 = document.getElementById("password2").value;
  if (password !== password2) {
    alert("两次输入的密码不一致");
    return false;
  }
  return true;
}

function checkPasswordStrength() {
  var password = document.getElementById("password").value;
  if (password.length < 8) {
    alert("密码长度不能小于8位");
    return false;
  }
  return true;
}