function search() {
  const username = document.getElementById("username").value;
  console.log(username);
  if (username === 'danqingyifan') {
    window.location.href = "/pages/personal.html";
  } else {
    var url = 'http://localhost:8080/ranks/getRanks/1?username=' + username;
    fetch(url)
      .then(response => response.json())
      .then(data => {
        if(response.status === 200){ 
        console.log(data);
        window.location.href = "/pages/personal/search?username=" + + "&page=1";
        }
      })
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