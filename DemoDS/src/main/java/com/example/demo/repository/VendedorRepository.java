package com.example.demo.repository;

import com.example.demo.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    
    @Query("SELECT v FROM Vendedor v LEFT JOIN FETCH v.produtos WHERE v.id = :vendedorId")
    Optional<Vendedor> findByIdWithProdutos(@Param("vendedorId") Long vendedorId);
}