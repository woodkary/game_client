

function search() {
  const username = document.getElementById("username").value;
  console.log(username);
  var search = window.location.search;
  var params = new URLSearchParams(search);
  var myUsername = params.get("username");
  document.getElementById("myUsername").setValue(myUsername);

  if (username == 'danqingyifan') {
    window.location.href = "pages/personal.html";
  } else {
    var url = 'http://localhost:8080/ranks/getRanks/1?username=' + encodeURIComponent(data.username);
    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log(data);
        window.location.href = "pages/personal/search?username=" + encodeURIComponent(data.username) + "&page=1";
        }
      )
      .catch(error => {
        console.error(error);
      });
  }
}

document.getElementById("username").addEventListener("keydown",
  function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      search();
    }
  }
);