create table notification
(
    id integer auto_increment primary key,
    notifier integer not null ,
    notifier_name varchar(100),
    receiver integer not null,
    outer_id integer not null,
    outer_title varchar(50),
    type integer not null,
    gmt_create bigint not null,
    status integer default 0 not null
);