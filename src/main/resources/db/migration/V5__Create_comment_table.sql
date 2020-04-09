create table comment
(
    id integer auto_increment primary key,
    parent_id integer not null,
    type int not null,
    content varchar(1024) not null,
    commentor int not null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint default 0
);