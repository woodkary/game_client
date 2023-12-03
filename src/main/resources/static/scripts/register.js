
function register(event,hrefString,doc) {
    event.preventDefault();
    let formEL=doc.getElementById("formEL");
    const inputELs = formEL.getElementsByTagName("input");
    let formData =  {};
    for (let i = 0; i < inputELs.length; i++) {
        formData[inputELs[i].name] = inputELs[i].value;
    }
    let xhr=new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/register", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        let response = JSON.parse(xhr.responseText);
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Handle the response here
            window.location.href=hrefString;
        }else{
            console.log("错误"+response);
        }
    };
    const jsonData = JSON.stringify(formData);
    xhr.send(jsonData);
}