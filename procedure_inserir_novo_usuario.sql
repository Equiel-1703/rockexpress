DELIMITER //

CREATE PROCEDURE sp_cadastrar_usuario(
    IN p_nome VARCHAR(25),
    IN p_email VARCHAR(50),
    IN p_senha VARCHAR(12),
    IN p_perfil ENUM('cliente', 'vendedor', 'administrador'),
    IN p_cpf VARCHAR(11),
    IN p_cnpj VARCHAR(14),
    IN p_nivel_acesso INT,
    IN p_logradouro VARCHAR(20),
    IN p_numero VARCHAR(20),
    IN p_complemento VARCHAR(20),
    IN p_bairro VARCHAR(20),
    IN p_cidade VARCHAR(20),
    IN p_estado VARCHAR(20),
    IN p_cep VARCHAR(8),
    OUT p_resultado VARCHAR(200),
    OUT p_usuario_id BIGINT
)
proc_label: BEGIN
    DECLARE v_usuario_id BIGINT DEFAULT 0;
    DECLARE v_email_existe INT DEFAULT 0;
    DECLARE v_cpf_existe INT DEFAULT 0;
    DECLARE v_cnpj_existe INT DEFAULT 0;
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        GET DIAGNOSTICS CONDITION 1
        @sqlstate = RETURNED_SQLSTATE, @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
        SET p_resultado = CONCAT('Erro: ', @errno, ' (', @sqlstate, '): ', @text);
        SET p_usuario_id = -1;
    END;
    
    -- Inicializa parâmetros de saída
    SET p_resultado = '';
    SET p_usuario_id = -1;
    
    -- Validações iniciais
    IF p_nome IS NULL OR p_email IS NULL OR p_senha IS NULL OR p_perfil IS NULL THEN
        SET p_resultado = 'Dados obrigatórios não informados';
        LEAVE proc_label;
    END IF;
    
    -- Verifica unicidade do email
    SELECT COUNT(*) INTO v_email_existe FROM usuario WHERE email = p_email;
    IF v_email_existe > 0 THEN
        SET p_resultado = 'Email já cadastrado';
        LEAVE proc_label;
    END IF;
    
    START TRANSACTION;
    
    -- Insere na tabela principal de usuários
    INSERT INTO usuario (nome, email, senha, data_cadastro, ativo, perfil)
    VALUES (p_nome, p_email, p_senha, CURDATE(), 1, p_perfil);
    
    SET v_usuario_id = LAST_INSERT_ID();
    SET p_usuario_id = v_usuario_id;
    
    -- Insere na tabela específica conforme o perfil
    IF p_perfil = 'cliente' THEN
        -- Valida CPF para cliente
        IF p_cpf IS NULL THEN
            SET p_resultado = 'CPF obrigatório para cliente';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        SELECT COUNT(*) INTO v_cpf_existe FROM usuario_cliente WHERE cpf = p_cpf;
        IF v_cpf_existe > 0 THEN
            SET p_resultado = 'CPF já cadastrado';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        INSERT INTO usuario_cliente (cliente_id, cpf)
        VALUES (v_usuario_id, p_cpf);
        
        -- Cadastra endereço se informado
        IF p_logradouro IS NOT NULL AND p_cep IS NOT NULL THEN
            INSERT INTO endereco (cpf_cliente, logradouro, numero, complemento, bairro, cidade, estado, cep)
            VALUES (p_cpf, p_logradouro, p_numero, p_complemento, p_bairro, p_cidade, p_estado, p_cep);
        END IF;
        
        -- Cria carrinho para o cliente
        INSERT INTO carrinho (cpf_cliente, quantidade_itens)
        VALUES (p_cpf, 0);
        
        SET p_resultado = 'Cliente cadastrado com sucesso';
        
    ELSEIF p_perfil = 'vendedor' THEN
        -- Valida CNPJ para vendedor
        IF p_cnpj IS NULL THEN
            SET p_resultado = 'CNPJ obrigatório para vendedor';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        SELECT COUNT(*) INTO v_cnpj_existe FROM usuario_vendedor WHERE cnpj = p_cnpj;
        IF v_cnpj_existe > 0 THEN
            SET p_resultado = 'CNPJ já cadastrado';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        INSERT INTO usuario_vendedor (cliente_id, cnpj)
        VALUES (v_usuario_id, p_cnpj);
        
        SET p_resultado = 'Vendedor cadastrado com sucesso';
        
    ELSEIF p_perfil = 'administrador' THEN
        -- Valida CPF para administrador
        IF p_cpf IS NULL THEN
            SET p_resultado = 'CPF obrigatório para administrador';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        SELECT COUNT(*) INTO v_cpf_existe FROM usuario_administrador WHERE cpf = p_cpf;
        IF v_cpf_existe > 0 THEN
            SET p_resultado = 'CPF já cadastrado para administrador';
            ROLLBACK;
            LEAVE proc_label;
        END IF;
        
        INSERT INTO usuario_administrador (cliente_id, cpf, nivel_acesso)
        VALUES (v_usuario_id, p_cpf, IFNULL(p_nivel_acesso, 1));
        
        SET p_resultado = 'Administrador cadastrado com sucesso';
        
    ELSE
        SET p_resultado = 'Perfil inválido';
        ROLLBACK;
        LEAVE proc_label;
    END IF;
    
    COMMIT;
END //

DELIMITER ;