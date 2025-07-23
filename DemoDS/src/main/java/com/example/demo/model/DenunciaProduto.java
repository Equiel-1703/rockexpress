package com.example.demo.model;

import com.example.demo.enums.StatusDenuncia;
import jakarta.persistence.*;

@Entity
public class DenunciaProduto extends Denuncia {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public DenunciaProduto() {}

    public DenunciaProduto(Cliente cliente, String motivo, StatusDenuncia status, Produto produto) {
        super(cliente, motivo, status);
        this.produto = produto;
    }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}
