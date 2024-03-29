create table public.section_group (
                               id int8 generated by default as identity,
                               created_date timestamp,
                               status varchar(255),
                               updated_date timestamp,
                               name varchar(255) not null,
                               primary key (id)
);

-- added group
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Без группы');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Коммутаторы');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Маршрутизаторы');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'ИБП');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Wi-Fi');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'VOIP');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'АТС');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Медный кабель');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'ВОК кабель');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Кабель-канал');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Гофра');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Лоток');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Телекоммуникационные шкафы и стойки');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Патч-панели, 110 кроссы, Krone');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Кроссы');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Прочее оборудование');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Крепеж');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Разъемы');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Работы МКР');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Розетки и розеточные модули');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Патчкорды');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Кроссовое оборудование');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Кросс оптический');
INSERT INTO public.section_group (created_date, status, updated_date, name) VALUES (CURRENT_TIMESTAMP AT TIME ZONE 'Europe/Moscow', 'ACTIVE', null, 'Прочее оборудование');
