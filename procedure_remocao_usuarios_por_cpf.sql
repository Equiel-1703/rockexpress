DELIMITER //

CREATE PROCEDURE RemoverUsuarioPorCPF(
    IN p_cpf VARCHAR(11),
    OUT p_mensagem VARCHAR(100)
)
BEGIN
    DECLARE v_cliente_id BIGINT;
    DECLARE v_count INT;
    
    -- Verificar se o CPF existe na tabela usuario_cliente
    SELECT COUNT(*) INTO v_count FROM usuario_cliente WHERE cpf = p_cpf;
    
    IF v_count = 0 THEN
        SET p_mensagem = CONCAT('Erro: Nenhum cliente encontrado com o CPF ', p_cpf);
    ELSE
        -- Obter o ID do usuário associado ao CPF
        SELECT cliente_id INTO v_cliente_id FROM usuario_cliente WHERE cpf = p_cpf;
        
        -- Remover o usuário (o CASCADE vai remover automaticamente das tabelas relacionadas)
        DELETE FROM usuario WHERE ID = v_cliente_id;
        
        -- Verificar se a remoção foi bem-sucedida
        IF ROW_COUNT() > 0 THEN
            SET p_mensagem = CONCAT('Usuário com CPF ', p_cpf, ' removido com sucesso.');
        ELSE
            SET p_mensagem = CONCAT('Erro: Não foi possível remover o usuário com CPF ', p_cpf);
        END IF;
    END IF;
END //

DELIMITER ;