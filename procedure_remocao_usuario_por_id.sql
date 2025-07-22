DELIMITER //

CREATE PROCEDURE RemoverDados(
    IN p_tabela VARCHAR(50),
    IN p_id BIGINT,
    IN p_identificador VARCHAR(50), -- Pode ser CPF, CNPJ ou outro identificador
    OUT p_mensagem VARCHAR(100)
    )
BEGIN
    DECLARE v_tipo_usuario ENUM('cliente', 'vendedor', 'administrador');
    DECLARE v_count INT;
    
    -- Verificar se a tabela existe
    SELECT COUNT(*) INTO v_count FROM information_schema.tables 
    WHERE table_schema = DATABASE() AND table_name = p_tabela;
    
    IF v_count = 0 THEN
        SET p_mensagem = 'Erro: Tabela não encontrada.';
    ELSE
        -- Remoção baseada na tabela especificada
        CASE p_tabela
            WHEN 'usuario' THEN
                -- Verificar o tipo de usuário antes de remover
                SELECT perfil INTO v_tipo_usuario FROM usuario WHERE ID = p_id;
                
                IF v_tipo_usuario IS NOT NULL THEN
                    DELETE FROM usuario WHERE ID = p_id;
                    SET p_mensagem = CONCAT('Usuário ', p_id, ' (', v_tipo_usuario, ') removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Usuário com ID ', p_id, ' não encontrado.');
                END IF;
                
            WHEN 'usuario_cliente' THEN
                -- Verificar se o cliente existe
                SELECT COUNT(*) INTO v_count FROM usuario_cliente WHERE cliente_id = p_id;
                
                IF v_count > 0 THEN
                    DELETE FROM usuario WHERE ID = p_id; -- Cascade vai remover da usuario_cliente
                    SET p_mensagem = CONCAT('Cliente ', p_id, ' removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Cliente com ID ', p_id, ' não encontrado.');
                END IF;
                
            WHEN 'usuario_vendedor' THEN
                -- Verificar se o vendedor existe
                SELECT COUNT(*) INTO v_count FROM usuario_vendedor WHERE cliente_id = p_id;
                
                IF v_count > 0 THEN
                    -- Primeiro remover produtos associados ao vendedor
                    DELETE FROM produto WHERE vendedor_associado IN 
                        (SELECT cnpj FROM usuario_vendedor WHERE cliente_id = p_id);
                    
                    DELETE FROM usuario WHERE ID = p_id; -- Cascade vai remover da usuario_vendedor
                    SET p_mensagem = CONCAT('Vendedor ', p_id, ' e seus produtos removidos com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Vendedor com ID ', p_id, ' não encontrado.');
                END IF;
                
            WHEN 'usuario_administrador' THEN
                -- Verificar se o administrador existe
                SELECT COUNT(*) INTO v_count FROM usuario_administrador WHERE cliente_id = p_id;
                
                IF v_count > 0 THEN
                    DELETE FROM usuario WHERE ID = p_id; -- Cascade vai remover da usuario_administrador
                    SET p_mensagem = CONCAT('Administrador ', p_id, ' removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Administrador com ID ', p_id, ' não encontrado.');
                END IF;
                
            WHEN 'endereco' THEN
                -- Remover por CPF do cliente
                DELETE FROM endereco WHERE cpf_cliente = p_identificador;
                
                IF ROW_COUNT() > 0 THEN
                    SET p_mensagem = CONCAT('Endereço do cliente com CPF ', p_identificador, ' removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Endereço para CPF ', p_identificador, ' não encontrado.');
                END IF;
                
            WHEN 'categoria' THEN
                -- Verificar se a categoria existe
                SELECT COUNT(*) INTO v_count FROM categoria WHERE ID = p_id;
                
                IF v_count > 0 THEN
                    -- Cascade vai remover os produtos associados
                    DELETE FROM categoria WHERE ID = p_id;
                    SET p_mensagem = CONCAT('Categoria ', p_id, ' e produtos associados removidos com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Categoria com ID ', p_id, ' não encontrada.');
                END IF;
                
            WHEN 'produto' THEN
                -- Verificar se o produto existe
                SELECT COUNT(*) INTO v_count FROM produto WHERE ID = p_id;
                
                IF v_count > 0 THEN
                    -- Primeiro remover itens de carrinho associados
                    DELETE FROM item_carrinho WHERE produto_id = p_id;
                    DELETE FROM produto WHERE ID = p_id;
                    SET p_mensagem = CONCAT('Produto ', p_id, ' removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Produto com ID ', p_id, ' não encontrado.');
                END IF;
                
            WHEN 'pedido' THEN
                -- Verificar se o pedido existe
                SELECT COUNT(*) INTO v_count FROM pedido WHERE ID = p_id;
                
                IF v_count > 0 THEN
                    -- Cascade vai remover os itens do pedido
                    DELETE FROM pedido WHERE ID = p_id;
                    SET p_mensagem = CONCAT('Pedido ', p_id, ' removido com sucesso.');
                ELSE
                    SET p_mensagem = CONCAT('Erro: Pedido com ID ', p_id, ' não encontrado.');
                END IF;
                
            ELSE
                SET p_mensagem = 'Erro: Operação para esta tabela não implementada.';
        END CASE;
    END IF;
END //

DELIMITER ;