function validateLogin(event) {
    event.preventDefault();
    let veriCode = document.getElementById("captcha").value;
    console.log(veriCode);
    let veriCodeJson = JSON.parse(veriCode);
    console.log(veriCodeJson);

    let xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.withCredentials = true;
    xmlHttpRequest.onreadystatechange = function () { // 设置响应http请求状态变化的事件
        if (xmlHttpRequest.readyState === 4) {
            let message = document.getElementById("verification-message");
            if (xmlHttpRequest.status === 200) {
                try {
                    let jsonResult = JSON.parse(xmlHttpRequest.responseText);
                    console.log(jsonResult);
                    console.log(jsonResult.responseText);
                    message.textContent = jsonResult.message;
                    let username = jsonResult.data.username;
                    console.log(username);
                    message.style.color = "green";
                    redirectToIndexPage(username);
                } catch (e) {
                    console.log(e);
                }
            } else {
                message.textContent = "验证码错误或过期";
                message.style.color = "red";
            }
        }
    }
    xmlHttpRequest.open("POST", "http://localhost:8080/typeVeriCode/3", true); // 创建http请求，并指定请求得方法（get）、url（https://www.runoob.com/try/ajax/ajax_info.txt）以及验证信息
    xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
    xmlHttpRequest.send(veriCodeJson); // 发送请求
}

function getCaptcha(event) {
    event.preventDefault();
    let xhr = new XMLHttpRequest();
    let email = document.getElementById("email").value;
    xhr.open("GET", "http://localhost:8080/sendVeriCode/3?email=" + email);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.withCredentials = true;
    xhr.onload = function () {
        let response = JSON.parse(xhr.responseText);
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("已发送验证码");
        } else {
            console.log("错误" + response.textContent);
        }
    };
    xhr.send();
}


function redirectToIndexPage(username) {
    window.location.href = "../index.html?username=" + encodeURIComponent(username);
}

function redirectToLoginPage() {
    window.location.href = "../pages/login.html";
}