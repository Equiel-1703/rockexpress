package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Vendedor extends Usuario {

    @Column(unique = true)
    private String cnpj;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Produto> produtos;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Vendedor() {
        super();
    }
}
