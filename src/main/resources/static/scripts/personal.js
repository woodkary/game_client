let username = null;
preLoad();
handleDataPersonal(1);
handleDataPersonal(2);
handleDataAll();
handleDataMonth();



function handleDataPersonal(type) {
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    let url = 'http://localhost:8080/ranks/othersReport/' + type + '?username=' + encodeURIComponent(username);
    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        let response = xhr.responseText;
        let jsonResult = JSON.parse(response);
        if (xhr.status === 200) {
            let data = jsonResult.data;
            if (type === 1)
                setInputDataPersonalSin(data);
            if (type === 2)
                setInputDataPersonalBrawl(data);
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

function setInputDataRecent(data) {
    // 获取表格元素
    var table = document.getElementById("recentTable");

    // 清空表格内容
    while (table.firstChild) {
        table.firstChild.remove();
    }

    // 遍历记录，并将数据填充到表格中
    for (var i = 0; i < 8; i++) {
        // 创建新的表格行
        var row = document.createElement("tr");

        // 获取当前记录的索引
        var index = i % data.length;

        // 获取当前记录对象
        var record = data[index];

        // 创建并填充单元格
        var resultCell = document.createElement("td");
        if (record.kills > record.deaths) {
            resultCell.textContent = "胜利";
        } else {
            resultCell.textContent = "失败";
        }
        row.appendChild(resultCell);
        resultCell.setAttribute("class", "td3"); // 添加 class 属性
        resultCell.setAttribute("id", "result" + i); // 添加 id 属性
        resultCell.setAttribute("width", "20px"); // 添加 width 属性
        row.appendChild(resultCell);

        var typeCell = document.createElement("td");
        typeCell.textContent = record.type;
        typeCell.setAttribute("class", "td3"); // 添加 class 属性
        typeCell.setAttribute("id", "type" + i); // 添加 id 属性
        typeCell.setAttribute("width", "20px"); // 添加 width 属性
        row.appendChild(typeCell);

        var timeCell = document.createElement("td");
        timeCell.textContent = record.gameTime;
        timeCell.setAttribute("class", "td3"); // 添加 class 属性
        timeCell.setAttribute("id", "gameTime" + i); // 添加 id 属性
        timeCell.setAttribute("width", "100px"); // 添加 width 属性
        row.appendChild(timeCell);

        // 将行添加到表格中
        table.appendChild(row);
    }
}

function setInputDataPersonalSin(data) {
    document.getElementById("gameNumsSin").textContent = data.gameNums;
    document.getElementById("winSin").textContent = data.win;
    document.getElementById("loseSin").textContent = data.lose;
    document.getElementById("winRateSin").textContent = data.winRate;
    document.getElementById("levelSin").textContent = data.level;
}
function setInputDataPersonalBrawl(data) {
    document.getElementById("gameNumsBrawl").textContent = data.gameNums;
    document.getElementById("winBrawl").textContent = data.win;
    document.getElementById("loseBrawl").textContent = data.lose;
    document.getElementById("winRateBrawl").textContent = data.winRate;
    document.getElementById("levelBrawl").textContent = data.level;
}
function setInputDataAll(data) {
    document.getElementById("kdaAll").textContent = data.kda;
    document.getElementById("winRateAll").textContent = data.winRate;
    document.getElementById("totalKillsAll").textContent = data.totalKills;
    document.getElementById("gameNumsAll").textContent = data.gameNums;
}
function setInputDataMonth(data) {
    document.getElementById("kdaMonth").textContent = data.kda;
    document.getElementById("winRateMonth").textContent = data.winRate;
    document.getElementById("totalKillsMonth").textContent = data.totalKills;
    document.getElementById("gameNumsMonth").textContent = data.gameNums;
}
function preLoad() {
    let query = window.location.search;
    let params = new URLSearchParams(query);
    username = params.get("username");
    console.log(username);
}
function redircetToRecord() {
    window.location.href = "../pages/record.html?username=" + encodeURIComponent(username);
}