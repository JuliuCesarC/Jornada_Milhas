create;

-- FLY WAY --------------

SELECT * FROM flyway_schema_history;

delete from flyway_schema_history where installed_rank = 5;

delete from flyway_schema_history where success = 0;

-- Testmonial --------------

select * from client_testimonial;

delete from client_testimonial where id > 0;

-- Destination --------------

SELECT * FROM destination;

SELECT * FROM destination WHERE name LIKE 'natal r%';



alter table destination add price boolean;
alter table destination change image_1 picture LONGBLOB not null;
alter table destination drop column image_2;
alter table destination drop column target;
alter table destination drop column description;

alter table destination change destination_description description varchar(220);

