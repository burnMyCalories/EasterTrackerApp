create database if not exists EasterTracker;
create table if not exists `User`(
    `id` int(50) not null primary key comment 'user id',
    `username` varchar(255) not null default 'anonymous',
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
    `name` varchar(255) not null default 'anonymous',
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
