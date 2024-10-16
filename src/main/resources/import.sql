-- INICIALIZACIÓN DE USUARIO
insert into user_torre (username, password, name, created_by, created_at) values ('admin','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'ADMINISTADOR', 'admin', now());
insert into user_torre (username, password, name, created_by, created_at) values ('user','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'USER', 'admin', now());
insert into role (role) VALUES  ('ADMINISTRATOR');
insert into role (role) VALUES  ('USER');
insert into user_roles (role_id, user_id) VALUES (1,1);
insert into user_roles (role_id, user_id) VALUES (2,1);
insert into user_roles (role_id, user_id) VALUES (2,2);

-- INICIALIZACIÓN DE CASBIN
insert into casbin_rule (id, ptype,v0,v1,v2) values (1,'p','ADMINISTRATOR','/api/v1/policy','GET|POST|PUT');





SELECT setval('role_id_seq', (SELECT MAX(id) FROM role));
SELECT setval('user_torre_id_seq',(SELECT MAX(id) FROM user_torre));
SELECT setval('casbin_sequence',(SELECT MAX(id) FROM casbin_rule));




