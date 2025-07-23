package com.example.demo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario{
	
	@Column(unique = true)
	private String cpf;

	@OneToOne(cascade = CascadeType.ALL )
	private Carrinho carrinho;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos;
	
	@OneToMany(mappedBy="cliente")
	private List<Avaliacao> avaliacoes;
	
	public Cliente(){
		super();
	}

}
