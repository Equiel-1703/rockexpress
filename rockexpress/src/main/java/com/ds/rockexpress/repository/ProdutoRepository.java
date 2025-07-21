package com.ds.rockexpress.repository;

import com.ds.rockexpress.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
