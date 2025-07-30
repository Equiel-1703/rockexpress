package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 1000)
    private String descricao;


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Produto> produtos;

    // Construtor vazio exigido pelo JPA
    public Categoria() {}

    // Construtor auxiliar
    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}