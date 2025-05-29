insert into cozinha (nome) VALUES('Tailandesa');
insert into cozinha (nome) VALUES('Japonesa');
insert into cozinha (nome) VALUES('Chilena');
insert into cozinha (nome) VALUES('Mexicana');
insert into cozinha (id, nome) VALUES(5, 'Argentina');
insert into cozinha (id, nome) VALUES(6, 'Brasileira');

insert into estado(nome) values('Pernambuco');
insert into estado(nome) values('Paraiba');
insert into estado(nome) values('Minas Gerais');
insert into estado(nome) values('Alagoas');

insert into cidade(nome, estado_id) values('Recife', 1);
insert into cidade(nome, estado_id) values('Olinda', 1);


insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Thai Gourmet', 10.0, 1, utc_timestamp, utc_timestamp, '50000-000', 'Av. Boa Viagem', '123', 'Apto 101', 'Boa Viagem', 1);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, '50010-000', 'Rua das Flores', '456', null, 'Centro', 2);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Japan Food', 12.50, 2, utc_timestamp, utc_timestamp, '58000-000', 'Av. Epitácio Pessoa', '789', 'Sala 12', 'Tambaú', 2);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Java Stackhouse', 12.0, 3, utc_timestamp, utc_timestamp, '30100-000', 'Rua Java', '101', null, 'Savassi', 2);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Lanchonete do Tio Sam', 11.0, 4, utc_timestamp, utc_timestamp, '57000-000', 'Av. América', '202', 'Loja A', 'Pajuçara', 1);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) values ('Bar da Maria', 6.0, 4, utc_timestamp, utc_timestamp, '57010-000', 'Rua do Sol', '303', null, 'Centro', 1);






insert into forma_pagamento(id, descricao) values(1, 'Cartão de credito');
insert into forma_pagamento(id, descricao) values(2, 'Boleto');
insert into forma_pagamento(id, descricao) values(3, 'Pix');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);




INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Hambúrguer Clássico', 'Pão, carne, queijo e alface', 25.90, true, 1);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 39.90, true, 2);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Suco Natural de Laranja', 'Suco fresco sem adição de açúcar', 8.50, true, 3);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Salada Caesar', 'Alface, frango grelhado, croutons e parmesão', 19.90, true, 4);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Espaguete à Bolonhesa', 'Massa com molho de carne', 29.90, true, 5);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Torta de Limão', 'Sobremesa com creme de limão e cobertura de merengue', 14.00, true, 6);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Coxinha de Frango', 'Coxinha crocante recheada com frango temperado', 6.00, true, 1);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Refrigerante Lata', 'Bebida gaseificada 350ml', 5.50, true, 2);
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ('Brownie com Sorvete', 'Brownie de chocolate com bola de sorvete de creme', 16.00, true, 3);
