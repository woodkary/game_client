let username = "";
preLoad();
window.onload = function () {
  initPageNum();
}

function preLoad() {
  let query = window.location.search;
  let params = new URLSearchParams(query);
  username = params.get("username");
  console.log(username);
}

let currentPage = 1; // 当前页数  
let totalPages = 8; // 总页数，根据实际情况进行调整  

function initPageNum() {
  let pageNum = document.getElementById('pageNum');
  pageNum.textContent = '1';
  getRanksInfo(1);
}
function updatePageNum() {
  var pageNum = document.getElementById('pageNum');
  pageNum.textContent = currentPage;
  getRanksInfo(currentPage);
}

function prevPage() {
  if (currentPage > 1) {
    currentPage--;
    updatePageNum();
  }
}

function nextPage() {
  if (currentPage < totalPages) {
    currentPage++;
    updatePageNum();
  }
}

function redirectToIndex(event) {
  event.preventDefault();
  let myUsername = sessionStorage.getItem('myUsername');
  console.log(myUsername);
  window.location.href = "../index.html?username=" + encodeURIComponent(myUsername);
}

function redirectToPersonal(event) {
  event.preventDefault();
  let myUsername = sessionStorage.getItem('myUsername');
  console.log(myUsername);
  window.location.href = "../pages/personal.html?username=" + encodeURIComponent(myUsername);
}

function redirectToRecord(event) {
  event.preventDefault();
  window.location.href = "../pages/record.html?username=" + encodeURIComponent(username);
}

function getRanksInfo(pageNum) {
  console.log(username);
  fetch(`http://localhost:8080/ranks/getRanks?username=${username}`)
    .then(response => {
      return response.json();
    })
    .then(data => {
      console.log(data.data);
      const ul = document.getElementById("recordTable");
      ul.innerHTML = ""; // Clear the existing list
      
      for (let i = 8 * (pageNum - 1); i < 8 * pageNum; i++) {
        const record = data.data[i];
        console.log(typeof record);
        const li = document.createElement("li");

        li.innerHTML = `
        <div class="row">
            <img class="head_photo" src="../images/portrait_0.jpg" />
            <span class="info">
                <span class="type">1 VS 1</span>
                <span class="gametime">${formattedDate}</span>
            </span>
            <span class="kda">
                <img class="kill" src="../images/strength.png">
                <span class="kills">
                    <p>${record.kills}</p>
                </span>
                <img class="death" src="../images/wither.png">
                <span class="deaths">
                    <p>${record.deaths}</p>
                </span>
            </span>
            <span class="result_win">
                <p>${win}</p>
            </span>
        </div>
    `;


        ul.appendChild(li);
        
        li.addEventListener('click', updateDetailedInfo(record.gameid));

      }
    })
    .catch(error => {
      console.error("Error fetching ranks info:", error);
    });
}

function updateDetailedInfo(gameid){
  fetch(`http://localhost:8080/ranks/getGamesByGameId?gameId=${gameid}`)
    .then(response => {
      console.log(response);
      console.log(response.textContent);
      return response.json();
    })
    .then(data => {
      console.log(data);
      const ul = document.getElementById("detailedRecordList");

      for (let i = 0; i < data.length; i++) {
        const record = data[i];
        const li = document.createElement("li");
        li.textContent = `Player: ${record.playerName} - Kills: ${record.kills} - Deaths: ${record.deaths} - Assists: ${record.assists} - Game Time: ${record.gameTime}`;
        ul.appendChild(li);
      }
    })
    .catch(error => {
      console.error("Error fetching records:", error);
    });
}


function setInputDataPersonal(data) {
  document.getElementById("gameNums").textContent = data.gameNums;
  document.getElementById("win").textContent = data.win;
  document.getElementById("lose").textContent = data.lose;
  document.getElementById("winRate").textContent = data.winRate;
  document.getElementById("level").textContent = data.level;
}


function handleDataPersonal(type) {
  fetch(`http://localhost:8080/ranks/myReport/${type}`)
    .then(response => response.json())
    .then(data => setInputDataPersonal(data))
    .catch(error => {
      console.error(error);
    });
}


