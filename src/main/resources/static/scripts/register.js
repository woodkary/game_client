const formEL = document.getElementById("formEL");
formEL.addEventListener("submit", function (event) {
    event.preventDefault();
    const inputELs = formEL.getElementsByTagName("input");
    const formData =  {};
    for (let i = 0; i < inputELs.length; i++) {
        formData[inputELs[i].name] = inputELs[i].value;
    }
    axios.post("http://localhost:8080/register",formData, {
        Headers:"Content-Type:application/json"
    }).then(res => {
        console.log(res);
        if(res.data.code === 200)
        {
            /*alert("注册成功");
            window.location.href = "/login";*/
            window.location.href = "/email-verification.html";
        }
    }).catch(err => {
        console.log(err);
        alert("注册失败");
    })
});