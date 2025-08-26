package com.example.demo.model;

import com.example.demo.enums.EnumRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String email;

	private String cpf;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos = new ArrayList<>();

	@Column(nullable = false)
	private String senha;

	@Enumerated(EnumType.STRING)
	private EnumRole nivelAcesso;

	@Column(nullable = false)
	private Boolean ativo;

	// NOVO CAMPO
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime dataCadastro;

	// Construtor com parâmetros (opcional)
	public Usuario(Long id, String nome, String email, String senha, EnumRole nivelAcesso,
				   Boolean ativo, String cpf, List<Endereco> enderecos) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nivelAcesso = nivelAcesso;
		this.ativo = ativo;
		this.cpf = cpf;
		this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
	}

	// Métodos utilitários para endereços
	public void adicionarEndereco(Endereco endereco) {
		if (enderecos == null) {
			enderecos = new ArrayList<>();
		}
		enderecos.add(endereco);
		endereco.setUsuario(this);
	}

	public void removerEndereco(Endereco endereco) {
		if (enderecos != null) {
			enderecos.remove(endereco);
			endereco.setUsuario(null);
		}
	}

	public boolean isAtivo() {
		return ativo != null && ativo;
	}
}
