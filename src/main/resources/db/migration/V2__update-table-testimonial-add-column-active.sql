alter table client_testimonial add active boolean;
update client_testimonial set active = true where id > 0;
alter table client_testimonial change active active boolean not null;