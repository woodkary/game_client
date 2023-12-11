function checkUsernameAvailability(username) {
    const usernameMessage = document.getElementById("usernameAvailabilityMessage");

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/ranks/checkIfExist?username=" + username);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.withCredentials = true;
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log(xhr.responseText);
                console.log(typeof xhr.responseText);
                const response = JSON.parse(xhr.responseText);
                console.log(response);
                if (response === false) {
                    usernameMessage.textContent = "用户名可用";
                } else {
                    usernameMessage.textContent = "用户名已存在";
                }
            } else {
                console.log("Error: " + xhr.status);
            }
        }
    };
    xhr.send();
}

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
        passwordStrengthEl.style.color="green";
    } else {
        passwordStrengthEl.textContent = "拉跨的密码";
        passwordStrengthEl.style.color="red";
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
    xhr.open("GET", "http://localhost:8080/sendVeriCode/1?email=" + email);
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
function submitUsrAndPwd(event){
    event.preventDefault();
    const username=document.getElementById("username").value;
    const password=document.getElementById("password").value;
    let formData={};
    formData["username"]=username;
    formData["password"]=password;
    formData=JSON.parse(formData);
    return new Promise(() => {
        // 发送第一个请求
        // 请将实际的请求代码替换成你的实际代码
        fetch('http://localhost:8080/register', {
            method: 'POST',
            data: formData,
            headers: { 'content-type': 'application/json' },
            withCredentials: true
            // 其他请求参数
        })
        .then(response => {
            let r=response.json();
            if(!response.ok&&response.status !== 400){
                throw new Error("服务器错误");
            }
            if(response.status===400){
                const usernameMessage = document.getElementById("usernameAvailabilityMessage");
                usernameMessage.style.color="red";
                usernameMessage.textContent=r.message;
            }
        }).catch(error=>{
            console.error(error);
        })
    });
}
function submitVeriCode(event){
    event.preventDefault();
    let formData = document.getElementById("captcha").value;
    formData=JSON.parse(formData);
    return new Promise((resolve, reject) => {
        // 发送第一个请求
        // 请将实际的请求代码替换成你的实际代码
        fetch('http://localhost:8080/typeVeriCode/1', {
            method: 'POST',
            data: formData,
            headers: { 'content-type': 'application/json' },
            withCredentials: true
            // 其他请求参数
        })
            .then(response => {
                let r=response.json();
                if(!response.ok&&response.status !== 400){
                    throw new Error("服务器错误");
                }
                if(response.status===400){
                    const usernameMessage = document.getElementById("usernameAvailabilityMessage");
                    usernameMessage.style.color="red";
                    usernameMessage.textContent=r.message;
                }else{
                    alert("注册成功");
                    window.location.href = "../pages/login.html";
                }
            }).catch(error=>{
            console.error(error);
        })

    });
    /*axios({
        method: 'post',
        url: 'http://localhost:8080/typeVeriCode/1',
        data: formData,
        headers: { 'content-type': 'application/json' },
        withCredentials: true
    }).then(response => {
        console.log(response);
        window.location.href = "../pages/login.html";
    }).catch(error => {
        console.error(error);
    });*/
}
function submitAccountForm(event) {
    event.preventDefault();
    submitUsrAndPwd(event)
        .then(response1 => {
            // 处理第一个请求的结果
            console.log(response1);
           return submitVeriCode(event);
        })
        .then(response2 => {
            console.log(response2);
        })
        .catch(error=>{
            // 处理错误
            console.error(error);
        })

}


