package com.example.demo.service;

import com.example.demo.model.Endereco;
import com.example.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
    
    public List<Endereco> listarPorUsuario(Long clienteId) {
        // Isso precisará ser implementado no repository
        return enderecoRepository.findByClienteId(clienteId);
    }
}