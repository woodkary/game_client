let username=null;
window.onload=function (){
    let query=window.location.search;
    let params=new URLSearchParams(query);
    username=params.get("username");

}

function toMyRecord(event){
    event.preventDefault();
    window.location.href="pages/personal.html?username="+encodeURIComponent(username);
}

function search() {
  let username = document.getElementById("username").value;
  window.location.href = "pages/personal.html?username="+encodeURIComponent(username);
}

document.getElementById("username").addEventListener("keydown",
  function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      search();
    }
  }
);