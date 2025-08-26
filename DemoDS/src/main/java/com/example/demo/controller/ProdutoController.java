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

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


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

    // Listar N produtos do banco (não importa o vendedor). Retornar lista vazia se não houver produtos.
    @GetMapping("/listar/{n}")
    public ResponseEntity<List<Produto>> listarNProdutos(@PathVariable int n) {
        System.out.println("Estou sendo chamado: produtos/listar/" + n);
        try {
            List<Produto> produtos = produtoService.listarNProdutos(n);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
