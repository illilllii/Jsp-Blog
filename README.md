# JSP 블로그 프로젝트

## 환경
- windows
- JDK1.8
- tomcat9.0
- sts tool
- mysql8.0
- postman
- lombok
- gson(json 파싱)
- git

## MySQL 데이터베이스 생성 및 사용자 생성
```sql
create user 'bloguser'@'%' identified by 'bitc5600';
GRANT ALL privileges on *.* TO 'bloguser'@'%';
create database blog;
```

## MySQL 테이블 생성
- bloguser 사용자로 접속
- 데이터베이스 선택 use blog;

### User 테이블
```sql
CREATE TABLE user(
	id int primary key auto_increment,
    username varchar(100) not null unique,
    password varchar(100) not null,
    email varchar(100) not null,
    address varchar(100),
    userRole varchar(20),
    createDate timestamp
) engine=InnoDB default charset=utf8;
```
### Board 테이블
```sql
CREATE TABLE board(
	id int primary key auto_increment,
    userId int,
    title varchar(100) not null,
    content longtext,
    readCount int default 0,
    createDate timestamp,
    foreign key (userId) references user (id)
) engine=InnoDB default charset=utf8;
```

### Reply 테이블
```sql
CREATE TABLE reply(
	id int primary key auto_increment,
    userId int,
    boardId int,
    content varchar(300) not null,
    createDate timestamp,
    foreign key (userId) references user (id) on delete set null,
    foreign key (boardId) references board (id) on delete cascade
) engine=InnoDB default charset=utf8;
```