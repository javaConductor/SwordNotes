
insert into sword_data.role(name, id) values ('ROLE_USER', 1);
insert into sword_data.role(name, id) values ('ROLE_MANAGER', 2);
insert into sword_data.role(name, id) values ('ROLE_ADMIN', 3);
insert into sword_data.role(name, id) values ('ROLE_SUPER_ADMIN', 4);

insert into sword_data.notes_user(name, password, username, id) values  ('John Travolta',  '$2a$10$kWzAOLBlXD5IjNRFUPqMreL1PCE5zy2RjKmIp96OP/3vwFdlGk26i', 'john', 5);
insert into sword_data.notes_user(name, username, password, id) values  ( 'David Collins', 'dave',' $2a$10$MbqBV56u40LIA6mZIIhiFOT4Pn.68QDIqW8fpLWaIljhyDQBNRE0q', 6);
insert into sword_data.notes_user(name, username, password, id) values  ('Sadar Bahar', 'sadar', '$2a$10$HbpKJgKKj7LF0h2oT7u4suGIUuI6OGwkCEMJGrPU1kVAb410AszEK', 7);

insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (5, 1);
insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (5, 2);
insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (6, 1);
insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (6, 3);
insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (7, 1);
insert into sword_data.notes_user_roles(notes_user_id, roles_id) values (7, 4);
