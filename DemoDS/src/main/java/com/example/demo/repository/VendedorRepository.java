package com.example.demo.repository;

import com.example.demo.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Vendedor findByEmail(String email);

    List<Vendedor> findByAtivoTrue();

    @Query("SELECT v FROM Vendedor v LEFT JOIN FETCH v.produtos WHERE v.id = :id")
    Optional<Vendedor> findByIdWithProdutos(@Param("id") Long id);
}