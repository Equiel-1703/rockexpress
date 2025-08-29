package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer estoque;

    @Lob // Anotação para "Large Object" (Objeto Grande)
    @Column(name = "imagem_base64", columnDefinition="TEXT") // Garante que a coluna no DB seja do tipo TEXT
    private String imagemBase64;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Column(nullable = false)
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    @JsonBackReference
    private Vendedor vendedor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedido;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    // Construtor vazio exigido pelo JPA
    public Produto() {}

    // Construtor com parâmetros úteis
    public Produto(String nome, String descricao, BigDecimal preco, Integer estoque, Boolean ativo, String imagemBase64) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
        this.dataCadastro = LocalDateTime.now();
        this.imagemBase64 = imagemBase64;
    }

    // Métodos de negócio
    public void diminuirEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (this.estoque < quantidade) {
            throw new IllegalStateException("Estoque insuficiente. Disponível: " + this.estoque);
        }
        this.estoque -= quantidade;
    }

    public void aumentarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.estoque += quantidade;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean temEstoqueSuficiente(Integer quantidade) {
        return quantidade != null && quantidade > 0 && this.estoque >= quantidade;
    }

    public BigDecimal calcularValorTotal(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        return this.preco.multiply(BigDecimal.valueOf(quantidade));
    }

    // Métodos de acesso (gerados pelo @Data do Lombok, mas explicitados para clareza)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    // Métodos equals e hashCode (gerados pelo Lombok, mas podem ser customizados se necessário)

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                ", ativo=" + ativo +
                '}';
    }

    public Object getId() {
        return id;
    }
}