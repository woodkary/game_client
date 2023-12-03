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