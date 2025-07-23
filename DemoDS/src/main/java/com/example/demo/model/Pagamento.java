package com.example.demo.model;

import com.example.demo.enums.StatusPagamentos;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Column(nullable = false)
    private String metodo; // Ex.: CARTAO, PIX, BOLETO

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamentos status; // Ex.: PENDENTE, PAGO, FALHOU

    // Construtor vazio exigido pelo JPA
    public Pagamento() {}

    // Construtor auxiliar
    public Pagamento(Pedido pedido, String metodo, BigDecimal valor, StatusPagamentos status) {
        this.pedido = pedido;
        this.metodo = metodo;
        this.valor = valor;
        this.status = status;
    }
}