

function search() {
  let username = document.getElementById("username").value;
  window.location.href = "pages/personal.html?username="+username;
}

document.getElementById("username").addEventListener("keydown",
  function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      search();
    }
  }
);