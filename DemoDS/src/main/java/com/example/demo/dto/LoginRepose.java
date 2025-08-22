package rockexpress1;

package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private boolean isVendedor;
    
    public LoginResponse(Long id, boolean isVendedor) {
        this.id = id;
        this.isVendedor = isVendedor;
    }
}