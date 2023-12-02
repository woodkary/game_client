
document.getElementById("login-form").addEventListener("submit", validateLogin);

function validateLogin() {
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function () { // 设置响应http请求状态变化的事件
        var jsonResult = JSON.parse(xmlHttpRequest.responseText);
        var message = document.getElementById("verification-message");
        if (xmlHttpRequest.status == 200) {
            message.textContent = jsonResult.message;
            message.style.color = "green";
            redirectToVerifyPage();
        } else {
            message.textContent = jsonResult.message;
            message.style.color = "red";
        }
    }

    var email = document.getElementById("email").value;

    /*let requestData = { username: username, password: password };
    requestData=JSON.stringify(requestData);*/

    xmlHttpRequest.open("GET", "http://localhost:8080/login?username=" + username + "&password=" + password, true); // 创建http请求，并指定请求得方法（get）、url（https://www.runoob.com/try/ajax/ajax_info.txt）以及验证信息
    xmlHttpRequest.send(null); // 发送请求

    //Post email to server
}

function redirectToVerifyPage() {
    window.location.href = "../pages/email-verification.html";
}

function redirectToLoginPage() {
    window.location.href = "../pages/login.html";
}