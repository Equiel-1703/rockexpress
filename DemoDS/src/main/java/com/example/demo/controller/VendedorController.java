package com.example.demo.controller;

import com.example.demo.model.Vendedor;
import com.example.demo.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @PostMapping
    public ResponseEntity<Vendedor> criarVendedor(@RequestBody Vendedor vendedor) {
        try {
            Vendedor novoVendedor = vendedorService.criarVendedor(vendedor);
            return ResponseEntity.ok(novoVendedor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> buscarPorId(@PathVariable Long id) {
        Vendedor vendedor = vendedorService.buscarPorId(id);
        if (vendedor != null) {
            return ResponseEntity.ok(vendedor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> listarTodos() {
        List<Vendedor> vendedores = vendedorService.listarTodos();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/{id}/produtos")
    public ResponseEntity<Vendedor> buscarComProdutos(@PathVariable Long id) {
        try {
            Vendedor vendedor = vendedorService.buscarComProdutos(id);
            return ResponseEntity.ok(vendedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> atualizarVendedor(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        Vendedor vendedorAtualizado = vendedorService.atualizarVendedor(id, vendedor);
        if (vendedorAtualizado != null) {
            return ResponseEntity.ok(vendedorAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVendedor(@PathVariable Long id) {
        vendedorService.deletarVendedor(id);
        return ResponseEntity.noContent().build();
    }
}