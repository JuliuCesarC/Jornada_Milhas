create;

-- FLY WAY --------------

SELECT * FROM flyway_schema_history;

delete from flyway_schema_history where installed_rank = 7;

delete from flyway_schema_history where success = 0;

-- Testmonial --------------

select * from client_testimonial;

delete from client_testimonial where id > 0;