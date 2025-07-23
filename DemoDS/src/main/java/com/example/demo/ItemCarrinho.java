package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco; // preço unitário no momento da adição

    // Construtor vazio exigido pelo JPA
    public ItemCarrinho() {}

    // Construtor auxiliar
    public ItemCarrinho(Carrinho carrinho, Produto produto, Integer quantidade, BigDecimal preco) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Integer getQuantidade() { return quantidade; }

    public BigDecimal getPreco() { return preco; }
}
