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
  getRanksInfo(1);

}

function initPageNum() {
  let pageNum = document.getElementById('pageNum');
  pageNum.textContent = '1';
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
        // Get the year, month, and day
        const date = new Date(record.gameTime);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // getMonth returns a zero-based month, so add 1
        const day = date.getDate();

        const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

        console.log(typeof record);
        const li = document.createElement("div");
        li.innerHTML = `
        <div>
          <span class="${record.mvp === true ? 'result_win' : 'result_lose'}">${record.mvp === true ? '胜利' : '失败'}</span>
          <span class="type">(${record.type === '大乱斗' ? '死斗' : '单挑'})</span>
          <img class="kill" src="../images/strength.png">
          <span class="kills">${record.kills}</span>
          <img class="death" src="../images/wither.png">
          <span class="deaths">${record.deaths}</span>
          <span class="gametime">${formattedDate}</span>
        </div>`
        ;
        li.className = "record-button";
        let gameId = record.gameId;
        console.log(gameId);
        if (i === 8 * (pageNum - 1)) {
          updateDetailedInfo(gameId);
        }
        li.addEventListener('click', () => updateDetailedInfo(gameId));
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
      //type
      document.getElementById("type-value").textContent = data.data[0].type === "大乱斗" ? "死斗" : "单挑";
      //duration
      let durationInMs = data.data[0].duration; // 从后端获取的毫秒数
      let minutes = Math.floor(durationInMs / 60000); // 转换为分钟
      let seconds = Math.floor((durationInMs / 1000) % 60); // 转换为秒
      if (seconds === 0) {
        document.getElementById("duration-value").textContent = `${minutes}分钟`; // 设置文本内容
      } else if (minutes === 0) {
        document.getElementById("duration-value").textContent = `${seconds}秒`; // 设置文本内容
      } else {
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

      const formattedDate = `${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}  `;
      const formattedTime = `${hour < 10 ? '0' + hour : hour}:${minute < 10 ? '0' + minute : minute}`;
      document.getElementById("date-value").textContent = formattedDate + formattedTime;

      const gameMode = data.data[0].type;
      console.log(gameMode);
      if (gameMode === "大乱斗") {
        const link = document.querySelector('link[href="../styles/record.css"]');
        console.log(link);
        if (link) {
          console.log(link);
          link.href = "../styles/record2.css";
          console.log(link);
        }
      } else {
        const link = document.querySelector('link[href="../styles/record2.css"]');
        if (link) {
          console.log(link);
          link.href = "../styles/record.css";
          console.log(link);
        }
      }

      for (let i = 0; i < data.data.length; i++) {
        const record = data.data[i];
        console.log(record);
        console.log(record.textContent);
        const li = document.createElement("li");
        if (gameMode === "大乱斗") {
          const melee = document.getElementById("melee");
          li.innerHTML = `


          `;
          melee.appendChild(li);
        } else {

          //直接填数据上去
        }
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


