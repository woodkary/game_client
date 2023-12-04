let username=null;
window.onload=function (){
    let query=window.location.search;
    let params=new URLSearchParams(query);
    username=params.get("username");
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

function handleDataPersonal(type)
{
    let xhr = new XMLHttpRequest();
    xhr.withCredentials=true;

    let url='http://localhost:8080/ranks/myReport/'+type;
    if(username!=null){
        url='http://localhost:8080/ranks/ranks/othersReport/'+type+'?username='+encodeURIComponent(username);
    }
    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        let response = xhr.responseText;
        let jsonResult=JSON.parse(response);
        if (xhr.status === 200) {
            let data=jsonResult.data;
            if(type===1)
                setInputDataPersonalSin(data);
            if(type===2)
                setInputDataPersonalBrawl(data);
        } else {
            console.log(jsonResult.message);
        }
    };
    xhr.send();
}
handleDataPersonal(1);
handleDataPersonal(2);

function setInputDataAll(data) {
    document.getElementById("kdaAll").textContent = data.kda;
    document.getElementById("winRateAll").textContent = data.winRate;
    document.getElementById("totalKillsAll").textContent = data.totalKills;
    document.getElementById("gameNumsAll").textContent = data.gameNums;
}


function handleDataAll()
{
    let xhr = new XMLHttpRequest();
    xhr.withCredentials=true;

    let url='http://localhost:8080/ranks/myAllRecords';
    if(username!=null){
        url='http://localhost:8080/ranks/othersAllRecords'+'?username='+encodeURIComponent(username);
    }
    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        let response = xhr.responseText;
        let jsonResult=JSON.parse(response);
        if (xhr.status === 200) {
            let data=jsonResult.data;
            setInputDataAll(data);
        } else {
            console.log(err);
        }
    };
    xhr.send();
}
handleDataAll();
function setInputDataMonth(data) {
    document.getElementById("kdaMonth").textContent = data.kda;
    document.getElementById("winRateMonth").textContent = data.winRate;
    document.getElementById("totalKillsMonth").textContent = data.totalKills;
    document.getElementById("gameNumsMonth").textContent = data.gameNums;
}


function handleDataMonth()
{
    let xhr = new XMLHttpRequest();
    xhr.withCredentials=true;
    let url='http://localhost:8080/ranks/myMonthRecords';
    if(username!=null){
        url='http://localhost:8080/ranks/othersMonthRecords'+'?username='+encodeURIComponent(username);
    }
    // 配置请求
    xhr.open('GET', url); // 根据您的实际需求指定适当的URL
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        let response = xhr.responseText;
        let jsonResult=JSON.parse(response);
        if (xhr.status === 200) {
            let data=jsonResult.data;
            setInputDataMonth(data);
        } else {
            console.log(err);
        }
    };
    xhr.send();
}
handleDataMonth();


