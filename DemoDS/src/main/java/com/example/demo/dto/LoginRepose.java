package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginRepose {
    private Long id;
    private boolean isVendedor;
    
    public LoginRepose(Long id, boolean isVendedor) {
        this.id = id;
        this.isVendedor = isVendedor;
    }
}