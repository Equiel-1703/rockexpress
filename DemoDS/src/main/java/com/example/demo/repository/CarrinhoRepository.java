package com.example.demo.repository;

import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findByCliente(Cliente cliente);

    Optional<Carrinho> findByClienteId(Long clienteId);

    @Query("SELECT c FROM Carrinho c JOIN FETCH c.itens WHERE c.cliente.id = :clienteId")
    Optional<Carrinho> findByClienteIdWithItens(@Param("clienteId") Long clienteId);

    boolean existsByCliente(Cliente cliente);
}