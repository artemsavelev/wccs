create unique index device_name_uindex on device (name);
alter table device add author_id int;

create unique index material_name_uindex on material (name);
alter table material add author_id int;

alter table work add author_id int;