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

    // --- Cadastro de cliente/vendedor ---
    @PostMapping("/register")
    public Object cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto) {
        if ("cliente".equalsIgnoreCase(dto.getTipoConta())) {
            Cliente cliente = new Cliente();
            cliente.setNome(dto.getNome());
            cliente.setEmail(dto.getEmail());
            cliente.setSenha(dto.getSenha());
            cliente.setCpf(dto.getCpf());
            cliente.setNivelAcesso(EnumRole.CLIENTE);
            cliente.setAtivo(true);
            return usuarioService.criarCliente(cliente);

        } else if ("vendedor".equalsIgnoreCase(dto.getTipoConta())) {
            Vendedor vendedor = new Vendedor();
            vendedor.setNome(dto.getNome());
            vendedor.setEmail(dto.getEmail());
            vendedor.setSenha(dto.getSenha());
            vendedor.setCnpj(dto.getCnpj());
            vendedor.setNivelAcesso(EnumRole.VENDEDOR);
            vendedor.setAtivo(true);
            return usuarioService.criarVendedor(vendedor);

        } else {
            throw new IllegalArgumentException("Tipo de conta inválido");
        }
    }

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
            // ⚠️ Em produção use BCrypt para verificar senhas!
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Senha incorreta"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login realizado com sucesso",
                "usuarioId", usuario.getId(),
                "email", usuario.getEmail(),
                "nome", usuario.getNome(),
                "nivelAcesso", usuario.getNivelAcesso().name()
        ));
    }
}
