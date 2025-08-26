package com.example.demo.controller;

import com.example.demo.dto.CarrinhoDTO;
import com.example.demo.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
@CrossOrigin(origins = "http://localhost:5173") // garante que o React consiga acessar
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getCarrinho(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(carrinhoService.getCarrinhoByClienteId(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{clienteId}/adicionar")
    public ResponseEntity<?> adicionarAoCarrinho(
            @PathVariable("clienteId") Long clienteId,
            @RequestBody CarrinhoDTO request) {

        System.out.println("➡️ Recebido no backend: produtoId=" + request.getProdutoId() +
                ", quantidade=" + request.getQuantidade());

        try {
            if (request.getProdutoId() == null || request.getQuantidade() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Campos 'produtoId' e 'quantidade' são obrigatórios.");
            }

            return ResponseEntity.ok(
                    carrinhoService.adicionarProduto(clienteId, request.getProdutoId(), request.getQuantidade())
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
