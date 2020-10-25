# 用户信息相关接口

## 登陆
### url
`api/login`

### 请求参数
- `username/email`*
- `password`*
### 返回参数
- `status`: 返回状态（sucess/error等）
- `msg`: 详细信息（比如具体哪里出错）

## 注册
`api/regsiter`

### 请求参数
- `username`*
- `nickname`*: 用户昵称
- `email`*
- `password`*
### 返回参数
- `status`: 返回状态
- `msg`: 详细信息

## 用户信息
`api/user/uuid`

> `POST/DELETE/PATCH/GET` 请求对应增删改查，如不用RESTFul API则放在user路由后再加uuid，或者在请求参数中加一个`method`字段
### 请求参数
- `uuid/nickname`*: 全局唯一标识符/用户昵称(GET时二选一，其他情况只有uuid必需)
- `nickname`
- `email`
- `gender`
- `myEggsCounts`: 我埋了几个彩蛋
- `foundEggsCounts`: 我找到了几个彩蛋
- ...
### 返回参数
- `status`: 返回状态
- `msg`: 详细信息
- `data`: 回传数据（查询后结果、修改成功后资料等）

## 好友列表
`api/friends/uuid`
> `GET`返回好友列表、`DELETE`删好友、`POST`加好友
### 请求参数
- `uuid`*
- `fuuid`: 好友的uuid
- ...
### 返回参数
- `status`: 返回状态
- `msg`: 详细信息
- `data`: 回传数据

# 地图相关接口

# 彩蛋相关接口