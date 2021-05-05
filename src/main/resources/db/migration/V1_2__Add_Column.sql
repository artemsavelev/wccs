alter table public.material ADD COLUMN section_group_id int8;

alter table if exists material add constraint FK_section_group foreign key (section_group_id) references section_group;