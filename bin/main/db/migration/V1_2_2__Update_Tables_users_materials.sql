-- insert new users
insert into users (created_date, status, updated_date, email, first_name, last_name, password, username, department_id) values ('2021-06-03 09:20:13.34847', 'ACTIVE', '2021-06-03 09:20:13.348478', 'safarov@smart.spb.ru', 'Тимур', 'Сафаров', '$2a$12$7oN4cIVdK2U9H/z3z9Q6BulXFeTiN0yjPzvQoHCy4uh2lZWREbHmy', 'prokilloki', 1);
insert into users (created_date, status, updated_date, email, first_name, last_name, password, username, department_id) values ('2021-06-03 10:17:28.864184', 'ACTIVE', '2021-06-03 10:17:28.864192', 'mpalina@smart.spb.ru', 'Мария', 'Палина', '$2a$12$mXZdoxm24hm2UWQY1EuF2uOFdZ/soL.FceVqjF.JToA37JMEomSPm', 'palina', 1);
insert into users (created_date, status, updated_date, email, first_name, last_name, password, username, department_id) values ('2021-06-03 10:56:25.780588', 'ACTIVE', '2021-06-03 10:56:25.780594', 'balankova@smart.spb.ru', 'Анастасия', 'Баланкова', '$2a$12$hs8zq5Cm.BcP00KQ4tq6hemZKsLR0d0ME4k3P1arhDH2S8R2OjqOS', 'alexin_t', 1);
insert into users (created_date, status, updated_date, email, first_name, last_name, password, username, department_id) values ('2021-06-03 16:06:22.383793', 'ACTIVE', '2021-06-03 16:06:22.383802', 'andrei@smart.spb.ru', 'Андрей', 'Супрунов', '$2a$12$E4P2sr4HEtiP6FfbOLzH3.VVynjxugF1jF4RLxa5TcrcYXhXjrRVq', 'СупруновАА', 2);
insert into users (created_date, status, updated_date, email, first_name, last_name, password, username, department_id) values ('2021-06-03 16:05:24.199257', 'ACTIVE', '2021-06-03 16:05:24.199287', 'bvn@smart.spb.ru', 'Вячеслав', 'Баканов', '$2a$12$cu8Qjfa/2Aib8Ty7pCV6ku.XkrTBW8eF7po9N0jmRWyjSdGYB5kwK', 'БакановВН', 2);

--  users - roles
-- Timur
insert into users_roles (user_id, role_id) values (3, 1);

-- Masha
insert into users_roles (user_id, role_id) values (4, 1);
insert into users_roles (user_id, role_id) values (4, 2);
insert into users_roles (user_id, role_id) values (4, 3);

-- Nastya
insert into users_roles (user_id, role_id) values (5, 1);

-- Andrey, Slava
insert into users_roles (user_id, role_id) values (6, 1);
insert into users_roles (user_id, role_id) values (7, 1);
insert into users_roles (user_id, role_id) values (6, 2);
insert into users_roles (user_id, role_id) values (7, 2);

-- update price in material table
update material set price = '24' where id = 1;