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

var currentPage = 1; // 当前页数  
var totalPages = 10; // 总页数，根据实际情况进行调整  

function updatePageNum() {  
  var pageNum = document.getElementById('pageNum');  
  pageNum.textContent = currentPage;  
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
updatePageNum();