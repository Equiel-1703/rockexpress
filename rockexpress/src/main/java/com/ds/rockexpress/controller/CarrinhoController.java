package com.ds.rockexpress.controller;

import com.ds.rockexpress.model.Carrinho;
import com.ds.rockexpress.model.ItemCarrinho;
import com.ds.rockexpress.model.Produto;
import com.ds.rockexpress.repository.CarrinhoRepository;
import com.ds.rockexpress.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoRepository carrinhoRepo;
    private final ProdutoRepository produtoRepo;

    public CarrinhoController(CarrinhoRepository carrinhoRepo, ProdutoRepository produtoRepo) {
        this.carrinhoRepo = carrinhoRepo;
        this.produtoRepo = produtoRepo;
    }

    @PostMapping
    public Carrinho criar(@RequestBody Carrinho carrinho) {
        for (ItemCarrinho item : carrinho.getItens()) {
            Long produtoId = item.getProduto().getId();
            Produto produto = produtoRepo.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado."));
            item.setProduto(produto);
            item.setCarrinho(carrinho);
        }
        return carrinhoRepo.save(carrinho);
    }

    @GetMapping("/{id}")
    public Carrinho buscar(@PathVariable Long id) {
        return carrinhoRepo.findById(id).orElse(null);
    }
}
