package com.example.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Usuario{
	
    @Column(nullable = false)
    private Integer nivelAcesso;

    public Administrador() {
        super();
    }
		

}
