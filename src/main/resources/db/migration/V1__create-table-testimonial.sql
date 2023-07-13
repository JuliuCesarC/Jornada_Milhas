create table client_testimonial(

    id bigint not null auto_increment,
    name varchar(100) not null,
    picture LONGBLOB not null,
    testimonial varchar(400) not null,

    primary key(id)
);