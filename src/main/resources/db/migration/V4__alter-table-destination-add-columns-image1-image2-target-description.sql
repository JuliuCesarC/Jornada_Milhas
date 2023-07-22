alter table destination drop column price;
alter table destination change picture image_one LONGBLOB not null;
alter table destination add image_two LONGBLOB;
alter table destination add target varchar(160) not null;
alter table destination add description varchar(220);
