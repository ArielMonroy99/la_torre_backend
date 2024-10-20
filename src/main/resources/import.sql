-- INICIALIZACIÓN DE USUARIO
insert into user_torre (username, password, name, created_by, created_at, status) values ('admin','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'ADMINISTADOR', 'admin', now(),'ACTIVE');
insert into user_torre (username, password, name, created_by, created_at, status) values ('user','$2y$12$ld33IqxZKaHwAS7H5ihpA.Aw6hDqJ4xR1A9taPAXYj3BQnaAs4nIu', 'USER', 'admin', now(),'ACTIVE');
insert into role (role) VALUES  ('ADMINISTRATOR');
insert into role (role) VALUES  ('USER');
insert into user_roles (role_id, user_id) VALUES (1,1);
insert into user_roles (role_id, user_id) VALUES (2,1);
insert into user_roles (role_id, user_id) VALUES (2,2);

-- INICIALIZACIÓN DE CASBIN
insert into casbin_rule (id, ptype,v0,v1,v2) values (1,'p','ADMINISTRATOR','/api/v1/policy','GET|POST|PUT');
insert into casbin_rule (id, ptype,v0,v1,v2) values (2,'p','ADMINISTRATOR','/api/v1/items','GET|POST');
insert into casbin_rule (id, ptype,v0,v1,v2) values (3,'p','ADMINISTRATOR','/api/v1/items/:id','|PUT');
insert into casbin_rule (id, ptype,v0,v1,v2) values (4,'p','ADMINISTRATOR','/api/v1/items/:id/activate','PUT');
insert into casbin_rule (id, ptype,v0,v1,v2) values (5,'p','ADMINISTRATOR','/api/v1/items/:id/inactivate','PUT');

-- INICIALIZACIÓN DE CATEGORIAS

INSERT INTO category (name, description, created_by, updated_by, created_at, updated_at, status) VALUES('Condimentos', 'Condimentos', 'admin', null, now(), null, 'ACTIVE');
INSERT INTO category (name, description, created_by, updated_by, created_at, updated_at, status) VALUES('Masa', 'Masa', 'admin', null, now(), null, 'ACTIVE');
INSERT INTO category (name, description, created_by, updated_by, created_at, updated_at, status) VALUES('Limpieza', 'Masa', 'admin', null, now(), null, 'ACTIVE');
INSERT INTO category (name, description, created_by, updated_by, created_at, updated_at, status) VALUES('Bebidas', 'Bebidas', 'admin', null, now(), null, 'ACTIVE');
INSERT INTO category (name, description, created_by, updated_by, created_at, updated_at, status) VALUES('Queso', 'Queso', 'admin', null, now(), null, 'ACTIVE');


insert into item  (minimum_stock, stock, type, category_id, created_at, updated_at, name, created_by, unit, updated_by, status) VALUES(10,11,'KITCHEN',2, now(), null, 'Harina', 'admin', 'kg' , null, 'ACTIVE');
insert into item  (minimum_stock, stock, type, category_id, created_at, updated_at, name, created_by, unit, updated_by, status) VALUES(10,11,'BEVERAGE',4, now(), null, 'Coca-Cola 500ml', 'admin', 'botella' , null, 'ACTIVE');
insert into item  (minimum_stock, stock, type, category_id, created_at, updated_at, name, created_by, unit, updated_by, status) VALUES(10,11,'CLEANING',3, now(), null, 'Limpia pisos', 'admin', 'botella' , null, 'ACTIVE');



-- REINICIO DE SECUENCIAS
SELECT setval('role_id_seq', (SELECT MAX(id) FROM role));
SELECT setval('user_torre_id_seq',(SELECT MAX(id) FROM user_torre));
SELECT setval('casbin_sequence',(SELECT MAX(id) FROM casbin_rule));
SELECT setval('category_id_seq',(SELECT MAX(id) FROM category));
SELECT setval('item_id_seq',(SELECT MAX(id) FROM item));



