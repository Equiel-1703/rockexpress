package conexao.bd.demo_conexao.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @Column(name = "pedido_cod", length = 20)
    private String pedidoCod;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidadeItem;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pedido_cod")
    private Pedido pedido;
}