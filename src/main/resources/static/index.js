let username = null;
window.onload = function () {
  let query = window.location.search;
  let params = new URLSearchParams(query);
  username = params.get("username");
}



document.getElementById("username").addEventListener("keydown",
  function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      search();
    }
  }
);

function toMyRecord(event) {
  event.preventDefault();
  window.location.href = "pages/personal.html?username=" + encodeURIComponent(username);
}

function search() {
  const input = document.getElementById("username");
  const validityState = input.validity;
  let username = document.getElementById("username").value;
  if (validityState.valueMissing) {
    input.setCustomValidity("用户名不能为空");
  }
  let usernameExist = false;
  fetch(`http://localhost:8080/ranks/checkIfExist?username=${encodeURIComponent(username)}`)
  .then(response => {
    console.log(response.text);
    console.log(response.status);
    if (response.status === 200) {
      //window.location.href = `pages/personal.html?username=${encodeURIComponent(username)}`;
    } else {
      input.setCustomValidity("用户名不存在");
    }
  })
  .catch(error => {
    console.error("Error:", error);
  });
}


