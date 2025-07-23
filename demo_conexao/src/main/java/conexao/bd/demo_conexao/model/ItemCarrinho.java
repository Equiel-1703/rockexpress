package conexao.bd.demo_conexao.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_carrinho")
public class ItemCarrinho {
    @Id
    @Column(name = "carrinho_id")
    private Integer carrinhoId;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidadeItem;

    @OneToOne
    @MapsId
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;
}
