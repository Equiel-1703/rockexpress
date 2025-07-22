create table endereco(
email varchar(50) PK, 
logradouro varchar(20), 
numero varchar(20), 
complemento varchar(20), 
bairro varchar(20), 
cidade varchar(20), 
estado varchar(20), 
cep varchar(8),
);

CREATE TABLE usuario(
nome VARCHAR(50) NOT NULL,
email VARCHAR(50) UNIQUE PRIMARY KEY NOT NULL,
senha VARCHAR(12),
cpf_cnpj VARCHAR(14) UNIQUE,
data_cadastro DATE,
ativo BIT(1) NOT NULL,
perfil ENUM('cliente', 'vendedor', 'administrador')
);

CREATE TABLE usuario_cliente(
cpf VARCHAR(11) UNIQUE PRIMARY KEY
);

CREATE TABLE usuario_vendedor(
cnpj VARCHAR(14) UNIQUE PRIMARY KEY
);


CREATE TABLE usuario_administrador(
cpf VARCHAR(14) UNIQUE PRIMARY KEY,
nivel_acesso INT
);