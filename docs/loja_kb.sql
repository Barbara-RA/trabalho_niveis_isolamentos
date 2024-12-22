DROP DATABASE loja_kb;

CREATE DATABASE loja_kb;


USE loja_kb;


CREATE TABLE produtos (
    ProdutoID INT AUTO_INCREMENT PRIMARY KEY,
    ProdutoNome VARCHAR(255) NOT NULL, -- Ajustado para permitir até 255 caracteres
    preco DOUBLE(10, 2) NOT NULL,
    UnidadesEmEstoque SMALLINT NOT NULL
);


-- Inserção de produtos no estoque
INSERT INTO produtos (ProdutoNome, preco, UnidadesEmEstoque)
VALUES 
    ('Placa Sapphire R9 270X 2GB', 715.90, 62),
    ('Placa Sapphire R9 270 2GB', 709.90, 38),
    ('PlayStation 5', 1999.99, 1000);



CREATE TABLE pedidos (
    PedidoID INT AUTO_INCREMENT PRIMARY KEY,
    ClienteID CHAR(5) NOT NULL,
    dataPedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE detalhes_pedido (
    DetalheID INT AUTO_INCREMENT PRIMARY KEY,
    PedidoID INT NOT NULL,
    ProdutoID INT NOT NULL,
    precoVenda DOUBLE(10, 2) NOT NULL,
    quantidade SMALLINT NOT NULL,
    desconto DOUBLE(10, 2) DEFAULT 0,
    FOREIGN KEY (PedidoID) REFERENCES pedidos (PedidoID),
    FOREIGN KEY (ProdutoID) REFERENCES produtos (ProdutoID)
);

-- Criação da tabela de clientes
CREATE TABLE clientes (
    ClienteID CHAR(5) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL
);


INSERT INTO clientes (ClienteID, nome, email, telefone)
VALUES
    ('C001', 'João Silva', 'joao.silva@email.com', '11999999999'),
    ('C002', 'Barbara Ramos', 'barbara.ramos@email.com', '21988888888'),
    ('C003', 'Lucio Costa', 'lucio.costa@email.com', '31977777777'),
    ('C004', 'Ana Souza', 'ana.souza@email.com', '41966666666'),
    ('C005', 'Lucas Lima', 'lucas.lima@email.com', '51955555555');
SELECT * FROM clientes;




