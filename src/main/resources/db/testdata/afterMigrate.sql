set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

insert into cozinha (id, nome) VALUES(1, 'Tailandesa');
insert into cozinha (id, nome) VALUES(2, 'Japonesa');
insert into cozinha (id, nome) VALUES(3, 'Chilena');
insert into cozinha (id, nome) VALUES(4, 'Mexicana');
insert into cozinha (id, nome) VALUES(5, 'Argentina');
insert into cozinha (id, nome) VALUES(6, 'Brasileira');

insert into estado(id, nome) values(1, 'Pernambuco');
insert into estado(id, nome) values(2, 'Paraiba');
insert into estado(id, nome) values(3, 'Minas Gerais');
insert into estado(id, nome) values(4, 'Alagoas');

insert into cidade(id, nome, estado_id) values(1, 'Recife', 1);
insert into cidade(id, nome, estado_id) values(2, 'Olinda', 1);


insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (1, 'Thai Gourmet', 10.0, 1, utc_timestamp, utc_timestamp, '50000-000', 'Av. Boa Viagem', '123', 'Apto 101', 'Boa Viagem', 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, '50010-000', 'Rua das Flores', '456', null, 'Centro', 2);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (3, 'Japan Food', 12.50, 2, utc_timestamp, utc_timestamp, '58000-000', 'Av. Epitácio Pessoa', '789', 'Sala 12', 'Tambaú', 2);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (4, 'Java Stackhouse', 12.0, 3, utc_timestamp, utc_timestamp, '30100-000', 'Rua Java', '101', null, 'Savassi', 2);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (5, 'Lanchonete do Tio Sam', 11.0, 4, utc_timestamp, utc_timestamp, '57000-000', 'Av. América', '202', 'Loja A', 'Pajuçara', 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values (6, 'Bar da Maria', 6.0, 4, utc_timestamp, utc_timestamp, '57010-000', 'Rua do Sol', '303', null, 'Centro', 1);






insert into forma_pagamento(id, descricao) values(1, 'Cartão de credito');
insert into forma_pagamento(id, descricao) values(2, 'Boleto');
insert into forma_pagamento(id, descricao) values(3, 'Pix');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);




INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (1, 'Hambúrguer Clássico', 'Pão, carne, queijo e alface', 25.90, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (2, 'Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 39.90, true, 2);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (3, 'Suco Natural de Laranja', 'Suco fresco sem adição de açúcar', 8.50, true, 3);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (4, 'Salada Caesar', 'Alface, frango grelhado, croutons e parmesão', 19.90, true, 4);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (5, 'Espaguete à Bolonhesa', 'Massa com molho de carne', 29.90, true, 5);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (6, 'Torta de Limão', 'Sobremesa com creme de limão e cobertura de merengue', 14.00, true, 6);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (7, 'Coxinha de Frango', 'Coxinha crocante recheada com frango temperado', 6.00, true, 1);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (8, 'Refrigerante Lata', 'Bebida gaseificada 350ml', 5.50, true, 2);
INSERT INTO produto (id, nome, descricao, preco, ativo, restaurante_id) VALUES (9, 'Brownie com Sorvete', 'Brownie de chocolate com bola de sorvete de creme', 16.00, true, 3);

