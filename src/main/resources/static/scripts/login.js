body{
    margin: 0;
}

.LOGO{
    content: url("LOGO.png");
}

#background{
    width: 100%;
    height: 100vh;
    background-image: url("background.png");
    background-repeat: no-repeat;
    background-size: cover;
    margin-top: 0px;
    position: absolute;
    opacity: 0.8;
    z-index: -1;
}

.navbar {
    color:#2F2E2D;
    background-color: #2F2E2D;
    width: 100%;
    height: 8vh;
}

.navbar .LOGO{
    width: 200px;
    height: 40px;
    margin-top: calc(4vh - 20px);
    margin-left: 10px;
}

.login{
    width:300px;
    height:400px;
    border: 5px black transparent;
    border-radius: 10px;
    position: absolute;
    background-color: white;
    left: calc(80% - 150px);
    top: 10vh;
    margin-top: 10vh;
    padding: 20px;
    background-color:rgba(16,16,16,0.27);
}

.login .LOGO{
    margin-left: 5vh;
    width : 200px;
    height : 40px;
}

.in{
    margin-left: 2vh;
    border-radius: 5px;
    width: 250px;
    height: 25px;
    font-family: "consolas";
}
.in text{
    margin-top: 50px;
}


#username{
    margin-top: 30px;
}
#password{
    margin-top: 15px;
}
#verification-message{
    margin-left: 2vh;
}

.login button{
    margin-top: 15px;
    background-color: #7BAC3B;
    color: white;
    opacity: 0.8;
    display: block;
    width: 255px;
}
.login button:hover{
    cursor:pointer;
    opacity: 1.0;
}

.login .account{
    height: 20px;
    line-height: 28px;
    font-size: 12px;
    padding: 0;
    display: inline;
}

#text{
    margin-left: 20px;
}
#sign-up
{
    color:#7BAC3B
}
#forget-password{
    margin-left: 90px;
    color:cadetblue;
}
#forget-password:hover{
    color:aqua;
    font-size: 10;
}

.login .container{
    display: flex;
}
.login .item{
    flex: 1;
}
.line{
    margin-top: 20px;
    width: 33.33%;
    height: 0px;
    border:1px solid white;
}
.container .para
{
    text-align: center;
    margin-top:-5px;
}
#line-one{
    float:left;
}
#line-two{
    float:right;
}