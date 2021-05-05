alter table public.device ADD COLUMN section_group_id int8 default 1;
alter table public.material ADD COLUMN section_group_id int8 default 1;
alter table public.work ADD COLUMN section_group_id int8 default 1;

alter table if exists device add constraint FK_group_device foreign key (section_group_id) references section_group;
alter table if exists material add constraint FK_group_material foreign key (section_group_id) references section_group;
alter table if exists work add constraint FK_group_work foreign key (section_group_id) references section_group;