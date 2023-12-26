let today = new Date();
let year = today.getFullYear();
let month = today.getMonth() + 1; // getMonth returns a zero-based value (where 0 indicates the first month)
let day = today.getDate();

let dateString = `${year}/${month < 10 ? '0' + month : month}/${day < 10 ? '0' + day : day}`;

// setInterval(function() {
//     location.reload();
// }, 20000);

window.onload = function () {
    getGamesByDate(dateString);
    soloRank();
    brawlRank();
    scoreRank();
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

function redirectToSquare(event) {
    event.preventDefault();
    let myUsername = sessionStorage.getItem('myUsername');
    console.log(myUsername);
    window.location.href = "../pages/square.html?username=" + encodeURIComponent(myUsername);
}

function createMatchCard() {
    const liveUpdate = document.getElementById("live_game_updates");
    liveUpdate.innerHTML = "";
    liveUpdate.innerHTML = `
    <div class="match-card">
        <div class="player" id="player1">
            <img src="../images/portrait_3.jpg" alt="Player 1">
            <span>玩家1名字</span>
        </div>
        <div class="score">3 : 1</div>
        <div class="player" id="player2">
            <img src="../images/portrait_5.jpg" alt="Player 2">
            <span>玩家2名字</span>
        </div>
    </div>`;
}

function getGamesByDate(dateString) {
    const liveUpdate = document.getElementById("live_game_updates");
    const url = `http://localhost:8080/ranks/getGamesByDate?date=${encodeURIComponent(dateString)}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data.data);
            // Replace the HTML content with the received data
            for (let i = 0; i < data.data.length; i++) {
                console.log(data.data[i]);
                let gameId = data.data[i].gameId;
                let type = data.data[i].type;
                fetch(`http://localhost:8080/ranks/getGamesByGameId?gameId=${gameId}`)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data.data);
                    })
                    .catch(error => console.log(error));
                if (type == 1) {
                    liveUpdate.innerHTML = "";
                    liveUpdate.innerHTML = `
                    <div class="match-card">
                        <div class="player" id="player1">
                            <img src="../images/portrait_3.jpg" alt="Player 1">
                            <span>玩家1名字</span>
                        </div>
                        <div class="score">3 : 1</div>
                        <div class="player" id="player2">
                            <img src="../images/portrait_5.jpg" alt="Player 2">
                            <span>玩家2名字</span>
                        </div>
                    </div>`;
                } else {

                }
                liveUpdate.appendChild(matchCard);
            }
        })
        .catch(error => console.log(error));
}

function createMatchCard(game) {
    console.log(game);


}

function allRanks() {
    soloRank();
    brawlRank();
    scoreRank();
}

function soloRank() {
    fetch(`http://localhost:8080/ranks/get1v1RankingOrder`)
        .then(response => {
            console.log(response);
            console.log(response.textContent);
            return response.json();
        }).then(data => {
            console.log(data.data);
            let rank = document.getElementById("ranking_list_kills");
            console.log(rank);
            rank.innerHTML = ``;
            rank.innerHTML = `
                <tr class="header">
                <th>名次 <i class="fas fa-sort-numeric-up"></i></th>
                <th colspan="2">玩家 <i class="fas fa-user"></i></th>
                <th>排位分 <i class="fas fa-coins"></i></th>
                </tr>`;
            for (let i = 0; i < 7; i++) {
                let rankItem = document.createElement("tr");
                rankItem.innerHTML = `
                <tr class="row">
                    <td class="ranking">${i + 1}</td>
                    <td class="head_photo"><img src="../images/portrait_${data.data[i].portrait}.jpg" alt="头像1" width="50" height="50">
                    </td>
                    <td class="username">${data.data[i].username}</td>
                    <td class="points">${data.data[i].scoreTotal1v1}</td>
                </tr>`;
                rank.appendChild(rankItem);
            }
        })
        .catch(error => console.log(error));
}
function brawlRank() {
    fetch(`http://localhost:8080/ranks/getBrawlRankingOrder`)
        .then(response => {
            console.log(response);
            console.log(response.textContent);
            return response.json();
        }).then(data => {
            console.log(data.data);
            let rank = document.getElementById("ranking_list_points");
            console.log(rank);
            rank.innerHTML = ``;
            rank.innerHTML = `
                <tr class="header">
                <th>名次 <i class="fas fa-sort-numeric-up"></i></th>
                <th colspan="2">玩家 <i class="fas fa-user"></i></th>
                <th>排位分 <i class="fas fa-coins"></i></th>
                </tr>`;
            for (let i = 0; i < 7; i++) {
                let rankItem = document.createElement("tr");
                rankItem.innerHTML = `
                <tr class="row">
                    <td class="ranking">${i + 1}</td>
                    <td class="head_photo"><img src="../images/portrait_${data.data[i].portrait}.jpg" alt="头像1" width="50" height="50">
                    </td>
                    <td class="username">${data.data[i].username}</td>
                    <td class="points">${data.data[i].scoreTotalBrawl}</td>
                </tr>`;
                rank.appendChild(rankItem);
            }
        })
        .catch(error => console.log(error));
}
function scoreRank() {
    fetch(`http://localhost:8080/ranks/getTotalScoreRankingOrder`)
        .then(response => {
            console.log(response);
            console.log(response.textContent);
            return response.json();
        }).then(data => {
            console.log(data.data);
            let rank = document.getElementById("ranking_list_damage");
            console.log(rank);
            rank.innerHTML = ``;
            rank.innerHTML = `
                <tr class="header">
                <th>名次 <i class="fas fa-sort-numeric-up"></i></th>
                <th colspan="2">玩家 <i class="fas fa-user"></i></th>
                <th>排位分 <i class="fas fa-coins"></i></th>
                </tr>`;
            for (let i = 0; i < 7; i++) {
                let rankItem = document.createElement("tr");
                rankItem.innerHTML = `
                <tr class="row">
                    <td class="ranking">${i + 1}</td>
                    <td class="head_photo"><img src="../images/portrait_${data.data[i].portrait}.jpg" alt="头像1" width="50" height="50">
                    </td>
                    <td class="username">${data.data[i].username}</td>
                    <td class="points">${data.data[i].scoreTotal1v1 + data.data[i].scoreTotalBrawl}</td>
                </tr>`;
                rank.appendChild(rankItem);
            }
        })
        .catch(error => console.log(error));
}