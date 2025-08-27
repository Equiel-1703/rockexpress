package com.example.demo.dto;

import com.example.demo.model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CarrinhoResponseDTO {

    private Long id;
    private List<ItemDTO> itens;
    private BigDecimal valorTotal;

    @Getter
    @Setter
    public static class ItemDTO {
        private Long produtoId;
        private String nomeProduto;
        private Integer quantidade;
        private BigDecimal preco;

        public Produto getProduto() {
            Produto produto = new Produto();
            produto.setId(produtoId);
            produto.setNome(nomeProduto);
            produto.setPreco(preco);
            return produto;
        }
    }
}
