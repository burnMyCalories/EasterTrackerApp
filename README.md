# EasterTracker BackEnd
This is the backend code for EasterTracker App.

## Preparation

Before running the project, please ensure you have these packages installed.

1.  Java version: 1.8+
2.  Tomcat version: 8.5+
3.  MySQL version: 5.7

## Installation

Once you have installed these packages in your server, please following the steps below:

1.  Install Maven (if you haven't installed yet)
2.  Run `mvn install`, which will generate a `war` file in target folder
3.  Upload the `war`file to your server `/webApps`

## Testing

Before testing, you should create database and table use the sql below:

```sql
create database if not exists EasterTracker;
create table if not exists `User`(
                                     `id` int(50) not null primary key comment 'user id',
                                     `username` varchar(255) not null unique default 'anonymous',
                                     `password` varchar(255) not null,
                                     `gender` varchar(2) not null,
                                     `nickname` varchar(255) not null default 'anonymous',
                                     `contact` varchar(255) not null,
                                     `is_active` int(1) not null default 0,
                                     `is_online` int(1) not null default 0,
                                     `latitude` double(50,10) not null default 0.0,
                                     `longitude` double(50,10) not null default 0.0,
                                     `version` int(50) not null default 1,
                                     `is_deleted` int(1) not null default 0,
                                     `creation_time` datetime not null default NOW(),
                                     `update_time` datetime not null default NOW()
) engine=innodb default charset=utf8;
create table if not exists `Friendship`(
                                           `id` int(50) not null primary key comment 'friendship id',
                                           `userfrom_id` int(50) not null,
                                           `userto_id` int(50) not null,
                                           `version` int(50) not null default 1,
                                           `is_deleted` int(1) not null default 0,
                                           `creation_time` datetime not null default NOW(),
                                           `update_time` datetime not null default NOW(),
                                           foreign key (`userfrom_id`) references `User`(`id`),
                                           foreign key (`userto_id`) references `User`(`id`)
) engine=innodb default charset=utf8;
create table if not exists `Message`(
                                        `id` int(50) not null primary key comment 'message id',
                                        `friend_id` int(50) not null,
                                        `type` int(4) not null default 0,
                                        `content` varchar(255) not null,
                                        `version` int(50) not null default 1,
                                        `is_deleted` int(1) not null default 0,
                                        `creation_time` datetime not null default NOW(),
                                        `update_time` datetime not null default NOW(),
                                        foreign key (`friend_id`) references `Friendship`(`id`)
) engine=innodb default charset=utf8;
create table if not exists `Egg`(
                                    `id` int(50) not null primary key comment 'egg id',
                                    `name` varchar(255) not null unique default 'anonymous',
                                    `color` varchar(50) not null,
                                    `type` int(4) not null default 0,
                                    `latitude` double(50,10) not null default 0.0,
                                    `longitude` double(50,10) not null default 0.0,
                                    `content` varchar(255) not null,
                                    `version` int(50) not null default 1,
                                    `is_deleted` int(1) not null default 0,
                                    `creation_time` datetime not null default NOW(),
                                    `update_time` datetime not null default NOW(),
                                    `expire_time` datetime not null default NOW()
) engine=innodb default charset=utf8;
create table if not exists `UserEggAction`(
                                              `id` int(50) not null primary key comment 'action id',
                                              `user_id` int(50) not null,
                                              `egg_id` int(50) not null,
                                              `action` int(4) not null default 0,
                                              `version` int(50) not null default 1,
                                              `is_deleted` int(1) not null default 0,
                                              `creation_time` datetime not null default NOW(),
                                              `update_time` datetime not null default NOW(),
                                              foreign key (`user_id`) references `User`(`id`),
                                              foreign key (`egg_id`) references `Egg`(`id`)
) engine=innodb default charset=utf8;
```

After successfully installation, you can test the project by accessing the url in your browser:

`http://server ip:8080/project name`

If you can successfully access it, the installation is successful.

## Usage

### Login & Register

1.  Register

    url:`/register`

    Method：POST

    Parameters：

    ```
    username
    password
    gender
    nickname
    contact
    latitude
    longitude
    ```

    Response：

    -   Status Code：

        ```
        400：Invalid Parameters
        410：Register Failed
        201：Register Success
        ```

    -   Result：

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example:

        `POST http://localhost:8080/test/register?gender=M&is_online=1&username=test2222&password=111111&latitude=15&longitude=45&nickname=abcd&contact=13800000000`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1277068326,
                        "username": "test2222",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 0,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

2.  Login

    url：`/login`

    Method：PUT

    Parameters：

    ```
    username
    password
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        401：Login Failed
        403：Already Logged In
        200：Login Success
        ```

    -   Result：

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/login?username=test2222&password=111111`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 629114150,
                        "username": "test2222",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 1,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Logout

    url：`/logout`

    Method：PUT

    Parameter：

    ```
    username
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        401：Logout Failed
        403：Already Logged Out
        200：Logout Success
        ```

    -   Result：

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/logout?username=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 629114150,
                        "username": "test2222",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 0,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Validation

    url：`/confirm`

    Method：POST

    Parameters：

    ```
    email
    mobile
    (choose one)
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        201：Validation Success
        500：Validation Failed
        ```

    -   Result：

        ```
        status：{
        	code：
        	msg：
        }
        code：
        expire：
        ```

    -   example

        `POST http://localhost:8080/test/confirm?email=543777820@qq.com`

        ```json
        {
            "code": "5sc5ty",
            "expire": "2020-10-28 16:37:02",
            "status": {
                "code": 0,
                "msg": "Success"
            }
        }
        ```

        ```json
        {
            "code": "l1e32z",
            "expire": "2020-10-28 16:38:50",
            "status": {
                "code": 0,
                "data": "发送成功"
            }
        }
        ```

-   Notice：

    Please login first before doing other operations, otherwise you will get a 401 Error, which shows you haven't login yet. (If your login has timed out, you will also get a 401 Error)

    ```json
    {
        "status": {
            "code": 1,
            "msg": "Not Logged In"
        },
        "result": {}
    }
    ```

### Users Info

1.  Select Users

    url：`/user`

    Method：GET

    Parameters：

    ```
    uuname
    id
    username
    password
    gender
    nickname
    contact
    latitude
    longitude
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/user?username=abc&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1814538945,
                        "username": "abc",
                        "gender": "M",
                        "nickname": "abcdefg",
                        "contact": "13801111100",
                        "is_online": 0,
                        "latitude": 22.0,
                        "longitude": 77.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

2.  Insert Users

    url：`/user`

    Method：POST

    Parameters：

    ```
    uuname
    username
    password
    gender
    nickname
    contact
    latitude
    longitude
    ```

    Response：

    -   Status Code:

        ```
        400：Invalid Parameters
        410：Insert Failed
        201：Insert Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `POST http://localhost:8080/test/user?gender=M&is_online=1&password=123456&latitude=15&longitude=45&nickname=abcd&contact=13800000000&username=test1111&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1039101465,
                        "username": "test1111",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 0,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Update Users

    url：`/user`

    Method：PUT

    Parameters：

    ```
    uuname
    id
    username
    password
    gender
    nickname
    contact
    latitude
    longitude
    is_online
    is_deleted
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Update Failed
        201：Update Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/user?username=test1111&password=123456&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1039101465,
                        "username": "test1111",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 0,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Delete Users

    url：`/user`

    Method：DELETE

    Parameters：

    ```
    uuname：
    id：
    username：
    nickname：
    
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameter
        410：Delete Failed
        201：Delete Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `DELETE http://localhost:8080/test/user?username=test1111&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1039101465,
                        "username": "test1111",
                        "gender": "M",
                        "nickname": "abcd",
                        "contact": "13800000000",
                        "is_online": 0,
                        "latitude": 15.0,
                        "longitude": 45.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

### Friendship Info

**Note: Friendship is directed**

1.  Select Friendship

    url：`/friendship`

    Method：GET

    Parameters：

    ```
    uuname
    id
    userfrom_id
    userto_id
    userfrom_username
    userto_username
    userfrom_nickname
    userto_nickname
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parmeters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/friendship?userto_nickname=abcdefg&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1927044041,
                        "userfrom": {
                            "id": 1139780601,
                            "username": "test4",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 1,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 1,
                            "get_count": 0,
                            "is_deleted": 0
                        },
                        "userto": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "is_deleted": 0
                    },
                    {
                        "id": 2014164941,
                        "userfrom": {
                            "id": 1139780601,
                            "username": "test4",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 1,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 1,
                            "get_count": 0,
                            "is_deleted": 0
                        },
                        "userto": {
                            "id": 1325542530,
                            "username": "test3",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 0,
                            "get_count": 2,
                            "is_deleted": 0
                        },
                        "is_deleted": 1
                    }
                ],
                "rows": 2
            }
        }
        ```

2.  Insert Friendship

    url：`/friendship`

    Method：POST

    Parameters：

    ```
    uuname
    userfrom_id
    userto_id
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parmeters
        410：Insert Failed
        201：Insert Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `POST http://localhost:8080/test/friendship?userfrom_id=1139780601&userto_id=687005410&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1385729779,
                        "userfrom": {
                            "id": 1139780601,
                            "username": "test4",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 1,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 1,
                            "get_count": 0,
                            "is_deleted": 0
                        },
                        "userto": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Update Friendship

    url：`/friendship`

    Method：PUT

    Parameters：

    ```
    uuname
    id
    userfrom_id
    userto_id
    is_deleted
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Update Failed
        201：Update Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/friendship?is_deleted=0&userfrom_id=1139780601&userto_id=687005410&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1927044041,
                        "userfrom": {
                            "id": 1139780601,
                            "username": "test4",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 1,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 1,
                            "get_count": 0,
                            "is_deleted": 0
                        },
                        "userto": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Delete Friendship

    url：`/friendship`

    Method：DELETE

    Parameters：

    ```
    uuname
    id
    userfrom_id
    userto_id
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Delete Failed
        201：Delete Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `DELETE http://localhost:8080/test/friendship?uuname=test2222&id=1385729779`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1385729779,
                        "userfrom": {
                            "id": 1139780601,
                            "username": "test4",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 1,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 1,
                            "get_count": 0,
                            "is_deleted": 0
                        },
                        "userto": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

### Message Info

1.  Select Message

    url：`/message`

    Method：GET

    Parameters：

    ```
    uuname
    id
    friend_id
    userfrom_id
    userto_id
    userfrom_username
    userto_username
    userfrom_nickname
    userto_nickname
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/message?userto_nickname=abcdefg&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 52551251,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "ddddddddddddddddd",
                        "is_deleted": 0
                    },
                    {
                        "id": 464859269,
                        "friend": {
                            "id": 2014164941,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 1325542530,
                                "username": "test3",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 0,
                                "get_count": 2,
                                "is_deleted": 0
                            },
                            "is_deleted": 1
                        },
                        "type": 1,
                        "content": "abcabcabcabcdddddddd",
                        "is_deleted": 0
                    },
                    {
                        "id": 922674365,
                        "friend": {
                            "id": 2014164941,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 1325542530,
                                "username": "test3",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 0,
                                "get_count": 2,
                                "is_deleted": 0
                            },
                            "is_deleted": 1
                        },
                        "type": 1,
                        "content": "abcabcabcabc",
                        "is_deleted": 0
                    },
                    {
                        "id": 938847569,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "ttttttttttttt",
                        "is_deleted": 0
                    },
                    {
                        "id": 998369285,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "abcabcabcabc",
                        "is_deleted": 0
                    },
                    {
                        "id": 1279150339,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "dddddddddd",
                        "is_deleted": 0
                    },
                    {
                        "id": 1741805756,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "uuuuuuu",
                        "is_deleted": 0
                    }
                ],
                "rows": 7
            }
        }
        ```

2.  Insert Messages

    url：`/message`

    Method：POST

    Parameters：

    ```
    uuname：
    friend_id：
    type：
    content：
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Insert Failed
        201：Insert Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `POST http://localhost:8080/test/message?type=2&friend_id=1927044041&uuname=test2222&content=uri://123123`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 208172447,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 2,
                        "content": "uri://123123",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Update Messages

    url：`/message`

    Method：PUT

    Parameters：

    ```
    uuname
    id
    friend_id
    is_deleted
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Update Failed
        201：Update Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/message?friend_id=1927044041&uuname=test2222&is_deleted=0`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1741805756,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 1,
                        "content": "uuuuuuu",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Delete Messages

    url：`/message`

    Method：DELETE

    Parameters：

    ```
    uuname
    id
    friend_id
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Delete Failed
        201：Delete Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `DELETE http://localhost:8080/test/message?id=208172447&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 208172447,
                        "friend": {
                            "id": 1927044041,
                            "userfrom": {
                                "id": 1139780601,
                                "username": "test4",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 1,
                                "latitude": 21.0,
                                "longitude": 55.0,
                                "set_count": 1,
                                "get_count": 0,
                                "is_deleted": 0
                            },
                            "userto": {
                                "id": 687005410,
                                "username": "test2",
                                "gender": "M",
                                "nickname": "abcdefg",
                                "contact": "13801111100",
                                "is_online": 0,
                                "latitude": 14.0,
                                "longitude": 66.0,
                                "set_count": 1,
                                "get_count": 1,
                                "is_deleted": 0
                            },
                            "is_deleted": 0
                        },
                        "type": 2,
                        "content": "uri://123123",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

### Egg Info

**Note: This section only operates on egg, not actions related to eggs.**

1.  Select Eggs

    url：`/egg`

    Method：GET

    Parameters：

    ```
    uuname
    id
    name
    color
    type
    latitude
    longitude
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/egg?uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1942732372,
                        "name": "egg11",
                        "color": "blue",
                        "type": 2,
                        "latitude": 21.0000001,
                        "longitude": 55.0,
                        "content": "url://something2111",
                        "expire_time": "2021-01-01 00:00:00.0",
                        "is_deleted": 0
                    },
                    {
                        "id": 2076294926,
                        "name": "egg15",
                        "color": "yellow",
                        "type": 3,
                        "latitude": 21.0,
                        "longitude": 66.0,
                        "content": "url://something2333",
                        "expire_time": "2020-11-01 01:00:00.0",
                        "is_deleted": 0
                    }
                ],
                "rows": 2
            }
        }
        ```

2.  Insert Eggs

    url：`/egg`

    Method：POST

    Parameters：

    ```
    uuname
    name
    color
    type
    latitude
    longitude
    content
    expire_time
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Insert Failed
        201：Insert Success
        ```

    -   结果

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `POST http://localhost:8080/test/egg?latitude=22&longitude=77&type=2&content=url://something2111&color=blue&expire_time=2020-11-01 01:00&uuname=test2222&name=egg11`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1254007430,
                        "name": "egg11",
                        "color": "blue",
                        "type": 2,
                        "latitude": 22.0,
                        "longitude": 77.0,
                        "content": "url://something2111",
                        "expire_time": "2020-11-01 01:00:00.0",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Update Eggs

    url：`/egg`

    Method：PUT

    Parameters：

    ```
    uuname
    id
    name
    color
    type
    latitude
    longitude
    content
    expire_time
    is_deleted
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Update Failed
        201：Update Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/egg?content=something2333&id=1254007430&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1254007430,
                        "name": "egg11",
                        "color": "blue",
                        "type": 2,
                        "latitude": 22.0,
                        "longitude": 77.0,
                        "content": "something2333",
                        "expire_time": "2020-11-01 01:00:00.0",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Delete Eggs

    url：`/egg`

    Method：DELETE

    Parameters：

    ```
    uuname
    id
    name
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Delete Failed
        201：Delete Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `DELETE http://localhost:8080/test/egg?id=1254007430&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1254007430,
                        "name": "egg11",
                        "color": "blue",
                        "type": 2,
                        "latitude": 22.0,
                        "longitude": 77.0,
                        "content": "something2333",
                        "expire_time": "2020-11-01 01:00:00.0",
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

### Action Info

Actions -> "put" or  "get"

1.  Select Actions

    url：`/action`

    Method：GET

    Parameters：

    ```
    uuname
    id
    user_id
    egg_id
    action
    user_username
    user_nickname
    egg_name
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/action?user_id=687005410&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 407058930,
                        "user": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "egg": {
                            "id": 1942732372,
                            "name": "egg11",
                            "color": "blue",
                            "type": 2,
                            "latitude": 21.0000001,
                            "longitude": 55.0,
                            "content": "url://something2111",
                            "expire_time": "2021-01-01 00:00:00.0",
                            "is_deleted": 0
                        },
                        "action": 1,
                        "is_deleted": 0,
                        "update_time": "2020-10-25 17:41:57.0"
                    },
                    {
                        "id": 724014183,
                        "user": {
                            "id": 687005410,
                            "username": "test2",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 14.0,
                            "longitude": 66.0,
                            "set_count": 1,
                            "get_count": 1,
                            "is_deleted": 0
                        },
                        "egg": {
                            "id": 2076294926,
                            "name": "egg15",
                            "color": "yellow",
                            "type": 3,
                            "latitude": 21.0,
                            "longitude": 66.0,
                            "content": "url://something2333",
                            "expire_time": "2020-11-01 01:00:00.0",
                            "is_deleted": 0
                        },
                        "action": 2,
                        "is_deleted": 0,
                        "update_time": "2020-10-26 15:29:12.0"
                    }
                ],
                "rows": 2
            }
        }
        ```

2.  Insert Actions

    url：`/action`

    Method：POST

    Parameters：

    ```
    uuname
    user_id
    egg_id
    action
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Insert Failed
        201：Insert Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `POST http://localhost:8080/test/action?egg_id=2076294926&action=2&uuname=test2222&user_id=1325542530`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1183662930,
                        "user": {
                            "id": 1325542530,
                            "username": "test3",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 0,
                            "get_count": 3,
                            "is_deleted": 0
                        },
                        "egg": {
                            "id": 2076294926,
                            "name": "egg15",
                            "color": "yellow",
                            "type": 3,
                            "latitude": 21.0,
                            "longitude": 66.0,
                            "content": "url://something2333",
                            "expire_time": "2020-11-01 01:00:00.0",
                            "is_deleted": 0
                        },
                        "action": 2,
                        "is_deleted": 0,
                        "update_time": "2020-10-28 17:58:55.0"
                    }
                ],
                "rows": 1
            }
        }
        ```

3.  Update Actions

    url：`/action`

    Method：PUT

    Parameters：

    ```
    uuname
    id
    user_id
    egg_id
    action
    is_deleted
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Update Failed
        201：Update Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `PUT http://localhost:8080/test/action?id=1183662930&uuname=test2222&is_deleted=1`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1183662930,
                        "user": {
                            "id": 1325542530,
                            "username": "test3",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 0,
                            "get_count": 3,
                            "is_deleted": 0
                        },
                        "egg": {
                            "id": 2076294926,
                            "name": "egg15",
                            "color": "yellow",
                            "type": 3,
                            "latitude": 21.0,
                            "longitude": 66.0,
                            "content": "url://something2333",
                            "expire_time": "2020-11-01 01:00:00.0",
                            "is_deleted": 0
                        },
                        "action": 2,
                        "is_deleted": 1,
                        "update_time": "2020-10-28 18:02:00.0"
                    }
                ],
                "rows": 1
            }
        }
        ```

4.  Delete Actions

    url：`/action`

    Method：DELETE

    Parameters：

    ```
    uuname
    id
    user_id
    egg_id
    action
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：Delete Failed
        201：Delete Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `DELETE http://localhost:8080/test/action?id=1183662930&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1183662930,
                        "user": {
                            "id": 1325542530,
                            "username": "test3",
                            "gender": "M",
                            "nickname": "abcdefg",
                            "contact": "13801111100",
                            "is_online": 0,
                            "latitude": 21.0,
                            "longitude": 55.0,
                            "set_count": 0,
                            "get_count": 3,
                            "is_deleted": 0
                        },
                        "egg": {
                            "id": 2076294926,
                            "name": "egg15",
                            "color": "yellow",
                            "type": 3,
                            "latitude": 21.0,
                            "longitude": 66.0,
                            "content": "url://something2333",
                            "expire_time": "2020-11-01 01:00:00.0",
                            "is_deleted": 0
                        },
                        "action": 2,
                        "is_deleted": 1,
                        "update_time": "2020-10-28 18:02:00.0"
                    }
                ],
                "rows": 1
            }
        }
        ```

### Others

1.  Get Users Nearby

    url：`/nearuser`

    Method：GET

    Parameters：

    ```
    uuname
    id
    username
    nickname
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   结果

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/nearuser?id=1183662930&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1814538945,
                        "username": "abc",
                        "gender": "M",
                        "nickname": "abcdefg",
                        "contact": "13801111100",
                        "is_online": 0,
                        "latitude": 22.0,
                        "longitude": 77.0,
                        "set_count": 0,
                        "get_count": 0,
                        "is_deleted": 0
                    }
                ],
                "rows": 1
            }
        }
        ```

2.  Get Nearby Eggs

    url：`/nearegg`

    Method：GET

    Parameters：

    ```
    uuname
    id
    username
    nickname
    ```

    Response：

    -   Status Code

        ```
        400：Invalid Parameters
        410：No such data
        200：Select Success
        ```

    -   Result

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	data：
        	rows：
        }
        ```

    -   example

        `GET http://localhost:8080/test/nearegg?id=1183662930&uuname=test2222`

        ```json
        {
            "status": {
                "code": 0,
                "msg": "Success"
            },
            "result": {
                "data": [
                    {
                        "id": 1942732372,
                        "name": "egg11",
                        "color": "blue",
                        "type": 2,
                        "latitude": 21.0000001,
                        "longitude": 55.0,
                        "content": "url://something2111",
                        "expire_time": "2021-01-01 00:00:00.0",
                        "is_deleted": 0
                    },
                    {
                        "id": 2076294926,
                        "name": "egg15",
                        "color": "yellow",
                        "type": 3,
                        "latitude": 21.0,
                        "longitude": 66.0,
                        "content": "url://something2333",
                        "expire_time": "2020-11-01 01:00:00.0",
                        "is_deleted": 0
                    }
                ],
                "rows": 2
            }
        }
        ```

3.  Upload/Download Resource Files

    url：`/file`

    -   Upload

        Method：POST

        File：`file:file`

        FrontEnd example：

        ```html
        <form action="/file" enctype="multipart/form-data" method="post">
            Upload：<input type="file" name="file"><br/>
            <input type="submit" value="Submit">
        </form>
        ```

        Notice：`enctype="multipart/form-data"`is required

        Status Code：

        ```
        201：Upload Success
        400：Invalid File
        500：Upload Failed
        ```

        Result：

        ```
        status：{
        	code：
        	msg：
        }
        result：{
        	name：
        	value：
        	type：
        }
        ```

        example：

        ```json
        {
            "result":[
                {
                    "name":"id.png",
                    "value":"",
                    "type":"file"
                }
            ],
            "status":{
                "code":0,
                "msg":"Success"
            }
        }
        ```

    -   Download

        Method：GET

        Parameter：`filename`

        Result：a file

        Status Code：

        ```
        200：Download Success
        404：File not found
        400：Invalid Parameters
        500：Download Failed
        ```

        

## CopyRight

Copyright © 2020, Team: `Burn My Calories`, release under [Apache-2.0 License](https://github.com/burnMyCalories/EasterEggMap-Backend/blob/main/LICENSE).

