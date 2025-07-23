package com.example.demo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    // Construtor vazio exigido pelo JPA
    public Carrinho() {}

    // Construtor auxiliar
    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
    }

     public void recalcularValorTotal() {
        if (itens != null) {
            valorTotal = itens.stream()
                    .map(item -> item.getPreco().multiply(new BigDecimal(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
     }
     
}

