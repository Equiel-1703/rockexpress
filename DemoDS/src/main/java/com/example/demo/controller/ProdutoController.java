package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoService.listarTodos();
    }
    
    @GetMapping("/buscar")
    public List<Produto> buscarPorNomeComOrdenacao(
        @RequestParam String nome,
        @RequestParam(required = false) String sort) {
        return produtoService.buscarPorNomeComOrdenacao(nome, sort);
    }

    @GetMapping("/ordenar")
    public List<Produto> listarOrdenado(@RequestParam String sort) {
        return produtoService.listarOrdenado(sort);
    }

    @GetMapping("/{id}")
    public Optional<Produto> buscar(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletar(id);
    }
}
