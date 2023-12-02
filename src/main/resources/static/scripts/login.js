function validateLogin(){

    var xmlHttpRequest = new XMLHttpRequest();

    xmlHttpRequest.onreadystatechange=function(){ // 设置响应http请求状态变化的事件
        if(xmlHttpRequest.readyState == 4){ // 判断异步调用是否成功,若成功开始局部更新数据
            var jsonResult=JSON.parse(xmlHttpRequest.responseText);
            var message = document.getElementById("verification-message");
            if(xmlHttpRequest.status == 200) {
                message.textContent = jsonResult.message;
                message.style.color = "green";
                window.location.href = "../index.html";
            } else {
                message.textContent = jsonResult.message;
                message.style.color = "red";
            }
        }else{
            message.textContent = "发送不成功";
            message.style.color = "red";
        }
    }
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    // 创建一个新的AJAX请求对象
    var xhr = new XMLHttpRequest();

    // 配置请求
    xhr.open('GET', 'http://localhost:8080/login?username='+username+"&password="+password); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');
    // 定义处理响应的函数
    xhr.onload = function() {
        if (xhr.status === 200) {
            var response = xhr.responseText;
            var jsonResult=JSON.parse(response);
            var message = document.getElementById("verification-message");
            message.textContent = jsonResult.message;
            message.style.color = "green";
            window.location.href = "../../index.html";
        } else {
            var message = document.getElementById("verification-message");
            message.textContent = "发送不成功";
            message.style.color = "red";
            alert('请求失败。错误代码：' + xhr.status);
        }
    };

    xhr.send();
}

function redirectToPage() {
    window.location.href = "email-login.html";
}
document.getElementById("login-form").addEventListener("submit", validateLogin);