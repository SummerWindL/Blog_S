--数据库表

--文章表
--文章ID
--文章标题
--文章内容
--创建时间，发表时间
--作者
create table tb_article(
	id INT UNSIGNED AUTO_INCREMENT,
	title VARCHAR(300) NOT NULL,
	content TEXT NOT NULL,
	createTime DATE,
	author VARCHAR(100),
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户表
--userID
--userName
--emailAdress
--createTime
--password
--loginflag 登录标记 0未登录，1已登录
create table tb_user(
	userId INT UNSIGNED AUTO_INCREMENT not null,
	userName varchar(300),
	emailAddress varchar(100) not null,
	password varchar(300) not null,
	createTime DATE,
	loginflag INT DEFAULT NULL,
	PRIMARY KEY (userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tb_user` VALUES (1, 'yanl', 'yanl.fu@sunyard.com', '-2687473805554003660/', '1970-1-1', 0);
INSERT INTO `tb_user` VALUES (2, 'fu', 'yanl.fu@yahoo.com', '-2687473805554003660/', '1970-1-1', 0);
