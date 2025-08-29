package com.example.demo.controller;

import com.example.demo.dto.UsuarioCadastroDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Usuario;
import com.example.demo.model.Vendedor;
import com.example.demo.service.UsuarioService;
import com.example.demo.enums.EnumRole;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- Cadastro de cliente ou vendedor ---
    @PostMapping("/register")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto) {
        try {
            if ("cliente".equalsIgnoreCase(dto.getTipoConta())) {
                Cliente cliente = new Cliente();
                cliente.setNome(dto.getNome());
                cliente.setEmail(dto.getEmail());
                cliente.setSenha(dto.getSenha());
                cliente.setCpf(dto.getCpf());
                cliente.setNivelAcesso(EnumRole.CLIENTE);
                cliente.setAtivo(true);

                Cliente novoCliente = usuarioService.criarCliente(cliente);
                return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);

            } else if ("vendedor".equalsIgnoreCase(dto.getTipoConta())) {
                Vendedor vendedor = new Vendedor();
                vendedor.setNome(dto.getNome());
                vendedor.setEmail(dto.getEmail());
                vendedor.setSenha(dto.getSenha());
                vendedor.setCnpj(dto.getCnpj());
                vendedor.setNivelAcesso(EnumRole.VENDEDOR);
                vendedor.setAtivo(true);

                Vendedor novoVendedor = usuarioService.criarVendedor(vendedor);
                return ResponseEntity.status(HttpStatus.CREATED).body(novoVendedor);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Tipo de conta inválido. Use 'cliente' ou 'vendedor'."));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 👈 isso mostra o erro detalhado no console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro ao cadastrar usuário", "error", e.getMessage()));
        }
    }

    // --- Login ---
    // --- Login ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String senha = loginRequest.get("senha");

        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não encontrado"));
        }

        if (!usuario.getSenha().equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Senha incorreta"));
        }
        
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "Login realizado com sucesso");
        response.put("usuarioId", usuario.getId());
        response.put("email", usuario.getEmail());
        response.put("nome", usuario.getNome());
        response.put("nivelAcesso", usuario.getNivelAcesso().name());

        // 🔑 Se for cliente, adiciona clienteId
        if (usuario instanceof Cliente cliente) {
            response.put("clienteId", cliente.getId());
        }

        // 🔑 Se for vendedor, adiciona vendedorId
        if (usuario instanceof Vendedor vendedor) {
            response.put("vendedorId", vendedor.getId());
        }

        return ResponseEntity.ok(response);
    }
}