package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Vendedor extends Usuario {
	
	@Column(unique = true)
	private String cnpj;
	
	@OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
	private List<Produto> produtos;
	
	@OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;
	
	
	public Vendedor() {
		super();
	}
}
