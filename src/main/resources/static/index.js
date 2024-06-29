let username = null;
window.onload = function () {
  let query = window.location.search;
  let params = new URLSearchParams(query);
  username = params.get("username");
  // 在你需要获取存储的用户名时，从sessionStorage中获取它
  sessionStorage.setItem('myUsername', username);
}


document.getElementById("username").addEventListener("keydown",
  function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      search();
    }
  }
);

function search() {
  const input = document.getElementById("username");
  const validityState = input.validity;
  let username = document.getElementById("username").value;
  if (validityState.valueMissing) {
    input.setCustomValidity("用户名不能为空");
    input.reportValidity();
    return;
  }

  fetch(`http://localhost:8080/ranks/checkIfExist?username=${encodeURIComponent(username)}`)
    .then(response => {
      console.log(response.status);
      if (response.status === 200) {
        window.location.href = `pages/personal.html?username=${encodeURIComponent(username)}`;
      } else {
        input.setCustomValidity("用户名不存在");
        input.reportValidity();
      }
    })
    .catch(error => {
      console.error("Error:", error);
    });
}

function redirectToIndex(event) {
  event.preventDefault();
  window.location.href = "index.html?username=" + encodeURIComponent(username);
}

function redirectToMyRecord(event) {
  event.preventDefault();
  window.location.href = "pages/personal.html?username=" + encodeURIComponent(username);
}

function redirectToSquare(event) {
  event.preventDefault();
  window.location.href = "pages/square.html?username=" + encodeURIComponent(username);
}
