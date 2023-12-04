## get请求格式
```javascript
let xhr=new XMLHttpRequest();
xhr.open("GET", "http://localhost:8080/请求?param1="+value1+"&param2="+value2);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.withCredentials=true;

xhr.onreadystatechange = function () {
    let response = JSON.parse(xhr.responseText);
    if (xhr.readyState===4&&xhr.status === 200) {
        //成功，Handle the response here
    }else{
        console.log("错误"+response);
    }
};
xhr.send();
```
## post请求格式
```javascript
let xhr=new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/请求");
  xhr.setRequestHeader("Content-Type", "application/json");
xhr.withCredentials=true;

  xhr.onreadystatechange = function () {
    let response = JSON.parse(xhr.responseText);
    if (xhr.readyState===4&&xhr.status === 200) {
      //成功，Handle the response here
    }else{
      console.log("错误"+response);
    }
  };
  let jsonValue=JSON.parse("任意值");
  xhr.send(jsonValue);
```
### 发送请求前，请加入如下代码保证跨域请求的可行
```javascript
xhr.withCredentials=true;
```
或者
```javascript
withCredentials: true
```