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

 CREATE TABLE produto (
 ID BIGINT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(50) NOT NULL,
 descricao VARCHAR(120) NOT NULL,
 preco DECIMAL NOT NULL,
 estoque INT NOT NULL,
 data_cadastro DATE,
 ativo BIT(1) NOT NULL
 );
 
 CREATE TABLE categoria(
ID MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(20) UNIQUE NOT NULL,
descricao VARCHAR(50)
);

CREATE TABLE carrinho(
email_cliente VARCHAR(50),
carrinho_id INT UNIQUE,
quantidade_itens INT
);

CREATE TABLE item_carrinho(
carrinho_id INT PRIMARY KEY,
quantidade_item INT NOT NULL
);

CREATE TABLE pedido(
ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
codigo_pedido VARCHAR(20) UNIQUE NOT NULL,
status ENUM('ativo', 'finalizado', 'em andamento') NOT NULL,
data DATE NOT NULL,
valor_total DECIMAL NOT NULL
);

CREATE TABLE item_pedido(
id_pedido BIGINT PRIMARY KEY,
quantidade_item INT NOT NULL
);

 