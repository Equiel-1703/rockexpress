package com.example.demo.service;

import com.example.demo.enums.EnumRole;
import com.example.demo.model.Vendedor;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Vendedor> listarTodos() {
        return vendedorRepository.findAll();
    }

    public Vendedor buscarPorId(Long id) {
        return vendedorRepository.findById(id).orElse(null);
    }

    public Vendedor buscarPorEmail(String email) {
        return vendedorRepository.findByEmail(email);
    }

    // Método para criar vendedor (deve estar aqui)
    public Vendedor criarVendedor(Vendedor vendedor) {
        vendedor.setNivelAcesso(EnumRole.VENDEDOR);
        vendedor.setAtivo(true);
        return vendedorRepository.save(vendedor);
    }

    public Vendedor atualizarVendedor(Long id, Vendedor vendedorAtualizado) {
        Optional<Vendedor> vendedorExistente = vendedorRepository.findById(id);
        if (vendedorExistente.isPresent()) {
            Vendedor vendedor = vendedorExistente.get();
            vendedor.setNome(vendedorAtualizado.getNome());
            vendedor.setEmail(vendedorAtualizado.getEmail());
            // Atualize outros campos conforme necessário
            return vendedorRepository.save(vendedor);
        }
        return null;
    }

    public void deletarVendedor(Long id) {
        vendedorRepository.deleteById(id);
    }

    public Vendedor buscarComProdutos(Long id) {
        return vendedorRepository.findByIdWithProdutos(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
    }

    public List<Vendedor> buscarVendedoresAtivos() {
        return vendedorRepository.findByAtivoTrue();
    }
}