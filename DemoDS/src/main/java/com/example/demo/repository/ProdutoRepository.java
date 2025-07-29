package com.example.demo.repository;

import com.example.demo.model.Produto;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByNomeContainingIgnoreCase(String nome, Sort sort);
}
