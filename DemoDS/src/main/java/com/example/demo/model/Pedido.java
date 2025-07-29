package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.example.demo.enums.EnumStatusPedido;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	@Id
	@GeneratedValue () //definir a strategy p criacao de id
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatusPedido status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;
	
	
	public Pedido() {

	}


	public void setStatus(String status2) {	
		try {
			this.status = EnumStatusPedido.valueOf(status2.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Status inválido: " + status2);
		}
		
	}


	public List<ItemPedido> getItens() {
		return this.itens;	
	}
	
	

}
