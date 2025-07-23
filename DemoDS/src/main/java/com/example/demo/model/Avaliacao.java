package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private Integer nota; // Ex.: 1 a 5 estrelas

    @Column(length = 2000)
    private String comentario;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataAvaliacao;

    @Column(nullable = false)
    private Boolean ativo = true; // pode ser usada para "desativar" uma avaliação sem apagar do BD

    // Construtor vazio exigido pelo JPA
    public Avaliacao() {}

    // Construtor auxiliar
    public Avaliacao(Produto produto, Cliente cliente, Integer nota, String comentario) {
        this.produto = produto;
        this.cliente = cliente;
        this.nota = nota;
        this.comentario = comentario;
    }
}