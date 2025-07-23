package com.example.demo.enums;

public enum EnumRole {
    ADMIN("Administrador"),
    CLIENTE("Usuário comum"),
    VENDEDOR("Vendedor de produtos");

    private String descricao;

    EnumRole(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
