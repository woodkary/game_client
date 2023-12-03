const formEL = document.getElementById("formEL");
console.log(formEL);
formEL.addEventListener("submit", function (event) {
    event.preventDefault();
    const inputELs = formEL.getElementsByTagName("input");
    const formData={};
    for (let i = 0; i < inputELs.length; i++) {
        formData[inputELs[i].name] = inputELs[i].value;
    }
    axios({
        method: 'post',
        url: 'http://localhost:8080/register',
        data:formData,
        headers: { 'content-type': 'application/json' },
        withCredentials: true
    })
    .then(response => {
      window.location.href="email-verification.html";
      console.log(response);
    })
    .catch(error => {
      console.error(error);
    });
});


// const formEL = document.getElementById("formEL");
// formEL.addEventListener("submit", function (event) {
// var http = new XMLHttpRequest();
// var url = 'http://localhost:8080/register';
// var params = "username='dan'&password='password123'&retypePassword='password123'&email='wocaonima@qq.com'";
// http.open('POST', url, true);

// //Send the proper header information along with the request
// http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

// http.onreadystatechange = function() {//Call a function when the state changes.
//     if(http.readyState == 4 && http.status == 200) {
//         alert(http.responseText);
//     }
// }
// http.send(params);
// });


