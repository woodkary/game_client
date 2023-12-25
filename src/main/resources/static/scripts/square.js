window.onload = function () {
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