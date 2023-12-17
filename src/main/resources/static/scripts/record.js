let username = "";
let currentPage = 1; // 当前页数  
let totalPages = 8; // 总页数，根据实际情况进行调整  

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

function redirectToMyPersonal(event) {
  event.preventDefault();
  let myUsername = sessionStorage.getItem('myUsername');
  console.log(myUsername);
  window.location.href = "../pages/personal.html?username=" + encodeURIComponent(myUsername);
}

function redirectToPersonal(event) {
  event.preventDefault();
  window.location.href = "../pages/personal.html?username=" + encodeURIComponent(username);
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
      const ul = document.getElementById("Rectangle1");
      ul.innerHTML = ""; // Clear the existing list
      totalPages = Math.ceil(data.data.length / 8);
      for (let i = 8 * (pageNum - 1); i < 8 * pageNum; i++) {
        const record = data.data[i];
        console.log(typeof record);
        const li = document.createElement("div");
        li.innerHTML = `
        <button class="record-button">
        <span class="${record.result === '胜利' ? 'result_win' : 'result_lose'}">${record.result}</span>
        <span class="type">(${record.type})</span>
        <img class="kill" src="../images/strength.png">
        <span class="kills">${record.kills}</span>
        <img class="death" src="../images/wither.png">
        <span class="deaths">${record.deaths}</span>
        <span class="gametime">${record.gametime}</span>
      </button>
        `
        let gameId = record.gameId;
        console.log(gameId);
        li.addEventListener('click', updateDetailedInfo(gameId));
        ul.appendChild(li);
      }
    })
    .catch(error => {
      console.error("Error fetching ranks info:", error);
    });
}

function updateDetailedInfo(gameid) {
  fetch(`http://localhost:8080/ranks/getGamesByGameId?gameId=${gameid}`)
    .then(response => {
      console.log(response);
      console.log(response.textContent);
      return response.json();
    })
    .then(data => {
      console.log(data.data);
      const ul = document.getElementById("detailedRecordList");
      //type
      document.getElementById("type-value").textContent = data.data[0].type;
      //duration
      let durationInMs = data.data[0].duration; // 从后端获取的毫秒数
      let minutes = Math.floor(durationInMs / 60000); // 转换为分钟
      let seconds = Math.floor((durationInMs / 1000) % 60); // 转换为秒
      if (seconds === 0) {
        document.getElementById("duration-value").textContent = `${minutes}分钟`; // 设置文本内容
      }
      else {
        document.getElementById("duration-value").textContent = `${minutes}分钟 ${seconds}秒`; // 设置文本内容
      }
      //date
      const date = new Date(data.data[0].gameTime);
      const year = date.getFullYear();
      const month = date.getMonth() + 1; // getMonth returns a zero-based month, so add 1
      const day = date.getDate();

      const hour = date.getHours();
      const minute = date.getMinutes();
      const second = date.getSeconds();

      const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;
      const formattedTime = `${hour < 10 ? '0' + hour : hour}:${minute < 10 ? '0' + minute : minute}:${second < 10 ? '0' + second : second}`;
      document.getElementById("date-value").textContent = formattedDate + formattedTime;


      for (let i = 0; i < data.data.length; i++) {
        const record = data.data[i];
        const li = document.createElement("li");
        if (record.type === "大乱斗") {


        }


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


