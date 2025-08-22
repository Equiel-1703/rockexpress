package com.example.demo.controller;

import com.example.demo.service.ClienteService;
import com.example.demo.model.Cliente;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoService;
    
    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getCarrinho(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(carrinhoService.getCarrinhoByClienteId(clienteId));
        } catch (CarrinhoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}