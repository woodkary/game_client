function setInputData(data) {
    document.getElementById("gameNums").textContent = data.gameNums;
    document.getElementById("win").textContent = data.win;
    document.getElementById("lose").textContent = data.lose;
    document.getElementById("winRate").textContent = data.winRate;
    document.getElementById("level").textContent = data.level;
}


function handleData(type)
{
    fetch(`http://localhost:8080/ranks/myReport/${type}`)
        .then(response => response.json())
        .then(data => setInputData(data))
        .catch(error=>{
            console.error(error);
});
}
handleData(1);
handleData(2);

function