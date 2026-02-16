package com.example.demo.service;

import com.example.demo.dto.LoginRepose;
import com.example.demo.model.Cliente;
import com.example.demo.model.Endereco;
import com.example.demo.model.Usuario;
import com.example.demo.model.Vendedor;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.enums.EnumRole;

import org.springframework.stereotype.Service;

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
            return usuario;
        }
        return null;
    }

    // Criar cliente
    public Cliente criarCliente(Cliente cliente) {
        cliente.setNivelAcesso(EnumRole.CLIENTE); // setNivelAcesso existe via Lombok
        cliente.setAtivo(true);                     // setAtivo existe via Lombok
        return usuarioRepository.save(cliente);     // save retorna Usuario, mas podemos cast se quiser
    }

    // Login
    public LoginRepose login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha) && usuario.getAtivo()) {
            return new LoginRepose(usuario.getId(), usuario instanceof Vendedor);
        }
        return null;
    }

    // Listar endereços
    public List<Endereco> listarEnderecos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario instanceof Cliente cliente) {  // Pattern matching do Java 17+
            return cliente.getEnderecos();
        }
        return null;
    }

    // Adicionar endereço
    public Cliente adicionarEndereco(Long usuarioId, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario instanceof Cliente cliente) {
            cliente.adicionarEndereco(endereco); // usa método da entidade
            return usuarioRepository.save(cliente);
        }
        return null;
    }

    // Criar vendedor
    public Vendedor criarVendedor(Vendedor vendedor) {
        vendedor.setNivelAcesso(EnumRole.VENDEDOR);
        vendedor.setAtivo(true);
        return usuarioRepository.save(vendedor);
    }


}
