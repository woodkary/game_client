    .background{
      width: 100%;  
      height: 100vh;  
      background-image: url("background.png");  
      background-repeat: no-repeat;  
      background-size: cover;
      margin-top: 0px;  
      position: absolute;  
      opacity: 0.6;  
      z-index: -1;  
    }
    .navigation-bar {  
      background-color: #2F2E2D;  
      overflow: hidden;  
      position: fixed;  
      top: 0;  
      left: 0;  
      width: 100%;  
      height: 7vh;
      z-index: 100;
    }  
    /* logo样式 */ 
    .logo { 
      position: fixed;  
      top: 1.5vh;
      left: 1vw;
      width: 150px;
      height: 30px;
    }  
    /* 导航链接样式 */  
    .navigation-bar a {   
      display: inline-block;  
      margin-left: 3%;  /* 调整按键之间的左侧距离 */  
      padding: 14px;  /* 调整按键的内边距 */  
      color: #f2f2f2;  
      text-align: center;  
      text-decoration: none;  
    }  
      /* 鼠标悬停在链接上时的样式 */  
      .navigation-bar a:hover {  
        background-color: #ddd;  
        color: black;  
      } 
      #page1{
        position: fixed;
        top: 0;
        left: 10%;
      } 
      #page2{
        position: fixed;
        top: 0;
        left: 16%;
      } 
      #page3{
        position: fixed;
        top: 0;
        left: 22%;
      } 
      .search-photo{ 
        position: fixed;  
        top: 1%;
        left: 89%;
        height: 30px;
      }
      .line{ 
        position: fixed;  
        top: 1%;
        left: 92%;
        height: 30px;
      }
      .head-photo{ 
        position: fixed;  
        top: 1%;
        left: 93%;
        height: 30px;
      }
    .button1 {  
      display: inline-block;  
      padding: 10px 18px;    
      color:black; /* 按钮的文本颜色 */  
      text-decoration: none;  
      border-radius: 4px; /* 按钮的圆角 */ 
      position: absolute;
      top: 13%;
      left: 75%; 
    }  
    .button1:hover {  
      background-color: #aab300; /* 鼠标悬停时的背景颜色 */  
    }
    .button2 {  
      display: inline-block;  
      padding: 10px 18px;    
      color:black; /* 按钮的文本颜色 */  
      text-decoration: none;  
      border-radius: 4px; /* 按钮的圆角 */ 
      position: absolute;
      top: 13%;
      left: 82%; 
    }  
    .button2:hover {  
      background-color: #aab300; /* 鼠标悬停时的背景颜色 */  
    }
    .HorizontalLine {  
      position: fixed; 
      margin-top: 10%;  /* 这些百分比可以根据您的需要进行调整 */  
      width: 100%;  
      text-align: center;
    }   
    #Rectangle1{
      position: absolute;
      left: 6%;
      top: 23%;
      width: 700px;  
      height: 150px;  
      background-color: #ffffff;  
      border: 1px solid #ddd;  
      text-align: center;  
      padding: 20px; 
      opacity: 0.6;   
    }
    .Head0{
      position: absolute;  
      top: 30%;
      left: 5%;
      width: 70px;
      height: 80px;
    }
    .ID {  
      position: absolute;  
      top: 45%;  
      left: 23%;  
      transform: translate(-50%, -50%);  
      font-size: 22px;  
      color: black;  
    }  
    .Rank {  
      position: absolute;  
      top: 45%;  
      left: 70%;  
      transform: translate(-50%, -50%);  
      font-size: 30px;  
      color: black;  
    } 

    #Rectangle2{
      position: absolute;
      left: 6%;
      top: 55%;
      width: 700px;  
      height: 250px;  
      background-color: #ffffff;  
      border: 1px solid #ddd;  
      text-align: center;  
      padding: 20px; 
      opacity: 0.6;   
    }
    .Report {  
      position: absolute;  
      top: 7%;  
      left: 8%;  
      transform: translate(-50%, -50%);  
      font-size: 18px;  
      color: black;  
    } 
    table {
      position: absolute;
      top: 30%;
      left: 3%;  
      width: 90%;  
      border-collapse: collapse; 
      table-layout: fixed; 
    }
    th, td {  
      border: 1px solid #c5abab;  
      padding: 8px;  
      text-align: left; 
      background-color: #f9f3f3;
    }    

    #Rectangle3{
      position: absolute;
      left: 65%;
      top: 23%;
      width: 350px;  
      height: 600px;  
      background-color: #ffffff;  
      border: 1px solid #ddd;  
      text-align: center;  
      padding: 20px; 
      opacity: 0.6;   
    }
    .Recent {  
      position: absolute;  
      top: 4%;  
      left: 18%;  
      transform: translate(-50%, -50%);  
      font-size: 18px;  
      color: black;  
    } 
    .button {  
      display: inline-block;  
      padding: 10px 18px;  
      background-color: #ffffff; /* 按钮的背景颜色 */  
      color:black; /* 按钮的文本颜色 */  
      border:1px solid #0056b3;
      text-decoration: none;  
      border-radius: 4px; /* 按钮的圆角 */ 
      position: absolute;
      top: 1%;
      left: 70%; 
    }  
    .button:hover {  
      background-color: #aab300; /* 鼠标悬停时的背景颜色 */  
    }  
    .table1 {
      position: absolute;
      top: 15%;
      left: 3%;  
      width: 90%;  
      border-collapse: collapse;   
      table-layout: fixed; 
    }
    .td1 {  
      padding: 18px;   
      background-color: #f3dddd;
    }
    .table1 tr .td1:nth-child(1) {  
      border-right:0; 
      text-align: left;  
    }   
    .table1 tr .td1:nth-child(2) {  
      border-left:0;
      border-right: 0; 
      text-align: left;  
    }
    .table1 tr .td1:nth-child(3) {  
      border-left:0; 
      text-align: right;  
    }