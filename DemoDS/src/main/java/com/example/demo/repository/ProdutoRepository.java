package com.example.demo.repository;

import com.example.demo.model.Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByVendedorId(Long vendedorId);


    List<Produto> findByNomeContainingIgnoreCase(String nome, Sort ordenacao);
}

