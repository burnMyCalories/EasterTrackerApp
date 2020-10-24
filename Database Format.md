# Database Format

-   User

    | 字段名称      | 字段类型     | 说明 | 备注                 |
    | ------------- | ------------ | ---- | -------------------- |
    | id            | int(50)      |      | not null primary key |
    | username      | varchar(255) |      |                      |
    | password      | varchar(255) |      |                      |
    | isActive      | int(1)       |      |                      |
    | isOnline      | int(1)       |      |                      |
    | creation_time | datetime     |      |                      |
    | update_time   | datetime     |      |                      |
    | version       | int(50)      |      |                      |
    | is_deleted    | int(1)       |      |                      |
    | latitude      | double(50)   |      |                      |
    | longitude     | double(50)   |      |                      |

-   Friendship

    | 字段名称      | 字段类型 | 说明 | 备注                 |
    | ------------- | -------- | ---- | -------------------- |
    | id            | int(50)  |      | not null primary key |
    | userfrom_id   | int(50)  |      | not null foreign key |
    | userto_id     | int(50)  |      | not null foreign key |
    | creation_time | datetime |      |                      |
    | update_time   | datetime |      |                      |
    | version       | int(50)  |      |                      |
    | is_deleted    | int(1)   |      |                      |
    |               |          |      |                      |
    |               |          |      |                      |
    |               |          |      |                      |
    |               |          |      |                      |

-   Message

    | 字段名称      | 字段类型     | 说明 | 备注                 |
    | ------------- | ------------ | ---- | -------------------- |
    | id            | int(50)      |      | not null primary key |
    | friend_id     | int(50)      |      | not null foreign key |
    | content       | varchar(255) |      |                      |
    | creation_time | datetime     |      |                      |
    | update_time   | datetime     |      |                      |
    | version       | int(50)      |      |                      |
    | is_deleted    | int(1)       |      |                      |
    | type          | int(4)       |      |                      |
    |               |              |      |                      |
    |               |              |      |                      |
    |               |              |      |                      |

-   Egg

    | 字段名称      | 字段类型     | 说明 | 备注                 |
    | ------------- | ------------ | ---- | -------------------- |
    | id            | int(50)      |      | not null primary key |
    | color         | varchar(50)  |      |                      |
    | latitude      | double(50)   |      |                      |
    | longitude     | double(50)   |      |                      |
    |               |              |      |                      |
    | content       | varchar(255) |      |                      |
    | creation_time | datetime     |      |                      |
    | update_time   | datetime     |      |                      |
    | version       | int(50)      |      |                      |
    | is_deleted    | int(1)       |      |                      |
    | name          | varchar(255) |      |                      |
	| expire_time   | datetime     |      |                      |
	| type          | int(4)       |      |                      |
	
-   UserEggAction

    | 字段名称      | 字段类型 | 说明 | 备注                 |
    | ------------- | -------- | ---- | -------------------- |
    | id            | int(50)  |      | not null primary key |
    | userid        | int(50)  |      | not null foreign key |
    | eggid         | int(50)  |      | not null foreign key |
    | action        | int(4)   |      |                      |
    | creation_time | datetime |      |                      |
    | update_time   | datetime |      |                      |
    | version       | int(50)  |      |                      |
    | is_deleted    | int(1)   |      |                      |
    |               |          |      |                      |
    |               |          |      |                      |
    |               |          |      |                      |

    