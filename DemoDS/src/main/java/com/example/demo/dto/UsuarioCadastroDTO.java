package com.example.demo.dto;

import lombok.Data;

@Data
public class UsuarioCadastroDTO {
    private String tipoConta; // "cliente" ou "vendedor"
    private String nome;
    private String email;
    private String senha;
    private String cpf;  // para cliente
    private String cnpj; // para vendedor
}
