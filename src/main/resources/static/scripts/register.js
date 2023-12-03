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
      headers: { 'content-type': 'application/json' }
    })
    .then(response => {
      window.location.href="email-verification.html";
      console.log(response);
    })
    .catch(error => {
      console.error(error);
    });
});

