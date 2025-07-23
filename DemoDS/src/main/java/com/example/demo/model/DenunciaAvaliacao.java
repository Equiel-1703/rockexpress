package com.example.demo.model;

import com.example.demo.enums.StatusDenuncia;
import jakarta.persistence.*;

@Entity
public class DenunciaAvaliacao extends Denuncia {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    public DenunciaAvaliacao() {}

    public DenunciaAvaliacao(Cliente cliente, String motivo, StatusDenuncia status, Avaliacao avaliacao) {
        super(cliente, motivo, status);
        this.avaliacao = avaliacao;
    }

    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }
}
