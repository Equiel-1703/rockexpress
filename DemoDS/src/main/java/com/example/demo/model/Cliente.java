package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario {

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL)
    private Carrinho carrinho;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public Cliente() {
        super();
    }

    // ✅ MÉTODO QUE ESTAVA FALTANDO
    public void adicionarEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }
}
