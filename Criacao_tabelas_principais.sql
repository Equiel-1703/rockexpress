CREATE TABLE usuario(
nome VARCHAR(50) NOT NULL,
email VARCHAR(50) PRIMARY KEY NOT NULL,
senha VARCHAR(12),
cpf_cnpj VARCHAR(14) UNIQUE,
data_cadastro DATE,
ativo BIT(1) NOT NULL,
perfil ENUM('cliente', 'vendedor', 'administrador')
);

create table endereco(
email varchar(50) primary KEY, 
logradouro varchar(20), 
numero varchar(20), 
complemento varchar(20), 
bairro varchar(20), 
cidade varchar(20), 
estado varchar(20), 
cep varchar(8)
);



CREATE TABLE categoria(
ID MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(20) UNIQUE NOT NULL,
descricao VARCHAR(50)
);

 CREATE TABLE produto (
 ID BIGINT AUTO_INCREMENT PRIMARY KEY,
 nome VARCHAR(50) NOT NULL,
 descricao VARCHAR(120) NOT NULL,
 preco DECIMAL NOT NULL,
 estoque INT NOT NULL,
 data_cadastro DATE,
 ativo BIT(1) NOT NULL,
 categoria_item VARCHAR(20) NOT NULL,
 foreign key (categoria_item) references categoria(nome)
 );
 
 

CREATE TABLE carrinho(
email_cliente VARCHAR(50),
carrinho_id INT UNIQUE,
quantidade_itens INT,
foreign key (email_cliente) references usuario(email)
);

CREATE TABLE item_carrinho(
carrinho_id INT PRIMARY KEY,
quantidade_item INT NOT NULL,
foreign key (carrinho_id) references carrinho(carrinho_id)
);

CREATE TABLE pedido(
ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
codigo_pedido VARCHAR(20) UNIQUE NOT NULL,
status ENUM('ativo', 'finalizado', 'em andamento') NOT NULL,
data DATE NOT NULL,
valor_total DECIMAL NOT NULL
);

CREATE TABLE item_pedido(
pedido VARCHAR(20) PRIMARY KEY,
quantidade_item INT NOT NULL,
foreign key (pedido) references pedido(codigo_pedido)
);

 