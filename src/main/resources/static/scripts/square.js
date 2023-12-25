let today = new Date();
let year = today.getFullYear();
let month = today.getMonth() + 1; // getMonth returns a zero-based value (where 0 indicates the first month)
let day = today.getDate();

let dateString = `${year}/${month < 10 ? '0' + month : month}/${day < 10 ? '0' + day : day}`;


window.onload = function () {
    getGamesByDate(dateString);
    createMatchCard();
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
    window.location.href = "../pages/square.html?username=" + encodeURIComponent(username);
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
    const url = `http://localhost:8080/ranks/getGamesByDate?date=${encodeURIComponent(dateString)}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            console.log(data.data);
            // Replace the HTML content with the received data
            for (let i = 0; i < data.data.length; i++) {
                const matchCard = createMatchCard(data.data[i]);
                liveUpdate.appendChild(matchCard);
            }
        })
        .catch(error => console.log(error));
}

function createMatchCard(game) {
    console.log(game);
    console.log(game.textContent);
    const liveUpdate = document.getElementById("live_game_updates");

    let type = game.type;
    if (type === '大乱斗') {
        
    } else {
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
            for (let i = 0; i < 7; i++) {
                
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

        })
        .catch(error => console.log(error));
}