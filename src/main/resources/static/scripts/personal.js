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