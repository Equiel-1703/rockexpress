package com.example.demo.controller;

import com.example.demo.model.Vendedor;
import com.example.demo.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;
    
    @GetMapping("/{id}/produtos")
    public ResponseEntity<Vendedor> buscarComProdutos(@PathVariable Long id) {
        try {
            Vendedor vendedor = vendedorService.buscarComProdutos(id);
            return ResponseEntity.ok(vendedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}