package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    
    // Listar todos os produtos de um vendedor específico
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Produto>> listarPorVendedor(@PathVariable Long vendedorId) {
        try {
            List<Produto> produtos = produtoService.listarPorVendedor(vendedorId);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Adicionar um produto ao catálogo de um vendedor
    @PostMapping("/vendedor/{vendedorId}")
    public ResponseEntity<Produto> adicionarProduto(
            @PathVariable Long vendedorId, 
            @RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.adicionarProduto(vendedorId, produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}