document.getElementById("create-account").addEventListener("click", function () {
    var accountForm = document.getElementById("account-form");
    if (accountForm.checkValidity()) {
        accountForm.submit();
    }
});

document.getElementById("send-code").addEventListener("click", function () {
    var emailForm = document.getElementById("email-form");
    if (emailForm.checkValidity()) {
        alert("验证码已发送,请查看你的邮箱");
        var inputCode = document.getElementById("verification-code").value;
        fetch
        if (inputCode.length > 0) {
            emailForm.submit();
        }
    }
});