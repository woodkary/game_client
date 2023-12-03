function setInputDataPersonal(data) {
    document.getElementById("gameNums").textContent = data.gameNums;
    document.getElementById("win").textContent = data.win;
    document.getElementById("lose").textContent = data.lose;
    document.getElementById("winRate").textContent = data.winRate;
    document.getElementById("level").textContent = data.level;
}


function handleDataPersonal(type)
{
    fetch(`http://localhost:8080/ranks/myReport/${type}`)
        .then(response => response.json())
        .then(data => setInputDataPersonal(data))
        .catch(error=>{
            console.error(error);
});
}
handleDataPersonal(1);
handleDataPersonal(2);

function setInputDataAll(data) {
    document.getElementById("kda").textContent = data.kda;
    document.getElementById("winRate").textContent = data.winRate;
    document.getElementById("totalKills").textContent = data.totalKills;
    document.getElementById("gameNums").textContent = data.gameNums;
}


function handleDataAll()
{
    fetch('http://localhost:8080/ranks/myAllRecords')
        .then(response => response.json())
        .then(data => setInputDataAll(data))
        .catch(error=>{
            console.error(error);
        });
}
handleDataAll();
function setInputDataMonth(data) {
    document.getElementById("kda").textContent = data.kda;
    document.getElementById("winRate").textContent = data.winRate;
    document.getElementById("totalKills").textContent = data.totalKills;
    document.getElementById("gameNums").textContent = data.gameNums;
}


function handleDataMonth()
{
    fetch('http://localhost:8080/ranks/myMonthRecords')
        .then(response => response.json())
        .then(data => setInputDataMonth(data))
        .catch(error=>{
            console.error(error);
        });
}
handleDataMonth();

function getRanks(username, page) {
    // 构建URL
    var url = "/ranks/getRanks/" + page + "?username=" + encodeURIComponent(username)+"&page=" + encodeURIComponent(page) ;
    // 发送GET请求
    fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            // 调用populateTable函数以填充表格
            setInputDataRecent(data);
        })
        .catch(function(error) {
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
        resultCell.setAttribute("id", "result"+i); // 添加 id 属性
        resultCell.setAttribute("width", "20px"); // 添加 width 属性
        row.appendChild(resultCell);

        var typeCell = document.createElement("td");
        typeCell.textContent = record.type;
        typeCell.setAttribute("class", "td3"); // 添加 class 属性
        typeCell.setAttribute("id", "type"+i); // 添加 id 属性
        typeCell.setAttribute("width", "20px"); // 添加 width 属性
        row.appendChild(typeCell);

        var timeCell = document.createElement("td");
        timeCell.textContent = record.gameTime;
        timeCell.setAttribute("class", "td3"); // 添加 class 属性
        timeCell.setAttribute("id", "gameTime"+i); // 添加 id 属性
        timeCell.setAttribute("width", "100px"); // 添加 width 属性
        row.appendChild(timeCell);

        // 将行添加到表格中
        table.appendChild(row);
    }
}

// 调用函数，传递username和page
// 获取当前页面的查询字符串部分
var search = window.location.search;

// 使用URLSearchParams来解析查询字符串
var params = new URLSearchParams(search);

// 从查询字符串中获取用户名
var username = params.get('username');
var page = 1;
getRanks(username, page);
