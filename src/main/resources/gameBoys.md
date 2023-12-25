---
title: gameBoys v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.17"

---

# gameBoys

> v1.0.0

Base URLs:

* <a href="http://dev-cn.your-api-server.com">开发环境: http://dev-cn.your-api-server.com</a>

# Authentication

# 邮箱验证

<a id="opIdtypeVeriCodeToRegister"></a>

## POST 验证发送的验证码

POST /typeVeriCode/{operation}

operation 1是注册,2是改密码,3是登录
登录本来应该是GET，但懒得改了

> Body Parameters

```json
"string"
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|operation|path|integer| yes |none|
|body|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdsendVeriCode"></a>

## GET 发送验证码

GET /sendVeriCode/{operation}

operation 1是注册,2是改密码,3是登录

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|operation|path|integer| yes |none|
|email|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

# 忘记密码

<a id="opIdresetPassword"></a>

## POST 重置密码

POST /resetPassword

仅需要输入密码

> Body Parameters

```json
"string"
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|string| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

# 注册

<a id="opIduserRegister"></a>

## POST 用户注册

POST /register

需要输入用户名和密码

> Body Parameters

```json
{
  "username": "string",
  "password": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[RegisterJSON](#schemaregisterjson)| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

# 查询排位信息

<a id="opIdupdatePortrait"></a>

## POST 修改头像

POST /ranks/portrait

请输入用户名username和头像id portrait

> Body Parameters

```json
{
  "username": "string",
  "portrait": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[SetPortraitJSON](#schemasetportraitjson)| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetPersonalReport"></a>

## GET 统计战报

GET /ranks/othersReport/{type}

仅返回一个PersonalReport对象。个人主页中需要调用两次

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|type|path|integer| yes |none|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetOthersMonthGame"></a>

## GET 统计本月战绩信息，即本月场次部分，仅返回一个Records对象。请看Records类

GET /ranks/othersMonthRecords

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetOthersAllGame"></a>

## GET 统计别人的战绩信息，即全部场次部分

GET /ranks/othersAllRecords

仅返回一个Records对象。请看Records类

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetTotalScoreRankingOrder"></a>

## GET 获取总分排行榜

GET /ranks/getTotalScoreRankingOrder

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdmyAllRankInformation"></a>

## GET 获取我自己或别人的所有比赛记录信息，返回List<RecordVO>

GET /ranks/getRanks

共64个RecordVO

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdmyRankInformation"></a>

## GET 获取我自己或别人的比赛记录信息，返回List<RecordVO>

GET /ranks/getRanks/{page}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|path|integer| yes |none|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetRankPosition"></a>

## GET 获取用户排名

GET /ranks/getRankPosition

请输入用户名username

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetGamesByGameId"></a>

## GET 通过id获取比赛记录信息，返回List<RecordVO>

GET /ranks/getGamesByGameId

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|gameId|query|integer| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdgetBrawlRankingOrder"></a>

## GET 获取乱斗排行榜

GET /ranks/getBrawlRankingOrder

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdget1v1RankingOrder"></a>

## GET 获取1v1排行榜

GET /ranks/get1v1RankingOrder

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdcheckIfExist"></a>

## GET 查用户是否存在

GET /ranks/checkIfExist

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|

> Response Examples

> 200 Response

```json
true
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|boolean|

# 更新排位信息

<a id="opIdrecordNewMatch"></a>

## POST 记录新的一场比赛

POST /games/recordNewMatch

返回1表示更新成功，返回0表示更新失败。把游戏里updateDatabase的参数全部用json发进来

> Body Parameters

```json
{
  "maxGameId": 0,
  "duration": 0,
  "username": "string",
  "kill": 0,
  "death": 0,
  "scoreGain": 0,
  "assist": 0,
  "takeDamage": 0,
  "takenDamage": 0,
  "mvpPlayer": "string",
  "gameMode": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[NewGameRecordJSON](#schemanewgamerecordjson)| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

<a id="opIdaddNewGame"></a>

## POST 添加新的一场游戏

POST /games/addNewGame

无需使用，只是测试而已。插件里有这个功能

> Body Parameters

```json
{
  "type": 0,
  "gameId": 0,
  "duration": 0,
  "mvpPlayer": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[AddNewGameJSON](#schemaaddnewgamejson)| no |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

# 登录

<a id="opIdlogin"></a>

## GET 登录

GET /login

需要输入用户名和密码

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|username|query|string| yes |none|
|password|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[JsonResult](#schemajsonresult)|

# Data Schema

<h2 id="tocS_AddNewGameJSON">AddNewGameJSON</h2>

<a id="schemaaddnewgamejson"></a>
<a id="schema_AddNewGameJSON"></a>
<a id="tocSaddnewgamejson"></a>
<a id="tocsaddnewgamejson"></a>

```json
{
  "type": 0,
  "gameId": 0,
  "duration": 0,
  "mvpPlayer": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|type|integer(int32)|false|none||none|
|gameId|integer(int32)|false|none||none|
|duration|integer(int64)|false|none||none|
|mvpPlayer|string|false|none||none|

<h2 id="tocS_NewGameRecordJSON">NewGameRecordJSON</h2>

<a id="schemanewgamerecordjson"></a>
<a id="schema_NewGameRecordJSON"></a>
<a id="tocSnewgamerecordjson"></a>
<a id="tocsnewgamerecordjson"></a>

```json
{
  "maxGameId": 0,
  "duration": 0,
  "username": "string",
  "kill": 0,
  "death": 0,
  "scoreGain": 0,
  "assist": 0,
  "takeDamage": 0,
  "takenDamage": 0,
  "mvpPlayer": "string",
  "gameMode": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|maxGameId|integer(int32)|false|none||none|
|duration|integer(int64)|false|none||none|
|username|string|false|none||none|
|kill|integer(int32)|false|none||none|
|death|integer(int32)|false|none||none|
|scoreGain|integer(int32)|false|none||none|
|assist|integer(int32)|false|none||none|
|takeDamage|number(double)|false|none||none|
|takenDamage|number(double)|false|none||none|
|mvpPlayer|string|false|none||none|
|gameMode|integer(int32)|false|none||none|

<h2 id="tocS_SetPortraitJSON">SetPortraitJSON</h2>

<a id="schemasetportraitjson"></a>
<a id="schema_SetPortraitJSON"></a>
<a id="tocSsetportraitjson"></a>
<a id="tocssetportraitjson"></a>

```json
{
  "username": "string",
  "portrait": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|username|string|false|none||none|
|portrait|integer(int32)|false|none||none|

<h2 id="tocS_Tag">Tag</h2>

<a id="schematag"></a>
<a id="schema_Tag"></a>
<a id="tocStag"></a>
<a id="tocstag"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||标签ID编号|
|name|string|false|none||标签名称|

<h2 id="tocS_RegisterJSON">RegisterJSON</h2>

<a id="schemaregisterjson"></a>
<a id="schema_RegisterJSON"></a>
<a id="tocSregisterjson"></a>
<a id="tocsregisterjson"></a>

```json
{
  "username": "string",
  "password": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|username|string|false|none||none|
|password|string|false|none||none|

<h2 id="tocS_Category">Category</h2>

<a id="schemacategory"></a>
<a id="schema_Category"></a>
<a id="tocScategory"></a>
<a id="tocscategory"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||分组ID编号|
|name|string|false|none||分组名称|

<h2 id="tocS_JsonResult">JsonResult</h2>

<a id="schemajsonresult"></a>
<a id="schema_JsonResult"></a>
<a id="tocSjsonresult"></a>
<a id="tocsjsonresult"></a>

```json
{
  "code": 0,
  "data": {},
  "message": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|code|integer(int32)|false|none||none|
|data|object|false|none||none|
|message|string|false|none||none|

<h2 id="tocS_Pet">Pet</h2>

<a id="schemapet"></a>
<a id="schema_Pet"></a>
<a id="tocSpet"></a>
<a id="tocspet"></a>

```json
{
  "id": 1,
  "category": {
    "id": 1,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 1,
      "name": "string"
    }
  ],
  "status": "available"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|true|none||宠物ID编号|
|category|[Category](#schemacategory)|true|none||分组|
|name|string|true|none||名称|
|photoUrls|[string]|true|none||照片URL|
|tags|[[Tag](#schematag)]|true|none||标签|
|status|string|true|none||宠物销售状态|

#### Enum

|Name|Value|
|---|---|
|status|available|
|status|pending|
|status|sold|

