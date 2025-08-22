package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.LoginRequest;
import com.example.demo.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.autenticar(request.getEmail(), request.getSenha());
        if (usuario != null) {
            return ResponseEntity.ok(usuario); // Login bem-sucedido
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }

    package com.example.demo.controller;

    import com.example.demo.model.Usuario;
    import com.example.demo.model.Cliente;
    import com.example.demo.model.Vendedor;
    import com.example.demo.model.Endereco;
    import com.example.demo.service.UsuarioService;
    import com.example.demo.service.LoginRequest;
    import com.example.demo.dto.LoginResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/usuarios")
    public class UsuarioController {
        private final UsuarioService usuarioService;
        
        public UsuarioController(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }
        
        // Métodos existentes...
        
        // Endpoint para criar cliente
        @PostMapping("/cliente")
        public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
            try {
                Cliente novoCliente = usuarioService.criarCliente(cliente);
                return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        // Endpoint para criar vendedor
        @PostMapping("/vendedor")
        public ResponseEntity<Vendedor> criarVendedor(@RequestBody Vendedor vendedor) {
            try {
                Vendedor novoVendedor = usuarioService.criarVendedor(vendedor);
                return ResponseEntity.status(HttpStatus.CREATED).body(novoVendedor);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        // Endpoint de login atualizado
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            LoginResponse response = usuarioService.login(request.getEmail(), request.getSenha());
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
            }
        }
        
        // Endpoint para listar endereços de um usuário
        @GetMapping("/{id}/enderecos")
        public ResponseEntity<List<Endereco>> listarEnderecos(@PathVariable Long id) {
            List<Endereco> enderecos = usuarioService.listarEnderecos(id);
            if (enderecos != null) {
                return ResponseEntity.ok(enderecos);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        
        // Endpoint para adicionar endereço a um usuário
        @PostMapping("/{id}/enderecos")
        public ResponseEntity<Cliente> adicionarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
            Cliente cliente = usuarioService.adicionarEndereco(id, endereco);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        
    }
}
}
