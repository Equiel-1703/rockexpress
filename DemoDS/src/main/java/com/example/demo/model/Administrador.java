package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario {

    @Column(nullable = false)
    private Integer nivelAcessoEspecial; // Renomeado para evitar conflito

    public Administrador() {
        super();
    }
}
