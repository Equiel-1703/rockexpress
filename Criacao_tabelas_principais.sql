create database rockexpress;

CREATE TABLE usuario(
ID BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(25) NOT NULL,
email VARCHAR(50) NOT NULL,
senha VARCHAR(12),
data_cadastro DATE,
ativo BIT(1) NOT NULL,
perfil ENUM('cliente', 'vendedor', 'administrador') NOT NULL
);

CREATE TABLE usuario_cliente(
cliente_id BIGINT PRIMARY KEY,
cpf VARCHAR(11) UNIQUE,
FOREIGN KEY (cliente_id) REFERENCES usuario(ID)
ON UPDATE CASCADE
ON DELETE CASCADE 
);

CREATE TABLE usuario_vendedor(
cliente_id BIGINT PRIMARY KEY,
cnpj VARCHAR(14) UNIQUE,
FOREIGN KEY (cliente_id) REFERENCES usuario(ID) 
ON UPDATE CASCADE
ON DELETE CASCADE 
);

CREATE TABLE usuario_administrador(
cliente_id BIGINT PRIMARY KEY,
cpf VARCHAR(11) UNIQUE,
nivel_acesso INT NOT NULL,
FOREIGN KEY (cliente_id) REFERENCES usuario(ID) 
ON UPDATE CASCADE
ON DELETE CASCADE 
);

CREATE TABLE endereco(
cpf_cliente VARCHAR(11) PRIMARY KEY, 
logradouro VARCHAR(20), 
numero VARCHAR(20), 
complemento VARCHAR(20), 
bairro VARCHAR(20), 
cidade VARCHAR(20), 
estado VARCHAR(20), 
cep VARCHAR(8),
FOREIGN KEY (cpf_cliente) REFERENCES usuario_cliente(cpf) 
ON UPDATE CASCADE
ON DELETE CASCADE 
);

CREATE TABLE categoria(
ID MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(20) UNIQUE NOT NULL,
descricao VARCHAR(50)
);

CREATE TABLE produto (
 ID BIGINT AUTO_INCREMENT PRIMARY KEY,
 vendedor_associado VARCHAR(14) NOT NULL,
 nome VARCHAR(50) NOT NULL,
 descricao VARCHAR(120) NOT NULL,
 preco DECIMAL NOT NULL,
 estoque INT NOT NULL,
 data_cadastro DATE,
 ativo BIT(1) NOT NULL,
 categoria_id MEDIUMINT,
 foreign key (categoria_id) references categoria(ID)
 ON UPDATE CASCADE
 ON DELETE CASCADE,
 FOREIGN KEY (vendedor_associado) REFERENCES usuario_vendedor(cnpj)
 ON UPDATE CASCADE
 ON DELETE CASCADE
);
 
 
CREATE TABLE carrinho(
cpf_cliente VARCHAR(11) PRIMARY KEY,
carrinho_id INT AUTO_INCREMENT UNIQUE,
quantidade_itens INT,
FOREIGN KEY (cpf_cliente) REFERENCES usuario_cliente(cpf)
ON UPDATE CASCADE
ON DELETE CASCADE
);

CREATE TABLE item_carrinho(
carrinho_id INT PRIMARY KEY,
produto_id BIGINT,
quantidade_item INT NOT NULL,
FOREIGN KEY (carrinho_id) REFERENCES carrinho(carrinho_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY (produto_id) REFERENCES produto(ID)
ON UPDATE CASCADE
ON DELETE CASCADE
);

CREATE TABLE pedido(
ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
codigo_pedido VARCHAR(20) UNIQUE NOT NULL,
status ENUM('pendente', 'pago', 'cancelado', 'enviado', 'entregue') NOT NULL,
data DATE NOT NULL,
valor_total DECIMAL NOT NULL
);

CREATE TABLE item_pedido(
pedido_cod VARCHAR(20) PRIMARY KEY,
quantidade_item INT NOT NULL,
FOREIGN KEY (pedido_cod) REFERENCES pedido(codigo_pedido)
ON UPDATE CASCADE
ON DELETE CASCADE
);

 