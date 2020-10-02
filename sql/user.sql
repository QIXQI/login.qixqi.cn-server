-- 使用数据库 qixqi_web
use qixqi_web;

-- 创建用户表 user
create table if not exists `user`(
	`uid` int(11) auto_increment primary key,
	`username` varchar(255) not null unique,
	`email` varchar(255) not null unique,
	`phone` varchar(255) not null unique,
	`password` varchar(255) not null,
	`sex` char(1) default 'u', 	-- 男m/女f
	`birthday` date,
	`register_time` timestamp default CURRENT_TIMESTAMP,
	`status_id` int(11) default 0,
	`avatar` varchar(255) default 'default.png',
	foreign key(`status_id`) references user_status(`status_id`)
	on delete set null on update cascade
)ENGINE=InnoDB AUTO_INCREMENT=1 default charset=utf8;


-- 创建用户状态表 user_status
create table if not exists `user_status`(
	`status_id` int(11) primary key,
	`status` varchar(255) not null unique
)ENGINE=InnoDB default charset=utf8;


insert into user_status (status_id, status) values (0, "离线");
insert into user_status (status_id, status) values (1, "在线");



-- 创建登录日志表 user_login_log
create table if not exists `user_login_log`(
	`uid` int(11) not null,
	`login_time` timestamp default CURRENT_TIMESTAMP,
	`site` varchar(255),		-- 登录地点
	`login_ip`	varchar(255),	-- 登录ip
	foreign key(`uid`) references user(`uid`)
	on delete cascade on update cascade
)ENGINE=InnoDB default charset=utf8;


-- 创建用户找回密码验证码表 user_reset_code
create table if not exists `user_reset_code`(
	`email` varchar(255) not null,
	`code` char(6) not null,
	`send_time` timestamp default CURRENT_TIMESTAMP
)ENGINE=InnoDB default charset=utf8;