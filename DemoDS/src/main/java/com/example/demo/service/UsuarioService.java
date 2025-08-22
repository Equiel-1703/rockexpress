package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario; // login bem-sucedido
        }
        return null; // login falhou
    }
    
    // Método para criar cliente
    public Cliente criarCliente(Cliente cliente) {
        cliente.setNivelAcesso(EnumRole.ROLE_CLIENTE);
        cliente.setAtivo(true);
        return (Cliente) usuarioRepository.save(cliente);
    }
    
    // Método para criar vendedor
    public Vendedor criarVendedor(Vendedor vendedor) {
        vendedor.setNivelAcesso(EnumRole.ROLE_VENDEDOR);
        vendedor.setAtivo(true);
        return (Vendedor) usuarioRepository.save(vendedor);
    }
    
    // Método para login
    public LoginResponse login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha) && usuario.getAtivo()) {
            return new LoginResponse(usuario.getId(), usuario instanceof Vendedor);
        }
        return null;
    }
    
    // Método para buscar endereços de um usuário
    public List<Endereco> listarEnderecos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            return cliente.getEnderecos();
        }
        return null; // Vendedores não têm endereços
    }
    
    // Método para adicionar endereço a um cliente
    public Cliente adicionarEndereco(Long usuarioId, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            endereco.setUsuario(usuario); // Supondo que Endereco tenha um campo usuario
            cliente.getEnderecos().add(endereco);
            return (Cliente) usuarioRepository.save(cliente);
        }
        return null; // Não é um cliente
    }
}
