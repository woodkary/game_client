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
function getCaptcha() {
    let xhr = new XMLHttpRequest();
    let email = document.getElementById("email").value;
    xhr.open("GET", "http://localhost:8080/sendVeriCode/2?email=" + email);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.withCredentials = true;

    console.log("email: " + email);

    xhr.onreadystatechange = function () {
        console.log("hello");
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                console.log(response);
            } else {
                console.log("Error: " + xhr.status);
            }
        }
    };
    xhr.send();

    //countdown
    const button = document.getElementById("get-captcha-btn");
    button.disabled = true;

    let countdown = 60;
    button.textContent = countdown + "秒后重新获取";
    const timer = setInterval(() => {
        countdown--;
        if (countdown <= 0) {
            // If the countdown is over, enable the button and reset the text
            clearInterval(timer);
            button.textContent = "获取验证码";
            button.disabled = false;
        } else {
            // Update the button text
            button.textContent = countdown + "秒后重新获取";
        }
    }, 1000);
}
function submitVeriCode(event) {
    event.preventDefault();
    let formData = document.getElementById("captcha").value;
    formData = JSON.parse(formData);
    return new Promise((resolve, reject) => {
        // 发送第一个请求
        // 请将实际的请求代码替换成你的实际代码
        fetch('http://localhost:8080/typeVeriCode/2', {
            method: 'POST',
            body: formData,
            headers: { 'content-type': 'application/json' },
            withCredentials: true
            // 其他请求参数
        })
            .then(response => {
                let r = response.json();
                if (!response.ok && response.status !== 400) {
                    throw new Error("服务器错误");
                }
                if (response.status === 400) {
                    const usernameMessage = document.getElementById("usernameAvailabilityMessage");
                    usernameMessage.style.color = "red";
                    usernameMessage.textContent = r.message;
                    throw new Error("输入有错误,错误如下：" + r.message);
                } else {
                    alert("修改成功");
                    resolve(r);
                    window.location.href = "../pages/login.html";
                }
            }).catch(error => {
            let emailMessage = document.getElementById("emailMessage");
            emailMessage = error.message;
            console.error(error);
            reject(error);
        })
    });
}