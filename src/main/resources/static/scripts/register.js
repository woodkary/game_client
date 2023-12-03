
// formEL.addEventListener("submit", function (event) {
//     event.preventDefault();
//     const inputELs = formEL.getElementsByTagName("input");
//     const formData = {
//         username: "JohnDoe",
//         password: "password123",
//         retypePassword: "password123",
//         email: "john.doe@example.com"
//       };
//     // for (let i = 0; i < inputELs.length; i++) {
//     //     formData[inputELs[i].name] = inputELs[i].value;
//     // }
//     axios({
//       method: 'post',
//       url: 'http://localhost:8080/register',
//       data: {
//         username: "JohnDoe",
//         password: "password123",
//         retypePassword: "password123",
//         email: "john.doe@example.com"
//       },
//       headers: { 'content-type': 'application/x-www-form-urlencoded' }
//     })
//     .then(response => {
//       console.log(response);
//     })
//     .catch(error => {
//       console.error(error);
//     });
// });


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

function encodeFormData(data) {
  return Object.keys(data)
    .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key]))
    .join('&');
}
formEL.addEventListener("submit", function (event) {
  axios.post('http://localhost:8080/register', encodeFormData({
    username: 'dan',
    password: 'password123',
    retypePassword: 'password123',
    email: 'wocaonima@qq.com'
  }), {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
    .then(function (response) {
      alert(response.data);
    })
    .catch(function (error) {
      console.log(error);
    });
});

