package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProdutoCreateDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private Boolean ativo;
    private String imagemBase64;
//    private String tamanho;
}