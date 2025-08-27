package com.example.demo.controller;

import com.example.demo.dto.CarrinhoDTO;
import com.example.demo.dto.CarrinhoResponseDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Produto;
import com.example.demo.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos")
@CrossOrigin(origins = "http://localhost:5173")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // Retorna todo o carrinho de um cliente
    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getCarrinho(@PathVariable Long clienteId) {
        try {
            Carrinho carrinho = carrinhoService.getCarrinhoByClienteId(clienteId);

            // Mapeia itens do carrinho para DTO
            List<CarrinhoResponseDTO.ItemDTO> itensDTO = carrinho.getItens().stream().map(item -> {
                Produto produto = item.getProduto(); // inicializa proxy se necessário
                CarrinhoResponseDTO.ItemDTO dto = new CarrinhoResponseDTO.ItemDTO();
                dto.setProdutoId((Long) produto.getId());
                dto.setNomeProduto(produto.getNome());
                dto.setQuantidade(item.getQuantidade());
                dto.setPreco(item.getPreco());
                return dto;
            }).collect(Collectors.toList());

            // Cria DTO de resposta
            CarrinhoResponseDTO response = new CarrinhoResponseDTO();
            response.setId(carrinho.getId());
            response.setItens(itensDTO);
            response.setValorTotal(carrinho.getValorTotal());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Adiciona um produto ao carrinho
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

            CarrinhoResponseDTO carrinho = carrinhoService.adicionarProduto(
                    clienteId, request.getProdutoId(), request.getQuantidade()
            );

            // Mapeia itens para DTO
            List<CarrinhoResponseDTO.ItemDTO> itensDTO = carrinho.getItens().stream().map(item -> {
                Produto produto = item.getProduto(); // inicializa proxy se necessário
                CarrinhoResponseDTO.ItemDTO dto = new CarrinhoResponseDTO.ItemDTO();
                dto.setProdutoId((Long) produto.getId());
                dto.setNomeProduto(produto.getNome());
                dto.setQuantidade(item.getQuantidade());
                dto.setPreco(item.getPreco());
                return dto;
            }).collect(Collectors.toList());

            // Cria DTO de resposta
            CarrinhoResponseDTO response = new CarrinhoResponseDTO();
            response.setId(carrinho.getId());
            response.setItens(itensDTO);
            response.setValorTotal(carrinho.getValorTotal());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
