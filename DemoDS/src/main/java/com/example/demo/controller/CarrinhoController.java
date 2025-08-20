package com.example.demo.controller;

import com.example.demo.model.Carrinho;
import com.example.demo.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Carrinho> getCarrinho(@PathVariable Long clienteId) {
        try {
            Carrinho carrinho = carrinhoService.getCarrinhoByClienteId(clienteId);
            return ResponseEntity.ok(carrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cliente/{clienteId}/adicionar")
    public ResponseEntity<Carrinho> adicionarItem(
            @PathVariable Long clienteId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {

        try {
            Carrinho carrinho = carrinhoService.adicionarItem(clienteId, produtoId, quantidade);
            return ResponseEntity.ok(carrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/cliente/{clienteId}/item/{itemId}")
    public ResponseEntity<Carrinho> atualizarQuantidade(
            @PathVariable Long clienteId,
            @PathVariable Long itemId,
            @RequestParam Integer quantidade) {

        try {
            Carrinho carrinho = carrinhoService.atualizarQuantidadeItem(clienteId, itemId, quantidade);
            return ResponseEntity.ok(carrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/cliente/{clienteId}/item/{itemId}")
    public ResponseEntity<Carrinho> removerItem(
            @PathVariable Long clienteId,
            @PathVariable Long itemId) {

        try {
            Carrinho carrinho = carrinhoService.removerItem(clienteId, itemId);
            return ResponseEntity.ok(carrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/cliente/{clienteId}/limpar")
    public ResponseEntity<Void> limparCarrinho(@PathVariable Long clienteId) {
        try {
            carrinhoService.limparCarrinho(clienteId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}