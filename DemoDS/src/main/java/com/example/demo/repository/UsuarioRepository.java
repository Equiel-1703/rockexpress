package com.example.demo.repository;

import com.example.demo.model.Cliente;
import com.example.demo.model.Usuario;
import com.example.demo.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Cliente findByCpf(String cpf);
    Vendedor findByCnpj(String cnpj);
}
