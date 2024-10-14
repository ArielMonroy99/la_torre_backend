-- INICIALIZACIÓN DE USUARIO
insert into user_torre (username, password, name, created_by, created_at) values ('admin','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'ADMINISTADOR', 'admin', now());
insert into user_torre (username, password, name, created_by, created_at) values ('user','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'USER', 'admin', now());
insert into role (role) VALUES  ('ADMINISTRATOR');
insert into role (role) VALUES  ('USER');
insert into user_roles (role_id, user_id) VALUES (1,1);
insert into user_roles (role_id, user_id) VALUES (2,1);
insert into user_roles (role_id, user_id) VALUES (2,2);



insert into casbin_rule (id, ptype,v0,v1,v2) values (1,'p','ADMINISTRATOR','/api/v1/policy','GET');
insert into casbin_rule (id, ptype,v0,v1,v2) values (3,'p','ADMINISTRATOR','/api/v1/policy','POST');

SELECT setval('role_id_seq', COALESCE((SELECT MAX(id) FROM role), 1),false);
SELECT setval('user_torre_id_seq', COALESCE((SELECT MAX(id) FROM user_torre), 1),false);
SELECT setval('casbin_sequence', COALESCE((SELECT MAX(id) FROM casbin_rule), 1),false);