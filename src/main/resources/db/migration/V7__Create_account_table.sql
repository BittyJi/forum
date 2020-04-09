create table account
(
    id integer auto_increment primary key,
    user_name varchar(50) not null,
    user_pwd varchar(255) not null,
    email varchar(255),
    sex integer,
    iphone varchar(50),
    gmt_create bigint,
    gmt_modified bigint
);