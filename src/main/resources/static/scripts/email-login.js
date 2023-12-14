
function validateLogin(event) {
    event.preventDefault();

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