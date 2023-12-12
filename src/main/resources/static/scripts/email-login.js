
function validateLogin(event) {
    event.preventDefault();
    let veriCode=document.getElementById("captcha").value;
    veriCode=JSON.parse(veriCode);

    let xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.withCredentials=true;
    xmlHttpRequest.onreadystatechange = function () { // 设置响应http请求状态变化的事件
        let jsonResult = JSON.parse(xmlHttpRequest.responseText);
        let message = document.getElementById("verification-message");
        if (xmlHttpRequest.status === 200) {
            message.textContent = jsonResult.message;
            let username=jsonResult.data.username;
            message.style.color = "green";
            redirectToIndexPage(username);
        } else {
            message.textContent = jsonResult.message;
            message.style.color = "red";
        }
    }

    /*var email = document.getElementById("email").value;*/
    /*let requestData = { username: username, password: password };
    requestData=JSON.stringify(requestData);*/

    xmlHttpRequest.open("POST", "http://localhost:8080/typeVeriCode/3", true); // 创建http请求，并指定请求得方法（get）、url（https://www.runoob.com/try/ajax/ajax_info.txt）以及验证信息
    xmlHttpRequest.send(veriCode); // 发送请求

    //Post email to server
}

function getCaptcha(event) {
    event.preventDefault();
    let xhr=new XMLHttpRequest();
    let email = document.getElementById("email").value;
    xhr.open("GET", "http://localhost:8080/sendVeriCode/3?email="+ email);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.withCredentials=true;
    
    xhr.onload = function () {
        let response = JSON.parse(xhr.responseText);
        if (xhr.readyState===4&&xhr.status === 200) {
            alert("已发送验证码");
        }else{
            console.log("错误"+response.textContent);
        }
    };
    xhr.send();
}




function redirectToIndexPage(username) {
    window.location.href = "../../index.html?username="+encodeURIComponent(username);
}

function redirectToLoginPage() {
    window.location.href = "../pages/login.html";
}