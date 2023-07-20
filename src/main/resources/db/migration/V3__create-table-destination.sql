create table destination(

    id bigint not null auto_increment,
    picture LONGBLOB not null,
    name varchar(100) not null,
    price decimal(10,2) not null,
    active boolean not null,

    primary key(id)
);