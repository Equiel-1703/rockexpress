package com.ds.rockexpress.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private String tamanhoSelecionado;

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    @JsonBackReference
    private Carrinho carrinho;

    // Construtor padrão exigido pelo JPA
    public ItemCarrinho() {}

    // Construtor com campos obrigatórios
    public ItemCarrinho(Produto produto, int quantidade, String tamanhoSelecionado) {
        setProduto(produto);
        setQuantidade(quantidade);
        setTamanhoSelecionado(tamanhoSelecionado);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantidade = quantidade;
    }

    public String getTamanhoSelecionado() {
        return tamanhoSelecionado;
    }

    public void setTamanhoSelecionado(String tamanhoSelecionado) {
        Objects.requireNonNull(tamanhoSelecionado, "Tamanho selecionado não pode ser nulo");
        this.tamanhoSelecionado = tamanhoSelecionado;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    // Métodos utilitários
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCarrinho that = (ItemCarrinho) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemCarrinho{" +
                "id=" + id +
                ", produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                ", tamanhoSelecionado='" + tamanhoSelecionado + '\'' +
                '}';
    }
}