let username = null;
let soloRank = 0;
let brawlRank = 0;
preLoad();

//soloRank 和 brawlRank 在上面的函数里面处理了
window.onload = function () {
    let ID = document.getElementById("ID");
    ID.textContent = username;
    handleDataPersonal(1);
    handleDataPersonal(2);
    handleDataAll();
}

function preLoad() {
    let query = window.location.search;
    let params = new URLSearchParams(query);
    username = params.get("username");
    getRanksInfo();
}

function handleRankScore() {
    let rank = document.getElementById("rank"); // 获取元素
    rank.textContent = "排位分:" + (((soloRank + brawlRank) < 0) ? 0 : (soloRank + brawlRank));
    console.log(rank);
}

function handleDataPersonal(type) {
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    let url = 'http://localhost:8080/ranks/othersReport/' + type + '?username=' + encodeURIComponent(username);
    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        let response = xhr.responseText;
        console.log(response);
        let jsonResult = JSON.parse(response);
        console.log(jsonResult);
        console.log(jsonResult.responseText);
        let data = jsonResult.data;
        console.log(data);
        console.log(data.portrait);
        if (xhr.status === 200) {
            if (type === 1) {
                setInputDataPersonalSin(data);
                soloRank = data.score;
            } if (type === 2) {
                setInputDataPersonalBrawl(data);
                brawlRank = data.score;
            }
            let portrait = document.getElementById("portrait");
            portrait.src = "../images/portrait_" + data.portrait + ".jpg";
            handleRankScore();
        } else {
            console.log(jsonResult.message);
        }

    };
    xhr.send();
}


function handleDataAll() {
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    let url = 'http://localhost:8080/ranks/othersAllRecords' + '?username=' + encodeURIComponent(username);

    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        let response = xhr.responseText;
        let jsonResult = JSON.parse(response);
        if (xhr.status === 200) {
            let data = jsonResult.data;
            setInputDataAll(data);
        } else {
            console.log(err);
        }
    };
    xhr.send();
}


function handleDataMonth() {
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    let url = 'http://localhost:8080/ranks/othersMonthRecords' + '?username=' + encodeURIComponent(username);

    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        let response = xhr.responseText;
        let jsonResult = JSON.parse(response);
        if (xhr.status === 200) {
            let data = jsonResult.data;
            setInputDataMonth(data);
        } else {
            console.log(err);
        }
    };
    xhr.send();
}

function getRanks(username, page) {
    // 构建URL
    var url = "/ranks/getRanks" + "?username=" + encodeURIComponent(username);
    // 发送GET请求
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            // 调用populateTable函数以填充表格
            setInputDataRecent(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function setInputDataPersonalSin(data) {
    document.getElementById("gameNumsSin").textContent = data.gameNums;
    document.getElementById("winSin").textContent = data.win;
    document.getElementById("loseSin").textContent = data.lose;
    document.getElementById("winRateSin").textContent = toPercentageValue(data.winRate);
    document.getElementById("levelSin").textContent = data.level;
    document.getElementById("winRate_solo").textContent = toPercentageValue(data.winRate);
    document.getElementById("gameNum_solo").textContent = data.gameNums +"场";
}
function setInputDataPersonalBrawl(data) {
    document.getElementById("gameNumsBrawl").textContent = data.gameNums;
    document.getElementById("winBrawl").textContent = data.win;
    document.getElementById("loseBrawl").textContent = data.lose;
    document.getElementById("winRateBrawl").textContent = toPercentageValue(data.winRate);
    document.getElementById("levelBrawl").textContent = data.level;
    document.getElementById("winRate_melee").textContent = toPercentageValue(data.winRate);
    document.getElementById("gameNum_melee").textContent = data.gameNums + "场";
}
function setInputDataAll(data) {
    document.getElementById("value_kda").textContent = (data.kda).toFixed(2);
    document.getElementById("total_kills").textContent = data.totalKills;
    document.getElementById("gameNumsAll").textContent = data.gameNums;
}



function toPercentageValue(value) {
    let percentageValue = value * 100;
    return percentageValue.toFixed(2) + '%';
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


function getRanksInfo() {
    console.log(username);
    fetch(`http://localhost:8080/ranks/getRanks?username=${username}`)
        .then(response => {
            console.log(response);
            console.log(response.textContent);
            return response.json();
        })
        .then(data => {
            console.log(data.data);
            const ul = document.getElementById("recordTable");
            ul.innerHTML = ""; // Clear the existing list
            let count = data.data.length > 8 ? 8 : data.data.length;
            console.log(count);
            for (let i = 0; i < count; i++) {
                const record = data.data[i];
                console.log(typeof record);
                const li = document.createElement("li");

                let win = record.mvp === true ? "胜利" : "失败";


                // Get the year, month, and day
                const date = new Date(record.gameTime);
                const year = date.getFullYear();
                const month = date.getMonth() + 1; // getMonth returns a zero-based month, so add 1
                const day = date.getDate();

                const formattedDate = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

                const result = record.mvp ? "result_win" : "result_lose";
                const type = record.type === "大乱斗" ? "死斗" : "单挑";

                li.innerHTML = `
                <div class="row">
                    <img class="head_photo" src="../images/${record.type==="大乱斗"?"Creepe.jpg":"Steve.png"}"/>
                    <span class="info">
                        <span class="type">${type}</span>
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
                    <span class=${result}>
                        <p>${win}</p>
                    </span>
                </div>
            `;
                ul.appendChild(li);
            }

        })
        .catch(error => {
            console.error("Error fetching ranks info:", error);
            console.log(error.name);
            console.log(error.message);
            console.log(error.stack);
        });
}


var modal = document.getElementById("myModal");
var btn = document.getElementById("portrait");
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function () {
    if (username === sessionStorage.getItem('myUsername')) {
        modal.style.display = "block";
    }
}

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Function to select an avatar
function selectAvatar(portrait) {
    console.log('Path:', portrait); // 输出path的值
    // Set the new avatar
    var portraitElement = document.getElementById("portrait");
    console.log('Portrait element:', portraitElement); // 输出portrait元素
    portraitElement.src = '../images/portrait_' + portrait + '.jpg';
    // Close the modal
    fetch('http://localhost:8080/ranks/portrait', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, portrait }),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => console.error('Error:', error));


    modal.style.display = "none";
}

async function runFunctions() {
    await getRanksInfo();

}

function redirectToSquare(event) {
    event.preventDefault();
    window.location.href = "../pages/square.html?username=" + encodeURIComponent(username);
}
