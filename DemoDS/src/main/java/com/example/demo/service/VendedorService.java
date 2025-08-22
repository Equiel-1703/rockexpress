package com.example.demo.service;

import com.example.demo.model.Vendedor;
import com.example.demo.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;
    
    public Vendedor buscarPorId(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
    }
    
    public Vendedor buscarComProdutos(Long id) {
        return vendedorRepository.findByIdWithProdutos(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
    }
}