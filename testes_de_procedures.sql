-- Teste com sucesso (cliente)
CALL sp_cadastrar_usuario(
    'Maria Silva', 'maria@email.com', 'senha456', 'cliente',
    '98765432109', NULL, NULL,
    'Av. Principal', '200', 'Casa 2', 'Jardim', 'Rio de Janeiro', 'RJ', '20010000',
    @resultado, @usuario_id
);
SELECT @resultado, @usuario_id;

-- Teste com erro (email duplicado)
CALL sp_cadastrar_usuario(
    'Maria Silva', 'maria@email.com', 'outrasenha', 'cliente',
    '11122233344', NULL, NULL,
    NULL, NULL, NULL, NULL, NULL, NULL, NULL,
    @resultado, @usuario_id
);
SELECT @resultado, @usuario_id;

-- Exemplo de uso para remover um usuário (e todos os dados relacionados por cascade)
CALL RemoverDados('usuario', NULL, '11122233344', @mensagem);
SELECT @mensagem;

-- Exemplo para remover um endereço por CPF
CALL RemoverDados('endereco', NULL, '12345678901', @mensagem);
SELECT @mensagem;

-- Exemplo para remover uma categoria
CALL RemoverDados('categoria', 5, NULL, @mensagem);
SELECT @mensagem;

-- Remover usuário cliente por CPF usando a procedure unificada
CALL RemoverDados('usuario_cliente_por_cpf', NULL, '12345678901', @mensagem);
SELECT @mensagem;


