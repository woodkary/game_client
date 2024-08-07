function validateLogin(event){
    event.preventDefault();
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    // 创建一个新的AJAX请求对象
    var xhr = new XMLHttpRequest();
    xhr.withCredentials=true;
    // 配置请求
    xhr.open('GET', 'http://localhost:8080/login?username='+username+"&password="+password); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');
    // 定义处理响应的函数
    xhr.onload = function() {
        var response = xhr.responseText;
        var jsonResult=JSON.parse(response);
        if (xhr.status === 200) {
            var message = document.getElementById("verification-message");
            message.textContent = jsonResult.message;
            let username=jsonResult.data.username;
            message.style.color = "green";
            window.location.href = "../index.html?username="+encodeURIComponent(username);
        } else {
            var message = document.getElementById("verification-message");
            message.textContent = jsonResult.message;
            message.style.color = "red";
        }
    };

    xhr.send();
}

function redirectToPage(event) {
    event.preventDefault();
    window.location.href = "email-login.html";
}