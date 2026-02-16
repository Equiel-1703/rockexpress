package com.example.demo.model;

import com.example.demo.enums.EnumRole;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // cada subclasse terá sua própria tabela

public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING) //
    private EnumRole nivelAcesso;

    @Column(nullable=false)
    private Boolean ativo;


    public Usuario() {}

    public Usuario (Long id, String email, String senha, LocalDateTime dataCadastro, EnumRole nivelAcesso, Boolean ativo) {
        this.id = id;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.nivelAcesso = nivelAcesso;
        this.ativo = ativo;
    }

    public String getSenha() {
        return this.senha;
    }
}

