let username = "";
let currentPage = 1; // 当前页数  
let totalPages = 8; // 总页数，根据实际情况进行调整  
let initGameMode;

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

function redirectToSquare(event) {
  event.preventDefault();
  window.location.href = "../pages/square.html?username=" + encodeURIComponent(username);
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
      totalPages = Math.ceil(data.data.length / 7);
      for (let i = 7 * (pageNum - 1); i < 7 * pageNum; i++) {
        const record = data.data[i];
        // Get the year, month, and day
        const date = new Date(record.gameTime);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // getMonth returns a zero-based month, so add 1
        const day = date.getDate();

        const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;


        console.log(typeof record);
        const li = document.createElement("button");
        li.innerHTML = `
        <img class="head_photo" src="../images/${record.type === "大乱斗" ? "Creepe.jpg" : "Steve.png"}" />
        <sapn class="info">
            <span class="type">${record.type === "大乱斗" ? "死斗" : "单挑"}</span>
            <span class="gametime">${formattedDate}</span>
        </sapn>
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
        <span class=${record.mvp ? "result_win" : "result_lose"}>
            <p>${record.mvp ? "胜利" : "失败"}</p>
        </span>`
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

      //kda=(kill*1.0+assist*0.7)/(death!=0?death:1)

      const gameMode = data.data[0].type;
      console.log(gameMode);
      initGameMode = gameMode;
      let cssFiles = document.querySelectorAll('link[rel="stylesheet"]');
      let link = cssFiles[0];
      if (gameMode === "大乱斗") {
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

      let melee = document.getElementById("melee1");

      if (gameMode === "大乱斗") {
        melee.className = 'melee';
        melee.innerHTML = `<p>死斗</p>
        <table id = "meleeTable">
          <tr>
            <th>用户名</th>
            <th>击杀数</th>
            <th>死亡数</th>
            <th>KDA</th>
            <th>伤害</th>
            <th>承伤</th>
          </tr>
          </table>`; // Clear the existing list
        const table = document.getElementById("meleeTable");
        for (let i = 0; i < data.data.length; i++) {
          const record = data.data[i];
          console.log(record);
          console.log(record.textContent);
          const tr = document.createElement("tr");
          tr.innerHTML = `
          <td>${record.username}</td>
          <td>${record.kills}</td>
          <td>${record.deaths}</td>
          <td>${record.kda}</td>
          <td>${record.takeDamage.toFixed(0)}</td>
          <td>${record.takenDamage.toFixed(0)}</td>
          `;
          table.appendChild(tr);
        }
      } else {
        melee.innerHTML = "";
        melee.innerHTML = `
        <div class="Win">
        <p>WIN</p>
        <img class="photo" id="win_photo" src="../images/portrait_2.jpg" style="border: 2px solid blueviolet;">
        <div class="username" id="win_username" colspan="12"
          style="text-align: center; position: absolute; top:200px; left: 185px;">小帅</div>
        <table class="Win_table">
          <tr>
            <th class="kills_word" colspan="2">击杀数<i class="fas fa-star animate-flicker"></i></th>
            <th class="deaths_word" colspan="2">死亡数<i class="fas fa-skull-crossbones"></i></th>
            <th class="damage_word" colspan="3">伤害 <i class="fas fa-heartbeat"></i></th>
            <th class="taken_word" colspan="3">承伤<i class="fas fa-shield-alt"></i></th>
            <th class="kda_word" colspan="2">KDA<i class="fas fa-chart-line"></i></th>
          </tr>
          <tr>
            <td class="kills_value" id="win_kills" colspan="2">用户名</td>
            <td class="deaths_value" id="win_deaths" colspan="2">用户名</td>
            <td class="damage_value" id="win_damage" colspan="3">用户名</td>
            <td class="taken_value" id="win_taken" colspan="3">用户名</td>
            <td class="kda_value" id="win_kda" colspan="2">用户名</td>
          </tr>
        </table>
      </div>
      <div class="Lose">
        <p>LOSE</p>
        <img class="photo" id="lose_photo" src="../images/portrait_3.jpg" style="border: 2px solid blueviolet;">
        <div class="username" id="lose_username" colspan="12"
          style="text-align: center; position: absolute; top:200px; left: 185px;">小美</div>
        <table class="Lose_table">
          <tr>
            <th class="kills_word" colspan="2">击杀数<i class="fas fa-star animate-flicker"></i></th>
            <th class="deaths_word" colspan="2">死亡数<i class="fas fa-skull-crossbones"></i></th>
            <th class="damage_word" colspan="3">伤害 <i class="fas fa-heartbeat"></i></th>
            <th class="taken_word" colspan="3">承伤<i class="fas fa-shield-alt"></i></th>
            <th class="kda_word" colspan="2">KDA<i class="fas fa-chart-line"></i></th>

          </tr>
          <tr>

            <td class="kills_value" id="lose_kills" colspan="2">用户名</td>
            <td class="deaths_value" id="lose_deaths" colspan="2">用户名</td>
            <td class="damage_value" id="lose_damage" colspan="3">用户名</td>
            <td class="taken_value" id="lose_taken" colspan="3">用户名</td>
            <td class="kda_value" id="lose_kda" colspan="2">用户名</td>
          </tr>
        </table>
      </div>`

        let winner = data.data[0].mvp ? data.data[0] : data.data[1];
        let loser = data.data[0].mvp ? data.data[1] : data.data[0];
        console.log(winner);
        // Get the elements
        const winUsername = document.getElementById('win_username');
        const winKills = document.getElementById('win_kills');
        const winDeaths = document.getElementById('win_deaths');
        const winKda = document.getElementById('win_kda');
        const winDamage = document.getElementById('win_damage');
        const winTaken = document.getElementById('win_taken');
        const winPhoto = document.getElementById('win_photo');

        // Update the text content

        winUsername.textContent = winner.username;
        winKills.textContent = winner.kills;
        winDeaths.textContent = winner.deaths;
        winKda.textContent = winner.kda;
        winDamage.textContent = Math.floor(winner.takeDamage);
        winTaken.textContent = Math.floor(winner.takenDamage);
        winPhoto.src = "../images/portrait_" + winner.portrait + '.jpg';
        console.log(winner.portrait);
        // Get the elements for the losing side
        const loseUsername = document.getElementById('lose_username');
        const loseKills = document.getElementById('lose_kills');
        const loseDeaths = document.getElementById('lose_deaths');
        const loseKda = document.getElementById('lose_kda');
        const loseDamage = document.getElementById('lose_damage');
        const loseTaken = document.getElementById('lose_taken');
        const losePhoto = document.getElementById('lose_photo');

        // Update the text content for the losing side
        loseUsername.textContent = loser.username;
        loseKills.textContent = loser.kills;
        loseDeaths.textContent = loser.deaths;
        loseKda.textContent = loser.kda;
        loseDamage.textContent = Math.floor(loser.takeDamage);
        loseTaken.textContent = Math.floor(loser.takenDamage);
        losePhoto.src = "../images/portrait_" + loser.portrait + '.jpg';
        //直接填数据上去
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


